package com.yq.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.BrandMapper;
import com.yq.shopping.po.Brand;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.BrandService;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;
	
	@Override
	public List<Brand> findAllBrandlList() {
		List<Brand> list = brandMapper.selectByExample(null);
		System.out.println(list);
		return list;
	}

	@Override
	public List<Brand> findBrandBy(Brand brand) {
		return null;
	}

	@Override
	public Brand findBrandById(Long id) {
		Brand brand = brandMapper.selectByPrimaryKey(id);
		return brand;
	}

	@Override
	public PageResult findByPageBrand(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(null);
		PageResult pageResult=new PageResult();
		pageResult.setRows(page.getResult());
		pageResult.setTotal(page.getTotal());
		return pageResult;
	}

	@Override
	public int updateBrand(Brand brand) {
		int result = brandMapper.updateByPrimaryKey(brand);
		System.out.println("更新成功"+result+"条信息");
		return result;
	}

	@Override
	public int addBrand(Brand brand) {
		int result = brandMapper.insert(brand);
		System.out.println("添加成功"+result+"条信息");
		return result;
	}

	@Override
	public int delBrand(Long id) {
		int result = brandMapper.deleteByPrimaryKey(id);
		System.out.println("删除成功"+result+"条信息");
		return result;
	}

	@Override
	public int delManyBrand(Long[] ids) {
		int result = 0;
		for (Long id : ids) {
			result += brandMapper.deleteByPrimaryKey(id);
		}
		System.out.println("删除成功"+result+"条信息");
		return result;
	}
	
	@Override
	public List<Map> selectOptionList() {
		return brandMapper.selectOptionList();
	}

}
