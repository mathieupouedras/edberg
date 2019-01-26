package domain;

import java.util.Objects;

public class Court {

    private final String name;
    private final String id;

    public Court(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Court{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return name.equals(court.name) &&
                id.equals(court.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
