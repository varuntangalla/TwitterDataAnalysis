package data;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import helper.JsonHelper;
import models.AppEntity;
import models.Handle;
import models.Keyword;
import models.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityRepo {
    public static void deleteEntity(long userId, int entityId){
        AppEntity appEntity = Ebean.find(AppEntity.class).where().eq("user_id", userId).eq("entity_id", entityId).findUnique();

        if (appEntity != null)
            Ebean.delete(appEntity);
    }

    public static AppEntity getEntityFromId(int entityId){
        //NULLPOINTER EXCEPTION LINE 2 AND 3
        AppEntity appEntity = Ebean.find(AppEntity.class, entityId);
        appEntity.keywords = Ebean.find(Keyword.class).where().eq("entity_id", entityId).findList();
        appEntity.handles = Ebean.find(Handle.class).where().eq("entity_id", entityId).findList();
        return appEntity;
    }

    public static List<Integer> getEntitiesForUser(long userId) {
        List<AppEntity> appEntities = Ebean.find(AppEntity.class).where().eq("user_id", userId).findList();
        List<Integer> entities = new ArrayList<>();
        for (AppEntity appEntity : appEntities) {
            entities.add(appEntity.entityId);
        }
        return entities;
    }

    public static List<String> getAllKeywords(long userId){
        List<AppEntity> appEntities = Ebean.find(AppEntity.class).where().eq("user_id", userId).findList();
        List<String> keywords = new ArrayList<>();
        for (AppEntity appEntity: appEntities){
            keywords.addAll(KeywordRepository.getKeywordsForEntity(appEntity.entityId));
            keywords.addAll(HandleRepo.getHandlesForEntity(appEntity.entityId));
        }
        return keywords;
    }

    public static int addEntity(JsonNode node, long userId) throws IOException {
        AppEntity appEntity = new AppEntity();
        appEntity.entityName = node.findValue("name").asText();
        appEntity.token = Ebean.find(Token.class, userId);

        //todo: remove duplication
        List<String> keywords = JsonHelper.readArray(node.findValue("keywords"));
        List<Keyword> keywordList = new ArrayList<Keyword>();

        for(String keyword: keywords){
            Keyword myKeyword = new Keyword();
            myKeyword.entity = appEntity;
            myKeyword.keyword = keyword;
            keywordList.add(myKeyword);
        }

        List<String> handles = JsonHelper.readArray(node.findValue("handles"));
        List<Handle> handleList = new ArrayList<Handle>();

        for (String handle: handles){
            Handle myHandle = new Handle();
            myHandle.entity = appEntity;
            myHandle.handle = handle;
            handleList.add(myHandle);
        }

        appEntity.keywords = keywordList;
        appEntity.handles = handleList;
        Ebean.save(appEntity);

        appEntity = Ebean.find(AppEntity.class).where()
                .eq("entity_name", appEntity.entityName)
                .eq("user_id", appEntity.token.userId)
                .findUnique();

        return appEntity.entityId;
    }

    public static boolean doesEntityBelongToUser(long userId, int entityId){
        AppEntity entity = Ebean.find(AppEntity.class).where().eq("user_id", userId).eq("entity_id", entityId).findUnique();
        if (entity == null) return false;
        return true;
    }
}
