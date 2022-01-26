package panels;

import modules.Gym;
import person.Client;
import repository.ClientRepository;
import validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientPanel extends JDialog implements ActionListener {
    private final JLabel firstName;
    private final JLabel lastName;
    private final JTextField giveFirstName;
    private final JTextField giveLastName;
    private final JButton addClient;
    Gym gym = null;


    public AddClientPanel() {
        setSize(200, 215);
        setLayout(null);

        matchTheContent();

        firstName = new JLabel("Podaj imie:", JLabel.CENTER);
        firstName.setBounds(10, 5, 160, 30);
        add(firstName);

        giveFirstName = new JTextField();
        giveFirstName.setBounds(10, 35, 160, 20);
        giveFirstName.setToolTipText("Wpisz imie!");
        add(giveFirstName);

        lastName = new JLabel("Podaj nazwisko:", JLabel.CENTER);
        lastName.setBounds(10, 65, 160, 30);
        add(lastName);

        giveLastName = new JTextField();
        giveLastName.setBounds(10, 95, 160, 20);
        giveLastName.setToolTipText("Wpisz nazwisko!");
        add(giveLastName);

        addClient = new JButton("Dodaj klienta");
        addClient.setBounds(10, 130, 160, 30);
        add(addClient);
        addClient.addActionListener(this);

        try {
            gym = new Gym(
                    new ClientRepository("ClientList.dat")
            );
        } catch (ValidationException a) {
            System.out.println(a.getMessage());
        }
    }


    private void matchTheContent() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int szer = getSize().width;
        int wys = getSize().height;
        int loks = (dim.width - szer) / 2;
        int lokw = (dim.height - wys) / 2;
        setLocation(loks, lokw);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public String getLastName() {
        return giveLastName.getText();
    }

    public String getFirstName() {
        return giveFirstName.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object zrodlo = e.getSource();
        if (zrodlo == addClient) {
            dispose();
            Client client = new Client(getFirstName(), getLastName(), gym.getLastClient().getClientNumber() + 1);
            gym.add(client);

            System.out.println(gym.getAll());
        }
    }
}