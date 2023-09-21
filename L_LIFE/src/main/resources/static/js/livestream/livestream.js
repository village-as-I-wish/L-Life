$(document).ready(function() {
    const scrollableDiv = document.getElementById("msgArea");
    console.log("scroll", scrollableDiv.scrollHeight);
// 스크롤이 맨 아래로 내려가도록 설정합니다.
    scrollableDiv.scrollTop = scrollableDiv.scrollHeight;
})
    /*
    * 패키지 상품 할인 ! 장바구니 담기!
    * */
function packageToCart(){
    var existingMemberId =parseInt($('#existingMember').text())
    console.log("testing Package to cart", existingMemberId)
    var carts =[]
    if (isNaN(existingMemberId)) { // 로그인 된 유저에 문제가 있는 경우
        alert('유효한 회원 ID가 아닙니다.');
    } else {
        $('.stream-sub-product-card').each(function(index) { // 전체 상품 추출
            // Create an object to store the extracted values
            var lsProduct = {
                mid: existingMemberId,
                lfId: parseInt($('#' + 'lsProductCard' + index + 'lfId').text()),
                lfOptId: parseInt($('#' + 'lsProductCard' + index + 'lfOptId').text()),
                totalCoin: parseInt($('#' + 'lsProductCard' + index + 'lfStCoin').text()) -1,
                lfPackageId :parseInt($('#liveStreamPackageId').text())
            };
            carts.push(lsProduct);
        });
        data = { carts:carts}
        $.ajax({
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                url: baseUrl + "/l-life/api/v1/standard/carts",
                success: function (res) {
                    Swal.fire({
                        title: '장바구니에 패키지 상품들을 모두 담았습니다.',
                        text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                        imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                    }).then((result) => {
                        if (result.isConfirmed) { // 알림창 확인 시

                        }
                    })
                }
            });
    }
}

function productToCart(productIndex){
    console.log("testing Product to cart")
    // 그냥 장바구니로 보낼 것
    var existingMemberId =parseInt($('#existingMember').text())

    var cartItemVO = {
        mId: existingMemberId,
        lfId: parseInt($('#' + 'lsProductCard' + productIndex + 'lfId').text()),
        lfOptId: parseInt($('#' + 'lsProductCard' + productIndex + 'lfOptId').text()),
        totalCoin: parseInt($('#' + 'lsProductCard' + productIndex + 'lfStCoin').text()),
        lfPackageId :parseInt($('#liveStreamPackageId').text())
    };
    $.ajax({
        type: "POST",
        data:  cartItemVO,
        url: baseUrl + "/l-life/api/v1/standard/cart",
        success: function (res) {
            if(res.result === 0){
                Swal.fire({
                    title: '스탠다드 구독권이 없습니다.',
                    text: '구독권 구매 후 다시 이용해주세요.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                }).then((result) => {
                    if (result.isConfirmed) {

                    }
                })
            }else if(res.result ===1){
                Swal.fire({
                    title: '장바구니에 상품을 담았습니다.',
                    text: '장바구니를 확인해주세요.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                }).then((result) => {
                    if (result.isConfirmed) {

                    }
                })
            }
        } ,
        error: (error) => {
            Swal.fire({
                title: '서버 오류로 장바구니에 담지 못했습니다.',
                text: '잠시 후 다시 이용해주세요.',
                imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
            }).then((result) => {
                if (result.isConfirmed) {

                }
            })
        }

    });

}