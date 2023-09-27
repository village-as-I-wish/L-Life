$(document).ready(function() {

    $('.premium-payment').click(function () {
        // 선택된 상품들을 담을 배열
        var selectedProducts = [];
        // 각 상품 요소를 순회하면서 선택 여부 확인
        $('.premium-checkbox:checked').each(function () {
            var id = $(this).attr('id').split('_')[1];
            var lfId = $('#lfId_' + id).val();
            var lfOptId = $('#lfOptId_' + id).val();
            var lfName = $('#lfName_' + id).text();
            var lfImgMain = $('#lfImgMain_' + id).val();
            var lfPrPrice = $('#lfPrPrice_' + id).val();
            var quantity = $('#quantity_' + id).val();
            var lfBrandName = $('#lfBrandName_' + id).val();
            var lfPrMinPeriod = $('#lfPrMinPeriod_' + id).val();
            var lfPrSubId = $('#lfPrSubId_' + id).val();
            var memberId = $('#memberId').val();
            var totalPrice = $('#lftotalPrice_' + id).val();
            selectedProducts.push(
                {
                    subscriptionId: lfPrSubId,
                    lfId: lfId,
                    lfOptId: lfOptId,
                    lfName: lfName,
                    lfImgMain: lfImgMain,
                    lfPrPrice: lfPrPrice,
                    quantity: quantity,
                    lfBrandName: lfBrandName,
                    lfPrMinPeriod: lfPrMinPeriod,
                    memberId: memberId,
                    totalPrice: totalPrice
                });
            console.log(selectedProducts)
        });

        console.log(selectedProducts.length)

        if (selectedProducts.length == 0) {
            Swal.fire({
                title: '선택된 상품이 없습니다.',
                text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
            })
            return
        }
        $.ajax({
            type: 'POST',
            url: '/l-life/subscription/premium/payment_detail',
            data: JSON.stringify(selectedProducts),
            dataType: "json",
            contentType: 'application/json',
            success: function (response) {
                console.log('Data sent successfully:', response);

                var redirectUrl = response.redirectUrl;
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
            },
            error: function (error) {
                // 오류 처리
                console.error('Error sending data:', error);
            }
        });
    });

    $('.no-subscription-btn').click(function () {
        console.log("CLICK")
        Swal.fire({
            title: '보유한 구독권이 없습니다.',
            text: '스탠다드 구독권 구매 후 이용해주세요.',
            imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = baseUrl + '/l-life/subscription/standard/standard_selection'
            }
        })
    })

    $('.standard-payment').click(function () {

        // 선택된 상품들을 담을 배열
        var selectedStProducts = [];
        // 각 상품 요소를 순회하면서 선택 여부 확인
        $('.standard-checkbox:checked').each(function () {
            console.log("this" + this)
            var sid = $(this).attr('id').split('_')[1];
            var lfId = $('#slfId_' + sid).val();
            var lfOptId = $('#slfOptId_' + sid).val();
            var lfName = $('#slfName_' + sid).val();
            var lfImgMain = $('#slfImgMain_' + sid).val();
            var lfStCoin = $('#slfstCoin_' + sid).val();
            var lfBrandName = $('#slfBrandName_' + sid).val();
            var lfStSubId = $('#slfStSubId_' + sid).val();
            var memberId = $('#memberId').val();
            var totalCoin = $('#stotalCoin_' + sid).val();

            selectedStProducts.push(
                {
                    subscriptionId: lfStSubId,
                    lfId: lfId,
                    lfOptId: lfOptId,
                    lfName: lfName,
                    lfImgMain: lfImgMain,
                    lfStCoin: lfStCoin,
                    lfBrandName: lfBrandName,
                    memberId: memberId,
                    totalCoin: totalCoin
                });
            console.log(selectedStProducts)
        });
        if (selectedStProducts.length == 0) {
            Swal.fire({
                title: '선택된 상품이 없습니다.',
                text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
            })
            return
        }
        $.ajax({
            type: 'POST',
            url: '/l-life/subscription/standard/payment_detail',
            data: JSON.stringify(selectedStProducts),
            dataType: "json",
            contentType: 'application/json',
            success: function (response) {
                console.log('Data sent successfully:', response);

                var redirectUrl = response.redirectUrl;
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
            },
            error: function (error) {
                // 오류 처리
                console.error('Error sending data:', error);
            }
        });
    });
});

$(document).ready(function() {
    const standardCheckboxes = $('.standard-checkbox');
    const premiumCheckboxes = $('.premium-checkbox');
    const coinsRemainingElement = $('.coins-remaining .coins');
    // const coinsRemainingTextElement = $('.coins-remaining > span');
    const totalPremiumPriceElement = $('.total-premium-price');
    const subscriptionPrices = $('.subscription-price');
    subscriptionPrices.each(function() {
        const currentPrice = parseInt($(this).text().replace(/[^0-9]/g, ''));
        $(this).text(currentPrice.toLocaleString() + '원');
    });
    const productPrices = $('.product-price');
    productPrices.each(function() {
        const currentPrice = parseInt($(this).text().replace(/[^0-9]/g, ''));
        $(this).text('월 ' + currentPrice.toLocaleString() + '원');
    });
    const productMinimumPeriod = document.querySelectorAll('.product-minium-peroid');

    productMinimumPeriod.forEach(periodElement => {
        const currentPeriod = parseInt(periodElement.textContent.replace(/[^0-9]/g, ''));
        periodElement.textContent = '구독기간 : ' + currentPeriod.toLocaleString() + '개월';

    });

    const itemPriceElements = $('.item-price');
    const initialProductPrices = [];
    const coinAmount = $('.coinAmount');

    itemPriceElements.each(function(index) {
        const decrementButton = $(this).find('.decrement');
        const incrementButton = $(this).find('.increment');
        const quantityInput = $(this).find('.quantity');
        const subscriptionPriceElement = $(this).find('.subscription-price');
        initialProductPrices[index] = parseInt(subscriptionPriceElement.text().replace(/[^0-9]/g, ''));

        decrementButton.click(function() {
            const quantity = parseInt(quantityInput.val());
            if (quantity > 0) {
                quantityInput.val(quantity - 1);
                updateTotalPrice();
            }
        });

        incrementButton.click(function() {
            const quantity = parseInt(quantityInput.val());
            const stockAmount = parseInt($('.stockAmount').val());
            if (quantity < stockAmount) {
                quantityInput.val(quantity + 1);
                updateTotalPrice();
            } else {
                Swal.fire({
                    title: '재고 수량을 초과하였습니다.',
                    text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                    imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                });
            }
        });

        quantityInput.change(function() {
            updateTotalPrice();
        });
    });

    standardCheckboxes.each(function() {
        $(this).change(function() {
            const listItem = $(this).closest('.list-item');
            let productCoins;
            if (listItem.find('.discount-coins').length > 0) {
                productCoins = listItem.find('.discount-coins img').length;
            } else {
                productCoins = parseInt(listItem.find('.product-coins').text());
            }
            const currentCoins = parseInt($('.coinAmount').text());
            if ($(this).prop('checked')) {
                if ((currentCoins - productCoins) < 0) {
                    $(this).prop('checked', false);
                    Swal.fire({
                        title: '보유 H코인이 부족합니다.',
                        text: '리바트 라이프와 함께 해주셔서 감사합니다.',
                        imageUrl: baseUrl + '/l-life/img/header/logo_l_life_b.png',
                    });
                    return;
                }
                for (let i = 0; i < productCoins; i++) {
                    const img = $('<img>').attr('src', 'https://img-resource.s3.ap-northeast-2.amazonaws.com/coin.png');
                    coinsRemainingElement.append(img);
                }
            } else {
                for (let i = 0; i < productCoins; i++) {
                    coinsRemainingElement.children().last().remove();
                }
            }
            // coinsRemainingTextElement.text(`${coinsRemainingElement.find('img').length}/${currentCoin}`);
            const minusCoin = coinsRemainingElement.find('img').length;
            coinAmount.text(`${currentCoins - minusCoin}`);
        });
    });

    premiumCheckboxes.each(function() {
        $(this).change(function() {
            updateTotalPrice();
        });
    });

    function updateTotalPrice() {
        let currentTotalPrice = 0;
        itemPriceElements.each(function(index) {
            const quantityInput = $(this).find('.quantity');
            const subscriptionPriceElement = $(this).find('.subscription-price');
            const checkbox = $(this).closest('.list-item').find('.premium-checkbox');
            const quantity = parseInt(quantityInput.val());
            subscriptionPriceElement.text((initialProductPrices[index] * quantity).toLocaleString() + '원');
            if (checkbox.prop('checked')) {
                currentTotalPrice += initialProductPrices[index] * quantity;
            }
        });
        totalPremiumPriceElement.text(`구독상품금액: ${currentTotalPrice.toLocaleString()}원`);
    }
});
