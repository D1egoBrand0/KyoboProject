// 전부 만들어야 한다.
const bookResultContainer = document.getElementById("북 리스트");
const cartALLBtn =document.getElementsByName("장바구니버튼");

// cartALLBtn.addEventListener("click",() => {
//     const bookInfoContainer = document.getElementById("책isbn체크박스");
//
//     for(container of bookInfoContainer){
//         const bookInfoCheck = container.getElementsByClassName("체크").item(0);
//         // 체크박스가 선택되었다면
//         if (bookInfoCheck){
//             // const
//         }
//
//         // 내가 넣ㅇ느 배열에 아무것도 들어있지 않다면
//         if(bookISBNArray.length === 0){
//             alert("상품을 하나라도 넣어줘");
//         } else {
//             insert_cart();
//             console.log(" --- *** --- ")
//         }
//
//
//         console.log(bookISBNArray)
//     }
// })
// 여기까지
get_books();

function get_books(){
    fetch("/main/books")
        .then(response => response.json())
        .then(value => {
            console.log(value);
            create_book_list(value);
        })
        .catch(reason => {
            console.log(reason);
        })
}

function create_book_list(bookList){
    for (book of bookList) {
        bookResultContainer.insertAdjacentHTML(
        //     여기는 html에 적은 태그의 내용을 넣는다. 반복문에 활용할 형식들이다.
            "beforeend",
        )
    }
}


// 장바구니에 책을 넣는 메소드
function insert_cart(bookISBNArray){
    // fetch("/main/cart",
    //     {
    //         method : "POST",
    //         headers: {"content-type" : "application/json"},
    //         body: JSON.stringify({bookArray : bookISBNArray})
    //     })
    //     .then(value => value.text())
    //     .then(value => {
    //         console.log(value);
    //     })
    //     .catch(reason => {
    //         console.log(reason);
    //     })

}



const searchBarInput = document.getElementById("search-all-things").item(0);
const searchBar = document.getElementById("search-all-things").item(0);
const seachBtn = document.getElementById("search-all-things").lastElementChild


// 키워드로 책들 정보 가져오기
function search_book(searchKeyword){
    fetch("main/books/${searchKeyword}")
        .then(value => value.json())
        .then(value => {create_book_list(value)})
        .catch(reason => {})
}

searchBarInput.onkeydown = event => {
    if(event.key === "Enter"){
        if(searchBarInput.value.trim() === ""){
            alert("검색어가 없어");
            // 모든 책 리스트 가져오기
            get_books();
        } else {
            //검색어를 제대로 입력하고 엔터키 눌렀을 시 서버에 해당 데이터를 요청함
            search_book(searchBarInput.value);
        }
    }
}