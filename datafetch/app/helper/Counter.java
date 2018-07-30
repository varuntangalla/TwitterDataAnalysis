package helper;

public class Counter {
    private  int count;
    private int mValue;

    public Counter(int mValue) {
        this.mValue = mValue;
        count = 1;
    }

    public int incrCounter(){
        count += 1;
        if (count == mValue) count = 0;
        return count;
    }
}
