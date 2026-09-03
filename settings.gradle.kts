plugins {
    // 로컬에 JDK 21 이 없으면 자동으로 내려받는다. 개발자마다 기본 JDK 가 달라도 빌드가 깨지지 않는다.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "leets"
