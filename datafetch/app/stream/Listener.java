package stream;

import helper.TweetWrapper;
import queue.Publisher;
import twitter4j.*;

import java.io.IOException;

public class Listener implements StatusListener {

    private Publisher publisher;
    long id;

    public Listener(long id, Publisher publisher) throws IOException {
        this.id = id;
        this.publisher = publisher;
    }

    @Override
    public void onStatus(Status status) {
        try {
            TweetWrapper tweetWrapper = new TweetWrapper(id, status);
            publisher.pushToQueue(tweetWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
