import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Home extends JDialog{
    private JButton btnOk;
    private JTextField txtName;
    private JButton btnCancel;
    private JComboBox txtType;
    private JPanel HomePanel;
    private JPasswordField txtPassword;
    private JPanel DummyPanel;
    private JButton LOGOUTButton;
    private JButton BtnSearch;
    private JButton BtnLogin;
    private JButton BtnDoctor;
    private JButton BtnCancel;
    private JButton BtnSignup;
    private JButton PATIENTButton;
    private JButton APPOINTMENTButton;
    private JButton PRESCRIPTIONButton;
    private JTextField txtDummy;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor");
        frame.setContentPane(new Home().HomePanel);
        frame.setMinimumSize(new Dimension(850, 974));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/usertype", "root", "1234");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from type");
            ResultSet rs = pst.executeQuery();
//                    table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Home() {
        connect();
        table_load();
        BtnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  name,utype,password;
                // id = txtId.getText();
                name = txtName.getText();
                utype = txtType.getSelectedItem().toString();
                password = String.valueOf(txtPassword.getPassword());
                // exp = txtExp.getText();

                try {
                    pst = con.prepareStatement("insert into type(name,utype,password)values(?,?,?)");  //name match with table column
                    //  pst.setString(1, id);
                    pst.setString(1, name);
                    pst.setString(2, utype);
                    pst.setString(3, password);
                    // pst.setString(4, exp);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    // txtId.setText("");
                    txtName.setText("");
                    //  txtType.setText("");
                    //  txtExp.setText("");
                    txtPassword.setText("");
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
//        btnCancel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Login ap=new Login(null);
//                ap.setVisible(true);

//                new Login.setVisible(true);
//            }
//        });

        BtnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               Registration ho=new Registration(null);
                ho.setVisible(true);
            }
        });
        BtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Login lo=new Login(null);
                lo.setVisible(true);
            }
        });
        BtnSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Signup lo=new Signup(null);
                lo.setVisible(true);
            }
        });

        BtnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
//        BtnDoctor.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Doctor lo=new Doctor();
//                lo.setVisible(true);
//            }
//        });
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    int a=JOptionPane.showConfirmDialog(null,"Do you really want to Logout?","Select",JOptionPane.YES_NO_CANCEL_OPTION);
                   if(a==0)
                   {
                       System.exit(0);
                   }
                }
            }
        });
//        PATIENTButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                {
//                    New_Patient lo = new New_Patient();
//                    lo.setVisible(true);
//                }
//            }
//            }
//        });
//        APPOINTMENTButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                Appointment ap = new Appointment();
//                ap.setVisible(true);
//            }
//        });
    }
}
