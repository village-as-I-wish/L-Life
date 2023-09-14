
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

function productToCart(){
    console.log("testing Product to cart")
    // 그냥 장바구니로 보낼 것
}