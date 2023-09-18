        let chat_check = false;
        const $chat_msgArea = document.querySelector(".chat_msgArea");
        document.addEventListener('DOMContentLoaded', function() {
        const $msg = document.getElementById('msg');
        $msg.addEventListener('keydown',(evt)=>{
            if(evt.keyCode==13){
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
