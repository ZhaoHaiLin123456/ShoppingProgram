package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.ContentCategoryMapper;
import com.yq.shopping.po.ContentCategory;
import com.yq.shopping.po.ContentCategoryExample;
import com.yq.shopping.po.ContentCategoryExample.Criteria;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.ContentCategoryService;
@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService{

	
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<ContentCategory> findAll() {
		List<ContentCategory> list = contentCategoryMapper.selectByExample(null);
		return list;
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<ContentCategory> page = (Page<ContentCategory>) contentCategoryMapper.selectByExample(null);
		PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
		return pageResult;
	}

	@Override
	public int add(ContentCategory contentCategory) {
		int	insert = contentCategoryMapper.insert(contentCategory);
		return insert;
	}

	@Override
	public int update(ContentCategory contentCategory) {
		return contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}

	@Override
	public ContentCategory findOne(Long id) {
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(Long[] ids) {
		int delNum =0;
		for (Long id : ids) {
			delNum += contentCategoryMapper.deleteByPrimaryKey(id);
		}
		return delNum;
	}

	@Override
	public PageResult findPage(ContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ContentCategoryExample example=new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		if (contentCategory!=null) {
			if (contentCategory.getId()!=null) {
				criteria.andIdEqualTo(contentCategory.getId());
			}
			if (contentCategory.getName()!=null&&!contentCategory.getName().equals("")) {
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
		}
		Page<ContentCategory> page = (Page<ContentCategory>) contentCategoryMapper.selectByExample(example);
		PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
		return pageResult;
	}

}
