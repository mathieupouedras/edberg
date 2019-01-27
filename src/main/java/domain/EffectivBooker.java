package domain;

public class EffectivBooker implements Booker {

    @Override
    public void boo2Hours(Repository repository, CourtRepository courtRepository, RequestService requestServiceMathieu, RequestService requestServiceJulien) {
        Court elisabeth7 = courtRepository.forName("Elisabeth n°7");
        Court elisabeth8 = courtRepository.forName("Elisabeth n°8");
        String date = "2019-02-03";

        Schedule elisabeth7_10h = new Schedule(elisabeth7, "600", 60, 6, "10:00", "11:00");
        Schedule elisabeth7_11h = new Schedule(elisabeth7, "660", 60, 6, "11:00", "12:00");

        Schedule elisabeth8_10h = new Schedule(elisabeth8, "600", 60, 6, "10:00", "11:00");
        Schedule elisabeth8_11h = new Schedule(elisabeth8, "660", 60, 6, "11:00", "12:00");

        boolean bookingSuccessfull1 = book(repository, requestServiceMathieu, elisabeth7, elisabeth7_11h, date,
                repository.getFormParameterName("form.parameter.member.emmanuel.value"));

        if (bookingSuccessfull1) {
            book(repository, requestServiceJulien, elisabeth7, elisabeth7_10h, date,
                    repository.getFormParameterName("form.parameter.member.damien.value"));
        }
        else {
            book(repository, requestServiceJulien, elisabeth8, elisabeth8_10h, date,
                    repository.getFormParameterName("form.parameter.member.damien.value"));

            book(repository, requestServiceMathieu, elisabeth8, elisabeth8_11h, date,
                    repository.getFormParameterName("form.parameter.member.emmanuel.value"));

        }
    }

    private boolean book(Repository repository, RequestService requestService, Court court, Schedule schedule, String date, String partnerId) {
        Response responseSchedule = requestService.chooseSchedule(court, schedule);
        Response responseCreate = requestService.createSchedule(schedule, date,
                partnerId);

        if (!responseCreate.isSuccessFull()) {
            System.err.println("create schedule : " + responseCreate.getBody());
            return false;
        }
        System.out.println("Booking successfull");
        return true;
    }

    @Override
    public boolean loginUsers(Repository repository, RequestService requestServiceMathieu, RequestService requestServiceJulien) {
        requestServiceMathieu.home();
        requestServiceJulien.home();

        Response responseLoginMathieu = requestServiceMathieu.login(repository.getUserMathieu());
        if (!responseLoginMathieu.isSuccessFull()) {
            System.err.println(" login mathieu: " + responseLoginMathieu.getBody());
            return false;
        }
        Response responseLoginJulien = requestServiceJulien.login(repository.getUserJulien());
        if (!responseLoginJulien.isSuccessFull()) {
            System.err.println(" login mathieu: " + responseLoginMathieu.getBody());
            return false;
        }

        return true;
    }

}
