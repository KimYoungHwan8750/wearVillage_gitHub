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