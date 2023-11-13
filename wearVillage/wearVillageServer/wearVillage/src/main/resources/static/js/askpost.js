
// 전화번호 하이폰 자동생성
const hypenTel = (target) => {
  target.value = target.value
    .replace(/[^0-9]/g, '')
    .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

// 문의 유형
const inquiryTypeSelect = document.getElementById('inquiryType');
const inquiryTypeError = document.getElementById('inquiryTypeError');
// 제목
const titleInput = document.getElementById('titleInput');
const titleError = document.getElementById('titleError');
// 내용입력
const contentInput = document.getElementById('contentInput');
const contentError = document.getElementById('contentError');
// 이메일
const emailInput = document.getElementById('emailInput');
const emailError = document.getElementById('emailError');
// 체크박스
const agreeCheckbox = document.getElementById('agreeCheckbox');
const agreeError = document.getElementById('agreeError');
const submitButton = document.getElementById('submitButton');


function validateForm() {
  const selectedInquiryType = inquiryTypeSelect.value;
  const titleValue = titleInput.value.trim();
  const contentValue = contentInput.value.trim();
  const emailValue = emailInput.value.trim();
  const isChecked = agreeCheckbox.checked;
  const formError = submitButton.checked;

  let hasError = false;
//문의 유형 확인
  if (selectedInquiryType === "") {
    inquiryTypeError.textContent = "문의 유형을 선택해주세요.";
    hasError = true;
    inquiryTypeSelect.addEventListener("input", function () {
    inquiryTypeError.textContent = "";
  })};
//제목 입력 자동확인
  if (titleValue === "") {
    titleError.textContent = "제목을 입력해주세요.";
    hasError = true;
    titleInput.addEventListener("input", function () {
    titleError.textContent = "";
  })};

  //내용 입력 자동확인
  if (contentValue === "") {
    contentError.textContent = "내용을 입력해주세요.";
    hasError = true;
    contentInput.addEventListener("input", function () {
      contentError.textContent = "";
    })
  };

  // 이메일 입력 자동확인
  if (emailValue === "") {
    emailError.textContent = "이메일을 입력해주세요.";
    hasError = true;
    emailInput.addEventListener("input", function () {
      var emailValue = emailInput.value;
      if (!validateEmail(emailValue)) {
        emailError.textContent = "올바른 이메일 형식을 입력해주세요.";
        hasError = true;
      } else
        emailError.textContent = "";
      }
    )}if(!validateEmail(emailValue)){
emailError.textContent = "올바른 이메일 형식을 입력해주세요.";
emailInput.addEventListener("input", function () {
      var emailValue = emailInput.value;
      if(!validateEmail(emailValue)){
        emailError.textContent = "올바른 이메일 형식을 입력해주세요.";
      }else{
         emailError.textContent = "";
      }
    })};


  //체크박스 자동 확인
  if (isChecked === false) {
    agreeError.textContent = "개인정보 수집 동의에 체크해주세요.";
    hasError = true;
    agreeCheckbox.addEventListener("input", function () {
      agreeError.textContent = "";
    })
  };


  // if (hasError === false) {
  //   formError.textContent = "필수 입력 항목을 모두 입력해주세요.";
  // } else {
  //   formError.textContent = "제출되었습니다.";
  // }
}




//이메일 형식 확인
function validateEmail(email) {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  return emailPattern.test(email);
}