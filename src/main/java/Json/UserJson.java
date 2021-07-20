package Json;

import org.json.simple.JSONObject;
import sample.Entity.UsersEntity;

public class UserJson {
    private JSONObject root;
    private UsersEntity usersEntity;
    public UserJson(){
        root = new JSONObject();
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public String getJsonLogin(String login, String password){
        root.put("login",login);
        root.put("password",password);
        return root.toJSONString();
    }
    public String getJsonLogin(){
        root.put("login",usersEntity.getLogin());
        root.put("password",usersEntity.getPassword());
        return root.toJSONString();
    }

    public String getJsonRegister(String login, String pass, String name, String email){
        root.put("login",login);
        root.put("password",pass);
        root.put("name",name);
        root.put("email",email);
        return root.toJSONString();
    }
}
