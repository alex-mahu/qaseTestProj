package framework.models;

import java.util.List;

import io.qase.api.enums.StepStatus;

public class StepRequest {
    private long position;
    private StepStatus status;
    private String comment;
    private List<String> attachments;

    public StepRequest(long position, StepStatus status) {
        this.position = position;
        this.status = status;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
