package helper;

import models.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TwitterQueryFormatter {

    //SEARCH API
    public static String getQueryString(List<Keyword> keywords) throws UnsupportedEncodingException {
        List<String> queryList = new ArrayList<String>();
        for (Keyword keyword: keywords){
            queryList.add(keyword.keyword);
        }
        String query = StringUtils.join(queryList, " OR ");
        return URLEncoder.encode(query.trim(), "UTF-8");
    }
}
