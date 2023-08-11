# Spring Boot oAuth2 project  
## 구글 ,네이버 oauth2 로그인 기본설정 yml
<details>
    <summary> oauth 로그인 접기/펼치기</summary>

````
application-oauth2.yml

spring:
  security:
    oauth2:
      client:
        registration:
          google:
            clientId: 
            clientSecret: 
            scope: email, profile

          naver:
            client-id: 
            client-secret: 
            redirect-uri: "http://localhost:8095/login/oauth2/code/naver"
            authorization-grant-type: authorization_code
            scope: email, nickname
            client-name: Naver

        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response

````

</details>
# oauth2
# oauth2
