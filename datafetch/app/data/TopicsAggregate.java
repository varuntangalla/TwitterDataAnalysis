package data;

/**
 * Created by satheesh on 15/2/17.
 */
public class TopicsAggregate {

    String name;
    long count;

    public TopicsAggregate(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
