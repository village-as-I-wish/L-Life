
function subscribePrimium(mId){
    console.log("mId", mId);

    if(mId === null || mId==='undefined'){
        console.log("로그인 되지 않음");
        Swal.fire({
            title: '로그인이 필요한 서비스입니다.',
            html: '로그인 후 이용 가능합니다.<br> 로그인 페이지로 이동합니다.',
            imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
        }).then((result) => {
            if (result.isConfirmed) { // 알림창 확인 시
                window.location.href = baseUrl+ '/l-life/member/login'
            }
            return;
        });
    }else{
        Swal.fire({
            title: '프리미엄 구독 서비스를 이용하시겠습니까?.',
            html: '가입 시 프리미엄 구독 서비스를 이용하실 수 있으며 <br> 클럽 집테리어에 가입됩니다.',
            imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
        }).then((result) => {
            if (result.isConfirmed) { // 알림창 확인 시
                $.ajax({
                    url: baseUrl+'/l-life/api/v1/subscription/plan/premium',
                    method: "POST",
                    data :{
                        mId: mId
                    },
                    success: function(response) {
                        if (response.code === 200 && response.result ==1) {
                            Swal.fire({
                                title: '프리미엄 구독 서비스 가입이 완료되었습니다.',
                                html: '리바트 라이프와 함께 해주셔서 감사합니다.<br> 프리미엄 구독관에서 구독 서비스를 이용해주세요.',
                                imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
                            }).then((result) => {
                                if (result.isConfirmed) { // 알림창 확인 시
                                    window.location.href = baseUrl+ '/l-life/premium/main'
                                }
                                return;
                            });
                        }else if(response.result ==-1){
                            Swal.fire({
                                title: '이용중인 프리미엄 구독 서비스권이 있습니다.',
                                html: '구독중인 서비스는 프리미엄 구독관에서 이용 가능합니다.<br> 프리미엄 구독관 페이지를 이용해주세요.',
                                imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
                            }).then((result) => {
                                if (result.isConfirmed) { // 알림창 확인 시
                                    window.location.href = baseUrl+ '/l-life/premium/main'
                                }
                                return;
                            });
                        }

                    },
                    error: function(err) {
                        console.error("Error Update:", err);
                    }
                });

            }
    });
        }
}

function goToLogin(){
    Swal.fire({
        title: '로그인이 필요한 서비스입니다.',
        html: '로그인 후 이용 가능합니다.<br> 로그인 페이지로 이동합니다.',
        imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
    }).then((result) => {
        if (result.isConfirmed) { // 알림창 확인 시
            window.location.href = baseUrl+ '/l-life/member/login'
        }
        return;
    });
}