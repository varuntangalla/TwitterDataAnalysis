package models;

import com.avaje.ebean.validation.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tweets")
public class Tweet {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Length(max=200)
    @Column(name="tweet_id")
    public String tweetId;

    @Column(name="entity_id")
    public long entityId;

    @Column(name="text")
    public String text;

    @Column(name="retweet_count")
    public long retweetCount;

    @Column(name="created_at")
    public Date createdAt;

    @Column(name="screen_name")
    public String screenName;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
