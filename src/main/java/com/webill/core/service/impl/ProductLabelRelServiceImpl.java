package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webill.core.model.Label;
import com.webill.core.model.ProductLabelRel;
import com.webill.core.service.ILabelService;
import com.webill.core.service.IProductLabelRelService;
import com.webill.core.service.mapper.ProductLabelRelMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * ProductLabelRel 服务层接口实现类
 *
 */
@Service
public class ProductLabelRelServiceImpl extends SuperServiceImpl<ProductLabelRelMapper, ProductLabelRel> implements IProductLabelRelService {
	@Autowired
	private ILabelService labelService;
	
	@Override
	public ProductLabelRel getProdLabRelByProdId(Integer prodId) {
		ProductLabelRel plr = baseMapper.getProdLabRelByProdId(prodId);
		return plr;
	}
	
	@Override
	public boolean addProdToRecomLabel(Integer prodId){
		Map<String, Object> map = new HashMap<>();
		map.put("label_name", "为你推荐");
		List<Label> lab = labelService.selectByMap(map);
		ProductLabelRel rlr = new ProductLabelRel();
		rlr.setProdId(prodId);
		rlr.setLabelId(lab.get(0).getId());
		boolean f = this.insertSelective(rlr);
		return f;
	}
	
	@Override
	public boolean addProdToNavLabel(Integer prodId, Integer labelId){
		ProductLabelRel pll = new ProductLabelRel();
		pll.setLabelId(labelId);
		pll.setProdId(prodId);
		boolean f = this.insertSelective(pll);
		return f;
	}
	
	@Override
	public boolean addProdToBusinessLabel(Integer prodId, String labelArray){
		String[] busLableId = labelArray.split(",");
		List<ProductLabelRel> busList = new ArrayList<>();
		for (String bl: busLableId) {
			ProductLabelRel blable = new ProductLabelRel();
			blable.setLabelId(Integer.parseInt(bl));
			blable.setProdId(prodId);
			blable.setTStatus(0);
			blable.setCreatedTime(new Date());
			busList.add(blable);
		}
		boolean f = this.insertBatch(busList);
		return f;
	}
	
	@Override
	public ProductLabelRel isRecommendByProdId(Integer prodId){
		ProductLabelRel plr = baseMapper.isRecommendByProdId(prodId);
		return plr;
	}
	
	@Override
	public List<ProductLabelRel> getBusinessLabelByProdId(Integer prodId){
		List<ProductLabelRel> plrList = baseMapper.getBusinessLabelByProdId(prodId);
		return plrList;
	}

	@Override
	public boolean delBusinessLabelByProdId(Integer prodId) {
		boolean f = false;
		List<Integer> ids = new ArrayList<>();
		List<ProductLabelRel> plrList = this.getBusinessLabelByProdId(prodId);
		if (plrList != null && plrList.size() > 0) {
			for (ProductLabelRel plr : plrList) {
				ids.add(plr.getId());
			}
			f = this.deleteBatchIds(ids);
		}
		return f;
	}
}