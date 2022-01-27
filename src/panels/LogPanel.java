package panels;
import modules.LogWorkers;
import person.Workers;
import repository.WorkerRepository;
import validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LogPanel extends JDialog implements ActionListener {
    private final JLabel login;
    private final JLabel haslo;
    private final JTextField podanyLogin;
    private final JTextField podanehaslo;
    private JTextField viewinfo;
    private final JButton zaloguj;
    private boolean isOk = false;
    private MainPanel mainPanel;
    private LogWorkers logWorkers = null;
    private String info;

    public LogPanel() {

        setSize(200, 215);
        setLayout(null);

        matchTheContent();

        login = new JLabel("Podaj login: ", JLabel.CENTER);
        login.setBounds(10, 5, 160, 30);
        add(login);

        podanyLogin = new JTextField();
        podanyLogin.setBounds(10, 30, 160, 20);
        podanyLogin.setToolTipText("Wpisz login!");
        add(podanyLogin);

        haslo = new JLabel("Podaj haslo: ", JLabel.CENTER);
        haslo.setBounds(10, 50, 160, 30);
        add(haslo);

        podanehaslo = new JTextField();
        podanehaslo.setBounds(10, 75, 160, 20);
        podanehaslo.setToolTipText("Wpisz haslo!");
        add(podanehaslo);

        zaloguj = new JButton("zaloguj");
        zaloguj.setBounds(10, 130, 160, 30);
        add(zaloguj);
        zaloguj.addActionListener(this);

        try {
            logWorkers = new LogWorkers(
                    new WorkerRepository("WorkersList.dat")
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

    public String getLogin() {
        return podanyLogin.getText();
    }

    public String getHaslo() {
        return podanehaslo.getText();
    }

    public void isOK() {
        List<Workers> list = logWorkers.getAll();
        info = "Błędny login lub hasło";
        for (Workers w : list) {
            if (w.equals(new Workers(getLogin(), getHaslo()))) {
                isOk = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo == zaloguj) {
            isOK();
            if (isOk) {
                dispose();
                mainPanel = new MainPanel();
            }else{
                JOptionPane.showMessageDialog(viewinfo,"Błędne dane logowania");
            }
        }
    }
}