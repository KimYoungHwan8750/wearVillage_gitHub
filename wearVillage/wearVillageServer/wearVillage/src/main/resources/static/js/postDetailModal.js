function getModal(dom,size){
    let top = size?.top;
    let left = size?.left;
    let bottom = size?.bottom;
    let right = size?.right;
    let form = new FormData();
    form.append("top",top);
    form.append("left",left);
    form.append("bottom",bottom);
    form.append("right",right);

    let options = {
        method : "post",
        body : form
    }
fetch(`/getModal`,options)
.then(res=>res.text())
.then(res=>{dom.innerHTML=res;
    let $plusXBtn = document.querySelector('.plusXBtn');
    let $nav_modal = document.querySelector('.nav_modal');
    let $vertical_line = document.querySelector('.vertical_line');
    let $horizontal_line = document.querySelector('.horizontal_line');
    let isHold;
    let isIng;

    let myEventListener = (doc,ele,evt)=>{
        ele.forEach(element => {
            doc.addEventListener(element,evt)
        });
    }

    myEventListener($plusXBtn,["click"],holdModal)
    myEventListener($plusXBtn,["mouseenter"],openModal)
    myEventListener($plusXBtn,["mouseleave"],leaveCloseModal)
    myEventListener($plusXBtn,["click"],clickCloseModal)
    document.onclick=function(e){
        if(e.target.parentElement===$nav_modal){
        } else if(e.target===$nav_modal){
        } else {
            clickCloseModal();
        }
    }
    function holdModal(){
        openModal();
        isHold=true;
    }
    function openModal(){
        $nav_modal.setAttribute("style",
            `opacity:1;
        transition:opacity 0.5s;
        top:-${$nav_modal.offsetHeight}px`);

        $horizontal_line.style=
            `transform:rotate(45deg);
        transition:all 0.5s;`;
        $vertical_line.style=
            `transform:rotate(45deg);
        transition:all 0.5s;`;
        $plusXBtn.style = `
        background-color: rgb(59, 59, 59);
        top:${top};
        left:${left};
        bottom:${bottom};
        right:${right};`


    }
    function leaveCloseModal(){
            $nav_modal.style.opacity = "0";
            $horizontal_line.style.transform = "none";
            $vertical_line.style.transform = "none";
            $plusXBtn.style = `
        transition:background-color 0.5s;
        background-color:rgb(255, 113, 113);
        top:${top};
        left:${left};
        bottom:${bottom};
        right:${right};
       
        `;
    }

    function clickCloseModal(){
            isHold = false;
            $nav_modal.style.opacity = "0";
            $horizontal_line.style.transform = "none";
            $vertical_line.style.transform = "none";
            $plusXBtn.style = `
        transition:background-color 0.5s;
        background-color:rgb(255, 113, 113);
        top:${top};
        left:${left};
        bottom:${bottom};
        right:${right};
       
        `;
        }

});


}
export {getModal}