package domain;

public interface RequestService {

    String home();
    String login(String csrf, String cookieValue, Pair user);
    String chooseSchedule(String cookieValue, Pair date, Pair schedule, Pair timestart, Pair duration);
    String chooseSchedule(String cookieValue, Pair schedule, Pair timestart);
    String createSchedule(String cookieValue, ExecuteScheduleFormParameters formParameters);
}
