import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration extends JDialog {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel RegistrationPanel;


    public Registration(JFrame parent) {
        super(parent);
        setTitle("Register");
        setContentPane(RegistrationPanel);
        setMinimumSize(new Dimension(850, 574));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String password = String.valueOf(txtPassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(Registration.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  //terminate the registration form
            }
        });

        setVisible(true);
//        HOMEButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Login ap=new Login(null);
//                ap.setVisible(true);
//            }
//        });
//        HOMEButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
    }

    public User user;
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/care";
        final String USERNAME = "root";
        final String PASSWORD = "1234";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();    //create these in login formform
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
               //user.usertype = resultSet.getString("usertype");
                user.id=resultSet.getString("id");
                user.password = resultSet.getString("password");
//                user.utype=resultSet.getString("utype");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }


        return user;
    }

    public static void main(String[] args) {
        Registration loginForm = new Registration(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("User Name is: " + user.name);
            System.out.println("          Email: " + user.email);
            System.out.println("          ID: " + user.id);
         //   System.out.println("          UserType: " + user.utype);

         //  System.out.println("          usertype " + user.usertype);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }

}