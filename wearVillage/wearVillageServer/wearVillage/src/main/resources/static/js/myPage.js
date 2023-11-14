{
    document
      .querySelector('.myPageMyClosetButtenSquare')
      .addEventListener('click', function () {
        let button = document.querySelector('.myPageMyClosetButtenSquare');
        let triangle = document.querySelector('.myPageMyClosetButtenTriangle');
        let container = document.querySelector('.myPageMyClosetContainer');

        if (button.style.marginTop === '335px') {
          button.style.marginTop = '0px';
          container.style.height = '50px';
          triangle.style.transform = 'rotate(90deg)';
          triangle.style.marginTop = '5px';
        } else {
          button.style.marginTop = '335px';
          container.style.height = '400px';
          triangle.style.transform = 'rotate(-90deg)';
          triangle.style.marginTop = '-5px';
        }
      });
}
{
    document
      .querySelector('.myPageMyClosetButtenSquare2')
      .addEventListener('click', function () {
        let button2 = document.querySelector('.myPageMyClosetButtenSquare2');
        let triangle2 = document.querySelector(
          '.myPageMyClosetButtenTriangle2',
        );
        let container2 = document.querySelector('.myPageMyClosetContainer2');

        if (button2.style.marginTop === '335px') {
          button2.style.marginTop = '0px';
          container2.style.height = '50px';
          triangle2.style.transform = 'rotate(90deg)';
          triangle2.style.marginTop = '5px';
        } else {
          button2.style.marginTop = '335px';
          container2.style.height = '400px';
          triangle2.style.transform = 'rotate(-90deg)';
          triangle2.style.marginTop = '-5px';
        }
      });
}
{
        let element1 = document.querySelector('.myPageMyInfoNickname');
        let element2 = document.querySelector('.myPageMyInfoEmail');
        let element3 = document.querySelector('.myPageMyInfoPassword');
        let element4 = document.querySelector('.myPageMyInfoBirth');
        let element5 = document.querySelector('.myPageMyInfoGender');
        let elements = [ element1,element2,element3,element4,element5];

        elements.forEach(element => {
            element.addEventListener('mousedown', function() {
                this.classList.add("active");
            });
        });

        document.addEventListener('mouseup', function() {
            elements.forEach(element => {
                element.classList.remove("active");
            });
        });
}


/**
 * @title 프로필 이미지 수정
 * @start 2023-11-14
 * @end 
 * @author 김영환
 * @description 프로필 이미지 수정 작업
 * @status ing
 *
 */
{

  let $myPageProfileImageId = document.getElementById("myPageProfileImageId");
  fetch("/getprofileimg?userNickname="+$th_sessionNickname)
  .then(res=>res.arrayBuffer())
  .then(res=>
    {
      console.log(res);
      console.log("res응답")
      const fileReader = new FileReader();
      let blob = new Blob([res],{type:"image/jpg"})
      let url = URL.createObjectURL(blob);
      console.log(url);
      console.log("url 응답");

    });

}

{
  
  document.querySelector('.myPageProfileSelectBtn1').addEventListener('change', async function (e) {
    //base 블록
    let profileImageView = document.querySelector("#myPageProfileImageId");
    let file= e.target.files[0];
    let blobURL = URL.createObjectURL(file)
    let formData = new FormData();
    let options = {method:"post",
                  body:formData}
    profileImageView.src = blobURL;
    formData.append('uploadFile',file);
    document.getElementById('successMsg').innerHTML="";

    //Data URL 생성

    //파일 유효성 검사. fileCheck==false일 때 조건에 부합하지 않는 걸로 판단하고 프로필 이미지 변경 작업 수행 종료
    if(!fileCheck(file.name,file.size))
    return false;
    
    //파일 저장 요청
    fetch("/uploadProfileImage",options)
    .then(res=>res.json())
    .then(res=>{
      console.log(res)
      let filePath = `${res[0].uploadPath.replaceAll('\\','/')}/${res[0].uuid}_${res[0].fileName}`;
      // 파일 저장 성공시 DataURL해제
      URL.revokeObjectURL(blobURL)
      changeProfimeImgPathInDataBase(filePath)
    }
    );


  });


}


{
  function changeProfimeImgPathInDataBase(Path){
        fetch('/update/profileImg?url=' + encodeURIComponent(encodeURIComponent(Path).replace(/[']/g, escape)))
          .then(response => {
            console.log("프로필 사진 업데이트가 완료되었습니다.");
          })
          .catch(error => {
            console.error('요청 중 오류가 발생했습니다.', error);
          });
        }
}

/**
 * @endPoint 프로필 이미지 수정
 */

// 파일 유효성 검증 로직
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