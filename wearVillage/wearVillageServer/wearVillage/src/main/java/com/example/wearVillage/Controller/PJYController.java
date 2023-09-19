package com.example.wearVillage.Controller;



import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;

@org.springframework.stereotype.Controller
//@RequiredArgsConstructor

public class PJYController {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PJYController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/posts")
    public ModelAndView listPosts(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "16") int size) {
      int offset = page * size;
      String sql = "SELECT * FROM (SELECT t.*, ROWNUM r FROM (SELECT * FROM POSTING_TABLE ORDER BY POST_ID DESC) t) WHERE r BETWEEN ? AND ?";
      int startRow = offset + 1;
      int endRow = offset + size;
      List<PostData> posts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostData.class), startRow, endRow);
        
      for (PostData post : posts) {
        String postText = post.getPostText();

        Document doc = Jsoup.parse(postText);
        Element img = doc.select("img").first();

        if (img != null) {
          String imgUrl = img.attr("src");
          post.setPostThumbnailUrl(imgUrl);
        }
      }
      
        String countSql = "SELECT COUNT(*) FROM POSTING_TABLE";
        Integer totalPosts = jdbcTemplate.queryForObject(countSql, Integer.class);

        int totalPages;

        if (totalPosts % size == 0)
          totalPages = totalPosts / size;
          else
            totalPages = totalPosts / size + 1;

        ModelAndView modelAndView = new ModelAndView("items_buy");
        modelAndView.addObject("posts", posts);

        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", totalPages);

        return modelAndView;
    }

}
