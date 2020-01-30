package com.itheima.account.controller;

import com.itheima.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.controller *
 * @since 1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    /**
     * 给指定的用户的ID 扣减余额
     * @param userId 用户的ID
     * @param money 扣减的钱
     * @return
     */
    @RequestMapping("/reduce")
    public Boolean debit(String userId,Integer money){
        accountService.debit(userId,money);
        return true;
    }
}
