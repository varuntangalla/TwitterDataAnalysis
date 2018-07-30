package analytics;

import com.rabbitmq.client.QueueingConsumer;
import data.*;
import helper.Serializer;
import helper.TweetWrapper;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import queue.Subscriber;
import twitter4j.Status;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

public class Analytics {
    static Logger logger = Logger.getLogger(Analytics.class.getName());
    static int tweetsCount = 0;

    private static void analyzeData(TweetWrapper tweetWrapper){

        List<Integer> presentEntities = findEntity(tweetWrapper);
        TalkersRepo.talkers(tweetWrapper, presentEntities);
        TweetRepository.mostRetweetedTweets(tweetWrapper, presentEntities);
        RecentHashtags.hashtags(tweetWrapper, presentEntities);
        TopicsRepo.trendingTopics(tweetWrapper, presentEntities);
        GeoRepo.geoAnalysis(tweetWrapper, presentEntities);
        Sentiment.sentimentAnalysis(tweetWrapper, presentEntities);

        tweetsCount++;
        if (tweetsCount == 100){
            tweetsCount = 0;
            logger.debug("Current time:" + new Date() + "\tTweet time:" + tweetWrapper.status.getCreatedAt());
        }
    }

    private static String getOriginalTweet(Status status){
        if (status.isRetweet())
            return status.getRetweetedStatus().getText();
        else return status.getText();
    }

    private static List<Integer> findEntity(TweetWrapper tweetWrapper) {
        Status status = tweetWrapper.status;
        String text = getOriginalTweet(status);
        List<Integer> presentEntities = new ArrayList<>();
        List<Integer> entities = EntityRepo.getEntitiesForUser(tweetWrapper.requestId);
        for (Integer entity : entities) {
            Matcher matcher = KeywordRepository.getPatternForEntity(entity).matcher(text);
            if (matcher.find()) {
                presentEntities.add(entity);
            }
        }
        return presentEntities;
    }

    private static void receiveFromQueue(Subscriber subscriber) throws IOException, ClassNotFoundException, InterruptedException {
        while (true) {
            QueueingConsumer.Delivery delivery = subscriber.getConsumer().nextDelivery();
            TweetWrapper tweetWrapper = (TweetWrapper) Serializer.deserialize(delivery.getBody());
            Analytics.analyzeData(tweetWrapper);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Subscriber subscriber = (Subscriber) context.getBean("Subscriber");
        receiveFromQueue(subscriber);
    }
}
