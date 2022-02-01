package panels;

import modules.Gym;
import person.Client;
import repository.ClientRepository;
import validation.ValidationException;

import javax.swing.*;
import java.awt.*;

public class ClientListPanel extends JDialog {
    Gym gym = null;
    private final JTextField giveClientList;

    public ClientListPanel() {
        setSize(500, 515);
        setLayout(null);
        setTitle("Lista klientów ");

        giveClientList = new JTextField("Lista Klientów: ");
        add(giveClientList);
        try {
            gym = new Gym(
                    new ClientRepository("ClientList.dat")
            );
        } catch (ValidationException a) {
            System.out.println(a.getMessage());
        }
        makeJList();
        matchTheContent();

    }

    private DefaultListModel<String> makeAModelList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 1; i < gym.getAll().size() + 1; i++) {
            Client temp = gym.getOne(i);
            StringBuilder sb = new StringBuilder();
            if (temp.getDays() > 0) {
                sb.append("Numer: " + temp.getClientNumber() + " " + temp.getFirstName() + " " + temp.getSecondName() + " dni: " + temp.getDays());
            } else {
                sb.append("Numer: " + temp.getClientNumber() + " " + temp.getFirstName() + " " + temp.getSecondName() + " dni: nie zakupiono");
            }
            listModel.addElement(sb.toString());
        }
        return listModel;
    }


    private void makeJList() {
        JList lista = new JList(makeAModelList());
        lista.setSize(500, 515);
        lista.setFont(new Font("monospace", Font.PLAIN, 15));
        add(lista);
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
}