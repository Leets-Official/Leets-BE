package land.leets.domain.application.type

enum class ApplicationStatus(val statusKo: String) {
    PASS_PAPER("서류 합격"),
    FAIL_PAPER("서류 불합격"),
    PASS("최종 합격"),
    FAIL("최종 불합격"),
    PENDING("보류");

    companion object {
        fun finals(): List<ApplicationStatus> = listOf(PASS, FAIL)
        fun papers(): List<ApplicationStatus> = listOf(PASS_PAPER, FAIL_PAPER)
    }
}
