package com.back.domain.member.member.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;

    @Column(unique = true)
    private String apiKey;// username, pw대신 쓸 난수

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.apiKey = UUID.randomUUID().toString();
    }

    //나중에 바뀔 것 같은 부분을 함수로 빼자
    public String getName() {
        return nickname;
    }

    public void modifyApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
