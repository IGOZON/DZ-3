import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class OrderingCardTest {
    SelenideElement form = $(".form");

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompletedTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов-Иванович Иван");
        $("[data-test-id='phone'] input").setValue("+78005553535");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    public void deliveryToTheCityAbroadTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Гонконг");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Евгений");
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void deliveryToTheCityInLatinTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Moscow");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Евгений");
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void ChoosingDateLessThanThreeDaysTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String planningDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Евгений");
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }


    //bug
    @Test
    public void specialLetterTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артём");
        $("[data-test-id='phone'] input").setValue("+78005553535");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }

    //bug
    @Test
    public void introductionOfTheFullNameTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем Петрович");
        $("[data-test-id='phone'] input").setValue("+78005553535");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void nameInLatinTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Igor Ivanov");
        $("[data-test-id='phone'] input").setValue("+78005553535");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //bug
    @Test
    public void introductionOfTheNameOnlyTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Игорь");
        $("[data-test-id='phone'] input").setValue("+79991231212");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    public void moreDigitsNumberFieldTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='phone'] input").setValue("+780055535357");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void lackPlusInNumberFieldTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='phone'] input").setValue("88005553535");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void fewerDigitsNumberFieldTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='phone'] input").setValue("+7999123112");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSendRequestEmptyCityTest() {
        open("http://localhost:9999");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNRequestEmptyDateTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldRequestEmptyNameTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79991231122");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRequestEmptyPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Артем");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__inner .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void notPressedFlagTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79220000000");
        form.$("button.button").click();
        $("[data-test-id=agreement].input_invalid").should(visible);
    }

}

