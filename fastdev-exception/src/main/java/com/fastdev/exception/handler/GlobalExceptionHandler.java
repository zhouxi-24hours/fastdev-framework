package com.fastdev.exception.handler;

import com.google.common.base.Throwables;
import com.fastdev.common.domain.Result;
import com.fastdev.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 全局异常处理
 * @author zhouxi
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义抛出异常处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result jsonErrorHandler(BaseException e) {
        logger.error("errorMessage: {}", Throwables.getStackTraceAsString(e));
        return new Result(e.getStatus(), null, e.getMessage());
    }

    /**
     * 其他异常处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandler(Exception e) {
        // 需要打印错误栈，方便排查问题
        logger.error("Exception:{}", Throwables.getStackTraceAsString(e));
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "系统异常，请联系管理员");
    }

    /**
     * 客户端网络异常
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<Result> httpClientErrorHandler(HttpClientErrorException e) {
        logger.error("HttpClientErrorException:{}", Throwables.getStackTraceAsString(e));
        Result result = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "HttpClientErrorException");
        HttpStatus statusCode = e.getStatusCode();
        if (HttpStatus.UNAUTHORIZED == statusCode) {
            return ResponseEntity.status(statusCode).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 服务端网络异常
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpServerErrorException.class)
    @ResponseBody
    public Result httpServerErrorHandler(HttpServerErrorException e) {
        logger.error("HttpServerErrorException:{}", Throwables.getStackTraceAsString(e));
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "HttpServerErrorException:" + e.getMessage());
    }

    /**
     * 参数校验
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        /**校验不通过*/
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            logger.error("BindException：{}", message);
            return new Result(com.fastdev.common.util.HttpStatus.VALID_FAIL.getValue(), null, message);
        }
        return Result.success();
    }

    /**
     * 参数校验
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        /**校验不通过*/
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            logger.error("MethodArgumentNotValidException：{}", message);
            return new Result(com.fastdev.common.util.HttpStatus.VALID_FAIL.getValue(), null, message);
        }
        return Result.success();
    }
}
