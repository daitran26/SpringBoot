package com.spring.baitap10.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.baitap10.common.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException {

    @JsonProperty("responseCode")
    private final Response response;

    @JsonProperty("responseMessage")
    private final String message;

    public CommonException(Response response, String message) {
        super(message);
        this.response = response;
        this.message = message == null ? response.getResponseMessage() : message;
    }

    public CommonException(Response response) {
        super(response.getResponseMessage());
        this.response = response;
        this.message = response.getResponseMessage();
    }


    @Override
    public String toString() {
        return "{"
                + "\"responseCode\":" + "\"" + this.getResponse().getResponseCode() + "\"" + ","
                + "\"responseMessage\":" + "\"" + (this.getMessage() == null ? this.getResponse()
                .getResponseMessage() : this.getMessage()) + "\""
                + "}";
    }
}
