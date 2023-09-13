        let chat_check = false;
        const $chat_msgArea = document.querySelector(".chat_msgArea");
        document.addEventListener('DOMContentLoaded', function() {
        const $msg = document.getElementById('msg');
        $msg.addEventListener('keyup',(evt)=>{
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
            websocket.send(username + "[wearCutLines]" + $msg.value);
            $msg.value=''
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
            // websocket.send(str);
        }

        function onMessage(msg) {
            let data = msg.data;
            console.log(msg);
            let sessionId = null;
            //데이터를 보낸 사람
            let message = null;
            let arr = data.split("[wearCutLines]", 2);
            //username:입력값 형태로 웹소켓에 전달되는데 이 때 :를 기준으로 데이터를 잘라서 배열로 반환함

            let cur_session = username;
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
                b.innerText = message;
                console.log(message);
                div.append(b);
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





    /*디자인 관련 스크립트*/ 

    const $chat_noticeClose = document.querySelector('.chat_noticeClose');
    const $chat_notice = document.querySelector('.chat_notice');
    const $chat_notice_content = document.querySelector('.chat_notice_content');
    $chat_noticeClose.addEventListener("click",()=>{
        // $chat_notice_content.style.display = 'none';
        $chat_notice_content.setAttribute('style','transition:all 1s;transform:scale(0)');
        // setTimeout(() => {
        // $chat_notice.setAttribute('style','transition:all 2s;width:30px;height:30px;');
            
        // }, 1000);
        console.log($chat_notice.getAttribute('style'));
    })
    