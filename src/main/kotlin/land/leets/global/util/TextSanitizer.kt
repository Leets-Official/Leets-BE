package land.leets.global.util

/**
 * 프론트에서 넘어온 문자열을 정리한다.
 *
 * 공백만 있는 값, 그리고 자바스크립트 값이 문자열로 직렬화되며 넘어온
 * "null" / "undefined" 는 값이 없는 것으로 취급한다.
 */
object TextSanitizer {

    private val PLACEHOLDERS = setOf("null", "undefined")

    fun clean(value: String?): String? =
        value?.trim()?.takeIf { it.isNotEmpty() && it.lowercase() !in PLACEHOLDERS }
}
