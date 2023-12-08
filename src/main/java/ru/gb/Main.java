package ru.gb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
    public static void main(String[] args) {

        String pathToChromeDriver = "src/main/resources/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.manage().window().maximize();
        chromeDriver.get("https://test-stand.gb.ru/login");
//        chromeDriver.get("https://www.google.com");

        System.out.println("title = " + chromeDriver.getTitle());

        chromeDriver.quit();

//        String pathToGeckoDriver = "src/main/resources/geckodriver";
//        System.setProperty("webdriver.firefox.driver", pathToGeckoDriver);
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.addArguments("--headless");
//        WebDriver ffDriver = new FirefoxDriver(firefoxOptions);
//        ffDriver.manage().window().maximize();
//        ffDriver.get("https://test-stand.gb.ru/login");
//        System.out.println("title = " + ffDriver.getTitle());
//        ffDriver.quit();

    }
}