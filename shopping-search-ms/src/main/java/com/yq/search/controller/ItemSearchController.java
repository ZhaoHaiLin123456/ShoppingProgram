package com.yq.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yq.search.service.ItemSearchService;
import com.yq.shopping.mapper.ItemMapper;

@RestController
@RequestMapping("/itemsearch-ms")
public class ItemSearchController {

	@Autowired
	private ItemSearchService itemSearchService;

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public Map search(@RequestBody Map searchMap) {
		return itemSearchService.search(searchMap);
	}



}
