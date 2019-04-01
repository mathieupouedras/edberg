package domain;

public class DummyBooker implements Booker {
    @Override
    public void boo2Hours(Repository repository, CourtRepository courtRepository, RequestService requestServiceMathieu, RequestService requestServiceJulien) {
        System.out.println("dummy bookings successfull");
    }

    @Override
    public boolean loginUsers(Repository repository, RequestService requestServiceMathieu, RequestService requestServiceJulien) {
        System.out.println("dummy users logged in");
        return true;
    }
}
