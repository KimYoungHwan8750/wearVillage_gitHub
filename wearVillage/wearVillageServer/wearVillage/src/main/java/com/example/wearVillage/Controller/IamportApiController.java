package com.example.wearVillage.Controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Locale;

@Controller
public class IamportApiController {
    private IamportClient api;

    @Value("${Iamport.apiKey")
    private String IamportApiKey;

    @Value("${Iamport.apiSecret}")
    private String IamportApiSecret;

    public IamportApiController(){
        this.api = new IamportClient("8170214561284332","UAQq95jvxqL2GwIXwqwol3gZSIxNnKgQrXgisM31DpEz51VFkcbD7rOjmAK2COGRqm0V8z3WJmPjbavS");
    }

    @ResponseBody
    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(
            Model model,
            Locale locale,
            HttpSession session,
            @PathVariable(value="imp_uid") String imp_uid) throws IamportResponseException, IOException{
        return api.paymentByImpUid(imp_uid);
    }
}

