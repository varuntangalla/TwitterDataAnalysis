package data;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.DatetimeHelper;
import helper.JsonHelper;
import helper.SentimentWrapper;
import models.Sentiment;
import models.SentimentAggregate;

import java.util.*;

public class SentimentRepo {

    public static void addSentiment(List<String> tweets, List<Integer> scores, List<SentimentWrapper> wrapper){
        Iterator<String> itweet = tweets.iterator();
        Iterator<Integer> iscore = scores.iterator();
        Iterator<SentimentWrapper> iwrapper = wrapper.iterator();

        while(itweet.hasNext() && iscore.hasNext() && iwrapper.hasNext()){
            SentimentWrapper sentimentWrapper = iwrapper.next();
            Integer score = iscore.next();
            String tweet = itweet.next();

            for (Integer entity: sentimentWrapper.presentEntities) {
                Sentiment sentiment = Ebean.find(Sentiment.class).where()
                        .eq("entity_id", entity)
                        .eq("score", score)
                        .eq("time", DatetimeHelper.getDateUptoMinutes(sentimentWrapper.time))
                        .findUnique();

                if (sentiment == null) {
                    sentiment = new Sentiment();
                    sentiment.time = DatetimeHelper.getDateUptoMinutes(sentimentWrapper.time);
                    sentiment.entityId = entity;
                    sentiment.score = score;
                    sentiment.count = 1;
                } else {
                    sentiment.setCount(sentiment.count + 1);
                }
                Ebean.save(sentiment);
            }
        }
    }

    public static JsonNode getSentiment(int entityId, Date startTime, Date endTime, String granularity) {
        List<Sentiment> sentiments = Ebean.find(Sentiment.class).where()
                                    .eq("entity_id", entityId)
                                    .le("time", endTime)
                                    .ge("time", startTime)
                                    .findList();

        Map<Date, Double> map = new HashMap<>();
        Map<Date, Integer> count = new HashMap<>();

        for(Sentiment sentiment: sentiments){
            Date time = new Date();
            if (granularity.equals("hours")) time = DatetimeHelper.getDateUptoHours(sentiment.time);
            else if (granularity.equals("days")) time = DatetimeHelper.getDateUptoDays(sentiment.time);
            Double scoreSum = map.get(time);
            if (scoreSum == null){
                map.put(time, (double) sentiment.score * sentiment.count);
                count.put(time, sentiment.count);
            }
            else{
                map.put(time, scoreSum + sentiment.score * sentiment.count);
                count.put(time, count.get(time) + sentiment.count);
            }
        }
        List<SentimentAggregate> list = new ArrayList<>();
        for (Map.Entry<Date, Double> entry: map.entrySet()){
            list.add(new SentimentAggregate(entry.getKey(), entry.getValue()/count.get(entry.getKey())));
        }

        return JsonHelper.jsonify(list);
    }
}
