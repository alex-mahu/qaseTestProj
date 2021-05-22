import TestCases.TestCase1;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Test3 extends CoreTestClass {

    WebDriver driver;

    @BeforeClass
    public void createDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://google.com");
    }

    @Test(dataProvider = "provideData")
    ///@CaseId(2)
    public void test1(String s) {
        new TestCase1(driver, dataProviderOptions)
                .step1()
                .step2(s)
                .step3()
                .finishCase();
    }

    @AfterClass
    public void destroyDriver() {
        driver.quit();
    }

    @DataProvider
    public Object[][] provideData() {
        return new Object[][] {
                {"Ssssssssss"},
                {"Tttttttttt"}
        };
    }
}
