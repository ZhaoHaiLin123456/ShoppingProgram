package com.yq.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.SpecificationOptionMapper;
import com.yq.shopping.mapper.TypeTemplateMapper;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.SpecificationOption;
import com.yq.shopping.po.SpecificationOptionExample;
import com.yq.shopping.po.TypeTemplate;
import com.yq.shopping.po.TypeTemplateExample;
import com.yq.shopping.po.TypeTemplateExample.Criteria;
@Service
@Transactional
public class TypeTemplateService implements com.yq.shopping.service.TypeTemplateService {

	@Autowired
	private TypeTemplateMapper typeTemplateMapper;
	
	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;
	
	@Override
	public List<TypeTemplate> findAllTypeTemplate() {
		return typeTemplateMapper.selectByExample(null);
	}

	@Override
	public List<TypeTemplate> findTypeTemplateBy(TypeTemplate typeTemplate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeTemplate findTypeTemplateById(Long id) {
		
		TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		System.out.println("service层");
		System.err.println(typeTemplate.toString());
		return typeTemplate;
	}

	@Override
	public PageResult findByPageTypeTemplate(int pageNo, int pageSize) {
		System.out.println("service层");
		System.err.println("pageNo="+pageNo);
		System.err.println("pageSize="+pageSize);
		PageHelper.startPage(pageNo, pageSize);
		Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(null);
		PageResult pageResult=new PageResult();
		pageResult.setRows(page.getResult());
		pageResult.setTotal(page.getTotal());
		return pageResult;
	}

	@Override
	public int updateTypeTemplate(TypeTemplate typeTemplate) {
		int result = typeTemplateMapper.updateByPrimaryKey(typeTemplate);
		return result;
	}

	@Override
	public int addTypeTemplate(TypeTemplate typeTemplate) {
		int insert = typeTemplateMapper.insert(typeTemplate);
		return insert;
	}

	@Override
	public int delManyTypeTemplate(Long[] ids) {
		int delResult=0;
		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			delResult = typeTemplateMapper.deleteByPrimaryKey(id);
		}
		return delResult;
	}

	/**
	 * 带条件分页查询
	 */
	@Override
	public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
		System.out.println("service层");
		System.err.println("pageNum="+pageNum);
		System.err.println("pageSize="+pageSize);
		
		PageHelper.startPage(pageNum, pageSize);
		TypeTemplateExample example=new TypeTemplateExample();
		Criteria criteria = example.createCriteria();
		if (typeTemplate!=null) {
			if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
		}
		Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(example);
		PageResult pageResult=new PageResult(page.getResult(), page.getTotal());
		return pageResult;
	}

	/**
	 * 根据模板ID查询规格列表
	 */
	@Override
	public List<Map> findSpecList(Long id) {
		//根据ID查询到模板对象
		TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		// 获得规格的数据spec_ids
		String specIds = typeTemplate.getSpecIds();// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
		// 将specIds的字符串转成JSON的List<Map>
		List<Map> list = JSON.parseArray(specIds, Map.class);
		// 获得每条记录:
		for (Map map : list) {
			// 根据规格的ID 查询规格选项的数据:
			// 设置查询条件:
			SpecificationOptionExample example = new SpecificationOptionExample();
			SpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));
			
			List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
		
			map.put("options", specOptionList);//{"id":27,"text":"网络",options:[{id：xxx,optionName:移动2G}]}
		}
		return list;
	}

}
