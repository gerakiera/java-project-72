package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
public class UrlCheck {
    private Long id;
    private Long urlId;
    private Integer statusCode;
    private String title;
    private String h1;
    private String description;
    private Timestamp createdAt;

    public UrlCheck(
            Integer statusCode,
            String title,
            String h1,
            String description) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
    }

    public UrlCheck(long id, Long urlId, int statusCode, String title, String h1, String description,
                    Timestamp createdAt) {
        this.id = id;
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.createdAt = createdAt;
    }

}
