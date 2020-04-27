package com.yq.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yq.shopping.po.Content;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.service.ContentService;

@RestController
@RequestMapping("/content-ms")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	
	

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public List<Content> findAll(){
		return contentService.findAll();
	}
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	@RequestMapping(value="/findPage",method=RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		PageResult findPage = contentService.findPage(pageNum, pageSize);
		return findPage;
		
	}
	
	
	/**
	 * 增加
	*/
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestBody Content content) {
		try {
			int num = contentService.add(content);
			return new Result(true, "添加成功", num);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result update(@RequestBody Content content) {
		try {
			int num = contentService.update(content);
			return new Result(true, "修改成功", num);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findOne/{id}",method=RequestMethod.GET)
	public Content findOne(@PathVariable Long id) {
		return contentService.findOne(id);
	}
	
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Result delete(Long [] ids) {
		try {
			int num = contentService.delete(ids);
			return new Result(true, "删除成功", num);
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
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public PageResult search(@RequestBody Content content, int pageNum,int pageSize) {
		return contentService.findPage(content, pageNum, pageSize);
	}
	
	@RequestMapping(value="/findByCategoryId",method=RequestMethod.GET)
	public List<Content> findByCategoryId(Long categoryId){
		return contentService.findByCategoryId(categoryId);
	}
	
}
