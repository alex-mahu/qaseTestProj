package TestCases;

import framework.QaseTestCase;
import org.openqa.selenium.WebDriver;

public class TestCase1 extends QaseTestCase {

    public TestCase1(WebDriver driver) {
        super(driver);
    }

    public TestCase1(WebDriver driver, String dataProviderContext) {
        super(driver, dataProviderContext);
    }

    @Override
    protected Long getQaseCaseId() {
        return 2L;
    }

    public TestCase1 step1() {
        doStepActions(1, () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                System.out.println("22");
            }
            System.out.println("Step 1");
        });
        return this;
    }

    public TestCase1 step2(String s) {
        doStepActions(2, () -> {
            System.out.println("Step 2 " + s);
            throw new RuntimeException("this is an exception to check the results");
        });
        return this;
    }

    public TestCase1 step3() {
        doStepActions(3, () -> {
            System.out.println("Step 3");
        });
        return this;
    }

}
