        let chat_check = false;
        const $chat_msgArea = document.querySelector(".chat_msgArea");
        const $msg = document.getElementById('msg');
        const $chat_midContent = document.querySelector('.chat_midContent');
        const $button_send = document.getElementById('button-send');
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

        document.addEventListener('DOMContentLoaded', function() {
        $msg.addEventListener('keydown',(evt)=>{
            if(evt.key=='Enter'){
                send();
                
            }

        })
        // 실행할 기능을 정의해주세요.
        let username =  'test'; /*[[${username}]]*/// 모델의 username 값을 JavaScript 변수로 가져옴



        // 버튼 클릭 이벤트 설정
        // let exitButton = document.getElementById("button-exit");
        // exitButton.addEventListener("click", function (e) {
        //     disconnect();
        // });
        // let joinButton = document.getElementById("button-join");
        // joinButton.addEventListener("click", function (e) {
        //     join();
        // });
        // function join(){
        //     if(chat_check==false){
            websocket = new SockJS("https://wearvillage.store/chat", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
            websocket.onmessage = onMessage;
            websocket.onopen = onOpen;
            websocket.onclose = onClose;
            chat_check=true;
        // }
            
        // }

        let sendButton = document.getElementById("button-send");
        sendButton.addEventListener("click", function (e) {
            send();
        });

        function send() {
            let am_or_pm=null;
            let chat_hours=null;
            let chat_minute=null;
            date = new Date();

            /*시간이 12시 이후면 오후로 표시*/
       if(date.getHours()<'13'){
        am_or_pm ='오전';
       } else {
        am_or_pm ='오후';
       }
            /*시간이 12시 이후면 시간을 -12해서 오후 시간으로 반환*/

        if(date.getHours()>'11'){
        chat_hours = date.getHours()-12;
        } else {
        chat_hours = date.getHours();
        }

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
            let data = msg.data;
            console.log(msg);
            let myId = null;
            //현재 아이디

            let target_id = null;
            //상대방 아이디
            let message = null;
            let chat_typing_time =null;
            let chatPlace_history = null;
            let chat_member = null;
            //메세지
            let arr = data.split("'wearCutLines'");
            //username:입력값 형태로 웹소켓에 전달되는데 이 때 :를 기준으로 데이터를 잘라서 배열로 반환함

            let chat_formData = {
                myId:arr[0],
                target_id:arr[1],
                message:arr[2],
                chat_typing_time:arr[4],
                chatPlace_history:arr[5]
            }

            //현재 세션에 로그인 한 사람
            myId = arr[0];
            target_id = arr[1];
            message = arr[2];
            chat_typing_time = arr[4]
            chatPlace_history =arr[5];
            chat_member = [$th_id,$th_target_id]
            console.log("확인222:"+myId+"//"+target_id+"//"+$th_post_id+"//"+chatPlace_history+"//"+chat_member[0]+"//"+chat_member[1])
            //현재 게시글 번호가 같고, 허락된 채팅 멤버간의 텍스트 표출
            if(chatPlace_history==$th_post_id&&chat_member.includes(myId)&&chat_member.includes(target_id)){
                console.log(chatPlace_history + "이프문" + $th_post_id)
            //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
            if (myId == $th_id) {
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
                display_chatTime.innerText = chat_typing_time;
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
                // 메세지 입력
                display_chatTime.innerText = chat_typing_time;
                display_userChat.innerText = message;
                //b를 div의 자식 태그로 설정
                div.append(display_userChat,display_chatTime);
                //div태그를 chat_msgArea의 자식으로 설정
                $chat_msgArea.append(div);
                //새로운 채팅이 올라올 때마다 스크롤 최하단으로 갱신
                $chat_midContent.scrollTop = $chat_midContent.scrollHeight;
            }
        }
        }
    });





    /*디자인 관련 스크립트*/ 

    const $chat_noticeClose = document.querySelector('.chat_noticeClose');
    const $chat_notice = document.querySelector('.chat_notice');
    const $chat_notice_content = document.querySelector('.chat_notice_content');
    setTimeout(()=>{
        $chat_notice.setAttribute('style','transition:all 2s ease-in; opacity:0;');
    },4000)