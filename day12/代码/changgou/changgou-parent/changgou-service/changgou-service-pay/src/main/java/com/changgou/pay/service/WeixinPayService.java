package com.changgou.pay.service;

import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.pay.service *
 * @since 1.0
 */
public interface WeixinPayService {
    /**
     * 生成二维码
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    Map<String,String> createNative(String out_trade_no, String total_fee);

    /**
     *
     * @param out_trade_no
     * @return
     */
    Map<String,String> queryStatus(String out_trade_no);
}
