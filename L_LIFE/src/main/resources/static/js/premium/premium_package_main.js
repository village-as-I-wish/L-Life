$(document).ready(function(){

    // $('ul.tabs li').click(function(){
    //     var tab_id = $(this).attr('data-tab');
    //
    //     $('ul.tabs li').removeClass('current');
    //     $('.tab-content').removeClass('current');
    //
    //     $(this).addClass('current');
    //     $("#"+tab_id).addClass('current');
    // }),

    $('.lf-pr-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        nextArrow:$('.next'),
        prevArrow:$('.prev'),
    });

    $('.lf-pr-promotion-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        nextArrow:$('.lf-pr-promotion-slider .next'),
        prevArrow:$('.lf-pr-promotion-slider .prev'),
    });

    $('.lf-pr-popular-slide-wrapper').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
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

