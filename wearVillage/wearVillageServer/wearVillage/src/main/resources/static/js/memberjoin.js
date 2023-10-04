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
const id_box = document.getElementById('id_box');
const id_text = document.getElementById('id_text');
const id_text2 = document.getElementById('id_text2');

//pw_box 인스턴스화
//pw_box.value에 따른 문구출력 = pw_text
//id_box에 입력된 값 사용 pw_box.value
const pw_box = document.getElementById('pw_box');
const pw_text = document.getElementById('pw_text');
const pw_text2 = document.getElementById('pw_text2');

//nickname_box 인스턴스화
//nickname_box.value에 따른 문구출력 = nickname_text
//id_box에 입력된 값 사용 nickname_box.value
const nickname_box = document.getElementById('nickname_box');
const nickname_text = document.getElementById('nickname_text');

//email_box 인스턴스화
//email_box.value에 따른 문구출력 = email_text
//email_box에 입력된 값 사용 email_box.value
const email_box = document.getElementById('email_box');
const email_text = document.getElementById('email_text');
const id_check_btn = document.getElementById('id_check_btn');

//생일
const birth_box = document.getElementById('birthday');
//성별
const male_box = document.getElementById('male');
const female_box = document.getElementById('female');

let duplicate_check = false;

//데이터 종합


function gendercheck() {
  return document.querySelector('input[name="gender"]:checked').value;
}
function create_user() {
  id_check();
  pw_check();
  email_check();
  nickname_check();
  birth_check();
  let dataForm = {
    "ID": id_box.value,
    "PW": pw_box.value,
    "EMAIL": email_box.value,
    "GENDER": gendercheck(),
    "BIRTH": inputbirth.value,
    "NICKNAME": nickname_box.value,
    "PROFILEIMG": fileCallPath,
    "THEME": gendercheck(),
  };

  if (
    id_check() &&
    pw_check() &&
    nickname_check() &&
    email_check() &&
    duplicate_check == true
  ) {
    fetch('/finished_signUp', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dataForm),
    }).then(res => res.text().then((location.href = '/')));
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
  if (/^[0-9a-zA-Z]{6,20}$/g.test(id_value_check)) {
    //아이디 중복 체크를 하기 위해서 서버에 id_box.value값 전달
    $.ajax({
      url: '/checkID', // 클라이언트가 요청을 보낼 서버의 URL 주소
      data: { id_box: id_box.value }, // HTTP 요청과 함께 서버로 보낼 데이터
      type: 'post', // HTTP 요청 방식(GET, POST)
      dataType: 'text', // 서버에서 보내줄 데이터의 타입
      success: function (result) {
        //중복된 아이디가 없을 때 = result에서 false반환
        if (result == 'false') {
          duplicate_check = true;
          id_text.style.color = 'rgba(0, 120, 0, 0.600)';
          //수정예정(이미지 체크박스)
          id_text.innerText = '생성 가능한 아이디입니다.';
          nextpage1 = 1;
          //  다음페이지
          return true;
          //중복된 아이디가 있을 때 = result에서 true반환
        } else if (result == 'true') {
          duplicate_check = false;
          id_text.style.color = 'rgba(180, 0, 0, 0.600)';
          //수정예정(이미지 체크박스)
          id_text.innerText = '중복된 아이디입니다.';
          nextpage1 = 0;
          return false;
        } else {
          alert('에러!');
          nextpage1 = 0;
        }
      },
      error: function (error) {
        alert(error);
      },
    });
    return true;
  } else if (id_box.value === '') {
    id_text.style.color = 'rgba(180, 0, 0, 0.600)';
    id_text.innerText = '아이디를 입력하세요.';
    //조건을 충족하지 못한 요소로 화면 이동. 당장에는 회원가입창이 작아서 필요없지만
    //추후 회원가입 화면에 들어가는 요소가 많아졌을 때 고객 편리성을 기대할 수 있음
    id_box.scrollIntoView();
    nextpage1 = 0;
    return false;
  } else {
    id_text.style.color = 'rgba(180, 0, 0, 0.600)';
    id_text.innerText = '아이디를 확인해주세요.';
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
  let hasSpecialcase = /^(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).*$/.test(
    pw_value_check,
  );
  let hasnumbercase = /^(?=.*[0-9]).*$/.test(pw_value_check);
  let haslengthcase = /^.{8,20}.*$/.test(pw_value_check);

  if (pw_value_check == '') {
    pw_text2.textContent = '';
    pw_text.textContent = '비밀번호를 입력하세요.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
  } else if (!hasUpperCase) {
    pw_text2.textContent = '';
    pw_text.textContent = '대문자가 없습니다.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
    nextpage2 = 0;
  } else if (!hasLowerCase) {
    pw_text2.textContent = '';
    pw_text.textContent = '소문자가 없습니다.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
    nextpage2 = 0;
  } else if (!hasSpecialcase) {
    pw_text2.textContent = '';
    pw_text.textContent = '특수문자가 없습니다.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
    nextpage2 = 0;
  } else if (!hasnumbercase) {
    pw_text2.textContent = '';
    pw_text.textContent = '숫자가 없습니다.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
    nextpage2 = 0;
  } else if (!haslengthcase) {
    pw_text2.textContent = '';
    pw_text.textContent = '8 ~ 20자 이하로 작성해야 합니다.';
    pw_text.style.color = "rgba(180, 0, 0, 0.600)";
    nextpage2 = 0;
  } else {
    pw_text.textContent = '';
    pw_text2.textContent = '사용 가능한 비밀번호';
    nextpage2 = 1;
    return true;
  }
}

nickname_box.addEventListener('input', nickname_check);
function nickname_check() {
 

  //아래는 디버깅용 코드. 필요시 활성화
  //console.log("nickname체크 ")
  let nickname_value_check = nickname_box.value;

  // 금지어 목록을 순회하면서 검사
  for (const word of forbiddenWords) {
    if (nickname_value_check.includes(word)) {
      nickname_text.style.color = 'red';
      nickname_text.innerText = '금지어를 포함한 닉네임은 사용할 수 없습니다.';
      nickname_box.scrollIntoView();
      nextpage3 = 0;
      return false; // 금지어가 포함되어 있으면 false 반환
    }
  }

  if (/^.{2,20}$/.test(nickname_value_check)) {
    nickname_text.style.color = 'rgba(0, 120, 0, 0.600)';
    nickname_text.innerText = '올바른 닉네임 형식입니다.';
    nextpage3 = 1;
    return true; // 올바른 형식의 닉네임이면 true 반환
  } else {
    nickname_text.style.color = 'rgba(180, 0, 0, 0.600)';
    nickname_text.innerText = '닉네임을 확인해주세요.';
    nickname_box.scrollIntoView();
    nextpage3 = 0;
    return false; // 잘못된 형식의 닉네임이면 false 반환
  }
}
email_box.addEventListener('input', email_check);

function email_check() {
  //아래는 디버깅용 코드. 필요시 활성화
  let email_value_check = email_box.value;
  // console.log("EMAIL체크 완료");
  if (/\w+[\w.]*@[\w.]+\.\w+/g.test(email_value_check)) {
    email_text.style.color = 'rgba(0, 120, 0, 0.600)';
    email_text.innerText = '올바른 이메일 형식입니다.';
    nextpage6 = 1;
    return true;
  } else {
    email_text.style.color = 'rgba(180, 0, 0, 0.600)';
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
  document.charset = 'utf-8';
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
const inputbirth = document.getElementById('birthday');

// 입력 필드에 이벤트 리스너를 추가합니다.
// 입력된 값을 가져옵니다.
inputbirth.addEventListener('input', birth_check);

function birth_check() {
  const birthValue = inputbirth.value;
  memberjoin_birth_error.textContent = "\u00a0";

  // 입력된 값이 숫자인지 확인합니다.
  if (!isNaN(birthValue)) {
    // 입력된 값이 숫자일 때, 길이가 8자리인지 확인합니다.
    if (birthValue.length === 8) {
      const year = parseInt(birthValue.substring(0, 4), 10);
      const month = parseInt(birthValue.substring(4, 6), 10);
      const day = parseInt(birthValue.substring(6, 8), 10);

      // 날짜가 유효한지 확인합니다.
      const birthDate =
        !isNaN(year) &&
        !isNaN(month) &&
        !isNaN(day) &&
        year >= 1950 &&
        year <= 2030 &&
        month >= 1 &&
        month <= 12 &&
        day >= 1 &&
        day <= 31;

      if (!birthDate || birthDate.length < 8) {
        // alert("올바른 날짜를 입력하세요 (19500101부터 20301231까지).");
        memberjoin_birth_error.textContent = '올바른 날짜를 입력하세요.'; // 입력 필드를 비웁니다.
        nextpage4 = 0;
      }

      if (birthDate) {
        memberjoin_birth_error.textContent = "\u00a0";
        nextpage4 = 1;
      }
    } else {
      memberjoin_birth_error.textContent = '올바른 날짜를 입력하세요.'; // 입력 필드를 비웁니다.
      nextpage4 = 0;
    }
  }
}
//생일 숫자만 최대 8자 입력가능
function numberMaxLength(e) {
  if (e.value.length > e.maxLength) {
    e.value = e.value.slice(0, e.maxLength);
  }
}

//성별
// 라디오 버튼 요소와 버튼 요소를 가져옵니다.
const male = document.getElementById('male');
const female = document.getElementById('female');

// 라디오 버튼 상태를 감지하는 이벤트 리스너를 추가합니다.
male.addEventListener('change', checkRadio);
female.addEventListener('change', checkRadio);

// 라디오 버튼 상태를 확인하고 버튼을 활성화 또는 비활성화하는 함수를 정의합니다.
function checkRadio() {
  if (male.checked || female.checked) {
    nextpage5 = 1; // 버튼 활성화
  }
}

// -----------------------------------탭으로 다음 페이지 가는거 막기 시작-----------------------------------
// 첫페이지 다음 버튼
const button = document.getElementById('nextbtn');
// 버튼에 포커스가 들어왔을 때 이벤트 핸들러를 추가합니다.
button.addEventListener('focus', function () {
  // 탭 키를 눌렀을 때 기본 동작을 막습니다.
  function preventTab(event) {
    if (event.key === 'Tab') {
      event.preventDefault();
    }
  }
  // 탭 키를 누르는 이벤트 핸들러를 추가합니다.
  document.addEventListener('keydown', preventTab);
  // 버튼에서 포커스가 빠져나갈 때 탭 키를 막는 이벤트 핸들러를 제거합니다.
  button.addEventListener('blur', function () {
    document.removeEventListener('keydown', preventTab);
  });
});
// 두번째 페이지 다음 버튼
const button2 = document.getElementById('nextbtn2');

// 버튼에 포커스가 들어왔을 때 이벤트 핸들러를 추가합니다.
button2.addEventListener('focus', function () {
  // 탭 키를 눌렀을 때 기본 동작을 막습니다.
  function preventTab(event) {
    if (event.key === 'Tab') {
      event.preventDefault();
    }
  }
  // 탭 키를 누르는 이벤트 핸들러를 추가합니다.
  document.addEventListener('keydown', preventTab);
  // 버튼에서 포커스가 빠져나갈 때 탭 키를 막는 이벤트 핸들러를 제거합니다.
  button2.addEventListener('blur', function () {
    document.removeEventListener('keydown', preventTab);
  });
});

// 화면 다음 버튼 흔들기
const shakeButton1 = document.getElementById('nextbtn');
const shakeButton2 = document.getElementById('nextbtn2');

// -----------------------------------/탭으로 다음 페이지 가는거 막기 종료-----------------------------------
// 화면 1~3페이지 전환 버튼
let currentSlide = 1;
const slider = document.querySelector('.full');

// 각 버튼에 대한 클릭 이벤트 리스너를 따로 정의합니다.
shakeButton1.addEventListener('click', function () {
  if (currentSlide < 3 && nextpage2 == 1 && nextpage1 == 1) {
    currentSlide++;
    slider.style.transform = `translateX(-${
      (currentSlide - 1) * 33.33333333
    }%)`;
    memberjoin_nextbtn_error1.innerText = '';
  } else {
    memberjoin_nextbtn_error1.innerText = '입력칸을 확인해주세요.';
    //버튼 흔들기
    shakeButton1.classList.add('shake');

    // 흔들린 후 0.5초 후에 흔들림 클래스를 제거합니다.
    setTimeout(function () {
      shakeButton1.classList.remove('shake');
    }, 500);
    id_check();
    pw_check();
  }
});

shakeButton2.addEventListener('click', function () {
  if (currentSlide < 3 && nextpage3 == 1 && nextpage4 == 1 && nextpage5 == 1) {
    currentSlide++;
    slider.style.transform = `translateX(-${
      (currentSlide - 1) * 33.33333333
    }%)`;
    memberjoin_nextbtn_error2.innerText = '';
  } else {
    memberjoin_nextbtn_error2.innerText = '입력칸을 확인해주세요.';
    shakeButton2.classList.add('shake');

    // 흔들린 후 0.5초 후에 흔들림 클래스를 제거합니다.
    setTimeout(function () {
      shakeButton2.classList.remove('shake');
    }, 500);
  }
});

function prevSlide() {
  if (currentSlide > 1) {
    currentSlide--;
    slider.style.transform = `translateX(-${
      (currentSlide - 1) * 33.33333333
    }%)`;

    memberjoin_nextbtn_error3.innerText = '';
  }
}

//프로필 사진 임시 ----------------------------
// 파일 입력 상자의 변경 이벤트 감지
document
  .getElementById('profile_picture')
  .addEventListener('change', function () {
    let formData = new FormData();
    let fileInput = document.querySelectorAll('input[name="uploadFile"');
    let fileList = fileInput[0].files;
    let fileObj = fileList[0];

    if (!fileCheck(fileObj.name, fileObj.size)) {
      return false;
    }

    formData.append('uploadFile', fileObj);

    $.ajax({
      url: '/uploadProfileImage',
      processData: false,
      contentType: false,
      data: formData,
      type: 'POST',
      dataType: 'json',
      success: function (result) {
        console.log(result);
        showUploadImage(result);
      },
      error: function (result) {
        console.log(error);
        console.log('이미지 파일이 아닙니다.');
      },
    });

    // 레귤러익스프레션
    let regex = new RegExp('(.*?).(jpg|png)$');
    let maxSize = 21048576;

    function fileCheck(fileName, fileSize) {
      let regex = new RegExp('(.*?).(jpg|png)$');
      let maxSize = 21048576;
      if (fileSize >= maxSize) {
        alert('파일 사이즈가 너무 큽니다');
        return false;
      }
      if (!regex.test(fileName)) {
        alert('해당 종류의 파일은 업로드가 불가능합니다.');
        return false;
      }
      return true;
    }

    // 이미지 받아와서 출력하기
    function showUploadImage(uploadResultArr) {
      let images = [];

      if (!uploadResultArr || uploadResultArr.length == 0) {
        return;
      }
      let uploadResult = $('#preview_image');

      let obj = uploadResultArr[0];
      let str = '';
      let fileCallPath =
        obj.uploadPath.replace(/\\/g, '/') +
        '/' +
        obj.uuid +
        '_' +
        obj.fileName;
      fileCallPath = encodeURIComponent(fileCallPath);

      let previewImage = document.getElementById('preview_image');
      // previewImage.src = '/display?fileName=' + fileCallPath;
      previewImage.setAttribute('src', '/display?fileName=' + fileCallPath);
      // str = "<img src='/display?fileName" + fileCallPath + "'>";

      // uploadResult.append(str);
    }
  });
