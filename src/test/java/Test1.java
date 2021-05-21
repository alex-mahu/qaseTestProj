import static io.qase.api.utils.IntegrationUtils.ENABLE_KEY;
import static org.testng.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qase.api.annotation.CaseId;
import io.qase.testng.QaseListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(QaseListener.class)
public class Test1 {

    static {
        System.setProperty("qase.enable", "true");
        System.setProperty("qase.api.token", "9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
        System.setProperty("qase.project.code", "TP1");
        System.setProperty("qase.run.id", "2");
    }

    @Test
    @CaseId(2)
    public void test1() {
        assertTrue(true);
    }

    @Test
    @CaseId(3)
    public void test2() {
        //Allure.getLifecycle().addAttachment("Test1", "img/png", "png", screenshotAs);
        getAttachment();
        assertTrue(false);
    }

    @Attachment("1")
    public byte[] getAttachment() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver d = new ChromeDriver();
        d.get("https://google.com");
        final byte[] screenshotAs = d.getScreenshotAs(OutputType.BYTES);
        d.quit();
        return screenshotAs;
    }

    @Test
    @CaseId(4)
    public void test3() {
        assertTrue(true);
    }
}
