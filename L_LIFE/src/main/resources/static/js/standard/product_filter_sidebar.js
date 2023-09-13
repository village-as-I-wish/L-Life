function search() {

    let lfBrandId = [];
    let lfMoodId = [];

    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    }

    var lfBrandIds = $('input[name="lfBrandId"]:checked').map(function () {
        return this.value;
    }).get();
    var lfMoodIds = $('input[name="lfMoodId"]:checked').map(function () {
        return this.value;
    }).get();
    var minCoin = 1;
    var maxCoin = document.querySelector('#myRange').value;
    var lfId


    var data = {
        lfBrandIds: lfBrandIds,
        lfMoodIds: lfMoodIds,
        minCoin: minCoin,
        maxCoin: maxCoin,
        lfId: lfId,
    }

    queryParams = '';
    if (lfBrandIds.length !== 0) {
        queryParams += "lfBrandIds=" + encodeURIComponent(lfBrandIds.join(",")) + "&";
    }
    if (lfMoodIds.length !== 0) {
        queryParams += "lfMoodIds=" + encodeURIComponent(lfMoodIds.join(",")) + "&";
    }
    if (minCoin !== 0) {
        queryParams += "minCoin=" + encodeURIComponent(minCoin) + "&";
    }
    if (maxCoin !== 0) {
        queryParams += "maxCoin=" + encodeURIComponent(maxCoin) + "&";
    }
    queryParams = queryParams.slice(0, -1);

    var url = "http://livart-life.com:8080/l-life/api/v1/standard/search?" + queryParams;

    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            var data = response;
            $('.sl-main-sub-products-txt .txt-pink').text(data.length);
            var $subProducts = $('.sl-main-sub-products');
            $subProducts.empty();
            data.forEach(function (item) {
                var $subProduct = $('<div>').addClass('sl-main-sub-product');
                var $link = $('<a>').attr('href', '/l-life/standard/' + item.lfId + '/detail');
                var $imgCol = $('<div>').addClass('sl-main-sub-product-img col');
                var $img = $('<img>').attr('src', item.lfImgMain).attr('alt', '상품메인사진');
                $imgCol.append($img);
                var $txtDiv = $('<div>').addClass('sl-main-sub-product-txt');
                var $p = $('<p>').text(item.lfName);
                $txtDiv.append($p);
                var $hr = $('<hr>').css('margin', '3px 0px 10px 0px');
                $txtDiv.append($hr);
                var $priceDiv = $('<div>').addClass('d-flex sl-main-sub-product-price');
                var $span = $('<span>').text(item.lfBrandName);
                $priceDiv.append($span);
                var $coinBox = $('<div>').addClass('coin-img-box');
                for (var i = 0; i < item.lfStCoin; i++) {
                    var $coinImg = $('<img>').addClass('coin-img').attr('src', 'https://img-resource.s3.ap-northeast-2.amazonaws.com/coin.png');
                    $coinBox.append($coinImg);
                }
                $priceDiv.append($coinBox);
                $txtDiv.append($priceDiv);
                $link.append($imgCol).append($txtDiv);
                $subProduct.append($link);
                $subProducts.append($subProduct);
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("통신 실패.")
        }
    });

}
