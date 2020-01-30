package com.changgou.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.changgou.item.feign.PageFeign;
import com.xpand.starter.canal.annotation.*;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * 监听类 监听数据的变化做处理
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.listener *
 * @since 1.0
 */
// 事件监听的注解 监听数据库的变化
@CanalEventListener
public class MyEventListener {

    //当数据被添加的时候触发
    // CanalEntry.EventType eventType  监听到的操作的类型  INSERT  UPDATE ,DELETE ,CREATE INDEX ,GRAND
    // CanalEntry.RowData rowData 被修改的数据()
 /*   @InsertListenPoint
    public void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...

        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }*/
    //当数据被更新的时候触发
    /*@UpdateListenPoint
    public void onEvent1(CanalEntry.RowData rowData) {
        //do something...
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }*/
    // 当数据被删除的时候触发
   /* @DeleteListenPoint
    public void onEvent3(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }*/

    //自定义事件的触发
    // destination = "example" 指定某一个目的地 一定要和配置文件中的目录保持一致
    //schema = "canal-test" 要监听的数据库实例
    //table = {"t_user", "test_table"},   要监听的表
    // eventType = CanalEntry.EventType.UPDATE  要监听的类型
    /*@ListenPoint(destination = "example", schema = "changgou_content", table = {"tb_content"}, eventType = {CanalEntry.EventType.UPDATE,CanalEntry.EventType.INSERT,CanalEntry.EventType.DELETE})
    public void onEvent4(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...

        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }*/


    @Autowired
    private ContentFeign contentFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param eventType
     * @param rowData
     */
    @ListenPoint(destination = "example", schema = "changgou_content", table = {"tb_content"}, eventType = {CanalEntry.EventType.UPDATE, CanalEntry.EventType.INSERT, CanalEntry.EventType.DELETE})
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1.获取到被修改的category_id
        String categoryId = getColumnValue(eventType, rowData);

        //2.调用feign 获取数据
        Result<List<Content>> byCategory = contentFeign.findByCategory(Long.valueOf(categoryId));
        //3.存储到redis中
        List<Content> data = byCategory.getData();//List
        //4.
        stringRedisTemplate.boundValueOps("content_" + categoryId).set(JSON.toJSONString(data));
    }

    private String getColumnValue(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1.判断更改类型 如果是删除 则需要获取到before的数据
        String categoryId = "";
        if (CanalEntry.EventType.DELETE == eventType) {
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                //column.getName(列的名称   column.getValue() 列对应的值
                if (column.getName().equals("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        } else {
            //2判断是 更新 新增 获取after的数据
            List<CanalEntry.Column> beforeColumnsList = rowData.getAfterColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                //column.getName(列的名称   column.getValue() 列对应的值
                if (column.getName().equals("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }
        //3.返回
        return categoryId;
    }

    //监听商品数据库的spu的表的数据的变化,变了,调用feign 生成静态页就可以了

    @Autowired
    private PageFeign pageFeign;

    @ListenPoint(destination = "example",
            schema = "changgou_goods",
            table = {"tb_spu"},
            eventType = {CanalEntry.EventType.UPDATE, CanalEntry.EventType.INSERT, CanalEntry.EventType.DELETE})
    public void onEventCustomSpu(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {

        //判断操作类型
        if (eventType == CanalEntry.EventType.DELETE) {
            String spuId = "";
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                if (column.getName().equals("id")) {
                    spuId = column.getValue();//spuid
                    break;
                }
            }
            //todo 删除静态页

        } else {
            //新增 或者 更新
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            String spuId = "";
            for (CanalEntry.Column column : afterColumnsList) {
                if (column.getName().equals("id")) {
                    spuId = column.getValue();
                    break;
                }
            }
            //更新 生成静态页
            pageFeign.createHtml(Long.valueOf(spuId));
        }
    }
}
