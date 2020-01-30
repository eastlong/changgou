package com.itheima.storage.controller;


import com.itheima.storage.service.StorageService;
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
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;


    /**
     *
     * @param commodityCode  商品编码
     * @param count 采购的数量
     * @return
     */
    @RequestMapping("/deduct")
    public Boolean deduct(String commodityCode,Integer count){
        storageService.deduct(commodityCode, count);
        return true;
    }
}
