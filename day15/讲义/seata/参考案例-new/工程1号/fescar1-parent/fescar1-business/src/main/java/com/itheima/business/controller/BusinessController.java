package com.itheima.business.controller;

import com.itheima.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.business.controller *
 * @since 1.0
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 提交事务
     * @return
     */
    @RequestMapping(value = "/purchase/commit")
    public String purchaseCommit(){
        try {
            businessService.purchase("U100000", "C100000", 30);
        } catch (Exception exx) {
            return exx.getMessage();
        }
        return "全局事务提交";
    }
}
