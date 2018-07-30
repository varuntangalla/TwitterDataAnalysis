package models;

import java.util.Date;

public class FollowersAggregate {

    Date time;

    int followers;

    public FollowersAggregate(Date time, int followers) {
        this.time = time;
        this.followers = followers;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
