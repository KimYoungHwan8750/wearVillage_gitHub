package com.example.wearVillage.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.wearVillage.DeleteEvent.DeleteSVC;
import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import com.example.wearVillage.UpdateEvent.UpdateEntityService;
import com.example.wearVillage.UpdateEvent.UpdateRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @Autowired
    private UpdateEntityService updateEntityService;


    //    @GetMapping("/updateForm/{id}")
//    public String viewPost2(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//        // 게시글 조회
//        String selectQuery = "SELECT * FROM POSTING_TABLE WHERE POST_ID = ?";
//        PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);
//
//        // 모델에 추가
//        redirectAttributes.addFlashAttribute("post", postData);
//
//        return "redirect:/update/" + id;
//    }
//    @ResponseBody
//    @GetMapping("/update/{id}")
//    public ResponseEntity<Void> updatePost(@PathVariable int id, @RequestBody UpdateRequest updateRequest){
//        try{
//            updateEntityService.updatePost(id,
//                                            updateRequest.getSubtitle(),
//                                            updateRequest.getText());
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        // 게시글 조회
        String selectQuery = "SELECT * FROM POSTING_TABLE WHERE POST_ID = ?";
        log.info("쿼리호출할게~{}",selectQuery);
        PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);
        log.info("쿼리호출했어~");
        model.addAttribute("postData", postData);
        log.info("쿼리넣었어~{}",postData.getPostId());
        return "update_posting";  // 수정 폼 페이지 반환
    }

    // 게시글 업데이트 요청 처리
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable int id, @ModelAttribute UpdateRequest updateRequest) {
        try {
            updateEntityService.updatePost(id,
                    updateRequest.getPostSubtitle(),
                    updateRequest.getPostPrice(),
                    updateRequest.getPostRentDefaultPrice(),
                    updateRequest.getPostRentDayPrice(),
                    updateRequest.getPostTagTop(),
                    updateRequest.getPostTagMiddle(),
                    updateRequest.getPostMapInfo(),
                    updateRequest.getPostText(),
                    updateRequest.getPostModifyDate());
            return "redirect:/posts";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/search/{query}")
    public ModelAndView searchQuery(@PathVariable String query, ModelAndView mav, PostData postData){
        mav.setViewName("searchedPosting");

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        StringBuffer sql = new StringBuffer();
        sql.append("select * from Posting_Table ");
        sql.append("where POST_SUBTITLE like :query ");
        sql.append("      or Post_Tag_Top like :query ");
        sql.append("      or Post_Tag_Middle like :query ");

        Map<String,Object>param = new HashMap<>();
        param.put("query","%"+query+"%");

        RowMapper mapper = new BeanPropertyRowMapper<>(PostData.class);

        List<PostData> searchedPost = template.query(sql.toString(), param, mapper);

        for (PostData SP : searchedPost) {
            String postText = SP.getPostText();

            Document doc = Jsoup.parse(postText);
            Element img = doc.select("img").first();

            if (img != null) {
                String imgUrl = img.attr("src");
                SP.setPostThumbnailUrl(imgUrl);
            }
        }

        mav.addObject("searchedPost",searchedPost);
        mav.addObject("postData",postData);
        log.info("검색결과={}",searchedPost);



        return mav;
    }
}