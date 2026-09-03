# 지원서 개인정보 빈 값 대응 가이드

## 1. 원인

문제의 핵심 원인은 서버가 빈 문자열을 그대로 저장하고 있었기 때문입니다.

- `ApplicationRequest`의 `name`, `phone`, `major`, `grade`, `interviewDay`, `interviewTime`, `motive`, `expectation`, `capability`, `conflict`, `passion` 등은 비어 있어도 서버가 수용했습니다.
- `ApplicationController`에서 `@Valid` 검증이 빠져 있었습니다.
- `CreateApplicationImpl`는 `request.phone`, `request.name`을 그대로 `User` / `Application`에 저장했습니다.
- 결과적으로 프론트가 빈 값으로 제출해도 DB에 저장되어, 관리자 화면에서 `""` 또는 `null`처럼 보이는 상태가 발생했습니다.

## 2. 백엔드 임시 조치

이번 수정으로 다음을 막았습니다.

- 최종 제출용 `ApplicationRequest`에 필수 값 검증을 추가
- `ApplicationController`의 최종 제출 요청에 `@Valid` 적용
- 임시저장용 `TemporaryApplicationRequest`는 "완성본 검증"이 아니라 "부분저장 허용" 구조로 이해해야 함
- `request.phone`, `request.sid`가 빈 값/문자열 `null`/`undefined`일 때 사용자 정보 덮어쓰기를 건너뛰도록 보완

중요한 점:

- `TemporaryApplicationRequest`에 `@NotBlank`를 걸면 임시저장 기능이 깨집니다.
- 임시저장은 정의상 미완성 데이터가 기본이므로, 최종 제출용 DTO와 다르게 처리해야 합니다.
- 즉, `@NotBlank`/`@Valid`는 최종 제출 API에만 적용해야 합니다.

## 3. 프론트 전달 사항

프론트는 아래 규칙을 반드시 지켜야 합니다.

### 필수 체크

- 최종 제출 전 `name`, `phone`, `major`, `grade`, `position`, `interviewDay`, `interviewTime`, `motive`, `expectation`, `capability`, `conflict`, `passion`을 모두 검증해야 합니다.
- 빈 문자열만 들어가는 경우엔 API 호출을 막아야 합니다.
- `trim()`을 적용한 뒤 빈 값인지 확인해야 합니다.
- `sid`는 문자열 `"null"`, `"undefined"`를 실제 값으로 취급하지 않도록 처리해야 합니다.

### 임시저장 규칙

- 임시저장 요청은 부분 데이터일 수 있으므로, 사용자 입력이 비어 있어도 400이 나면 안 됩니다.
- 임시저장 시 현재 단계에서 비어 있는 필드는 기존 값으로 유지하거나, 서버가 null-safe하게 처리해야 합니다.
- `TemporaryApplicationRequest`에는 최종 제출용 필수값 검증을 걸지 말아야 합니다.

### 필수 UX 규칙

- 사용자가 입력을 비운 상태로 최종 제출 버튼을 누르면 즉시 에러 메시지를 표시
- `input`이 비어 있을 때는 바로 경고 UI 노출
- 백엔드 에러를 받더라도 빈 값 방지 로직을 프론트에서도 유지해야 함
- `phone`은 숫자만 허용, 길이/형식 검사 필요
- 중복 제출 방지: `isSubmitting`, disabled, 성공 시 잠금 유지

## 4. DB 위치 관련 확인

코드상으로는 DB가 Cloudflare가 아니라 서버 환경 변수로 연결되는 MySQL 구조로 보입니다.

### 코드상 근거

`src/main/resources/application.yml`에 다음이 있습니다.

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${DATABASE_USERNAME}
    url: ${DATABASE_URL}
    password: ${DATABASE_PASSWORD}
```

즉, 실제 DB 주소/비밀번호는 코드 안에 있지 않고, 서버 배포 환경 변수로 주입됩니다.

### 정리

- Cloudflare: DNS, WAF, CDN, SSL, 보안 레이어 역할
- OCI: 현재 코드에서 이미지 저장용 `oci.*` 설정이 보이므로, 백엔드 + DB가 OCI 상에 있을 가능성이 높습니다.
- 프론트 주소 `https://158.180.90.211.nip.io`는 OCI 대역으로 보이며, AWS RDS를 먼저 가정하는 건 잘못된 추정입니다.
- 결론적으로: 코드만으로는 확정 불가하며, `DATABASE_URL`과 해당 인프라 콘솔/환경변수부터 확인해야 합니다.

## 5. 운영 대응

- DB에 이미 비어 있는 데이터가 들어간 행은 직접 정리 필요
- 이름/전화번호가 비어 있는 지원자에 대해 재신청 안내 또는 별도 인증 절차 필요
- 다음 기수부터는 프론트+백엔드 둘 다 빈 값 방지 로직 강제
- 개인정보는 로그에 찍지 말고 마스킹 처리
- 액세스 토큰, 리프레시 토큰은 서버 로그에 노출하면 안 됩니다. `console.log`로 토큰 전체를 찍는 건 보안 사고입니다.

## 6. 추천 액션

1. 최종 제출용 DTO에만 `@NotBlank` 검증 적용
2. 임시저장 로직은 부분 데이터 허용 구조로 유지
3. 서버에서 중복 제출 방어 및 `sid` 문자열 null 처리 보완
4. 운영 DB에서 중복 지원 데이터 정리 후 UNIQUE 제약 추가
5. 인프라에서 `DATABASE_URL`이 실제로 어디를 가리키는지 확인

## 7. 재배포 전 체크리스트 (main 브랜치 직접 배포용)

main 브랜치에 바로 반영되는 구조라면, 아래 항목을 반드시 확인해야 합니다.

### 7-1. 최종 제출 검증

- [ ] `ApplicationRequest`의 필수 값 검증이 실제로 동작하는지 확인
- [ ] `@Valid`가 최종 제출 `POST /application`에 붙어 있는지 확인
- [ ] 빈 문자열, `"null"`, `"undefined"`가 최종 제출에서 허용되지 않는지 확인
- [ ] `name`, `phone`, `major`, `grade`, `position`, `interviewDay`, `interviewTime`, `motive`, `expectation`, `capability`, `conflict`, `passion`이 모두 검증되는지 확인

### 7-2. 임시저장 복원 검증

- [ ] `PUT /temporary-application`이 부분 데이터를 그대로 저장하는지 확인
- [ ] 비어 있는 필드가 임시저장 시 덮어써지지 않는지 확인
- [ ] GET `/temporary-application` 후 비어 있는 값이 보이지 않는지 확인
- [ ] 임시저장 후 재접속 시 값이 비어 보이지 않는지 확인
- [ ] `TemporaryApplicationRequest`에 최종 제출용 `@NotBlank`가 걸려 있지 않은지 확인

### 7-3. 중복 제출 보호

- [ ] 동일 사용자가 같은 제출 요청을 여러 번 보내더라도 서버에서 막히는지 확인
- [ ] 중복 데이터 정리 완료 후 `applications.user_id` UNIQUE 제약을 추가할 수 있는지 확인
- [ ] 이미 제출된 사용자가 다시 `POST /application`하면 `409 Conflict`가 반환되는지 확인
- [ ] 네트워크 실패 후 재시도 시에도 중복 제출이 막히는지 확인

### 7-4. 중복 정리 순서

UNIQUE 제약은 다음 순서로 적용해야 합니다.

1. 중복 조회
2. 어느 행을 남길지 결정 (`MIN(id)` 또는 최신 `updated_at` 기준)
3. 중복 행 삭제
4. UNIQUE 제약 추가

예시:

```sql
SELECT user_id, COUNT(*) c, GROUP_CONCAT(id) ids
FROM applications
GROUP BY user_id
HAVING c > 1;
```

### 7-5. 데이터 복구/안전장치

- [ ] 빈 값이 이미 DB에 들어간 행을 조회하는 SQL을 실행했는지 확인
- [ ] `users.email` 기반 연락 경로 확보가 가능한지 확인
- [ ] `applications`와 `users` 조인 쿼리로 이메일/지원자 식별이 되는지 확인
- [ ] 액세스 토큰/리프레시 토큰 로그 보관 여부를 점검했는지 확인

### 7-6. 배포 직전 점검

- [ ] PR 없이 main에 바로 반영하는 상황이라면, QA 확인을 최소 1회 더 수행
- [ ] 운영 데이터 영향 범위를 사전에 확인
- [ ] 배포 직후, 다음 두 가지를 즉시 검증
  - 새 지원서 제출에서 빈 값이 막히는지
  - 임시저장 복원 시 값이 정상적으로 떠오는지
- [ ] 배포 후 15분 내 조치 가능 상태를 확보

## 8. 프론트 전달용 문구

아래 문구를 그대로 프론트에 전달하면 됩니다.

> 지원서 제출 전 빈 값 검증을 반드시 추가해주세요.
>
> - 최종 제출에서 `name`, `phone`, `major`, `grade`, `position`, `interviewDay`, `interviewTime`, `motive`, `expectation`, `capability`, `conflict`, `passion`은 모두 `trim()` 후 빈 값 여부를 확인해야 합니다.
> - 빈 값이면 API 호출 전에 막아 주세요.
> - 제출 버튼은 `isSubmitting`/`disabled` 상태를 유지해서 중복 클릭을 막아 주세요.
> - `phone`은 숫자/형식 검증을 추가해 주세요.
> - `sid`는 문자열 `"null"`, `"undefined"`를 실제 값으로 취급하지 않도록 처리해 주세요.
> - 임시저장 요청은 부분 데이터일 수 있으므로, 비어 있는 필드는 최종 제출 검증에 걸리지 않도록 유지해 주세요.
> - 서버에서 이미 제출된 사용자에게 409를 리턴할 수 있으니, 사용자에게 "이미 제출된 지원서입니다" 메시지를 보여 주세요.
> - 액세스 토큰/리프레시 토큰은 서버 로그에 찍지 말아 주세요.

## 9. 프론트 구현 체크리스트

- [ ] 최종 제출 form validation
- [ ] 각 input의 trim + blank check
- [ ] 최종 제출 버튼 disabled 처리
- [ ] 중복 클릭/중복 요청 방지
- [ ] 네트워크 실패 후 재제출 방지 UX
- [ ] 임시저장 복원값 빈 값 체크
- [ ] `sid`/`phone` 문자열 `null` 처리 방지
- [ ] 에러 메시지 UI 표기
- [ ] 토큰 로그 제거

## 10. 운영 기준으로 가장 중요한 3개

1. 최종 제출 시 빈 값이 서버로 전달되면 막는다.
2. 임시저장 값이 복원되는지 검증한다.
3. 서버에서 중복 제출을 막는다.

이 3개가 지켜지면 이번 이슈의 재발 가능성을 크게 낮출 수 있습니다.
