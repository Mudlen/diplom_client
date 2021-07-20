package sample.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimInputs {
    private TranslateTransition translateTransition;

    public AnimInputs(Node node){
        translateTransition = new TranslateTransition(Duration.millis(70), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(3);
        translateTransition.setAutoReverse(true);
    }

    public void play(){
        translateTransition.play();
    }
}
