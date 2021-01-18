package com.jiamu.jiamu001.controller;

import com.jiamu.jiamu001.domain.ResponseBean;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@RestControllerAdvice
public class ExceptionController {
    Logger logger = LoggerFactory.getLogger("exception");

    //mybatis等runtime
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handle500(RuntimeException e){
        logger.error("RuntimeException"+e.getMessage()+e.getLocalizedMessage());
        return new ResponseBean(500, e.getMessage(), null);
    }

    //mybatis等runtime
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handle505(RuntimeException e){
        logger.error("RuntimeException"+e.getMessage());
        return new ResponseBean(500, e.getMessage(), null);
    }


    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        logger.error("ShiroException"+e.getMessage());
        return new ResponseBean(401, null, null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401(UnauthorizedException e) {
        logger.warn("UnauthorizedException"+e.getMessage());
        return new ResponseBean(401, "Unauthorized", null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        logger.error(ex.getMessage());
        return new ResponseBean(getStatus(request).value(), ex.getMessage(), null);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized(UnsupportedEncodingException e) {
        logger.error(e.getMessage());
        return new ResponseBean(401, "Unauthorized", e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean fileNotFind(FileNotFoundException e){
        logger.error(e.getMessage());
        return new ResponseBean(500, "fileNotFind", e.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}