const searchArea_width = document.querySelector('.header_searchArea');
const searchText_focus = document.querySelector('.header_searchText');
const searchArea_originalStyle = searchArea_width.getAttribute('style');
const searchText_originalStyle = searchText_focus.getAttribute('style');
const searchText_originalPlaceholder = searchText_focus.getAttribute('placeholder');

searchText_focus.addEventListener('focusin',()=>{
    searchArea_width.setAttribute('style','width:500px;');
    searchText_focus.setAttribute('style','width:470px; text-align:left; font-size:18px')
    searchText_focus.setAttribute('placeholder','');
})

searchText_focus.addEventListener('focusout',()=>{
    searchArea_width.setAttribute('style',searchArea_originalStyle);
    searchText_focus.setAttribute('style',searchText_originalStyle)
    searchText_focus.setAttribute('placeholder',searchText_originalPlaceholder);
})

    // DOM 요소를 가져옵니다.
    const rectangle = document.querySelector('.header_rectangle');
    const menu = document.querySelector('.header_menus');

    // .rectangle 요소에 클릭 이벤트 리스너를 추가합니다.
    rectangle.addEventListener('click', () => {
        menu.style.right = menu.style.right === '0' ? '-250px' : '0';
    });

    // 문서의 다른 영역을 클릭했을 때 메뉴를 닫는 이벤트 리스너를 추가합니다.
    document.addEventListener('click', (e) => {
        if (!menu.contains(e.target) && e.target !== rectangle) {
            menu.style.right = '-250px';
        }
    });