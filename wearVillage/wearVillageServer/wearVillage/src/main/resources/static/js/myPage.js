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

{
  document.querySelector('.myPageProfileSelectBtn1').addEventListener('change', function () {
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
      let uploadResult = $('#myPageProfileImageId');

      let obj = uploadResultArr[0];
      let str = '';
      let fileCallPath =
        obj.uploadPath.replace(/\\/g, '/') +
        '/' +
        obj.uuid +
        '_' +
        obj.fileName;
      fileCallPath = encodeURIComponent(fileCallPath);
      profileimg = fileCallPath;

      let previewImage = document.getElementById('myPageProfileImageId');
      // previewImage.src = '/display?fileName=' + fileCallPath;
      previewImage.setAttribute('src', '/display?fileName=' + fileCallPath);
      // str = "<img src='/display?fileName" + fileCallPath + "'>";

      // uploadResult.append(str);
    }
  });


}

{
  let uploadFileInput = document.getElementById('uploadFile');
  let previewImage = document.getElementById('myPageProfileImageId');

  uploadFileInput.addEventListener('change', event => {
    let file = event.target.files[0];
    if (file) {
      // FileReader 객체를 생성합니다.
      let reader = new FileReader();

      // 'load' 이벤트 핸들러를 추가합니다.
      reader.addEventListener('load', event => {
        // 이미지의 src 속성을 업데이트합니다.
        previewImage.src = event.target.result;

        // 이미지 src 값이 변경되었으므로 이 값을 가져옵니다.
        let srcValue = previewImage.src;

        console.log(srcValue); // 콘솔에 src 속성값을 출력해 확인할 수 있습니다.

        // 서버에 요청을 보냅니다.
        fetch('/update/profileImg?url=' + encodeURIComponent(srcValue))
          .then(response => {
            if (response.ok) {
              console.log('이미지 업데이트 요청이 성공했습니다.');
            } else {
              console.error('이미지 업데이트 요청이 실패했습니다.');
            }
          })
          .catch(error => {
            console.error('요청 중 오류가 발생했습니다.', error);
          });
      });

      // 선택한 파일의 데이터 URL을 읽습니다.
      reader.readAsDataURL(file);
    }
  });
