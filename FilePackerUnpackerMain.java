import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

import com.sun.glass.events.KeyEvent;

class FileLogin extends Template implements ActionListener, Runnable
{
    JButton SUBMIT;
    JLabel label1, label2, label3, TopLabel;
    final JTextField text1,text2;
    private static int attemp = 3;

    FileLogin()
    {
        TopLabel = new JLabel();
        TopLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        TopLabel.setText("File Packer Unpacker : Login");
        TopLabel.setForeground(Color.BLUE);

        Dimension topsize = TopLabel.getPreferredSize();
        TopLabel.setBounds(50,40,topsize.width,topsize.height);
        _header.add(TopLabel);

        label1 = new JLabel();
        label1.setText("Username : ");
        label1.setForeground(Color.white);

        Dimension size = label1.getPreferredSize();

        label1.setBounds(50,135,size.width, size.height);
        label1.setHorizontalAlignment(SwingConstants.CENTER); 

        text1 = new JTextField(15);
        Dimension tsize = text1.getPreferredSize();
        text1.setBounds(200,135,tsize.width,tsize.height);

        text1.setToolTipText("Enter Username");

        label2 = new JLabel();
        label2.setText("Password : ");
        label2.setBounds(50,175,size.width, size.height);
        label2.setForeground(Color.white);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        text2 = new JPasswordField(15);
        text2.setBounds(200,175,tsize.width,tsize.height);

        text2.setToolTipText("Enter Password");

        text2.addFocusListener(new FocusListener() 
        {
            public void focusGained(FocusEvent e)
            {

            }
            public void focusLost(FocusEvent e)
            {
                label3.setText("");
            }
        });

        SUBMIT = new JButton("SUBMIT");
        SUBMIT.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension ssize = SUBMIT.getPreferredSize();

        SUBMIT.setBounds(50,220,ssize.width, ssize.height);

        label3 = new JLabel();
        label3.setText("");

        Dimension l3size = label3.getPreferredSize();

        label3.setForeground(Color.RED);
        label3.setBounds(50,250, l3size.width, l3size.height);

        Thread t = new Thread(this);
        t.start();

        _content.add(label1);
        _content.add(text1);

        _content.add(label2);
        _content.add(text2);

        _content.add(label3);
        _content.add(SUBMIT);

        pack();
        validate();

        ClockHome();
        setVisible(true);
        this.setSize(500,500);
        this.setResizable(false);
        setLocationRelativeTo(null);
        SUBMIT.addActionListener(this);
    }

    public boolean Validate(String Username, String password)
    {
        if((Username.length()<8)|| (password.length()<8))
            return false;
        else
            return true;
    }

    public void actionPerformed(ActionEvent ae)
    {
        String value1 = text1.getText();
        String value2 = text2.getText();

        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
    

        if(ae.getSource() == minimize)
        {
            this.setState(this.ICONIFIED);
        }

        if(ae.getSource()==SUBMIT)
        {
            if(Validate(value1,value2) == false)
            {
                text1.setText("");
                text2.setText("");
                JOptionPane.showMessageDialog(this, "Short username", "File Packer Unpacker", JOptionPane.ERROR_MESSAGE);
            }
            if(value1.equals("Admin1234") && value2.equals("Admin1234"))
            {
                NextPage page = new NextPage(value1);
                this.setVisible(false);
                page.pack();
                page.setVisible(true);
                page.setSize(500,500);
            }
            else
            {
                attemp--;
                

                if(attemp == 0)
                {
                    JOptionPane.showMessageDialog(this, "Number of attempts finished", "File Packer Unpacker", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(this, "Incorrect login or passowrd", "Error",JOptionPane.ERROR_MESSAGE);
            }
        
        }
    }

    public void run()
    {
        for(;;)
        {
            if(text2.isFocusOwner())
            {
                if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
                {
                    text2.setToolTipText("Warnbing : CAPS LOCK is ON");
                }
                else
                    text2.setToolTipText("null");

                if((text2.getText()).length()<8)
                    label3.setText("Weak Password");
                else 
                    label3.setText("");
            }
        }
    }
}
   

class FilePackerUnpackerMain
{
    public static void main(String arg[])
    {
        try
        {
            FileLogin frame = new FileLogin();
            frame.setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
 }
