const registForm = document.forms.item(0);
// 이메일 체크 정규식

const id = document.getElementsByName("email");
const pw = document.getElementsByName("password");
const name = document.getElementsByName("name");
const birth = document.getElementsByName("birth");
const phone_num = document.getElementsByName("phone");
const message_num = document.getElementsByName("message_num");
const btn_regist = document.getElementsByName("regist-mail");
const back = document.getElementsByName("go-back")
// 이메일***@***.*** 체크









btn_regist.item(0).addEventListener("click", ()=>{
    const userPhoneValue = phone_num.item(0).value;
    console.log(userPhoneValue);
    const userPhoneLength = userPhoneValue.length;
    phone_num.value =
        userPhoneLength === 11 ?
            userPhoneValue.substring(0, 3) + '-' + userPhoneValue.substring(3, 7) + '-' + userPhoneValue.substring(7, 11) :
            userPhoneValue.substring(0, 3) + '-' + userPhoneValue.substring(3, 6) + '-' + userPhoneValue.substring(6, 10);
    console.log(birth[0].value)
    console.log(phone_num.value);
    registForm.submit(); //do register
})


// 문자메시지로 인증하기
function phone_auth(){
    const bodyValue = JSON.stringify({
        "type": "SMS",
        "countryCode": "82",
        "from": "01029342912",  //발신번호 자기폰번호
        "contentType": "COMM",
        "content": "문자메시지 내용임",
        "messages": [
            {
                "content": "COMM",
                "to": "01029342912"  // 받는사람 번호
            }

        ]
    });
    fetch("https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/messages",
        {
            method: "POST",
            body: bodyValue,
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "x-ncp-apigw-timestamp": `{Timestamp}`,
                "x-ncp-iam-access-key": `{Sub Account Access Key}`,
                "x-ncp-apigw-signature-v2": `{API Gateway Signature}`
            }
        }
        ).then(value => {
            if(value.status === 202){
                console.log("정상작동");
            } else {
                console.log("오류.")
            }
        }).catch(reason => {
            console.log(reason);
    });

}


// function go

function phone_authenticate(){
    fetch('/api/sms/'+ phone_num[0].value)
        .then(value => {
            console.log(value);
            if(value.ok) {
                return value.text();
            }
            // if(value.text() === ){
            //
            // }
        })
        .then(value => {

            console.log(phone_num[0].value)
            console.log(value)
        })
        .catch(reason => {
            console.log(reason);
        })
}



// 강사님거 위에거 변형하기
// function phoneNumber_authenticate(){
//     fetch('/api/sms/' + userPhoneInput.value)
//         .then(value => {
//             if(value.ok) {
//                 return value.json();
//             }
//         })
//         .then(value => {
//             console.log(value);
//         })
//         .catch(reason => {
//             console.log(reason);
//         });
// }

function phoneNumber_authenticate_confirm(){
    fetch(`/api/sms?authNumber=${message_num[0].value}`)
        .then(value => {
            if(value.ok) {
                return value.text();
            }
        })
        .then(value => {
            console.log(value);
        })
        .catch(reason => {
            console.log(phone_num[0].value);
            console.log(reason);
        });
}

function email_authenticate(){
    fetch(`/api/user/${id[0].value}`)
        .then(value => value.text())
        .then(value => {
            if(value === "true"){
                console.log("이미 있는 유저다 => "+id[0].value);
                alert("유저가 존재해서 불가능");
            } else {
                alert("아이디 사용가능");
            }
            console.log(value);
        })
        .catch(reason => {
            console.log(reason);
        })
}


function checking_complete() {
    fetch(`/api/user/${id[0].value}`)
        .then(value => value.text())
        .then(value => {
            if (value.text.length < 9) {
                alert("정해진 형식을 따라주세요");
            } else {
                console.log(emailExp.test(value.text() + "정답?"));
                alert("감사");
            }
        })
        .catch(reason => {
            console.log(reason);
        })
}

