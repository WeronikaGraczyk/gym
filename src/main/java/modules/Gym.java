package modules;


import person.Client;
import repository.RepositoryInterface;
import validation.ValidationException;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;

public class Gym {
    protected final RepositoryInterface<Client> repository;

    public static class Error {
        public final static String REPOSITORY_MESSAGE = "Należy wskazać repozytorium";
        public final static String PERSON_EXIST = "Taka osoba istnieje";
    }

    public Gym(RepositoryInterface<Client> repository) throws ValidationException {
        if (repository == null) {
            throw new ValidationException(Error.REPOSITORY_MESSAGE);
        }
        this.repository = repository;
    }
    public boolean add(Client team) {
        if (getOne(team.getClientNumber()) != null) throw new ValidationException(Error.PERSON_EXIST);
        return repository.add(team);
    }

    public List<Client> getAll() {
        return repository.getAll();
    }

    public List<Client> getSortedByIndex() {
        TreeSet<Client> sortedResult = new TreeSet<>(
                Comparator.naturalOrder()
        );
        sortedResult.addAll(repository.getAll());
        return List.copyOf(sortedResult);
    }


    public Client getOne(int number) {
        Predicate<Client> predicate = client -> client.getClientNumber()==number;
        for (Client client : repository.getAll()) {
            if (predicate.test(client)) {
                return client;
            }
        }
        return null;
    }

    public boolean update(int number, Client newValue) {
        Client client = getOne(number);
        if (client == null) {
            return false;
        }
        return repository.update(client, newValue);
    }

    public boolean remove(int number) {
        Client client = getOne(number);
        if (client == null) {
            return false;
        }
        return repository.remove(client);
    }

    public Client getLastClient(){
        Client last=new Client("Klient","Zero",0);
        for(Client c:getAll()){
            if(last.getClientNumber()<c.getClientNumber())
                last=c;
        }
        return last;
    }
}