package infrastructure;

import domain.Court;
import domain.CourtRepository;

public class StaticCourtRepository implements CourtRepository {

    public static final String ELISABETH_5_ID = "34304";
    public static final String ELISABETH_7_ID = "34305";
    public static final String ELISABETH_8_ID = "34306";
    public static final String MOUCHOTTE_ID = "34303";


    public static final String ELISABETH_5_NAME = "Elisabeth n°5";
    public static final String ELISABETH_7_NAME = "Elisabeth n°7";
    public static final String ELISABETH_8_NAME = "Elisabeth n°8";
    public static final String MOUCHOTTE_NAME = "Mouchotte";

    @Override
    public Court forName(String name) {
        switch(name) {
            case ELISABETH_5_NAME: return new Court(name, ELISABETH_5_ID);
            case ELISABETH_7_NAME: return new Court(name, ELISABETH_7_ID);
            case ELISABETH_8_NAME: return new Court(name, ELISABETH_8_ID);
            case MOUCHOTTE_NAME: return new Court(name, MOUCHOTTE_ID);
            default: throw new RuntimeException("No court for name :" + name);
        }
    }
}
