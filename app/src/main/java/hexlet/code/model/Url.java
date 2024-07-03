package hexlet.code.model;

import java.sql.Timestamp;

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

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
