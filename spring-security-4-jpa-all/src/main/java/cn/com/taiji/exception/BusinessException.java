package cn.com.taiji.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BusinessException extends RuntimeException {

    private int type;
    private Map<String, Object> attributes = new HashMap<String, Object>();


    public BusinessException() {
        super();
    }

    public BusinessException(String message, int type) {
        super(message);
        this.type = type;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}