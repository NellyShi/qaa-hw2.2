package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class TestingCardDeliveryOrderForm {

    @Test
    void shouldCityBeRussia() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Шигапова Нэлли");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id= 'agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Встреча успешно забронирована")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCityBeRussia1() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='name'] input").setValue("Шигапова Нэлли");
        $("[data-test-id='phone'] input").setValue("+79000000000");
        $("[data-test-id= 'agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Встреча успешно забронирована")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCityBeOnlyRussia() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Минск");
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldRecieveBeAtLeastThreeDays() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = date.plusDays(2).format(formatter);
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(text);
        $(".button").click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldNotNameBeLatin() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='name'] input").setValue("Shigapova Nelli");
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }

    @Test
    void shouldNotNameWithUnderscore() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='name'] input").setValue("Шигапова_Нэлли");
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }

    @Test
    void shouldNotPhoneBeMoreMoreThan11() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='name'] input").setValue("Шигапова Нэлли");
        $("[data-test-id='phone'] input").setValue("+790000000000");
        $("[data-test-id= 'agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр")).shouldBe(visible);
    }

    @Test
    void shouldBeAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ульяновск");
        $("[data-test-id='name'] input").setValue("Шигапова Нэлли");
        $("[data-test-id='phone'] input").setValue("+70900000000");
        $(".button").click();
        $("[data-test-id= 'agreement'] .checkbox__box").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}
