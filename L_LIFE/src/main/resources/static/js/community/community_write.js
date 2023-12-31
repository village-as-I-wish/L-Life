// const imageFiles = [];

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
        image_format: 'jpeg'
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
        // const imageFile = await convertURLtoFile(response.images[0].image);
        $('#ai-image-'+index).val(response.images[0].image)
        // console.log("생성된 이미지 파일" + imageFile)
        // console.log(imageFile)
        // imageFiles[index] = imageFile;
        var image1 = $("#ai-image-"+index);
        if (image1.length > 0) {
            var imageId = image1.attr("id");
            var imageCls = image1.attr("class")
            image1.attr("src", response.images[0].image);
            if (imageId.indexOf("imageCls") !== -1) {
                image1.attr("class", imageCls.replace("before", "created"));
            } else if (imageCls.indexOf("created") === -1) {
                image1.attr("class", imageCls + " created");
            }
        }

        var container = image1.closest(".ai-box");
        container.find("p, span").remove();

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
// const convertURLtoFile = async (url) => {
//     // 임시방편
//     const response = await fetch("https://cors-anywhere.herokuapp.com/"+url);
//     const data = await response.blob();
//
//     // URL에서 확장자 추출
//     const extMatch = url.match(/\.(\w+)(\?|$)/);
//     const ext = extMatch ? extMatch[1] : 'jpg'; // 기본 확장자를 jpg로 설정하거나 원하는 확장자로 변경할 수 있습니다.
//
//     // URL에서 파일 이름 추출
//     const filenameMatch = url.match(/\/([^/]+)(\?|$)/);
//     const filename = filenameMatch ? filenameMatch[1] : 'image'; // 기본 파일 이름을 'image'로 설정하거나 원하는 파일 이름으로 변경할 수 있습니다.
//
//     const metadata = { type: `image/${ext}` };
//     return new File([data], `${filename}.${ext}`, metadata);
// };


$(document).ready(function(){

    $('#loading').hide();

    const memberId = $('#memberId').val();

    $('.write-slide-wrapper').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 500,
        arrows:true,
        draggable: false,
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
    if (pages[0] && pages[1]) {
                pages[0].classList.add('flipped');
                pages[1].classList.add('flipped');
    }



// 플립북 작성 버튼 클릭
    $('.submit-btn').click(function() {
        const memberId = $('#memberId').val();
        console.log("memberId", memberId)
        if(memberId === null || memberId ==='undefined' || memberId==''){
            Swal.fire({
                title: '로그인이 필요한 페이지 입니다.',
                text: '로그인 후 다시 이용해주세요.',
                imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
                confirmButtonText: '확인',
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = "/l-life/member/login";
                }
            });

        }else{
            $.ajax({
                data: {
                    mId: memberId
                },
                method: 'POST',
                url: '/l-life/api/v1/community/book/',
                success: function (res) {
                    console.log("Book Inserted ", res.result)
                    if (res.result !== -1) {
                        for (let i = 1; i <= 3; i++) {
                            (function (i) {
                                var formData = new FormData()
                                var inputFile = $('#imgUpload0' + i)
                                var aiFile = $('#ai-image-'+i).val()
                                const selectedFile = inputFile[0].files[0]
                                // const aiImageFile = aiFile

                                formData.append('bookId', res.result);
                                formData.append('bpTitle', $('#title-' + i).val());
                                formData.append('bpContent', $('#content-' + i).val());
                                formData.append('bpTag', $('#tag-' + i).val());
                                formData.append('bpPageNum', i);
                                formData.append('bpAiContent', $('#ai-text' + i).val())
                                formData.append('file', selectedFile)
                                formData.append('lfId', $('#product-' + i).val())
                                formData.append('aiImageFile',aiFile)
                                console.log(aiFile)

                                $.ajax({
                                    method: 'POST',
                                    url: '/l-life/api/v1/community/bookPage/',
                                    data: formData,
                                    contentType: false,
                                    processData: false,
                                    async: false,
                                    success: function (result) {
                                        console.log("페이지 생성 성공",result);
                                    },
                                    error: function (e) { // bookPage 에러
                                        Swal.fire({
                                            title: '플립북 페이지 생성이 오류가 발생하였습니다.',
                                            text: '잠시 후 다시 이용해주세요.',
                                            confirmButtonText: '확인',
                                        }).then((res) => {
                                            if (res.isConfirmed) {
                                                window.location.href = "/l-life/community/main";
                                            }
                                        });
                                    },
                                });
                            })(i);
                        }
                        Swal.fire({
                            imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
                            title: 'Livart Book 생성이 완료 되었습니다.',
                            text: '커뮤니티 메인페이지로 이동합니다.',
                            confirmButtonText: '확인',
                        }).then((result) => {
                            if (result.isConfirmed) {
                               window.location.href = "/l-life/community/main";
                            }
                        });
                    }else{// 북 생성 오류
                        Swal.fire({
                            title: 'Livart Book 생성에 오류가 발생하였습니다.',
                            text: '잠시후 다시 이용해주세요.',
                            imageUrl: 'https://img-resource.s3.ap-northeast-2.amazonaws.com/L-life-common/logo_l_life_b.png',
                            confirmButtonText: '확인',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = "/l-life/community/main";
                            }
                        });
                    }

                },
            })
        }

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