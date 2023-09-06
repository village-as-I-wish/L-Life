function calendarInit() {
    var date = new Date();
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000);
    var kstGap = 9 * 60 * 60 * 1000;
    var today = new Date(utc + kstGap);

    var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());

    var currentYear = thisMonth.getFullYear();
    var currentMonth = thisMonth.getMonth();
    var currentDate = thisMonth.getDate();

    renderCalendar(thisMonth);

    function renderCalendar(thisMonth) {
        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();

        var startDay = new Date(currentYear, currentMonth, 1);
        var prevDate = new Date(startDay - 1).getDate();
        var prevDay = startDay.getDay();

        var endDay = new Date(currentYear, currentMonth + 1, 0);
        var nextDate = endDay.getDate();
        var nextDay = endDay.getDay();

        $('.year-month').text(currentYear + '.' + (currentMonth + 1));

        var calendar = $('.calendar');
        calendar.html('');

        calendar.append('<div class="weekdays">' +
            '<span class="day">일</span>' +
            '<span class="day">월</span>' +
            '<span class="day">화</span>' +
            '<span class="day">수</span>' +
            '<span class="day">목</span>' +
            '<span class="day">금</span>' +
            '<span class="day">토</span>' +
            '</div>');

        var days = $('<div class="days"></div>');

        for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
            var date = new Date(currentYear, currentMonth - 1, i);
            var dateString = formatDate(date);
            days.append('<button class="day prev" onclick="dayChecked(' + i + ')">' + i + '</button>');
        }

        for (var i = 1; i <= nextDate; i++) {
            var date = new Date(currentYear, currentMonth, i);
            var dateString = formatDate(date);
            days.append('<button class="day current" onclick="dayChecked(' + i + ')">' + i + '</button>');
        }

        for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
            var date = new Date(currentYear, currentMonth + 1, i);
            var dateString = formatDate(date);
            days.append('<button class="day next" onclick="dayChecked(' + i + ')">' + i + '</button>');
        }

        calendar.append(days);

        if (today.getMonth() === currentMonth) {
            var todayDate = today.getDate();
            var currentMonthDate = $('.days .current');
            currentMonthDate.eq(todayDate - 1).addClass('today');
        }
    }

    $('.go-prev').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth - 1, 1);
        renderCalendar(thisMonth);
    });

    $('.go-next').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth + 1, 1);
        renderCalendar(thisMonth);
    });
}
function formatDate(date) {
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0');
    var day = date.getDate().toString().padStart(2, '0');
    return year + '-' + month + '-' + day;
}
function dayChecked(i){

    $('.day.selected').removeClass('selected');

    var button = $(event.target);
    button.addClass('selected');

    var checkedMonth= $('.year-month').text();
    var checkedDay = i;
    var dateString = checkedMonth + '.' + checkedDay;
    console.log(dateString);
    $('.checkedDay').text(dateString);

}
function timeChecked(button){
    $('.time-btn').removeClass('time-selected');
    $(button).addClass('time-selected');

    var time = $(button).attr('data-value');
    console.log('data-value:', time);

    $('.checkedTime').text(time);

}
$(document).ready(function() {
        calendarInit();

});