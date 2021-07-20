package sample;

import Json.MessageJson;
import Json.RoomJson;
import Json.UserJson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import net.Network;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import sample.Entity.MessageEntity;
import sample.Entity.RoomEntity;
import sample.Entity.UsersEntity;
import sample.animation.AnimInputs;
import java.util.ArrayList;
import java.util.HashMap;


public class Controller {
    @FXML
    private Label some_name_chat;
    @FXML
    private TextField textSearch;
    @FXML
    private TextField chatName;
    @FXML
    private RadioButton privateChat;
    @FXML
    private VBox publicRoom;
    @FXML
    private Label addChat;
    @FXML
    private TextArea msgArea;
    @FXML
    private TextField inputSearchCreate;
    //переменные для не декорированного окна
    @FXML
    private Pane dragZone;

    @FXML
    private Pane collapse;

    @FXML
    private Pane deploy;

    @FXML
    private Pane close;

    @FXML
    private HBox box;
    //-----------------------------------------

    @FXML
    private Label nickName;

    @FXML
    private Pane linkToUserPage;

    @FXML
    private Label defaultLabelUser;

    @FXML
    private VBox menuDialog;

    @FXML
    private CustomTextField input;

    @FXML
    private ImageView addEmojis;

    @FXML
    private Pane emojiPicker;

    @FXML
    private AnchorPane auth_page;

    @FXML
    private Tab tabLOGIN;

    @FXML
    private CustomTextField login;

    @FXML
    private CustomPasswordField password;

    @FXML
    private Button loginNOW;

    @FXML
    private Label go_to_register_page;

    @FXML
    private Tab tabREG;

    @FXML
    private CustomTextField nameREG;

    @FXML
    private CustomTextField emailREG;

    @FXML
    private CustomTextField loginREG;

    @FXML
    private CustomPasswordField passwordREG;

    @FXML
    private Button registerBTN;
    @FXML
    private TabPane tabPaneRegLog;
    @FXML
    private AnchorPane chat_page;

    @FXML
    private TabPane tabPaneChat;

    @FXML
    private Tab someChat;
    @FXML
    private Tab search;



    private double xOffset = 0;
    private double yOffset = 0;
    private double width = 0;
    private double height = 0;
    private double x = 0;
    private double y = 0;
    private boolean flag;
    private boolean f;
    private static UsersEntity currentUser;
    private Network network;
    private static RoomEntity currentRoom;
    private static String roomFromServer = "";
    private static HashMap<String,ArrayList<RoomEntity>> roomsUser = new HashMap<>();
    private static HashMap<String,ArrayList<String>> allMessage = new HashMap<>();

    public static void addRoomUser(String key, ArrayList<RoomEntity> value){
        roomsUser.put(key,value);
    }
    public static ArrayList<RoomEntity> getValueRoomsUser(String key){
        return roomsUser.get(key);
    }
    public static ArrayList<String> messageEntities(String roomName){return allMessage.get(roomName);}
    public static HashMap<String,ArrayList<String>> getAllMessage(){
        return allMessage;
    }
    public RoomEntity getRoom(String key,String name){
        ArrayList<RoomEntity> list =  getValueRoomsUser(key);
        for(RoomEntity roomEntity: list){
            if((roomEntity.getName()).equals(name)){
                return roomEntity;
            }
        }
        return null;
    }

    public void appendMessage(String name){
        msgArea.appendText(name);
    }
    @FXML
    void initialize() {
        network = new Network(args -> {
            if((currentRoom.getName()).equals(args[0])){
                appendMessage(messageEntities((String) args[0])
                        .get(messageEntities((String) args[0]).size()-1));
            }

        });

        roomsUser.put("private",new ArrayList<>());
        //Добавление стандартных возможностей для не декорированного окна
        dragZone.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        dragZone.setOnMouseDragged(event -> {
            dragZone.getScene().getWindow().setX(event.getScreenX() - xOffset);
            dragZone.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });
        dragZone.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                flag = !flag;
                maximized(flag);
            }
        });

        close.setOnMouseClicked(event -> {
            network.close();
            Main.getPrimaryStage().close();
        });
        collapse.setOnMouseClicked(event -> {
            Main.getPrimaryStage().setIconified(true);
        });
        deploy.setOnMouseClicked(event -> {
            flag = !flag;
            maximized(flag);
        });
        // ----------------------------------------------------------------
        ImageView plus = new ImageView();
        Image plus_img = new Image("img/plus1.png");
        //plus.setImage(plus_img);
        plus.setFitWidth(30);
        plus.setFitHeight(30);
        input.setLeft(plus);

        addEmojis.setOnMouseClicked(event -> {
            f = !f;
            emojiPicker.setVisible(f);
            if(emojiPicker.isVisible()){
                input.setOnKeyReleased(event1 -> {
                    if(event1.getCode() == KeyCode.ESCAPE){
                        f = !f;
                        emojiPicker.setVisible(f);
                    }
                });
            }
        });

        go_to_register_page.setOnMouseClicked(event -> {
            tabPaneRegLog.getSelectionModel().select(tabREG);
        });

        registerBTN.setOnAction(event -> {
            if(!loginREG.getText().equals("")
                    && !passwordREG.getText().equals("")
            && !nameREG.getText().equals("")
                    && !emailREG.getText().equals(""))
            {
                String log = loginREG.getText();
                String pass = passwordREG.getText();
                String hashPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pass);
                String name = nameREG.getText();
                String email = emailREG.getText();
                UserJson userJson = new UserJson();
                network.sendMessage("/register "+userJson.getJsonRegister(log,hashPass,name,email));
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else {
                new AnimInputs(loginREG).play();
                new AnimInputs(passwordREG).play();
                new AnimInputs(nameREG).play();
                new AnimInputs(emailREG).play();
            }
            tabPaneRegLog.getSelectionModel().select(tabLOGIN);
        });
        loginNOW.setOnAction(event -> {
            if(!login.getText().equals("") && !password.getText().equals("")){
                String log = login.getText();
                String pass = password.getText();
                UserJson userJson = new UserJson();
                network.sendMessage("/login "+userJson.getJsonLogin(log,pass));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(currentUser != null){
                    setUser(currentUser);
                    auth_page.setVisible(false);
                    chat_page.setVisible(true);
                }else {
                    AnimInputs animInputs = new AnimInputs(login);
                    AnimInputs animInputsPass = new AnimInputs(password);
                    animInputs.play();
                    animInputsPass.play();
                }
            }else {
                AnimInputs animInputs = new AnimInputs(login);
                AnimInputs animInputsPass = new AnimInputs(password);
                animInputs.play();
                animInputsPass.play();
            }
        });

    }


    private void maximized(boolean flag){
        if(flag){
            height = Main.getPrimaryStage().getHeight();
            width = Main.getPrimaryStage().getWidth();
            x = Main.getPrimaryStage().getX();
            y = Main.getPrimaryStage().getY();
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            Main.getPrimaryStage().setX(bounds.getMinX());
            Main.getPrimaryStage().setY(bounds.getMinY());
            Main.getPrimaryStage().setWidth(bounds.getWidth());
            Main.getPrimaryStage().setHeight(bounds.getHeight());
        }else {
            Main.getPrimaryStage().setX(x);
            Main.getPrimaryStage().setY(y);
            Main.getPrimaryStage().setWidth(width);
            Main.getPrimaryStage().setHeight(height);
        }
    }

    public void getEmojiText(MouseEvent mouseEvent){
        Label label = (Label) mouseEvent.getSource();
        input.appendText(label.getText());
        input.requestFocus();
        input.positionCaret(input.getLength());
    }

    public void setUser(UsersEntity user){
        nickName.setText(user.getName());
    }

    public void createChat(ActionEvent actionEvent) {
        if(!chatName.getText().equals("")){
            String name = chatName.getText();
            int adminId = currentUser.getId();
            int isPublic;
            if(privateChat.isSelected()){
                isPublic = 0;
            }else {isPublic = 1;}
            RoomJson roomJson = new RoomJson();
            network.sendMessage("/create_room "+roomJson.getJsonRoom(name,adminId,isPublic));
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else {
            new AnimInputs(chatName).play();
        }
    }

    public void searchChat(MouseEvent actionEvent) {
        tabPaneChat.getSelectionModel().select(search);
    }

    public void send(ActionEvent event){
        MessageJson messageJson = new MessageJson();
        if(!(input.getText()).equals("")) {
            network.sendMessage("/message " +
                    messageJson.getJsonMessage(currentUser.getId(),
                            currentRoom.getId(), input.getText()));
            input.requestFocus();
            input.clear();
        }
    }

    public static void setCurrentUser(UsersEntity user) {
        currentUser = user;
    }

    public void reload(MouseEvent mouseEvent) {
        network.sendMessage("/reload");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(getValueRoomsUser("public") != null){
            publicRoom.getChildren().remove(2,publicRoom.getChildren().size());
            for(RoomEntity roomEntity: getValueRoomsUser("public")){
                Label room = new Label(roomEntity.getName());
                room.setCursor(Cursor.HAND);
                room.setMinWidth(publicRoom.getWidth());
                room.setAlignment(Pos.CENTER);
                room.getStyleClass().add("deploy-collapse");
                room.setFont(new Font(18));
                room.setTextFill(Color.valueOf("#b3bbe3"));
                Separator separator = new Separator();
                separator.setOrientation(Orientation.HORIZONTAL);
                separator.setOpacity(0.5);
                publicRoom.getChildren().add(room);
                publicRoom.getChildren().add(separator);
                room.setOnMouseClicked(event -> {
                    if(event.getClickCount() == 2){
                        setChatMenu("public",roomEntity.getName());
                    }
                });
            }
        }else System.out.println("list is empty");
    }

    public void addPrivateChat(ActionEvent event) {
        if(!textSearch.getText().equals("")){
            network.sendMessage("/get_private_chat "+textSearch.getText());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!roomFromServer.equals("error")){
                setChatMenu("private",textSearch.getText());
                textSearch.setText("");
            }else {
                textSearch.setText("");
                new AnimInputs(textSearch).play();
            }

        }else {
            new AnimInputs(textSearch).play();
        }
    }

    public void setChatMenu(String key, String name){
        FlowPane chat = new FlowPane();
        chat.getStyleClass().add("avatar");
        chat.setAlignment(Pos.CENTER);
        chat.setPrefSize(70,70);
        Tooltip prompt = new Tooltip(name);
        prompt.setFont(new Font(24));
        Label label = new Label("chat");
        label.setFont(new Font(18));
        label.setTextFill(Color.valueOf("#b3bbe3"));
        label.setTooltip(prompt);
        chat.getChildren().add(label);
        chat.setOnMouseClicked(event -> {
            currentRoom = getRoom(key,name);
            if(!allMessage.containsKey(name)){
                allMessage.put(name,new ArrayList<>());
            }
            tabPaneChat.getSelectionModel().select(someChat);
            some_name_chat.setText(name);
            msgArea.setText("");
            for(String msg: allMessage.get(name)){
                msgArea.appendText(msg);
            }
        });
        menuDialog.getChildren().add(chat);

    }

    public void loadHistory(MouseEvent mouseEvent) {
        network.sendMessage("/load_history "+currentRoom.getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msgArea.setText("");
        for(String msg: allMessage.get(currentRoom.getName())){
            msgArea.appendText(msg);
        }
    }
}
