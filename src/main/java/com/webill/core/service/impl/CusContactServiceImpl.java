package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.webill.core.model.CusContact;
import com.webill.core.service.ICusContactService;
import com.webill.core.service.mapper.CusContactMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * CusContact 服务层接口实现类
 *
 */
@Service
public class CusContactServiceImpl extends SuperServiceImpl<CusContactMapper, CusContact> implements ICusContactService {
	
	@Override
	public boolean delCusContact(Integer cusId){
		boolean f = false;
		List<Integer> ids = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<>();
		map.put("cus_id", cusId);
		List<CusContact> cusConList = this.selectByMap(map);
		if (cusConList != null && cusConList.size() > 0) {
			for (CusContact cusCon : cusConList) {
				ids.add(cusCon.getId());
			}
			f = this.deleteBatchIds(ids);
		}
		return f;
	}

}