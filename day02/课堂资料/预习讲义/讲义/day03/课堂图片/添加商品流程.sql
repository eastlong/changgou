-- parentid=0顶级分类[一级分类]
SELECT * FROM tb_category WHERE parent_id=0;

-- 家用电器  查询parent_id=74的分类
SELECT * FROM tb_category WHERE parent_id=74;

-- 显示大家电下的所有子分类  parent_id=75
SELECT * FROM tb_category WHERE parent_id=75;
-- (1)综合  根据父ID查询子分类   templateId:模板ID=42
--      分类：平板电视    id=76
SELECT * FROM tb_category WHERE parent_id=?

-- 加载分类对应的品牌数据   分类ID->根据分类ID找到品牌集合数据(tb_category_brand)
SELECT * FROM tb_category_brand WHERE category_id=76;
-- (2)根据分类ID查询品牌数据
-- 子查询|
SELECT * FROM tb_brand tb WHERE tb.id IN(
 SELECT tcb.brand_id FROM tb_category_brand tcb WHERE tcb.category_id=76
)
-- 等值查询|
SELECT tb.* FROM tb_category_brand tcb,tb_brand tb WHERE tcb.category_id=76 AND tb.id=tcb.brand_id

-- (3)查询模板数据,根据用户所选择的分类找到分类的模板id(templateId)根据模板ID查询模板信息
SELECT * FROM tb_template WHERE id=42;


-- (4)查询规格数据
SELECT * FROM tb_spec WHERE template_id=42;


-- （5）根据用户选中的分类对应的模板ID(templateId)查询参数
select * from tb_para where template_id=42

-- (6)保存数据  存储SPU+List<SKU>





