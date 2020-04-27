package com.yq.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yq.search.service.ItemSearchService;
import com.yq.shopping.po.Item;
@Service
@Transactional
public class ItemSearchServiceImpl implements ItemSearchService {
	
	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public Map search(Map searchMap) {
		Map map = new HashMap();
		Query query = new SimpleQuery("*:*");
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		ScoredPage<Item> page = solrTemplate.queryForPage("collection1", query, Item.class);
		
		map.put("rows", page.getContent());
		
		return map;
	}

	@Override
	public void importList(List list) {
		solrTemplate.saveBeans("collection1", list);
		solrTemplate.commit("collection1");
	}

	@Override
	public void deleteByGoodsIds(List goodsIds) {
		SimpleQuery query = new SimpleQuery("*:*");
		Criteria criteria=new Criteria("item_goodsid").in(goodsIds);
		query.addCriteria(criteria);
		solrTemplate.delete("collection1", query);
		solrTemplate.commit("collection1");
	}

}
