package stream;

import helper.PropertyLoader;
import queue.Publisher;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.util.Properties;

public class SimpleStream {

    private String accessToken;
    private String accessTokenSecret;
    private Publisher publisher;

    public SimpleStream(Publisher publisher) {
        Properties properties = PropertyLoader.getProperties("twitter.properties");
        accessToken = properties.getProperty("author.access_token");
        accessTokenSecret = properties.getProperty("author.access_token_secret");
        this.publisher = publisher;
    }

    public void sample() throws IOException, InterruptedException {
       FilterStream filterStream = new FilterStream(34577867, null, null, new AccessToken(accessToken, accessTokenSecret), publisher);
       filterStream.sample();
    }

    public void filter(String[] track) throws IOException, InterruptedException {
        FilterStream filterStream = new FilterStream(34577867, null, track, new AccessToken(accessToken, accessTokenSecret), publisher);
        filterStream.filter();
    }

    public void follow(long[] follow) throws IOException, InterruptedException {
            FilterStream filterStream = new FilterStream(34577867, follow, null, new AccessToken(accessToken, accessTokenSecret), publisher);
        filterStream.filter();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleStream simpleStream = new SimpleStream(new Publisher("TweetRepo"));
        simpleStream.sample();
    }
}
