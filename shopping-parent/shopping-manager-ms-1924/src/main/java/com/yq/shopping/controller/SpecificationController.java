package com.yq.shopping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.po.Specification;
import com.yq.shopping.po.SpecificationOption;
import com.yq.shopping.po.TbSpecification;
import com.yq.shopping.service.SpecificationService;

@RestController
public class SpecificationController {

	@Autowired
	private SpecificationService specificationService;
	
	/**根据规查询Specifitions**/
	@RequestMapping(value="/findAllSpecificationsList",method=RequestMethod.GET)
	public List<Specification> findAllSpecificationsList(){
		List<Specification> specificationsList = specificationService.findAllSpecificationsList();
		for (Specification specification : specificationsList) {
			System.out.println(specification);
		}
		return specificationsList;
	}

	
	/**编辑数据带回:根据规格id查询TbSpecifitions**/
	@RequestMapping(value="/findSpecificationsOptionById/{id}",method=RequestMethod.GET)
	public TbSpecification findSpecificationsOptionById(@PathVariable Long id) {
		TbSpecification tbSpecification = specificationService.findSpecificationsOptionById(id);
		Specification specification = tbSpecification.getSpecification();
		System.out.println(specification.toString());
		List<SpecificationOption> optionList = tbSpecification.getSpecificationOptionList();
		for (SpecificationOption specificationOption : optionList) {
			System.out.println("specificationOption="+specificationOption);
		}
		return tbSpecification;
	}
	
	/**分页查询**/
	@RequestMapping(value="/findByPageSpecification",method=RequestMethod.GET)
	public PageResult findByPageSpecification(int pageNo, int pageSize) {
		return specificationService.findByPageSpecification(pageNo, pageSize);
	}
	
	/**添加**/
	@RequestMapping(value="/addTbSpecification",method=RequestMethod.POST)
	public Result addTbSpecification(@RequestBody TbSpecification tbSpecification) {
		try {
			specificationService.addTbSpecification(tbSpecification);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	/**删除规格信息+该规格option的所有信息**/
	@RequestMapping(value="/delTbSpecificationsById",method=RequestMethod.GET)
	public Result delTbSpecificationsById(Long[] ids) {
		
		try {
			specificationService.delTbSpecificationsById(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}

	/**修改功能**/
	@RequestMapping(value="/updateTbSpecification",method=RequestMethod.POST)
	public Result updateTbSpecification(@RequestBody TbSpecification tbSpecification) {

		List<SpecificationOption> optionList = tbSpecification.getSpecificationOptionList();
		Specification specification = tbSpecification.getSpecification();
		
		System.out.println(specification.toString());
		for (SpecificationOption specificationOption : optionList) {
			System.out.println(specificationOption.toString());
		}
		System.out.println("进入contrller层************************************************");
		try {
			specificationService.updateTbSpecification(tbSpecification);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	@RequestMapping(value="/selectSpecificationOptionList",method=RequestMethod.GET)
	public List<Map> selectSpecificationOptionList(){
		return specificationService.selectOptionList();
	}
	
	
	
}
