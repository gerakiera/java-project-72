package hexlet.code.controller;

import hexlet.code.dto.MainPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void root(Context ctx) {
        var flash = ctx.consumeSessionAttribute("flash");
        var page = new MainPage((String) flash);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("index.jte", model("page", page));
    }
    public static void index(Context ctx) throws SQLException {
        var page = new UrlsPage(UrlsRepository.getEntities(),
                ctx.consumeSessionAttribute("flash"),
                UrlChecksRepository.findLatestChecks());
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        //url.setUrlChecks(UrlChecksRepository.getEntitiesByUrlId(id));
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new UrlPage(url, flash);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) {
        try {
            var noValidName = ctx.formParam("url");
            var urlName = new URL(Objects.requireNonNull(noValidName));
            String name = urlName.getProtocol() + "://" + urlName.getAuthority();
            var url = new Url(name);
            UrlsRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некоректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }
}