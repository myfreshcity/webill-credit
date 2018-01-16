package com.webill.app.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.model.Category;
import com.webill.core.service.ICategoryService;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.common.JsonResult;
import com.webill.framework.controller.BaseController;

/** 
* @ClassName: CategoryController 
* @Description: 
* @author ZhangYadong
* @date 2017年11月16日 下午4:12:56 
*/
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{
	@Autowired
    private ICategoryService categoryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "system/category/index";
    }
	
	@RequestMapping(value = "/addCate", method = RequestMethod.GET)
	public String addCate() {
		return "system/category/add-category";
	}
	
	@RequestMapping(value = "/cateJson", method = { RequestMethod.GET , RequestMethod.POST},produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Object cateJson() throws Exception {
		List<Category> flist = categoryService.getFirstCatList();
		JSONObject ob = new JSONObject();
		for (Category fcate: flist) {
			List<Category> slist = categoryService.getSecondCatList(fcate.getId());
			List<String> strs = new LinkedList<>();
			for (Category scate : slist) {
				strs.add(scate.getCatName());
			}
			ob.put(fcate.getCatName(), strs);
		}
		return ob;	
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String catList(Category cate) {
       logger.info("logging test....");
       Page page = this.getPage(Integer.MAX_VALUE);
       page =   categoryService.getCategoryList(page, cate);
       return JSONUtil.toJSONString(page);
    }
	
	@RequestMapping(value = "/childCatList/{id}", method = { RequestMethod.GET },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	public String childCatList(@PathVariable Integer id, Model model) {
		List<Category> catList = categoryService.getChildCatList(id);
		model.addAttribute("childCatList", catList);
		return "system/category/child-category";
	}
	
	@RequestMapping(value = "/checkCatName", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult checkCatName(String catName) {
		Map<String, Object> map = new HashMap<>();
		map.put("cat_name", catName);
		List<Category> cats = categoryService.selectByMap(map);
		if (cats != null && cats.size() > 0) {
			return renderSuccess("分类名称已存在！", "200");
		}else {
			return renderSuccess("分类名称可用！", "500");
		}
	}
	
	@RequestMapping(value = "/firstCatList", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String firstCatList() {
		List<Category> fcls = categoryService.getFirstCatList();
		return JSONUtil.toJSONString(fcls);
    }
	
	@RequestMapping(value = "/secondCatList", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public String secondCatList(Integer id) {
		List<Category> scls = categoryService.getSecondCatList(id);
		return JSONUtil.toJSONString(scls);
	}
	
	/** 
	 * @Title: addCat 
	 * @Description: 添加商品分类信息
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:49:17
	 * @param cat
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/addCat", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult addCat(Category cat) {
		Map<String, Object> map = new HashMap<>();;
		map.put("cat_name", cat.getCatName());
		List<Category> cats = categoryService.selectByMap(map);
		if (cats != null && cats.size() > 0) {
			return renderSuccess("分类名称已存在！", "300");
		}
		boolean f = categoryService.addCat(cat);
		if (f) {
			return renderSuccess("添加分类成功！", "200");
		}else {
			return renderError("添加分类失败！", "500");
		}
	}
	
	/** 
	 * @Title: uploadImg 
	 * @Description: 上传商品分类图标
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:49:03
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @return Object
	 */
	@RequestMapping(value = "/upload/cateIcon", method = { RequestMethod.POST }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
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
            String url = constPro.CATEGORY_FILE_SAVE_PATH;
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
	 * @Title: showCategory 
	 * @Description: 分类在首页显示操作
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:48:50
	 * @param cat
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/showCategory", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult showCategory(Category cat) {
		cat.setIsDisplay(0);
		boolean f = categoryService.updateSelectiveById(cat);
		if (f) {
			return renderSuccess("分类显示成功！", "200");
		}else {
			return renderSuccess("修改分类显示异常！", "500");
		}
	}
	
	/** 
	 * @Title: stopCategory 
	 * @Description: 分类在首页不显示操作
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:48:30
	 * @param cat
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/stopCategory", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult stopCategory(Category cat) {
		cat.setIsDisplay(1);
		boolean f = categoryService.updateSelectiveById(cat);
		if (f) {
			return renderSuccess("分类不显示成功！", "200");
		}else {
			return renderSuccess("修改分类显示异常！", "500");
		}
	}
	
	/** 
	 * @Title: deleteCat 
	 * @Description: 物理删除分类
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:48:11
	 * @param id
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/deleteCat", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult deleteCat(Integer id) {
		Category cat = categoryService.selectById(id);
		if (cat.getLevel() == 1) {
			List<Category> scats = categoryService.getSecondCatList(id);
			if (scats != null && scats.size() > 0) {
				for (Category scat : scats) {
					categoryService.deleteById(scat.getId());
				}
			}
		}
		boolean f = categoryService.deleteById(id);
		if (f) {
			return renderSuccess("删除分类成功！", "200");
		}else {
			return renderSuccess("删除分类异常！", "500");
		}
	}
	
	/** 
	 * @Title: updateCatParentId 
	 * @Description: 修改商品分类父分类，转移分类
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:52:16
	 * @param cat
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/updateCatParentId", method = { RequestMethod.POST },produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult updateCatParentId(Category cat) {
		boolean f = categoryService.updateSelectiveById(cat);
		if (f) {
			return renderSuccess("分类修改成功！", "200");
		}else {
			return renderSuccess("分类修改异常！", "500");
		}
	}
	
	/** 
	 * @Title: updateCat 
	 * @Description: 修改分类信息
	 * @author ZhangYadong
	 * @date 2017年12月27日 上午10:57:10
	 * @param cat
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping(value = "/updateCat", method = {RequestMethod.POST}, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public JsonResult updateCat(Category cat){
		boolean f = categoryService.updateSelectiveById(cat);
		if(f){
			return renderSuccess("分类修改成功！", "200");
		}else {
			return renderError("分类修改失败！", "500");
		}
	}
}
