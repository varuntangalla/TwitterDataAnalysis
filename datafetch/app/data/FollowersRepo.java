package data;



import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.DatetimeHelper;
import helper.JsonHelper;
import models.Followers;
import models.FollowersAggregate;
import twitter4j.User;

import java.util.*;

public class FollowersRepo {
    public static void updateFollowerCount(List<User> users){
        for (User user: users){
            Followers followers = Ebean.find(Followers.class).where()
                                .eq("screen_name", '@' + user.getScreenName())
                                .eq("time", DatetimeHelper.getDateUptoHours(new Date()))
                                .findUnique();
            if (followers != null){
                followers.setCount(user.getFollowersCount());
            }
            else{
                followers = new Followers();
                followers.screenName = '@' + user.getScreenName();
                followers.time = DatetimeHelper.getDateUptoHours(new Date());
                followers.count = user.getFollowersCount();
            }
            Ebean.save(followers);
        }
    }

    public static JsonNode getFollowers(Integer entityId, Date startTime, Date endTime, String granularity){ //todo: rewrite

        List<String> handles = HandleRepo.getHandlesForEntity(entityId);
        Map<Date, Integer> avgFollowers = new HashMap<>();
        for (String handle: handles){
            List<Followers> followersList = getFollowers(handle, startTime, endTime);
            for (Followers followers: followersList){
                Date time = new Date();
                if (granularity.equals("hours")) time = DatetimeHelper.getDateUptoHours(followers.time);
                else if (granularity.equals("days")) time = DatetimeHelper.getDateUptoDays(followers.time);

                Integer followersCount = avgFollowers.get(time);
                if (followersCount == null){
                    avgFollowers.put(time, followers.count);
                }
                else{
                    avgFollowers.put(time, followersCount + followers.count);
                }
            }
        }

        List<FollowersAggregate> list = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry: avgFollowers.entrySet()){
            list.add(new FollowersAggregate(entry.getKey(), entry.getValue()/handles.size()));
        }

        return JsonHelper.jsonify(list);
    }

    public static List<Followers> getFollowers(String handle, Date startTime, Date endTime){
        return Ebean.find(Followers.class).where().eq("screen_name", "@" + handle)
                .le("time", endTime)
                .ge("time", startTime)
                .findList();
    }
}
