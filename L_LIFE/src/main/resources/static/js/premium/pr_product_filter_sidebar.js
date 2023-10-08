function search() {

    let lfBrandId = [];
    let lfMoodId = [];
    let baseUrl = [];



    // output.innerHTML = slider.value;
    //
    // slider.oninput = function () {
    //     output.innerHTML = this.value;
    // }

    var lfBrandIds = $('input[name="lfBrandId"]:checked').map(function () {
        return this.value;
    }).get();
    var lfMoodIds = $('input[name="lfMoodId"]:checked').map(function () {
        return this.value;
    }).get();
    var minPrice = 1;
    var maxPrice = document.querySelector('#myRange').value;
    var lfId
    console.log(lfBrandIds, lfMoodIds)


    var data = {
        lfBrandIds: lfBrandIds,
        lfMoodIds: lfMoodIds,
        minPrice: minPrice,
        maxPrice: maxPrice,
        lfId: lfId,
    }

    queryParams = '';
    if (lfBrandIds.length !== 0) {
        queryParams += "lfBrandIds=" + encodeURIComponent(lfBrandIds.join(",")) + "&";
    }
    if (lfMoodIds.length !== 0) {
        queryParams += "lfMoodIds=" + encodeURIComponent(lfMoodIds.join(",")) + "&";
    }
    if (minPrice !== 0) {
        queryParams += "minPrice=" + encodeURIComponent(minPrice) + "&";
    }
    if (maxPrice !== 0) {
        queryParams += "maxPrice=" + encodeURIComponent(maxPrice) + "&";
    }
    queryParams = queryParams.slice(0, -1);
    console.log(queryParams)

    var url = baseUrl + "/l-life/api/v1/premium/search?" + queryParams;
    console.log(url)

    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            var data = response;
            $('.pl-main-sub-products-txt .txt-pink').text(data.length);
            var $subProducts = $('.pl-main-sub-products');
            $subProducts.empty();
            data.forEach(function (item) {
                var $subProduct = $('<div>').addClass('pl-main-sub-product');
                var $link = $('<a>').attr('href', '/l-life/premium/' + item.lfId + '/detail');
                var $imgCol = $('<div>').addClass('pl-main-sub-product-img col');
                var $img = $('<img>').attr('src', item.lfImgMain).attr('alt', '상품메인사진');
                $imgCol.append($img);
                var $txtDiv = $('<div>').addClass('pl-main-sub-product-txt');
                var $p = $('<p>').text(item.lfName).addClass('pl-main-sub-product-name');
                $txtDiv.append($p);
                var $hr = $('<hr>').css('margin', '3px 0px 10px 0px');
                $txtDiv.append($hr);
                var $priceDiv = $('<div>').addClass('d-flex pl-main-sub-product-price');
                var $span = $('<span>').text(item.lfBrandName);
                $priceDiv.append($span);
                var $coinBox = $('<div>').addClass('coin-img-box');
                $coinBox.text('월');
                var $span = $('<span>').text(item.lfPrPrice.toLocaleString());
                $coinBox.append($span);
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
