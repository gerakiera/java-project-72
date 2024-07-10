package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Url(String name) {
        this.name = name;
    }

}
