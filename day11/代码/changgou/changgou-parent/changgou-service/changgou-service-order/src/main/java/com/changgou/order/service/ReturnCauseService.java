package com.changgou.order.service;

import com.changgou.order.pojo.ReturnCause;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:ReturnCause业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface ReturnCauseService {

    /***
     * ReturnCause多条件分页查询
     * @param returnCause
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnCause> findPage(ReturnCause returnCause, int page, int size);

    /***
     * ReturnCause分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ReturnCause> findPage(int page, int size);

    /***
     * ReturnCause多条件搜索方法
     * @param returnCause
     * @return
     */
    List<ReturnCause> findList(ReturnCause returnCause);

    /***
     * 删除ReturnCause
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改ReturnCause数据
     * @param returnCause
     */
    void update(ReturnCause returnCause);

    /***
     * 新增ReturnCause
     * @param returnCause
     */
    void add(ReturnCause returnCause);

    /**
     * 根据ID查询ReturnCause
     * @param id
     * @return
     */
     ReturnCause findById(Integer id);

    /***
     * 查询所有ReturnCause
     * @return
     */
    List<ReturnCause> findAll();
}
