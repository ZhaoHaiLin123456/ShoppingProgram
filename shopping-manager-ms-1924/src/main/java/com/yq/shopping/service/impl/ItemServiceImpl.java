package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.ItemMapper;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.ItemExample;
import com.yq.shopping.po.ItemExample.Criteria;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public List<Item> findAll() {
		return itemMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Item> page = (Page<Item>) itemMapper.selectByExample(null);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void add(Item item) {
		int insert = itemMapper.insert(item);
	}

	@Override
	public void update(Item item) {
		itemMapper.updateByPrimaryKey(item);
	}

	@Override
	public Item findOne(Long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			itemMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(Item item, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ItemExample example = new ItemExample();
		Criteria criteria = example.createCriteria();
		if (item != null) {
			if (item.getTitle() != null && item.getTitle().length() > 0) {
				criteria.andTitleLike("%" + item.getTitle() + "%");
			}
			if (item.getSellPoint() != null && item.getSellPoint().length() > 0) {
				criteria.andSellPointLike("%" + item.getSellPoint() + "%");
			}
			if (item.getBarcode() != null && item.getBarcode().length() > 0) {
				criteria.andBarcodeLike("%" + item.getBarcode() + "%");
			}
			if (item.getImage() != null && item.getImage().length() > 0) {
				criteria.andImageLike("%" + item.getImage() + "%");
			}
			if (item.getStatus() != null && item.getStatus().length() > 0) {
				criteria.andStatusLike("%" + item.getStatus() + "%");
			}
			if (item.getItemSn() != null && item.getItemSn().length() > 0) {
				criteria.andItemSnLike("%" + item.getItemSn() + "%");
			}
			if (item.getIsDefault() != null && item.getIsDefault().length() > 0) {
				criteria.andIsDefaultLike("%" + item.getIsDefault() + "%");
			}
			if (item.getSellerId() != null && item.getSellerId().length() > 0) {
				criteria.andSellerIdLike("%" + item.getSellerId() + "%");
			}
			if (item.getCartThumbnail() != null && item.getCartThumbnail().length() > 0) {
				criteria.andCartThumbnailLike("%" + item.getCartThumbnail() + "%");
			}
			if (item.getCategory() != null && item.getCategory().length() > 0) {
				criteria.andCategoryLike("%" + item.getCategory() + "%");
			}
			if (item.getBrand() != null && item.getBrand().length() > 0) {
				criteria.andBrandLike("%" + item.getBrand() + "%");
			}
			if (item.getSpec() != null && item.getSpec().length() > 0) {
				criteria.andSpecLike("%" + item.getSpec() + "%");
			}
			if (item.getSeller() != null && item.getSeller().length() > 0) {
				criteria.andSellerLike("%" + item.getSeller() + "%");
			}
		}
		Page<Item> page = (Page<Item>) itemMapper.selectByExample(example);
		return new PageResult(page.getResult(), page.getTotal());
	}

}
