

$(document).ready(function() {
    // $('.return-button').click(function(event) {
    //     // event.preventDefault(); // 기본 동작 방지 (링크 이동 방지)
    //     const button = $(this); // 현재 클릭한 버튼
    //     const productId = $(this).data('product-id');
    //
    //     $.ajax({
    //         url: `/l-life/api/v1/member/return/${productId}`,
    //         method: 'POST',
    //         success: function(data) {
    //             console.log('반납 요청 성공');
    //             // 버튼의 텍스트 변경
    //             button.text('반납 완료');
    //
    //             // 버튼 비활성화
    //             button.attr('disabled', 'disabled').off('click');
    //
    //             // 버튼 색상 변경
    //             button.css('background-color', 'gray');
    //             Swal.fire({
    //                 title: '반납이 완료되었습니다.',
    //                 text: '리바트 라이프와 함께 해주셔서 감사합니다.',
    //                 imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
    //             })
    //         },
    //         error: function(xhr, status, error) {
    //             console.error('반납 요청 실패:', error);
    //         }
    //     });
    // });
});
