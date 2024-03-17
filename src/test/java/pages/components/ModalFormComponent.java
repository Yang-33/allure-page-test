package pages.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ModalFormComponent {

    public void modalValue(String key, String value){
        $(".table-responsive").$(byText(key)).parent().shouldHave(text(value));
    }

    public void modalHead(){
        $("#example-modal-sizes-title-lg").shouldHave(exactText("Thanks for submitting the form"));
    }
}
