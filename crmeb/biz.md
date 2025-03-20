# CRMEB系统商品和分类相关表结构及关系

## 主要商品相关表

### 1. 商品基础表
- **eb_store_product**：商品主表，包含商品基本信息如名称、价格、库存、分类等
  - 主键：id (商品ID)
  - 字段包括：商品名称、价格、图片、库存、销量、分类ID等
  - 商品状态字段：is_show(上下架)、is_del(删除)、is_recycle(回收站)
  - 商品类型标记：is_seckill(秒杀)、is_bargain(砍价)、is_hot(热卖)等

### 2. 商品规格与属性相关表
- **eb_store_product_attr**：商品属性表
  - 主键：id
  - 外键：product_id (关联商品表)
  - 记录商品属性名称和属性值(如颜色、尺寸等)
  - type字段区分普通商品(0)、秒杀(1)、砍价(2)、拼团(3)

- **eb_store_product_attr_value**：商品属性值表
  - 主键：id
  - 外键：product_id (关联商品表)
  - 记录商品不同规格组合的具体SKU信息
  - 包含价格、库存、成本、图片等信息
  - type字段区分普通商品(0)、秒杀(1)、砍价(2)、拼团(3)

- **eb_store_product_attr_result**：商品属性结果表
  - 主键：id
  - 外键：product_id (关联商品表)
  - 保存商品属性参数的整体结果

- **eb_store_product_rule**：商品规则值(规格)表
  - 定义通用的规格模板，如"颜色"规格包含"白色、黑色、红色"等

### 3. 商品分类关联表
- **eb_store_product_cate**：商品分类辅助表
  - 主键：id
  - 外键：product_id (关联商品表)、cate_id (关联分类表)
  - 用于多对多关联商品与分类

### 4. 商品内容相关表
- **eb_store_product_description**：商品描述表
  - 主键：id
  - 外键：product_id (关联商品表)
  - 存储商品详情描述HTML内容
  - type字段区分普通商品(0)、秒杀(1)、砍价(2)、拼团(3)

### 5. 商品用户互动表
- **eb_store_product_relation**：商品点赞和收藏表
  - 主键：id
  - 外键：uid (用户ID)、product_id (商品ID)
  - type字段区分不同交互类型(收藏collect、点赞like)

- **eb_store_product_reply**：商品评论表
  - 主键：id
  - 外键：uid (用户ID)、product_id (商品ID)、oid (订单ID)
  - 记录用户对商品的评价信息

- **eb_store_product_log**：商品浏览/统计表
  - 记录商品的浏览、加购、下单等行为统计

### 6. 优惠与营销相关表
- **eb_store_product_coupon**：商品优惠券表
  - 主键：id
  - 外键：product_id (商品ID)
  - 关联商品和优惠券

### 7. 活动商品相关表
- **eb_store_seckill**：商品秒杀表
  - 主键：id
  - 外键：product_id (商品ID)
  - 存储秒杀活动信息

- **eb_store_combination**：拼团商品表
  - 主键：id
  - 外键：product_id (商品ID)
  - 存储拼团活动信息

- **eb_store_bargain**：砍价商品表
  - 主键：id
  - 外键：product_id (商品ID)
  - 存储砍价活动信息

### 8. 购物车表
- **eb_store_cart**：购物车表
  - 主键：id
  - 外键：uid (用户ID)、product_id (商品ID)
  - 记录用户购物车中的商品

## 商品规格和属性表关系示例

假设有一件商品："万事利真丝围巾"，它有以下规格：
- 颜色：蓝色、红色
- 尺寸：200*27cm、240*30cm

### 1. eb_store_product_rule（规格模板表）

这个表存储了可复用的规格模板，比如"颜色"规格模板、"尺寸"规格模板：

```
id: 2
rule_name: "围巾"
rule_value: "[{\"value\":\"颜色\",\"detail\":[\"格物致知蓝咖\",\"格物致知红色\"],\"inputVisible\":false},{\"value\":\"尺码\",\"detail\":[\"200*27cm\",\"240*30\"],\"inputVisible\":false}]"
```

商户在添加新商品时可以选择这些预设的规格模板，避免重复输入同样的规格选项。

### 2. eb_store_product_attr（商品属性表）

当给商品设置规格时，会在这个表中记录该商品使用了哪些属性名称：

```
id: 16
product_id: 3  // 对应"万事利真丝围巾"商品
attr_name: "颜色"
attr_values: "格物致知蓝咖,格物致知红色"
type: 0  // 0表示普通商品
is_del: 0

id: 17
product_id: 3
attr_name: "尺码"
attr_values: "200*27cm,240*30"
type: 0
is_del: 0
```

可以看到，"万事利真丝围巾"使用了两个规格属性："颜色"和"尺码"。

### 3. eb_store_product_attr_value（商品属性值表）

这个表存储了具体每个规格组合的SKU信息，包括价格、库存、图片等：

```
id: 19
product_id: 3
suk: "格物致知蓝咖,200*27cm"  // 规格组合：蓝色+200*27cm
stock: 698  // 库存
sales: 0  // 销量
price: 374.00  // 价格
image: "crmebimage/public/maintain/2021/12/25/...jpg"  // 图片
cost: 100.00  // 成本价
ot_price: 412.00  // 原价
weight: 0.50  // 重量
type: 0  // 普通商品
attr_value: "{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"200*27cm\"}"  // JSON格式存储规格值

id: 20
product_id: 3
suk: "格物致知蓝咖,240*30"  // 规格组合：蓝色+240*30
// ... 其他属性

id: 21
product_id: 3
suk: "格物致知红色,200*27cm"  // 规格组合：红色+200*27cm
// ... 其他属性

id: 22
product_id: 3
suk: "格物致知红色,240*30"  // 规格组合：红色+240*30
// ... 其他属性
```

对于这款围巾，由于有2种颜色和2种尺码，所以一共有4种规格组合(2×2=4)，对应4条SKU记录。

### 4. eb_store_product_attr_result（商品属性结果表）

这个表存储了商品所有规格和属性的完整JSON数据，便于快速读取：

```
id: x
product_id: 3
result: "{\"attr\":[{\"value\":\"颜色\",\"detailValue\":\"\",\"attrHidden\":\"\",\"detail\":[\"格物致知蓝咖\",\"格物致知红色\"]},{\"value\":\"尺码\",\"detailValue\":\"\",\"attrHidden\":\"\",\"detail\":[\"200*27cm\",\"240*30\"]}],\"value\":[{\"detail\":{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"200*27cm\"},\"price\":\"374\",\"cost\":\"100\",\"ot_price\":\"412\",\"stock\":\"698\",\"bar_code\":\"\",\"weight\":\"0.5\",\"volume\":\"0\",\"brokerage\":\"0\",\"brokerage_two\":\"0\"},{\"detail\":{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"240*30\"},\"price\":\"374\",\"cost\":\"100\",\"ot_price\":\"412\",\"stock\":\"366\",\"bar_code\":\"\",\"weight\":\"0.5\",\"volume\":\"0\",\"brokerage\":\"0\",\"brokerage_two\":\"0\"},{\"detail\":{\"颜色\":\"格物致知红色\",\"尺码\":\"200*27cm\"},\"price\":\"374\",\"cost\":\"100\",\"ot_price\":\"412\",\"stock\":\"124\",\"bar_code\":\"\",\"weight\":\"0.5\",\"volume\":\"0\",\"brokerage\":\"0\",\"brokerage_two\":\"0\"},{\"detail\":{\"颜色\":\"格物致知红色\",\"尺码\":\"240*30\"},\"price\":\"374\",\"cost\":\"100\",\"ot_price\":\"412\",\"stock\":\"677\",\"bar_code\":\"\",\"weight\":\"0.5\",\"volume\":\"0\",\"brokerage\":\"0\",\"brokerage_two\":\"0\"}]}"
change_time: 1640408162  // 上次修改时间
type: 0  // 普通商品
```

这个表中的JSON数据包含了所有规格组合和它们的价格、库存等信息，相当于是其他表的汇总信息。

### 活动商品的规格处理

如果这款围巾同时参与了秒杀活动，系统会在相同的表结构中使用不同的type值来区分：

```
// eb_store_product_attr表
id: 18
product_id: 3
attr_name: "颜色"
attr_values: "格物致知蓝咖,格物致知红色"
type: 1  // 1表示秒杀商品
is_del: 0

// eb_store_product_attr_value表中，会有秒杀价格的记录
id: 23
product_id: 3
suk: "格物致知红色,200*27cm"
stock: 124
price: 374.00  // 秒杀价格可能不同
type: 1  // 1表示秒杀商品
quota: 124  // 秒杀限购数量
quota_show: 124  // 显示的限购数量
```

## 分类表(eb_category)详解

`eb_category`是CRMEB系统中的一个通用分类表，用于多种不同的分类场景：

```sql
CREATE TABLE `eb_category` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父级ID',
  `path` varchar(255) NOT NULL DEFAULT '/0/' COMMENT '路径',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `type` smallint(2) NULL DEFAULT 1 COMMENT '类型，1 产品分类，2 附件分类，3 文章分类， 4 设置分类， 5 菜单分类，6 配置分类， 7 秒杀配置',
  `url` varchar(255) NULL DEFAULT '' COMMENT '地址',
  `extra` text NULL COMMENT '扩展字段 Jsos格式',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态, 1正常，0失效',
  `sort` int(5) NOT NULL DEFAULT 99999 COMMENT '排序',
  ...
)
```

### 分类类型及使用场景

#### 1. 产品分类 (type=1)

**使用场景**：
- 商城前台展示的商品分类树结构
- 商品管理中对商品进行分类归类
- 用户浏览和筛选商品时的导航结构

**典型应用**：
- 首页、分类页面的商品分类导航
- 商品详情页的"面包屑"导航
- 商品搜索和筛选时的分类选项

例如：服装、电子产品、食品、家居用品等商品分类。

#### 2. 附件分类 (type=2)

**使用场景**：
- 系统上传的各类图片、文件的分类管理
- 后台媒体资源中心的分类组织

**典型应用**：
- 商品图片管理（商品上传图片时左侧的分类列表）
- 商品详情页富文本编辑器中上传的图片
- 广告图、轮播图等媒体资源的管理

例如：商品图片、Banner图片、文章图片、LOGO等不同类型的媒体资源分类。

#### 3. 文章分类 (type=3)

**使用场景**：
- CMS内容管理系统的文章分类
- 商城资讯、帮助中心的内容分类

**典型应用**：
- 商城公告栏目
- 帮助中心分类
- 营销资讯类别

例如：公司新闻、商品资讯、购物指南、配送说明、售后服务等内容分类。

#### 4. 设置分类 (type=4)

**使用场景**：
- 系统后台设置项的分类组织
- 各类配置的分组管理

**典型应用**：
- 系统设置的分组导航
- 各功能模块配置的分类

例如：基础设置、短信设置、支付设置、物流设置等配置分类。

#### 5. 菜单分类 (type=5)

**使用场景**：
- 系统后台管理面板的菜单结构
- 权限模块对功能的分组

**典型应用**：
- 后台管理系统的导航菜单
- 权限划分的功能模块

例如：商品管理、订单管理、用户管理、系统设置等后台菜单分类。

#### 6. 配置分类 (type=6)

**使用场景**：
- 系统各类参数配置的分组
- 开发配置项的分类

**典型应用**：
- 系统参数配置界面的分组
- 第三方接口配置的分类
- 组合数据的分类管理

例如：99Api配置、短信配置、微信配置、支付配置等系统配置的分类。

#### 7. 秒杀配置 (type=7)

**使用场景**：
- 秒杀活动的时间段配置
- 秒杀场次管理

**典型应用**：
- 秒杀活动的时间段设置
- 不同时段秒杀商品的管理

例如：上午场、下午场、晚间场等秒杀活动时间段的配置分类。

### 商品与分类的关系

1. **产品通过中间表与分类关联**：`eb_store_product_cate`表建立商品与分类的多对多关系
   ```sql
   CREATE TABLE `eb_store_product_cate` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `product_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品id',
     `cate_id` int(11) NOT NULL DEFAULT 0 COMMENT '分类id',
     `add_time` int(11) NOT NULL DEFAULT 0 COMMENT '添加时间',
     PRIMARY KEY (`id`)
   )
   ```

2. **树形分类结构**：通过`pid`(父ID)和`path`字段构建树形分类结构
   ```
   服装(pid=0, path='/0/')
   ├── 女装(pid=服装ID, path='/0/服装ID/')
   │   ├── 裙装(pid=女装ID, path='/0/服装ID/女装ID/')
   │   │   └── 连衣裙(pid=裙装ID, path='/0/服装ID/女装ID/裙装ID/')
   │   └── 上衣(pid=女装ID, path='/0/服装ID/女装ID/')
   └── 男装(pid=服装ID, path='/0/服装ID/')
   ```

3. **一个商品可以属于多个分类**：例如一件"连衣裙"既可以在"女装 > 裙装 > 连衣裙"分类下，也可以在"新品"、"热卖"等分类下

### 分类表设计的优势

CRMEB使用统一的分类表来管理这么多不同场景的分类，有以下几个优势：

1. **代码复用**：使用同一套分类管理逻辑处理不同类型的分类
2. **统一接口**：统一的CRUD接口简化了开发
3. **灵活扩展**：如果需要新增分类类型，只需添加新的type值，无需修改数据库结构
4. **树形结构支持**：所有类型的分类都支持通过pid(父ID)和path字段构建树形结构

## 备注
以上内容整理自CRMEB系统的数据库结构和代码分析，作为系统开发和维护的参考资料。
