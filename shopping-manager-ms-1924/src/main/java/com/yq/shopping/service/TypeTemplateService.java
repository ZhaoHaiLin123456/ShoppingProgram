package com.yq.shopping.service;

import java.util.List;
import java.util.Map;

import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.TypeTemplate;

public interface TypeTemplateService {

	/**查询所有信息**/
	public List<TypeTemplate> findAllTypeTemplate();

	/**条件查询**/
	public List<TypeTemplate> findTypeTemplateBy(TypeTemplate typeTemplate);

	/**根据id查询**/
	TypeTemplate findTypeTemplateById(Long id);

	/**分页查询**/
	public PageResult findByPageTypeTemplate(int pageNo, int pageSize);

	/**修改功能**/
	public int updateTypeTemplate(TypeTemplate typeTemplate);

	/**添加**/
	public int addTypeTemplate(TypeTemplate typeTemplate);

	/**多选删除功能**/
	public int delManyTypeTemplate(Long[] ids);
	
	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TypeTemplate typeTemplate, int pageNum,int pageSize);
	
	/**根据模板ID查询规格列表**/
	public List<Map> findSpecList(Long id);
}
