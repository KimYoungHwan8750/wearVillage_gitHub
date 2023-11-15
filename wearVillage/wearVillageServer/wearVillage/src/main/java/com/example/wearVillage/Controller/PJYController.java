package com.example.wearVillage.Controller;



import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.SimpleFormatter;

import com.example.wearVillage.DAO.ProductBuyDAO.*;
import com.example.wearVillage.DAO.findIDPW.FindIdForm;
import com.example.wearVillage.DAO.findIDPW.FindPwForm;
import com.example.wearVillage.DAO.findIDPW.FindSVC;
import com.example.wearVillage.DAO.findIDPW.NewPwForm;
import com.example.wearVillage.DeleteEvent.DeleteSVC;
import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import com.example.wearVillage.UpdateEvent.UpdateEntityRepository;
import com.example.wearVillage.UpdateEvent.UpdateEntityService;
import com.example.wearVillage.UpdateEvent.UpdateRequest;
import com.example.wearVillage.UpdateEvent.posting_table;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.regex.qual.Regex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor

public class PJYController {

    private final JdbcTemplate jdbcTemplate;
    private final EmailService emailService;
    private final DeleteSVC deleteSVC;
    private final FindSVC findSVC;
    private final UpdateEntityRepository updateEntityRepository;
    private final ProductBuyDAO productBuyDAO;

    //DAO - > SVC 수정필
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
        } else if (postTagTop != null) {
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

        String countSql = "SELECT COUNT(*) FROM POSTING_TABLE";
        if (postTagMiddle != null) {
            countSql += " WHERE POST_TAG_MIDDLE=:postTagMiddle";
        } else if (postTagTop != null) {
            countSql += " WHERE POST_TAG_TOP=:postTagTop";
        }
        Integer totalPosts = namedParameterJdbcTemplate.queryForObject(countSql, params, Integer.class);

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


    @GetMapping("/mail/send")
    public String mail() {
        return "memberjoin.html";
    }

    @ResponseBody
    @PostMapping("/mail/send")
    public String sendMail(HttpSession session, GmailDto gmailDto) throws MessagingException {
        int gmailAuthCode = emailService.sendMimeMessage(gmailDto, session);
        session.setAttribute("gmailAuthCode", gmailAuthCode);
        session.setMaxInactiveInterval(1 * 60);
        log.info("메일 송신 완료, 번호={}", gmailAuthCode);
        return "memberjoin.html";
    }

    @ResponseBody
    @PostMapping("/verify")
    public Map<String, Boolean> verify(HttpSession session, @RequestBody Map<String, String> payload) {
        Integer sessionAuthCode = (Integer) session.getAttribute("authCode");

        Map<String, Boolean> response = new HashMap<>();

        if (sessionAuthCode != null && sessionAuthCode.equals(Integer.parseInt(payload.get("authCode")))) {
            log.info("메일 인증 성공");
            response.put("success", true);
        } else {
            log.info("메일 인증 실패");
            response.put("success", false);
        }
        return response;
    }
//    삭제

    @GetMapping("/delete/viewPost2")
    public String deleteById(@RequestParam Long id) {
        int deletedRow = deleteSVC.deleteById(id);
        log.info("요청보냄. id={}", id);
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
    public String editPost(@PathVariable("id") Long id, Model model, HttpSession session) {
        // 게시글 조회
        if (session.getAttribute("email") != null) {
            String selectQuery = "SELECT * FROM POSTING_TABLE WHERE POST_ID = ?";
            PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);
            model.addAttribute("postData", postData);
            model.addAttribute("editMode", "edit");
            return "posting.html";  // 수정 폼 페이지 반환
        } else {
            return "redirect:/login";
        }
    }


//
//    // 게시글 업데이트 요청 처리
//    @PostMapping("/edit")
//    public String updatePost(@PathVariable int id, @ModelAttribute UpdateRequest updateRequest) {
//        try {
//            updateEntityService.updatePost(id,
//                    updateRequest.getPostSubtitle(),
//                    updateRequest.getPostPrice(),
//                    updateRequest.getPostRentDefaultPrice(),
//                    updateRequest.getPostRentDayPrice(),
//                    updateRequest.getPostTagTop(),
//                    updateRequest.getPostTagMiddle(),
//                    updateRequest.getPostMapInfo(),
//                    updateRequest.getPostText(),
//                    updateRequest.getPostModifyDate());
//            return "redirect:/posts";
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return "error";
//        }
//    }


    // 게시글 업데이트 요청 처리
    @PostMapping("/edit")
    @ResponseBody
    public String updatePost(@RequestBody posting_table postData) {
        try {
            updateEntityRepository.save(postData);
            return "수정 성공!";
        } catch (Exception e) {
            log.error("에러내용={}", e.getMessage());
            return "수정 실패.";
        }
    }

    @GetMapping("/search/{query}")
    public ModelAndView searchQuery(@PathVariable String query, ModelAndView mav, PostData postData) {
        mav.setViewName("searchedPosting");

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        StringBuffer sql = new StringBuffer();
        sql.append("select * from Posting_Table ");
        sql.append("where POST_SUBTITLE like :query ");

        Map<String, Object> param = new HashMap<>();
        param.put("query", "%" + query + "%");

        RowMapper<PostData> mapper = new BeanPropertyRowMapper<>(PostData.class);

        try{
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
            mav.addObject("searchedPost", searchedPost);
            StringBuffer sql2 = new StringBuffer();
            sql2.append("select * from Posting_Table ");
            sql2.append("where Post_Tag_Top like :query ");
            sql2.append(   "or Post_Tag_Middle like :query ");

            Map<String, Object> param2 = new HashMap<>();
            param2.put("query", "%" + query + "%");

            RowMapper<PostData> mapper2 = new BeanPropertyRowMapper<>(PostData.class);

            try{
                List<PostData> categorySearchedPost = template.query(sql2.toString(), param2, mapper2);
                for (PostData CSP : categorySearchedPost) {
                    String postText = CSP.getPostText();

                    Document doc = Jsoup.parse(postText);
                    Element img = doc.select("img").first();

                    if (img != null) {
                        String imgUrl = img.attr("src");
                        CSP.setPostThumbnailUrl(imgUrl);
                    }
                }
                mav.addObject("categorySearchedPost",categorySearchedPost);
                log.info("카테고리검색결과={}",categorySearchedPost);
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }

            log.info("검색결과={}", searchedPost);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


        mav.addObject("postData", postData);
        return mav;
    }

    //아이디 찾기
    @GetMapping("/login/findId")
    public ModelAndView findId() {
        ModelAndView mav = new ModelAndView("findId");

        return mav;
    }

    @PostMapping("/login/findId")
    public ModelAndView findIdByEmail(FindIdForm findIdForm) {
        ModelAndView mav = new ModelAndView("findedId");
        String findedId = findSVC.findId(findIdForm.getEmail());
        mav.addObject("findedId", findedId);
        return mav;
    }

    //비밀번호 찾기
    @GetMapping("/login/findPw")
    public ModelAndView findPw() {
        ModelAndView mav = new ModelAndView("findPw");

        return mav;
    }


    @PostMapping("/login/findPw")
    public ModelAndView findPwByIdAndEmail(HttpSession session, FindPwForm findPwForm) {
        ModelAndView mav = new ModelAndView("findPwAuth");
        String AuthCode = findSVC.findPwByIdAndPw(findPwForm.getId(), findPwForm.getEmail());
        //조회에 실패한 경우
        if (AuthCode == "조회 실패") {
            mav.setViewName("findPw");
            mav.addObject("errMessage", "회원 정보를 찾을수 없습니다.");
            return mav;
            //메일 발송이 불가능한 경우
        } else if (AuthCode == "메일 발송 불가능") {
            mav.setViewName("findPw");
            mav.addObject("errMessage", "현재 메일 서비스가 불가능합니다.");
            return mav;
            //메일 발송
        } else {
            session.setAttribute("id", findPwForm.getId());
            session.setAttribute("email", findPwForm.getEmail());
            session.setAttribute("AuthCode", AuthCode);
            return mav;
        }
    }

    @PostMapping("/login/tempPw")
    public ModelAndView setTempPw(HttpSession session, NewPwForm newPwForm) {
        ModelAndView mav = new ModelAndView("findPwAuth");
        GmailDto dto = new GmailDto();
        dto.setAddress(newPwForm.getEmail());
        try {
            String tempPw = Integer.toString(emailService.sendPwMail(dto, session));
            findSVC.setTempPw(newPwForm.getEmail(), newPwForm.getId(), tempPw);
            mav.addObject("resultSubtitle", "임시 비밀번호 발급 성공");
            mav.addObject("resultMsg", "임시 비밀번호 발급 성공에 성공했습니다. 메일을 확인해주세요.");
            mav.addObject("status", 1);
            return mav;
        } catch (MessagingException e) {
            mav.addObject("resultSubtitle", "임시 비밀번호 발급 실패");
            mav.addObject("resultMsg", "임시 비밀번호 발급 실패했습니다. 한번더 확인해주세요.");
            mav.addObject("status", 1);
            return mav;
        }
    }

    @ResponseBody
    @PostMapping("/buyCall")
    public ProductBuyForm buyCall(@ModelAttribute ProductBuyForm productBuyForm) {
        log.info("pF={}", productBuyForm);
        ProductBuyForm madeForm = productBuyDAO.readyToTrade(productBuyForm);
        log.info("madeForm = {}", madeForm);
        if (madeForm.getBuyerId().isEmpty()) {
            return null;
        } else {
            log.info("madeForm탐");
            return madeForm;
        }
    }

    @GetMapping("/product")
    public String buyProduct(Model model, HttpSession session) {
        Object madeForm = model.getAttribute("madeForm");
        log.info("madeForm = {}", madeForm);
        if (madeForm == null) {
            return "error/404";
        } else {
            session.setAttribute("madeForm", madeForm);
            return "productControllPannel";
        }
    }

    @PostMapping("/tradeCall")
    public ModelAndView tradeCall(ProductBuyForm productBuyForm) {
        ModelAndView mav = new ModelAndView();
        log.info("pbF={}", productBuyForm);
        String result = productBuyDAO.trade(productBuyForm);
        if (result.equals("구매 완료")) {
            log.info("구매완료");
            //TODO 구매신청메세지
            mav.addObject("productInfo", productBuyForm);
            mav.setViewName("tradeToChat");
        } else {
            log.info("구매실패");
            mav.setViewName("postDetail3");
        }
        return mav;
    }

    @PostMapping("/rentCall")
    public ModelAndView rentCall(ProductRentForm productRentForm) {
        ModelAndView mav = new ModelAndView();
        log.info("pf={}", productRentForm);
        String result = productBuyDAO.trade2(productRentForm);
        int a = Integer.valueOf(productRentForm.getRentFinishDay());
        log.info("a={}", a);
        String finalDay = String.valueOf(a + 3);
        log.info("int finalDay={}", finalDay);

//      middleMiliage를 구하기 위한 형변환
        String regprice = productRentForm.getPrice().replaceAll("[^0-9]", "");
        int price = Integer.valueOf(regprice);

        String regRentDefaultPrice = productRentForm.getRentDefaultPrice().replaceAll("[^0-9]", "");
        int rentDefaultPrice = Integer.valueOf(regRentDefaultPrice);

        String regRentDayPrice = productRentForm.getRentDayPrice().replaceAll("[^0-9]", "");
        int rentDayPrice = Integer.valueOf(regRentDayPrice);

        String rentStartDay = productRentForm.getRentStartDay();
        String rentFinishDay = productRentForm.getRentFinishDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate dateA = LocalDate.parse(rentStartDay, formatter);
        LocalDate dateB = LocalDate.parse(rentFinishDay, formatter);
        long rentDays = ChronoUnit.DAYS.between(dateA, dateB);

        String middleMiliage = String.valueOf(price - (rentDefaultPrice + (rentDayPrice * rentDays)));
        log.info("string middleMiliage = {}", middleMiliage);

        if (result.equals("대여 완료")) {
            //TODO 대여신청메세지
            log.info("대여완료");
//            파이널폼 생성
            ProductFinalForm productFinalForm = new ProductFinalForm(productRentForm, middleMiliage, finalDay);
            int finalResult = productBuyDAO.tradeFinal(productFinalForm);
            log.info("final={}", finalResult);
            mav.addObject("productInfo", productRentForm);
            mav.setViewName("tradeToChat");
        } else {
            log.info("대여실패");
        }
        return mav;
    }

    @Bean
    @Scheduled(fixedDelay = 86400000) // 하루마다 체크한다.
//    @Scheduled(fixedDelay = 20000)
    public void check() {
        //DB를 긁어와서 data에 List를 저장
        List<RentData> data = productBuyDAO.checkPerDay();
        //List를 이용해서 rent_check 0과 1구별하여 반납이 안된 대여글 조회
        Iterator<RentData> iterData = data.iterator();
        while (iterData.hasNext()) {
            RentData element = iterData.next();
            String isRent = element.getReturnCheck();
            //rent_Check 값이 0(false)일때
            if (isRent.equals("0")) {
                //rent_Check가 0(false)이면서 finalDay를 초과한경우
                //middleMiliage를 회수하여 판매자에게 줘야한다.

                //오늘 날짜 String으로
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String formattedToday = formatter.format(today);

                //날짜들 int로
                String thisFinalDay = productBuyDAO.checkFinalDay(element.getTradeId());
                int intToday = Integer.valueOf(formattedToday);
                int intFinDay = Integer.valueOf(thisFinalDay);

                //finalDay 초과시
                if (intToday > intFinDay) {
                    log.info("tid {}의 대여 날짜 초과. middleMiliage를 회수하여 판매자에게 준다.", element.getTradeId());
                    //tid의 중립 마일리지 조회
                    String middleMiliage = productBuyDAO.getMiddleMiliage(element.getTradeId());
                    //중립마일리지 int로
                    int intMiliage = Integer.valueOf(middleMiliage);
                    //판매자 조회
                    String sellerId = productBuyDAO.whoIsSeller(element.getTradeId());
                    //마일리지 반환
                    productBuyDAO.returnMiliage(sellerId, intMiliage);
                    //rent_Check to 2(거래종료)
                    productBuyDAO.completeTrade(element.getTradeId());
                }
            } else if (isRent.equals("1")) {
                //rent_Check 값이 1(true)일때
                //구매자는 middleMiliage를 돌려받아야한다.
                //tid의 중립 마일리지 조회
                String middleMiliage = productBuyDAO.getMiddleMiliage(element.getTradeId());
                //중립마일리지 int로
                int intMiliage = Integer.valueOf(middleMiliage);
                //구매자 조회
                String buyerId = productBuyDAO.whoIsBuyer(element.getTradeId());
                //마일리지 반환
                productBuyDAO.returnMiliage(buyerId, intMiliage);
                //rent_Check to 2(거래종료)
                productBuyDAO.completeTrade(element.getTradeId());
            }
        }
    }

    @GetMapping("/rentComplete/{tid}")
    public ModelAndView rentComplete(@PathVariable String tid,HttpSession session){
        ModelAndView mav = new ModelAndView("myPage");
        log.info("rentComplete들어옴");
        String sid = (String) session.getAttribute("id");
        log.info("sid={}",sid);
        String sellerId = productBuyDAO.whoIsSeller(tid);
        log.info("sellerId={}",sellerId);
        if(sid.equals(sellerId)){
            productBuyDAO.sellerCompleteTrade(tid);
        } else {
            mav.addObject("sessionErrMessage","에러가 발생했습니다.");
        }
        return mav;
    }

    @GetMapping("/test123123")
    public String test123123(){

        return "filetest";
    }
}