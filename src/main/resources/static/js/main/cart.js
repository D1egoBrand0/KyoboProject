const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
const selectAllBtn =  document.getElementById("cart-result-container").querySelector("i");
const bookCartContainer = document.getElementById("book-cart-container");
const bookInfoContainer = document.getElementById("book-info-container");
const removeAllBtn = document.getElementById("remove-all-btn");
const heartAllBtn = document.getElementById("heart-all-btn");


get_cart();


function get_cart(){
    // const request = new XMLHttpRequest();
    // request.open("GET","/user/cart");
    // request.send();
    // // request.addEventListener("load", () =>{}); // 아래와 동일 2개의 방법중 이게 하나의 방법이다.
    // request.onload = () => {
    //     // request.status
    //     const javaScriptOBJ = JSON.parse(request.response);
    // }


    fetch("/user/cart")
        .then(response => response.json())      //하나의 데이터를 받을때는 text()로 하면 된다.
        .then(value => {
            console.log(value);
            create_book_list_in_cart(value);
        })
        .catch(reason => {
            console.log(reason)}) //에러 이유
}

function create_book_list_in_cart(cartlist){
    bookCartContainer.innerHTML= "";
    for(cart of cartlist){
        bookCartContainer.insertAdjacentHTML("beforeend",
            // `<section class="book-info-container">
            //     <i class="fa-regular fa-circle-check" onclick=""></i>
            //     <input type="hidden" value=${cart.ISBN} class="book-info-isbn" />
            //     <img src="" alt="이미지">
            //     <div class="book-info">
            //         <div>${cart.title}</div>
            //         <span><b>${cart.price}</b>원</span>
            //     </div>
            //     <div class="book-info-price-container">
            //         <div><b>${cart.price * cart.bookCount}</b>원</div>
            //         <div class="book-info-count-container">
            //             <i class="fa-solid fa-minus"></i>
            //             <input type="number" value="${cart.bookCount}">
            //             <i class="fa-solid fa-plus"></i>
            //         </div>
            //     </div>
            // </section>`)         //여기는 예제
            `여기에 들어갈것은 장바구니 안에 들어간 1개의 틀이다.
            장바구니의 태그들을 여기에 복붙하자.
            아마 몇 줄이 될 것이다.
            찜목록도 이렇게 가능하다고 한다. 이거는 반드시 해봐야 할 듯
            `    )
    //     카운트가 +-될때마다 put요청으로 (수정하는데 사용) 해야한다고 한다.
    }


    function modify_cart(operator){
        const body=[];
        [...bookInfoContainer].forEach(container => {
            const classList = container.children.item(0).classList;
            if(classList.contains('fa-circle-check')){
                const isbnValue = container.querySelector()
            }
        })
    }

    function checkBox_click(checkbox) {
        if (checkbox.className.contains('fa-solid')) {
            checkbox.className = 'fa-regular fa-circle-check';
        } else {
            checkbox.className = 'fa-solid fa-circle-check';
        }
    }

    function get_clicked_boxes(){
        const boxes = [];
        [...bookInfoContainer].forEach(container => {
            const classList = constainer.children.item(0).classList;
        })

    }

}


removeAllBtn.onclick = checkBox_allclick;
function checkBox_all_click(checkbox) {
    if (checkbox.className.contains('fa-solid')) {
        checkbox.className = 'fa-regular fa-circle-check';
    } else {
        checkbox.className = 'fa-solid fa-circle-check';
    }
}



