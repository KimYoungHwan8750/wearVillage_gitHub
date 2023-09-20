        let chat_check = false;
        const $chat_msgArea = document.querySelector(".chat_msgArea");
<<<<<<< HEAD
=======
        const $msg = document.getElementById('msg');
        const $chat_midContent = document.querySelector('.chat_midContent');
        const $button_send = document.getElementById('button-send');
        const Encoder = new TextEncoder();
        const Decoder = new TextDecoder();
        JSON.parse($th_chatHistory).forEach(element => {
            if ($th_id == element["USER_ID"]) {
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
                // 메세지 입력
                display_chatTime.innerText = element["CHAT_DATE"];
                display_userChat.innerText = element["MESSAGE"];
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
                // 메세지 입력
                display_chatTime.innerText = element["CHAT_DATE"];
                display_userChat.innerText = element["MESSAGE"];
                //b를 div의 자식 태그로 설정
                div.append(display_userChat,display_chatTime);
                //div태그를 chat_msgArea의 자식으로 설정
                $chat_msgArea.append(div);
                //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
                $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            }
            
        });
        let chat_time = null;
        let date; // 날짜 객체 생성
        $msg.addEventListener('keydown',()=>{
            setTimeout(()=>{
                if($msg.value!=''){
                    $button_send.style.backgroundColor="var(--scrollbarsColor)";
                } else {
                    $button_send.style.backgroundColor="#a7a7a7";
                }
            },5)

        })

>>>>>>> 3ea686e29dc9b060e6ae9b93e927c07d70c4c89f
        document.addEventListener('DOMContentLoaded', function() {
        const $msg = document.getElementById('msg');
        $msg.addEventListener('keydown',(evt)=>{
<<<<<<< HEAD
            if(evt.keyCode==13){
=======
            let $msg_notEnter_notWordSpace = $msg.value.replace(/\n*/g,'').replace(/\s*/g,"");
        if(evt.key=='Enter'&&$msg_notEnter_notWordSpace!=''&&evt.shiftKey==false){
>>>>>>> 3ea686e29dc9b060e6ae9b93e927c07d70c4c89f
                send();
            }
        })
        // 실행할 기능을 정의해주세요.
        let username =  'test'; /*[[${username}]]*/// 모델의 username 값을 JavaScript 변수로 가져옴




        // 버튼 클릭 이벤트 설정
        let exitButton = document.getElementById("button-exit");
        exitButton.addEventListener("click", function (e) {
            disconnect();
        });
        let joinButton = document.getElementById("button-join");
        joinButton.addEventListener("click", function (e) {
            join();
        });
        function join(){
            if(chat_check==false){
            username = document.getElementById('username').value;
            websocket = new SockJS("https://wearvillage.store/chattest", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
            websocket.onmessage = onMessage;
            websocket.onopen = onOpen;
            websocket.onclose = onClose;
            chat_check=true;
        }
            
        }

        let sendButton = document.getElementById("button-send");
        sendButton.addEventListener("click", function (e) {
            send();
        });

        function send() {
            let msg = document.getElementById("msg");
            websocket.send(username + "[wearCutLines]" + $msg.value);
            $msg.value = '';
        }

<<<<<<< HEAD
=======
        /*분 표시*/
        if(date.getMinutes()<'10'){
            chat_minute = '0'+date.getMinutes();
        } else {
        chat_minute = date.getMinutes();

        }
        let chat_typing_time = am_or_pm+" " +chat_hours+":"+chat_minute;

            websocket.send(
                $th_id + "'wearCutLines'" + 
                $th_target_id + "'wearCutLines'" + 
                $msg.value + "'wearCutLines'" + 
                $th_chat_thema + "'wearCutLines'" + 
                chat_typing_time + "'wearCutLines'" +
                $th_post_id
                );
                console.log("테스트확인:"+$th_id+"//"+$th_target_id+"//"+$th_post_id)
            
            setTimeout(()=>{
            $msg.value='';
            $button_send.style.backgroundColor="#a7a7a7";},5)
        }
>>>>>>> ee50c55e39ad5332db6801d04cc4c619b18dfaa2
        function disconnect() {
            // let str = username + ": 님이 채팅을 종료했습니다.";
            websocket.send(str);
            websocket.close();
            chat_check=false;

        }
        

        //채팅창에서 나갔을 때
        function onClose(evt) {
            // let str = username + ": 님이 방을 나가셨습니다.";
            websocket.send(str);
        }

        //채팅창에 들어왔을 때
        function onOpen(evt) {
            // let str = username + ": 님이 입장하셨습니다.";
<<<<<<< HEAD
            websocket.send(str);
=======
            // websocket.send(str);arC
            console.log("입장함")
>>>>>>> ee50c55e39ad5332db6801d04cc4c619b18dfaa2
        }

        function onMessage(msg) {
            let data = msg.data;
            console.log(msg);
            let sessionId = null;
            //데이터를 보낸 사람
            let message = null;
<<<<<<< HEAD
            let arr = data.split("[wearCutLines]", 2);
=======
            let chat_typing_time =null;
            let chatPlace_history = null;
            let chat_member = null;
            //메세지
            let arr = data.split("'wearCutLines'");
>>>>>>> ee50c55e39ad5332db6801d04cc4c619b18dfaa2
            //username:입력값 형태로 웹소켓에 전달되는데 이 때 :를 기준으로 데이터를 잘라서 배열로 반환함

            let chat_formData = {
                myId:arr[0],
                target_id:arr[1],
                message:arr[2],
                chat_typing_time:arr[4],
                chatPlace_history:arr[5]
            }

            //현재 세션에 로그인 한 사람
            
            sessionId = arr[0];
            message = arr[1];

            console.log("sessionID : " + sessionId);
            console.log("cur_session : " + cur_session);

            //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
            if (sessionId == cur_session) {
                let div = document.createElement('div');
                div.classList.add('chat_myTextBox');
                let b = document.createElement('b');
                b.classList.add('chat_Text');
                b.classList.add('chat_myText');
                b.innerText = sessionId + " : " + message;
                div.appendChild(b);
                $chat_msgArea.append(div);
                $chat_msgArea.scrollTop = $chat_msgArea.scrollHeight;
            } else {
                let div = document.createElement('div');
                div.classList.add('chat_targetTextBox');
                let b = document.createElement('b');
                b.classList.add('chat_Text');
                b.classList.add('chat_targetText');
                b.innerText = sessionId + " : " + message;
                div.appendChild(b);
                $chat_msgArea.append(div);
                $chat_msgArea.scrollTop = $chat_msgArea.scrollHeight;

            }
        }
    });
