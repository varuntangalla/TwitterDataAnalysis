package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import data.*;
import helper.Authentication;
import helper.DatetimeHelper;
import helper.JsonHelper;
import play.mvc.*;

import stream.StreamManagement;

import java.io.IOException;
import java.util.Date;

public class Application extends Controller { //todo: remove duplication in methods

    /*public static Result index() {
        return ok(index.render("Your new application is ready."));
    }*/

    public static Result options(String url){
        return ok();
    }

    public static Result addEntity(String accessToken, String accessTokenSecret) throws IOException, InterruptedException {

        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (userId == 0){
            userId = TokenRepository.addUser(accessToken, accessTokenSecret);
        }

        JsonNode node = request().body().asJson();
        int entityId = EntityRepo.addEntity(node, userId);

        StreamManagement.restartStream(userId, accessToken, accessTokenSecret);

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("entity_id", entityId);
        return ok(objectNode);
    }

    public static Result deleteEntity(int entityId, String accessToken, String accessTokenSecret) throws IOException, InterruptedException {
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);

        if (EntityRepo.doesEntityBelongToUser(userId, entityId)){
            EntityRepo.deleteEntity(userId, entityId);
            StreamManagement.restartStream(userId, accessToken, accessTokenSecret);
            return ok();
        }
        else{
            return badRequest();
        }
    }

    public static Result getEntity(int entityId, String accessToken, String accessTokenSecret) throws IOException {
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {
            return ok(JsonHelper.getEntity(entityId));
        }
        else{
            return badRequest();
        }
    }

    public static Result getAllEntities(String accessToken, String accessTokenSecret){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);

        return ok(JsonHelper.getAllEntities(userId));
    }

    public static Result getTalkers(int entityId, String startTime, String endTime, String accessToken, String accessTokenSecret, String granularity){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {
            return ok(TalkersRepo.getTalkers(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime), granularity));
        }
        else return badRequest();
    }

    public static Result getFollowers(int entityId, String startTime, String endTime, String accessToken, String accessTokenSecret, String granularity){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)){
            return ok(FollowersRepo.getFollowers(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime), granularity));
        }
        else{
            return badRequest();
        }
    }

    public static Result getGeoDistribution(int entityId, String startTime, String endTime, String accessToken, String accessTokenSecret){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)){
            return ok(GeoRepo.getGeoDistribution(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime)));
        }
        else{
            return badRequest();
        }
    }

    public static Result getMostRetweetedTweets(int entityId, String startTime, String endTime, Integer count, String accessToken, String accessTokenSecret){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {
            return ok(TweetRepository.getMostRetweetedTweets(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime), count));
        }
        else{
            return badRequest();
        }
    }

    public static Result getHashtags(int entityId, String startTime, String endTime, Integer count, String accessToken, String accessTokenSecret){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {
            return ok(RecentHashtags.getRecentHashtags(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime), count));
        }
        else{
            return badRequest();
        }
    }

    public static Result getRelevantTopics(int entityId, Integer count/*, String accessToken, String accessTokenSecret*/) {
        /*long userId = Authentication.authenticate(accessToken, accessTokenSecret);*/
        /*if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {*/
            return ok(TopicsRepo.getRelevantTopics(entityId, count));
       /* } else {
            return badRequest();
        }*/
    }

    public static Result getSentiment(int entityId, String startTime, String endTime, String accessToken, String accessTokenSecret, String granularity){
        long userId = Authentication.authenticate(accessToken, accessTokenSecret);
        if (EntityRepo.doesEntityBelongToUser(userId, entityId)) {
            return ok(SentimentRepo.getSentiment(entityId, DatetimeHelper.getDateFromString(startTime), DatetimeHelper.getDateFromString(endTime), granularity));
        } else {
            return badRequest();
        }
    }
}