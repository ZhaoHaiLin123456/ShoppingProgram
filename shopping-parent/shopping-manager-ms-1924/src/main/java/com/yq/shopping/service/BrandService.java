package com.yq.shopping.service;

import java.util.List;
import java.util.Map;

import com.yq.shopping.po.Brand;
import com.yq.shopping.po.PageResult;


public interface BrandService {

	/**查询所有新闻信息**/
	public List<Brand> findAllBrandlList();

	/**条件查询**/
	public List<Brand> findBrandBy(Brand brand);

	/**根据id查询Brand**/
	Brand findBrandById(Long id);

	/**分页查询**/
	public PageResult findByPageBrand(int pageNo, int pageSize);

	/**修改Brand功能**/
	public int updateBrand(Brand brand);

	/**添加**/
	public int addBrand(Brand brand);

	public int delBrand(Long id);

	/**多选删除功能**/
	public int delManyBrand(Long[] ids);

	public List<Map> selectOptionList();
}
