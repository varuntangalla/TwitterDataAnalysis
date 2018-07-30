package search;

import com.avaje.ebean.Ebean;
import core.TwitterFactoryInstance;
import helper.TwitterQueryFormatter;
import models.Token;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TwitterLookupService {
    private Twitter twitter;
    private Map<Long, Long> maxTweetId;

    public TwitterLookupService(){
        TwitterFactory twitterFactory = TwitterFactoryInstance.getTwitterFactory();
        twitter = twitterFactory.getInstance();

        maxTweetId = new HashMap<Long, Long>();
    }

//    void multiQuery(Iterator<Token> tokenIterator) throws UnsupportedEncodingException {
//        while (tokenIterator.hasNext()) {
//            query(tokenIterator.next());
//        }
//    }
//
//    void query(Token token) throws UnsupportedEncodingException {
//        Query query = getQuery(token);
//        twitter.setOAuthAccessToken(new AccessToken(token.accessToken, token.accessTokenSecret));
//        QueryResult result;
//        try {
//            result = twitter.search(query);
//            long maxId = maxTweetId.get(token.userId)==null?0: maxTweetId.get(token.userId);
//
//            int count = 0;
//            for (Status status: result.getTweets()){
//                count++;
//                maxId = Math.max(maxId, status.getId());
//                //publish
//            }
//            System.out.println("Count: " + count);
//            maxTweetId.put(token.userId, maxId);
//
//        } catch (TwitterException te) {
//            handleError(token, te);
//        }
//    }
//
//    Query getQuery(Token token) throws UnsupportedEncodingException {
//        Query query = new Query(TwitterQueryFormatter.getQueryString(token.keywords));
//        query.setCount(100);
//
//        if (maxTweetId.get(token.userId) != null){
//            query.setSinceId(maxTweetId.get(token.userId)); //!
//        }
//
//        query.setResultType(Query.ResultType.recent);
//
//        return query;
//    }
//
//    private static void handleError(Token token, TwitterException te) {
//        if(te.getErrorCode() == 89){
//            Ebean.delete(token);
//        }
//    }

    public void queryUsingApplication(){
        try {
            List<User> users = twitter.users().lookupUsers(new String[]{"arunkumar_ra"});
            for (User user: users){
                System.out.println(user);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
