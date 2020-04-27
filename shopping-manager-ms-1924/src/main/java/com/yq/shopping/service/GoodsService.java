package com.yq.shopping.service;

import java.util.List;

import com.yq.shopping.po.Goods;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.TbGoods;

public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Goods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	 * 设置审核状态
	 * 插入spu商品信息
	 * 插入sku商品信息
	*/
	public void add(TbGoods goods);
	
	
	/**
	 * 修改
	 * 
	 */
	public void update(TbGoods goods);
	

	/**
	 * 根据ID获取实体：spu和sku
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
	/**
	 * 批量删除 
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Goods goods, int pageNum,int pageSize);
	
	/**
	 * 更新spu产品状态（产品上架）
	 * @param ids
	 * @param status
	 */
	public void updateStatus(Long[] ids,String status);


	/**
	 * 根据GoodId和Status查找sku产品信息
	 * @param ids
	 * @param status
	 * @return
	 */
	public List<Item> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
	
}
