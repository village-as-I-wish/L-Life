Kakao.init('853107fcd1d77eb702ba6624f015df5b');
console.log(Kakao.isInitialized()); // sdk 초기화 판단 여부

function kakaoLogin() {
    Kakao.Auth.login({
        success: function (response) {
            Kakao.API.request({
                url: '/v2/user/me',
                success: function (response) {
                    console.log(response)
                    const data = {
                        email:response.kakao_account.email,
                        name:response.properties.nickname,
                        gender:response.kakao_account.gender,
                        profile:response.properties.profile_image
                    }
                    $.ajax({
                        url:"/l-life/member/login",
                        type:"POST",
                        data:data,
                        success : function (res) {
                        }
                    })
                },
                fail: function (error) {
                    console.log(error)
                },
            })
        },
        fail: function (error) {
            console.log(error)
        },
    })
}