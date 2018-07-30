package data;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.AppEntity;
import models.Keyword;
import models.Token;

import java.util.*;

public class TokenRepository {

    public static void addUser(JsonNode node){
        Token token = new Token();
        token.accessToken = node.findValue("access_token").asText();
        token.accessTokenSecret = node.findValue("access_token_secret").asText();
        Ebean.save(token);
    }

    public static long addUser(String accessToken, String accessTokensecret){
        Token token = new Token();
        token.accessToken = accessToken;
        token.accessTokenSecret = accessTokensecret;
        Ebean.save(token);

        token = Ebean.find(Token.class).where().eq("access_token", accessToken)
                        .eq("access_token_secret", accessTokensecret).findUnique();
        return token.userId;
    }

    public static List<Token> getUsers(){
        return Ebean.find(Token.class).findList();
    }
}