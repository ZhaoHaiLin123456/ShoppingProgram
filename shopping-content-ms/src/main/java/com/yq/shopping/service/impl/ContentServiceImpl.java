package com.yq.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.shopping.mapper.ContentMapper;
import com.yq.shopping.po.Content;
import com.yq.shopping.po.ContentExample;
import com.yq.shopping.po.ContentExample.Criteria;
import com.yq.shopping.po.PageResult;
import com.yq.shopping.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public List<Content> findAll() {
		return contentMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Content> page = (Page<Content>) contentMapper.selectByExample(null);
		PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
		return pageResult;
	}

	@Override
	public int add(Content content) {
		int insert = contentMapper.insert(content);
		// 清除缓存
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		return insert;
	}

	@Override
	public int update(Content content) {
		Content oldContent = contentMapper.selectByPrimaryKey(content.getId());
		// 清除之前分类的广告缓存
		redisTemplate.boundHashOps("content").delete(oldContent.getCategoryId());

		int newResult = contentMapper.updateByPrimaryKey(content);
		// 清除缓存
		if (content.getCategoryId() != oldContent.getCategoryId()) {
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		return newResult;

	}

	@Override
	public Content findOne(Long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(Long[] ids) {
		int deleteByPrimaryKey = 0;
		for (Long id : ids) {
			Content oldContent = contentMapper.selectByPrimaryKey(id);

			redisTemplate.boundHashOps("content").delete(oldContent);
			deleteByPrimaryKey += contentMapper.deleteByPrimaryKey(id);
		}
		return deleteByPrimaryKey;

	}

	@Override
	public PageResult findPage(Content content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ContentExample example=new ContentExample();
		Criteria criteria = example.createCriteria();
		if (content!=null) {
			if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
		}
		Page<Content> page = (Page<Content>) contentMapper.selectByExample(example);
		PageResult pageResult = new PageResult(page.getResult(), page.getTotal());
		return pageResult;
	}

	public List<Content> findByCategoryId(Long categoryId) {
		// 加入缓存的代码:
		List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

		if (list == null) {
			System.out.println("查询数据库===================");
			ContentExample example = new ContentExample();
			Criteria criteria = example.createCriteria();
			// 有效广告:
			criteria.andStatusEqualTo("1");

			criteria.andCategoryIdEqualTo(categoryId);

			// 排序
			example.setOrderByClause("sort_order");

			list = contentMapper.selectByExample(example);

			redisTemplate.boundHashOps("content").put(categoryId, list);
		} else {
			System.out.println("从缓存中获取====================");
		}

		return list;
	}
}
