package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppTest {
    private static MockWebServer server;
    public static Javalin app;
    private static String testUrl;
    
    @BeforeEach
    public final void setApp() throws IOException, SQLException {
        app = App.getApp();
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        server = new MockWebServer();
        var html = Files.readString(Paths.get("src/test/resources/test.html"));
        var serverResponse = new MockResponse()
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .setResponseCode(200)
                .setBody(html);
        server.enqueue(serverResponse);
        server.start();
    }

    @AfterAll
    public static void shutDown() throws IOException {
        server.shutdown();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (serv, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    void testUrlsPageHandler() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("ID", "Имя", "Последняя проверка", "Код ответа");
        });
    }

    @Test
    void testShowUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://www.example.com");
            UrlsRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.example.com");
        });
    }


}