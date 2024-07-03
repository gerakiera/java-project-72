package hexlet.code.dto;

import hexlet.code.model.Url;
import lombok.Getter;

@Getter
public class UrlPage {
    private final Url url;
    private final String flash;
    public UrlPage(Url url, String flash) {
        this.url = url;
        this.flash = flash;
    }
}
