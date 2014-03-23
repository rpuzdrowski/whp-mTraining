package org.motechproject.whp.mtraining.csv.response;

import org.motechproject.mtraining.dto.ContentIdentifierDto;
import org.motechproject.whp.mtraining.csv.validator.CourseImportError;

import java.util.Collections;
import java.util.List;

public class CourseImportResponse {
    private static final String FAILURE_RESPONSE_STATUS = "FAILURE";
    public static final String SUCCESS_RESPONSE_STATUS = "SUCCESS";
    private String status;
    private String message;
    private List<CourseImportError> errors;

    private CourseImportResponse(String status, String message, List<CourseImportError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public static CourseImportResponse failure(List<CourseImportError> validationErrors) {
        String errorMessage = "Could not import the CSV due to errors. Please fix the errors and try importing again.";
        return new CourseImportResponse(FAILURE_RESPONSE_STATUS, errorMessage, validationErrors);
    }

    public static CourseImportResponse success(ContentIdentifierDto courseIdentifier) {
        String message = String.format("Course: %s with version %s has been imported successfully", courseIdentifier.getContentId(), courseIdentifier.getVersion());
        return new CourseImportResponse(SUCCESS_RESPONSE_STATUS, message, Collections.EMPTY_LIST);
    }

    public boolean isSuccess() {
        return SUCCESS_RESPONSE_STATUS.equals(status);
    }

    public boolean isFailure() {
        return FAILURE_RESPONSE_STATUS.equals(status);
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<CourseImportError> getErrors() {
        return errors;
    }
}