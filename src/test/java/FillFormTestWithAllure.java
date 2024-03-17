import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;

import static Utils.RandomDateUtils.*;
import static io.qameta.allure.Allure.step;

@Tag("Selenoid")
public class FillFormTestWithAllure extends TestBase {


    String fileName = "1653613466_10-funart-pro-p-krisa-za-kompom-krasivo-foto-10.jpg";
    File fileToUpload = new File("src/test/java/Resourses/" + fileName);
    String firstName = getRandomName();
    String lastName = getRandomLastName();
    String email = getRandomEmail();
    String gender = getRandomItemFromArray(genders);
    String number = String.valueOf(getRandomNumber());
    String yearOfBirth = String.valueOf(getRandomInt(2000, 2010));
    String monthOfBirth = "September";
    String dayOfBirth = String.valueOf(getRandomInt(1, 30));
    String subject = "Math";
    String hobbie = "Music";
    String address = getRandomAdress();
    String state = "Haryana";
    String city = "Karnal";
    String checkPersonality = "Student Name";
    String checkEmail = "Student Email";
    String checkGender = "Gender";
    String checkMobile = "Mobile";
    String checkDateOfBirth = "Date of Birth";
    String checkSubjects = "Subjects";
    String checkHobbies = "Hobbies";
    String checkFileName = "Picture";
    String checkAddress = "Address";
    String checkStateAndCity = "State and City";

    @Test
    @DisplayName("Успешная регистрация")
    @Feature("Форма регистрации")
    void successRegistrationTestWithAllure() {

        step("Открыть страницу регистрации в браузере", () -> {
            registrationPage.openRegistrationPage();
        });

        step("Заполнить форму регистрации", () -> {
            registrationPage.setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setGender(gender)
                    .setNumber(number)
                    .setDateOfBirth(dayOfBirth, monthOfBirth, yearOfBirth)
                    .setSubject(subject)
                    .setHobbies(hobbie)
                    .uploadPicture(fileToUpload)
                    .setAdress(address)
                    .setState(state)
                    .setCity(city);
        });

        step("Нажать на кнопку подтверждения", () -> {
            registrationPage.clickSabmitButton()
                    .checkModalHead();
        });

        step("Проверка соответствия введенных данных и данных в модальном оке", () -> {
            registrationPage.checkModalValue(checkPersonality, firstName + " " + lastName)
                    .checkModalValue(checkEmail, email)
                    .checkModalValue(checkGender, gender)
                    .checkModalValue(checkMobile, number)
                    .checkModalValue(checkDateOfBirth, dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                    .checkModalValue(checkSubjects, subject)
                    .checkModalValue(checkHobbies, hobbie)
                    .checkModalValue(checkFileName, fileName)
                    .checkModalValue(checkAddress, address)
                    .checkModalValue(checkStateAndCity, state + " " + city);
        });

    }
}
