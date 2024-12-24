package org.aileen.mod.kit.dto;

import java.io.Serializable;

/**
 * Json响应
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
public class WebResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;

    public WebResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public WebResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebResult(Integer code) {
        this.code = code;
    }

    public WebResult() {

    }

    public static <T> WebResult<T> success(T data) {
        return new WebResult<T>(200, "success", data);
    }

    public static <T> WebResult<T> success() {
        return new WebResult<T>(200, "success", null);
    }

    public static <T> WebResult<T> success(String message) {
        return new WebResult<T>(200, message);
    }

    public static <T> WebResult<T> success(String message, T data) {
        return new WebResult<T>(200, message, data);
    }

    public static <T> WebResult<T> error(String message) {
        return new WebResult<T>(500, message);
    }

    public static <T> WebResult<T> error() {
        return new WebResult<T>(500, "error");
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
