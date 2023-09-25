<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
    // JavaScript 코드는 이 부분에 포함됩니다.
    var IMP = window.IMP;
    IMP.init("imp03533685");

    var today = new Date();
    var hours = today.getHours();
    var minutes = today.getMinutes();
    var seconds = today.getSeconds();
    var milliseconds = today.getMilliseconds();
    var makeMerchantUid = hours + minutes + seconds + milliseconds;

    function requestPay() {
        // 이 함수는 "결제하기" 버튼을 클릭하면 호출됩니다.
        // 결제 요청을 수행하는 코드가 들어갑니다.
        IMP.init('imp03533685');
        IMP.request_pay({
            pg: 'html5_inicis.INIpayTest',
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: '주문명:결제테스트'/*상품명*/,
            amount: 1000/*상품 가격*/,
            buyer_email: 'iamport@siot.do'/*구매자 이메일*/,
            buyer_name: '구매자이름',
            buyer_tel: '010-1234-5678'/*구매자 연락처*/,
            buyer_addr: '서울특별시 강남구 삼성동'/*구매자 주소*/,
            buyer_postcode: '123-456'/*구매자 우편번호*/
        }, function (rsp) { // callback
            if (rsp.success) {
                console.log(rsp);
            } else {
                console.log(rsp);
            }
        });
    };
</script>