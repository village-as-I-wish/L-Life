$(document).ready(function () {

    const swiper = new Swiper('.swiper-container', {
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        draggable: true,
        resistance: true,
        resistanceRatio: 0.85,
    });

    const bookSwiper = new Swiper('.book-swiper', {
        autoplay: {
            delay: 0, //add
            disableOnInteraction: false,
        },
        delay:0,
        speed: 5000,
        loop: true,
        loopAdditionalSlides: 1,
        slidesPerView: 4,
        mousewheel: true,
    });

});
    function showBook(bookNum) {
    var findIndex = '#image-box-' + bookNum;
    var bookId = $(findIndex + '> input').val();
    console.log("북아이디" + bookId);

    var data = {
        bookId: bookId
    }

    $.ajax({
        url: '/l-life/api/v1/community/book/' + bookId,
        type: 'GET',
        data: data,
        success: function(response) {
            console.log(response);
            console.log(response.result.furnitures)

            var subtitleText = '지금, ' + response.result.pages[0].bpTitle + '의 페이지들 속에서 당신만의 스타일을 발견해보세요.';
            $(".book-main-subtitle").text(subtitleText);

            // 1번째 페이지 업데이트
            $(".page1-img").attr("src", response.result.pages[0].bpImg);
            $(".page-title").text(response.result.pages[0].bpTitle);
            $(".page-ai-img").attr("src", response.result.pages[0].bpAiImg);
            $(".page-ai-content").text(response.result.pages[0].bpAiContent);
            $(".page-content").text(response.result.pages[0].bpContent);
            // 2번째 페이지 업데이트
            $(".page2-title").text(response.result.pages[1].bpTitle);
            $(".page2-content").text(response.result.pages[1].bpContent);
            $(".page2-ai-img").attr("src", response.result.pages[1].bpAiImg);
            $(".page2-img").attr("src", response.result.pages[1].bpImg);
            $(".page2-ai-content").text(response.result.pages[1].bpAiContent);
            // 3번째 페이지 업데이트
            $(".page3-img").attr("src", response.result.pages[2].bpImg);
            $(".page3-title").text(response.result.pages[2].bpTitle);
            $(".page3-content").text(response.result.pages[2].bpContent);
            $(".page3-ai-content").text(response.result.pages[2].bpAiContent);
            $(".page3-ai-img").attr("src", response.result.pages[2].bpAiImg);
            // 4번째 페이지 업데이트
            $(".page4-1 .top-row .product-image-left-1").eq(0).attr("src", response.result.pages[0].bpImg);
            $(".page4-1 .top-row .product-image-left-1").eq(1).attr("src", response.result.pages[1].bpImg);
            $(".page4-1 .bottom-row .product-image-left-2").attr("src", response.result.pages[2].bpImg);
            for (let i = 0; i < 3; i++) {
                $(".product-image-" + i).attr("src", response.result.furnitures[i].lfImgMain);
                $(".product-brand-" + i).text(response.result.furnitures[i].lfBrandName);
                $(".product-name-" + i).text(response.result.furnitures[i].lfName);
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
}


var pages = document.getElementsByClassName('page');
for (var i = 0; i < pages.length; i++) {
    var page = pages[i];
    if (i % 2 === 0) {
        page.style.zIndex = (pages.length - i);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    for (var i = 0; i < pages.length; i++) {
        pages[i].pageNum = i + 1;
        pages[i].onclick = function() {
            if (this.pageNum % 2 === 0) {
                this.classList.remove('flipped');
                this.previousElementSibling.classList.remove('flipped');
            } else {
                this.classList.add('flipped');
                this.nextElementSibling.classList.add('flipped');
            }
        }
    }

    // 1페이지와 2페이지를 flipped 상태로 만들어 2,3페이지가 바로 보이게 함
    if (pages[0] && pages[1]) {
        pages[0].classList.add('flipped');
        pages[1].classList.add('flipped');
    }
});


