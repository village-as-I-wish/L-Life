let optionId;

let selectedFiles = [];

$(document).ready(function(){
    var productId = parseInt($('#productId').val());

    $(".dropdown-content div").click(function() {
        var selectedOptionId = parseInt($(this).data('lf-opt-id'));
        var memberId = parseInt($('#memberId').val());
        console.log("memberId는 바로" + memberId);
        $.ajax({
            url: '/l-life/api/v1/standard/checkStock/' + productId + '/' + selectedOptionId,
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
                                    url: '/l-life/api/v1/standard/reservation',
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

    $('#photoRegist').on('change', function(event) {
        const files = event.target.files;
        if (files.length > 0) {
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                const reader = new FileReader();

                reader.onload = function(e) {
                    // 파일 데이터와 이미지 URL을 객체에 저장
                    const fileData = {
                        file: file,
                        imageUrl: e.target.result
                    };
                    selectedFiles.push(fileData);

                    // 선택한 이미지를 리스트에 추가
                    const listItem = `
                    <li id="upload-img">
                        <img src="${fileData.imageUrl}" alt="리뷰 사진"/>
                        <button class="btn-del" onclick="removeImage(this, ${i})"></button>
                    </li>
                `;
                    $('#imageList').append(listItem);
                };

                reader.readAsDataURL(file);
            }
        }
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

function removeImage(button, index) {
    $(button).parent().remove();
    selectedFiles.splice(index, 1);

}


/* 첨부파일 검증 */
function validation(obj){
    const fileTypes = ['application/pdf', 'image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif', 'application/haansofthwp', 'application/x-hwp'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}


function submitReview(memberId){

    const formData = new FormData();

    let lfId = parseInt($('#modal-lf-id').text())
    let title = $('#reviewTitle').val()
    let content = $('#reviewContents').val()
    let delratingValue = parseInt($("input[name='delivery-rating']:checked").val()) || 0;
    let totalratingValue = parseInt($("input[name='rating']:checked").val()) || 0;
    let serratingValue = parseInt($("input[name='service-rating']:checked").val()) || 0;

    formData.append('mId', memberId);
    formData.append('lfId', lfId);
    formData.append('lfReviewTitle', title);
    formData.append('lfReviewContent', content);
    formData.append('lfReviewType', 0);
    formData.append('lfReviewDelRating',delratingValue)
    formData.append('lfReviewSerRating',serratingValue)
    formData.append('lfReviewRating',totalratingValue)

    var files = $('#photoRegist')[0].files;
    for (var i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
    }

    $.ajax({
        type : "POST",
        data : formData,
        contentType: false,
        processData: false,
        url : baseUrl +"/l-life/api/v1/standard/review",
        success : function(res){
                Swal.fire({
                    title: '리뷰 등록이 완료되었습니다.',
                    text: '소중한 의견 감사드립니다.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                }).then((result) => {
                    if (result.isConfirmed) {
                    }
                })
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            Swal.fire({
                title: '리뷰 등록에 실패하였습니다.',
                text: '잠시 후 다시 작성 부탁드립니다.',
                imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
            }).then((result) => {
                if (result.isConfirmed) {

                }
            });
        }
    });


}
function deleteReview(reviewId){
    console.log("[ 리뷰 삭제 ] : ", reviewId);
    Swal.fire({
        title: '리뷰를 삭제하시겠습니까?',
        text: '삭제 시 해당 리뷰는 더 이상 볼 수 없습니다.',
        imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
    }).then((result) => {
        if (result.isConfirmed) {
            data ={
                lf
            }
            $.ajax({
                type : "POST",
                data : formData,
                contentType: false,
                processData: false,
                url : baseUrl +"/l-life/api/v1/standard/review",
                success : function(res){
                    Swal.fire({
                        title: '리뷰 등록이 완료되었습니다.',
                        text: '소중한 의견 감사드립니다.',
                        imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                    }).then((result) => {
                        if (result.isConfirmed) {
                        }
                    })
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    Swal.fire({
                        title: '리뷰 등록에 실패하였습니다.',
                        text: '잠시 후 다시 작성 부탁드립니다.',
                        imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                    }).then((result) => {
                        if (result.isConfirmed) {

                        }
                    });
                }
            });
        }
    });

}