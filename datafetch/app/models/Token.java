package models;

import com.avaje.ebean.validation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="token")
public class Token {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long userId;

    @Length(max=300)
    @Column(name="access_token")
    public String accessToken;

    @Length(max=300)
    @Column(name="access_token_secret")
    public String accessTokenSecret;

    @OneToMany(mappedBy = "token", targetEntity = AppEntity.class, cascade = CascadeType.ALL)
    public List<AppEntity> appEntities;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public List<AppEntity> getAppEntities() {
        return appEntities;
    }

    public void setAppEntities(List<AppEntity> appEntities) {
        this.appEntities = appEntities;
    }
}
