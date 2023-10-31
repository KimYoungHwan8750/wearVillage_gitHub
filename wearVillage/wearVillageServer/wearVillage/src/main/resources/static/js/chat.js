let chat_check = false;
let user_status=null;
//첫 채팅을 쳤을 때 채팅방이 있으면 채팅 진행, 없으면 채팅방 생성
let createChatroomFlag = false;
const $chat_msgArea = document.querySelector(".chat_msgArea");
const $msg = document.getElementById('msg');
const $chat_midContent = document.querySelector('.chat_midContent');
const $button_send = document.getElementById('button-send');
let whoChatBeforeFromHistory_right = false;
let whoChatBeforeFromHistory_left = false;
let whoChatBeforeFromLive_right = null;
let whoChatBeforeFromLive_left = null;
//바로 전 채팅의 시간
let whatTimeBeforeFromHistory_left = null;
let whatTimeBeforeFromHistory_right = null;
//바로 전 채팅이 내 채팅인지 판단
let beforeChatisMyChat_left = null;
let beforeChatisMyChat_right = null;
let firstchat_left= true;
let firstchat_right= true;
let kyhtest=null;
/*디자인 관련 스크립트*/

const $chat_noticeClose = document.querySelector('.chat_noticeClose');
const $chat_notice = document.querySelector('.chat_notice');
const $chat_notice_content = document.querySelector('.chat_notice_content');
setTimeout(()=>{
    $chat_notice.setAttribute('style','transition:all 2s ease-in; opacity:0;');
},4000)
function dateFormater(inputDate){
    let amOrPm = '';
    let hour =inputDate.getHours();
    let minute =inputDate.getMinutes();
    if(hour>11){
        amOrPm="오후";
    }
    else {
        amOrPm="오전";
    }

    if(hour>12){hour-=12;}
    if(minute<10){minute="0"+minute;}

    return amOrPm+" "+hour+":"+minute;
}
fetch("/userInfo",{method:'POST'}).then(res=>
    res.json())
    .then(
        res=>{
            if(res!=null){
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
            }
        }
    )
    .catch((err)=>{
        alert("서버에 오류가 발생했습니다. 자세한 사항은 고객 센터에 문의해주세요.\n 오류 내용:"+err)
    })
JSON.parse($th_chatHistory).forEach(chatMessage => {
    // console.log(chatMessage);
    // console.log("date:"+date);
    // console.log("date.getMinutes()"+date.getMinutes());

    if (JSON.parse($th_sender) == chatMessage['sender']) {
        // RIGHT
        let historydate= new Date(chatMessage['chat_DATE']);
        historydate.getT
        kyhtest=chatMessage['chat_DATE'];
        console.log(chatMessage['chat_DATE']+"날짜")
        //div태그 생성
        let div = document.createElement('div');
        //div태그에 chat_myTextBox 클래스 부여
        div.classList.add('chat_myTextBox');
        //span태그 생성
        let display_userChat = document.createElement('span');
        //span태그 생성
        let display_chatTime = document.createElement('span');
        //display_userChat에 chat_Text와 chat_myText클래스 부여
        display_chatTime.classList.add('chat_displayTime');
        display_userChat.classList.add('chat_Text','chat_myText');
        display_chatTime.innerText = dateFormater(historydate);
        display_chatTime.style.display="none";

        // if(beforeChatisMyChat_right==true&&firstchat_right==false&&historydate.getMinutes()==whatTimeBeforeFromHistory_right){
        // } else {
        //     display_chatTime.innerText = dateFormater(historydate);
        // }
        // 메세지 입력
        if(whoChatBeforeFromHistory_right==false){
            display_userChat.innerText = chatMessage['message']+'프로필이미지';
        } else {
            display_userChat.innerText = decodeURIComponent(chatMessage['message']);
        }
        //b를 div의 자식 태그로 설정
        div.append(display_chatTime,display_userChat);
        //div태그를 chat_msgArea의 자식으로 설정
        $chat_msgArea.append(div);
        //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
        $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
        whoChatBeforeFromHistory_right=true;
        whoChatBeforeFromHistory_left=false;
        beforeChatisMyChat_right = true;
        beforeChatisMyChat_left=false;
        whatTimeBeforeFromHistory_right=historydate.getMinutes();
        firstchat_right=false;
    } else {
        //LEFT
        let historydate= new Date(chatMessage['chat_DATE']);

        //div태그 생성
        let div = document.createElement('div');
        //div태그에 chat_targetTextBox 클래스 부여
        div.classList.add('chat_targetTextBox');
        //span태그 생성
        let display_userChat = document.createElement('span');
        //span태그 생성
        let display_chatTime = document.createElement('span');
        //display_userChat에 chat_Text와 chat_targetText클래스 부여
        display_chatTime.classList.add('chat_displayTime');
        display_userChat.classList.add('chat_Text','chat_targetText');
        let display_profileImg = document.createElement('img');
        display_profileImg.setAttribute('src','/profileimg?fileName='+$th_postUserInfo.PROFILEIMG);
        display_profileImg.classList.add("chat_profileImg");
            display_chatTime.innerText = dateFormater(historydate);
        display_chatTime.style.display="none";


        // if(beforeChatisMyChat_left==true&&firstchat_left==false&&historydate.getMinutes()==whatTimeBeforeFromHistory_left){
        // } else {
        //     display_chatTime.innerText = dateFormater(historydate);
        // }
        display_userChat.innerText = decodeURIComponent(chatMessage['message']);

        // 메세지 입력
        if(whoChatBeforeFromHistory_left==false){
            div.append(display_profileImg,display_userChat,display_chatTime);
        } else {
            div.classList.add("target_SecondChat")
            div.append(display_userChat,display_chatTime);
        }

        //b를 div의 자식 태그로 설정
        //div태그를 chat_msgArea의 자식으로 설정
        $chat_msgArea.append(div);
        //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
        $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
        whoChatBeforeFromHistory_right=false;
        whoChatBeforeFromHistory_left=true;
        beforeChatisMyChat_right = false;
        beforeChatisMyChat_left=true;
        whatTimeBeforeFromHistory_left=historydate.getMinutes();
        console.log(whatTimeBeforeFromHistory_left+"왓타임 레프트");

        firstchat_left=false;

    }




});
document.querySelectorAll('.chat_targetTextBox').forEach(e=> {

        if (e.previousElementSibling != null) {
            if (e.previousElementSibling.lastChild.textContent === e.lastChild.textContent) {
                e.lastChild.style.display = "block";
            }
        }
    }
)

document.querySelectorAll('.chat_myTextBox').forEach(e=> {
        if (e.previousElementSibling == null&&e.nextElementSibling == null) {
            e.firstChild.style.display="block";
        } else if(e.previousElementSibling != null &&e.nextElementSibling!=null&&e.nextElementSibling.firstChild.textContent===e.firstChild.textContent){
        } else if(e.previousElementSibling!=null&&e.previousElementSibling.firstChild.textContent===e.firstChild.textContent){
            e.firstChild.style.display="block";
        } else if(e.nextElementSibling==null){
            e.firstChild.style.display="block";
        }
    }
)

document.querySelectorAll('.chat_targetTextBox').forEach(e=> {
        if (e.previousElementSibling == null&&e.nextElementSibling == null) {
            e.lastChild.style.display="block";
        } else if(e.previousElementSibling != null &&e.nextElementSibling!=null&&e.nextElementSibling.lastChild.textContent===e.lastChild.textContent){
        } else if(e.previousElementSibling!=null&&e.previousElementSibling.lastChild.textContent===e.lastChild.textContent){
            e.lastChild.style.display="block";
        } else if(e.nextElementSibling==null){
            e.lastChild.style.display="block";

        }
    }
)


$msg.addEventListener('keydown',()=>{
    setTimeout(()=>{
        if($msg.value!=''){
            $button_send.style.backgroundColor="var(--scrollbarsColor)";
        } else {
            $button_send.style.backgroundColor="#a7a7a7";
        }
    },5)

})

document.addEventListener('DOMContentLoaded', function() {

    $msg.addEventListener('keydown',(evt)=>{
        let $msg_notEnter_notWordSpace = $msg.value.replace(/\n*/g,'').replace(/\s*/g,"");
        if(evt.key=='Enter'&&$msg_notEnter_notWordSpace!=''&&evt.shiftKey==false){
            send();
        }

    })

    websocket = new SockJS("http://localhost:8090/chat", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = onClose;
    chat_check=true;

    let sendButton = document.getElementById("button-send");
    sendButton.addEventListener("click", function (e) {
        send();
    });

    function send() {
        let sendMessage={
            "sender":JSON.parse($th_sender),
            "addressee":JSON.parse($th_addressee),
            "message":encodeURIComponent($msg.value),
            "chatroom":JSON.parse($th_postId)
        }
        //채팅방이 있는지 조회하고 없으면 생성하고 있으면 채팅 동작
        if(!createChatroomFlag){
            fetch("/createChatroom",
                {
                    headers:{"Content-Type":"application/json"},
                    method:"post",
                    body:JSON.stringify({
                        "MEMBER1":JSON.parse($th_sender),
                        "MEMBER2":JSON.parse($th_addressee),
                        "POST_ID":JSON.parse($th_postId)
                    })
                }
            ).then(res=>res.json())
                .then(res=>
                {
                    createChatroomFlag=true;
                    websocket.send(
                        JSON.stringify(sendMessage)
                    );

                    setTimeout(()=>{
                        $msg.value='';
                        $button_send.style.backgroundColor="#a7a7a7";},5)
                })
        } else {
            websocket.send(
                JSON.stringify(sendMessage)
            );

            setTimeout(()=>{
                $msg.value='';
                $button_send.style.backgroundColor="#a7a7a7";},5)
        }




    }
    function websocketSend(){

    }
    function disconnect() {
        // let str = username + ": 님이 채팅을 종료했습니다.";
        // websocket.send(str);
        websocket.close();
        chat_check=false;

    }


    //채팅창에서 나갔을 때
    function onClose(evt) {
        // let str = username + ": 님이 방을 나가셨습니다.";
        // websocket.send(str);
    }

    //채팅창에 들어왔을 때
    function onOpen(evt) {
        // let str = username + ": 님이 입장하셨습니다.";
        // websocket.send(str);arC
        console.log("입장함")
    }

    function onMessage(msg) {
        let data = JSON.parse(msg.data);
        let sender = data['sender'];
        let addressee = data['addressee'];
        let message = data['message'];
        console.log(sender + "//" + $th_sender);
        console.log("이런형태의 데이터가 왔다.");
        console.log(data);
        console.log("msg");
        console.log(msg);
        //현재 게시글 번호가 같고, 허락된 채팅 멤버간의 텍스트 표출

        //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
        if (JSON.parse($th_sender) == sender) {
            //RIGHT

            //div태그 생성
            let div = document.createElement('div');
            //div태그에 chat_myTextBox 클래스 부여
            div.classList.add('chat_myTextBox');
            //span태그 생성
            let display_userChat = document.createElement('span');
            //span태그 생성
            let display_chatTime = document.createElement('span');
            let display_profileImg = document.createElement('img');
            display_profileImg.setAttribute('src','/profileimg?fileName='+user_status.profileimg);
            display_profileImg.classList.add("chat_profileImg");
            //display_userChat에 chat_Text와 chat_myText클래스 부여
            display_chatTime.classList.add('chat_displayTime');
            let nowTime = new Date();
            if(beforeChatisMyChat_right==true&&nowTime.getMinutes()==whatTimeBeforeFromHistory_right){
            } else {
                display_chatTime.innerText = dateFormater(nowTime);
            }
            display_chatTime.innerText=dateFormater(nowTime);
            display_userChat.classList.add('chat_Text','chat_myText');
            // 메세지 입력
            if(whoChatBeforeFromHistory_right==false){
                display_userChat.innerText = decodeURIComponent(message)+'프로필이미지';
            } else {
                display_userChat.innerText = decodeURIComponent(message);
            }
            //display_userChat.innerText = message;
            console.log(message);
            //b를 div의 자식 태그로 설정
            div.append(display_chatTime,display_userChat);
            display_chatTime.style.display="none";

            //div태그를 chat_msgArea의 자식으로 설정
            $chat_msgArea.append(div);
            //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
            $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            whoChatBeforeFromHistory_right=true;
            whoChatBeforeFromHistory_left=false;
            beforeChatisMyChat_right = true;
            beforeChatisMyChat_left=false;
            whatTimeBeforeFromHistory_right=nowTime.getMinutes();
        } else {
            //LEFT

            let nowTime = new Date();

            //div태그 생성
            let div = document.createElement('div');
            //div태그에 chat_targetTextBox 클래스 부여
            div.classList.add('chat_targetTextBox');
            //span태그 생성
            let display_userChat = document.createElement('span');
            //span태그 생성
            let display_chatTime = document.createElement('span');
            //display_userChat에 chat_Text와 chat_targetText클래스 부여
            display_chatTime.classList.add('chat_displayTime');
            display_userChat.classList.add('chat_Text','chat_targetText');
            if(beforeChatisMyChat_left==true&&nowTime.getMinutes()==whatTimeBeforeFromHistory_left){
            } else {
                display_chatTime.innerText = dateFormater(nowTime);
            }
            display_chatTime.innerText=dateFormater(new Date());

            if(whoChatBeforeFromHistory_left==false){
                display_userChat.innerText = decodeURIComponent(message)+'프로필이미지';
            } else {
                display_userChat.innerText = decodeURIComponent(message);
            }
            // 메세지 입력
            // display_userChat.innerText = message;
            //b를 div의 자식 태그로 설정

            div.append(display_userChat,display_chatTime);
            display_chatTime.style.display="none";

            //div태그를 chat_msgArea의 자식으로 설정
            $chat_msgArea.append(div);
            //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
            $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            whoChatBeforeFromHistory_right=false;
            whoChatBeforeFromHistory_left=true;
            beforeChatisMyChat_right = false;
            beforeChatisMyChat_left=true;
            whatTimeBeforeFromHistory_left==nowTime.getMinutes();
        }

        document.querySelectorAll('.chat_myTextBox').forEach(e=> {
                if (e.previousElementSibling == null&&e.nextElementSibling == null) {
                    e.firstChild.style.display="block";
                } else if(e.previousElementSibling != null &&e.nextElementSibling!=null&&e.nextElementSibling.firstChild.textContent===e.firstChild.textContent){
                    e.firstChild.style.display="none";

                } else if(e.previousElementSibling!=null&&e.previousElementSibling.firstChild.textContent===e.firstChild.textContent){
                    e.firstChild.style.display="block";
                } else if(e.nextElementSibling==null){
                    e.firstChild.style.display="block";

                }
            }
        )

        document.querySelectorAll('.chat_targetTextBox').forEach(e=> {
                if (e.previousElementSibling == null&&e.nextElementSibling == null) {
                    e.lastChild.style.display="block";
                } else if(e.previousElementSibling != null &&e.nextElementSibling!=null&&e.nextElementSibling.lastChild.textContent===e.lastChild.textContent){
                    e.lastChild.style.display="none";

                } else if(e.previousElementSibling!=null&&e.previousElementSibling.lastChild.textContent===e.lastChild.textContent){
                    e.lastChild.style.display="block";
                } else if(e.nextElementSibling==null){
                    e.lastChild.style.display="block";

                }
            }
        )

    }
});