package data;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.DatetimeHelper;
import helper.JsonHelper;
import helper.TweetWrapper;
import models.Talkers;
import models.TalkersAggregate;
import twitter4j.Status;

import java.util.*;

public class TalkersRepo {
    public static JsonNode getTalkers(Integer entityId, Date startTime, Date endTime, String granularity){

        List<Talkers> talkersList = Ebean.find(Talkers.class).where()
                .eq("entity_id", entityId)
                .le("mention_time", endTime)
                .ge("mention_time", startTime)
                .findList();

        Map<Date, Long> map = new HashMap<>();
        for (Talkers talkers: talkersList){
            Date mentionTime = new Date();
            if (granularity.equals("hours")) mentionTime = DatetimeHelper.getDateUptoHours(talkers.mentionTime);
            else if (granularity.equals("days")) mentionTime = DatetimeHelper.getDateUptoDays(talkers.mentionTime);

            Long talkCount = map.get(mentionTime);
            if (talkCount == null){
                map.put(mentionTime, talkers.count);
            }
            else{
                map.put(mentionTime, talkers.count + talkCount);
            }
        }

        List<TalkersAggregate> list = new ArrayList<>();
        for (Map.Entry<Date, Long> entry: map.entrySet()){
            list.add(new TalkersAggregate(entry.getValue(), entry.getKey()));
        }

        return JsonHelper.jsonify(list);
    }

    public static void talkers(TweetWrapper tweetWrapper, List<Integer> entities){
        Status status = tweetWrapper.status;
        Date mentionTime = DatetimeHelper.getDateUptoHours(status.getCreatedAt());
        for (Integer entity: entities){
            Talkers talkers = Ebean.find(Talkers.class).where()
                                .eq("entity_id", entity)
                                .eq("mention_time", mentionTime)
                                .findUnique();
            if (talkers == null){
                talkers = new Talkers();
                talkers.count = 1;
                talkers.entityId = entity;
                talkers.mentionTime = mentionTime;
            }
            else{
                talkers.setCount(talkers.count + 1);
            }
            Ebean.save(talkers);
        }
    }
}
