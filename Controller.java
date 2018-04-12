import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Label;

import static javafx.animation.Animation.INDEFINITE;

public class Controller {
    private LabT mainTimer = new LabT();
    private LabT allTimer = new LabT();
    private LabT researchTimer = new LabT();
    private LabT restTimer = new LabT();
    private LabT otherTimer = new LabT();

    @FXML
    private Label mainTimerLabel;
    @FXML
    private Label allTimerLabel;
    @FXML
    public Label childTimerLabel1;
    @FXML
    public Label childTimerLabel2;
    @FXML
    public Label childTimerLabel3;

    public Controller() {
        mainTimer.active();
        allTimer.active();
        researchTimer.active();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), //時間経過をトリガにするのはTimelineクラスを使う
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) { //ここに書いた処理がDuration.seconds(1)で示した感覚で実行される
                        mainTimer.timeCount();
                        allTimer.timeCount();
                        researchTimer.timeCount();
                        restTimer.timeCount();
                        otherTimer.timeCount();

                        mainTimerLabel.setText(mainTimer.formatPrint());
                        allTimerLabel.setText(allTimer.formatPrint());
                        childTimerLabel1.setText(researchTimer.formatPrint());
                        childTimerLabel2.setText(restTimer.formatPrint());
                        childTimerLabel3.setText(otherTimer.formatPrint());
                    }
                }
        ));
        timeline.setCycleCount(INDEFINITE); //何回繰り返すか指定する
        timeline.play();
    }
}
