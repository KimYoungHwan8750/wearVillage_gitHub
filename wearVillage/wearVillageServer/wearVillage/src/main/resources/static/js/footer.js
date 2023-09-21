const $header_login_link = document.querySelector('.header_login_link');
const $header_login_img = document.querySelector('.header_login_img');
const $header_login_text = document.querySelector('.header_login_text');
const $headerTest = document.querySelector('.headerTest');
const $footer_login = document.querySelector('.footer_login.footer_bottom_sub');
let user_status=null;
async () => {
    l
}
fetch("/userInfo").then(res=>
        res.json()
        .then(
            res=>{
                if(res!=""){
                    $header_login_link.setAttribute("href","/logout");
                    $header_login_text.innerText = "로그아웃";
                    $header_login_img.setAttribute("src","img/img_logout.png");
                    $footer_login.setAttribute("href","/logout");
                    $footer_login.innerText = "로그아웃";
                    user_status = {
                        id:res[0]["id"],
                        pw:res[0]["pw"],
                        nickname:res[0]["nickname"],
                        email:res[0]["email"],
                        profileimg:res[0]["profileimg"],
                        gender:res[0]["gender"],
                        theme:res[0]["theme"],
                        birth:res[0]["birth"]
                    };

                } else{
                    $header_login_link.setAttribute("href","/login");
                    $header_login_text.innerText = "로그인";
                    $header_login_img.setAttribute("src","img/img_login.png");
                    $footer_login.setAttribute("href","/login");
                    $footer_login.innerText = "로그인";
                }
            }
        ))
        .catch(err=>console.log(err+": 로그인 여부 확인에 대한 에러"));

