package com.spring.baitap10.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody {
    private String responseCode;
    private String responseMessage;
    private Object responseData;

    public ResponseBody(Response response, Object data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
        this.responseData = data;
    }

//    public ResponseBody(CommonException e, Object data) {
//        this.responseCode = e.getResponse().getResponseCode();
//        this.responseMessage = e.getResponse().getResponseMessage();
//        this.responseData = data;
//    }

    public ResponseBody(Response response) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
    }

    public ResponseBody(Response response, String responseMessage, Object data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = responseMessage;
        this.responseData = data;
    }

    public ResponseBody(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ResponseBody(String responseCode, String responseMessage, Object data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseData = data;
    }

}
