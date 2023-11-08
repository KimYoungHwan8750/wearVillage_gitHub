package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.getChatPostCount.myPageCountPostSVC;
import com.example.wearVillage.DAO.myPageDeleteDAO.DeleteAccountSVC;
import com.example.wearVillage.DAO.myPageDeleteDAO.IdDeleteForm;
import com.example.wearVillage.DAO.myPageGetMiliageDAO.MyPageGetMiliageForm;
import com.example.wearVillage.DAO.myPageGetMiliageDAO.MyPageGetMiliageSVC;
import com.example.wearVillage.DAO.myPageGetMyPostCountsDAO.myPageGetPostCountSVC;
import com.example.wearVillage.DAO.myPagePWChangeDAO.ChangeUserPWForm;
import com.example.wearVillage.DAO.myPagePWChangeDAO.myPagePWChangeSVC;
import com.example.wearVillage.DAO.myPagePaymentDAO.PaymentForm;
import com.example.wearVillage.DAO.myPagePaymentDAO.PaymentSVC;
import com.example.wearVillage.DAO.myPageProfileImageDAO.USER_INFO_FORM;
import com.example.wearVillage.DAO.myPageProfileImageDAO.myPageProfileImageSVC;
import com.example.wearVillage.DAO.myPageTossPaymentDAO.TossPaymentForm;
import com.example.wearVillage.DAO.myPageTossPaymentDAO.TossPaymentSVC;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class pjyMyPageController {

    private final myPageProfileImageSVC ImageSVC;
    private final myPagePWChangeSVC changePWSVC;
    private final myPageCountPostSVC countPostSVC;
    private final PaymentSVC paymentSVC;
    private final TossPaymentSVC tossPaymentSVC;
    private final MyPageGetMiliageSVC myPageGetMiliageSVC;
    private final myPageGetPostCountSVC myPageGetCountsSVC;
    private final DeleteAccountSVC deleteAccountSVC;

    @Value("${kapi.apikey}")
    private String kapiApiKey;


    @GetMapping("/mypage")
    public ModelAndView myPage(ModelAndView mav, HttpSession session, USER_INFO_FORM userInfoForm, MyPageGetMiliageForm myPageGetMiliageForm,
            @ModelAttribute ChangeUserPWForm changeUserPWForm) {
        if (session.getAttribute("email") != null) {
            String userId = (String) session.getAttribute("id");
            String nickname = (String) session.getAttribute("nickname");
            String email = (String) session.getAttribute("email");
            log.info("{}'님이 로그인했습니다.'", nickname);
            int countedPost = countPostSVC.countPost(userId,nickname);
            Integer miliage = myPageGetMiliageSVC.getMiliage(userId,myPageGetMiliageForm);

            int amount = miliage;
            DecimalFormat df = new DecimalFormat("###,###");
            String money = df.format(amount);

            mav.addObject("miliage",money);
            mav.addObject("email",email);
            mav.addObject("kapiApiKey",kapiApiKey);
            mav.addObject("countedPost",countedPost);
            mav.addObject("userId",userId);
            mav.setViewName("myPage");
            return mav;
        } else {
            mav.setViewName("login");
            return mav;
        }
    }

    @ResponseBody
    @GetMapping("/update/profileImg")
    public boolean myPageProfileUpload(@RequestParam String url, HttpSession session, Model model) {
        String Userid = (String) session.getAttribute("id");
        log.info("UserID={}", Userid);
        log.info("url={}", url);
        int updatedRow = ImageSVC.changeProfileImage(Userid, url);
        log.info("updatedRow={}", updatedRow);
        session.removeAttribute("profileimg");
        log.info("url={}", url);
        session.setAttribute("profileimg", url);
        return true;
    }

    // @PostMapping("/update/changePW")
    // public ModelAndView changePW(HttpSession session, @ModelAttribute @Valid ChangeUserPWForm changeUserPWForm, BindingResult bindingResult){
    //     ModelAndView mav = new ModelAndView();
    //     String userId = (String) session.getAttribute("id");
    //     if(bindingResult.hasErrors()){
    //         bindingResult.rejectValue("newPW","newPW","머하냐");
    //         mav.addObject("changeUserPWForm", changeUserPWForm);
    //         mav.setViewName("myPage");
    //         return mav;
    //     }
    //     int row = changePWSVC.changePW(userId,changeUserPWForm);
    //     log.info("컨트롤러 row = {}",row);
    //     mav.setViewName("myPage");
    //     return mav;
    // }
//    @PostMapping("/update/changePW")
//    public ResponseEntity<?> changePassword(HttpSession session, @ModelAttribute @Valid ChangeUserPWForm form, BindingResult bindingResult){
//        //세션들고오기
//        String userId = (String) session.getAttribute("id");
//
//        String findedPW = changePWSVC.getPWbyID(userId);
//        log.info("찾은 비밀번호:{}",findedPW);
//        log.info("입력한 비밀번호:{}",form.getPW());
//
//        if(!findedPW.equals(form.getPW())){
//
//        } else {
//            changePWSVC.changePW(userId,form);
//        }
//
//        return ResponseEntity.ok().build();
//    }

    @ResponseBody
    @PostMapping("/update/changePW")
    public ResponseEntity<?> changePW(HttpSession session, @Valid @RequestBody ChangeUserPWForm form,BindingResult bindingResult){
        String userId = (String) session.getAttribute("id");
        String findedPW = changePWSVC.getPWbyID(userId);
        String getPW = form.getPW();
        String getNewPW = form.getNewPW();
        String getNewPWConfirm = form.getNewPWConfirm();
        log.info("요청받은body : {}",form);
        if(findedPW.equals(getPW)){
            log.info("현재 비밀번호 맞음. changePWSVC시작");
            if(!getNewPW.equals(getNewPWConfirm)){
                log.info("새로운 비밀번호와 새로운 비밀번호 확인이 일치하지 않습니다.");
                return ResponseEntity.badRequest().body("신규 비밀번호와 비밀번호 확인이    일치하지 않습니다.");
            } else{
                int changedRow = changePWSVC.changePW(userId,form);
                if(changedRow == 0){
                    return ResponseEntity.badRequest().body("비밀번호가 정상적으로 수정되지 않았습니다.(서버에러)");
                }
                log.info("res = {}",ResponseEntity.ok().body("zz"));
                return ResponseEntity.ok().body("zz");
            }
        }else{
            log.info("현재 비밀번호가 일치하지 않음.");
            return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다. 확인해주세요.");
        }

    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex){
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된요청");

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/successKapi")
    public ModelAndView successKapi(@RequestParam String pg_token, HttpSession session){
        ModelAndView mav = new ModelAndView();
        String idSession = (String) session.getAttribute("id");
        String emailSession = (String) session.getAttribute("email");
        log.info("email세션:{}",emailSession);
        log.info("id세션:{}",idSession);

        mav.addObject("idSession",idSession);
        mav.addObject("sessionEmail",emailSession);
        mav.addObject("pg_token",pg_token);
        mav.setViewName("KapiAccept");
        return mav;
    }

    @PostMapping("/paymentSave")
    public String paymentDataToDB(@ModelAttribute PaymentForm paymentForm) {
        paymentSVC.paymentDateToDB(paymentForm);
        return "myPage";
    }

    @PostMapping("/tossPaymentSave")
    public String tossPaymentToDB(@ModelAttribute TossPaymentForm tossPaymentForm){
        tossPaymentSVC.tossPaymentToDB(tossPaymentForm);
        return "myPage";
    }

    @GetMapping("/deleteId")
    public String gotoDelete(){
        return "deleteId";
    }

    @PostMapping("/deleteId")
    public ModelAndView deleteAccount(HttpSession session, IdDeleteForm idDeleteForm){
        ModelAndView mav = new ModelAndView("main");
        String sid = (String) session.getAttribute("id");
        log.info(sid);
        log.info(idDeleteForm.getId());
        try{
            if(deleteAccountSVC.howMuchmiliage(idDeleteForm.getId())){
                if(sid.equals(idDeleteForm.getId())){
                    deleteAccountSVC.DeleteAccount(idDeleteForm.getId());
                    mav.addObject("alertMsg","성공적으로 삭제되었습니다.");
                } else {
                    mav.addObject("errMessage","연결이 끊겼습니다. 다시 로그인 해주세요.");
                    mav.setViewName("deleteId");
                }
            } else if (!deleteAccountSVC.howMuchmiliage(idDeleteForm.getId())){
                mav.addObject("errMessage","남은 마일리지가 있을시 회원 탈퇴가 불가능합니다.");
                mav.setViewName("deleteId");
            }
        } catch (NullPointerException e){
            mav.addObject("errMessage","아이디를 정확히 입력해주세요");
            mav.setViewName("deleteId");
        }
        session.invalidate();
        return mav;
    }
}
