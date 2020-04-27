package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.GoodsDescMapper;
import com.yq.shopping.po.GoodsDesc;
import com.yq.shopping.po.GoodsDescExample;
import com.yq.shopping.po.GoodsDescExample.Criteria;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.GoodsDescService;

@Service
@Transactional
public class GoodsDescServiceImpl implements GoodsDescService {

	@Autowired
	private GoodsDescMapper goodsDescMapper;

	@Override
	public List<GoodsDesc> findAll() {
		return goodsDescMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<GoodsDesc> page = (Page<GoodsDesc>) goodsDescMapper.selectByExample(null);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void add(GoodsDesc goodsDesc) {
		goodsDescMapper.insert(goodsDesc);

	}

	@Override
	public void update(GoodsDesc goodsDesc) {
		goodsDescMapper.updateByPrimaryKey(goodsDesc);
	}

	@Override
	public GoodsDesc findOne(Long id) {
		return goodsDescMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			goodsDescMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(GoodsDesc goodsDesc, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		GoodsDescExample example = new GoodsDescExample();
		Criteria criteria = example.createCriteria();
		if (goodsDesc != null) {
			if (goodsDesc.getIntroduction() != null && goodsDesc.getIntroduction().length() > 0) {
				criteria.andIntroductionLike("%" + goodsDesc.getIntroduction() + "%");
			}
			if (goodsDesc.getSpecificationItems() != null && goodsDesc.getSpecificationItems().length() > 0) {
				criteria.andSpecificationItemsLike("%" + goodsDesc.getSpecificationItems() + "%");
			}
			if (goodsDesc.getCustomAttributeItems() != null && goodsDesc.getCustomAttributeItems().length() > 0) {
				criteria.andCustomAttributeItemsLike("%" + goodsDesc.getCustomAttributeItems() + "%");
			}
			if (goodsDesc.getItemImages() != null && goodsDesc.getItemImages().length() > 0) {
				criteria.andItemImagesLike("%" + goodsDesc.getItemImages() + "%");
			}
			if (goodsDesc.getPackageList() != null && goodsDesc.getPackageList().length() > 0) {
				criteria.andPackageListLike("%" + goodsDesc.getPackageList() + "%");
			}
			if (goodsDesc.getSaleService() != null && goodsDesc.getSaleService().length() > 0) {
				criteria.andSaleServiceLike("%" + goodsDesc.getSaleService() + "%");
			}
		}
		Page<GoodsDesc> page = (Page<GoodsDesc>) goodsDescMapper.selectByExample(example);
		return new PageResult(page.getResult(), page.getTotal());
	}

}
