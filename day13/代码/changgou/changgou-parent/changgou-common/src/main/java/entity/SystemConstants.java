package entity;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package entity *
 * @since 1.0
 */
public class SystemConstants {
    /**
     * 秒杀商品存储到前缀的KEY
     */
    public static final String SEC_KILL_GOODS_PREFIX="SeckillGoods_";


    /**
     * 存储域订单的hash的大key
     */
    public static final String SEC_KILL_ORDER_KEY="SeckillOrder";

    /**
     * 用户排队的队列的KEY
     */
    public static final String SEC_KILL_USER_QUEUE_KEY="SeckillOrderQueue";


    /**
     * 用户排队标识的key (用于存储 谁 买了什么商品 以及抢单的状态)
     */

    public static final String SEC_KILL_USER_STATUS_KEY = "UserQueueStatus";
}
