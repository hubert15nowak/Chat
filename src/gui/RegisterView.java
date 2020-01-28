package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView {
    private JTextField loginField;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JButton backButton;
    private JPasswordField passwordField2;
    private JPanel panel;
    private MainPanel mainPanel;

    public RegisterView(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!checkPassword()) {
                    JOptionPane.showMessageDialog(panel, "Passwords are not the same");
                } else if(!checkLogin())  {
                    JOptionPane.showMessageDialog(panel, "Login must have at least 3 letters");
                } else {
                    register(loginField.getText(),(passwordField1.getText()));
                }

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.switchToLogin();
            }
        });
    }

    private void register(String username, String password) {
        mainPanel.register(username, password);
    }

    private boolean checkLogin() {
        if(loginField.getText().length()<3) return false;
        return true;
    }

    private boolean checkPassword() {
        if(passwordField1.getText()=="") return false;
        return passwordField1.getText().equals(passwordField2.getText());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("asd");


        frame.setMinimumSize(new Dimension(800,600));
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JPanel getPanel() {
        return panel;
    }
}
