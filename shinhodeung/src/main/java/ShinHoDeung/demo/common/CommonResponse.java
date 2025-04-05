package ShinHoDeung.demo.common;

import org.springframework.http.ResponseEntity;

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

    public ResponseEntity toResponseEntity(){
        return ResponseEntity
        .ok()
        .header("Content-Type", "application/json;charset=UTF-8")
        .body(this);
    }
}