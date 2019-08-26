package edu.xalead.taobao.common.exception.CommonExceptionAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.event.ItemEvent;
@ControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler
    public ResponseEntity<String> commonExceptionHandler(TaobaoItemException e){
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
