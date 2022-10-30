/*
 */
package it.artisan.exception;

import com.google.common.base.Strings;

import it.artisan.response.CodeDefault;
import it.artisan.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全局异常处理ControllerAdvice
 * @author zengfan
 */
@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 系统自定义全局异常
     * @param request 请求
     * @param e 错误信息
     * @return 请求结果
     */
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, GlobalException e) {
        RestResponse exception;
        if (e.getExtraInfo().isEmpty()) {
            exception = RestResponse.exception(e.getCodeEnum());
        } else {
            exception = RestResponse.exception(e.getCodeEnum(), e.getExtraInfo());
        }
        log.error("GlobalException: {}", exception);
        logTrace(request);
        return exception;
    }
    /**
     * 系统自定义全局异常
     * @param request 请求
     * @param e 错误信息
     * @return 请求结果
     */
    @ExceptionHandler(value = GlobalVerifyException.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, GlobalVerifyException e) {
        RestResponse exception;
        if(Objects.nonNull(e.getCodeEnum())){
            exception = RestResponse.exception(e.getCodeEnum());
        }else{
            exception = RestResponse.exception(e.getMessage());
        }
        log.error("GlobalVerifyException: {}", exception);
        logTrace(request);
        return exception;
    }

    /**
     * controller 参数转化时, 主要从这里捕获错误信息
     * @param request 请求
     * @param e 错误信息
     * @return 请求结果
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, ServletRequestBindingException e) {
        RestResponse exception = RestResponse.exception(CodeDefault.ILLEGAL_ARGUMENT);
        log.error("请求参数错误: {}", exception, e);
        logTrace(request);
        return exception;
    }

    /**
     * NPE处理
     * @param request 请求
     * @param npe 错误信息
     * @return 请求结果
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, NullPointerException npe) {
        RestResponse exception = RestResponse.exception(CodeDefault.NULL_POINT_ERROR);
        if(!Strings.isNullOrEmpty(npe.getMessage())){
            exception.setMessage(npe.getMessage());
        }
        Map<String,Object> extras = new HashMap<>(0);
        StackTraceElement stackTraceElement = npe.getStackTrace()[0];
        extras.put("class", stackTraceElement.getClassName());
        extras.put("method", stackTraceElement.getMethodName());
        extras.put("lineNumber", stackTraceElement.getLineNumber());
        exception.setExtraInfo(extras);
        log.error("RuntimeException: {}", exception, npe);
        logTrace(request);
        return exception;
    }

    /**
     * 这个兜底
     * @param request 请求
     * @param e 错误信息
     * @return 请求结果
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, RuntimeException e) {
        RestResponse exception;
        if(Strings.isNullOrEmpty(e.getMessage())){
            exception = RestResponse.exception(CodeDefault.INTERNAL_SERVER_ERROR);
        }else{
            exception = RestResponse.exception(e.getMessage());
        }
        log.error("RuntimeException: {}", exception, e);
        logTrace(request);
        return exception;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse exception(HttpServletRequest request, Exception e) {
        RestResponse exception = RestResponse.exception(CodeDefault.INTERNAL_SERVER_ERROR);
        log.error("RuntimeException: {}", exception, e);
        logTrace(request);
        return exception;
    }

    /**
     * 打印堆栈不够，还需要请求信息时需要打印处理
     * @param request 请求
     */
    private void logTrace(HttpServletRequest request) {
        if(log.isTraceEnabled()){
            log.trace("Method: {}", request.getMethod());
            log.trace("URI: {}", request.getRequestURI());
            log.trace("User: {}", Optional.ofNullable(request.getUserPrincipal()).map(Objects::toString).orElse(""));
            log.trace("Query: {}", request.getParameterMap().keySet().stream().map(key->String.format("%s=%s",key,request.getParameter(key)))
                    .collect(Collectors.joining(" ## ")));
            log.trace("Remote: {}:{} ## {}", request.getRemoteAddr(), request.getRemotePort(), request.getRemoteHost());
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))){
                log.trace("Body: \n=====\n{}\n=====\n", bufferedReader.lines().collect(Collectors.joining("\n")));
            } catch (IOException readError) {
                log.trace("Body Read Error: {}", readError.getMessage());
            }
        }
    }
}
