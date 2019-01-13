package domain;

import java.util.ArrayList;
import java.util.List;

public class ExecuteScheduleFormParameters {
    private List<Pair> parameters;

    private ExecuteScheduleFormParameters() {

    }

    static class Builder {
        private final Repository repository;
        private final List<Pair> parameters;

        Builder(Repository repository) {
            this.repository = repository;
            parameters = new ArrayList<>();
        }

        Builder member(String memberId) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.member.name"), memberId));
            return this;
        }

        Builder date(String date) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.default_date.name"), date));
            return this;
        }

        Builder timeStart(String timeStart) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.default_timestart.name"), timeStart));
            return this;
        }

        Builder timeEnd(String timeEnd) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.timeend.name"), timeEnd));
            return this;
        }

        Builder schedule(String schedule) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.default_schedule.name"), schedule));
            return this;
        }

        Builder csrf(String csrf) {
            parameters.add(new Pair(repository.getFormParameterName("form.parameter.csrf.reservation.name"), csrf));
            return this;
        }


        ExecuteScheduleFormParameters build() {
            ExecuteScheduleFormParameters formParameters = new ExecuteScheduleFormParameters();

            parameters.add(new Pair(repository.getFormParameterName("form.parameter.action.name"),
                    repository.getFormParameterName("form.parameter.action.value")));

            parameters.add(new Pair(repository.getFormParameterName("form.parameter.choice.name"),
                    repository.getFormParameterName("form.parameter.choice.value")));

            parameters.add(new Pair(repository.getFormParameterName("form.parameter.default_duration.name"),
                    repository.getFormParameterName("form.parameter.default_duration.value")));

            parameters.add(new Pair(repository.getFormParameterName("form.parameter.row.name"),
                    repository.getFormParameterName("form.parameter.row.value")));

            parameters.add(new Pair(repository.getFormParameterName("form.parameter.request.name"),
                    repository.getFormParameterName("form.parameter.request.value")));


            formParameters.setParameters(this.parameters);
            return formParameters;
        }
    }

    public void setParameters(List<Pair> parameters) {
        this.parameters = parameters;
    }

    public List<Pair> getParameters() {
        return parameters;
    }



}



