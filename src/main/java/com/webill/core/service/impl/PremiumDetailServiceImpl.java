package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.webill.app.util.PinYinUtil;
import com.webill.core.model.PremiumDetail;
import com.webill.core.service.IPremiumDetailService;
import com.webill.core.service.mapper.PremiumDetailMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * PremiumDetail 服务层接口实现类
 *
 */
@Service
public class PremiumDetailServiceImpl extends SuperServiceImpl<PremiumDetailMapper, PremiumDetail> implements IPremiumDetailService {

	@Override
	public List<PremiumDetail> selectOrderBy(Map<String, Object> map) {
		return baseMapper.selectOrderBy(map);
	}

	@Override
	public List<PremiumDetail> getFullDetail(List<PremiumDetail> oList) {
		//处理不计免赔
		PremiumDetail dd= null;//A保险
		PremiumDetail dl= null;//A保险不计免赔险
		//处理后数据
		List<PremiumDetail> nList = new ArrayList<PremiumDetail>();
		for(PremiumDetail l:oList){
			String flag = l.getAmount(); // 险种是否选中
			if((!"false".equals(flag)) && (!"不投保".equals(flag)) ){

				if(l.isSelected()){//如果选择不计免赔，存入两条数据
					String pCode = PinYinUtil.getFirstSpell(l.getPrmName()).toUpperCase();
					dd = new PremiumDetail();
					if(l.getAmount().equals("true")){
						dd.setAmount("投保");
					}else{
						dd.setAmount(l.getAmount());
					}
					dd.setId(l.getId());
					dd.setPrmName(l.getPrmName());
					dd.setPrmOrderId(l.getPrmOrderId());
					dd.setPrmCode(pCode);
					
					nList.add(dd);
					dd.setCreatedTime(new Date());
					
					dl = new PremiumDetail();
					if(l.getAmount().equals("true")){
						dl.setAmount("投保");
					}else{
						dl.setAmount(l.getAmount());
					}
					dl.setPrmName(l.getPrmName()+"不计免赔");
					dl.setPrmOrderId(l.getPrmOrderId());
					dl.setPrmCode(pCode+"_BJMP");
					
					nList.add(dl);
					dl.setCreatedTime(null);
				}else{//没有选择不计免赔，存入一条数据
					String pCode = PinYinUtil.getFirstSpell(l.getPrmName()).toUpperCase();
					if(!pCode.contains("BJMP")){
						dd = new PremiumDetail();
						if(l.getAmount().equals("true")){
							dd.setAmount("投保");
						}else{
							dd.setAmount(l.getAmount());
						}
						dd.setId(l.getId());
						dd.setPrmName(l.getPrmName());
						dd.setPrmOrderId(l.getPrmOrderId());
						dd.setPrmCode(pCode);
						nList.add(dd);
						dd.setCreatedTime(null);
					}
				}
			}
		}
		return nList;
	}


}