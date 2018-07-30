package analytics;

import data.SentimentRepo;
import helper.Counter;
import helper.SentimentWrapper;
import helper.TweetWrapper;

import java.util.ArrayList;
import java.util.List;

public class Sentiment {

    private static List<String> tweets = new ArrayList<>();
    private static List<SentimentWrapper> wrapper = new ArrayList<>();
    private static final int BATCH_SIZE = 10;
    private static Counter counter = new Counter(10);

    public static void sentimentAnalysis(TweetWrapper tweetWrapper, List<Integer> presentEntities){

        if (counter.incrCounter() == 0){
            tweets.add(tweetWrapper.status.getText());
            wrapper.add(new SentimentWrapper(presentEntities, tweetWrapper.status.getCreatedAt()));
            if (tweets.size() > BATCH_SIZE){
                StanfordNLP sa = new StanfordNLP();
                sa.addDocument(tweets);
                List<Integer> scores = sa.score();
                System.out.println(scores);
                SentimentRepo.addSentiment(tweets, scores, wrapper);
                tweets.clear();
                scores.clear();
                wrapper.clear();
            }
        }
    }
}
