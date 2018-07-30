package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="hashtag_counter")
public class HashtagCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    public long id;

    @Column(name="hashtag")
    public String hashtag;

    @Column(name="entity_id")
    public int entityId;

    @Column(name="created_at") //createdAt is the latest mention of the hashtag in any tweet.
    public Date createdAt;

    @Column(name="count")
    public long count;

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
