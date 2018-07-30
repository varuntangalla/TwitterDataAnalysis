package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="sentiment")
public class Sentiment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long id;

    @Column(name="time")
    public Date time;

    @Column(name="entity_id")
    public int entityId;

    @Column(name="score")
    public int score;

    @Column(name="count")
    public int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
