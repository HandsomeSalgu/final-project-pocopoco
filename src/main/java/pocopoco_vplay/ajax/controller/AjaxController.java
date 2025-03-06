package pocopoco_vplay.ajax.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pocopoco_vplay.board.model.service.BoardService;
import pocopoco_vplay.board.model.vo.Content;
import pocopoco_vplay.users.model.vo.Users;

@RestController
@RequestMapping("/board/")
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class AjaxController {
	
	private BoardService bService;
	
	@PutMapping("like")
	public int likeAllTemp(@RequestBody HashMap<String, String> map, HttpSession session){
		System.out.println(map);

		int userNo = ((Users)session.getAttribute("loginUser")).getUserNo();
//		map.put("userNo", );
		
		System.out.println(map);
		
		return bService.allTempLike(map);
	}
}
