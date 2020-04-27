package com.yq.shopping.service;

import java.util.List;
import java.util.Map;

import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Specification;
import com.yq.shopping.po.SpecificationOption;
import com.yq.shopping.po.TbSpecification;

public interface SpecificationService {

	
	/**根据规格id查询Specifitions**/
	public List<Specification> findAllSpecificationsList();

	
	/**编辑数据带回:根据规格id查询TbSpecifitions**/
	public TbSpecification findSpecificationsOptionById(Long id);
	
	/**分页查询**/
	public PageResult findByPageSpecification(int pageNo, int pageSize);
	
	/**添加**/
	public void addTbSpecification(TbSpecification tbSpecification);
	
	/**删除规格信息+该规格option的所有信息**/
	public void delTbSpecificationsById(Long[] ids);

	/**修改功能**/
	public void updateTbSpecification(TbSpecification tbSpecification);


	public List<Map> selectOptionList();
	
	
}
