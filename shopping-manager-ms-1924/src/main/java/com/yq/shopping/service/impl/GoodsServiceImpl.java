package com.yq.shopping.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.BrandMapper;
import com.yq.shopping.mapper.GoodsDescMapper;
import com.yq.shopping.mapper.GoodsMapper;
import com.yq.shopping.mapper.ItemCatMapper;
import com.yq.shopping.mapper.ItemMapper;
import com.yq.shopping.mapper.SellerMapper;
import com.yq.shopping.po.Brand;
import com.yq.shopping.po.Goods;
import com.yq.shopping.po.GoodsDesc;
import com.yq.shopping.po.GoodsExample;
import com.yq.shopping.po.GoodsExample.Criteria;
import com.yq.shopping.po.Item;
import com.yq.shopping.po.ItemCat;
import com.yq.shopping.po.ItemExample;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.po.Seller;
import com.yq.shopping.po.TbGoods;
import com.yq.shopping.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private GoodsDescMapper goodsDescMapper;

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private SellerMapper sellerMapper;

	@Override
	public PageResult findPage(Goods goods, int pageNum, int pageSize) {
		System.out.println("进入service层测试findPage");
		System.out.println(goods.toString());
		PageHelper.startPage(pageNum, pageSize);
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		// criteria.andIsDeleteIsNull();

		if (goods != null) {
			if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
				System.out.println("设置sellerId: " + goods.getSellerId());
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
				criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
			}
			if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
				criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
			}
			if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
				criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
			}
			if (goods.getCaption() != null && goods.getCaption().length() > 0) {
				criteria.andCaptionLike("%" + goods.getCaption() + "%");
			}
			if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
				criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
			}
			if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
				criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
			}
			if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
				criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
			}

		}
		Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(example);
		System.out.println("##### " + page.getResult());
		System.out.println(page.getTotal());
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		System.out.println("进入service层测试updateStatus");
		for (Long id : ids) {
			// 根据id查询goods信息
			Goods tbGoods = goodsMapper.selectByPrimaryKey(id);
			// 修改状态；上架
			tbGoods.setAuditStatus(status);
			// 更新
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	/**
	 * 根据GoodsId和Status，查询库存产品信息
	 */
	@Override
	public List<Item> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {
		for (int i = 0; i < goodsIds.length; i++) {
			Long long1 = goodsIds[i];
			System.out.println("goodsIds=========="+goodsIds);
		}
		System.out.println("status=========="+status);
		ItemExample example = new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");// 状态
		criteria.andGoodsIdIn(Arrays.asList(goodsIds));
		List<Item> list = itemMapper.selectByExample(example);
		//将sku查询出来
		for (Item item : list) {
			System.out.println(item.toString());
		}
		return list;
	}

	@Override
	public List<Goods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void add(TbGoods tbGoods) {
		System.out.println("进入商品添加service层");
		//System.out.println(tbGoods.toString());
		
		tbGoods.getGoods().setAuditStatus("0");// 1、设置审核状态

		goodsMapper.insert(tbGoods.getGoods());// 2、插入商品信息tb_goods

		System.out.println("tb_goods_desc表的外键"+tbGoods.getGoods().getId());
		tbGoods.getGoodsDesc().setGoodsId(tbGoods.getGoods().getId());// 3、设置tb_goods_desc表的外键

		goodsDescMapper.insert(tbGoods.getGoodsDesc());// 4、插入商品的扩展信息tb_goods_desc

		setItemList(tbGoods);// 5、保存sku信息
	}

	/**
	 * 判断是否启用规模 1、启用 保存sku 2、不启用 new 一个sku 再保存
	 * 
	 * @param tbGoods
	 */
	private void setItemList(TbGoods tbGoods) {
		System.out.println(tbGoods.toString());
		System.out.println("是否启用规模：" + tbGoods.getGoods().getIsEnableSpec());
		if ("1".equals(tbGoods.getGoods().getIsEnableSpec())) {
			// 启用规格
			// 保存SKU列表的信息
			List<Item> itemsList = tbGoods.getItemList();
			
			for (Item item : itemsList) {
				String title = tbGoods.getGoods().getGoodsName();
				Map<String, String> map = JSON.parseObject(item.getSpec(), Map.class);
				for (String key : map.keySet()) {
					title += "" + map.get(key);
				}

				item.setTitle(title);

				setValue(tbGoods, item);

				itemMapper.insert(item);
				
			}
		} else {
			// 没有启用规模
			Item item = new Item();

			item.setTitle(tbGoods.getGoods().getGoodsName());

			item.setPrice(tbGoods.getGoods().getPrice());

			item.setNum(999);

			item.setStatus("1");//sku库存商品上架

			item.setIsDefault("1");
			item.setSpec("{}");

			setValue(tbGoods, item);
			itemMapper.insert(item);
		}

	}

	private void setValue(TbGoods tbGoods, Item item) {
		List<Map> imageList = JSON.parseArray(tbGoods.getGoodsDesc().getItemImages(), Map.class);
		if (imageList.size() > 0) {
			item.setImage((String) imageList.get(0).get("url"));
		}
		// 保存三级分类信息
		item.setCategoryid(tbGoods.getGoods().getCategory3Id());
		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());
		// 设置商品id
		item.setGoodsId(tbGoods.getGoods().getId());
		item.setSellerId(tbGoods.getGoods().getSellerId());

		ItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getGoods().getCategory3Id());

		item.setCategory(itemCat.getName());

		Brand brand = brandMapper.selectByPrimaryKey(tbGoods.getGoods().getBrandId());

		item.setBrand(brand.getName());

		Seller seller = sellerMapper.selectByPrimaryKey(tbGoods.getGoods().getSellerId());
		item.setStatus("0");

		item.setSeller(seller.getNickName());

	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods tbGoods) {
		// 修改商品信息
		tbGoods.getGoods().setAuditStatus("0");
		goodsMapper.updateByPrimaryKey(tbGoods.getGoods());
		// 修改商品扩展信息:
		goodsDescMapper.updateByPrimaryKey(tbGoods.getGoodsDesc());
		// 修改SKU信息:
		// 先删除，再保存:
		// 删除SKU的信息:
		ItemExample example = new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(tbGoods.getGoods().getId());
		itemMapper.deleteByExample(example);
		// 保存SKU的信息
		setItemList(tbGoods);

	}

	/**
	 * 根据goodId查询sku信息
	 * Goods
	 * GoodsDesc
	 * Item
	 */
	@Override
	public TbGoods findOne(Long id) {
		TbGoods tbGoods=new TbGoods();
		Goods goods = goodsMapper.selectByPrimaryKey(id);
		tbGoods.setGoods(goods);
		//查询扩展表的信息
		GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		tbGoods.setGoodsDesc(goodsDesc);
		
		//查询sku信息
		ItemExample example=new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<Item> list = itemMapper.selectByExample(example);
		tbGoods.setItemList(list);
		return tbGoods;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			Goods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);//实现假删除
		}
	}

	
	
}
