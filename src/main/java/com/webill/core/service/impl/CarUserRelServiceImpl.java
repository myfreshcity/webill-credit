package com.webill.core.service.impl;

import com.webill.app.util.DateUtil;
import com.webill.core.Constant;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.CarUserRel;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.ICarUserRelService;
import com.webill.core.service.mapper.CarUserRelMapper;
import com.webill.framework.common.JsonResult;
import com.webill.framework.service.impl.SuperServiceImpl;
import com.webill.framework.service.mapper.EntityWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CarUserRel 服务层接口实现类
 */
@Service
public class CarUserRelServiceImpl extends SuperServiceImpl<CarUserRelMapper, CarUserRel> implements ICarUserRelService {
    @Autowired
    private ICarInfoService carInfoService;
    
    public List<CarUserRel> selectCarList(String userId) {
        EntityWrapper<CarUserRel> ew = new EntityWrapper<CarUserRel>();
        ew.addFilter("user_id = {0}", userId);
        ew.groupBy("license_no");
        ew.orderBy("updated_time desc");
        List<CarUserRel> list = this.selectList(ew);
        logger.debug("get car list by user:" + userId);
        return list;
    }
    
    @Override
    public String addCarUserRel(CarUserRel rel) {
    	//返回结果标识
		String result = null;
		
		//添加人车关系时返回的标识
		ResponseInfo info = null;
		
		//判断车辆是否存在
		CarInfo c = carInfoService.checkCar(rel.getLicenseNo());
		if(c!=null){
			//车辆存在情况
			//建立人车关系
			if(rel.getCarOwner().equals(c.getCarOwner())){
				rel.setCarId(c.getId());
				info = this.addRelOnly(rel);
				if(info.equals(ResponseInfo.SUCCESS)){
					result = Constant.CAR_ADD_SUCCESS;//关系建立成功
				}else{
					result = Constant.CAR_ADD_FAIL;//关系建立失败
				}
			}else{
				return result = Constant.CAR_ADD_OWNERNAME_NOT_SAME;
			}
		}else{
			//车辆不存在，从第三方接口获取车辆信息，同时第三方入库
			CarInfo ci = new CarInfo();
			ci.setLicenseNo(rel.getLicenseNo());
			
			CarInfo dbCar = new CarInfo();
			dbCar = carInfoService.addCarInfoOnly(ci);
			
			if(dbCar!=null){
				if(rel.getCarOwner().equals(dbCar.getCarOwner())){
					dbCar.setCurUserId(rel.getUserId().toString());
					//建立人车关系
					rel.setLicenseNo(dbCar.getLicenseNo());
					rel.setUserId(Integer.valueOf(rel.getUserId()));
					rel.setCarOwner(dbCar.getCarOwner());
					rel.setMobileNo(rel.getMobileNo());
					rel.setCarId(dbCar.getId());
					
					info = this.addRelOnly(rel);
					if(info.equals(ResponseInfo.SUCCESS)){
						
						result = Constant.CAR_ADD_SUCCESS;//关系建立成功
					}else{
						
						result = Constant.CAR_ADD_FAIL;//关系建立失败
					}
				}else{
					result = Constant.CAR_ADD_OWNERNAME_NOT_SAME;
				}
			}else{
				result = Constant.CAR_ADD_NOT_EXIST;
			}
		}
    	return result;
    }

	@Override
	public List<CarUserRel> getCarListBy(Map<String, Object> map) {
		return baseMapper.getCarListBy(map);
	}

	@Override
	public CarUserRel getCarUserRelByMap(Map<String, Object> map) {
		return baseMapper.getCarUserRelByMap(map);
	}

	@Override
	public ResponseInfo addRelOnly(CarUserRel rel) {
		Date now = new Date();
		rel.setUpdatedTime(now);
		rel.setCreatedTime(now);
		boolean flag = this.insert(rel);
		if (flag) {
            return ResponseInfo.SUCCESS;
        } else {
            return ResponseInfo.ACTION_FAIL;
        }
	}

}