package com.changgou.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.order.config *
 * @since 1.0
 */
@Component
public class MyFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            //1.获取请求对象
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                //2.获取请求对象中的所有的头信息(网关传递过来的)
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();//头的名称
                    String value = request.getHeader(name);//头名称对应的值
                    System.out.println("name:" + name + "::::::::value:" + value);
                    //3.将头信息传递给fegin (restTemplate)
                    requestTemplate.header(name,value);
                }
            }
        }


    }
}
