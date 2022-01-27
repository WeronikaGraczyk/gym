package modules;


import person.Workers;
import repository.RepositoryInterface;
import validation.ValidationException;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;

public class LogWorkers {
    protected final RepositoryInterface<Workers> repository;

    public static class Error {
        public final static String REPOSITORY_MESSAGE = "Należy wskazać repozytorium";
        public final static String PERSON_EXIST = "Taka osoba istnieje";
    }

    public LogWorkers(RepositoryInterface<Workers> repository) throws ValidationException {
        if (repository == null) {
            throw new ValidationException(Error.REPOSITORY_MESSAGE);
        }
        this.repository = repository;
    }

    public boolean add(Workers person) {
        if (getOne(person.getLogin()) != null) throw new ValidationException(Error.PERSON_EXIST);
        return repository.add(person);
    }

    public List<Workers> getAll() {
        return repository.getAll();
    }

    public List<Workers> getSortedByLogin() {
        TreeSet<Workers> sortedResult = new TreeSet<>(
                Comparator.naturalOrder()
        );
        sortedResult.addAll(repository.getAll());
        return List.copyOf(sortedResult);
    }


    public Workers getOne(String login) {
        Predicate<Workers> predicate = person -> person.getLogin().equals(login);
        for (Workers workers : repository.getAll()) {
            if (predicate.test(workers)) {
                return workers;
            }
        }
        return null;
    }

    public boolean update(String login, Workers newValue) {
        Workers workers = getOne(login);
        if (workers == null) {
            return false;
        }
        return repository.update(workers, newValue);
    }

    public boolean remove(String login) {
        Workers workers = getOne(login);
        if (workers == null) {
            return false;
        }
        return repository.remove(workers);
    }

}