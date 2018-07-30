package stream;

import data.EntityRepo;
import data.TokenRepository;
import models.Token;
import queue.Publisher;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamManagement {
    public static Map<Long, FilterStream> streamMap;
    public static Publisher publisher;

    public static void restartStream(long userId, String accessToken, String accessTokenSecret) throws IOException, InterruptedException {
        FilterStream filterStream = streamMap.get(userId);
        if (filterStream != null) filterStream.shutdown();

        List<String> keywords = EntityRepo.getAllKeywords(userId);
        if (keywords.size() != 0) {
            filterStream = new FilterStream(userId, null, keywords.toArray(new String[keywords.size()]), new AccessToken(accessToken, accessTokenSecret), publisher);
            filterStream.filter();
            streamMap.put(userId, filterStream);
        }
    }

    public static void startStreams(Publisher publisher) throws IOException, InterruptedException {
        StreamManagement.publisher = publisher;
        StreamManagement.streamMap = new HashMap<>();

        List<Token> tokenList = TokenRepository.getUsers();
        for (Token token: tokenList){
            restartStream(token.userId, token.accessToken, token.accessTokenSecret);
        }
    }

    public static void closeStreams(){
        for (FilterStream filterStream: streamMap.values()){
            filterStream.shutdown();
        }
        System.out.println("Closing twitter streams.");
    }
}
