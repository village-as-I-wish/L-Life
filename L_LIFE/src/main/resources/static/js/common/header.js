
$(document).ready(function() {

    var selectedItem = localStorage.getItem('selectedItem');

    if (selectedItem) {
        // 저장된 선택 항목이 있으면 해당 항목을 강조합니다.
        $('#' + selectedItem).addClass('header-selected');
    }


    $('#menu li a').click(function (e) {


        e.preventDefault(); // 기본 링크 동작을 중지합니다.

        // 이전에 선택된 요소에서 'selected' 클래스를 제거합니다.
        $('#menu li a.header-selected').removeClass('header-selected');

        // 현재 선택된 요소에 'selected' 클래스를 추가합니다.
        $(this).addClass('header-selected');

        // 선택한 값을 표시합니다.
        // var selectedItemText = $(this).text();
        // $('#selectedItem').text(selectedItemText);
        localStorage.removeItem('selectedItem');
        // 선택한 항목을 로컬 스토리지에 저장합니다.
        localStorage.setItem('selectedItem', $(this).attr('id'));
        window.location.href = $(this).attr('href');

    });
    $('.header-logo a').click(function(e){
        localStorage.removeItem('selectedItem');
        $('#menu li a.header-selected').removeClass('header-selected');

    });

});
