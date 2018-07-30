package helper;

import com.avaje.ebean.Ebean;
import models.Token;

public class Authentication {
    public static long authenticate(String accessToken, String accessTokenSecret){
        Token token = Ebean.find(Token.class).where().eq("access_token", accessToken).eq("access_token_secret", accessTokenSecret).findUnique();
        if (token == null){
            return 0;
        }
        else{
            return token.userId;
        }
    }
}
