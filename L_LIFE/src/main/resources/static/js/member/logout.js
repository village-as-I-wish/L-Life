function kakaoLogout() {
    Kakao.Auth.logout(function () {
        // 세션 초기화
        $.ajax({
            url: "/l-life/member/logout", // Spring Boot에서 세션을 처리할 엔드포인트
            type: "POST",
            success: function (res) {
                if (res.success) {
                    alert("카카오 로그아웃 및 서버 세션 로그아웃이 완료되었습니다.");
                    window.location.href = "/l-life/main"; // 로그아웃 후 리다이렉트
                } else {
                    alert("서버 세션 로그아웃 실패");
                }
            },
        });
    });
}