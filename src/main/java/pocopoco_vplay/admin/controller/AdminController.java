package pocopoco_vplay.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import pocopoco_vplay.admin.model.service.AdminService;
import pocopoco_vplay.board.model.vo.Content;
import pocopoco_vplay.commom.Pagination;
import pocopoco_vplay.commom.model.vo.PageInfo;
import pocopoco_vplay.users.model.vo.Users;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
@SessionAttributes("loginUser")
public class AdminController {
	
	private final AdminService aService;
	
	@GetMapping("dashboard")
	public ModelAndView joinDashboard(ModelAndView mv){
		
		int userCount = aService.getUsersCount();
		int templatesCount = aService.getTemplatesCount();
		
		mv.addObject("userCount", userCount).addObject("templatesCount", templatesCount);
		mv.setViewName("dashboard");
		return mv;
	}
	
	@GetMapping(value="drawChart", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String drawChart() {
		
		int countVedio = 0;
		int countMusic = 0;
		int countSound = 0;
		int countGraphic = 0;
		int countStockV = 0;
		int countPhoto = 0;
		int countFont = 0;
		
		for(int i =1; i<=7 ; i++) {
			int result = aService.countMenuTemp(i);
			
			switch(i) {
			case 1: countVedio = result; break;
			case 2: countMusic = result; break;
			case 3: countSound = result; break;
			case 4: countGraphic = result; break;
			case 5: countStockV = result; break;
			case 6: countPhoto = result; break;
			case 7: countFont = result;
			}
		}
		
		List<JSONObject> dataList = new ArrayList<>();
		
		dataList.add(new JSONObject().put("menu", "Video Templates").put("count", countVedio));
		dataList.add(new JSONObject().put("menu", "Music").put("count", countMusic));
		dataList.add(new JSONObject().put("menu", "Sound Effects").put("count", countSound));
		dataList.add(new JSONObject().put("menu", "Graphic Templates").put("count", countGraphic));
		dataList.add(new JSONObject().put("menu", "Stock Video").put("count", countStockV));
		dataList.add(new JSONObject().put("menu", "Photos").put("count", countPhoto));
		dataList.add(new JSONObject().put("menu", "Fonts").put("count", countFont));
		
		JSONArray array = new JSONArray(dataList);

		return array.toString();
	}
	
	@GetMapping("users")
	public ModelAndView joinUsers(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = aService.getUsersCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		ArrayList<Users> list = aService.selectAllUser(pi);
		
		for(Users u : list) {
			System.out.println(u.getJoinDate());
		}
		
		mv.addObject("list", list).addObject("pi",pi).setViewName("users_management");
		return mv;
	}
	
	@GetMapping("inquiry")
	public ModelAndView joinInquiry(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = aService.getInquiryCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		ArrayList<Content> list = aService.selectAllQuiry(pi);
		
		for(Content c : list) {
			c.setUserId(aService.selectUser(c.getUserNo()));
			//System.out.println(c);
		}
		
		mv.addObject("list", list).addObject("pi",pi);
		
		mv.setViewName("Inquiry_management");
		
		return mv;
	}
	
	@GetMapping("templates")
	public ModelAndView joinTemplates(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = aService.getTemplatesCount();
		System.out.println(listCount);
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		ArrayList<Content> list = aService.selectAllTemplates(pi);
		
		
		
		for(Content c : list) {
			System.out.println(c);
			c.setUserId(aService.selectUser(c.getUserNo()));
		}
		
		mv.addObject("list", list).addObject("pi",pi);
		mv.setViewName("managing_templates");
		
		return mv;
	}
	
	@GetMapping("requestPost")
	public ModelAndView joinrequestPost(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = aService.getrequestPostCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		ArrayList<Content> list = aService.selectAllRequestPost(pi);
		
		for(Content c : list) {
			c.setUserId(aService.selectUser(c.getUserNo()));
		}
		
		mv.addObject("list", list).addObject("pi", pi);
		mv.setViewName("Managing_request_posts");
		return mv;
	}
	
	@GetMapping("mupdate")
	@ResponseBody
	public int userUpdate(@ModelAttribute Users user) {
		return aService.userUpdate(user);
	}
	
	@GetMapping("iupdate")
	@ResponseBody
	public int inquiryUpdate(@ModelAttribute Content content) {
		if(content.getContentStatus().equals("N")) {
			content.setDeleteStatus("Y");
		}else {
			content.setDeleteStatus("N");
		}
		
		return aService.inquiryUpdate(content);
	}
	
	@GetMapping("tupdate")
	@ResponseBody
	public int templatesUpdate(@ModelAttribute Content content) {
		if(content.getContentStatus().equals("N")) {
			content.setDeleteStatus("Y");
		}else {
			content.setDeleteStatus("N");
		}
		
		return aService.templatesUpdate(content);
	}
	
	@GetMapping("rupdate")
	@ResponseBody
	public int requestUpdate(@ModelAttribute Content content) {
		if(content.getContentStatus().equals("N")) {
			content.setDeleteStatus("Y");
		}else {
			content.setDeleteStatus("N");
		}
		
		return aService.requestUpdate(content);
	}
	
	
	
	
}
