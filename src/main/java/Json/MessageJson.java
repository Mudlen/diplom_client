package Json;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sample.Entity.MessageEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageJson {
    private JSONObject root;
    private String room;
    public MessageJson(){
        root = new JSONObject();
    }

    public String getJsonMessage(int fromUserId, int toRoomId, String text){
        root.put("from_user_id",fromUserId);
        root.put("to_room_id",toRoomId);
        root.put("text",text);
        return root.toJSONString();
    }
    public ArrayList<String> getListFromJson(String json){
        ArrayList<String> messages = new ArrayList<>();
        String message;
        JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
        int length = Math.toIntExact((Long) jsonObject.get("length"));
        room = (String) jsonObject.get("nameChat");
        if(length <= 0){
            return null;
        }
        for(int i = 0; i < length; i++){
            JSONObject msg = (JSONObject) jsonObject.get(i+"");
            message = msg.get("date") + "\n" +msg.get("from_user_id") + " : " + msg.get("text");
            message = "\n" + message + "\n";
            messages.add(message);
        }
        return messages;
    }

    public String getRoom() {
        return room;
    }
}
