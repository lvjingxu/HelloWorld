
package com.znv.manage.common.handler;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;


/**
 * 
  * @ClassName: ControllerExceptionHandler
  * @Description: 控制层全局异常处理
  * @author 时智超
  * @date 2018/5/16 16:29
  *
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
   
   private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    
   @ExceptionHandler(value = { ConstraintViolationException.class })
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Result constraintViolationException(ConstraintViolationException ex) {
       logger.info(ex.getMessage());
       return new Result(HttpStatus.BAD_REQUEST.value(),"参数错误");
   }
   
   @ExceptionHandler(value = { IllegalArgumentException.class })
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Result IllegalArgumentException(IllegalArgumentException ex) {
       logger.info(ex.getMessage());
       return new Result(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
   }

   @ExceptionHandler(value = { NoHandlerFoundException.class})
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public Result noHandlerFoundException(Exception ex) {
       logger.info(ex.getMessage());
       return new Result(HttpStatus.NOT_FOUND.value(), ex.getMessage());
   }
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        //logger.error("缺少请求参数", e);
        return new Result(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

   
   @ExceptionHandler(value = { Exception.class })
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public Result unknownException(Exception ex) {
       logger.error("",ex);
       if(ex instanceof BusinessException){
           BusinessException businessException=(BusinessException) ex;
           if(businessException.getCode()!=null){
               return new Result(businessException.getCode(), ex.getMessage());
           }else{
               return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
           }
       }
       if(ex instanceof MethodArgumentTypeMismatchException){
           return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(),"参数类型错误:"+ex.getMessage());
       }else if(ex instanceof BusinessException){

           return new Result(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
       }

       return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
   }
}