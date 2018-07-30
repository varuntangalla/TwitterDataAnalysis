package core;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFactoryInstance {
    private static final String CONSUMER_KEY = "i89tukGuKHININpvI7S7s0qac";
    private static final String CONSUMER_SECRET = "AT6hnzcQdEbVxLhydM7Dl2kmu9yiHWc8Y8tn8TI0Ya9jzOXnsW";

    public static TwitterFactory getTwitterFactory(){
        return new TwitterFactory(getCB().build());
    }

    public static TwitterStreamFactory getStreamingTwitterFactory() {
        return new TwitterStreamFactory(getCB().build());
    }

    public static TwitterFactory getAppAuthTwitterFactory(){
        return new TwitterFactory(getCB().setApplicationOnlyAuthEnabled(true).build());
    }

    public static ConfigurationBuilder getCB(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);
        return cb;
    }
}
