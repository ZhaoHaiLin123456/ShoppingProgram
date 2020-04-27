package com.yq.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.SpecificationMapper;
import com.yq.shopping.mapper.SpecificationOptionMapper;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Specification;
import com.yq.shopping.po.SpecificationOption;
import com.yq.shopping.po.SpecificationOptionExample;
import com.yq.shopping.po.SpecificationOptionExample.Criteria;
import com.yq.shopping.po.TbSpecification;
import com.yq.shopping.service.SpecificationService;

@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	/**
	 * 主页面
	 */
	@Override
	public List<Specification> findAllSpecificationsList() {
		List<Specification> list = specificationMapper.selectByExample(null);
		return list;
	}

	/**
	 * 编辑数据带回:根据规格id查询TbSpecifitions
	 */
	@Override
	public TbSpecification findSpecificationsOptionById(Long id) {
		TbSpecification tbSpecification = new TbSpecification();
		Specification specification = specificationMapper.selectByPrimaryKey(id);
		// 根据规格id，查询所有的规格选项id
		SpecificationOptionExample example = new SpecificationOptionExample();
		Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
		tbSpecification.setSpecification(specification);
		tbSpecification.setSpecificationOptionList(specificationOptions);
		return tbSpecification;
	}

	@Override
	public PageResult findByPageSpecification(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);
		PageResult pageResult = new PageResult();
		pageResult.setRows(page.getResult());
		pageResult.setTotal(page.getTotal());
		return pageResult;
	}

	@Override
	public void addTbSpecification(TbSpecification tbSpecification) {
		System.out.println(tbSpecification.toString());
		// 保存规格一方的数据
		int insertSpecification = specificationMapper.insert(tbSpecification.getSpecification());
		// 保存规格选项多方数据
		List<SpecificationOption> optionList = tbSpecification.getSpecificationOptionList();
		System.out.println(optionList.toString());
		for (SpecificationOption specificationOption : optionList) {
			// 设置规格id
			System.out.println(specificationOption.toString());
			specificationOption.setSpecId(tbSpecification.getSpecification().getId());
			specificationOptionMapper.insert(specificationOption);
		}
	}

	@Override
	public void delTbSpecificationsById(Long[] ids) {
		
		for (int i = 0; i < ids.length; i++) {
			// 根据规格id，查询所有的规格选项id
			SpecificationOptionExample example = new SpecificationOptionExample();
			Criteria criteria = example.createCriteria();
			System.out.println(ids[i]);
			criteria.andSpecIdEqualTo(ids[i]);
			List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
			for (SpecificationOption specificationOption : specificationOptions) {
				System.out.println(specificationOption.toString());
				specificationOptionMapper.deleteByPrimaryKey(specificationOption.getId());
			}
			// 删除一方的规格数据
			specificationMapper.deleteByPrimaryKey(ids[i]);		
		}
	}

	@Override
	public void updateTbSpecification(TbSpecification tbSpecification) {
	
		
//		List<SpecificationOption> optionList = tbSpecification.getSpecificationOptionList();
//		Specification specification = tbSpecification.getSpecification();
//		
//		System.out.println(specification.toString());
//		for (SpecificationOption specificationOption : optionList) {
//			System.out.println(specificationOption.toString());
//		}
//		
//		System.out.println("进入service层************************************************");
//		System.out.println("service层代码");
		int insert=0;
		
		// 1、规格信息一方信息更新
		specificationMapper.updateByPrimaryKey(tbSpecification.getSpecification());
		// 2、先删除所有规格选项信息，然后将页面数据信息重新添加
		// 2-1根据规格id，查询所有的规格选项id
		SpecificationOptionExample example = new SpecificationOptionExample();
		Criteria criteria = example.createCriteria();
		
		System.out.println("id为=:"+tbSpecification.getSpecification().getId());
		
		criteria.andSpecIdEqualTo(tbSpecification.getSpecification().getId());
		List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
		for (SpecificationOption spec : specificationOptions) {
			specificationOptionMapper.deleteByPrimaryKey(spec.getId());
		}
		//2-2重新添加
		List<SpecificationOption> options = tbSpecification.getSpecificationOptionList();
		for (SpecificationOption specificationOption : options) {
			//插入外键
			specificationOption.setSpecId(tbSpecification.getSpecification().getId());
			insert+= specificationOptionMapper.insert(specificationOption);
		}
		System.out.println("重新添加"+insert+"条数据");
	}
	
	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}
	
	
}
