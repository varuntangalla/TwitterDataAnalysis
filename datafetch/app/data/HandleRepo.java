package data;

import com.avaje.ebean.Ebean;
import models.Handle;

import java.util.ArrayList;
import java.util.List;

public class HandleRepo {

    public static List<String> getHandlesForEntity(int entityId){
        List<Handle> handles = Ebean.find(Handle.class).where().eq("entity_id", entityId).findList();
        List<String> keywords = new ArrayList<String>();
        for (Handle handle: handles){
            keywords.add(handle.handle);
        }
        return keywords;
    }

    public static List<String> getAllHandles(){
        List<Handle> handles = Ebean.find(Handle.class).findList();
        List<String> keywords = new ArrayList<String>();
        for (Handle handle: handles){
            keywords.add(handle.handle);
        }
        return keywords;
    }
}
