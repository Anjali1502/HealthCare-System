import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class Chat extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel p1=new JPanel();

    private JTextArea ca= new JTextArea();
    private JTextField cf=new JTextField();
    private JButton b=new JButton();
    private JLabel l=new JLabel();

    Chat(){                                                // Layout and Properties defined in Constructer

        JFrame f=new JFrame();
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        f.setLayout(null);
        f.setSize(400,400);
        f.getContentPane().setBackground(Color.cyan);
        f.setTitle("ChatBot");
        f.add(ca);
        f.add(cf);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        add(p1);
        ca.setSize(300,310);
        ca.setLocation(1, 1);
        ca.setBackground(Color.BLACK);
        cf.setSize(300,20);        //chat field
        cf.setLocation(1,320);
        f.add(b);
        l.setText("SEND");
        b.add(l);
        b.setSize(400,20);
        b.setLocation(300,320);

        b.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(e.getSource()==b) {                           // Message sents on Click button

                    String text=cf.getText().toLowerCase();
                    ca.setForeground(Color.GREEN);
                    ca.append("You-->"+text+"\n");
                    cf.setText("");

                    if(text.contains("hi doctor")) {                         // input Checking
                        replyMeth("Hi there");
                    }
                    else if(text.contains("how are you")) {
                        replyMeth("I'm Good :).Thankyou for asking");
                    }
                    else if(text.contains("i have a problem")) {
                        replyMeth("Tell me about your problem");
                    }
                    else if(text.contains("i have Knee Pain")) {
                        replyMeth("Come in our clinic" + "\n" + "I will check you");
                    }
                    else if(text.contains("please schedule an appointment")) {
                        replyMeth("Ok,Contact Receptionist");
                    }
                    else if(text.contains("bye doctor")) {
                        replyMeth("See you Soon ,Bye");
                    }
                    else
                        replyMeth("I Can't Understand");

                }

            }

        });

    }
    public void replyMeth(String s) {                          // Reply Method
        ca.append("Doctor-->"+s+"\n");
    }


}



public class ChatBot {                                     //Driver Class

    public static void main(String[] args) {             //main class

        new Chat();                                  // instantiation
    }

}
