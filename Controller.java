import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private int c = 0;
    private LabT mainTimer = new LabT();
    private LabT allTimer = new LabT();
    private LabT researchTimer = new LabT();
    private LabT restTimer = new LabT();
    private LabT otherTimer = new LabT();

    private LocalDateTime dt;
    private LocalDateTime startTime;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss");
    private DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("uuuu/MM/dd-HH:mm:ss");

    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), //時間経過をトリガにするのはTimelineクラスを使う
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) { //ここに書いた処理がDuration.seconds(1)で示した感覚で実行される
                        c++;
                        if (c > 60){
                            c = 0;
                            timeline.stop();
                            timeline.play();
                        }
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

    private void writeCSV() {
        try {
            String home = System.getProperty("user.home");
            FileWriter f = new FileWriter(home + "/.labtimer_log", true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(f));
            pw.print(startTime.format(dtfYear) + ", ");
            pw.print(LocalDateTime.now().format(dtfYear) + ", ");
            pw.print(startTime.toEpochSecond(OffsetDateTime.now().getOffset()) + ", ");
            pw.print(LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset()) + ", ");
            pw.print(allTimer.getTime() + ", ");
            pw.print(researchTimer.getTime() + ", ");
            pw.print(restTimer.getTime() + ", ");
            pw.println(otherTimer.getTime());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            writeCSV();

            mainButton.setText("らぼいん");
            statusLabel.setText("らぼりだ中");
            nowTimeLabel.setText("らぼりだ");

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
            startTime = dt;
            inDate.setText(dt.format(dtf));
            nowTimeLabel.setText("現在時刻");

            mainTimerLabel.setText(mainTimer.formatPrint()); //0に戻ったことを表示
            allTimerLabel.setText(allTimer.formatPrint());
            childTimerLabel1.setText(researchTimer.formatPrint());
            childTimerLabel2.setText(restTimer.formatPrint());
            childTimerLabel3.setText(otherTimer.formatPrint());

            mainTimer.active();
            allTimer.active();
            button1Click(null); //らぼいん初回は研究中とする

            timeline.setCycleCount(100); //何回繰り返すか指定する
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
        nowTimeLabel.setText("らぼりだ");
    }

}
