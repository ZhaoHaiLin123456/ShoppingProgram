package com.yq.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.yq.shopping.po.ContentCategory;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.service.ContentCategoryService;

@RestController
@RequestMapping("/content-category-ms")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping(value="/findAllContentCategory",method=RequestMethod.GET)
	public List<ContentCategory> findAllContentCategory(){
		return contentCategoryService.findAll();
	}
	
	
	@RequestMapping(value="/findPage",method=RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		return contentCategoryService.findPage(pageNum, pageSize);
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestBody ContentCategory contentCategory) {
		int num =0;
		try {
			 num = contentCategoryService.add(contentCategory);
			return new Result(true, "添加成功", num);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败", num);
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result update(@RequestBody ContentCategory contentCategory) {
		int num =0;
		try {
			 num = contentCategoryService.update(contentCategory);
			return new Result(true, "更新成功", num);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "更新失败", num);
		}
	}
	

	@RequestMapping(value="/findOne/{id}",method=RequestMethod.GET)
	public ContentCategory findOne(@PathVariable Long id) {
		return contentCategoryService.findOne(id);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Result delete(Long [] ids) {
		int num =0;
		try {
			 num = contentCategoryService.delete(ids);
			return new Result(true, "删除成功", num);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败", num);
		}
	}

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public PageResult search(@RequestBody ContentCategory contentCategory, int pageNum,int pageSize) {
		System.out.println(contentCategory.toString());
		return contentCategoryService.findPage(contentCategory, pageNum, pageSize);
	}
}
