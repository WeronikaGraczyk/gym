package repository;

import person.Client;
import validation.ValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;

public class ClientRepository implements RepositoryInterface<Client>, Serializable {
    protected LinkedHashSet<Client> data;
    protected String fileName;

    public static class IORepositoryException extends RuntimeException {
        public IORepositoryException(String message) {
            super(message);
        }
    }

    static class Error {
        public static final String FILE_NAME = "Brak nazwy pliku dla repozytorium";
        public static final String IN_EXCEPTION = "Bład odczytu danych z pliku";
        public static final String OUT_EXCEPTION = "Bład zapisu danych do pliku";
        public static final String CLASS_NOT_FOUND_EXCEPTION = "Błedny format danych w pliku";
    }

    public ClientRepository(String fileName) throws ValidationException {
        if (fileName == null || fileName.length() == 0) {
            throw new ValidationException(Error.FILE_NAME);
        }
        this.fileName = fileName;
        try {
            read();
        } catch (IORepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void read() throws IORepositoryException {
        data = new LinkedHashSet<>();
        try {
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(fileName));
            data = (LinkedHashSet<Client>) input.readObject();
            input.close();
        } catch (IOException e) {
            throw new IORepositoryException(Error.IN_EXCEPTION);
        } catch (ClassNotFoundException e) {
            throw new IORepositoryException(Error.CLASS_NOT_FOUND_EXCEPTION);
        }
    }

    public void save() throws IORepositoryException {
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(fileName)
            );
            output.writeObject(data);
            output.close();
        } catch (IOException e) {
            throw new IORepositoryException(Error.OUT_EXCEPTION);
        }

    }

    @Override
    public boolean add(Client object) {
        boolean result = data.add(object);
        if (result) {
            try {
                save();
            } catch (IORepositoryException e) {
                return false;
            }
        }
        return result;
    }

    @Override
    public boolean update(Client oldValue, Client newValue) {
        for (Client client : data) {
            if (client.equals(oldValue)) {
                client = newValue;
                try {
                    save();
                } catch (IORepositoryException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Client> getAll() {
        return List.copyOf(data);
    }

    @Override
    public List<Client> filter(Predicate<Client> predicate) {
        List<Client> result = new ArrayList<>();
        for (Client client : data) {
            if (predicate.test(client)) {
                result.add(client);
            }
        }
        return result;
    }

    @Override
    public boolean remove(Client object) {
        boolean result = data.removeIf(Client -> Client.equals(object));
        if (result) {
            try {
                save();
            } catch (IORepositoryException e) {
                return false;
            }
        }
        return result;
    }

}