
let spaceAnalyzeUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/classifier'
let spaceDetectorUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/detector'
let styleAnalyzeUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/analyzer'
let colorAnalyzerUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/extractor'
let skOpenApiKey = 'DACPsMWCZv64OCmI8M2BK4gV9QuunGsp2KDFzC88'
function sendAnalyzeRequest(requestUrl, imgUrl){
    var headers = {
        'accept': 'application/json',
        'appkey': skOpenApiKey
    };

    var data = {
        'image_path': imgUrl
    };

    return $.ajax({
        url: requestUrl,
        type: 'POST',
        headers: headers,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(response) {
        },
        error: function(error) {
        }
    });
}

function test(){
    let imgUrl = 'https://www.ikea.com/images/2-e4e271bd007a75af466351b6828af61c.jpg'
    // 공간 분석 API
    var spaceAnalyze = sendAnalyzeRequest(spaceAnalyzeUrl,imgUrl)


    // 사물 분석 API
    var furnitureAnalyze  = sendAnalyzeRequest(spaceDetectorUrl,imgUrl)

    // 스타일 분석 API
    var styleAnalyze = sendAnalyzeRequest(styleAnalyzeUrl,imgUrl)

    // 색상 분석 API
    var colorAnalyze = sendAnalyzeRequest(colorAnalyzerUrl,imgUrl)


    $.when(spaceAnalyze, furnitureAnalyze,styleAnalyze,colorAnalyze).done(function(spaceResponse, spaceDetectorResponse, styleResponse, colorResponse) {
        console.log('모든 AJAX 요청이 완료되었습니다.');

        var data1 = spaceResponse[0].data.results[0].results;
        var data2 = spaceDetectorResponse[0].data.results[0].results;
        var data3 = styleResponse[0].data.results[0].results;
        var data4 = colorResponse[0].data.results[0].results;

        let Report = createReport(data1, data2, data3, data4)
        console.log(Report)
    });


}

function createReport(data1, data2, data3,data4){

    console.log(data1)
    console.log(data2) // 사물 검출
    console.log(data3)
    console.log(data4)

    var objectImage = document.getElementById('objectImage');
    var objectOverlay = document.getElementById('objectOverlay');

    // 이미지에 좌표 띄우기
    for (var i = 0; i < data2.length; i++) {
        var objectData = data2[i]; // 현재 객체 가져오기

        // 이미지 크기 가져오기
        var imageWidth = objectImage.width;
        var imageHeight = objectImage.height;

        // 좌표를 이미지 크기에 맞게 변환
        var xmin = ((objectData.xmin + objectData.xmax)/2) * imageWidth;
        var ymin = ((objectData.ymin + objectData.ymax)/2) * imageHeight;

        // 오버레이 엘리먼트에 좌표와 라벨 추가
        var pickerBox = document.createElement('div');
        pickerBox.className = 'picker-box';
        pickerBox.style.left = xmin + 'px';
        pickerBox.style.top = ymin + 'px';

        var pickerLabel = document.createElement('span');
        pickerLabel.className = 'picker-label';
        pickerLabel.textContent = objectData.label;

        var pickerImage = document.createElement('img');
        pickerImage.alt = 'picker';
        pickerImage.src = 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-Community/c_img_picker.png'; // 이미지 경로를 설정해야 합니다.

        pickerBox.appendChild(pickerLabel);
        pickerBox.appendChild(pickerImage);
        objectOverlay.appendChild(pickerBox);
    }

    return 'testing Success'

}

function createColorChart(){

    var data=[
        {
            "r": 122,
            "g": 115,
            "b": 95,
            "proportion": 0.24352575231481483
        },
        {
            "r": 233,
            "g": 233,
            "b": 230,
            "proportion": 0.2077907986111111
        },
        {
            "r": 29,
            "g": 32,
            "b": 22,
            "proportion": 0.1951316550925926
        },
        {
            "r": 62,
            "g": 75,
            "b": 54,
            "proportion": 0.18214699074074073
        },
        {
            "r": 174,
            "g": 167,
            "b": 155,
            "proportion": 0.17140480324074073
        }
    ]

    const yValues = data.map(item => item.proportion * 100);

    const barColors = data.map(item => `rgb(${item.r},${item.g},${item.b})`);
    const xValues = ["Italy", "France", "Spain", "USA", "Argentina"];

    console.log(yValues);
    console.log(barColors);

    new Chart("myChart", {
        type: "doughnut",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues
            }]
        },
        options: {
            title: {
                display: true,
                text: "Colors In Picture"
            }
        }
    });
}