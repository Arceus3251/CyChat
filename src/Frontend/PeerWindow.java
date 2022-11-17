package Frontend;

import javax.swing.*;
import java.awt.event.*;

public class PeerWindow extends JDialog
{
    private JPanel peerPanel;
    private JButton buttonOK;
    private JButton buttonCANCEL;
    private JTextField hostName;
    private JTextField portNumber;
    private JTextField uniqueID;
    private JLabel errorLabel;
    private boolean cancelState;


    public PeerWindow()
    {
        cancelState = false;
        setContentPane(peerPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);




        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();

            }
        });

        buttonCANCEL.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();

            }
        });

        // call onCancel() when X is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();

            }

        });

        // call onCancel() on ESCAPE (Keyboard)
        peerPanel.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();

            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK()
    {

        if(hostName.getText().equals(""))
        {
            errorLabel.setText("Host Name cannot be empty");
            return;
        }
        if(portNumber.getText().equals(""))
        {
            errorLabel.setText("Port Number cannot be empty");
            return;
        }
        if(uniqueID.getText().equals(""))
        {
            errorLabel.setText("Unique Identifier cannot be empty");
            return;
        }
        if(uniqueID.getText().length()<5)
        {
            errorLabel.setText("Unique ID must be greater than 4 characters");
            return;
        }

        cancelState = false;
        dispose();
    }

    private void onCancel()
    {
        cancelState = true;
        dispose();
    }

    public boolean getCancelState()
    {
        return cancelState;

    }

    public String getHostName()
    {
        return hostName.getText();

    }

    public int getPortNumber()
    {
        return Integer.parseInt(portNumber.getText());

    }

    public String getUniqueID()
    {
        return uniqueID.getText();
    }

}

