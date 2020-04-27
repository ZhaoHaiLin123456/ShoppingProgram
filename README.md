# ShoppingProgram
针对大型电商网站的用户多、高并发、海量数据，功能多，变更快，频繁发布，为了解决这些问题，对单体项目进行架构优化。学习项目
	责任描述：
1)	运营商商品管理微服务：首页、商品管理（品牌、参数、模板、分类、审核）、商家后台管理（添加商品、管理上商品、用户管理）、ActiveMQ触发（数据导入solr、清楚solr）。
2)	广告内容管理微服务：广告管理（Redis）、广告分类管理。
3)	门户搜索微服务：搜索商品（Solr）、数据导入(Solr)。
4)	动态数据静态化处理微服务：使用freemarker生成详情页sku数据。
	技术描述：
1)	使用分布式开发，前后端分离、使用网关（负载均衡、解决跨域）
2)	使用WebService微服务实现单一职责的开发
3)	使用Solr服务：关系型数据库里需要做全文搜索的字段，导入Solr中
4)	使用redis非关系型数据库缓存海量的广告内容、商品内容等，解决高并发、性能优化。
5)	使用ActiveMQ消息服务（导入solr库消息、通知生成商品详情页的消息、搜索solr库、动态数据静态化处理freemarker）
6)	使用angular.js完成页面接口接入
