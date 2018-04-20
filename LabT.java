class LabT {
    private long time = 0;
    private boolean status = false;

    void timeCount(){
        if (status) time++;
    }
    void reset(){
        time = 0;
    }
    long getTime(){
        return time;
    }
    boolean getStatus() {
        return status;
    }
    void active(){
        status = true;
    }
    void inactive(){
        status = false;
    }
    String formatPrint(){
        return String.format("%02d:%02d:%02d", time/3600, time/60, time%60);
    }
}
