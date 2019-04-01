package domain;

public interface RequestService {

    void home();
    public domain.Response login(Pair user);
    Response chooseSchedule(Court court, Schedule schedule);
    Response createSchedule(Schedule schedule, String date, String partnerId);
}
