package com.changgou.goods.service.impl;
import com.changgou.goods.dao.CategoryBrandMapper;
import com.changgou.goods.pojo.CategoryBrand;
import com.changgou.goods.service.CategoryBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:CategoryBrand业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;


    /**
     * CategoryBrand条件+分页查询
     * @param categoryBrand 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<CategoryBrand> findPage(CategoryBrand categoryBrand, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(categoryBrand);
        //执行搜索
        return new PageInfo<CategoryBrand>(categoryBrandMapper.selectByExample(example));
    }

    /**
     * CategoryBrand分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<CategoryBrand> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<CategoryBrand>(categoryBrandMapper.selectAll());
    }

    /**
     * CategoryBrand条件查询
     * @param categoryBrand
     * @return
     */
    @Override
    public List<CategoryBrand> findList(CategoryBrand categoryBrand){
        //构建查询条件
        Example example = createExample(categoryBrand);
        //根据构建的条件查询数据
        return categoryBrandMapper.selectByExample(example);
    }


    /**
     * CategoryBrand构建查询对象
     * @param categoryBrand
     * @return
     */
    public Example createExample(CategoryBrand categoryBrand){
        Example example=new Example(CategoryBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if(categoryBrand!=null){
            // 分类ID
            if(!StringUtils.isEmpty(categoryBrand.getCategoryId())){
                    criteria.andEqualTo("categoryId",categoryBrand.getCategoryId());
            }
            // 品牌ID
            if(!StringUtils.isEmpty(categoryBrand.getBrandId())){
                    criteria.andEqualTo("brandId",categoryBrand.getBrandId());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id){
        categoryBrandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改CategoryBrand
     * @param categoryBrand
     */
    @Override
    public void update(CategoryBrand categoryBrand){
        categoryBrandMapper.updateByPrimaryKey(categoryBrand);
    }

    /**
     * 增加CategoryBrand
     * @param categoryBrand
     */
    @Override
    public void add(CategoryBrand categoryBrand){
        categoryBrandMapper.insert(categoryBrand);
    }

    /**
     * 根据ID查询CategoryBrand
     * @param id
     * @return
     */
    @Override
    public CategoryBrand findById(Integer id){
        return  categoryBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询CategoryBrand全部数据
     * @return
     */
    @Override
    public List<CategoryBrand> findAll() {
        return categoryBrandMapper.selectAll();
    }
}
