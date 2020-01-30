package com.changgou.goods.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou.goods.controller *
 * @since 1.0
 */
@ControllerAdvice// 交给spring容器管理 标识是一个异常处理器
public class BaseExceptionHandler {

    //方法当被@requestMapingg注解修饰的方法 有 异常发生的时候 被调用

    // Exception.class 当  发生了 Exeption 异常以及它的子类的异常都被捕获到处理.

    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public Result handlerException(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }

  /*  @ExceptionHandler(value= aaa.class)
    public Result handlerException(){

    }

    @ExceptionHandler(value= bbb.class)
    public Result handlerException(){

    }*/


}
