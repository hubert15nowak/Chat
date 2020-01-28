package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class ChatView {
    private JPanel panel;
    private JTextField textField;
    private JButton sendButton;
    private JButton backButton;
    private JTextPane textPane;
    private JList list;
    private JButton createRoomButton;
    private JLabel name;
    private MainPanel mainPanel;
    private String lastRoom;
    private final String NO_ROOM = "no room selected";

    public ChatView(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        name.setText(NO_ROOM);

        textPane.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                textPane.setEditable(false);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                textPane.setEditable(true);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name.setText(NO_ROOM);
                mainPanel.leave();
                list.clearSelection();
                lastRoom = NO_ROOM;
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!name.getText().equals(NO_ROOM)) {
                    if (textField.getText() != "") {
                        mainPanel.sendMessage(textField.getText());
                        newRoomMessage("You: " + textField.getText());
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please enter room first");
                }
            }
        });
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String roomName = JOptionPane.showInputDialog(panel, "Enter room name", "Create new room",JOptionPane.QUESTION_MESSAGE);
                if(roomName.length()>=3) mainPanel.create(roomName);
                else JOptionPane.showMessageDialog(panel, "Enter at least 3 letters");

            }
        });
    }

    public void setRoomsList(ArrayList<String> rooms) {

        list.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return rooms.size();
            }

            @Override
            public Object getElementAt(int i) {
                return rooms.get(i);
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if(evt!= null && !list.getSelectedValue().equals(lastRoom)) {
                    lastRoom = (String)list.getSelectedValue();
                    name.setText(lastRoom);
                    mainPanel.enter(lastRoom);
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public void newRoomMessage(String s) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), "\n\r"+s, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
