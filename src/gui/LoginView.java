package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    private JButton loginButton;
    private JTextField loginField;
    private JButton registerButton;
    private JPanel panel;
    private JPasswordField passwordField;
    private MainPanel mainPanel;

    public LoginView(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.switchToRegister();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(loginField.getText()!="" && passwordField.getText()!="") {
                    login(loginField.getText(), passwordField.getText());
                } else {
                    JOptionPane.showMessageDialog(panel, "fill login and password");
                }
            }
        });
    }

    private void login(String username, String password) {
        mainPanel.login(username,password);
    }

    public JPanel getPanel() {
        return panel;
    }
}
