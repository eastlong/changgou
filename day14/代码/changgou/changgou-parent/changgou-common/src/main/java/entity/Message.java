package entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*****
 * @Author: www.itheima.com
 * @Description: entity:MQ消息封装
 ****/
public class Message implements Serializable{

    //执行的操作  1：增加，2：修改,3：删除
    private int code;

    //数据
    private Object content;

    //发送的routkey
    @JSONField(serialize = false)
    private String routekey;

    //交换机
    @JSONField(serialize = false)
    private String exechange;

    public Message() {
    }

    public Message(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public Message(int code, Object content, String routekey, String exechange) {
        this.code = code;
        this.content = content;
        this.routekey = routekey;
        this.exechange = exechange;
    }

    public String getRoutekey() {
        return routekey;
    }

    public void setRoutekey(String routekey) {
        this.routekey = routekey;
    }

    public String getExechange() {
        return exechange;
    }

    public void setExechange(String exechange) {
        this.exechange = exechange;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
