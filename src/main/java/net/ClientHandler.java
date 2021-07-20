package net;

import Json.MessageJson;
import Json.RoomJson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sample.Entity.RoomEntity;
import sample.Entity.UsersEntity;
import sample.Controller;
import java.util.ArrayList;
import java.util.HashMap;


public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private Callback onMessageReceivedCallback;
    public ClientHandler(Callback onMessageReceivedCallback) {
        this.onMessageReceivedCallback = onMessageReceivedCallback;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if (onMessageReceivedCallback != null) {
            if(s.startsWith("/")){
                if(s.startsWith("/login")){
                    String user = s.split("\\s", 2)[1];
                    if(!user.equals("error")) {
                        JSONObject jsonUser = (JSONObject) JSONValue.parse(user);
                        UsersEntity requestedUser = new UsersEntity();
                        requestedUser.setId(Math.toIntExact((Long) jsonUser.get("id")));
                        requestedUser.setName((String) jsonUser.get("name"));
                        requestedUser.setLogin((String) jsonUser.get("login"));
                        requestedUser.setPassword((String) jsonUser.get("password"));
                        requestedUser.setEmail((String) jsonUser.get("email"));
                        Controller.setCurrentUser(requestedUser);
                    }else {
                        Controller.setCurrentUser(null);
                    }
                }
                if(s.startsWith("/reload")){
                    String roomList = s.split("\\s", 2)[1];
                    RoomJson roomJson = new RoomJson();
                    ArrayList<RoomEntity> list = (ArrayList<RoomEntity>) roomJson.getListFromJson(roomList);
                    Controller.addRoomUser("public",list);
                }
                if(s.startsWith("/get_private_chat")){
                    String str = s.split("\\s", 2)[1];
                    if(!str.equals("error")){
                        JSONObject room = (JSONObject) JSONValue.parse(str);
                        RoomEntity roomEntity = new RoomEntity(
                                Math.toIntExact((Long) room.get("id")),
                                (String)room.get("name"),
                                Math.toIntExact((Long) room.get("adminId")),
                                Math.toIntExact((Long) room.get("isPublic")));
                        Controller.getValueRoomsUser("private").add(roomEntity);
                    }

                }
                if(s.startsWith("/message")){
                    String msg = s.split("\\s", 2)[1];
                    JSONObject jsonObject = (JSONObject) JSONValue.parse(msg);
                    String message = jsonObject.get("date") + "\n"+jsonObject.get("from_user_id")+" : "+jsonObject.get("text");
                    message = "\n" +message+ "\n";
                    if(Controller.getAllMessage().containsKey((String) jsonObject.get("to_room_id"))) {
                        Controller.messageEntities((String) jsonObject.get("to_room_id")).add(message);
                        onMessageReceivedCallback.callback((String) jsonObject.get("to_room_id"));
                    }
                }
                if(s.startsWith("/load_history")){
                    String listMsg = s.split("\\s", 2)[1];
                    MessageJson messageJson = new MessageJson();
                    ArrayList<String> messages = messageJson.getListFromJson(listMsg);
                    if(messages != null){
                        Controller.messageEntities(messageJson.getRoom()).clear();
                        Controller.messageEntities(messageJson.getRoom()).addAll(messages);
                    }
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
