const $header_login_link = document.querySelector('.header_login_link');
const $header_login_img = document.querySelector('.header_login_img');
const $header_login_text = document.querySelector('.header_login_text');
const $headerTest = document.querySelector('.headerTest');
const $footer_login = document.querySelector('.footer_login.footer_bottom_sub');
let user_status=null;

fetch("/userInfo", ).then(res=>
        res.json()
        .then(
            res=>{
                if(res!=null){
                    $header_login_link.setAttribute("href","/logout");
                    $header_login_text.innerText = "로그아웃";
                    $header_login_img.setAttribute("src","img/img_logout.png");
                    $footer_login.setAttribute("href","/logout");
                    $footer_login.innerText = "로그아웃";
                    user_status = {
                        id:res[0]["ID"],
                        pw:res[0]["PW"],
                        nickname:res[0]["NICKNAME"],
                        email:res[0]["EMAIL"],
                        profileimg:res[0]["PROFILEIMG"],
                        gender:res[0]["GENDER"],
                        theme:res[0]["THEME"],
                        birth:res[0]["BIRTH"]
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
        .catch((err)=>{
        $header_login_link.setAttribute("href","/login");
        $header_login_text.innerText = "로그인";
        $header_login_img.setAttribute("src","img/img_login.png");
        $footer_login.setAttribute("href","/login");
        $footer_login.innerText = "로그인"});

