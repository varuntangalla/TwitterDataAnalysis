package controllers;

import models.AppOAuthToken;
import play.libs.Json;
import play.mvc.Result;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import utils.Serializer;
import views.html.home;
import views.html.index;

import java.io.IOException;

import static play.mvc.Controller.request;
import static play.mvc.Controller.session;
import static play.mvc.Results.*;


public class Application {

    private static final String applicationName = "TwiST";

    public static Result index() {
        String serializedAccessToken = session("access_token");
        if (serializedAccessToken != null) {
            AccessToken accessToken;
            try {
                accessToken = (AccessToken) Serializer.fromString(serializedAccessToken);
            } catch (Exception e) {
                return redirect(routes.Application.signout());
            }

            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthAccessToken(accessToken);
            try {
                twitter.verifyCredentials();
            } catch (TwitterException e) {
                redirect(routes.Application.signout());
            }

            return ok(home.render(applicationName, Json.stringify(Json.toJson(accessToken))));
        } else
            return ok(index.render(applicationName));
    }

    public static Result getRequestToken() {
        Twitter twitter = new TwitterFactory().getInstance();
        RequestToken requestToken;
        try {
            requestToken = twitter.getOAuthRequestToken(routes.Application.signin("", "").absoluteURL(request()));
        } catch (TwitterException e) {
            return internalServerError("Unable to get request token from Twitter.");
        }

        new AppOAuthToken(requestToken.getToken(), requestToken.getTokenSecret()).save();
        return ok(requestToken.getToken());
    }

    public static Result signin(String oauth_token, String oauth_verifier) {
        if (oauth_token.equals("") || oauth_verifier.equals(""))
            return internalServerError("Twitter permission not granted; Sign in failed!");

        AppOAuthToken appOAuthToken = AppOAuthToken.find.where().eq("oauth_token", oauth_token).findUnique();
        if (appOAuthToken == null)
            return internalServerError("Invalid oauth token; Sign in failed!");

        Twitter twitter = new TwitterFactory().getInstance();
        RequestToken requestToken = new RequestToken(appOAuthToken.oauth_token, appOAuthToken.oauth_token_secret);
        appOAuthToken.delete();
        AccessToken accessToken;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
        } catch (TwitterException e) {
            return internalServerError("Unable to obtain access token; Sign in failed");
        }

        try {
            session("access_token", Serializer.toString(accessToken));
        } catch (IOException e) {
            return internalServerError("Unable to create session; Sign in failed");
        }

        return redirect(routes.Application.index());
    }

    public static Result signout() {
        session().clear();
        return redirect(routes.Application.index());
    }
}
