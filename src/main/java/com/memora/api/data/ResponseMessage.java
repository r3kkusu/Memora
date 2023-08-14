package com.memora.api.data;

import com.memora.api.util.DateUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseMessage<T> {

    private boolean isSuccess;
    private List<String> errors;
    private String message;
    private long timestamp;
    private T data;

    public ResponseMessage() {
        this(null, true);
    }

    public ResponseMessage(String message, boolean isSuccess) {
        this(message, isSuccess, null);
    }

    public ResponseMessage(String message, boolean isSuccess, T data) {
        this(message, new ArrayList<>(), isSuccess, DateUtils.getCurrentTimeStamp(), data);
    }

    public ResponseMessage(String message, List<String> errors, boolean isSuccess, long timestamp, T data) {
        this.message = message;
        this.errors = errors;
        this.isSuccess = isSuccess;
        this.timestamp = timestamp;
        this.data = data;
    }
}
