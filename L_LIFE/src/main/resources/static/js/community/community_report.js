let file;
let spaceAnalyzeUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/classifier'
let spaceDetectorUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/detector'
let styleAnalyzeUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/analyzer'
let colorAnalyzerUrl = 'https://apis.openapi.sk.com/urbanbase/v1/space/extractor'
let skOpenApiKey = 'qhJAJSCI5X6kfEWt7MWHQ7pUwS29uYVL4hDdxrih'

let spaceDict = {
    'livingroom': '거실',
    'bathroom': '욕실',
    'room': '방',
    'dressingroom': '드레스룸',
    'entrance': '현관',
    'balcony': '발코니',
    'homeoffice': '홈오피스 / 서재',
    'kitchen': '주방',
    'diningroom': '다이닝룸'
}

let furnitureDict = {

}

let styleDict ={
    'modern' : '모던',
    'nordic' :'북유럽',
    'romantic' :'로맨틱',
    'classic' :'클래식',
    'vintage' :'빈티지',
    'casual' :'캐주얼',
    'natural' :'내츄럴'
}
var colorDataSample = [
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
$(document).ready(function () {
    const $dropbox = $('.drag-file');
    const $input_filename = $('.message');
    const $previewImage = $('.upload-file');

    // 박스 안에 drag 하고 있을 때
    $dropbox.on('dragover', function (e) {
        e.preventDefault();
        $(this).css('background-color', 'rgba(13, 110, 253, 0.25)');
    });

    // 박스 밖으로 drag가 나갈 때
    $dropbox.on('dragleave', function (e) {
        $(this).css('background-color', 'white');
    });

    // 박스 안에 drop 했을 때
    $dropbox.on('drop', function (e) {
        e.preventDefault();
        $(this).css('background-color', 'white');

        file = e.originalEvent.dataTransfer.files[0];
        let filename = file.name;
        $input_filename.html(filename);

        // 이미지 파일인 경우에만 미리보기를 표시
        if (file.type.match(/^image\//)) {
            let reader = new FileReader();

            reader.onload = function (e) {
                $previewImage.attr('src', e.target.result);
                $previewImage.removeClass('ex-image');
                $previewImage.addClass('dr-image');
            };

            reader.readAsDataURL(file);

        } else {
            // 이미지 파일이 아닌 경우 에러 메시지 표시
            $input_filename.html('Only image files are allowed');
            $input_filename.addClass('error');
        }
    });
    createColorChart(colorDataSample);

    $('.rec-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow: $('.rec-next'),
        prevArrow: $('.rec-prev'),
    });
    $('.showroom-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow: $('.show-next'),
        prevArrow: $('.show-prev'),
    });
});

/**
 * 색상 차트 생성 함수
 */
function createColorChart(data) {


    const yValues = data.map(item => item.proportion * 100);

    const barColors = data.map(item => `rgb(${item.r},${item.g},${item.b})`);

    console.log(yValues);
    console.log(barColors);

    new Chart("myChart", {
        type: "doughnut",
        data: {
            datasets: [{
                backgroundColor: barColors,
                data: yValues
            }]
        },
        options: {
            title: {
                display: false,
                text: "Colors In Picture"
            }
        }
    });
}

/**
 * 어반베이스 요청 함수
 * @param requestUrl
 * @param imgUrl
 * @returns {*}
 */
function sendAnalyzeRequest(requestUrl, imgUrl) {
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
        success: function (response) {
        },
        error: function (error) {
        }
    });
}

/**
 * 스타일 기반 관련 상품 로드 함수
 * @param style
 * @returns {*}
 */
function requestRecProducts(style) {
    return $.ajax({
        url: 'http://localhost:8080/l-life/api/v1/community/rec-products?style=' + style,
        type: 'GET',
        success: function (response) {
        },
        error: function (error) {
        }
    });
}

function uploadImage() {
    const formData = new FormData();
    formData.append('file', file);
    var data = {
        "file": file
    }
    console.log("files", file)
    return $.ajax({
        url: 'http://localhost:8080/l-life/api/v1/community/upload-files',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
        },
        error: function (error) {
        }
    });
}


function requestReport() {
    console.log("testing")
    console.log("file", file)


    var imgUrlResponse = uploadImage()
    $.when(imgUrlResponse).done(function (imgUrlUploaded) {
        console.log("uploaded Image", imgUrlUploaded);
        var objectImage = $('.report-sample-img');
        objectImage.attr('src', imgUrlUploaded.result);

        // 공간 분석 API
        var spaceAnalyze = sendAnalyzeRequest(spaceAnalyzeUrl, imgUrlUploaded.result)

        // 사물 분석 API
        var furnitureAnalyze = sendAnalyzeRequest(spaceDetectorUrl, imgUrlUploaded.result)

        // 스타일 분석 API
        var styleAnalyze = sendAnalyzeRequest(styleAnalyzeUrl, imgUrlUploaded.result)

        // 색상 분석 API
        var colorAnalyze = sendAnalyzeRequest(colorAnalyzerUrl, imgUrlUploaded.result)

        $.when(spaceAnalyze, furnitureAnalyze, styleAnalyze, colorAnalyze).done(function (spaceResponse, spaceDetectorResponse, styleResponse, colorResponse) {
            console.log('모든 AJAX 요청이 완료되었습니다.');

            var data1 = spaceResponse[0].data.results[0].results;
            var data2 = spaceDetectorResponse[0].data.results[0].results;
            var data3 = styleResponse[0].data.results[0].results;
            var data4 = colorResponse[0].data.results[0].results;

            // let Report = createReport(data1, data2, data3, data4)
            console.log(data1, data2, data3, data4)

            let Report = createReport(data1, data2, data3, data4)

            var getRecProducts = requestRecProducts(data3[0].label);
            $.when(getRecProducts).done(function (products) {
                loadRecProducts(products.result);

            })
        });


    });


}

/**
 * 스타일 연관 추천 상품 Html 생성 함수
 * @param products
 */
function loadRecProducts(products) {
    var recSlideWrapper = $('.rec-slide-wrapper'); // 제품 카드를 추가할 요소 선택

    // rec-slide-wrapper 안의 내용 삭제
    console.log(recSlideWrapper)
    recSlideWrapper.slick("unslick")
    recSlideWrapper.empty();
    recSlideWrapper.slick('slickRemove',0);
    recSlideWrapper.slick('slickRemove',0);
    recSlideWrapper.slick('slickRemove',0);
    recSlideWrapper.slick('slickRemove',0);

    // products 배열 순회
    for (var i = 0; i < products.length; i++) {
        var product = products[i];

        // 새로운 제품 카드 생성
        var productCard = $('<div class="rec-product-card">');

        // 이미지 엘리먼트 생성
        var imgElement = $('<img>').attr('src', product.lfImgMain).attr('alt', '제품 이미지');

        // 브랜드 로고와 제품명 추가
        var brandLogo = $('<span class="brand-logo">').text(product.lfBrandName);
        var productName = $('<span class="rec-product-name">').text(product.lfName);
        var productInfo = $('<div class="rec-product-card-info">').append(brandLogo, productName);

        // 생성한 엘리먼트를 제품 카드에 추가
        productCard.append(imgElement, productInfo);

        // 제품 카드를 rec-slide-wrapper에 추가
        recSlideWrapper.append(productCard);

    }
    recSlideWrapper.slick('refresh');
}


/*
*  공간 & 색상 & 스타일 분석 레포트 html 생성 파트
* */
function createReport(data1, data2, data3, data4) {

    console.log(data1) // 공간 검출
    console.log(data2) // 사물 검출
    console.log(data3) // 스타일 검출
    console.log(data4) // 색상

    // 공간 레포트 변경
    console.log(spaceDict[data1[0].label])
    $('#spaceResult').text(spaceDict[data1[0].label])

    // 색상 레포트 변경
    createColorChart(data4)


    var styles = '';
    // 공간 스타일 변경
    for (var i = 0; i < data3.length; i++) {
        var item = data3[i];
        if (item && item.label) {
            styles += styleDict[item.label] + '(' + (Math.round(item.probability * 100 * 10) / 10) + '%) ,'
        }
    }
    styles = styles.slice(0, -1);

    $('#spaceStyle').text(styles)

    var objectImage = document.getElementById('objectImage');

    var objectOverlay = document.getElementById('objectOverlay');

    // 이미지에 좌표 띄우기
    for (var i = 0; i < data2.length; i++) {
        var objectData = data2[i]; // 현재 객체 가져오기

        // 이미지 크기 가져오기
        var imageWidth = objectImage.width;
        var imageHeight = objectImage.height;
        console.log("images", imageWidth, imageHeight)
        // 좌표를 이미지 크기에 맞게 변환
        var xmin = ((objectData.xmin + objectData.xmax) / 2) * imageWidth;
        var ymin = ((objectData.ymin + objectData.ymax) / 2) * imageHeight;

        console.log(xmin, ymin);
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

        var pickerPercent = document.createElement('p');
        pickerPercent.className = 'picker-percent';
        pickerPercent.textContent = (Math.round(objectData.score * 100 * 10) / 10) + '%'

        pickerBox.appendChild(pickerLabel);
        pickerBox.appendChild(pickerImage);
        pickerBox.appendChild(pickerPercent);

        objectOverlay.appendChild(pickerBox);
    }

    return 'testing Success'

}