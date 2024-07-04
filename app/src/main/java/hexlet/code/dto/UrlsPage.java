package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.List;
import java.util.Map;

public final class UrlsPage {
    private List<Url> urls;
    private String flash;
    private Map<Long, UrlCheck> urlChecks;

    public UrlsPage(List<Url> urls, String flash, Map<Long, UrlCheck> urlChecks) {
        this.urls = urls;
        this.flash = flash;
        this.urlChecks = urlChecks;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public Map<Long, UrlCheck> getUrlChecks() {
        return urlChecks;
    }

    public void setUrlChecks(Map<Long, UrlCheck> urlChecks) {
        this.urlChecks = urlChecks;
    }
}
