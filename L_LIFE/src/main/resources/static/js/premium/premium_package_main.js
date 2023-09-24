$(document).ready(function(){

    $('.lf-pr-slide-wrapper').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.pr-md-pick-list .next'),
        prevArrow:$('.pr-md-pick-list .prev'),
    });

    $('.lf-pr-promotion-slide-wrapper').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.pr-promotion-list .next'),
        prevArrow:$('.pr-promotion-list .prev'),
    });

    $('.lf-pr-popular-slide-wrapper').slick({
        slidesToShow: 6,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        nextArrow:$('.lf-pr-popular-slider .next'),
        prevArrow:$('.lf-pr-popular-slider .prev'),
    });


    // $('.lf-pr-slide-product-img').each(function(index) {
    //     const $overlay = $(this).find('.img-overlay');
    //     const $overlayText = $overlay.find('p');
    //
    //     $overlayText.text(`우드 & 화이트 감성 ${index + 1}`); // 원하는 텍스트 설정
    //
    //     // $(this).mouseenter(function() {
    //     //     $overlay.css('opacity', 1);
    //     // });
    //     //
    //     // $(this).mouseleave(function() {
    //     //     $overlay.css('opacity', 0);
    //     // });
    // });
})

