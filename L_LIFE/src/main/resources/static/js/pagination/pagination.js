var paginationNum = [[${paginationNum}]];
var currentPage = [[${page}]];

document.getElementById('startBtn').onclick = function() {
    window.location.href = '/l-life/premium/main?page=1';
};

document.getElementById('prev').onclick = function() {
    if (currentPage > 1) {
        window.location.href = '/l-life/premium/main?page=' + (currentPage - 1);
    }
};

document.getElementById('next').onclick = function() {
    if (currentPage < paginationNum) {
        window.location.href = '/l-life/premium/main?page=' + (currentPage + 1);
    }
};

document.getElementById('endBtn').onclick = function() {
    window.location.href = '/l-life/premium/main?page=' + paginationNum;
};
