import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.animation.Animation.INDEFINITE;

public class Controller implements Initializable {
    private LabT mainTimer = new LabT();
    private LabT allTimer = new LabT();
    private LabT researchTimer = new LabT();
    private LabT restTimer = new LabT();
    private LabT otherTimer = new LabT();

    private LocalDateTime dt;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss");

    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), //時間経過をトリガにするのはTimelineクラスを使う
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) { //ここに書いた処理がDuration.seconds(1)で示した感覚で実行される
                        mainTimer.timeCount();
                        allTimer.timeCount();
                        researchTimer.timeCount();
                        restTimer.timeCount();
                        otherTimer.timeCount();

                        dt = LocalDateTime.now();
                        nowTime.setText(dt.format(dtf));

                        mainTimerLabel.setText(mainTimer.formatPrint());
                        allTimerLabel.setText(allTimer.formatPrint());
                        childTimerLabel1.setText(researchTimer.formatPrint());
                        childTimerLabel2.setText(restTimer.formatPrint());
                        childTimerLabel3.setText(otherTimer.formatPrint());
                    }
                }
        )
    );

    @FXML
    private Label mainTimerLabel;
    @FXML
    private Label allTimerLabel;
    public Label childTimerLabel1;
    public Label childTimerLabel2;
    public Label childTimerLabel3;
    public Button mainButton;
    public Label statusLabel;
    public Label inDate;
    public Label nowTime;
    public Label nowTimeLabel;


    public Controller() {

    }

    public void button1Click(ActionEvent actionEvent) {
        researchTimer.active();
        restTimer.inactive();
        otherTimer.inactive();

        mainTimer.reset();
        mainTimerLabel.setText(mainTimer.formatPrint()); //0に戻ったことを表示
        statusLabel.setText("研究中");

    }

    public void button2Click(ActionEvent actionEvent) {
        researchTimer.inactive();
        restTimer.active();
        otherTimer.inactive();

        mainTimer.reset();
        mainTimerLabel.setText(mainTimer.formatPrint());
        statusLabel.setText("休憩中");

    }

    public void button3Click(ActionEvent actionEvent) {
        researchTimer.inactive();
        restTimer.inactive();
        otherTimer.active();

        mainTimer.reset();
        mainTimerLabel.setText(mainTimer.formatPrint());
        statusLabel.setText("その他動作中");

    }

    public void mainButtonClick(ActionEvent actionEvent) {
        if (mainTimer.getStatus()){ //タイマーが動作中のクリック

            researchTimer.inactive(); //タイマーがstop時、全部非アクティブ前提
            restTimer.inactive();
            otherTimer.inactive();
            allTimer.inactive();
            mainTimer.inactive();

            timeline.stop();

            mainButton.setText("らぼいん");
            statusLabel.setText("らぼりだ中");
            nowTimeLabel.setText("らぼりだ: ");

            dt = LocalDateTime.now();
            nowTime.setText(dt.format(dtf));


        }else { //タイマーが動作していない
            mainButton.setText("らぼりだ");

            researchTimer.reset();
            restTimer.reset();
            otherTimer.reset();
            allTimer.reset();
            mainTimer.reset();

            dt = LocalDateTime.now();
            inDate.setText(dt.format(dtf));
            nowTimeLabel.setText("現在時刻: ");

            mainTimerLabel.setText(mainTimer.formatPrint()); //0に戻ったことを表示
            allTimerLabel.setText(allTimer.formatPrint());
            childTimerLabel1.setText(researchTimer.formatPrint());
            childTimerLabel2.setText(restTimer.formatPrint());
            childTimerLabel3.setText(otherTimer.formatPrint());

            mainTimer.active();
            allTimer.active();
            button1Click(null); //らぼいん初回は研究中とする

            timeline.setCycleCount(INDEFINITE); //何回繰り返すか指定する
            timeline.play();

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainTimerLabel.setText(mainTimer.formatPrint());
        allTimerLabel.setText(allTimer.formatPrint());
        childTimerLabel1.setText(researchTimer.formatPrint());
        childTimerLabel2.setText(restTimer.formatPrint());
        childTimerLabel3.setText(otherTimer.formatPrint());
        mainButton.setText("らぼいん");
        statusLabel.setText("らぼりだ中");
        nowTimeLabel.setText("らぼりだ: ");
    }

}
