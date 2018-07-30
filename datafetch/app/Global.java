import core.Followers;
import play.*;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import queue.Publisher;
import stream.StreamManagement;

import java.io.IOException;
import java.util.Date;

public class Global extends GlobalSettings{
    @Override
    public void onStart(Application app){
        System.out.println("Starting play...." + new Date());
        try {
            Publisher publisher = new Publisher("TWEETS");
            StreamManagement.startStreams(publisher);
            Thread followers = new Thread(new Followers());
            followers.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop(Application app){
        StreamManagement.closeStreams();
    }

    private class ActionWrapper extends Action.Simple {
        public ActionWrapper(Action<?> action) {
            this.delegate = action;
        }

        @Override
        public F.Promise<SimpleResult> call(Http.Context ctx) throws java.lang.Throwable {
            F.Promise<SimpleResult> result = this.delegate.call(ctx);
            Http.Response response = ctx.response();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
            return result;
        }
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
        return new ActionWrapper(super.onRequest(request, actionMethod));
    }
}
