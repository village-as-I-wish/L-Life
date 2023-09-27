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

