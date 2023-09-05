$(document).ready(function() {
    calendarInit();

    var totalPrice = 0;
    $('.sum-price').each(function () {
        var rowTotal = parseInt($(this).text());
        totalPrice += rowTotal;
    });

    // 누적된 총 합계를 결과 요소에 표시합니다.
    $('.sum-price-num').text(totalPrice.toLocaleString() + '원');
    $('.price-num').text(totalPrice.toLocaleString() + '원');
    $('.final-price-num').text(totalPrice.toLocaleString() + '원');

});

function formatDate(date) {
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0');
    var day = date.getDate().toString().padStart(2, '0');
    return year + '-' + month + '-' + day;
}

function calendarInit() {
    var date = new Date();
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000);
    var kstGap = 9 * 60 * 60 * 1000;
    var today = new Date(utc + kstGap);

    var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());

    var currentYear = thisMonth.getFullYear();
    var currentMonth = thisMonth.getMonth();
    var currentDate = thisMonth.getDate();

    renderCalendar(thisMonth);

    function renderCalendar(thisMonth) {
        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();

        var startDay = new Date(currentYear, currentMonth, 1);
        var prevDate = new Date(startDay - 1).getDate();
        var prevDay = startDay.getDay();

        var endDay = new Date(currentYear, currentMonth + 1, 0);
        var nextDate = endDay.getDate();
        var nextDay = endDay.getDay();

        $('.year-month').text(currentYear + '.' + (currentMonth + 1));

        var calendar = $('.calendar');
        calendar.html('');

        calendar.append('<div class="weekdays">' +
            '<span class="day">일</span>' +
            '<span class="day">월</span>' +
            '<span class="day">화</span>' +
            '<span class="day">수</span>' +
            '<span class="day">목</span>' +
            '<span class="day">금</span>' +
            '<span class="day">토</span>' +
            '</div>');

        var days = $('<div class="days"></div>');

        for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
            var date = new Date(currentYear, currentMonth - 1, i);
            var dateString = formatDate(date);
            days.append('<button class="day prev">' + i + '</button>');
        }

        for (var i = 1; i <= nextDate; i++) {
            var date = new Date(currentYear, currentMonth, i);
            var dateString = formatDate(date);
            days.append('<button class="day current">' + i + '</button>');
        }

        for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
            var date = new Date(currentYear, currentMonth + 1, i);
            var dateString = formatDate(date);
            days.append('<button class="day next">' + i + '</button>');
        }

        calendar.append(days);

        if (today.getMonth() === currentMonth) {
            var todayDate = today.getDate();
            var currentMonthDate = $('.days .current');
            currentMonthDate.eq(todayDate - 1).addClass('today');
        }
    }

    $('.go-prev').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth - 1, 1);
        renderCalendar(thisMonth);
    });

    $('.go-next').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth + 1, 1);
        renderCalendar(thisMonth);
    });
}
$(document).ready(function() {
    $('.time-btn').on('click', function() {
        $('.time-btn').removeClass('selected');
        $(this).addClass('selected');
        var selectedTime = $(this).data('value');
        console.log('Selected time:', selectedTime);
        // 선택된 시간에 대한 처리 작업 수행
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



/* 결제 관련 */
function createPaymentData(){
    var amount =  1 // 주문 금액
    var orderId =  createOrderNum() // 주문 ID -> 변경 필요
    var orderName = 'testing' // 상품명으로 바꿔야 함.
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
 * 결제 함수
 */
function payment(memberId){
    var selectedPaymentMethod = $(".payment-btn-group input[type='radio']:checked").val();
    if (selectedPaymentMethod === "") {
        alert('결제 방식을 선택해주세요');
        return;
    }
    // 선택되지 않았을 때 예외 처리 필요
    console.log("testing")
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
    console.log(data,kakaoPayKey);

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

function paymentSuccess(memberId){

    // var subscriptionPlanIdTxt = $('#subscriptionPlanId').text()
    // var subscriptionPlanId = parseInt(subscriptionPlanIdTxt);
    // data={
    //     subscriptionPlanId:  subscriptionPlanId, // 스탠다드 라인
    // }
    // $.ajax({
    //     type : "POST",
    //     data : data,
    //     url : baseUrl +"/l-life/api/v1/subscription",
    //     success : function(res){
    //         if (res.result == 1){
    //             Swal.fire({
    //                 title: '구독이 시작되었습니다.',
    //                 text: '리바트 라이프와 함께 해주셔서 감사합니다.',
    //                 imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
    //             }).then((result) => {
    //                 if (result.isConfirmed) {
    //                     window.location.href = baseUrl + '/l-life/member/' + memberId + '/mypage'
    //                 }
    //             })
    //         }else{
    //             Swal.fire({
    //                 title:'구독 중인 상품이 있습니다.',
    //                 text: '마이페이지를 확인해주세요.',
    //                 imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
    //             }).then((result) => {
    //                 if (result.isConfirmed) {
    //                     window.location.href = baseUrl + '/l-life/member/' + memberId + '/mypage'
    //                 }
    //             })
    //         }
    //     },
    //     error : function(XMLHttpRequest, textStatus, errorThrown){
    //         alert("통신 실패.")
    //     }
    // });

}