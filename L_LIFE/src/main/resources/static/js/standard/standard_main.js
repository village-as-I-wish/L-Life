$(document).ready(function() {

    $('.sl-main-live-products').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 3000,
        nextArrow: $('.next'),
        prevArrow: $('.prev'),
    });
});