package helper;

import twitter4j.Status;

import java.io.Serializable;

public class TweetWrapper implements Serializable {
    private static final long serialVersionUID = 10034L;

    public long requestId;
    public Status status;

    public TweetWrapper(long requestId, Status status) {
        this.requestId = requestId;
        this.status = status;
    }
}
