package com.yq.shopping.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yq.shopping.po.Brand;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.service.BrandService;

@RestController
public class BrandController {

	@Autowired
	private BrandService brandService;

	@RequestMapping(value="/findBrandsList",method=RequestMethod.GET)
	public List<Brand> findBrandsList(){
		List<Brand> list = brandService.findAllBrandlList();
		System.out.println(list);
		return list;
	}
	
	@RequestMapping(value="/findBrandById/{id}",method=RequestMethod.GET)
	public Brand findBrandById(@PathVariable Long id) {
		System.out.println("id"+id);
		Brand brand = brandService.findBrandById(id);
//		System.out.println(brand.toString());
		return brand;		
	}
	
	@RequestMapping(value="/findByPageBrand",method=RequestMethod.GET)
	public PageResult findByPageBrand(int pageNo, int pageSize) {
		PageResult pageResult = brandService.findByPageBrand(pageNo, pageSize);
		return pageResult;	
	}
	
	@RequestMapping(value="/updateBrand",method=RequestMethod.POST)
	public Result updateBrand(@RequestBody Brand brand) {
		Result result = new Result();
		int num = brandService.updateBrand(brand);
		if (num>0) {
			System.out.println("更新成功！");
			result.setSuccess(true);
			result.setNum(num);
		}else {
			result.setSuccess(false);
			result.setNum(num);
		}
		return result;
	}
	
	@RequestMapping(value="/addBrand",method=RequestMethod.POST)
	public Result addBrand(@RequestBody Brand brand) 	{
		Result result = new Result();
		int num = brandService.addBrand(brand);
		if (num>0) {
			System.out.println("添加成功！");
			result.setSuccess(true);
			result.setNum(num);
		}else {
			result.setSuccess(false);
			result.setNum(num);
		}
		return result;
	}

	@RequestMapping(value="/delBrand/{id}",method=RequestMethod.GET)
	public Result delBrand(@PathVariable Long id) {	
		Result result = new Result();
		int num = brandService.delBrand(id);
		if (num>0) {
			System.out.println("删除成功！");
			result.setSuccess(true);
			result.setNum(num);
		}else {
			result.setSuccess(false);
			result.setNum(num);
		}
		return result;
	}

	@RequestMapping(value="/delManyBrand",method=RequestMethod.GET)
	public Result delManyBrand(Long[] ids) {
		Result result = new Result();
		int num = brandService.delManyBrand(ids);
		if (num>0) {
			System.out.println("删除成功！");
			result.setSuccess(true);
			result.setNum(num);
		}else {
			result.setSuccess(false);
			result.setNum(num);
		}
		return result;
	}
	
	@RequestMapping(value="/selectBandOptionList",method=RequestMethod.GET)
	public List<Map> selectBandOptionList(){
		return brandService.selectOptionList();
	}
	

}
