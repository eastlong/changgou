package com.changgou.user.service;

import com.changgou.user.pojo.OauthClientDetails;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:OauthClientDetails业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OauthClientDetailsService {

    /***
     * OauthClientDetails多条件分页查询
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(OauthClientDetails oauthClientDetails, int page, int size);

    /***
     * OauthClientDetails分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(int page, int size);

    /***
     * OauthClientDetails多条件搜索方法
     * @param oauthClientDetails
     * @return
     */
    List<OauthClientDetails> findList(OauthClientDetails oauthClientDetails);

    /***
     * 删除OauthClientDetails
     * @param id
     */
    void delete(String id);

    /***
     * 修改OauthClientDetails数据
     * @param oauthClientDetails
     */
    void update(OauthClientDetails oauthClientDetails);

    /***
     * 新增OauthClientDetails
     * @param oauthClientDetails
     */
    void add(OauthClientDetails oauthClientDetails);

    /**
     * 根据ID查询OauthClientDetails
     * @param id
     * @return
     */
     OauthClientDetails findById(String id);

    /***
     * 查询所有OauthClientDetails
     * @return
     */
    List<OauthClientDetails> findAll();
}
