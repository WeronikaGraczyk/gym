package person;

import java.io.Serializable;

public class Client implements Comparable<Client>, Serializable {
    private final String firstName;
    private final String secondName;
    private final int clientNumber;
    private int days = -1;
    private final int COSTPERDAY = 4;
    private int cost = 0;

    public Client(String firstName, String secondName, int number) {
        StringValidator(firstName);
        StringValidator(secondName);
        this.firstName = firstName;
        this.secondName = secondName;
        this.clientNumber = number;
    }

    public void StringValidator(String word) {
        if (word == null || word.length() == 0) {
            throw new IllegalArgumentException("Podano złe dane");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    @Override
    public String toString() {
        return "Client: " + firstName + " " + secondName + ", clientNumber: " + clientNumber + " days " + getDays();
    }

    @Override
    public int compareTo(Client o) {
        return getClientNumber() - (o.getClientNumber());
    }

    public int getCost() {
        return cost;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
        this.cost = days * COSTPERDAY;
    }
}