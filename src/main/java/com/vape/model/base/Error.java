package com.vape.model.base;

public enum Error {
    OK(0, "Thành công"),
    NOT_OK(-1, "Không thành công"),
    EXCEPTION(500, "Lỗi hệ thống"),
    INVALID_AUTHENTICATION(103, "Thông tin xác thực không đúng"),
    INVALID_PARAM(104, "Dữ liệu tham số truyền lên không đúng"),
    EMPTY(110, "Không có dữ liệu"),
    EXISTED(111, "Đã tồn tại dữ liệu");

    private final int errorCode;
    private String message;

    Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}