package infrastructure;

import domain.Court;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static infrastructure.StaticCourtRepository.*;

class StaticCourtRepositoryTest {

    private static final String UNKNOWN_COURT = "unknown court";

    @Test
    void should_return_elisabeth_7() {
        StaticCourtRepository repository = new StaticCourtRepository();

        Court expectedCourt = new Court(ELISABETH_7_NAME, ELISABETH_7_ID);

        assertThat(repository.forName(ELISABETH_7_NAME), is(expectedCourt));
    }

    @Test
    void should_throw_runtime_exception() {
        StaticCourtRepository staticCourtRepository = new StaticCourtRepository();

        assertThrows(
                RuntimeException.class,
                () -> {
                    staticCourtRepository.forName(UNKNOWN_COURT);
                }
        );
    }
}