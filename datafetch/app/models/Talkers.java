package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="talkers")
public class Talkers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long id;

    @Column(name="entity_id")
    public int entityId;

    @Column(name="mention_time")
    public Date mentionTime;

    @Column(name="count")
    public long count;

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

    public Date getMentionTime() {
        return mentionTime;
    }

    public void setMentionTime(Date mentionTime) {
        this.mentionTime = mentionTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
