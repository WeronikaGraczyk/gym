package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementPanel extends JDialog implements ActionListener {
    private final JButton removeClient;
    private final JButton addNewWorker;

    public ManagementPanel() {
        setSize(615, 300);
        setLayout(null);
        matchTheContent();

        removeClient = new JButton("Usuń Klienta");
        removeClient.setBounds(60, 40, 160, 160);
        add(removeClient);
        removeClient.addActionListener(this);

        addNewWorker = new JButton("Dodaj nowego pracownika");
        addNewWorker.setBounds(360, 40, 160, 160);
        add(addNewWorker);
        addNewWorker.addActionListener(this);
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
        if (source == removeClient) {
            //new remove client panel
        } else if (source == addNewWorker) {
            AddNewWorker a = new AddNewWorker();
        }
    }
}
