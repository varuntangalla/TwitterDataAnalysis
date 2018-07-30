package data;

import com.avaje.ebean.Ebean;
import models.AppEntity;
import models.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class KeywordRepository {

    static Map entityPattern = new HashMap();
    
    //change this method to include handle table
    private static void makePatternForEntity(int entityId){
        List<String> keywords = getKeywordsForEntity(entityId);

        String pattern = StringUtils.join(keywords, "|");
        entityPattern.put(entityId, Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
    }

    public static Pattern getPatternForEntity(int entityId){
        if (!entityPattern.containsKey(entityId)) makePatternForEntity(entityId);
        return (Pattern) entityPattern.get(entityId);
    }

    public static List<String> getKeywordsForEntity(int entityId){
        List<Keyword> keywordList = Ebean.find(Keyword.class).where().eq("entity_id", entityId).findList();
        List<String> keywords = new ArrayList<String>();
        for (Keyword keyword: keywordList){
            keywords.add(keyword.keyword);
        }
        return keywords;
    }
}
