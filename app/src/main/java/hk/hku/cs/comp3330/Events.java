package hk.hku.cs.comp3330;

public class Events {
    String EVENT, TIME, MONTH, DATE, YEAR;

    public Events(String EVENT, String TIME, String MONTH, String DATE, String YEAR) {
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.MONTH = MONTH;
        this.DATE = DATE;
        this.YEAR = YEAR;
    }

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }
}
