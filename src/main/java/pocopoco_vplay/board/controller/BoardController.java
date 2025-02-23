package pocopoco_vplay.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pocopoco_vplay.board.model.service.BoardService;
import pocopoco_vplay.users.exception.UsersException;
import pocopoco_vplay.users.model.vo.Users;

@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
@SessionAttributes("loginUser")
public class BoardController {
	private final BoardService bService;
	
	@GetMapping("video-templates-list")
	public String joinVideoTemplatesList() {
		return "videoTemplates_list";
	}
	
	@GetMapping("selectCategory")
	@ResponseBody
	public ArrayList<HashMap<String, Object>> selectCategory(@RequestParam("value") String menu , Model model , HttpSession session) {
		Users loginUser = (Users)session.getAttribute("loginUser");
		ArrayList<HashMap<String,Object>> list =bService.selectCategory(menu);
		System.out.println(list);
		if(loginUser != null) {
			return list;
		}else {
			throw new UsersException("오류");
		}
		
 
	}
}
