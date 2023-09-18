
function test(){

    // var headers = {
    //     'accept': 'application/json',
    //     'appkey': 'DACPsMWCZv64OCmI8M2BK4gV9QuunGsp2KDFzC88'
    // };
    //
    // var data = {
    //     'image_path': 'https://www.conceptvirtualdesign.com/wp-content/uploads/2021/07/1-Luxury-Dressing-Room-Design-1024x657.jpg'
    // };
    //
    // $.ajax({
    //     url: 'https://apis.openapi.sk.com/urbanbase/v1/space/analyzer',
    //     type: 'POST',
    //     headers: headers,
    //     data: JSON.stringify(data),
    //     contentType: 'application/json',
    //     success: function(response) {
    //         // 서버 응답을 처리하는 코드
    //         console.log(response);
    //     },
    //     error: function(error) {
    //         // 오류 처리 코드
    //         console.error(error);
    //     }
    // });

    var headers = {
        'Authorization': 'KakaoAK 4842da52426f6e804efae6ca27303d66',
        'Content-Type': 'application/json'
    };

    var data = JSON.stringify({
        'prompt': 'living room interior large natural',
        'negative_prompt': ''
    });

// AJAX POST 요청 보내기
    $.ajax({
        url: 'https://api.kakaobrain.com/v2/inference/karlo/t2i',
        type: 'POST',
        headers: headers,
        data: data,
        success: function(response) {
            // 서버 응답을 처리하는 코드
            console.log(response);
        },
        error: function(error) {
            // 오류 처리 코드
            console.error(error);
        }
    });
}