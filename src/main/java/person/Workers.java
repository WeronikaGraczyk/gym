package person;

import java.io.Serializable;
import java.util.Objects;

public class Workers implements Comparable<Workers>, Serializable {
    private String login;
    private int haslo;

    public Workers(String login, String haslo) {
        this.login = login;
        this.haslo = haslo.hashCode();
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workers workers = (Workers) o;
        return Objects.equals(login, workers.login) && Objects.equals(haslo, workers.haslo);
    }

    @Override
    public int compareTo(Workers o) {
        return this.getLogin().compareTo(o.getLogin());
    }
}