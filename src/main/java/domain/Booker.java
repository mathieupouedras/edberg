package domain;

public interface Booker {

    void boo2Hours(Repository repository, CourtRepository courtRepository, RequestService requestServiceMathieu, RequestService requestServiceJulien);
    boolean loginUsers(Repository repository, RequestService requestServiceMathieu, RequestService requestServiceJulien);

}
