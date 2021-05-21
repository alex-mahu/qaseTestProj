package framework;

import static io.qase.api.enums.StepStatus.*;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
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
import io.qase.api.models.v1.testrunresults.Step;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public abstract class QaseTestCase {
    private final WebDriver driver;
    private final long start;
    protected StepStatus stepStatus = passed;
    protected QaseApi client;
    private NewTestRunResults.NewTestRunResultsBuilder testCaseBuilder;
    private List<StepRequest> steps;
    private Exception exception;
    private String stacktrace;

    public QaseTestCase(WebDriver driver) {
        this.driver = driver;
        client = new QaseApi("9cc2fafc22ded2940b975d4d6f4b0f99cf603ff2");
        testCaseBuilder = NewTestRunResults.builder()
                                           .caseId(getQaseCaseId());
        steps = new ArrayList<>();
        start = System.currentTimeMillis();
    }

    protected abstract Long getQaseCaseId();

    protected void performStep(long stepPosition, StepContent stepContent) {
        StepRequest currentStep;
        if (stepStatus == failed || stepStatus == blocked) {
            stepStatus = blocked;
            currentStep = new StepRequest(stepPosition, blocked);
        } else {
            try {
                stepContent.performStep();
                currentStep = new StepRequest(stepPosition, passed);
            } catch (Exception e) {
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
        final NewTestRunResults testCase = testCaseBuilder.comment(format("Test case %d failed, check steps for details", getQaseCaseId()))
                                                          .time(System.currentTimeMillis() - start)
                                                          .status((stepStatus == failed) || (stepStatus == blocked) ? RunResultStatus.failed : RunResultStatus.passed)
                                                          .stacktrace(stacktrace)
                                                          .build();
        final NewTestCaseResultRequest newTestCaseResultRequest = NewTestCaseResultRequest.fromTestCaseResultRequest(testCase, steps);

        new TestCaseResultManager().createTestCaseResult(NewTestRunResults.class, newTestCaseResultRequest);

//        client.testRunResults().create(
//                "TP1",
//                3,
//                getQaseCaseId(),
//                testCase.getStatus(),
//                Duration.ofMillis(testCase.getTime()),
//                null,
//                testCase.getComment(),
//                testCase.getStacktrace(),
//                testCase.getDefect(),
//                testCase.getSteps().toArray(new Step[]{})
//        );
    }

    private void addAttachmentToStep(StepRequest step) {
        final File failure = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        ZonedDateTime time = LocalDateTime.now().atZone(ZoneId.systemDefault());
        failure.renameTo(new File(time.toString().replace(":", "-") + ".png"));
        final Attachment attachment = client.attachments().add("TP1", failure).get(0);

//        final io.qase.api.models.v1.testrunresults.Attachment stepAttachment = new io.qase.api.models.v1.testrunresults.Attachment();
//        stepAttachment.setFilename(failure.getName());
//        Path path = failure.toPath();
//        String mimeType = null;
//        try {
//            mimeType = Files.probeContentType(path);
//        } catch (IOException ignore) {
//        }
//        stepAttachment.setMime(mimeType);
//        stepAttachment.setSize(failure.length());
//        stepAttachment.setUrl(attachment.getFullPath());

        step.setAttachments(Collections.singletonList(attachment.getHash()));
    }
}
