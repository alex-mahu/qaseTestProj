import TestCases.TestCase1;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test3 {

    WebDriver driver;

    @BeforeClass
    public void createDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://google.com");
    }

    @Test
    ///@CaseId(2)
    public void test1() {
        new TestCase1(driver)
                .step1()
                .step2()
                .step3()
                .finishCase();
    }

    @AfterClass
    public void destroyDriver() {
        driver.quit();
    }
}
