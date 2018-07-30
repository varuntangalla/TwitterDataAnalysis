package core;

import data.RecentHashtags;

import java.io.IOException;
import java.util.Date;


public class MainApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(RecentHashtags.getRecentHashtags(1, new Date(1411126200000L), new Date(1411137000000L), 10));
    }
}
