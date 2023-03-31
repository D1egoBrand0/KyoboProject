//package com.kyobo.koreait;
//
//import org.springframework.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//public class MessageSendTest {
//
//    public String makeSignature(
//            String method,
//            String url,
//            String timestamp,
//            String accessKey,
//            String secretKey) throws Exception {
//        String space = " ";					// one space
//        String newLine = "\n";					// new line
////        String method = "GET";					// method  위로 올렸다.
////        String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
////        String timestamp = "{timestamp}";			// current timestamp (epoch)
////        String accessKey = "{accessKey}";			// access key id (from portal or Sub Account)
////        String secretKey = "{secretKey}";
//
//        String message = new StringBuilder()
//                .append(method)
//                .append(space)
//                .append(url)
//                .append(newLine)
//                .append(timestamp)
//                .append(newLine)
//                .append(accessKey)
//                .toString();
//
//        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//
//        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//        String encodeBase64String = Base64.encodeBase64String(rawHmac);
//
//        return encodeBase64String;
//    }
//
//    @Test
//    public void sendMessage() throws Exception{
//        String requestURL = "https://sens.apigw.ntruss.com/sms/v2/services/" +
//                "ncp:sms:kr:303908619815:web_dev_study_230313/messages";
////        서비스 id는 콘솔의 프로젝트에서 가져올수 있다.
//        String method = "POST";
//        String url = "/sms/v2/services/ncp:sms:kr:303908619815:web_dev_study_230313/messages" ;
//        String timestamp = Long.toString(System.currentTimeMillis());
//        String accessKey = "J4vuXhdLUz1kk28BE47H";
//        String secretKey = "eTB1YxXsTODCPaAVPDyNCJgHvTc5C6jweeuuOCts";
//
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
//                        .setMaxConnPerRoute(50)     //최대 커넥션 수
//                        .setMaxConnPerRoute(20)
//                        .build();
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
//        messageObject.put("content","자바에서 보낸 문자입니다.");
//        messageObject.put("to","01029342912");
////        Array에 메세지 보낼 사람들의 정보를 넣어준다.
//        messageArray.put(messageObject);
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
//    }
//
//
//
//}
