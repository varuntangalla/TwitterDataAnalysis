package helper;

import java.util.Date;
import java.util.List;

public class SentimentWrapper {
    public List<Integer> presentEntities;
    public Date time;

    public SentimentWrapper(List<Integer> presentEntities, Date time) {
        this.presentEntities = presentEntities;
        this.time = time;
    }

    public List<Integer> getPresentEntities() {
        return presentEntities;
    }

    public void setPresentEntities(List<Integer> presentEntities) {
        this.presentEntities = presentEntities;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
