const slide_btn = document.querySelectorAll('.slide_btn');
let slide_right;
let right;
let right_Int;
let move_status=false;
let flag = true;
// let time_interval = setInterval(()=>{
//     if($('.item1_slide').css('right')=='3600px'){
//         console.log("ok");
//         $('.item1_slide').css('transition','');
//         $('.item1_slide').css('right','900px');
//     }
//     if(move_status==false){
//     next();
//     move_status=true;
//     }
// },6000)
var time_out = setTimeout(()=>{if(move_status==false&&flag==true){
    if($('.item1_slide').css('right')=='3600px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','900px');
    } else if($('.item1_slide').css('right')=='0px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','2700px');
    }
    next();
}},5000)

//forEach 시작

//forEach 끝
const slide_next_btn = document.querySelector('.btn_next');
const slide_previous_btn = document.querySelector('.btn_previous');

slide_next_btn.addEventListener('click',()=>{
    if($('.item1_slide').css('right')=='3600px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','900px');
    }
    if(move_status==false){
    next();
    move_status=true;
    }
    
});

slide_previous_btn.addEventListener('click',()=>{
    if($('.item1_slide').css('right')=='0px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','2700px');
    }
    if(move_status==false){
    previous();
    move_status=true;
    }
    
});

function next(){
    slide_right = ($('.item1_slide').css('right')).match(/[0-9]/g);
    right='';
    flag=false;
    slide_right.forEach(element => {
            right+=element;
        });
    right_Int=parseInt(right);
    if(move_status==false){
    $('.item1_slide').css('right',(right_Int+900)+'px');
    $('.item1_slide').css('transition','all 2s');
    move_status=true;
    setTimeout(()=>{move_status=false},2000);

    if(right==900){
        $('.circle1').css('backgroundColor','gray');
        $('.circle2').css('backgroundColor','red');
        $('.circle3').css('backgroundColor','gray');
    } else if(right==1800){
        $('.circle1').css('backgroundColor','gray');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','red');
    } else if(right==2700){
        $('.circle1').css('backgroundColor','red');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','gray');
    } else if(right==0){
        $('.circle1').css('backgroundColor','red');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','gray');
    }
    clearTimeout(time_out);
    setTimeout(()=>{if(move_status==false){
        if($('.item1_slide').css('right')=='3600px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','900px');
    } else if($('.item1_slide').css('right')=='0px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','2700px');
    }
    next();
}},5000);
    setTimeout(()=>{flag=true},5000)
}}

function previous(){
    slide_right = ($('.item1_slide').css('right')).match(/[0-9]/g);
    right='';
    flag=false;
    slide_right.forEach(element => {
            right+=element;
        });
    right_Int=parseInt(right);
    $('.item1_slide').css('right',(right_Int-900)+'px');
    $('.item1_slide').css('transition','all 2s');
    setTimeout(()=>{move_status=false},2000);
    if(right==900){
        $('.circle1').css('backgroundColor','gray');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','red');
    } else if(right==1800){
        $('.circle1').css('backgroundColor','red');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','gray');
    } else if(right==2700){
        $('.circle1').css('backgroundColor','gray');
        $('.circle2').css('backgroundColor','red');
        $('.circle3').css('backgroundColor','gray');
    } else if(right==0){
        $('.circle1').css('backgroundColor','gray');
        $('.circle2').css('backgroundColor','gray');
        $('.circle3').css('backgroundColor','red');
    }
    setTimeout(()=>{if(move_status==false){
        if($('.item1_slide').css('right')=='3600px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','900px');
    } else if($('.item1_slide').css('right')=='0px'){
        $('.item1_slide').css('transition','');
        $('.item1_slide').css('right','2700px');
    }
    next();
    }},5000);
    setTimeout(()=>{flag=true},4500)

}