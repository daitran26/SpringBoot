package com.spring.baitap10.common;

import lombok.Getter;

@Getter
public enum Response {
    SUCCESS("0000","Thành công"),
    ERROR_AUTH_SYSTEM("5999","Lỗi xác thực [ERR: AUTH_ERROR]"),
    OBJECT_IS_EXISTS("5582", "Đối tượng đã tồn tại"),
    NOT_FOUND("5501", "Không tìm thấy dữ liệu"),
    NOT_EXITS_REQUIRED("5902", "Yêu cầu không tồn tại"),
    DATA_NOT_FOUND("5505", "Không tìm thấy dữ liệu cần tra cứu"),
    MISSING_PARAM("0996", "Thiếu dữ liệu đầu vào bắt buộc"),
    OBJECT_NOT_FOUND("5583", "Đối tượng không tồn tại"),
    PARAM_NOT_VALID("0006", "Dữ liệu đầu vào không hợp lệ"),
    SYSTEM_ERROR("9999","Lỗi hệ thống: MDSHOP_ERR");

    private final String responseCode;
    private final String responseMessage;

    Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.responseCode).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.responseMessage).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
