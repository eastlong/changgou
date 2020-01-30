package com.changgou.goods.service;
import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Spec业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SpecService {

    /***
     * Spec多条件分页查询
     * @param spec
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(Spec spec, int page, int size);

    /***
     * Spec分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spec> findPage(int page, int size);

    /***
     * Spec多条件搜索方法
     * @param spec
     * @return
     */
    List<Spec> findList(Spec spec);

    /***
     * 删除Spec
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Spec数据
     * @param spec
     */
    void update(Spec spec);

    /***
     * 新增Spec
     * @param spec
     */
    void add(Spec spec);

    /**
     * 根据ID查询Spec
     * @param id
     * @return
     */
     Spec findById(Integer id);

    /***
     * 查询所有Spec
     * @return
     */
    List<Spec> findAll();


    /**
     * 根据分类的ID 查询规格的列表数据
     * @param id 三级分类的ID
     * @return
     */
    List<Spec> findByCategoryId(Integer id);
}
