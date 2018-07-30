package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class AppOAuthToken extends Model {

    @Id
    public String oauth_token;

    @Constraints.Required
    public String oauth_token_secret;

    public AppOAuthToken(String oauth_token, String oauth_token_secret) {
        this.oauth_token = oauth_token;
        this.oauth_token_secret = oauth_token_secret;
    }

    public static Finder<String, AppOAuthToken> find = new Finder<String, AppOAuthToken>(String.class, AppOAuthToken.class);
}
