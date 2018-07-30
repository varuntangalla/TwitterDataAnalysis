package models;

import java.util.Date;

public class TalkersAggregate {
    public long count;
    public Date time;

    public TalkersAggregate(long count, Date time) {
        this.count = count;
        this.time = time;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
