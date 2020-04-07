package cc.vant.seckillmall.controller;

import cc.vant.seckillmall.util.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({InvalidParameterException.class})
    public Response<?> invalidParam(InvalidParameterException e) {
        return Response.fail("参数异常:" + e.getMessage());
    }
}