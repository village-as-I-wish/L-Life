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
    console.log(lfBrandIds, lfMoodIds)

    var data = {
        lfBrandIds: lfBrandIds,
        lfMoodIds: lfMoodIds,
        minCoin: minCoin,
        maxCoin: maxCoin
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
    console.log(queryParams)

    var url = "http://localhost:8080/l-life/api/v1/standard/search?" + queryParams;
    console.log(url)

    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            var data = response;
            var html = '';
            $('.sl-main-sub-products-txt .txt-pink').text(data.length);
            data.forEach(function (item) {
                html += '<div class="sl-main-sub-product">';
                html += '<a href="/standard/1/detail">';
                html += '<div class="sl-main-sub-product-img col">';
                html += '<img alt="상품메인사진" src="' + item.lfImgMain + '"/>';
                html += '</div>';
                html += '<div class="sl-main-sub-product-txt">';
                html += '<p>' + item.lfName + '</p>';
                html += '<hr style="margin: 3px 0px 10px 0px">';
                html += '<div class="d-flex sl-main-sub-product-price">';
                html += '<span>' + item.lfBrandName + '</span>';
                html += '<div class="coin-img-box">';
                for (var i = 0; i < item.lfStCoin; i++) {
                    html += '<img class="coin-img" src="https://img-resource.s3.ap-northeast-2.amazonaws.com/coin.png"/>';
                }
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</a>';
                html += '</div>';
            });
            $('.sl-main-sub-products').html(html);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("통신 실패.")
        }
    });
}
