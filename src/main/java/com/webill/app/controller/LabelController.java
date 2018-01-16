package com.webill.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webill.core.model.Label;
import com.webill.core.model.LabelGroup;
import com.webill.core.model.SelectJson;
import com.webill.core.service.ILabelGroupService;
import com.webill.core.service.ILabelService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.controller.BaseController;

/** 
* @ClassName: LabelController 
* @Description: 
* @author ZhangYadong
* @date 2017年12月12日 下午4:45:59 
*/
@Controller
@RequestMapping("/label")
public class LabelController extends BaseController{
	@Autowired
    private ILabelService labelService;
	@Autowired
	private ILabelGroupService labelGroupService;
	
	@RequestMapping(value = "/labelJson", method = { RequestMethod.GET , RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object labelJson() throws Exception {
		List<Label> nls = labelService.getNavLabel();
		List<SelectJson> sjlist = new ArrayList<>();
		for (Label la : nls) {
			SelectJson sj = new SelectJson();
			sj.setKey(la.getId().toString());
			sj.setValue(la.getLabelName());
			sjlist.add(sj);
		}
		return sjlist;	
	}
	
	@RequestMapping(value = "/labelGroupList", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String labelGroupList() {
		List<LabelGroup> fcls = labelGroupService.labelGroupList();
		return JSONUtil.toJSONString(fcls);
    }
	
	@RequestMapping(value = "/labelListByGroupId", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public String labelListByGroupId(Integer id) {
		List<Label> fcls = labelService.labelListByGroupId(id);
		return JSONUtil.toJSONString(fcls);
	}
}
