package ro.pub.cs.systems.eim.practicaltest02.model;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;

public class WeatherForecastInformation {

    private String val;
    private int minute;

    public WeatherForecastInformation() {
        this.val = null;
        this.minute = -1;
    }

    public WeatherForecastInformation(
            String val,
            int minute) {
        this.val = val;
        this.minute = minute;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return this.minute;
    }


    @Override
    public String toString() {
        return this.val + "\n";
    }

}
