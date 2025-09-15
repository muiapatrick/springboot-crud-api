package com.example.main.dto;

import com.example.main.util.APICode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ResponseDTO {
    private String statusCode;
    private String statusDesc;
    private Object message;
    private Object data;

    public ResponseDTO(APICode apiCode, Object message) {
        this.statusCode = apiCode.getCode();
        this.statusDesc = apiCode.getDesc();
        this.message = message;
    }

    public ResponseDTO(APICode apiCode, Object message, Object data) {
        this.statusCode = apiCode.getCode();
        this.statusDesc = apiCode.getDesc();
        this.message = message;
        this.data = data;
    }
}
