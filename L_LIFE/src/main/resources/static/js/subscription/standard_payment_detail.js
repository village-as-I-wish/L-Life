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
function payment(memberId){
        var selectedPaymentMethod = $(".payment-btn-group input[type='radio']:checked").val();
        if (selectedPaymentMethod === "") {
            alert('결제 방식을 선택해주세요');
            return;
        }
        // 선택되지 않았을 때 예외 처리 필요

        // 여기에 결제 로직 추가
        if (selectedPaymentMethod === "kakao") {
            paymentKaKao(payKeys.kakaoPayKey, memberId);
        } else if (selectedPaymentMethod === "toss") {
            paymentToss(payKeys.tossPayKey, memberId);
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
function paymentKaKao(kakaoPayKey, memberId) {
    data = createPaymentData()
    IMP.init(kakaoPayKey);
    IMP.request_pay({
            pg: "kakaopay",
            pay_method: 'card',
            merchant_uid: data.orderId,
            name: data.orderName,
            amount: data.amount,
            buyer_name: data.customerName,
        },
        function (rsp) {
            if (rsp.success) {
               paymentSuccess(memberId);
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
function paymentToss(tossPayKey, memberId){
    var tossPayments = TossPayments(tossPayKey)
    data = createPaymentData()
    var successUrl = baseUrl + '/l-life/member/1/mypage'; // 결제 성공 시 이동할 페이지 -> 변경 필요 (memberId)
    var failUrl = baseUrl + '/l-life/member/1/mypage'; // 결제 실패 시 이동할 페이지 -> 변경 필요 (memberId)
    tossPayments.requestPayment('카드', {
        amount: data.amount,
        orderId:  data.orderId,
        orderName: data.orderName,
        customerName: data.customerName,
    }).then((res) =>{
        paymentSuccess(memberId);


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
 * @returns {data}
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

/**
 * 결제 성공시 호출 알림
 */
function paymentSuccess(memberId){

    var subscriptionPlanIdTxt = $('#subscriptionPlanId').text()
    var subscriptionPlanId = parseInt(subscriptionPlanIdTxt);
    data={
        subscriptionPlanId:  subscriptionPlanId, // 스탠다드 라인
    }
    $.ajax({
        type : "POST",
        data : data,
        url : baseUrl +"/l-life/api/v1/subscription",
        success : function(res){
            if (res.result == 1){
                Swal.fire({
                    title: '구독이 시작되었습니다.',
                    text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = baseUrl + '/l-life/member/' + memberId + '/mypage'
                    }
                })
            }else{
                Swal.fire({
                    title:'구독 중인 상품이 있습니다.',
                    text: '마이페이지를 확인해주세요.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = baseUrl + '/l-life/member/' + memberId + '/mypage'
                    }
                })
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("통신 실패.")
        }
    });

}