package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.ItemCatMapper;
import com.yq.shopping.po.ItemCat;
import com.yq.shopping.po.ItemCatExample;
import com.yq.shopping.po.ItemCatExample.Criteria;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.ItemCatService;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public List<ItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<ItemCat> page = (Page<ItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void add(ItemCat itemCat) {
		itemCatMapper.insert(itemCat);

	}

	@Override
	public void update(ItemCat itemCat) {
		itemCatMapper.updateByPrimaryKey(itemCat);

	}

	@Override
	public ItemCat findOne(Long id) {
		return itemCatMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			itemCatMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(ItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ItemCatExample example = new ItemCatExample();
		Criteria criteria = example.createCriteria();
		if (itemCat != null) {
			if (itemCat.getName() != null && itemCat.getName().length() > 0) {
				criteria.andNameLike("%" + itemCat.getName() + "%");
			}
		}
		Page<ItemCat> page = (Page<ItemCat>) itemCatMapper.selectByExample(example);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public List<ItemCat> findByParentId(Long parentId) {
		ItemCatExample example=new ItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		return list;
	}

}
