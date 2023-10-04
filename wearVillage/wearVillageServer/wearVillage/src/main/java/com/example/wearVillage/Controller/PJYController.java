package com.example.wearVillage.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wearVillage.DeleteEvent.DeleteSVC;
import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;
@Slf4j
@org.springframework.stereotype.Controller
//@RequiredArgsConstructor

public class PJYController {

    private final JdbcTemplate jdbcTemplate;
    private final EmailService emailService;
    private final DeleteSVC deleteSVC;

    @GetMapping("/posts")
    public ModelAndView listPosts(@RequestParam(defaultValue = "0") int page,
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
        if(postTagMiddle!=null){
            countSql+=" WHERE POST_TAG_MIDDLE=:postTagMiddle";
        }else if(postTagTop!=null){
            countSql+=" WHERE POST_TAG_TOP=:postTagTop";
        }
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




    public PJYController(EmailService emailService, JdbcTemplate jdbcTemplate, DeleteSVC deleteSVC) {
        this.jdbcTemplate = jdbcTemplate;
        this.emailService = emailService;
        this.deleteSVC = deleteSVC;
    }
    
    @GetMapping("/mail/send")
    public String mail(){
        return "memberjoin.html";
    }

    @ResponseBody
    @PostMapping("/mail/send")
    public String sendMail(HttpSession session, GmailDto gmailDto) throws MessagingException {
        int gmailAuthCode = emailService.sendMimeMessage(gmailDto,session);
        session.setAttribute("gmailAuthCode",gmailAuthCode);
        session.setMaxInactiveInterval(1 * 60);
        log.info("메일 송신 완료, 번호={}",gmailAuthCode);
        return "memberjoin.html";
    }

    @ResponseBody
    @PostMapping("/verify")
    public Map<String, Boolean> verify(HttpSession session, @RequestBody Map<String, String> payload){
        Integer sessionAuthCode = (Integer) session.getAttribute("authCode");

        Map<String, Boolean> response = new HashMap<>();

        if(sessionAuthCode != null && sessionAuthCode.equals(Integer.parseInt(payload.get("authCode")))){
            log.info("메일 인증 성공");
            response.put("success",true);
        } else {
            log.info("메일 인증 실패");
            response.put("success",false);
        }
        return response;
    }
//    삭제

    @GetMapping("/delete/viewPost2")
    public String deleteById(@RequestParam Long id){
        int deletedRow = deleteSVC.deleteById(id);
        log.info("요청보냄. id={}",id);
        return "redirect:/posts";
    }


}
