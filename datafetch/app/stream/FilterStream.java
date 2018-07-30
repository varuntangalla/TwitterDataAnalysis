package stream;

import core.TwitterFactoryInstance;
import queue.Publisher;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;

public final class FilterStream{

    long id;
    long[] follow;
    String[] track;
    AccessToken accessToken;
    StatusListener listener;
    TwitterStream twitterStream;

    public FilterStream(long id, long[] follow, String[] track, AccessToken accessToken, Publisher publisher) throws IOException {
        this.id = id;
        this.follow = follow;
        this.track = track;
        this.accessToken = accessToken;

        listener = new Listener(id, publisher);
        twitterStream = TwitterFactoryInstance.getStreamingTwitterFactory().getInstance(accessToken);
        twitterStream.addListener(listener);
    }

    public void filter() throws IOException, InterruptedException {
        twitterStream.filter(new FilterQuery(0, follow, track, null, new String[]{"en"}));
    }

    public void sample(){
        twitterStream.sample();
    }

    public void shutdown(){ twitterStream.shutdown(); }
}