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

public class AddNewWorker extends JDialog implements ActionListener {
    private final JLabel login;
    private final JLabel password;
    private final JLabel confirmPassword;
    private final JTextField giveLogin;
    private final JPasswordField givePassword;
    private final JPasswordField giveConfirmPassword;
    private JTextField viewinfo;
    private final JButton newWorker;
    private boolean isOk = false;
    private LogWorkers logWorkers = null;
    private String info;

    public AddNewWorker() {
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
        confirmPassword = new JLabel("Zatwierdź hasło: ", JLabel.CENTER);
        confirmPassword.setSize(160, 30);
        newPanel.add(confirmPassword, constraints);


        constraints.gridx = 1;
        giveConfirmPassword = new JPasswordField(20);
        newPanel.add(giveConfirmPassword, constraints);
        addKey(giveConfirmPassword);
        setLocationRelativeTo(null);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newWorker = new JButton("Dodaj");
        newWorker.addActionListener(this);
        newPanel.add(newWorker, constraints);

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

    private String getConfirmPassword() {
        return String.valueOf(giveConfirmPassword.getPassword());
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

    private void addKey(JTextField jTextField) {
        jTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isOk();
                    if (isOk) {
                        dispose();
                        JOptionPane.showMessageDialog(viewinfo, "Dodano nowego pracownika");
                    } else {
                        JOptionPane.showMessageDialog(viewinfo, info);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void isOk() {
        isOk=true;
        if(getConfirmPassword().length()==0 || getPassword().length()==0||getLogin().length()==0) {
            info = "puste pole";
            isOk = false;
        }else if (!getConfirmPassword().equals(getPassword())) {
            info = "hasła sie nie zgadzają";
            isOk = false;
        } else {
            List<Workers> list = logWorkers.getAll();
            for (Workers w : list) {
                if ((w.getLogin()).equals(getLogin())) {
                    isOk = false;
                    info="Podany login istnieje";
                    break;
                }
            }
        }
    }

    public void addNewWorker(){
        logWorkers.add(new Workers(getLogin(),getPassword()));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newWorker) {
            isOk();
            if (isOk) {
                dispose();
                addNewWorker();
                JOptionPane.showMessageDialog(viewinfo, "Dodano nowego pracownika");
            } else {
                JOptionPane.showMessageDialog(viewinfo, info);
            }
        }

    }
}
