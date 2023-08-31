function alterPageUrl(pageType, page, lfSubType) {
    var subType = lfSubType === 0 ? 'standard' : 'premium';
    return `/l-life/${pageType}/main?page=${page}`;
}

var paginationNum = [[${paginationNum}]];
var currentPage = [[${page}]];
var lfSubType = [[${lfSubType}]];

document.getElementById('startBtn').onclick = function() {
    window.location.href = alterPageUrl('premium', 1, lfSubType);
};

document.getElementById('prev').onclick = function() {
    if (currentPage > 1) {
        window.location.href = alterPageUrl('premium', currentPage - 1, lfSubType);
    }
};

document.getElementById('next').onclick = function() {
    if (currentPage < paginationNum) {
        window.location.href = alterPageUrl('premium', currentPage + 1, lfSubType)
    }
};

document.getElementById('endBtn').onclick = function() {
    window.location.href = alterPageUrl('premium', paginationNum, lfSubType)
};
