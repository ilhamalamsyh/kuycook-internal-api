package com.kuycook.kuycookinternalapi.dto.response;

public class MessageResponse {
    private Integer code;
    private Boolean status;
    private String message;

    public MessageResponse(Integer code, Boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
