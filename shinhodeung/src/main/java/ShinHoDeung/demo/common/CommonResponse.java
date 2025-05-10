package ShinHoDeung.demo.common;

import lombok.Data;

@Data
public class CommonResponse {
    private String statusCode;
    private Object data;
    private String message;

    public CommonResponse(String statusCode, Object data, String message){
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }
}