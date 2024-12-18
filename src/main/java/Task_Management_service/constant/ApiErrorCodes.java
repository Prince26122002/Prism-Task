package Task_Management_service.constant;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public enum ApiErrorCodes implements Error{
    SUCCESS(40001, "Success"),
    NOT_FOUND(40002, "not found"),
    USER_ALREADY_EXIST(40003, "User already exist"),
    INVALID_INPUT(40004, "Invalid request input"),
    INVALID_MOBILE_OR_PASSWORD(40005,"Invalid Mobile no. or Password"),
    USER_NOT_FOUND(40006, "User not found"),
    TEAM_MEMBER_NOT_FOUND(40007,"Team Member not found"),
    TEAM_ALREADY_EXIST(40008, "Team already exist"),
    TEAM_NOT_FOUND(40009,"Team not found"),
    COMMENT_NOT_FOUND(40009,"Comment not found"),
    ACTIVITY_LOGS_NOT_FOUND(40010,"ActivityLogs not found"),
    TASK_NOT_FOUND(40011,"Task not found"),
    OWNER_NOT_FOUND(40012,"Owner not found"),
    PROJECT_NOT_FOUND(40013,"Project not found"),
    STEP_NOT_FOUND(40014,"Step not found"),
    ASSIGNEE_NOT_FOUND(40014,"Assignee not found"),
    REPORTER_NOT_FOUND(40015,"Reporter not found"),
    WORKFLOW_NOT_FOUND(40016,"Workflow not found with this id"),
    MOBILE_ALREADY_EXIST(40017,"Mobile no. already exist"),
    INVALID_ENTITY_ID(40018,"Invalid Entity Id");

    private int errorCode;
    private String errorMessage;
    private HttpStatus status;
    private  String message;
    private LocalDateTime timestamp;


    ApiErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}

