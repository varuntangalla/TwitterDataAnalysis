package core;

import data.HandleRepo;
import search.AppLookupService;
import twitter4j.TwitterException;

import java.util.List;

public class Followers implements Runnable{ //todo: make this a cron job and run every hour

    private static final int SLEEP_TIME_IN_MS = 3600000;
    AppLookupService lookupService;
    @Override
    public void run() {
        try {
            lookupService = new AppLookupService();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        while(true){
            List<String> handles = HandleRepo.getAllHandles();
            try {
                lookupService.findFollowersCount(handles);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(SLEEP_TIME_IN_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
