package land.leets.domain.interview.usecase;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import land.leets.domain.interview.type.HasInterview;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest
class CreateInterviewImplTest {

	MockWebServer server = new MockWebServer();

	@Autowired
	CreateInterview createInterview;

	@Test
	void execute_mock() throws InterruptedException, IOException {
		server.start(8080);
		server.enqueue(new MockResponse().setResponseCode(200));

		createInterview.execute(UUID.randomUUID(), HasInterview.CHECK);

		RecordedRequest request = server.takeRequest();
		assertThat(request.getPath()).isEqualTo("/interview");

		server.shutdown();
	}
}
