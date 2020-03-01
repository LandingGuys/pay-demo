package com.henu.pay.exception;

import com.henu.pay.enums.ResponseEnum;
import com.henu.pay.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lv
 * @date 2020-02-20 19:46
 */
@Slf4j
@ControllerAdvice
public class PayExceptionHandler {
    @ExceptionHandler(PayException.class)
    @ResponseBody
    public ResponseVo payRepeatHandle() {
        return ResponseVo.error(ResponseEnum.PAY_ORDER_NO_REPEAT);
    }
}
