package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {
    private static MockWebServer mockWebServer;
    private static Javalin app;
    @BeforeEach
    public final void setApp() throws IOException, SQLException {
        app = App.getApp();
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        var html = Files.readString(Paths.get("src/test/resources/test.html"));
        var serverResponse = new MockResponse()
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .setResponseCode(200)
                .setBody(html);
        mockWebServer.enqueue(serverResponse);
        mockWebServer.start();
    }

    @AfterAll
    public static void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            //assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }
    @Test
    void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            //assertThat(response.body().string()).contains("ID", "Имя", "Последняя проверка", "Код ответа");
        });
    }
    @Test
    void testUrlsShow() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/2");
            assertThat(response.code()).isEqualTo(404);
        });
    }
    @Test
    void testShowUrl() {
        JavalinTest.test(app, (server, client) -> {
            var newUrl = new Url("https://ru.hexlet.io/projects/72/members/39734?step=6");
            UrlsRepository.save(newUrl);
            var response = client.get("/urls/" + newUrl.getId());
            var response2 = client.post("/urls/" + newUrl.getId() + "/checks");
            assert response.body() != null;
            assertThat(response.body().string()).contains("https://ru.hexlet.io");
            assertThat(response2.code()).isEqualTo(200);
        });
    }
}
