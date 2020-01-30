package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.search.service.impl
 ****/
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    /****
     * 通过ElasticsearchTemplate实现对索引库的操作
     */
    @Autowired
    private ElasticsearchTemplate esTemplate;

    /***
     * 搜索实现
     * @param searchMap:用户的搜索条件
     *                   关键字搜索  key=keywords
     * @return
     */
    @Override
    public Map searhc(Map<String, String> searchMap) {
        //准备搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = basicQueryBuilder(searchMap);

        //搜索结果
        Map resultMap = searchList(nativeSearchQueryBuilder);

        //聚合查询
        if(searchMap==null || StringUtils.isEmpty(searchMap.get("category"))){
            //分类分组查询
            List<String> categoryList = searchCategoryList(nativeSearchQueryBuilder);
            resultMap.put("categoryList",categoryList);
        }

        //聚合查询
        if(searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            //查询品牌集合
            List<String> brandList = searchBrandList(nativeSearchQueryBuilder);
            resultMap.put("brandList", brandList);
        }


        //聚合查询
        //查询规格数据
        Map<String, Set<String>> specMap = searchSpec(nativeSearchQueryBuilder);
        resultMap.put("specList",specMap);
        return resultMap;
    }

    /****
     * 根据规格分组查询规格数据
     */
    public Map<String,Set<String>> searchSpec(NativeSearchQueryBuilder nativeSearchQueryBuilder){
        /****
         * 根据规格spec进行分组查询
         * 1:给当前分组取一个别名
         * 2:分组的域的名字
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword"));

        //实现分组搜索
        AggregatedPage<SkuInfo> categoryPage = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);

        //获取所有分组数据
        Aggregations aggregations = categoryPage.getAggregations();

        //根据别名获取分组数据
        StringTerms stringTerms = aggregations.get("skuSpec");

        //创建一个集合，从StringTerms获取每个分组数据
        List<String> specList = new ArrayList<String>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            //规格
            String spec = bucket.getKeyAsString();
            specList.add(spec);
        }
        //汇总统计
        Map<String, Set<String>> mapSpec = putAllSpec(specList);
        return mapSpec;
    }

    /*****
     * 将所有规格数据进行汇总操作
     */
    public Map<String,Set<String>> putAllSpec(List<String> specList){
        //返回数据定义
        Map<String,Set<String>> map = new HashMap<String,Set<String>>();

        for (String spec : specList) {
            //将spec转成Map结构
            Map<String,String> specMap = JSON.parseObject(spec,Map.class);

            //循环specMap
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                //获取当前规格的名字和值
                String key = entry.getKey();    //规格名字   电视屏幕尺寸
                String value = entry.getValue(); //规格的值   50英寸

                //从map中获取当前规格的规格集合信息
                Set<String> specSet = map.get(key);

                //如果存在规格集合信息，则将当前规格信息加入到Set集合中
                if(specSet==null){
                    specSet = new HashSet<String>();
                }
                specSet.add(value);
                //如果没有，则创建该规格的新的Set集合
                map.put(key,specSet);
            }
        }
        return map;
    }


    /****
     * 品牌分组搜索实现
     * @param nativeSearchQueryBuilder
     * @return
     */
    public List<String> searchBrandList(NativeSearchQueryBuilder nativeSearchQueryBuilder){
        /****
         * 根据品牌名字进行分组查询
         * 1:给当前分组取一个别名
         * 2:分组的域的名字
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));

        //实现分组搜索
        AggregatedPage<SkuInfo> categoryPage = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);

        //获取所有分组数据
        Aggregations aggregations = categoryPage.getAggregations();

        //根据别名获取分组数据
        StringTerms stringTerms = aggregations.get("skuBrand");

        //创建一个集合，从StringTerms获取每个分组数据
        List<String> brandList = new ArrayList<String>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            //品牌的名字
            String categoryName = bucket.getKeyAsString();
            brandList.add(categoryName);
        }
        return brandList;
    }


    /****
     * 分类分组搜索实现
     * @param nativeSearchQueryBuilder
     * @return
     */
    public List<String> searchCategoryList(NativeSearchQueryBuilder nativeSearchQueryBuilder){
        /****
         * 根据分类名字进行分组查询
         * 1:给当前分组取一个别名
         * 2:分组的域的名字
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        //品牌合并分组查询
        //nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        //规格合并查询

        //实现分组搜索
        AggregatedPage<SkuInfo> categoryPage = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);

        //获取所有分组数据
        Aggregations aggregations = categoryPage.getAggregations();

        //根据别名获取分组数据
        StringTerms stringTerms = aggregations.get("skuCategory");

        //创建一个集合，从StringTerms获取每个分组数据
        List<String> categoryList = new ArrayList<String>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            //分类的名字
            String categoryName = bucket.getKeyAsString();
            categoryList.add(categoryName);
        }
        return categoryList;
    }


    /***
     * 搜索实现
     * @param nativeSearchQueryBuilder
     * @return
     */
    public Map searchList(NativeSearchQueryBuilder nativeSearchQueryBuilder){
        //指定高亮域
        HighlightBuilder.Field field = new HighlightBuilder.Field("name");  //指定高亮域的名字
        //指定高亮的前缀
        field.preTags("<em style='color:red;'>");
        //指定高亮的后缀
        field.postTags("</em>");
        //显示高亮数据的长度
        field.fragmentSize(100);
        //开启高亮
        nativeSearchQueryBuilder.withHighlightFields(field);

        /***
         * 执行搜索
         * 1:搜索条件封装对象   NativeSearchQuery
         * 2:搜索的数据需要转换成指定的JavaBean对象的字节码
         */
        //AggregatedPage<SkuInfo> skuPage = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);

        /***
         * 高亮搜索实现
         * SearchResultMapper:将查询出的结果进行处理，实现新的映射转换
         */
        AggregatedPage<SkuInfo> skuPage = esTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
            /***
             * 数据+分页
             * @param response:查询之后的数据
             * @param clazz
             * @param pageable:分页数据的封装
             * @param <T>
             * @return
             */
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                //创建一个集合对象，存储高亮数据
                List<T> list = new ArrayList<T>();

                //获取所有数据,循环所有数据
                for (SearchHit hit : response.getHits()) {
                    //获取非高亮数据  {"name":"华为手机","iamge":"http://www.itheima.com/1.jpg"}
                    String json = hit.getSourceAsString();
                    SkuInfo skuInfo = JSON.parseObject(json,SkuInfo.class);

                    /***
                     * 获取高亮数据
                     * hit.getHighlightFields():获取所有高亮域的数据
                     * get("name"):获取name域的高亮数据
                     */
                    HighlightField highlightField = hit.getHighlightFields().get("name");
                    if(highlightField!=null){
                        //创建StringBuffer，存储高亮数据
                        StringBuffer buffer = new StringBuffer();
                        for (Text text : highlightField.getFragments()) {
                            buffer.append(text.toString());
                        }
                        //将非高亮数据替换成高亮数据
                        skuInfo.setName(buffer.toString());
                    }
                    //将高亮数据存入到List集合中
                    list.add((T) skuInfo);
                }
                /***
                 * 1:高亮集合数据
                 * 2:分页对象
                 * 3:总记录数
                 */
                return new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
            }
        });

        //创建Map，封装返回结果数据
        Map resultMap = new HashMap();
        //获取总记录数
        long totalElements = skuPage.getTotalElements();
        resultMap.put("total",totalElements);
        //总页数
        resultMap.put("totalPages",skuPage.getTotalPages());
        //获取集合数据
        List<SkuInfo> skuInfoList = skuPage.getContent();
        resultMap.put("rows",skuInfoList);
        return resultMap;
    }


    /****
     * 用于构建搜索对象
     * 主要用于封装搜索条件
     * @param searchMap
     * @return
     */
    public NativeSearchQueryBuilder basicQueryBuilder(Map<String,String> searchMap){
        //准备搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //多条件组合
        BoolQueryBuilder queryBuilder  = QueryBuilders.boolQuery();

        if(searchMap!=null){
            //获取关键词，判断关键词是否为空,不为空，则将搜索的关键词作为搜索条件
            if(!StringUtils.isEmpty(searchMap.get("keywords"))){
                //则将搜索的关键词作为搜索条件
                //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("name",searchMap.get("keywords")));
                queryBuilder.must(QueryBuilders.matchQuery("name",searchMap.get("keywords")));
            }

            //分类 category,不为空，则用户选中了分类
            if(!StringUtils.isEmpty(searchMap.get("category"))){
                queryBuilder.must(QueryBuilders.matchQuery("categoryName",searchMap.get("category")));
            }

            //品牌 brand,不为空，用户选中品牌
            if(!StringUtils.isEmpty(searchMap.get("brand"))){
                queryBuilder.must(QueryBuilders.matchQuery("brandName",searchMap.get("brand")));
            }

            //价格范围查找Range
            if(!StringUtils.isEmpty(searchMap.get("price"))){
                //根据-分割
                String[] prices = searchMap.get("price").split("-");

                //prices[0]<price
                queryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(prices[0])));

                //如果prices.length==2, AND price<=prices[1]
                if(prices.length==2){
                    queryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[1])));
                }
            }

            //规格检索
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                //key=spec_网络           value=电信2G
                //key=spec_手机屏幕尺寸   value=4.0-4.9英寸
                String key = entry.getKey();
                //以spec_开始，说明校验的是规格数据
                if(key.startsWith("spec_")){
                    queryBuilder.must(QueryBuilders.matchQuery("specMap."+key.substring(5)+".keyword",entry.getValue()));
                }
            }

            //排序
            String sortRule = searchMap.get("sortRule");
            String sortFeild = searchMap.get("sortFeild");
            if(!StringUtils.isEmpty(sortFeild)){
                nativeSearchQueryBuilder.withSort(
                        SortBuilders.fieldSort(sortFeild)   //排序的域
                        .order(SortOrder.valueOf(sortRule))); //排序的类型
            }
        }

        //分页实现  pageNum:当前页
        Integer page = converter(searchMap);
        Integer size = 30;   //每页显示的条数
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page-1,size));

        //设置组合条件
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        return nativeSearchQueryBuilder;
    }


    /***
     * 获取当前页
     * @return
     */
    public Integer converter(Map<String,String> searchMap){
        if(searchMap!=null && !StringUtils.isEmpty(searchMap.get("pageNum"))){
            try {
                //获取分页，数据转换
                return Integer.parseInt(searchMap.get("pageNum"));
            } catch (NumberFormatException e) {
            }
        }
        //默认第1页
        return 1;
    }

    /***
     * 调用商品微服务，查询商品数据
     * 并且将商品数据导入到ES索引库中
     */
    @Override
    public void importSku() {
        //1.先查询List<Sku>
        List<Sku> skuList = skuFeign.findByStatus("1");

        //2.将List<Sku>转换成List<SkuInfo>
        String skuJson = JSON.toJSONString(skuList);  //{"name":"xxx","price":2345}
        List<SkuInfo> skuInfoList =JSON.parseArray(skuJson,SkuInfo.class) ;

        //3.将spec规格转换成Map，赋值给specMap
        for (SkuInfo skuInfo : skuInfoList) {
            //获取规格
            String specJson = skuInfo.getSpec();
            //并将规格转成Map
            Map<String,Object> specMap = JSON.parseObject(specJson, Map.class);
            //将Map赋值给specMap
            skuInfo.setSpecMap(specMap);
        }

        //4.使用SpringData ElasticSearch 实现数据批量导入索引库
        skuEsMapper.saveAll(skuInfoList);
    }
}
