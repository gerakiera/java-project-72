package hexlet.code.dto;

import lombok.Getter;

@Getter
public class MainPage extends BasePage {
    public MainPage(String flash) {
        super(flash);
    }

    public void setFlash(String flash) {
        super.setFlash(flash);
    }
}
