package com.example.wearVillage.testArea.findByEmailProject.Controller.APIResponse;

import lombok.Getter;

@Getter
public class APIResponse<T> {
    String status;
    T Body;
    String divMessage;
    public APIResponse(T Body){
        this.Body = Body;
    }

    public APIResponse(){}
    public APIResponse<T> isSuccess(){
        this.status = "Success";
        return this;
    }

    public APIResponse<T> isFail(){
        this.status = "Fail";
        return this;
    }
    public APIResponse<T> setDivMessage(String str){
        this.divMessage = str;
        return this;
    }
}
