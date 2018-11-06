import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class LabT {
    private long time = 0;
    private long elapsedTime = 0;
    private boolean status = false;
    private LocalDateTime startTime;

    LabT(){
        startTime = LocalDateTime.now();
    }

    void timeCount(){
        if (status) time = ChronoUnit.SECONDS.between(startTime, LocalDateTime.now());
    }
    void reset(){
        elapsedTime = 0;
        startTime = LocalDateTime.now();
    }
    long getTime(){
        return elapsedTime;
    }
    boolean getStatus() {
        return status;
    }
    void active(){
        inactive();
        status = true;
        startTime = LocalDateTime.now();
    }
    void inactive(){
        status = false;
        elapsedTime += time;
        time = 0;
    }
    String formatPrint(){
        return String.format("%02d:%02d:%02d", (elapsedTime+time)/3600%60, (elapsedTime+time)/60%60, (elapsedTime+time)%60);
    }
}
