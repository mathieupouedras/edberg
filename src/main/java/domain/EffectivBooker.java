package domain;

public class EffectivBooker implements Booker {

    @Override
    public void boo2Hours(Repository repository, CourtRepository courtRepository, RequestService requestServiceMathieu, RequestService requestServiceJulien) {
        Court mouchotte = courtRepository.forName("Mouchotte");
        Schedule mouchotte17 = new Schedule(mouchotte, "1020", 60, 3, "17:00", "18:00");
        String date = "2019-01-30";

        boolean bookingSuccessfull1 = book(repository, requestServiceMathieu, mouchotte, mouchotte17, date,
                repository.getFormParameterName("form.parameter.member.emmanuel.value"));

        if (bookingSuccessfull1) {
            Schedule mouchotte18 = new Schedule(mouchotte, "1080", 60, 3, "18:00", "19:00");
            book(repository, requestServiceJulien, mouchotte, mouchotte18, date,
                    repository.getFormParameterName("form.parameter.member.damien.value"));
        }
        else {
            Schedule mouchotte19 = new Schedule(mouchotte, "1140", 60, 3, "19:00", "20:00");
            book(repository, requestServiceJulien, mouchotte, mouchotte19, date,
                    repository.getFormParameterName("form.parameter.member.damien.value"));

        }
    }

    private boolean book(Repository repository, RequestService requestServiceMathieu, Court mouchotte, Schedule mouchotte17, String date, String partnerId) {
        Response responseSchedule = requestServiceMathieu.chooseSchedule(mouchotte, mouchotte17);
        Response responseCreate = requestServiceMathieu.createSchedule(mouchotte17, date,
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
