package com.yq.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yq.shopping.po.Item;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Result;
import com.yq.shopping.service.ItemService;

@RestController
@RequestMapping("/item-ms")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public List<Item> findAll(){			
		return itemService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping(value="/findPage",method=RequestMethod.GET)
	public PageResult  findPage(int page,int rows){			
		return itemService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param item
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result add(@RequestBody Item item){
		try {
			itemService.add(item);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param item
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result update(@RequestBody Item item){
		try {
			itemService.update(item);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findOne",method=RequestMethod.GET)
	public Item findOne(Long id){
		return itemService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Result delete(Long [] ids){
		try {
			itemService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public PageResult search(@RequestBody Item item, int page, int rows  ){
		return itemService.findPage(item, page, rows);		
	}
	
}
