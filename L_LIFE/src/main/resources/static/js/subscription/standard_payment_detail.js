// $(document).ready(function() {
//     let selectedPaymentMethod = "";
//
//     $("input[name='payment-method']").click(function () {
//         if ($(this).is(":checked")) {
//             selectedPaymentMethod = $(this).val(); // 선택된 값 저장
//         }
//     });
//
//
//
// });

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
 * 결제 함수
 */
function payment(baseUrl){
        console.log(baseUrl)
        var selectedPaymentMethod = $(".payment-btn-group input[type='radio']:checked").val();
        if (selectedPaymentMethod === "") {
            alert('결제 방식을 선택해주세요');
            return;
        }
        // 선택되지 않았을 때 예외 처리 필요

        // 여기에 결제 로직 추가
        if (selectedPaymentMethod === "kakao") {
            paymentKaKao(payKeys.kakaoPayKey);
        } else if (selectedPaymentMethod === "toss") {
            paymentToss(payKeys.tossPayKey, baseUrl);
        } else if(selectedPaymentMethod==""){
            alert("결제 방식을 선택해주세요. ");
            return;
        }else{
            alert('다른 결제 방식을 선택해주세요.');
            return;
        }
}
/**
 * 카카오 결제 함수
 */
function paymentKaKao(kakaoPayKey) {
    data = createPaymentData()
    console.log(data)
    IMP.init(kakaoPayKey); // imp 번호
    IMP.request_pay({
            pg: "kakaopay",
            pay_method: 'card',
            merchant_uid: data.orderId,
            name: data.orderName,
            amount: data.amount,
            buyer_name: data.customerName,
        },
        function (rsp) { // callback
            if (rsp.success) {
               alert('결제에 성공하였습니다. ');
               return ;

            } else {
                // 결제 실패 시 로직,
                alert('결제에 실패하였습니다. ');
            }
        });
}

/**
 * 토스 페이먼츠 결제 함수
 */
function paymentToss(tossPayKey, contextUrl){
    var tossPayments = TossPayments(tossPayKey)
    data = createPaymentData()
    console.log(data)
    var successUrl = baseUrl + '/l-life/member/1/mypage'; // 결제 성공 시 이동할 페이지 -> 변경 필요
    var failUrl = baseUrl + '/l-life/member/1/mypage'; // 결제 실패 시 이동할 페이지 -> 변경 필요
    tossPayments.requestPayment('카드', {
        amount: data.amount,
        orderId:  data.orderId,
        orderName: data.orderName,
        customerName: data.customerName,
    }).then((res) =>{
        $.ajax({
            type : "POST",
            url : baseUrl +"/l-life/api/v1/subscription",
            // data : params,
            success : function(res){
                alert(res.code);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
                alert("통신 실패.")
            }
        });
    }).catch(function (error) {
            if (error.code === 'USER_CANCEL') {
                // 결제 고객이 결제창을 닫았을 때 에러 처리
            } else if (error.code === 'INVALID_CARD_COMPANY') {
                // 유효하지 않은 카드 코드에 대한 에러 처리
            }
        });

}

/**
 * 결제 데이터 생성 함수
 */
function createPaymentData(){
    var amount =  $('#final-price').text()
    var orderId =  createOrderNum() // 주문 ID -> 변경 필요
    var orderName = $('#product-name').text()
    var customerName = '김승주'

    data ={
        amount : amount,
        orderId : orderId,
        orderName: orderName,
        customerName : customerName
    }
    return data

}
