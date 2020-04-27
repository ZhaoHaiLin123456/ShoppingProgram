package com.yq.shopping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.po.TypeTemplate;
import com.yq.shopping.service.impl.TypeTemplateService;

@RestController
public class TypeTemplateController {

	@Autowired
	private TypeTemplateService typeTemplateService;
	
	/**查询所有信息**/
	@RequestMapping(value="/findAllBrandlList",method=RequestMethod.GET)
	public List<TypeTemplate> findAllBrandlList(){
		return typeTemplateService.findAllTypeTemplate();
		
	}

	/**条件查询**/
	@RequestMapping(value="/findTypeTemplateBy",method=RequestMethod.GET)
	public List<TypeTemplate> findTypeTemplateBy(TypeTemplate typeTemplate){
		return null;
		
	}

	/**根据id查询**/
	@RequestMapping(value="/findTypeTemplateById/{id}",method=RequestMethod.GET)
	TypeTemplate findTypeTemplateById(@PathVariable Long id) {
		return typeTemplateService.findTypeTemplateById(id);
	}

	/**分页查询**/
	@RequestMapping(value="/findByPageTypeTemplate",method=RequestMethod.GET)
	public PageResult findByPageTypeTemplate(int pageNo, int pageSize) {
		return typeTemplateService.findByPageTypeTemplate(pageNo, pageSize);
	}

	/**修改功能**/
	@RequestMapping(value="/updateTypeTemplate",method=RequestMethod.POST)
	public Result updateTypeTemplate(@RequestBody TypeTemplate typeTemplate) {
		try {
			typeTemplateService.updateTypeTemplate(typeTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
		
	}

	/**添加**/
	@RequestMapping(value="/addTypeTemplate",method=RequestMethod.POST)
	public Result addTypeTemplate(@RequestBody TypeTemplate typeTemplate) {
		try {
			typeTemplateService.addTypeTemplate(typeTemplate);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}

	/**多选删除功能**/
	@RequestMapping(value="/delManyTypeTemplate",method=RequestMethod.GET)
	public Result delManyTypeTemplate(Long[] ids) {
		try {
			int num = typeTemplateService.delManyTypeTemplate(ids);
			Result result = new Result(true, "删除成功");
			result.setNum(num);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	@RequestMapping(value="/findPage",method=RequestMethod.POST)
	public PageResult findPage(@RequestBody TypeTemplate typeTemplate, int pageNum,int pageSize) {
		PageResult pageResult = typeTemplateService.findPage(typeTemplate, pageNum, pageSize);
		return pageResult;
	}
	
	/**根据模板ID查询规格列表**/
	@RequestMapping(value="/findBySpecList",method=RequestMethod.GET)
	public List<Map> findSpecList(Long id){
		return typeTemplateService.findSpecList(id);
	}



}
