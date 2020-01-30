package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.user.feign *
 * @since 1.0
 */
@FeignClient(name="user")
@RequestMapping("/user")
public interface UserFeign {
    @GetMapping("/load/{id}")
    public Result<User> findByUsername(@PathVariable(name="id") String id);
}
