package com.yq.shopping.controller;

import java.util.List;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;


import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yq.shopping.activemq.JmsConfig;
import com.yq.shopping.po.Goods;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.po.TbGoods;
import com.yq.shopping.service.GoodsService;

@RestController
@RequestMapping("/goods-ms")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public PageResult search(@RequestBody Goods goods, int page, int rows  ){
		
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
//		String sellerId = "yqtech"; //暂时设定
//		goods.setSellerId(sellerId);
		goods.setAuditStatus("0");//默认显示未上架的商品
		PageResult gooPageResult = goodsService.findPage(goods, page, rows);
		return gooPageResult;		
	}

	/**
	 * 商家后台前端调用
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/searchBussiness",method=RequestMethod.POST)
	public PageResult searchBussiness(@RequestBody Goods goods, int page, int rows  ){
		
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
//		String sellerId = "yqtech"; //暂时设定
//		goods.setSellerId(sellerId);
//		goods.setAuditStatus("0");//默认显示未上架的商品
		PageResult gooPageResult = goodsService.findPage(goods, page, rows);
		return gooPageResult;		
	}
	
	
	
	@RequestMapping(value="/updateStatus",method=RequestMethod.GET)
	public Result updateStatus(Long[] ids,String status){
//		System.out.println("商品审核商品审核商品审核商品审核商品审核商品审核");
//		System.out.println("ids: " + ids);
//		System.out.println("status: " + status);
		try {
			goodsService.updateStatus(ids, status);
			
			if("1".equals(status)){//如果是审核通过 
				//*****导入到索引库
				//得到需要导入的SKU列表?
				
				List<Item> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);
				
				
				//导入到solr
				//itemSearchService.importList(itemList);	
				final String jsonString = JSON.toJSONString(itemList);//转换为json传输
				System.out.println("Goods conroller: " + jsonString);
				Topic topicSolrDestination = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
				jmsTemplate.send(topicSolrDestination, new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						//System.out.println("发送消息到search服务"+jsonString);
						return  session.createTextMessage(jsonString);
					}
				});
				
				//****生成商品详细页
				for(final Long goodsId:ids){
					Topic topicPageDestination = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
					jmsTemplate.send(topicPageDestination, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {							
							return  session.createTextMessage(goodsId+"");
						}
					});					
				}
				
			}		
			
			return new Result(true, "修改状态成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改状态失败");
		}
	}
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public List<Goods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping(value="/findPage",method=RequestMethod.GET)
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestBody TbGoods goods){
		System.out.println("进行添加商品操作");
		System.out.println("#######"+goods.getGoods().getGoodsName()+"#######");
		System.out.println("#######"+goods.getGoodsDesc().getItemImages());
		System.out.println("#######"+goods.getGoodsDesc().getSpecificationItems());
		System.out.println("#######"+goods.getGoodsDesc().getCustomAttributeItems());
		List<Item> itemsList = goods.getItemList();
		for (Item item : itemsList) {
			System.out.println("#######"+item.toString());
		}
		
		try {
			// 获得商家信息:
			//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			String sellerId = "yqtech"; //暂时设定
			goods.getGoods().setSellerId(sellerId);
			
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result update(@RequestBody TbGoods goods){
		// 获得商家信息:
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		String sellerId = "yqtech"; //暂时设定
		TbGoods goods2 = goodsService.findOne(goods.getGoods().getId());
		if(!sellerId.equals(goods2.getGoods().getSellerId()) || !sellerId.equals(goods.getGoods().getSellerId())){
			return new Result(false, "非法操作");
		}
		
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findOne",method=RequestMethod.GET)
	public TbGoods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
