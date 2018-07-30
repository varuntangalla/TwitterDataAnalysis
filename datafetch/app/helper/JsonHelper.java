package helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import data.*;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonHelper {

    public static JsonNode getEntity(int entityId) throws IOException { //todo: move code to entity repo
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.valueToTree(new EntityWrapper(entityId));

        return result;
    }

    public static JsonNode getAllEntities(long userId) { //todo: move code to entity repo
        List<Integer> entityList = EntityRepo.getEntitiesForUser(userId);
        List<EntityWrapper> wrappers = new ArrayList<>();

        for (Integer entity : entityList) {
            wrappers.add(new EntityWrapper(entity.intValue()));
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(wrappers);
    }

    public static List<String> readArray(JsonNode node) {
        List<String> arr = new ArrayList<String>();
        for (JsonNode x : node) {
            arr.add(x.asText());
        }
        return arr;
    }

    public static <T> JsonNode jsonify(T a){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(a);
    }
}