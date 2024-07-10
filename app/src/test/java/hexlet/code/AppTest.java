package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
    public static Javalin app;
    //private static String testUrl;
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
            Assertions.assertEquals(200, response.code());
        });
    }
    @Test
    public void testAddUrl() {
        JavalinTest.test(app, (srv, client) -> {
            var requestBody = "url=http://localhost:7070/abc";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://localhost:7070");
            var url = UrlsRepository.getByName("http://localhost:7070")
                    .orElse(new Url("")).getName();
            assertThat(url).contains("http://localhost:7070");
        });
    }
    @Test
    public void testAddBadUrl() {
        JavalinTest.test(app, (srv, client) -> {
            var requestBody = "url=abc";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            var urls = UrlsRepository.getByName("abc");
            assertThat(urls).isEmpty();
        });
    }
}
