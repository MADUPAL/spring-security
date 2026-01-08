package com.back.domain.member.member.service;

import com.back.domain.member.member.entity.Member;
import com.back.standard.util.Ut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 회원의 정보를 어떻게 토큰화 시켜야 하는지 알고있는 클래스
 * jjwt에 의존하는 클래스(jjwt를 알고있다)
 * memberService는 이 클래스를 의존한다
 * memberService -> AuthTokenService -> jjwt
 * 나중에 jwt를 다루는 라이브러리를 바꾼다면 이 클래스만 변경하면 된다.
 * 마치 controller -> service -> repository같은 구조
 */
@Service
public class AuthTokenService {
    @Value("${custom.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${custom.accessToken.expirationSeconds}")
    private int accessTokenExpirationSeconds;

    //default로 둬서 같은 패키지 내에 있는 애들만 접근 가능, 다른 클래스가 못쓰게 해서 결합도를 낮춘다.
    //이 프로젝트에서는 memberService만 접근 가능
    String genAccessToken(Member member) {
        long id = member.getId();
        String username = member.getUsername();
        String name = member.getName();

        return Ut.jwt.toString(
                jwtSecretKey,
                accessTokenExpirationSeconds,
                Map.of("id", id, "username", username, "name", name)
        );
    }

    Map<String, Object> payload(String accessToken) {
        Map<String, Object> parsedPayload = Ut.jwt.payload(jwtSecretKey, accessToken);

        if (parsedPayload == null) return null;

        int id = (int) parsedPayload.get("id");
        String username = (String) parsedPayload.get("username");
        String name = (String) parsedPayload.get("name");

        return Map.of("id", id, "username", username, "name", name);
    }
}