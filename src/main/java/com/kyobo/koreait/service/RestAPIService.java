package com.kyobo.koreait.service;

import com.kyobo.koreait.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Log4j2
@Service
public class RestAPIService {
//    인증번호 만들기
    @Autowired
    private UserMapper userMapper;
//    public static String sms_authenticate_number = "";  //아래쪽에서 스트링빌드나, 단순 스트링으로 하거나

//    유저가 존재하면 true, 없으면 false 반환.
    public boolean get_user(String userEmail){
        return userMapper.get_user(userEmail) != null;
    }


    public String sendMessage(String phone_number) throws Exception{
//        String sms_authenticate_number = "";                            // 방법1
        StringBuilder smsAuthenticateNumber = new StringBuilder();    // 방법2
        String requestURL = "https://sens.apigw.ntruss.com/sms/v2/services/" +
                "ncp:sms:kr:303908619815:web_dev_study_230313/messages";
//        서비스 id는 콘솔의 프로젝트에서 가져올수 있다.
        String method = "POST";
        String url = "/sms/v2/services/ncp:sms:kr:303908619815:web_dev_study_230313/messages" ;
        String timestamp = Long.toString(System.currentTimeMillis());
        String accessKey = "J4vuXhdLUz1kk28BE47H";
        String secretKey = "eTB1YxXsTODCPaAVPDyNCJgHvTc5C6jweeuuOCts";

//        인증번호를 만들기 위한 랜덤 클래스
        for (int i = 0; i < 4; i++) {
//            sms_authenticate_number += (int)(Math.random() * 10);
            smsAuthenticateNumber.append((int) (Math.random() * 10));
        }
        log.info("-----만들어진 랜덤 인증번호 4자리 ===> " + smsAuthenticateNumber);

////        1. http요청을 위해 RestTemplate 객체선언
//        RestTemplate restTemplate = new RestTemplate();
//
////        2. 응답 메세지를 받기 위해 HttpComponentsClientHttpRequestFactory 객체를 생성
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
////        요청 timeout 20초
//        factory.setConnectTimeout(20000);
//        factory.setReadTimeout(20000);
//
////        3. HttpClient 객체 생성
//        HttpClient httpClient = HttpClientBuilder.create()
//                .setMaxConnPerRoute(50)     //최대 커넥션 수
//                .setMaxConnPerRoute(20)
//                .build();
//
////        4.factory에 httpclient 객체 삽입
//        factory.setHttpClient(httpClient);
//
////        5.restTemplate 갸ㅐㄱ체에 factory 설정
//        restTemplate.setRequestFactory(factory);
//
////        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
////        map.add("key","value");
////        map.add("key","value2");
////        map.add("key","value3");  // 리스트 값으로 같은 키값으로 할때 하나의 키,하나의 밸류만 인정하는게 아니라 여러밸류를 인정한다.
//
//
////        6. 요정을 위한 HTTP Header작성(생성)
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.set("x-ncp-apigw-timestamp", timestamp);
//        httpHeaders.set("x-ncp-iam-access-key", accessKey);
//        httpHeaders.set("x-ncp-apigw-signature-v2", makeSignature("POST", url, timestamp, accessKey, secretKey));
//
//
////        7.JSON 형태의 body 데이터 생성
//        JSONObject body = new JSONObject();
//        body.put("type", "SMS");
//        body.put("countryCode", "82");
//        body.put("from", "01029342912");
//        body.put("contentType", "COMM");
//        body.put("content", "메세지 확인용3");
//
////        모든 메세지 정보를 담은 JSONArray 객체
//        JSONArray messageArray = new JSONArray();
////        메세지를 보낸 한 사람의 내용을 담은 JSON 객체
//        JSONObject messageObject = new JSONObject();
////        messageObject.put("content","자바에서 보낸 문자입니다.");
////        messageObject.put("to","01029342912");
//        messageObject.put("content", sms_authenticate_number);
//        messageObject.put("to", phone_number); //전달받은 휴대폰 번호에 문자 발송
//
////        Array에 메세지 보낼 사람들의 정보를 넣어준다.
//        messageArray.put(messageObject);
////        messages key값은 JSON형태의 Array객체를 value로 가져야한다
//        body.put("messages",messageArray);
//
////        8. 위에 정의한 Header와 body 정보를 가지는 HttpEntity 객체 생성
//        HttpEntity<String> httpEntity = new HttpEntity<>(body.toString(), httpHeaders);
//
////        9.HTTP 요청 ==> 응답받은 응답 객체를 받기위해 ResponseEntity 선언
////        (요청 URL, 요청 Header와 body 정보를 가지는 Entity객체, 응답받을 형식)
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestURL, httpEntity, String.class);
//
//
////        10. 요청 결과 출력
//        System.out.println(responseEntity);
//        System.out.println(responseEntity.getStatusCode());
//        System.out.println(responseEntity.getBody());
//
////        return responseEntity;



//        return requestURL;
        return smsAuthenticateNumber.toString();

    }

    public String makeSignature(
            String method,
            String url,
            String timestamp,
            String accessKey,
            String secretKey) throws Exception {
        String space = " ";					// one space
        String newLine = "\n";					// new line
//        String method = "GET";					// method  위로 올렸다.
//        String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
//        String timestamp = "{timestamp}";			// current timestamp (epoch)
//        String accessKey = "{accessKey}";			// access key id (from portal or Sub Account)
//        String secretKey = "{secretKey}";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }




}
