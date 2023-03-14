import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Login extends JDialog {
        private JPanel LoginPanel;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPasswordField txtPassword;
    private JTextField txtEmail;
    private JTextField txtName;
    private JPasswordField passwordField1;
    private JTextField txtId;
    private JLabel lblPassword;
    private JLabel lblEmail;
    private JLabel txtConfirm;


    public Login(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(850, 674));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginUser();  //goto loginuser, method name
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void LoginUser()
    {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
      //  String usertype = txtusertype.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String confirm = String.valueOf(passwordField1.getPassword());

        if (id.isEmpty()||name.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(id,name,email,password);
        if (user != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    private User addUserToDatabase(String id,String name, String email, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/care";
        final String USERNAME = "root";
        final String PASSWORD = "1234";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (id,name,email,password) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
           // preparedStatement.setString(4, usertype);
            preparedStatement.setString(4, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
              user.id=id;
                user.name = name;
                user.email = email;
               // user.usertype = usertype;
                user.password = password;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        Login myForm = new Login(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.name);
        }
        else {
            System.out.println("Registration canceled");
        }
    }
}


