package pocopoco_vplay.board.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pocopoco_vplay.board.model.vo.Content;

@Mapper
public interface BoardMapper {

	ArrayList<HashMap<String, Object>> selectCategory(@Param("menu") String menu, @Param("userNo") int userNo);

	ArrayList<Content> selectCategoryMyProjects(@Param("menu") String menu, @Param("userNo") int userNo);

}
