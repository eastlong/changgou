package com.itheima.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.account.service *
 * @since 1.0
 */
@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void debit(String userId, Integer money) {
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
    }
}
