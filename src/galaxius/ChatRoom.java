/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxius;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author xalgra
 */
public class ChatRoom extends JPanel{
    private JTextField enterField;
    private JTextArea displayArea;
    private Client client;
    
    public ChatRoom(Client newclient)
    {
        client = newclient;
        this.setLayout( new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        enterField = new JTextField();
        enterField.setEnabled(true);    
        enterField.setFocusable(false);
        enterField.setPreferredSize(new Dimension(220,20));
        enterField.addActionListener( new ActionListener(){
        
                
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData(new Pack(e.getActionCommand()));
                enterField.setText("");
                enterField.setFocusable(false);
                client.requestFocusInWindow();
                
            }       
        
        });
        
        
        
        displayArea = new JTextArea();
        displayArea.setFocusable(false);
        
        displayArea.setEditable(false);
        
        
        add( new JScrollPane(displayArea),BorderLayout.CENTER);
        add(enterField,BorderLayout.SOUTH);
        
    }
    public void append(String message)
    {
        displayArea.append(message+"\n");
        
    }
    public void setChatEnabled(boolean state)
    {
        enterField.setEditable(state);
    }
    public void getFocus()
    {
        enterField.setFocusable(true);
        enterField.requestFocusInWindow();
        
    }
}
