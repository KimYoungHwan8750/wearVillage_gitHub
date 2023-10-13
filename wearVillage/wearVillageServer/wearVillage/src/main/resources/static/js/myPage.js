let $UpdateBtn = document.getElementById('myPageUpdateBtnId');
$UpdateBtn.pointer

    //버튼 클릭 애니메이션
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
      //버튼클릭 애니메이션 끝

        //내정보 active 시작
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
        //내정보 active 끝