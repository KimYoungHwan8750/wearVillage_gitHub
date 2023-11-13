package com.example.wearVillage.Controller;


import com.example.wearVillage.DAO.AskPostDAO;
import com.example.wearVillage.DAO.FaqPostDAO;
import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class CsController {

    private final AskPostDAO askPostDAO;
    private final FaqPostDAO faqPostDAO;

    @GetMapping("/askpost") // 1:1 문의 등록 페이지 호출
    public String askpost(HttpSession session){
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            return "askpost";
        }
    }

    // 쿼리파라미터
    @GetMapping("/askdetail/{askpostid}")
    public String askdetail(@PathVariable String askpostid,
                            Model model,
                            HttpSession session){
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            AskObject askObject = askPostDAO.askFind(askpostid);
            model.addAttribute("askObject",askObject);
            return "askdetail";
        }
    }

    @GetMapping("/asklist/{currentPage}")
    public String asklist(@PathVariable(required = false) Long currentPage, HttpSession session, Model model){
        Long defaultPage = (currentPage != null) ? currentPage : 1L;
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}",sid);
            List<AskObject> askObjectList = askPostDAO.askFindAll(sid);
            // 총 게시물 수
            int totalAskObjects = askObjectList.size();
            // 페이지당 게시물 수
            int pageSize = 10; // 예시로 페이지당 10개의 게시물을 보여줄 것이라 가정합니다.
            // 전체 페이지 수 계산
            int totalPages = (int) Math.ceil((double) totalAskObjects / pageSize);
            // 현재 페이지에 해당하는 게시물 리스트
            int startIndex = (int) ((defaultPage - 1) * pageSize);
            int endIndex = Math.min(startIndex + pageSize, totalAskObjects);
            List<AskObject> currentPageAskObjects = askObjectList.subList(startIndex, endIndex);

            model.addAttribute("askObjectList", currentPageAskObjects);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", defaultPage);
            /*
                    현재 페이지가 1 이면, 이전 없음 / 총 페이지가 6 이상이면, 다음 있음
                    현재 페이지가 6 이면, 이전 있음 / 총 페이지가 11 이상이면, 다음 있음 - 총 페이지가 10 이하이면, 다음 없음
                    현재 페이지가 11이면, 이전 있음 / 총 페이지가 16 이상이면, 다음 있음 - 총 페이지가 15 이하이면, 다음 없음

                    if(currentPage==1) 이전 X   /  if(totalPage > 5--(currentPage + 4)--) 다음 O
                    if(currentPage==6) 이전 O   /  if(totalPage > 10--(currentPage + 4)--) 다음 O / if(totalPage < 11--(currentPage + 5)--) 다음 X
                    if(currentPage==11) 이전 O   /  if(totalPage > 15--(currentPage + 4)--) 다음 O / if(totalPage < 16--(currentPage + 5)--) 다음 X

                    currentPage > 5 [이전 있음] >> currentPage 가 6 이상이라면 항상 이전 버튼 "존재"
                    currentPage // totalPage > (currentPage + 4) [다음 있음] // totalPage < (currentPage + 5) [다음 없음] >> "존재"
             */
            return "asklist";
        }
    }

    @GetMapping("/FAQlist")         // GET http://localhost:8090/FAQlist
    public String FAQlist(Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            List<FaqObject> faqObjectList = faqPostDAO.faqFindAll();
            model.addAttribute("FAQlist",faqObjectList);
            log.info("FAQlist={}",faqObjectList);
            return "FAQlist";
        }
    }

    @GetMapping("/FAQ/{faqpostid}")             // GET http://localhost:8090/FAQ/{PathVariable}
    public String FAQ(@PathVariable String faqpostid, Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            FaqObject faqObject = faqPostDAO.faqFind(faqpostid);
            model.addAttribute("faqObject",faqObject);
            return "FAQ";
        }
    }

    @GetMapping("/supportmain")     // GET http://localhost:8090/supportmain
    public String supportMain(HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            String sid = (String) session.getAttribute("id");
            log.info("sid={}", sid);
            return "support_main";
        }
    }

    @PostMapping("/askwrite")              // POST http://localhost:8090/askwrite
    public String askWrite(AskObject askObject, Model model, HttpSession session) {
        if(session.getAttribute("id")==null){
            log.info("세션 없음");
            return "/login";
        } else {
            Object sessionId = session.getAttribute("id");
            askObject.setId(sessionId.toString());
            String askpostid = askPostDAO.askWrite(askObject);
            if(!(askpostid.isEmpty())) { // 성공
                model.addAttribute("askdetail", askObject);
                return "redirect:/askdetail/"+askpostid; // 상세 페이지로 갈때 컨트롤러 엔드포인트
            } else {    // 실패
                return "redirect:/errorpage"; // 실패 시 에러페이지
            }
        }
    }

    //삭제          http://localhost:8090/askdelete/85       삭제로직을 보내는 주소
    @GetMapping("/askdelete/{askpostid}")
    public String askDelete(@PathVariable("askpostid") String askpostid,
                            HttpSession session) {
        if (session.getAttribute("id") == null) {
            log.info("세션 없음");
            return "/login";
        } else {
            String id = (String) session.getAttribute("id");
            log.info("sid={}", id);
            boolean b_result = askPostDAO.askDelete(askpostid, id);
            if (b_result) {
                return "redirect:/asklist/1";
            } else {
                return "redirect:/errorpage";
            }
        }
    }





}
