package search;

import core.TwitterFactoryInstance;
import data.FollowersRepo;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.OAuth2Token;

import java.util.ArrayList;
import java.util.List;

public class AppLookupService {

    private Twitter twitter;

    public AppLookupService() throws TwitterException {
        twitter = TwitterFactoryInstance.getAppAuthTwitterFactory().getInstance();
        OAuth2Token token = twitter.getOAuth2Token();

        twitter.setOAuth2Token(token);
    }

    public void findFollowersCount(List<String> handles) throws TwitterException {
        List<String> screenNames = new ArrayList();
        for (String handle: handles){
            screenNames.add(handle.substring(0));
        }

        for (int i = 0; i < screenNames.size(); i += 100){
            List<String> screenNamesSublist = screenNames.subList(i, Math.min(i+100, screenNames.size()));
            List<User> users = twitter.users().lookupUsers(screenNamesSublist.toArray(new String[screenNamesSublist.size()]));
            FollowersRepo.updateFollowerCount(users);
        }
    }
}
