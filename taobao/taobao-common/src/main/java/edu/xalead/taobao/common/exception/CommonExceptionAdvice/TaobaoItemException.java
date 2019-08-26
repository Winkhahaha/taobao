package edu.xalead.taobao.common.exception.CommonExceptionAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaobaoItemException extends RuntimeException{
    private int code;
    public TaobaoItemException(int code, String mesg) {
        super(mesg);
        this.code = code;
    }

    public TaobaoItemException(TaobaoItemExceptionEnum e) {
        super(e.getMesg());
        this.code = e.getCode();

    }
}
