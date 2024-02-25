package land.leets.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSetup implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jdbcTemplate.execute("ALTER DATABASE leets CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci");
        jdbcTemplate.execute("ALTER TABLE portfolios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        jdbcTemplate.execute("DROP TABLE contributors");
        jdbcTemplate.execute("DROP TABLE portfolios");
    }
}
