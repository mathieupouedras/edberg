package domain;

import infrastructure.PropertiesFileRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteScheduleFormParametersTest {

    @Test
    void should() {
        Repository repository = new PropertiesFileRepository("repository.data");
        ExecuteScheduleFormParameters executeScheduleFormParameters = new ExecuteScheduleFormParameters.Builder(repository)
                .member(repository.getFormParameterName("form.parameter.member.emmanuel.value"))
                .date("2019-01-16")
                .timeStart("17:00")
                .timeEnd("18:00")
                .schedule("34303")
                .csrf("my csrf")
                .build();

        System.out.println(executeScheduleFormParameters.getParameters());
    }

}