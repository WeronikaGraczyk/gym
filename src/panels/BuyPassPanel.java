package panels;

import modules.Gym;
import person.Client;
import repository.ClientRepository;
import validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuyPassPanel extends JDialog implements ActionListener {
    private final JLabel uniqueNumber;
    private final JLabel howMuchDays;
    private final JTextField giveUniqueNumber;
    private final JTextField giveDays;
    private final JButton buy;
    private boolean isOk = false;
    Gym gym = null;
    private String info;
    private JTextField viewinfo;
    private int days;
    private int index;

    public BuyPassPanel() {
        setSize(200, 215);
        setLayout(null);

        matchTheContent();

        uniqueNumber = new JLabel("Podaj unikalny numer klienta:", JLabel.CENTER);
        uniqueNumber.setBounds(10, 5, 160, 30);
        add(uniqueNumber);

        giveUniqueNumber = new JTextField();
        giveUniqueNumber.setBounds(10, 35, 160, 20);
        giveUniqueNumber.setToolTipText("Wpisz numer!");
        add(giveUniqueNumber);

        howMuchDays = new JLabel("Podaj ilosc dni:", JLabel.CENTER);
        howMuchDays.setBounds(10, 65, 160, 30);
        add(howMuchDays);

        giveDays = new JTextField();
        giveDays.setBounds(10, 95, 160, 20);
        giveDays.setToolTipText("Wpisz ilosc dni!");
        add(giveDays);

        buy = new JButton("zakup");
        buy.setBounds(10, 130, 160, 30);
        add(buy);
        buy.addActionListener(this);


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

    public boolean ValidateMethod() {
        String text = giveDays.getText();
        if (text.length() != 0 && text.matches("\\d+")) {
            days = Integer.parseInt(text);
        }
        String text1 = giveUniqueNumber.getText();
        if (text1.length() != 0 && text1.matches("\\d+")) {
            index = Integer.parseInt(text1);
            return index > 0 && days > 0;
        }
        return false;
    }

    public int getDays() {
        return days;
    }

    public int getIndex() {
        return index;
    }

    public void isOK() {
        List<Client> list = gym.getAll();
        info = "Nie ma takiego numeru karty";
        for (Client c : list) {
            if (getIndex() == c.getClientNumber()) {
                if (gym.getOne(c.getClientNumber()).getDays() != -1) {
                    info = "Karnet jest juz zakupiony";
                } else if (getDays() == 0) {
                    info = "Zła liczba dni";
                } else {
                    isOk = true;
                    gym.getOne(getIndex()).setDays(getDays());
                    Client client = new Client(c.getFirstName(), c.getSecondName(), c.getClientNumber());
                    gym.update(c.getClientNumber(), client);
                    info = "";
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo == buy) {
            if (!ValidateMethod()) {
                String info1 = "Podano błędne dane!";
                JOptionPane.showMessageDialog(viewinfo, info1);
            } else if (ValidateMethod()) {
                isOK();
                if (isOk) {
                    dispose();
                    int i = gym.getOne(getIndex()).getCost();
                    String info1 = "Zakupiono karnet, cena to: " + i + "zł";
                    JOptionPane.showMessageDialog(viewinfo, info1);
                } else {
                    JOptionPane.showMessageDialog(viewinfo, info);
                }
            }
        }
    }
}
