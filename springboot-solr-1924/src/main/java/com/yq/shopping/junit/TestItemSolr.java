package com.yq.shopping.junit;
import java.util.Arrays;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yq.shopping.AppSolr;
import com.yq.shopping.mapper.ItemMapper;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.ItemExample;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {AppSolr.class})
public class TestItemSolr {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	
	@Test
	public void testDeleteAllSolr() {
		SimpleQuery simpleQuery = new SimpleQuery("*:*");
		
		solrTemplate.delete("collection1", simpleQuery);
		solrTemplate.commit("collection1");
		System.out.println("操作完成");
	}
	
	@Test
	public void testImportManyObject(){
		List<Item> list = itemMapper.selectByExample(null);
		System.out.println(list.toString());
		solrTemplate.saveBeans("collection1", list);
		solrTemplate.commit("collection1");
		System.out.println("操作完成");
	}
	
	@Test
	public void testQuery() {
		SimpleQuery simpleQuery = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("item_title").endsWith("电脑");
		simpleQuery.setRows(1000);
		simpleQuery.addCriteria(criteria);

		Page<Item> page = solrTemplate.query("collection1", simpleQuery, Item.class);
		List<Item> list = page.getContent();
		for (Item item : list) {
			System.out.println(item.getId()+","+item.getBrand()+","+item.getImage());
		}
	
	}
	
	@Test
	public void testiTemMapper() {
		System.out.println("测试");
		ItemExample example = new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("0");// 状态
		criteria.andGoodsIdEqualTo(149187842867983L);
		List<Item> list = itemMapper.selectByExample(example);
		for (Item item : list) {
			System.out.println(item.toString());
		}
	}
	
}
