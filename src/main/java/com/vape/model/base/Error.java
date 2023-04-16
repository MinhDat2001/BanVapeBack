package com.vape.model.base;

public enum Error {
    OK(0, "Thành công"),
    NOT_OK(-1, "Không thành công"),
    EXCEPTION(500, "Lỗi hệ thống"),
    INVALID_AUTHENTICATION(103, "Thông tin xác thực không đúng"),
    INVALID_PARAM(104, "Dữ liệu tham số truyền lên không đúng"),
    EMPTY(110, "Không có dữ liệu"),
    EXISTED(111, "Đã tồn tại dữ liệu"),
    NOT_EXISTED(112, "Không tồn tại dữ liệu"),
    NOT_AUTHORIZED(113, "Không có quyền thực hiện"),

    IS_PURCHASED(0, "Đơn hàng này đã được thanh toán"),
    PURCHASE(0, "Thanh toán thành công"),
    INVALID_TOTAL_PRICE_OR_QUANTITY(1, "Số tiền thanh toán hoặc số lượng sản phẩm không hợp lệ");

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