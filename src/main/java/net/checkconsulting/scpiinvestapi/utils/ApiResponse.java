package net.checkconsulting.scpiinvestapi.utils;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private List<ErrorDetail> errors;

    public ApiResponse(String message, T data) {
        this.status = "success";
        this.message = message;
        this.data = data;
        this.errors = null;
    }

    public ApiResponse(String message, List<ErrorDetail> errors) {
        this.status = "error";
        this.message = message;
        this.data = null;
        this.errors = errors;
    }




    @Data
    public static class ErrorDetail {
        private String field;
        private String message;

        public ErrorDetail(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
