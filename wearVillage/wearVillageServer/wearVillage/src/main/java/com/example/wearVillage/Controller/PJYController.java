package com.example.wearVillage.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wearVillage.DAO.findIDPW.FindIdForm;
import com.example.wearVillage.DAO.findIDPW.FindPwForm;
import com.example.wearVillage.DAO.findIDPW.FindSVC;
import com.example.wearVillage.DAO.findIDPW.NewPwForm;
import com.example.wearVillage.DeleteEvent.DeleteSVC;
import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import com.example.wearVillage.UpdateEvent.UpdateEntityService;
import com.example.wearVillage.UpdateEvent.UpdateRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor

public class PJYController {

    private final JdbcTemplate jdbcTemplate;
    private final EmailService emailService;
    private final DeleteSVC deleteSVC;
    private final FindSVC findSVC;

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
        PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);
        model.addAttribute("postData", postData);
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

        RowMapper<PostData> mapper = new BeanPropertyRowMapper<>(PostData.class);

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

    //아이디 찾기
    @GetMapping("/login/findId")
    public ModelAndView findId(){
        ModelAndView mav = new ModelAndView("findId");

        return mav;
    }

    @PostMapping("/login/findId")
    public ModelAndView findIdByEmail(FindIdForm findIdForm){
        ModelAndView mav = new ModelAndView("findedId");
        String findedId = findSVC.findId(findIdForm.getEmail());
        mav.addObject("findedId",findedId);
        return mav;
    }

    //비밀번호 찾기
    @GetMapping("/login/findPw")
    public ModelAndView findPw(){
        ModelAndView mav = new ModelAndView("findPw");

        return mav;
    }


    @PostMapping("/login/findPw")
    public ModelAndView findPwByIdAndEmail(HttpSession session,FindPwForm findPwForm){
        ModelAndView mav = new ModelAndView("findPwAuth");
        String AuthCode = findSVC.findPwByIdAndPw(findPwForm.getId(),findPwForm.getEmail());
        //조회에 실패한 경우
        if(AuthCode == "조회 실패"){
            mav.setViewName("findPw");
            mav.addObject("errMessage","회원 정보를 찾을수 없습니다.");
            return mav;
            //메일 발송이 불가능한 경우
        } else if(AuthCode == "메일 발송 불가능"){
            mav.setViewName("findPw");
            mav.addObject("errMessage","현재 메일 서비스가 불가능합니다.");
            return mav;
            //메일 발송
        } else {
            session.setAttribute("id",findPwForm.getId());
            session.setAttribute("email",findPwForm.getEmail());
            session.setAttribute("AuthCode",AuthCode);
            return mav;
        }
    }

    @PostMapping("/login/tempPw")
    public ModelAndView setTempPw(HttpSession session,NewPwForm newPwForm){
        ModelAndView mav = new ModelAndView("findPwAuth");
        GmailDto dto = new GmailDto();
        dto.setAddress(newPwForm.getEmail());
        try {
            String tempPw = Integer.toString(emailService.sendPwMail(dto,session));
            findSVC.setTempPw(newPwForm.getEmail(), newPwForm.getId(), tempPw);
            mav.addObject("resultSubtitle","임시 비밀번호 발급 성공");
            mav.addObject("resultMsg","임시 비밀번호 발급 성공에 성공했습니다. 메일을 확인해주세요.");
            mav.addObject("status",1);
            return mav;
        } catch (MessagingException e) {
            mav.addObject("resultSubtitle","임시 비밀번호 발급 실패");
            mav.addObject("resultMsg","임시 비밀번호 발급 실패했습니다. 한번더 확인해주세요.");
            mav.addObject("status",1);
            return mav;
        }
    }
}