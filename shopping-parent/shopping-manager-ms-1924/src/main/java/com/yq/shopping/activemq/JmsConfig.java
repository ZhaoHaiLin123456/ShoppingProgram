package com.yq.shopping.activemq;

/**
 * 运营商后台微服务 1、导入Solr库的消息 2、通知生成商品详情页
 * 
 * @author HaiLin
 *
 */
public class JmsConfig {

	public final static String TOPIC_HTML = "pingyougou.topic.createhtml";
	public final static String TOPIC_HTML_DELETE = "pingyougou.topic.deletehtml";

	public final static String QUEUE = "pingyougou.queue.solr";

	public final static String TOPIC_SOLR = "pingyougou.topic.solr";

	public final static String QUEUE_DELETE = "pingyougou.queue.solr.delete";
	
}
