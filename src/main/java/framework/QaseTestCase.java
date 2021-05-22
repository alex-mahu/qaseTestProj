package framework;

import static framework.models.NewTestCaseResultRequest.fromTestCaseResultRequest;
import static io.qase.api.enums.StepStatus.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import framework.models.NewTestCaseResultRequest;
import framework.models.StepRequest;
import io.qase.api.QaseApi;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.enums.StepStatus;
import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.testrunresults.NewTestRunResults;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public abstract class QaseTestCase {
    private final WebDriver driver;
    private final String dataProviderContext;
    private final long start;
    protected StepStatus stepStatus = passed;
    protected QaseApi client;
    private Long failedStep;
    private final NewTestRunResults.NewTestRunResultsBuilder testCaseBuilder;
    private final List<StepRequest> steps;
    private Exception exception;
    private String stacktrace;

    public QaseTestCase(WebDriver driver) {
        this(driver, null);
    }

    public QaseTestCase(WebDriver driver, String dataProviderContext) {
        this.driver = driver;
        this.dataProviderContext = dataProviderContext;
        client = new QaseApi("9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
        testCaseBuilder = NewTestRunResults.builder()
                                           .caseId(getQaseCaseId());
        steps = new ArrayList<>();
        start = System.currentTimeMillis();
        performDataCreation();
    }

    protected void performDataCreation() {
    }

    protected abstract Long getQaseCaseId();

    protected void doStepActions(long stepPosition, StepContent stepContent) {
        StepRequest currentStep;
        if (stepStatus == failed || stepStatus == blocked) {
            stepStatus = blocked;
            currentStep = new StepRequest(stepPosition, blocked);
        } else {
            try {
                stepContent.performStep();
                currentStep = new StepRequest(stepPosition, passed);
            } catch (Exception e) {
                failedStep = stepPosition;
                stepStatus = failed;
                currentStep = new StepRequest(stepPosition, failed);
                stacktrace = ExceptionUtils.getStackTrace(e);
                testCaseBuilder.defect(true)
                               .stacktrace(stacktrace);
                addAttachmentToStep(currentStep);
                exception = e;
            }
        }
        steps.add(currentStep);
    }

    public void finishCase() {

        final NewTestRunResults testCase = testCaseBuilder.comment(getCommentContent())
                                                          .time((System.currentTimeMillis() - start) / 1000)
                                                          .status((stepStatus == failed) || (stepStatus == blocked) ? RunResultStatus.failed : RunResultStatus.passed)
                                                          .stacktrace(stacktrace)
                                                          .build();

        final NewTestCaseResultRequest newTestCaseResultRequest = fromTestCaseResultRequest(testCase, steps);

        new TestCaseResultManager().createTestCaseResult(newTestCaseResultRequest);
        if (nonNull(exception)) {
            throw new RuntimeException(exception);
        }
    }

    private String getCommentContent() {
        String comment;
        if (nonNull(failedStep)) {
            comment = format("Test case %d failed at step %d", getQaseCaseId(), failedStep);
        } else {
            comment = "Test execution passed";
        }
        if (nonNull(dataProviderContext)) {
            comment += "\nRan with values:\n" + dataProviderContext;
        }
        return comment;
    }

    private void addAttachmentToStep(StepRequest step) {
        final File failure = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        ZonedDateTime time = LocalDateTime.now().atZone(ZoneId.systemDefault());
        failure.renameTo(new File(time.toString().replace(":", "-") + ".png"));
        final Attachment attachment = client.attachments().add("TP1", failure).get(0);
        step.setAttachments(Collections.singletonList(attachment.getHash()));
    }
}
