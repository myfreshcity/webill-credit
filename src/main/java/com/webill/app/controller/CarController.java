package com.webill.app.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.StringUtil;
import com.webill.core.Constant;
import com.webill.core.model.CarInfo;
import com.webill.core.model.CarUserRel;
import com.webill.core.model.User;
import com.webill.core.model.UserContact;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.ICarUserRelService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;
import com.webill.framework.service.mapper.EntityWrapper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by newcity on 2017/5/11.
 */
@Controller
@RequestMapping("/car")
public class CarController extends BaseController {
    private static Log logger = LogFactory.getLog(CarController.class);

    @Autowired
    private ICarInfoService carInfoService;
    
    @Autowired
    private ICarUserRelService carUserRelService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/car/index";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("car", carInfoService.selectById(id));
        return "system/car/edit";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveCar(CarInfo cr, Model model) {
    	CarInfo where = new CarInfo();
    	where.setLicenseNo(cr.getLicenseNo());
    	boolean f = carInfoService.updateSelective(cr,where);
    	return f;
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String list(CarInfo car) {
        logger.info("logging test....");
        Page page = this.getPage(Integer.MAX_VALUE);
        EntityWrapper<CarInfo> ew = new EntityWrapper<CarInfo>();
        //ew.addFilter("status={0}", Constant.NORMAL_STATUS);
        if(null != car.getInsurerCom()){
            ew.addFilter("insurer_com={0}", car.getInsurerCom());
        }
        ew.orderBy("created_time desc");
        page = carInfoService.selectPage(page, ew);
        return JSONUtil.toJSONString(page);
    }

    /**   
     * @Title: selectCarInfo   
     * @Description: 根据id获取车辆信息 , 用于修改、添加车辆页面数据显示  
     * @author: WangLongFei  
     * @date: 2017年8月30日 上午10:22:21   
     * @param id
     * @param flag
     * @param model
     * @return  
     * @return: String  
     */
    @RequestMapping(value = "/selectCarInfo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String selectCarInfo(@RequestParam(required =true)String userId,@RequestParam(required = false)String carId,Model model){
    	CarInfo ci = new CarInfo(); 
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("curUserId", userId);
    	if(StringUtil.isNotEmpty(carId)){
    		//编辑车辆信息
    		map.put("carId", carId);
    		ci = carInfoService.getCarInfoByUser(map);
    	}else{
    		//添加车辆信息
    		ci.setCurUserId(userId);
    	}
    	model.addAttribute("ci", ci);
    	return "system/car/edit2";
    }
    
    /**   
     * @Title: saveCarInfo   
     * @Description: 编辑车辆信息  
     * @author: WangLongFei  
     * @date: 2017年8月30日 上午10:20:56   
     * @param car
     * @param model
     * @return  
     * @return: JsonResult  
     */
    @RequestMapping(value = "/saveCarInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveCarInfo(CarInfo car) {
    	boolean f = false;
    	car.setStatus(Constant.NORMAL_STATUS);
    	f = carInfoService.addCarToUser(car);
    	if(f){
    		return renderSuccess("操作成功！", "200",car);
    	}else{
    		return renderError("操作失败！", "500");
    	}
    }
    
    /**   
     * @Title: saveCarUserRel   
     * @Description: 补充资料，保存证件路径  
     * @author: WangLongFei  
     * @date: 2017年9月5日 下午1:54:36   
     * @param cur
     * @param model
     * @return  
     * @return: JsonResult  
     */
    @RequestMapping(value = "/saveCarUserRel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveCarUserRel(CarUserRel cur, Model model) {
    	boolean f = carUserRelService.updateSelectiveById(cur);
    	if(f){
    		return renderSuccess("操作成功！", "200");
    	}else{
    		return renderError("操作失败！", "500");
    	}
    }
    
    /**   
     * @Title: updatePhoto   
     * @Description: 上传图片    
     * @author: WangLongFei  
     * @date: 2017年9月1日 下午2:27:46   
     * @param request
     * @param response
     * @param myFile
     * @return  
     * @return: JsonResult  
     */
    @RequestMapping(value = "/upload/photo",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> uploadPhoto(HttpServletRequest request,HttpServletResponse response,CarInfo ci){
    	Map<String, Object> json = new HashMap<String, Object>();
        try {
            //输出文件后缀名称
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            MultipartFile myfile = null;
            if(ci.getIdCardN()!=null){
            	myfile = ci.getIdCardN(); 
            }else if(ci.getIdCardP()!=null){
            	myfile = ci.getIdCardP();
            }else if(ci.getVehicleLicenseN()!=null){
            	myfile = ci.getVehicleLicenseN();
            }else if(ci.getVehicleLicenseP()!=null){
            	myfile = ci.getVehicleLicenseP();
            }else if(ci.getDriveLicenseN()!=null){
            	myfile = ci.getDriveLicenseN();
            }else{
            	myfile = ci.getDriveLicenseP();
            }
            //
            String fileType = FilenameUtils.getExtension(myfile.getOriginalFilename());
            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            String url = constPro.FILE_SAVE_PATH;
            //相对路径
            String path = "/"+name + "." + fileType;
            File file = new File(url);
            if(!file.exists()){
                file.mkdirs();
            }
            myfile.transferTo(new File(url+path));
            json.put("success", path);
            String pathFlag = "";
            if(ci.getIdCardN()!=null){
            	pathFlag = "idCardPPath";
            }else if(ci.getIdCardP()!=null){
            	pathFlag = "idCardNPath";
            }else if(ci.getVehicleLicenseN()!=null){
            	pathFlag = "vehicleLicenseNPath";
            }else if(ci.getVehicleLicenseP()!=null){
            	pathFlag = "vehicleLicensePPath";
            }else if(ci.getDriveLicenseN()!=null){
            	pathFlag = "driveLicenseNPath";
            }else{
            	pathFlag = "driveLicensePPath";
            }
            json.put("pathFlag", pathFlag);
        } catch (Exception e) {
//        	result = renderError("上传失败！", "500");
            e.printStackTrace();
        }
        return json;
    }
    
}
