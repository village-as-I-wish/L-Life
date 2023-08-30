$(document).ready(function() {
    let selectedPaymentMethod = "";

    $("input[name='payment-method']").click(function () {
        if ($(this).is(":checked")) {
            selectedPaymentMethod = $(this).val(); // 선택된 값 저장
        }
    });

    // 다른 곳에서 selectedPaymentMethod 사용 가능
    $(".payment-button").click(function () {
        if (selectedPaymentMethod === "") {
            alert('결제 방식을 선택해주세요');
            return;
        }
        // 여기에 결제 로직 추가
        if (selectedPaymentMethod === "kakao") {
            paymentKaKao(payKeys.kakaoPayKey);
        } else if (selectedPaymentMethod === "toss") {
            paymentToss(payKeys.tossPayKey);
        } else if(selectedPaymentMethod==""){
            alert("결제 방식을 선택해주세요. ");
            return;
        }else{
            alert('다른 결제 방식을 선택해주세요.');
            return;
        }
    });

});
/**
 * 결제 주문 번호 생성 함수
 * @returns {string}
 */
function createOrderNum(){
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for(let i=0;i<10;i++) {
        orderNum += Math.floor(Math.random() * 8);
    }
    return orderNum;
}

/**
 * 카카오 결제 함수
 */
function paymentKaKao(kakaoPayKey) {

    IMP.init(kakaoPayKey); // imp 번호
    IMP.request_pay({
            pg: "kakaopay",
            pay_method: 'card',
            merchant_uid: createOrderNum(),
            name: '정기 구독권 33',
            amount: 100,
            buyer_email: "",
            buyer_name: "sj1" + new Date().getTime(),
            buyer_tel: "010-1111-2222",
            // buyer_addr: data.deleveryAddress2 + " " + data.deleveryAddress3,
            // buyer_postcode: data.deleveryAddress1,
            // m_redirect_url: m_redirect,
        },
        function (rsp) { // callback
            if (rsp.success) {
                // 결제 성공 시 로직,
                console.log("결제가 완료되었습니다. ")
                // data.impUid = rsp.imp_uid;
                // data.merchant_uid = rsp.merchant_uid;
                // paymentComplete(data);

            } else {
                // 결제 실패 시 로직,
                console.log("결제에 실패하였습니다. . ")
            }
        });
}

/**
 * 토스 페이먼츠 결제 함수
 */
function paymentToss(tossPayKey){
    var clientKey = tossPayKey // 키값 숨겨야 함!
    var tossPayments = TossPayments(clientKey)

    tossPayments.requestPayment('카드', {
        amount: 100000, // 결제 금액
        orderId:  createOrderNum(), // 주문 ID -> 변경 필요
        orderName: '스탠다드 구독권 33', // 주문명
        customerName: '김토스', // 구매자 이름
        successUrl: 'https://docs.tosspayments.com/guides/payment/test-success', // 결제 성공 시 이동할 페이지 -> 변경 필요
        failUrl: 'https://docs.tosspayments.com/guides/payment/test-fail', // 결제 실패 시 이동할 페이지 -> 변경 필요
    })
        // https://docs.tosspayments.com/reference/error-codes#결제창공통-sdk-에러
        .catch(function (error) {
            if (error.code === 'USER_CANCEL') {
                // 결제 고객이 결제창을 닫았을 때 에러 처리
            } else if (error.code === 'INVALID_CARD_COMPANY') {
                // 유효하지 않은 카드 코드에 대한 에러 처리
            }
        });
}


