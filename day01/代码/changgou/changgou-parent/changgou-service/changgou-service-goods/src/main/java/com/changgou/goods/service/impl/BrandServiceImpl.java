package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou.goods.service.impl *
 * @since 1.0
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.selectAll();

        return brands;
    }

    @Override
    public Brand findById(Integer id) {
        // select * from tb_brand where id = ?
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        //insert into tb_brand(id,name,....) values(#{})
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        //update tb_brand  set name=? where id =?
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        //delete from tb_brand wehre id =?
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        //select * from tb_brand where name like "%?%" and letter = '?'
        //1.创建条件对象( 设置字节码对象 标识 查询哪一个表)
        //CTR + ALT + M
        Example example = createExample(brand);

        //3.根据条件来执行查询
        List<Brand> brands = brandMapper.selectByExample(example);
        //4.返回结果
        return brands;
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {

        //1.开始分页 紧跟着的[第一个查询 才会被分页]
        PageHelper.startPage(page, size);
        //2.执行查询
        List<Brand> brands = brandMapper.selectAll();
        List<Brand> brands1111 = brandMapper.selectAll();

        System.out.println(brands.size() + "::::::::brands1111:" + brands1111.size());

        //3.获取到结果集


        //4.封装pageinfo 返回

        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size, Brand brand) {
        //1.开始分页
        PageHelper.startPage(page,size);
        //2.构建查询的条件
        Example example = createExample(brand);
        //3.执行查询
        List<Brand> brands = brandMapper.selectByExample(example);
        //4.获取结果
        //5.封装pageinfo 返回
        return new PageInfo<Brand>(brands);
    }

    private Example createExample(Brand brand) {
        Example example = new Example(Brand.class);//select * from tb_brand
        Example.Criteria criteria = example.createCriteria();
        //2.判断 拼接条件
        if (brand != null) {
            if (!StringUtils.isEmpty(brand.getName())) {// where name like ?
                //第一个参数:指定要条件比较的 属性的名称(POJO的属性名)
                //第二个参数:指定要比较的值
                criteria.andLike("name", "%" + brand.getName() + "%");
            }

            if (!StringUtils.isEmpty(brand.getLetter())) {// where letter = ?
                //第一个参数:指定要条件比较的 属性的名称(POJO的属性名)
                //第二个参数:指定要比较的值
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        return example;
    }


}
