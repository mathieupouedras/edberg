package infrastructure;

import domain.Pair;
import domain.Repository;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertiesFileRepositoryTest {

    @Test
    void should_retrieve_useragent() {
        Repository repository = new PropertiesFileRepository("repository.data");
        Pair expectedUserAgent = new Pair("useragent", "A user agent");

        assertThat(repository.getUserAgent(), is(expectedUserAgent));
    }

    @Test
    void should_not_find_property_file() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    Repository repository = new PropertiesFileRepository("unknown_file");
                    repository.getUserAgent();
                });
    }

    @Test
    void should_return_form_parameters_for_bookings() {
        Repository repository = new PropertiesFileRepository("repository.data");

        System.out.println(repository.getDefaultDate());
        System.out.println(repository.getSchedule7());
        System.out.println(repository.getSchedule8());
        System.out.println(repository.getTimeStart10());
        System.out.println(repository.getTimeStart11());
        System.out.println(repository.getDefaultDuration());
    }
}