package framework.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.models.v1.testrunresults.NewTestRunResults;

public class NewTestCaseResultRequest {
    @SerializedName("case_id")
    private Long caseId;
    private RunResultStatus status;
    private Long time;
    @SerializedName("member_id")
    private Integer memberId;
    private String comment;
    private String stacktrace;
    private Boolean defect;
    private List<StepRequest> steps;
    private List<String> attachments;

    public static NewTestCaseResultRequest fromTestCaseResultRequest(NewTestRunResults newTestRunResults, List<StepRequest> steps) {
        NewTestCaseResultRequest ntcrr = new NewTestCaseResultRequest();
        ntcrr.setAttachments(newTestRunResults.getAttachments());
        ntcrr.setCaseId(newTestRunResults.getCaseId());
        ntcrr.setComment(newTestRunResults.getComment());
        ntcrr.setDefect(newTestRunResults.getDefect());
        ntcrr.setStacktrace(newTestRunResults.getStacktrace());
        ntcrr.setMemberId(newTestRunResults.getMemberId());
        ntcrr.setStatus(newTestRunResults.getStatus());
        ntcrr.setTime(newTestRunResults.getTime());
        ntcrr.setSteps(steps);
        return ntcrr;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public RunResultStatus getStatus() {
        return status;
    }

    public void setStatus(RunResultStatus status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public Boolean getDefect() {
        return defect;
    }

    public void setDefect(Boolean defect) {
        this.defect = defect;
    }

    public List<StepRequest> getSteps() {
        return steps;
    }

    public void setSteps(List<StepRequest> steps) {
        this.steps = steps;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
