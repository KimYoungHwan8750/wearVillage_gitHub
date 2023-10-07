        let chat_check = false;
        let user_status=null;
        //첫 채팅을 쳤을 때 채팅방이 있으면 채팅 진행, 없으면 채팅방 생성
        let createChatroomFlag = false;
        const $chat_msgArea = document.querySelector(".chat_msgArea");
        const $msg = document.getElementById('msg');
        const $chat_midContent = document.querySelector('.chat_midContent');
        const $button_send = document.getElementById('button-send');
            /*디자인 관련 스크립트*/ 

    const $chat_noticeClose = document.querySelector('.chat_noticeClose');
    const $chat_notice = document.querySelector('.chat_notice');
    const $chat_notice_content = document.querySelector('.chat_notice_content');
    setTimeout(()=>{
        $chat_notice.setAttribute('style','transition:all 2s ease-in; opacity:0;');
    },4000)
    
        function dateFormater(date){
            let amOrPm = '';
            let hour =date.getHours();
            let minute =date.getMinutes();
            if(hour>11){
                amOrPm="오후";
            }
            else {
                amOrPm="오전";
            }

            if(hour>12){hour-=12;}
            if(minute<11){minute="0"+minute;}

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
        console.log(chatMessage);
        let date= new Date(chatMessage['chat_DATE']);

        if (JSON.parse($th_sender) == chatMessage['sender']) {
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
            display_chatTime.innerText = dateFormater(date);
            // 메세지 입력
            display_userChat.innerText = chatMessage['message'];
            //b를 div의 자식 태그로 설정
            div.append(display_chatTime,display_userChat);
            //div태그를 chat_msgArea의 자식으로 설정
            $chat_msgArea.append(div);
            //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
            $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
        } else {
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
            display_chatTime.innerText = dateFormater(date);


            // 메세지 입력
            display_userChat.innerText = chatMessage['message'];
            //b를 div의 자식 태그로 설정
            div.append(display_userChat,display_chatTime);
            //div태그를 chat_msgArea의 자식으로 설정
            $chat_msgArea.append(div);
            //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
            $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
        }
    

        
        
    });



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
                "message":$msg.value,
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
                display_chatTime.innerText=dateFormater(new Date());
                display_userChat.classList.add('chat_Text','chat_myText');
                // 메세지 입력
                display_userChat.innerText = message;
                console.log(message);
                //b를 div의 자식 태그로 설정
                div.append(display_chatTime,display_userChat);
                //div태그를 chat_msgArea의 자식으로 설정
                $chat_msgArea.append(div);
                //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
                $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            } else {
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
                display_chatTime.innerText=dateFormater(new Date());


                // 메세지 입력
                display_userChat.innerText = message;
                //b를 div의 자식 태그로 설정
                div.append(display_userChat,display_chatTime);
                //div태그를 chat_msgArea의 자식으로 설정
                $chat_msgArea.append(div);
                //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
                $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            }
        
        }
    });





