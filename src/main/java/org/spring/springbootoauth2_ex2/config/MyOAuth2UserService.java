package org.spring.springbootoauth2_ex2.config;

import lombok.RequiredArgsConstructor;
import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.spring.springbootoauth2_ex2.entity.Role;
import org.spring.springbootoauth2_ex2.repository.MemberRepository;
import org.spring.springbootoauth2_ex2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Google, Naver
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientId = clientRegistration.getClientId();
        String resistrateId = clientRegistration.getRegistrationId();
        String clientName = clientRegistration.getClientName();

        System.out.println("============================");
        System.out.println("clientId: " + clientId);
        System.out.println("resistrateId: " + resistrateId);
        System.out.println("clientName: " + clientName);
        System.out.println("============================");

        // 실제로 필요한   -> email ,pw, role
        Map<String, Object> attributes = oAuth2User.getAttributes();

        for (String key : attributes.keySet()) {
            System.out.println(key + " : " + attributes.get(key));
        }
        System.out.println("=============email===============");
        System.out.println("email->" + attributes.get("email"));

        String email = "";
        String pw = "dkanrjsk";
        String nickName = "";
        if (resistrateId.equals("google")) {
            System.out.println("구글로그인");
            email = (String) attributes.get("email");

        } else if (resistrateId.equals("naver")) {
            System.out.println("네이버로그인");
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            email = (String) response.get("email");
            nickName = (String) response.get("name");

            System.out.println((String) response.get("id"));
            System.out.println((String) response.get("nickname"));
            System.out.println((String) response.get("gender"));
            System.out.println((String) response.get("email"));
            System.out.println((String) response.get("name"));

        } else if (resistrateId.equals("kakao")) {

            System.out.println("카카오로그인");
            //JSON 으로 받은 데이터 -> Map으로 변환
            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

            System.out.println("reponse : " + response);
            System.out.println("reponse - email : " + response.get("email"));

            Map<String, Object> profile = (Map<String, Object>) response.get("profile");
            System.out.println("reponse - nickname : " + profile.get("nickname"));

            email = (String) response.get("email");
            nickName = (String) profile.get("nickname");
        }

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(email);
        if (optionalMemberEntity.isPresent()) {
            // oAuthUsr -> DB 비교 -> 있으면
            return new MyUserDetails(optionalMemberEntity.get());
        }

        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .email(email)
                .pw(passwordEncoder.encode(pw))
                .nickName(nickName)
                .role(Role.MEMBER)
                .build());

        // oAuthUsr -> DB비교  없으면 생성
        return new MyUserDetails(memberEntity, attributes);

    }


}