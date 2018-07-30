package data;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.DatetimeHelper;
import helper.JsonHelper;
import helper.TweetWrapper;
import models.Tweet;
import twitter4j.Status;
import java.util.Date;
import java.util.List;

public class TweetRepository {

    private static final int MIN_RETWEETS_FOR_TRENDING = 10;

    public static JsonNode getMostRetweetedTweets(int entityId, Date startTime, Date endTime, Integer maxRows){
        if (startTime.equals(DatetimeHelper.getDateFromString("19700101000000"))) {
            startTime = new Date(System.currentTimeMillis() - 2592000000L); //2592000000 = 30 * 24 * 60 * 60 * 1000
        }

        List<Tweet> tweetList = Ebean.find(Tweet.class).where()
                                .eq("entity_id", entityId)
                                .le("created_at", endTime)
                                .ge("created_at", startTime)
                                .order().desc("retweet_count")
                                .setMaxRows(maxRows)
                                .findList();
        return JsonHelper.jsonify(tweetList);
    }

    public static void mostRetweetedTweets(TweetWrapper tweetWrapper, List<Integer> presentEntities) {

        if (tweetWrapper.status.isRetweet()){
            Status status = tweetWrapper.status.getRetweetedStatus();

            if (status.getRetweetCount() < MIN_RETWEETS_FOR_TRENDING) return;

            List<Tweet> tweetList = Ebean.find(Tweet.class).where().eq("tweet_id", status.getId()).findList();
            if (tweetList.size() == 0){
                for (Integer entityId: presentEntities){
                    Tweet tweet = extractDataFromTweet(status); //todo: remove extra work
                    tweet.entityId = entityId;
                    tweetList.add(tweet);
                }
            }
            else{
                for (Tweet tweet: tweetList){
                    tweet.setRetweetCount(status.getRetweetCount());
                }

            }
            Ebean.save(tweetList);
        }
    }

    private static Tweet extractDataFromTweet(Status status) {
        Tweet tweet = new Tweet();

        tweet.createdAt = status.getCreatedAt();
        tweet.retweetCount = status.getRetweetCount();

        tweet.tweetId = Long.toString(status.getId());
        tweet.text = status.getText();
        tweet.screenName = status.getUser().getScreenName();

        return tweet;
    }
}
