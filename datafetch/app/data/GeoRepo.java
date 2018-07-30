package data;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.DatetimeHelper;
import helper.JsonHelper;
import helper.TweetWrapper;
import models.Geography;
import twitter4j.Status;

import java.util.Date;
import java.util.List;

public class GeoRepo {
    public static void geoAnalysis(TweetWrapper tweetWrapper, List<Integer> presentEntities) {
        Status status = tweetWrapper.status;
        if (status.getGeoLocation() == null) return;

        for (Integer entity: presentEntities) {
            Geography geography = new Geography();
            geography.entityId = entity;
            geography.createdAt = status.getCreatedAt();
            geography.latitude = status.getGeoLocation().getLatitude();
            geography.longitude = status.getGeoLocation().getLongitude();
            Ebean.save(geography);
        }
    }

    public static JsonNode getGeoDistribution(int entityId, Date startTime, Date endTime){
        if (startTime.equals(DatetimeHelper.getDateFromString("19700101000000"))) {
            startTime = new Date(System.currentTimeMillis() - 216000000L); //43200 = 12 * 60 * 60
        }

        List<Geography> geographyList = Ebean.find(Geography.class).where()
                                        .eq("entity_id", entityId)
                                        .le("created_at", endTime)
                                        //.ge("created_at", startTime)
                                        .findList();

        return JsonHelper.jsonify(geographyList);
    }
}
