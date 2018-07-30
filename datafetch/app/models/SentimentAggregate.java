package models;

import java.util.Date;

public class SentimentAggregate {
    Date time;
    double score;

    public SentimentAggregate(Date time, double score) {
        this.time = time;
        this.score = score;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
