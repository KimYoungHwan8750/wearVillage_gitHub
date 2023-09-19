      
      
          let dataForm = {
            ID:id_box.value,
            PW:pw_box.value,
            EMAIL:email_box.value,
            GENDER:gendercheck(),
            BIRTH:birth_box.value,
            NICKNAME:nickname_box.value,
            PROFILEIMG:"",
            THEMA:gendercheck()
        }

        
        function gendercheck(){
          return document.querySelector('input[name="gender"]:checked').value;
        }
      
      //1페이지에서 2페이지 갈때 확인용
      let nextpage1 = 0; //아이디
      let nextpage2 = 0; //비번
      //2페이지에서 3페이지 갈때 확인용
      let nextpage3 = 0; //닉네임
      let nextpage4 = 0; //생일
      let nextpage5 = 0; //성별
      // 3페이지에서 완료할때
      let nextpage6 = 0; //이메일

//id_box 인스턴스화
      //id_box.value에 따른 문구출력 = id_text
      //중복검사에 따른 문구출력 = id_text2
      //id_box에 입력된 값 사용 id_box.value
      const id_box = document.getElementById("id_box");
      const id_text = document.getElementById("id_text");
      const id_text2 = document.getElementById("id_text2");

      //pw_box 인스턴스화
      //pw_box.value에 따른 문구출력 = pw_text
      //id_box에 입력된 값 사용 pw_box.value
      const pw_box = document.getElementById("pw_box");
      const pw_text = document.getElementById("pw_text");
      const pw_text2 = document.getElementById("pw_text2");

      //nickname_box 인스턴스화
      //nickname_box.value에 따른 문구출력 = nickname_text
      //id_box에 입력된 값 사용 nickname_box.value
      const nickname_box = document.getElementById("nickname_box");
      const nickname_text = document.getElementById("nickname_text");


      //email_box 인스턴스화
      //email_box.value에 따른 문구출력 = email_text
      //email_box에 입력된 값 사용 email_box.value
      const email_box = document.getElementById("email_box");
      const email_text = document.getElementById("email_text");
      const id_check_btn = document.getElementById("id_check_btn");


      //생일
      const birth_box = document.getElementById("birthday");
      //성별
      const male_box = document.getElementById("male");
      const female_box = document.getElementById("female"); 
      
      let duplicate_check = false;



      function create_user() {
        id_check();
        pw_check();
        email_check();
        nickname_check();
        birth_check();
        if (id_check() && pw_check() && nickname_check() && email_check() &&duplicate_check == true) {
          fetch(
            "/finished_signUp",
            {
              method:"POST",
              headers:{"Content-Type":"application/json"},
              body:JSON.stringify(dataForm),
           })
           .then(res=>res.text()
           .then(ok=>ok==ok?location.href="/":alert("회원가입에 실패했습니다. 관리자에게 문의하세요."))
           )
           .catch(err=>alert(err+"에러메세지")
           )
           
        }
      }
      //   function create_user() {
      // if(nextpage2 == 0 || nextpage3 == 0 || nextpage4 == 0|| nextpage5 == 0 || nextpage6 == 0){
      //   all_error.textContent = "빈칸을 확인해주세요.";
      // }else{
      //   all_error.style.color = "rgba(0, 120, 0, 0.600";
      //   all_error.textContent = "임시: 회원가입 완료";
      // }
      // }
      id_box.addEventListener('input', id_check);
      function id_check() {
        //아래는 디버깅용 코드. 필요시 활성화
        // console.log("ID체크 완료");
        let id_value_check = id_box.value;
        if ((/^[0-9a-zA-Z]{6,20}$/g).test(id_value_check)) {
          //아이디 중복 체크를 하기 위해서 서버에 id_box.value값 전달
          $.ajax({
            url: "/checkID", // 클라이언트가 요청을 보낼 서버의 URL 주소
            data: { id_box: id_box.value },                // HTTP 요청과 함께 서버로 보낼 데이터
            type: "post",                             // HTTP 요청 방식(GET, POST)
            dataType: "text",                         // 서버에서 보내줄 데이터의 타입
            success: function (result) {

              //중복된 아이디가 없을 때 = result에서 false반환
              if (result == "false") {
                duplicate_check = true;
                id_text.style.color = "rgba(0, 120, 0, 0.600)";
                //수정예정(이미지 체크박스)
                id_text.innerText = "생성 가능한 아이디입니다.";
                nextpage1 = 1;
                //  다음페이지 
                return true;
                //중복된 아이디가 있을 때 = result에서 true반환
              } else if (result == "true") {
                duplicate_check = false;
                id_text.style.color = "rgba(180, 0, 0, 0.600)";
                //수정예정(이미지 체크박스)
                id_text.innerText = "중복된 아이디입니다.";
                nextpage1 = 0;
                return false;
              } else {
                alert("에러!");
                nextpage1 = 0;
              }
            },
            error: function (error) {
              alert(error);
            }
          })
          return true;
        } else if (id_box.value === "") {
          id_text.style.color = "rgba(180, 0, 0, 0.600)";
          id_text.innerText = "아이디를 입력하세요."
          //조건을 충족하지 못한 요소로 화면 이동. 당장에는 회원가입창이 작아서 필요없지만
          //추후 회원가입 화면에 들어가는 요소가 많아졌을 때 고객 편리성을 기대할 수 있음
          id_box.scrollIntoView();
          nextpage1 = 0;
          return false;
        } else {
          id_text.style.color = "rgba(180, 0, 0, 0.600)";
          id_text.innerText = "아이디를 확인해주세요.";
          id_box.scrollIntoView();
          nextpage1 = 0;
          return false;
        }
      }

// 비밀번호 입력 필드에 이벤트 리스너를 추가합니다.
pw_box.addEventListener('input', pw_check);

function pw_check() {
    let pw_value_check = pw_box.value;
    let hasUpperCase = /^(?=.*[A-Z]).*$/.test(pw_value_check);
    let hasLowerCase = /^(?=.*[a-z]).*$/.test(pw_value_check);
    let hasSpecialcase = /^(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).*$/.test(pw_value_check);
    let hasnumbercase = /^(?=.*[0-9]).*$/.test(pw_value_check);
    let haslengthcase = /^.{8,20}.*$/.test(pw_value_check);

    if (pw_value_check == "") {
        pw_text2.textContent = "";
        pw_text.textContent = "비밀번호를 입력하세요.";
    } else if (!hasUpperCase) {
        pw_text2.textContent = "";
        pw_text.textContent = "대문자가 없습니다.";
        nextpage2 = 0;
    } else if (!hasLowerCase) {
        pw_text2.textContent = "";
        pw_text.textContent = "소문자가 없습니다.";
        nextpage2 = 0;
    } else if (!hasSpecialcase) {
        pw_text2.textContent = "";
        pw_text.textContent = "특수문자가 없습니다.";
        nextpage2 = 0;
    } else if (!hasnumbercase) {
        pw_text2.textContent = "";
        pw_text.textContent = "숫자가 없습니다.";
        nextpage2 = 0;
    } else if (!haslengthcase) {
        pw_text2.textContent = "";
        pw_text.textContent = "8 ~ 20자 이하로 작성해야 합니다.";
        nextpage2 = 0;
    } else {
        pw_text.textContent = "";
        pw_text2.textContent = "사용 가능한 비밀번호";
        nextpage2 = 1;
    }
}

        
          
      
      
     nickname_box.addEventListener("input", nickname_check) 
      function nickname_check() {
        const forbiddenWords = ["ㅅㅂ", "씨발", "씨바", "개세끼", "18년", "18놈", "18새끼", "ㄱㅐㅅㅐㄲl", "ㄱㅐㅈㅏ", "가슴만져", "가슴빨아", "가슴빨어", "가슴조물락", "가슴주물럭", "가슴쪼물딱",
          "가슴쪼물락", "가슴핧아", "가슴핧어", "강간", "개가튼년", "개가튼뇬", "개같은년", "개걸레", "개고치", "개너미", "개넘", "개년", "개놈", "개늠", "개똥", "개떵", "개떡",
          "개라슥", "개보지", "개부달", "개부랄", "개불랄", "개붕알", "개새", "개세", "개쓰래기", "개쓰레기", "개씁년", "개씁블", "개씁자지", "개씨발", "개씨블", "개자식", "개자지",
          "개잡년", "개젓가튼넘", "개좆", "개지랄", "개후라년", "개후라들놈", "개후라새끼", "걔잡년", "거시기", "걸래년", "걸레같은년", "걸레년", "걸레핀년", "게부럴", "게세끼", "게이",
          "게새끼", "게늠", "게자식", "게지랄놈", "고환", "공지", "공지사항", "귀두", "깨쌔끼", "난자마셔", "난자먹어", "난자핧아", "내꺼빨아", "내꺼핧아", "내버지", "내자지", "내잠지",
          "내조지", "너거애비", "노옴", "누나강간", "니기미", "니뿡", "니뽕", "니씨브랄", "니아범", "니아비", "니애미", "니애뷔", "니애비", "니할애비", "닝기미", "닌기미", "니미",
          "닳은년", "덜은새끼", "돈새끼", "돌으년", "돌은넘", "돌은새끼", "동생강간", "동성애자", "딸딸이", "똥구녁", "똥꾸뇽", "똥구뇽", "똥", "띠발뇬", "띠팔", "띠펄", "띠풀", "띠벌",
          "띠벨", "띠빌", "마스터", "막간년", "막대쑤셔줘", "막대핧아줘", "맛간년", "맛없는년", "맛이간년", "멜리스", "미친구녕", "미친구멍", "미친넘", "미친년", "미친놈", "미친눔",
          "미친새끼", "미친쇄리", "미친쇠리", "미친쉐이", "미친씨부랄", "미튄", "미티넘", "미틴", "미틴넘", "미틴년", "미틴놈", "미틴것", "백보지", "버따리자지", "버지구녕", "버지구멍",
          "버지냄새", "버지따먹기", "버지뚫어", "버지뜨더", "버지물마셔", "버지벌려", "버지벌료", "버지빨아", "버지빨어", "버지썰어", "버지쑤셔", "버지털", "버지핧아", "버짓물", "버짓물마셔",
          "벌창같은년", "벵신", "병닥", "병딱", "병신", "보쥐", "보지", "보지핧어", "보짓물", "보짓물마셔", "봉알", "부랄", "불알", "붕알", "붜지", "뷩딱", "븅쉰", "븅신", "빙띤",
          "빙신", "빠가십새", "빠가씹새", "빠구리", "빠굴이", "뻑큐", "뽕알", "뽀지", "뼝신", "사까시", "상년", "새꺄", "새뀌", "새끼", "색갸", "색끼", "색스", "색키", "샤발",
          "써글", "써글년", "성교", "성폭행", "세꺄", "세끼", "섹스", "섹스하자", "섹스해", "섹쓰", "섹히", "수셔", "쑤셔", "쉐끼", "쉑갸", "쉑쓰", "쉬발", "쉬방", "쉬밸년", "쉬벌",
          "쉬불", "쉬붕", "쉬빨", "쉬이발", "쉬이방", "쉬이벌", "쉬이불", "쉬이붕", "쉬이빨", "쉬이팔", "쉬이펄", "쉬이풀", "쉬팔", "쉬펄", "쉬풀", "쉽쌔", "시댕이", "시발", "시발년",
          "시발놈", "시발새끼", "시방새", "시밸", "시벌", "시불", "시붕", "시이발", "시이벌", "시이불", "시이붕", "시이팔", "시이펄", "시이풀", "시팍새끼", "시팔", "시팔넘", "시팔년",
          "시팔놈", "시팔새끼", "시펄", "실프", "십8", "십때끼", "십떼끼", "십버지", "십부랄", "십부럴", "십새", "십세이", "십셰리", "십쉐", "십자석", "십자슥", "십지랄", "십창녀",
          "십창", "십탱", "십탱구리", "십탱굴이", "십팔새끼", "ㅆㅂ", "ㅆㅂㄹㅁ", "ㅆㅂㄻ", "ㅆㅣ", "쌍넘", "쌍년", "쌍놈", "쌍눔", "쌍보지",
          "쌔끼", "쌔리", "쌕스", "쌕쓰", "썅년", "썅놈", "썅뇬", "썅늠", "쓉새", "쓰바새끼", "쓰브랄쉽세", "씌발", "씌팔", "씨가랭넘", "씨가랭년", "씨가랭놈", "씨발",
          "씨발년", "씨발롬", "씨발병신", "씨방새", "씨방세", "씨밸", "씨뱅가리", "씨벌", "씨벌년", "씨벌쉐이", "씨부랄", "씨부럴", "씨불", "씨불알", "씨붕", "씨브럴", "씨블",
          "씨블년", "씨븡새끼", "씨빨", "씨이발", "씨이벌", "씨이불", "씨이붕", "씨이팔", "씨파넘", "씨팍새끼", "씨팍세끼", "씨팔", "씨펄", "씨퐁넘", "씨퐁뇬", "씨퐁보지",
          "씨퐁자지", "씹년", "씹물", "씹미랄", "씹버지", "씹보지", "씹부랄", "씹브랄", "씹빵구", "씹뽀지", "씹새", "씹새끼", "씹세", "씹쌔끼", "씹자석", "씹자슥", "씹자지",
          "씹지랄", "씹창", "씹창녀", "씹탱", "씹탱굴이", "씹탱이", "씹팔", "아가리", "애무", "애미", "애미랄", "애미보지", "애미씨뱅", "애미자지", "애미잡년", "애미좃물",
          "애비", "애자", "양아치", "어미강간", "어미따먹자", "어미쑤시자", "영자", "엄창", "에미", "에비", "엔플레버", "엠플레버", "염병", "염병할", "염뵹", "엿먹어라", "오랄",
          "오르가즘", "왕버지", "왕자지", "왕잠지", "왕털버지", "왕털보지", "왕털자지", "왕털잠지", "우미쑤셔", "운디네", "운영자", "유두", "유두빨어", "유두핧어", "유방", "유방만져",
          "유방빨아", "유방주물럭", "유방쪼물딱", "유방쪼물럭", "유방핧아", "유방핧어", "육갑", "이그니스", "이년", "이프리트", "자기핧아", "자지", "자지구녕", "자지구멍", "자지꽂아",
          "자지넣자", "자지뜨더", "자지뜯어", "자지박어", "자지빨아", "자지빨아줘", "자지빨어", "자지쑤셔", "자지쓰레기", "자지정개", "자지짤라", "자지털", "자지핧아", "자지핧아줘",
          "자지핧어", "작은보지", "잠지", "잠지뚫어", "잠지물마셔", "잠지털", "잠짓물마셔", "잡년", "잡놈", "저년", "점물", "젓가튼", "젓가튼쉐이", "젓같내", "젓같은", "젓까", "젓나",
          "젓냄새", "젓대가리", "젓떠", "젓마무리", "젓만이", "젓물", "젓물냄새", "젓밥", "정액마셔", "정액먹어", "정액발사", "정액짜", "정액핧아", "정자마셔", "정자먹어", "정자핧아",
          "젖같은", "젖까", "젖밥", "젖탱이", "조개넓은년", "조개따조", "조개마셔줘", "조개벌려조", "조개속물", "조개쑤셔줘", "조개핧아줘", "조까", "조또", "족같내", "족까", "족까내",
          "존나", "존나게", "존니", "졸라", "좀마니", "좀물", "좀쓰레기", "좁빠라라", "좃가튼뇬", "좃간년", "좃까", "좃까리", "좃깟네", "좃냄새", "좃넘", "좃대가리", "좃도", "좃또",
          "좃만아", "좃만이", "좃만한것", "좃만한쉐이", "좃물", "좃물냄새", "좃보지", "좃부랄", "좃빠구리", "좃빠네", "좃빠라라", "좃털", "좆같은놈", "좆같은새끼", "좆까", "좆까라",
          "좆나", "좆년", "좆도", "좆만아", "좆만한년", "좆만한놈", "좆만한새끼", "좆먹어", "좆물", "좆밥", "좆빨아", "좆새끼", "좆털", "좋만한것", "주글년", "주길년", "쥐랄", "지랄",
          "지랼", "지럴", "지뢀", "쪼까튼", "쪼다", "쪼다새끼", "찌랄", "찌질이", "창남", "창녀", "창녀버지", "창년", "처먹고", "처먹을", "쳐먹고", "쳐쑤셔박어", "촌씨브라리",
          "촌씨브랑이", "촌씨브랭이", "크리토리스", "큰보지", "클리토리스", "트랜스젠더", "페니스", "항문수셔", "항문쑤셔", "허덥", "허버리년", "허벌년", "허벌보지", "허벌자식", "허벌자지",
          "허접", "허젚", "허졉", "허좁", "헐렁보지", "혀로보지핧기", "호냥년", "호로", "호로새끼", "호로자슥", "호로자식", "호로짜식", "호루자슥", "호모", "호졉", "호좁", "후라덜넘",
          "후장", "후장꽂아", "후장뚫어", "흐접", "흐젚", "흐졉", "bitch", "fuck", "fuckyou", "nflavor", "penis", "pennis", "pussy", "sex"];
        //금지어

        //아래는 디버깅용 코드. 필요시 활성화
        //console.log("nickname체크 ")
        let nickname_value_check = nickname_box.value;

        // 금지어 목록을 순회하면서 검사
        for (const word of forbiddenWords) {
          if (nickname_value_check.includes(word)) {
            nickname_text.style.color = "red";
            nickname_text.innerText = '금지어를 포함한 닉네임은 사용할 수 없습니다.';
            nickname_box.scrollIntoView();
            nextpage3 = 0;
            return false; // 금지어가 포함되어 있으면 false 반환
          }
        }

        if (/^.{2,20}$/.test(nickname_value_check)) {
          nickname_text.style.color = "rgba(0, 120, 0, 0.600)";
          nickname_text.innerText = '올바른 닉네임 형식입니다.';
          nextpage3 = 1;
          return true; // 올바른 형식의 닉네임이면 true 반환
        } else {
          nickname_text.style.color = "rgba(180, 0, 0, 0.600)";
          nickname_text.innerText = '닉네임을 확인해주세요.';
          nickname_box.scrollIntoView();
          nextpage3 = 0;
          return false; // 잘못된 형식의 닉네임이면 false 반환
        }
      }
email_box.addEventListener("input", email_check) 
      
      function email_check() {
        //아래는 디버깅용 코드. 필요시 활성화
        let email_value_check = email_box.value;
        // console.log("EMAIL체크 완료");
        if ((/\w+[\w.]*@[\w.]+\.\w+/g).test(email_value_check)) {
          email_text.style.color = "rgba(0, 120, 0, 0.600)";
          email_text.innerText = '올바른 이메일 형식입니다.';
          nextpage6 = 1;
          return true;
        } else {
          email_text.style.color = "rgba(180, 0, 0, 0.600)";
          email_text.innerText = '이메일을 확인해주세요.';
          email_box.scrollIntoView();
          nextpage6 = 0;
          return false;
        }
      }



    // <!--포스트 방식 URL,파라미터 전달-->
    // <!--action에 URL지정, params에 JSON 데이터 지정-->

      function sendPost(action, params) {
        var form = document.createElement('form');
        form.setAttribute('method', 'post');
        form.setAttribute('action', action);
        document.charset = "utf-8";
        for (var key in params) {
          var hiddenField = document.createElement('input');
          hiddenField.setAttribute('type', 'hidden');
          hiddenField.setAttribute('name', key);
          hiddenField.setAttribute('value', params[key]);
          form.appendChild(hiddenField);
        }
        document.body.appendChild(form);
        form.submit();
      }

      //생일 입력
      // 입력 필드의 ID를 가져옵니다.
      const inputbirth = document.getElementById("birthday");
      
      // 입력 필드에 이벤트 리스너를 추가합니다.
      // 입력된 값을 가져옵니다.
      inputbirth.addEventListener("input", birth_check) 
        
        function birth_check() {
     const birthValue = inputbirth.value;
  // 입력된 값이 숫자인지 확인합니다.
  if (!isNaN(birthValue)) {
    // 입력된 값이 숫자일 때, 길이가 8자리인지 확인합니다.
    if (birthValue.length === 8) {
      const year = parseInt(birthValue.substring(0, 4), 10);
      const month = parseInt(birthValue.substring(4, 6), 10);
      const day = parseInt(birthValue.substring(6, 8), 10);
      
      // 날짜가 유효한지 확인합니다.
      const birthDate = !isNaN(year) && !isNaN(month) && !isNaN(day) &&
      year >= 1950 && year <= 2030 &&
      month >= 1 && month <= 12 &&
      day >= 1 && day <= 31;
      
      if (!birthDate || birthDate.length < 8) {
        // alert("올바른 날짜를 입력하세요 (19500101부터 20301231까지).");
        memberjoin_birth_error.textContent = "올바른 날짜를 입력하세요."; // 입력 필드를 비웁니다.
        nextpage4 = 0;
      }

      if(birthDate) {
        memberjoin_birth_error.textContent = "";
        nextpage4 = 1;
    }
    } 
    else{
      memberjoin_birth_error.textContent = "올바른 날짜를 입력하세요."; // 입력 필드를 비웁니다.
      nextpage4 = 0;
    }
  } 
}
//생일 숫자만 최대 8자 입력가능
function numberMaxLength(e){
        if(e.value.length > e.maxLength){
          e.value = e.value.slice(0, e.maxLength);
        }}

//성별
// 라디오 버튼 요소와 버튼 요소를 가져옵니다.
const male = document.getElementById("male");
const female = document.getElementById("female");

// 라디오 버튼 상태를 감지하는 이벤트 리스너를 추가합니다.
male.addEventListener("change", checkRadio);
female.addEventListener("change", checkRadio);

// 라디오 버튼 상태를 확인하고 버튼을 활성화 또는 비활성화하는 함수를 정의합니다.
function checkRadio() {
  if (male.checked || female.checked) {
    nextpage5= 1; // 버튼 활성화
  }; 
};

// -----------------------------------탭으로 다음 페이지 가는거 막기 시작-----------------------------------
// 첫페이지 다음 버튼 
const button = document.getElementById("nextbtn");
// 버튼에 포커스가 들어왔을 때 이벤트 핸들러를 추가합니다.
button.addEventListener("focus", function () {
  // 탭 키를 눌렀을 때 기본 동작을 막습니다.
  function preventTab(event) {
    if (event.key === "Tab") {
      event.preventDefault();
    }
  }
  // 탭 키를 누르는 이벤트 핸들러를 추가합니다.
  document.addEventListener("keydown", preventTab);
  // 버튼에서 포커스가 빠져나갈 때 탭 키를 막는 이벤트 핸들러를 제거합니다.
  button.addEventListener("blur", function () {
    document.removeEventListener("keydown", preventTab);
  });
});
// 두번째 페이지 다음 버튼 
const button2 = document.getElementById("nextbtn2");

// 버튼에 포커스가 들어왔을 때 이벤트 핸들러를 추가합니다.
button2.addEventListener("focus", function () {
  // 탭 키를 눌렀을 때 기본 동작을 막습니다.
  function preventTab(event) {
    if (event.key === "Tab") {
      event.preventDefault();
    }
  }
  // 탭 키를 누르는 이벤트 핸들러를 추가합니다.
  document.addEventListener("keydown", preventTab);
  // 버튼에서 포커스가 빠져나갈 때 탭 키를 막는 이벤트 핸들러를 제거합니다.
  button2.addEventListener("blur", function () {
    document.removeEventListener("keydown", preventTab);
  });
});




// 화면 다음 버튼 흔들기
const shakeButton1 = document.getElementById("nextbtn");
const shakeButton2 = document.getElementById("nextbtn2");

// -----------------------------------/탭으로 다음 페이지 가는거 막기 종료-----------------------------------
// 화면 1~3페이지 전환 버튼
let currentSlide = 1;
const slider = document.querySelector('.full');

// 각 버튼에 대한 클릭 이벤트 리스너를 따로 정의합니다.
shakeButton1.addEventListener("click", function() {
    if (currentSlide < 3 &&  nextpage2 == 1 &&  nextpage1 == 1) { 
        currentSlide++;
        slider.style.transform = `translateX(-${(currentSlide - 1) * 33.33333333}%)`;
        memberjoin_nextbtn_error1.innerText = '';
    } else {
        memberjoin_nextbtn_error1.innerText = '입력칸을 확인해주세요.';
        //버튼 흔들기
        shakeButton1.classList.add("shake");

        // 흔들린 후 0.5초 후에 흔들림 클래스를 제거합니다.
        setTimeout(function() {
            shakeButton1.classList.remove("shake");
        }, 500);
        id_check();
        pw_check();
    }
});

shakeButton2.addEventListener("click", function() {
    if (currentSlide < 3 && nextpage3 == 1 && nextpage4 == 1 && nextpage5 == 1) { 
        currentSlide++;
        slider.style.transform = `translateX(-${(currentSlide - 1) * 33.33333333}%)`;
        memberjoin_nextbtn_error2.innerText = '';
    } else {
        memberjoin_nextbtn_error2.innerText = '입력칸을 확인해주세요.';
        shakeButton2.classList.add("shake");

        // 흔들린 후 0.5초 후에 흔들림 클래스를 제거합니다.
        setTimeout(function() {
            shakeButton2.classList.remove("shake");
        }, 500);
    }
});

        function prevSlide() {
          if (currentSlide > 1) {
            currentSlide--;
            slider.style.transform = `translateX(-${(currentSlide - 1) * 33.33333333}%)`;
          
            memberjoin_nextbtn_error3.innerText = '';
          }
        }

        //프로필 사진 임시 ----------------------------
// 파일 입력 상자의 변경 이벤트 감지
      document.getElementById('profile_picture').addEventListener('change', function () {
        var file = this.files[0];
        var previewImage = document.getElementById('preview_image');

        if (file) {
          // FileReader를 사용하여 선택한 이미지를 읽고 미리보기에 표시
          var reader = new FileReader();
          reader.onload = function (e) {
            previewImage.src = e.target.result;
          };
          reader.readAsDataURL(file);
        } else {
          // 파일이 선택되지 않은 경우 미리보기 이미지 초기화
          previewImage.src = "img/기본프사.jpg";
        }
      });