package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webill.app.util.StringUtil;
import com.webill.core.model.CarInfo;
import com.webill.core.model.ShownCar;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IShownCarService;
import com.webill.core.service.mapper.ShownCarMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

@Service
public class ShownCarServiceImpl  extends SuperServiceImpl<ShownCarMapper, ShownCar> implements IShownCarService {

    @Autowired
    private ICarInfoService carInfoService;
	
	@Override
	public List<CarInfo> getList(String id) {
		ShownCar sc = this.selectById(id);
		List<CarInfo> ciList = new ArrayList<CarInfo>();
		CarInfo ci = null;
		if(StringUtil.isNotEmpty(sc.getCarIds())){
			String[] carIds= sc.getCarIds().split(",");
			for (String carId : carIds) {
				ci = new CarInfo();
				ci = carInfoService.selectById(carId);
				if(ci!=null){
					ciList.add(ci);
				}
			}
		}
		return ciList;
	}

}
