package data;

/**
 * Created by satheesh on 15/2/17.
 */
public class HashTagsAggregate {
    String hashtag;
    long count;

    public HashTagsAggregate(String hashtag, long count) {
        this.hashtag = hashtag;
        this.count = count;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
