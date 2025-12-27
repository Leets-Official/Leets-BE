package land.leets.global.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class DatabaseSetup(
    private val jdbcTemplate: JdbcTemplate
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        jdbcTemplate.execute("ALTER DATABASE leets CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci")
        jdbcTemplate.execute("ALTER TABLE portfolios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    }
}
