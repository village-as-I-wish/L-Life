let optionId;
$(document).ready(function(){
    var productId = parseInt($('#productId').val());

    $(".dropdown-content div").click(function() {
        var selectedOptionId = parseInt($(this).data('lf-opt-id'));
        var memberId = parseInt($('#memberId').val());
        console.log("memberId는 바로" + memberId);
        $.ajax({
            url: '/l-life/standard/checkStock/' + productId + '/' + selectedOptionId,
            method: 'GET',
            success: function (stockAmount) {
                var button = $('.lf-pr-submit-btns form button');
                console.log(stockAmount);
                if (stockAmount <= 0) {
                    // 옵션의 재고가 없다면 '장바구니 담기' 버튼을 '재입고 알림' 버튼으로 변경
                    button.text('재입고 알림 신청');
                    button.off('click').click(function() {
                        event.preventDefault();
                        Swal.fire({
                            title: '재고가 없습니다.',
                            text: '재입고 알림을 신청하시겠습니까?',
                            confirmButtonText: '신청하기',
                            cancelButtonText: '취소',
                            showCancelButton: true
                        }).then((result) => {
                            if (result.isConfirmed) {
                                // TODO : 재입고알림신청 버튼 클릭시 관련 API 호출
                                data = {
                                    lfOptId: selectedOptionId,
                                    lfId: productId,
                                    memberId: memberId,
                                }
                                console.log(data);
                                $.ajax({
                                    url: '/l-life/standard/reservation',
                                    method: 'POST',
                                    data: data,
                                    success: function (response) {
                                        console.log("재입고 알림 신청 완료")
                                        console.log(response)
                                    },
                                    error: function (error) {
                                        console.log("재입고 알림 신청 실패")
                                        console.log(error)
                                    }
                                });
                            }
                        });
                    });
                } else {
                    button.text('장바구니 담기');
                    button.off('click');
                }
            }
        });
    });

    $('.lf-pr-submit-btns form').on('submit', function(event) {
        event.preventDefault();
        Swal.fire({
            title: '장바구니에 추가되었습니다.',
            text: '장바구니로 이동하시겠습니까?',
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            showCancelButton: true,
        }).then((result) => {
            if (result.isConfirmed) {
                console.log(optionId)
                console.log(productId)
                // TODO : 장바구니 담기 버튼 클릭시 관련 API 호출
                const data = {
                    lfOptId: optionId,
                    lfId: productId,
                };
                $.ajax({
                    url: '#',
                    method: 'POST',
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    success: function(response) {
                        console.log("장바구니에 담기 완료");
                        console.log(response);
                        // 확인 -> 장바구니 페이지로 이동
                        window.location.href = "/l-life/member/mypage/cart";
                    },
                    error: function(error) {
                        window.location.href = "/l-life/member/mypage/cart";
                        console.log("장바구니에 담기 실패");
                        console.log(error);
                    }
                });
            }
        });
    });


    $('.lf-pr-main-content .main-tab li').click(function(){
        var tab_id = $(this).attr('data-tab');

        $('ul.tabs li').removeClass('current');
        $('.tab-content').removeClass('current');

        $(this).addClass('current');
        $("#"+tab_id).addClass('current');
    });

    $('.lf-pr-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.next'),
        prevArrow:$('.prev'),
    });

    $('.lf-recommendation-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.next'),
        prevArrow:$('.prev'),
    });

});
window.onload=()=>{
    document.querySelector('.dropbtn_click').onclick = ()=>{
        dropdown();
    }
    document.getElementsByClassName('fastfood').onclick = ()=>{
        showMenu(value);
    };
    dropdown = () => {
        var v = document.querySelector('.dropdown-content');
        var dropbtn = document.querySelector('.dropbtn')
        v.classList.toggle('show');
        dropbtn.style.borderColor = 'rgb(94, 94, 94)';
    }

    showMenu=(value)=>{
        var dropbtn_content = document.querySelector('.dropbtn_content');
        dropbtn_content.innerText = value;
        console.log(value);

        var dropdownContents = document.querySelectorAll('.dropdown-content div');
        dropdownContents.forEach((content) => {
            if (content.getAttribute('data-option-text') === value) {
                optionId = content.getAttribute('data-lf-opt-id');
            }
        });
    }
}
window.onclick= (e)=>{
    if(!e.target.matches('.dropbtn_click')){
        var dropdowns = document.getElementsByClassName("dropdown-content");

        var dropbtn_icon = document.querySelector('.dropbtn_icon');
        var dropbtn_content = document.querySelector('.dropbtn_content');
        var dropbtn_click = document.querySelector('.dropbtn_click');
        var dropbtn = document.querySelector('.dropbtn');

        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}