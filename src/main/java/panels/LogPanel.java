package panels;

import modules.LogWorkers;
import person.Workers;
import repository.WorkerRepository;
import validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class LogPanel extends JDialog implements ActionListener{
    private final JLabel login;
    private final JLabel password;
    private final JTextField giveLogin;
    private final JPasswordField givePassword;
    private JTextField viewinfo;
    private final JButton logIn;
    private boolean isOk = false;
    private MainPanel mainPanel;
    private LogWorkers logWorkers = null;
    private String info;

    public LogPanel() {
        matchTheContent();

        login = new JLabel("Podaj login: ", JLabel.CENTER);
        login.setBounds(10, 5, 160, 30);
        add(login);

        giveLogin = new JTextField();
        giveLogin.setBounds(10, 30, 160, 20);
        giveLogin.setToolTipText("Wpisz login!");
        add(giveLogin);
        addKey(giveLogin);

        password = new JLabel("Podaj haslo: ", JLabel.CENTER);
        password.setBounds(10, 50, 160, 30);
        add(password);

        givePassword = new JPasswordField();
        givePassword.setBounds(10, 75, 160, 20);
        givePassword.setToolTipText("Wpisz haslo!");
        add(givePassword);
        addKey(givePassword);

        logIn = new JButton("zaloguj");
        logIn.setBounds(10, 110, 160, 50);
        add(logIn);
        logIn.addActionListener(this);

        try {
            logWorkers = new LogWorkers(
                    new WorkerRepository("WorkersList.dat")

            );
        } catch (ValidationException a) {
            System.out.println(a.getMessage());
        }
        //       logWorkers.add(new Workers("admin","admin"));
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
        setSize(200, 215);
        setLayout(null);
    }

    private String getLogin() {
        return giveLogin.getText();
    }

    private String getPassword() {
        return String.valueOf(givePassword.getPassword());
    }

    private void isOK() {
        List<Workers> list = logWorkers.getAll();
        info = "Błędny login lub hasło";
        for (Workers w : list) {
            if (w.equals(new Workers(getLogin(), getPassword()))) {
                isOk = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == logIn) {
            isOK();
            if (isOk) {
                dispose();
                mainPanel = new MainPanel();
            } else {
                JOptionPane.showMessageDialog(viewinfo, "Błędne dane logowania");
            }
        }
    }

    private void addKey(JTextField jTextField) {
        jTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isOK();
                    if (isOk) {
                        dispose();
                        mainPanel = new MainPanel();
                    } else {
                        JOptionPane.showMessageDialog(viewinfo, "Błędne dane logowania");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}