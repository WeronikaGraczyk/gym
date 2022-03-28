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
    private final JButton logInButton;
    private boolean isOk = false;
    private MainPanel mainPanel;
    private LogWorkers logWorkers = null;
    private String info;

    public LogPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(15, 15, 15, 15);

        constraints.gridx = 0;
        constraints.gridy = 0;

        login = new JLabel("Podaj login: ");
        login.setSize(160, 30);
        newPanel.add(login, constraints);

        constraints.gridx = 1;
        giveLogin = new JTextField(20);
        giveLogin.setToolTipText("Wpisz login!");
        newPanel.add(giveLogin, constraints);
        addKey(giveLogin);

        constraints.gridx = 0;
        constraints.gridy = 1;
        password = new JLabel("Podaj haslo: ", JLabel.CENTER);
        password.setSize(160, 30);
        newPanel.add(password, constraints);

        constraints.gridx = 1;
        givePassword = new JPasswordField(20);
        givePassword.setToolTipText("Wpisz haslo!");
        newPanel.add(givePassword, constraints);
        addKey(givePassword);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        logInButton= new JButton("Zaloguj");
        logInButton.addActionListener(this);
        newPanel.add(logInButton, constraints);

        add(newPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repoInitial();

    }

    private String getLogin() {
        return giveLogin.getText();
    }

    private String getPassword() {
        return String.valueOf(givePassword.getPassword());
    }

    public void repoInitial() {
        try {
            logWorkers = new LogWorkers(
                    new WorkerRepository("WorkersList.dat")
            );
        } catch (ValidationException a) {
            System.out.println(a.getMessage());
        }
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
        if (source == logInButton) {
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