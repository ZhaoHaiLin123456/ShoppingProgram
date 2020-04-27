package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.SpecificationMapper;
import com.yq.shopping.mapper.SpecificationOptionMapper;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.SpecificationOption;
import com.yq.shopping.po.SpecificationOptionExample;
import com.yq.shopping.po.SpecificationOptionExample.Criteria;
import com.yq.shopping.service.SpecificationOptionService;

@Service
@Transactional
public class SpecificationOptionServiceImpl implements SpecificationOptionService {

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	@Override
	public List<SpecificationOption> findAll() {
		return specificationOptionMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<SpecificationOption> page = (Page<SpecificationOption>) specificationOptionMapper.selectByExample(null);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void add(SpecificationOption specificationOption) {
		specificationOptionMapper.insert(specificationOption);
	}

	@Override
	public void update(SpecificationOption specificationOption) {
		specificationOptionMapper.updateByPrimaryKey(specificationOption);
	}

	@Override
	public SpecificationOption findOne(Long id) {
		return specificationOptionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			specificationOptionMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(SpecificationOption specificationOption, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		SpecificationOptionExample example=new SpecificationOptionExample();
		Criteria criteria = example.createCriteria();
		if (specificationOption!=null) {
			if (specificationOption.getOptionName()!=null&!specificationOption.getOptionName().equals("")) {
				criteria.andOptionNameLike("%"+specificationOption.getOptionName()+"%");
			}
		}
		Page<SpecificationOption> page = (Page<SpecificationOption>) specificationOptionMapper.selectByExample(example);
		return new PageResult(page.getResult(), page.getTotal());
	}

}
