package helper;

import data.EntityRepo;
import models.AppEntity;
import models.Handle;
import models.Keyword;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class EntityWrapper {
    public int id;
    public String name;
    public List<String> keywords;
    public List<String> handles;

    public EntityWrapper(int id) {
        AppEntity appEntity = EntityRepo.getEntityFromId(id);
        this.name = appEntity.entityName;
        this.id = appEntity.entityId;
        this.keywords = new ArrayList<String>();
        for (Keyword k: appEntity.keywords){
            this.keywords.add(k.keyword);
        }

        this.handles = new ArrayList<String>();
        for (Handle h: appEntity.handles){
            this.handles.add(h.handle);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getHandles() {
        return handles;
    }

    public void setHandles(List<String> handles) {
        this.handles = handles;
    }
}
