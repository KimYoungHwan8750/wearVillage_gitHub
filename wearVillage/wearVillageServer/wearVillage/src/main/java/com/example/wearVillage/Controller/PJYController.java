package com.example.wearVillage.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
<<<<<<< HEAD
        @RequestParam(defaultValue = "16") int size,
        @RequestParam(required = false) String postTagMiddle,
        @RequestParam(required = false) String postTagTop) { // 추가 파라미터

      int offset = page * size;
      int startRow = offset + 1;
      int endRow = offset + size;

      Map<String, Object> params = new HashMap<>();
      params.put("startRow", startRow);
      params.put("endRow", endRow);

      StringBuilder sqlBuilder = new StringBuilder();
      
      sqlBuilder.append("SELECT * FROM (SELECT t.*, ROWNUM r FROM (SELECT * FROM POSTING_TABLE");

      if (postTagMiddle != null) {
        sqlBuilder.append(" WHERE POST_TAG_MIDDLE=:postTagMiddle");
        params.put("postTagMiddle", postTagMiddle);
      } else if(postTagTop!=null){
        sqlBuilder.append(" WHERE POST_TAG_TOP=:postTagTop");
        params.put("postTagTop", postTagTop);
      }
    
    sqlBuilder.append(" ORDER BY POST_ID DESC) t) WHERE r BETWEEN :startRow AND :endRow");

    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

    List<PostData> posts =
            namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new BeanPropertyRowMapper<>(PostData.class));

            
        for (PostData post : posts) {
          String postText = post.getPostText();

          Document doc = Jsoup.parse(postText);
          Element img = doc.select("img").first();

          if (img != null) {
            String imgUrl = img.attr("src");
            post.setPostThumbnailUrl(imgUrl);
          }
        }

      String countSql="SELECT COUNT(*) FROM POSTING_TABLE";
=======
                                  @RequestParam(defaultValue = "16") int size,
                                  @RequestParam(required = false) String postTagMiddle,
                                  @RequestParam(required = false) String postTagTop) { // 추가 파라미터

        int offset = page * size;
        int startRow = offset + 1;
        int endRow = offset + size;

        Map<String, Object> params = new HashMap<>();
        params.put("startRow", startRow);
        params.put("endRow", endRow);

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT * FROM (SELECT t.*, ROWNUM r FROM (SELECT * FROM POSTING_TABLE");

        if (postTagMiddle != null) {
            sqlBuilder.append(" WHERE POST_TAG_MIDDLE=:postTagMiddle");
            params.put("postTagMiddle", postTagMiddle);
        } else if(postTagTop!=null){
            sqlBuilder.append(" WHERE POST_TAG_TOP=:postTagTop");
            params.put("postTagTop", postTagTop);
        }

        sqlBuilder.append(" ORDER BY POST_ID DESC) t) WHERE r BETWEEN :startRow AND :endRow");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        List<PostData> posts =
                namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new BeanPropertyRowMapper<>(PostData.class));


        for (PostData post : posts) {
            String postText = post.getPostText();

            Document doc = Jsoup.parse(postText);
            Element img = doc.select("img").first();

            if (img != null) {
                String imgUrl = img.attr("src");
                post.setPostThumbnailUrl(imgUrl);
            }
        }

        String countSql="SELECT COUNT(*) FROM POSTING_TABLE";
>>>>>>> 3ea686e29dc9b060e6ae9b93e927c07d70c4c89f
        if(postTagMiddle!=null){
            countSql+=" WHERE POST_TAG_MIDDLE=:postTagMiddle";
        }else if(postTagTop!=null){
            countSql+=" WHERE POST_TAG_TOP=:postTagTop";
        }
<<<<<<< HEAD
    Integer totalPosts=namedParameterJdbcTemplate.queryForObject(countSql,params,Integer.class);

    int totalPages;

    if(totalPosts % size ==0)
    totalPages=totalPosts/size;
    else
    totalPages=totalPosts/size+1;

    ModelAndView modelAndView=new ModelAndView ("items_buy");
    modelAndView.addObject ("posts",posts);

    modelAndView.addObject ("currentPage",page);
    modelAndView.addObject ("totalPages",totalPages);

    return modelAndView;
  }
=======
        Integer totalPosts=namedParameterJdbcTemplate.queryForObject(countSql,params,Integer.class);

        int totalPages;

        if(totalPosts % size ==0)
            totalPages=totalPosts/size;
        else
            totalPages=totalPosts/size+1;

        ModelAndView modelAndView=new ModelAndView ("items_buy");
        modelAndView.addObject ("posts",posts);

        modelAndView.addObject ("currentPage",page);
        modelAndView.addObject ("totalPages",totalPages);

        return modelAndView;
    }
>>>>>>> 3ea686e29dc9b060e6ae9b93e927c07d70c4c89f
}
