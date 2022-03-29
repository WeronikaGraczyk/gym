package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JDialog implements ActionListener {
    private final JButton addClient;
    private final JButton buyPass;
    private final JButton clientList;
    private final JButton appManagement;

    public MainPanel() {
        setSize(615, 500);
        setLayout(new GridLayout(2,2));

        matchTheContent();

        addClient = new JButton("Dodaj Klienta");
        addClient.setBounds(60, 40, 160, 160);
        add(addClient);
        addClient.addActionListener(this);

        buyPass = new JButton("Wykup karnet");
        buyPass.setBounds(360, 40, 160, 160);
        add(buyPass);
        buyPass.addActionListener(this);

        clientList = new JButton("Lista klientów");
        clientList.setBounds(60, 250, 160, 160);
        add(clientList);
        clientList.addActionListener(this);

        appManagement = new JButton("Zarządzanie aplikacją");
        appManagement.setBounds(360, 250, 160, 160);
        add(appManagement);
        appManagement.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addClient) {
            AddClientPanel client = new AddClientPanel();
        } else if (source == buyPass) {
            BuyPassPanel buy = new BuyPassPanel();
        } else if (source == clientList) {
            ClientListPanel panel = new ClientListPanel();
        }else if(source==appManagement){
            ManagementPanel m=new ManagementPanel();
        }
    }
}