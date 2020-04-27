package com.yq.shopping.po;

import java.io.Serializable;
import java.util.List;

public class TbGoods implements Serializable{
	
	
	private Goods goods;//spu基本信息
	
	private GoodsDesc goodsDesc;//spu产品扩展信息
	
	private List<Item> itemList;//sku 多方 产品库存信息

	public Goods getGoods() {
		return goods;
	}

	public GoodsDesc getGoodsDesc() {
		return goodsDesc;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public void setGoodsDesc(GoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "TbGoods [goods=" + goods + ", goodsDesc=" + goodsDesc + ", itemList=" + itemList + "]";
	}


	
	
	
}
