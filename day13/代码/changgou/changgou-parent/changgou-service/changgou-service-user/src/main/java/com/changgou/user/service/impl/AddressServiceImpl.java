package com.changgou.user.service.impl;

import com.changgou.user.dao.AddressMapper;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:admin
 * @Description:Address业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    /**
     * Address条件+分页查询
     *
     * @param address 查询条件
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Address> findPage(Address address, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(address);
        //执行搜索
        return new PageInfo<Address>(addressMapper.selectByExample(example));
    }

    /**
     * Address分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Address> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Address>(addressMapper.selectAll());
    }

    /**
     * Address条件查询
     *
     * @param address
     * @return
     */
    @Override
    public List<Address> findList(Address address) {
        //构建查询条件
        Example example = createExample(address);
        //根据构建的条件查询数据
        return addressMapper.selectByExample(example);
    }


    /**
     * Address构建查询对象
     *
     * @param address
     * @return
     */
    public Example createExample(Address address) {
        Example example = new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        if (address != null) {
            // 
            if (!StringUtils.isEmpty(address.getId())) {
                criteria.andEqualTo("id", address.getId());
            }
            // 用户名
            if (!StringUtils.isEmpty(address.getUsername())) {
                criteria.andLike("username", "%" + address.getUsername() + "%");
            }
            // 省
            if (!StringUtils.isEmpty(address.getProvinceid())) {
                criteria.andEqualTo("provinceid", address.getProvinceid());
            }
            // 市
            if (!StringUtils.isEmpty(address.getCityid())) {
                criteria.andEqualTo("cityid", address.getCityid());
            }
            // 县/区
            if (!StringUtils.isEmpty(address.getAreaid())) {
                criteria.andEqualTo("areaid", address.getAreaid());
            }
            // 电话
            if (!StringUtils.isEmpty(address.getPhone())) {
                criteria.andEqualTo("phone", address.getPhone());
            }
            // 详细地址
            if (!StringUtils.isEmpty(address.getAddress())) {
                criteria.andEqualTo("address", address.getAddress());
            }
            // 联系人
            if (!StringUtils.isEmpty(address.getContact())) {
                criteria.andEqualTo("contact", address.getContact());
            }
            // 是否是默认 1默认 0否
            if (!StringUtils.isEmpty(address.getIsDefault())) {
                criteria.andEqualTo("isDefault", address.getIsDefault());
            }
            // 别名
            if (!StringUtils.isEmpty(address.getAlias())) {
                criteria.andEqualTo("alias", address.getAlias());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Address
     *
     * @param address
     */
    @Override
    public void update(Address address) {
        addressMapper.updateByPrimaryKey(address);
    }

    /**
     * 增加Address
     *
     * @param address
     */
    @Override
    public void add(Address address) {
        addressMapper.insert(address);
    }

    /**
     * 根据ID查询Address
     *
     * @param id
     * @return
     */
    @Override
    public Address findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Address全部数据
     *
     * @return
     */
    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }

    @Override
    public List<Address> list(String username) {
        //select * from tb_address where usenrame =?
        Address address = new Address();
        address.setUsername(username);
        return addressMapper.select(address);
    }
}
