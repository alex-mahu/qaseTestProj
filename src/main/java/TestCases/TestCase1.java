package TestCases;

import framework.QaseTestCase;
import org.openqa.selenium.WebDriver;

public class TestCase1 extends QaseTestCase {

    public TestCase1(WebDriver driver) {
        super(driver);
    }

    @Override
    protected Long getQaseCaseId() {
        return 2L;
    }

    public TestCase1 step1() {
        performStep(1, () -> {
            System.out.println("Step 1");
        });
        return this;
    }

    public TestCase1 step2() {
        performStep(2, () -> {
            System.out.println("Step 2");
            throw new RuntimeException("this is an exception to check the results");
        });
        return this;
    }

    public TestCase1 step3() {
        performStep(3, () -> {
            System.out.println("Step 3");
        });
        return this;
    }

}
