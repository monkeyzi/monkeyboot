package com.monkeyzi.mboot.common.core.exception;

import com.monkeyzi.mboot.common.core.holder.SpringContextHolder;
import com.monkeyzi.mboot.common.core.log.MbootErrorLogDto;
import com.monkeyzi.mboot.common.core.log.MbootErrorLogEvent;
import com.monkeyzi.mboot.common.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class MbootGlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String serviceName;
    /**
     * 请求方法不支持的异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R MethodNotSupportException(HttpRequestMethodNotSupportedException e){
        log.error("405错误-{}",e);
        return R.error(405,"method not support");
    }


    /**
     * 404
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public R NoHandlerFoundException(NoHandlerFoundException e ) {
        log.info("404={}",e);
        return R.error(404,"接口资源不存在");
    }

    /**
     * 415
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn("415不支持的媒体类型={}",e);
        return R.error(415, "不支持媒体类型");
    }
    /**
     * 参数校验异常处理  @RequestBody 注解
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public R bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String msg=fieldErrors.get(0).getDefaultMessage();
        log.warn("参数校验处理111={}",msg);
        return R.error(1,msg);
    }
    /**
     * 请求的json格式不正确
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("400坏的请求,json格式不正确={}",e);
        return R.error(400, "坏的请求");
    }
    /**
     * 拦截 @Validated注解校验的参数  @RequestParam @PathVariable 单个字段
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public R handleValidationException(ConstraintViolationException e){
        for(ConstraintViolation<?> s:e.getConstraintViolations()){
            log.warn("参数异常444 e={}",s.getMessage());
            return R.errorMsg(s.getMessage());
        }
        return R.errorMsg("参数不合法");
    }

    /**
     * 参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.warn("请求参数不合法异常 e={}",e.getParameter());
        return R.error(400, "坏的请求");
    }

    /**
     * 参数绑定异常
     * @param exception   实体
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public R BindException(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String msg=fieldErrors.get(0).getDefaultMessage();
        log.warn("参数校验处理222={}",msg);
        return R.error(1,msg);
    }

    /**
     * 400 坏的请求
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("400坏的请求={}",e);
        return R.error(400, "坏的请求");
    }
    /**
     * AccessDeniedException
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handleAccessDeniedException(AccessDeniedException e) {
        log.warn("拒绝授权异常信息 ex={}", "权限不足，不允许访问", e);
        return R.error(403,"权限不足，不允许访问！");

    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R businessException(BusinessException e) {
        log.error("business exception");
        return R.error(e.getCode(), e.getMessage());
    }
    /**
     * 500异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Exception e, HttpServletRequest request) {
        // 方法名
        String uri=request.getRequestURI();
        // 请求方法
        String method=request.getMethod();
        // 获取异常类型
        String exceptionType=e.getClass().getSimpleName();
        MbootErrorLogDto errorLogDto=MbootErrorLogDto.builder().e(e)
                .request(request)
                .serviceName(serviceName)
                .requestUri(uri)
                .method(method)
                .build();
        SpringContextHolder.publishEvent(new MbootErrorLogEvent(errorLogDto));

        log.error("异常信息uri={},method={},exceptionType={}",uri,method,exceptionType);
        e.printStackTrace();
        return R.error(500,"哎呀，接口开小差了！");
    }


}
