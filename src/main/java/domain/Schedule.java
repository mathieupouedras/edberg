package domain;

public class Schedule {
    private static final int DEFAULT_DURATION = 60;
    private static final int TODAY = 0;
    private final Court court;
    private final String startTimeId;
    private final int duration;
    private final int dayNumber;
    private final String startTime;
    private final String endTime;

    public Schedule(Court court, String startTimeId, int duration, int dayNumber, String startTime, String endTime) {
        this.court = court;
        this.startTimeId = startTimeId;
        this.duration = duration;
        this.dayNumber = dayNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule defaultScheduleOnSundayWithduration60(Court court, String startTimeId, String startTime, String endTime) {
        return new Schedule(court, startTimeId, DEFAULT_DURATION, TODAY, startTime, endTime);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Court getCourt() {
        return court;
    }

    public String getStartTimeId() {
        return startTimeId;
    }

    public int getDuration() {
        return duration;
    }

    public int getDayNumber() {
        return dayNumber;
    }
}
