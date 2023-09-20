
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

$(document).ready(function(){

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

    // 카테고리 선택
    $('#category-1').change(function () {
        var selectedCategory = $(this).val();
        var $productSelect = $('#product-1'); // product select 요소 선택
        console.log(selectedCategory)

        $.ajax({
            type: 'GET',
            url: '/l-life/api/v1/community/product/'+selectedCategory,
            dataType: 'json',
            success: function (data) {
                console.log(data.result)
                $productSelect.empty();
                $.each(data.result, function (index, item) {
                    console.log(item)
                    console.log(item.lfId)
                    console.log(item.lfName)
                    $productSelect.append($('<option>', {
                        value: item.lfId,
                        text: item.lfName
                    }));
                });
            }
        })
    })


    // 플립북 작성 버튼 클릭
    $('.submit-btn').click(function() {
        const data = {
            mId:1,
            bookMaingImg:"",
            pages:[
                {},
                {},
                {}
            ],
            furnitures:[
                {},
                {},
                {}
            ],
        };

        $.ajax({
            url: '/l-life/api/v1/community/book/',
            method: 'POST',
            data: data,
            success : function(res) {
                console.log(res)
            }
        })
    })


})
