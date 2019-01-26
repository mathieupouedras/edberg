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
        Pair expectedUserAgent = new Pair("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");

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

    @Test
    void should_dirty_test() {
        Repository repository = new PropertiesFileRepository("repository.data");

        System.out.println(repository.getUserMathieu());
        System.out.println(repository.getUserJulien());
        System.out.println(repository.getFormParameterName("form.parameter.member.damien.value"));
        System.out.println(repository.getFormParameterName("form.parameter.member.emmanuel.value"));


        System.out.println(repository.getFormParameterName("form.parameter.timeend.11.value"));
        System.out.println(repository.getFormParameterName("form.parameter.timeend.12.value"));
        System.out.println(repository.getFormParameterName("form.parameter.schedule.value.7"));
        System.out.println("");
        System.out.println(repository.getFormParameterName("form.parameter.timestart.10.value"));
        System.out.println(repository.getFormParameterName("form.parameter.timeend.11.value"));
        System.out.println(repository.getFormParameterName("form.parameter.schedule.value.7"));

    }
}