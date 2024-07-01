package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MainPage extends BasePage{
    public MainPage(String flash) {
        super(flash);
    }
}
