package com.changgou.content.service;

import com.changgou.content.pojo.ContentCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:ContentCategory业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface ContentCategoryService {

    /***
     * ContentCategory多条件分页查询
     * @param contentCategory
     * @param page
     * @param size
     * @return
     */
    PageInfo<ContentCategory> findPage(ContentCategory contentCategory, int page, int size);

    /***
     * ContentCategory分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ContentCategory> findPage(int page, int size);

    /***
     * ContentCategory多条件搜索方法
     * @param contentCategory
     * @return
     */
    List<ContentCategory> findList(ContentCategory contentCategory);

    /***
     * 删除ContentCategory
     * @param id
     */
    void delete(Long id);

    /***
     * 修改ContentCategory数据
     * @param contentCategory
     */
    void update(ContentCategory contentCategory);

    /***
     * 新增ContentCategory
     * @param contentCategory
     */
    void add(ContentCategory contentCategory);

    /**
     * 根据ID查询ContentCategory
     * @param id
     * @return
     */
     ContentCategory findById(Long id);

    /***
     * 查询所有ContentCategory
     * @return
     */
    List<ContentCategory> findAll();
}
