package Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sample.Entity.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public class RoomJson {
    private JSONObject root;

    public RoomJson(){
        root = new JSONObject();
    }

    public String getJsonRoom(String name, int adminId, int isPublic){
        root.put("name",name);
        root.put("adminId",adminId);
        root.put("isPublic",isPublic);
        return root.toJSONString();
    }

    public List<RoomEntity> getListFromJson(String json){
        List<RoomEntity> roomEntities = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
        JSONArray array = (JSONArray) jsonObject.get("array");
        for(int i = 0; i < array.size(); i++){
            JSONObject data = (JSONObject) array.get(i);
            RoomEntity roomEntity = new RoomEntity(
                    Math.toIntExact((Long) data.get("id")),
                    (String) data.get("name"),
                    Math.toIntExact((Long) data.get("adminId")),
                    Math.toIntExact((Long) data.get("isPublic")));

            roomEntities.add(roomEntity);
        }
        return roomEntities;
    }
}
