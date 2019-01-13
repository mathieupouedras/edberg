package infrastructure;

import domain.Pair;
import domain.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileRepository implements Repository {

    private final String fileName;
    private Properties properties;

    public PropertiesFileRepository(String fileName) {
        this.fileName = fileName;
        properties = loadProperties();
    }

    @Override
    public Pair getUserAgent() {
        return new Pair(properties.getProperty("header.useragent.name"), properties.getProperty("header.useragent.value"));
    }
    public Pair getCookie() {
        return new Pair("header.cookie.name");
    }

    @Override
    public String getUrl(String name) {
        return properties.getProperty(name);
    }

    @Override
    public String getCookieParameterName(String name) {
        return properties.getProperty(name);
    }

    @Override
    public String getCsrfHidenInputElementId() {
        return properties.getProperty("csrf.hiden.input.element.id");
    }

    @Override
    public String getCsrfHidenInputElementAttributeName() {
        return properties.getProperty("csrf.hiden.input.element.attributeName");
    }

    @Override
    public String getBookCsrfHidenInputElementId() {
        return properties.getProperty("csrf.hiden.input.book.element.id");
    }

    @Override
    public String getBookCsrfHidenInputElementAttributeName() {
        return properties.getProperty("csrf.hiden.input.book.element.attributeName");
    }

    @Override
    public String getFormParameterName(String name) {
        return properties.getProperty(name);
    }

    @Override
    public Pair getUserMathieu() {
        return new Pair(properties.getProperty("user.mathieu.login"), properties.getProperty("user.mathieu.password"));
    }

    @Override
    public Pair getDefaultDate() {
        return new Pair(properties.getProperty("form.parameter.date.name"), properties.getProperty("form.parameter.date.value"));
    }

    @Override
    public Pair getSchedule7() {
        return new Pair(properties.getProperty("form.parameter.schedule.name"), properties.getProperty("form.parameter.schedule.value.7"));
    }

    @Override
    public Pair getSchedule8() {
        return new Pair(properties.getProperty("form.parameter.schedule.name"), properties.getProperty("form.parameter.schedule.value.8"));
    }

    @Override
    public Pair getTimeStart10() {
        return new Pair(properties.getProperty("form.parameter.timestart.name"), properties.getProperty("form.parameter.timestart.value.10"));
    }

    @Override
    public Pair getTimeStart11() {
        return new Pair(properties.getProperty("form.parameter.timestart.name"), properties.getProperty("form.parameter.timestart.value.11"));
    }

    @Override
    public Pair getDefaultDuration() {
        return new Pair(properties.getProperty("form.parameter.duration.name"), properties.getProperty("form.parameter.duration.value"));
    }

    private Properties loadProperties() {
        try(InputStream resourceAsStream = PropertiesFileRepository.class.getClassLoader().getResourceAsStream(fileName)) {
            if (resourceAsStream == null) {
                throw new RuntimeException("File not found : " + fileName);
            }
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
