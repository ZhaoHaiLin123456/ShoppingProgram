package com.yq.shopping.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.yq.shopping.mapper.GoodsDescMapper;
import com.yq.shopping.mapper.GoodsMapper;
import com.yq.shopping.mapper.ItemCatMapper;
import com.yq.shopping.mapper.ItemMapper;
import com.yq.shopping.po.Goods;
import com.yq.shopping.po.GoodsDesc;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.ItemCat;
import com.yq.shopping.po.ItemExample;
import com.yq.shopping.po.ItemExample.Criteria;
import com.yq.shopping.service.ItemPageService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class ItemPageServiceImpl implements ItemPageService {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	// @Value("${pagedir}")
	private String pagedir = "f:\\item\\";
	
	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private GoodsDescMapper goodsDescMapper;

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public boolean genItemHtml(Long goodsId) {

		Configuration configuration = freeMarkerConfigurer.getConfiguration();

		try {
			Template template = configuration.getTemplate("item.ftl");
			// 创建数据模型
			Map dataModel = new HashMap<>();
			// 1、商品主表数据
			Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);
			// 2、商品扩展数据
			GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			// 3、读取商品分类
			ItemCat itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id());
			ItemCat itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id());
			ItemCat itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);
			// 4、读取SKU列表

			ItemExample example = new ItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);// SPU ID
			criteria.andStatusEqualTo("1");// 状态有效，已经上架的商品才能导出来
			example.setOrderByClause("is_default desc");// 按是否默认字段进行排序，目的是返回的结果第一条为默认SKU

			List<Item> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);

			Writer out = new FileWriter(pagedir + goodsId + ".html");

			template.process(dataModel, out);// 输出
			out.close();
			return true;

		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();		
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
