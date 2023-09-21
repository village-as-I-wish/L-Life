
function displayImage(input,page) {
    const $previewImage = $('.upload-file-'+page);
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            $previewImage.attr('src', e.target.result);
            $previewImage.removeClass('ex-image');
            $previewImage.addClass('dr-image');
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function chatGPT(index) {
    // 태그 기반 이미지 생성
    const tags = $('.tags').text()
    $('#loading').show();

    const prompt = 'Create a photo of the living room with an atmosphere related to ' + tags

    const data = {
        prompt: prompt,
    }

    // GPT
    // $.ajax({
    //     url: "https://api.openai.com/v1/images/generations",
    //     method: 'POST',
    //     headers: {
    //         Authorization: "Bearer " + gptToken,
    //         'Content-Type': 'application/json',
    //     },
    //     data: JSON.stringify(data),
    // }).then(async function (response) {
    //     console.log(response.data[0].url)
    //     // 이미지 URL을 파일로 변환
    //     const imageFile = await convertURLtoFile(response.data[0].url);
    //     $('#ai-image-file').val(imageFile)
    //     console.log("생성된 이미지 파일" + imageFile)
    //     console.log(imageFile)
    //     $('#ai-image').attr("src", response.data[0].url)
    //     $('#loading').hide();
    // });

    // Karlo
    $.ajax({
        url: "https://api.kakaobrain.com/v2/inference/karlo/t2i",
        method: 'POST',
        headers: {
            Authorization: `KakaoAK ` + kakaoRestApiKey,
            'Content-Type': 'application/json',
        },
        data: JSON.stringify(data),
    }).then(async function (response) {
        console.log(response.images[0].image)
        // 이미지 URL을 파일로 변환
        const imageFile = await convertURLtoFile(response.images[0].image);
        $('#ai-image-file'+index).val(imageFile)
        console.log("생성된 이미지 파일" + imageFile)
        console.log(imageFile)
        $('#ai-image-'+index).attr("src", response.images[0].image)

        $('#loading').hide();
    });

    // 줄글 기반 요약문 생성
    const contents = $('#content-'+index).val()
    console.log(contents)
    const messages = 'Make one sentence of promotional text in Korean using the following sentences. ' + contents + 'within 20 characters.'

    const data2 = {
        model: 'gpt-3.5-turbo-instruct',
        prompt: messages,
        max_tokens: 100,
        temperature: 0.7,
    }

    $.ajax({
        url: "https://api.openai.com/v1/completions",
        method: 'POST',
        headers: {
            Authorization: "Bearer " + gptToken,
            'Content-Type': 'application/json',
        },
        data: JSON.stringify(data2),
    }).then(function (response) {
        console.log(response.choices[0].text);
        $('#ai-text'+index).val(response.choices[0].text);
    });
}

// GPT에게 받은 이미지 URL -> 파일
const convertURLtoFile = async (url) => {
    // 임시방편
    const response = await fetch("https://cors-anywhere.herokuapp.com/"+url);
    const data = await response.blob();

    // URL에서 확장자 추출
    const extMatch = url.match(/\.(\w+)(\?|$)/);
    const ext = extMatch ? extMatch[1] : 'jpg'; // 기본 확장자를 jpg로 설정하거나 원하는 확장자로 변경할 수 있습니다.

    // URL에서 파일 이름 추출
    const filenameMatch = url.match(/\/([^/]+)(\?|$)/);
    const filename = filenameMatch ? filenameMatch[1] : 'image'; // 기본 파일 이름을 'image'로 설정하거나 원하는 파일 이름으로 변경할 수 있습니다.

    const metadata = { type: `image/${ext}` };
    return new File([data], `${filename}.${ext}`, metadata);
};


$(document).ready(function(){

    $('#loading').hide();

    const memberId = $('#memberId').val();

    $('.write-slide-wrapper').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.show-next'),
        prevArrow:$('.show-prev'),
    });
    $('.show-prev').attr('disabled',true)


    // 슬라이더 초기화 후 이벤트 리스너 등록
    $('.write-slide-wrapper').on('afterChange', function (event, slick, currentSlide) {
        console.log(currentSlide)
        $('.current-page-num').text((currentSlide+1) + '페이지 / 3페이지')
        if (currentSlide === 0) {
            $('.show-prev').attr('disabled',true)
            $('.show-next').removeAttr('disabled');
        } else if (currentSlide === slick.slideCount - 1) {
            $('.show-prev').removeAttr('disabled');
            $('.show-next').attr('disabled',true)
        } else {
            $('.show-prev').removeAttr('disabled');
            $('.show-next').removeAttr('disabled');
        }
    });


    var pages = document.getElementsByClassName('page');
    for(var i = 0; i < pages.length; i++)
    {
        var page = pages[i];
        if (i % 2 === 0)
        {
            page.style.zIndex = (pages.length - i);
        }
    }

    for(var i = 0; i < pages.length; i++)
    {
        //Or var page = pages[i];
        pages[i].pageNum = i + 1;
        pages[i].onclick=function()
        {
            if (this.pageNum % 2 === 0)
            {
                this.classList.remove('flipped');
                this.previousElementSibling.classList.remove('flipped');
            }
            else
            {
                this.classList.add('flipped');
                this.nextElementSibling.classList.add('flipped');
            }
        }
    }



// 플립북 작성 버튼 클릭
    $('.submit-btn').click(function() {

        const formData = new FormData();

        var pages = []
        var furnitures = []
        // var files = []
        // var aifiles = []
        for (let i = 1; i <= 3; i++){
            var inputFile = $('#imgUpload0'+i)
            const selectedFile = inputFile[0].files[0]

            let page = {
                bpTitle: $('#title-'+i).val(),
                bpContent: $('#content-'+i).val(),
                bpTag : $('#tag-'+i).val(),
                bpPageNum: i,
                bpAiContent: $('#ai-text'+i).val()
                // aiFile: $('#ai-image-file'+i).val(),
                // file: selectedFile
            }

            let furniture = {
                lfId: $('#product-'+i).val()
            }
            formData.append('files', selectedFile);
            // files.push(selectedFile)
            // formData.append("aifiles",$('#ai-image-file'+i).val())
            // aifiles.push($('#ai-image-file'+i).val())
            pages.push(page)
            furnitures.push(furniture)
        }

        console.log(pages)
        console.log(furnitures)
        // console.log(files)
        // console.log(aifiles)


        formData.append("mId",memberId);
        formData.append("pages",pages)
        // formData.append("files",files);
        // formData.append("aifiles",aifiles);
        // formData.append("pages",pages);
        // formData.append("furnitures",furnitures);

        console.log(formData)
        // const data = {
        //     mid: memberId,
        //     pages:pages,
        //     furnitures:furnitures
        // };

        // console.log(data)
        $.ajax({
            url: '/l-life/api/v1/community/bookTest/',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success : function(res) {
                console.log(res)
            }
        })
    })



    // 임시 카테고리 함수
    // 카테고리 선택 1페이지
    $('#category-1').change(function () {
        let selectedCategory = $(this).val();
        var $productSelect1 = $('#product-1'); // product select 요소 선택
        console.log(selectedCategory)

        $.ajax({
            type: 'GET',
            url: '/l-life/api/v1/community/product/'+selectedCategory,
            dataType: 'json',
            success: function (data) {
                console.log(data.result)
                $productSelect1.empty();
                $.each(data.result, function (index, item) {
                    console.log(item)
                    console.log(item.lfId)
                    console.log(item.lfName)
                    $productSelect1.append($('<option>', {
                        value: item.lfId,
                        text: item.lfName
                    }));
                });
            }
        })
    })

    // 카테고리 선택 2페이지
    $('#category-2').change(function () {
        let selectedCategory = $(this).val();
        var $productSelect2 = $('#product-2'); // product select 요소 선택
        console.log(selectedCategory)

        $.ajax({
            type: 'GET',
            url: '/l-life/api/v1/community/product/'+selectedCategory,
            dataType: 'json',
            success: function (data) {
                console.log(data.result)
                $productSelect2.empty();
                $.each(data.result, function (index, item) {
                    console.log(item)
                    console.log(item.lfId)
                    console.log(item.lfName)
                    $productSelect2.append($('<option>', {
                        value: item.lfId,
                        text: item.lfName
                    }));
                });
            }
        })
    })

    // 카테고리 선택 3페이지
    $('#category-3').change(function () {
        let selectedCategory = $(this).val();
        var $productSelect3 = $('#product-3'); // product select 요소 선택
        console.log(selectedCategory)

        $.ajax({
            type: 'GET',
            url: '/l-life/api/v1/community/product/'+selectedCategory,
            dataType: 'json',
            success: function (data) {
                console.log(data.result)
                $productSelect3.empty();
                $.each(data.result, function (index, item) {
                    console.log(item)
                    console.log(item.lfId)
                    console.log(item.lfName)
                    $productSelect3.append($('<option>', {
                        value: item.lfId,
                        text: item.lfName
                    }));
                });
            }
        })
    })


})
