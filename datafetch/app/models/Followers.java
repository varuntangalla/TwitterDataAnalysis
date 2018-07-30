package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="followers")
public class Followers {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name="screen_name")
    public String screenName;

    @Column(name="time")
    public Date time;

    @Column(name="count")
    public int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
