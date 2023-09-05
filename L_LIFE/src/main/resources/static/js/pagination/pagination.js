var paginationNum = [[${paginationNum}]];
var currentPage = [[${page}]];

// 현재 URL 가져오기
var currentUrl = window.location.href;

$(document).ready(function() {
    $('#startBtn').on('click', function() {
        window.location.href = currentUrl.replace(/page=\d+/, 'page=1');
    });

    $('#prev').on('click', function() {
        if (currentPage > 1) {
            var prevPage = currentPage - 1;
            window.location.href = currentUrl.replace(/page=\d+/, 'page=' + prevPage);
        }
    });

    $('#next').on('click', function() {
        if (currentPage < paginationNum) {
            var nextPage = currentPage + 1;
            window.location.href = currentUrl.replace(/page=\d+/, 'page=' + nextPage);
        }
    });

    $('#endBtn').on('click', function() {
        window.location.href = currentUrl.replace(/page=\d+/, 'page=' + paginationNum);
    });
});
