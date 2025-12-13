package land.leets.domain.interview.type

enum class HasInterview(val statusKo: String) {
    CHECK("참여"),
    UNCHECK("불참"),
    PENDING("미정")
}
