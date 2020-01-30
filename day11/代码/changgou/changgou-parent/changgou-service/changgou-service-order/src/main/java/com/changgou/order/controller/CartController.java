package com.changgou.order.controller;

import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.order.controller *
 * @since 1.0
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;



    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 添加购物车
     *
     * @param id  要购买的商品的SKU的ID
     * @param num 要购买的数量
     * @return
     */
    @RequestMapping("/add")
    public Result add(Long id, Integer num) {
        //springsecurity 获取当前的用户名 传递service

//        String username = "szitheima";

        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        System.out.println("哇塞::用户名:"+username);

        cartService.add(id, num, username);
        return new Result(true, StatusCode.OK, "添加成功");

    }

    @RequestMapping("/list")
    public Result<List<OrderItem>> list() {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        System.out.println("哇塞::用户名:"+username);
        List<OrderItem> orderItemList = cartService.list(username);
        return new Result<List<OrderItem>>(true, StatusCode.OK, "列表查询成功", orderItemList);


    }


}
