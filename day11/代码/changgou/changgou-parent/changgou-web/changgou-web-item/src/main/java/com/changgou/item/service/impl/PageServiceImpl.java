package com.changgou.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.item.service.PageService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.item.service.impl *
 * @since 1.0
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private TemplateEngine templateEngine;

    //生成静态文件路径
    @Value("${pagepath}")
    private String pagepath;

    /**
     * 构建数据模型
     * @param spuId
     * @return
     */
    private Map<String,Object> buildDataModel(Long spuId){
        //构建数据模型
        Map<String,Object> dataMap = new HashMap<>();
        //获取spu 和SKU列表
        Result<Spu> result = spuFeign.findById(spuId);
        Spu spu = result.getData();

        //获取分类信息
        dataMap.put("category1",categoryFeign.findById(spu.getCategory1Id()).getData());
        dataMap.put("category2",categoryFeign.findById(spu.getCategory2Id()).getData());
        dataMap.put("category3",categoryFeign.findById(spu.getCategory3Id()).getData());
        if(spu.getImages()!=null) {
            dataMap.put("imageList", spu.getImages().split(","));
        }

        dataMap.put("specificationList", JSON.parseObject(spu.getSpecItems(),Map.class));
        dataMap.put("spu",spu);

        //根据spuId查询Sku集合
        Sku skuCondition = new Sku();
        skuCondition.setSpuId(spu.getId());
        Result<List<Sku>> resultSku = skuFeign.findList(skuCondition);
        dataMap.put("skuList",resultSku.getData());
        return dataMap;
    }

    /***
     * 生成静态页
     * @param spuId
     */
    @Override
    public void createPageHtml(Long spuId) {
        // 1.上下文  模板  +  数据集 =html
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(spuId);
        context.setVariables(dataModel);//model.addtribute()
        // 2.准备文件
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, spuId + ".html");
        // 3.生成页面
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            //模板的文件
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
