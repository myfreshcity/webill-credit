package com.webill.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webill.app.SystemProperty;
import com.webill.app.util.HttpUtils;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.CarUserRel;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.ICarUserRelService;
import com.webill.core.service.mapper.CarInfoMapper;
import com.webill.framework.common.JsonResult;
import com.webill.framework.service.impl.SuperServiceImpl;

import reactor.core.Reactor;
import reactor.event.Event;

/**
 *
 * CarInfo 服务层接口实现类
 *
 */
@Service
public class CarInfoServiceImpl extends SuperServiceImpl<CarInfoMapper, CarInfo> implements ICarInfoService {
    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;

    @Autowired
    private ICarUserRelService carUserRelService;
    
    @Autowired
    protected SystemProperty constPro;
    
	@Override
	public CarInfo checkCar(String licenseNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("license_no", licenseNo);
		map.put("status", 0);
		List<CarInfo> cilist = this.selectByMap(map);
		if(cilist.size()==1){
			return cilist.get(0);
		}else{
			return null;
		}
	}

	@Transactional
	@Override
	public boolean addCarByOcr(CarInfo ci, CarUserRel cur) {
		CarInfo ciold= this.checkCar(ci.getLicenseNo());
		boolean f = false;
		CarInfo ciwhere = new CarInfo();
		if(ciold!=null){
			ciwhere.setId(ciold.getId());
			ciold.setStatus(Constant.DEL_STATUS);
			//修改数据为废弃状态
			f = this.updateSelective(ciold, ciwhere);
			
			//新增车辆信息
			ci.setStatus(Constant.NORMAL_STATUS);
			ci.setCreatedTime(new Date());
			f = this.insertSelective(ci);
			
			//建立关系
			cur.setCarId(ci.getId());
			cur.setCreatedTime(new Date());
			cur.setUpdatedTime(new Date());
			f = carUserRelService.insertSelective(cur);
		}else{
			ci.setStatus(Constant.NORMAL_STATUS);
			ci.setCreatedTime(new Date());
			f = this.insertSelective(ci);
			cur.setCarId(ci.getId());
			cur.setCreatedTime(new Date());
			cur.setUpdatedTime(new Date());
			f = carUserRelService.insertSelective(cur);
		}
		return f;
	}

	@Override
	public List<CarInfo> getDuringCarList(Map<String, Object> map) {
		return baseMapper.getDuringCarList(map);
	}

	@Override
	public List<CarInfo> getCarListByUserId(String userId) {
		return baseMapper.getCarListByUserId(userId);
	}

	@Override
	public CarInfo getCarInfoByUser(Map<String, Object> map) {
		return baseMapper.getCarInfoByUser(map);
	}

	@Transactional
	@Override
	public boolean addCarToUser(CarInfo ci) {
		boolean f = this.insertOrUpdateSelective(ci);
		
		//检查人车关系是否存在
		List<CarUserRel> curList = new ArrayList<CarUserRel>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("license_no", ci.getLicenseNo());
		map.put("user_id", ci.getCurUserId());
		curList = carUserRelService.selectByMap(map);
		
		if(f){
			CarUserRel cur = new CarUserRel();
			if(curList.size()>0){
				//人车关系存在
				cur.setId(curList.get(0).getId());
			}
			cur.setCarId(ci.getId());
			cur.setUpdatedTime(new Date());
			cur.setLicenseNo(ci.getLicenseNo());
			cur.setUserId(Integer.valueOf(ci.getCurUserId()));
			cur.setMobileNo(ci.getOwnerPhone());
			cur.setCreatedTime(new Date());
			f = carUserRelService.insertOrUpdateSelective(cur);
		}
		return f;
	}

	@Transactional
	@Override
	public CarInfo addCarInfoOnly(CarInfo ci) {
		//查询，车辆入库
		String url = constPro.MMH_NET_URL + "/carinfo/get/" + ci.getLicenseNo();
		String rs = HttpUtils.httpGetRequest(url);
		//更新去年投保公司和投保日期
		r.notify("car.update",Event.wrap(ci.getLicenseNo()));
		JSONObject jo = JSON.parseObject(rs);
		CarInfo dbCar = new CarInfo();
		if(rs.contains("502 bad")){
			logger.info("查询车辆，第三方接口未开启=============="+rs);
			return null;
		}else if(rs.contains("404 Not Found")){
			logger.info("查询车辆，未找到第三方接口=============="+rs);
			return null;
		}else{
			if (jo.containsKey("id")) {
				Integer carId = jo.getInteger("id");
				String licenseNo = jo.getString("licenseNoBC");
				String carOwner = jo.getString("carOwnerBC");
				
				dbCar.setId(carId);
				dbCar.setLicenseNo(licenseNo);
				dbCar.setCarOwner(carOwner);
				
				if(carId!=null){
					return dbCar;
				}else{
					return null;
				}
			} else if(jo.getString("isSuccess").equals("500")){
				return null;
			}else{
				return null;
			}
		}
	}

}