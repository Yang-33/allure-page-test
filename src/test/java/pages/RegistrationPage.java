package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;
import pages.components.ModalFormComponent;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RegistrationPage {

    CalendarComponent calendarComponent = new CalendarComponent();
    ModalFormComponent modalCompanent = new ModalFormComponent();
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement firstLastInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement genderInput = $("#genterWrapper");
    private final SelenideElement numberInput = $("#userNumber");
    private final SelenideElement subjectInput = $("#subjectsInput");
    private final SelenideElement hobbiesInput = $("#hobbiesWrapper");
    private final SelenideElement fileInput = $("#uploadPicture");
    private final SelenideElement adresInput = $("#currentAddress");
    private final SelenideElement stateInput = $(byText("Select State"));
    private final SelenideElement stateCityDropDown = $("#stateCity-wrapper");
    private final SelenideElement cityInput = $(byText("Select City"));
    private final SelenideElement submitButton = $("#submit");

    public RegistrationPage openRegistrationPage() {
        open("https://demoqa.com/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        return this;
    }

    public RegistrationPage setFirstName(String name) {
        firstNameInput.setValue(name);
        return this;
    }

    public RegistrationPage setLastName(String lastName) {
        firstLastInput.setValue(lastName);
        return this;
    }

    public RegistrationPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public RegistrationPage setGender(String gender) {
        genderInput.$(byText(gender)).click();
        return this;
    }

    public RegistrationPage setNumber(String namber) {
        numberInput.setValue(namber);
        return this;
    }

    public RegistrationPage setSubject(String subject) {
        subjectInput.setValue(subject).pressEnter();
        return this;
    }

    public RegistrationPage setHobbies(String hobbies) {
        hobbiesInput.$(byText(hobbies)).click();
        return this;
    }

    public RegistrationPage uploadPicture(File file) {
        fileInput.uploadFile(file);
        return this;
    }

    public RegistrationPage setAdress(String adress) {
        adresInput.setValue(adress);
        return this;
    }

    public RegistrationPage setState(String state) {
        stateInput.click();
        stateCityDropDown.$(byText(state)).click();
        return this;
    }

    public RegistrationPage setCity(String city) {
        cityInput.click();
        stateCityDropDown.$(byText(city)).click();
        return this;
    }

    public RegistrationPage clickSabmitButton() {
        submitButton.click();
        return this;
    }

    public RegistrationPage setDateOfBirth(String day, String month, String year) {
        $("#dateOfBirthInput").click();
        calendarComponent.setDateOfBirth(day, month, year);
        return this;
    }

    public RegistrationPage checkModalHead() {
        modalCompanent.modalHead();
        return this;
    }

    public RegistrationPage checkModalValue(String key, String value) {
        modalCompanent.modalValue(key, value);
        return this;
    }

}
