package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JDialog implements ActionListener {
    private final JButton dodajKlienta;
    private final JButton wykupKarnet;
    private final JButton zakupDiety;
    private final JButton administrowanieAplikacja;

    public MainPanel() {
        setSize(615, 500);
        setLayout(null);

        matchTheContent();

        dodajKlienta = new JButton("Dodaj Klienta");
        dodajKlienta.setBounds(60, 40, 160, 160);
        add(dodajKlienta);
        dodajKlienta.addActionListener(this);

        wykupKarnet = new JButton("Wykup karnet");
        wykupKarnet.setBounds(360, 40, 160, 160);
        add(wykupKarnet);
        wykupKarnet.addActionListener(this);

        zakupDiety = new JButton("Zakup diety");
        zakupDiety.setToolTipText(" w budowie");
        zakupDiety.setBackground(Color.LIGHT_GRAY);
        zakupDiety.setBounds(60, 250, 160, 160);
        add(zakupDiety);
        zakupDiety.addActionListener(this);

        administrowanieAplikacja = new JButton("Zarządzanie aplikacją");
        administrowanieAplikacja.setToolTipText(" w budowie");
        administrowanieAplikacja.setBackground(Color.LIGHT_GRAY);
        administrowanieAplikacja.setBounds(360, 250, 160, 160);
        add(administrowanieAplikacja);
        administrowanieAplikacja.addActionListener(this);
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
        Object zrodlo = e.getSource();
        if (zrodlo == dodajKlienta) {
            AddClientPanel client = new AddClientPanel();
        } else if (zrodlo == wykupKarnet) {
            BuyPassPanel buy = new BuyPassPanel();
        }
    }
}
