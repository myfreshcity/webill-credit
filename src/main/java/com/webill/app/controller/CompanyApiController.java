package com.webill.app.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.util.AreaChildrenUtil;
import com.webill.app.util.StringUtil;
import com.webill.core.model.Company;
import com.webill.core.model.User;
import com.webill.core.service.ICompanyService;
import com.webill.core.service.IUserService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: CompanyApiController 
 * @Description: 
 * @author: ZhangYadong
 * @date: 2018年3月21日
 */

@Api(value = "companyAPI", description = "公司API", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/api/company")
public class CompanyApiController extends BaseController {
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IUserService userService;
	@Autowired
	private AreaChildrenUtil areaChildrenUtil;
	
	/** 
	 * @Title: busiLiceImg 
	 * @Description: 上传营业执照图片
	 * @author ZhangYadong
	 * @date 2018年1月2日 下午7:05:43
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/upload/busiLiceImg", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody 
	public Object uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
        try {
            //输出文件后缀名称
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            String url = constPro.BUSI_LICE_FILE_PATH;
            //相对路径
            String path = "/"+name + "." + fileType;
            File f = new File(url);
            if(!f.exists()){
                f.mkdirs();
            }
            file.transferTo(new File(url+path));
            map.put("success", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
	
	/**  
	 * @Title: submitComInfo  
	 * @Description: 添加企业信息
	 * @author: ZhangYadong
	 * @date: 2018年3月21日
	 * @param com
	 * @return JsonResult
	 */ 
	@RequestMapping(value = "/submitComInfo", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult submitComInfo(@RequestBody Company com){
		companyService.addComInfo(com);
		User user = userService.updateUserCom(com.getUserId(), com.getId());
		if (!StringUtil.isEmpty(user)) {
			return renderSuccess("添加企业信息成功！", "200", user);
		} else {
			return renderError("添加企业信息失败！", "500");
		}
	}
	
	@ApiOperation(value = "根据条件获取企业信息列表")
	@RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String companyList(HttpServletRequest request, Company com) {
	    Page page = this.getPage(request, Integer.MAX_VALUE);
        page = companyService.getComList(page, com);
        return JSONUtil.toJSONString(page);
    }
	
	@ApiOperation(value = "根据条件获取企业信息列表")
	@RequestMapping(value = "/getComDetail/{comId}", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object getComDetail(@PathVariable String comId) {
		Company comp = companyService.selectById(comId);
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("com_id", comId);
		List<User> userList = userService.selectByMap(userMap);
		User userCom = null;
		if (userList != null && userList.size() > 0) {
			 userCom = userList.get(0);
		}else {
			return renderError("该企业没有绑定用户！", "500");
		}
		comp.setUserMobileNo(userCom.getMobileNo());
		comp.setComAddr(areaChildrenUtil.getValue(comp.getComAddrCode()));
		if (!StringUtil.isEmpty(comp.getOperatorId())) {
			User opUser = userService.selectById(comp.getOperatorId());
			comp.setOperatorName(opUser.getRealName());
		}
		return renderSuccess("获取企业信息成功！", "200", comp);
	}
	
	@ApiOperation(value = "企业审核")
	@RequestMapping(value = "/checkCom", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object checkCom(@RequestBody Company com) {
		com.setReviewTime(new Date());
		boolean f = companyService.updateSelectiveById(com);
		if (f) {
			if (com.getStatus() == 2) { // 申请状态：1-待审核 2-审核通过 3-审核拒绝
				return renderSuccess("企业信息审核通过！", "200", com);
			}else if(com.getStatus() == 3){
				return renderError("企业信息审核拒绝！", "300");
			}
		}
		return renderError("审核企业信息出现异常！", "500");
	}
	
	
}
