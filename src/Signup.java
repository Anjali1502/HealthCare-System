import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Signup extends JDialog {
    private JPanel LoginPanel;
    private JButton btnLogin;
    private JLabel lblPassword;
    private JLabel lblEmail;
    private JLabel txtConfirm;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField passwordField1;
    private JButton btnCancel;
    private JComboBox txtType;

    public Signup(JFrame parent) {
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
//               Home ap=new Home();
//               ap.setVisible(true);

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);


//        HOMEButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Home ap=new Home();
//               ap.setVisible(true);
//            }
//        });
//        HOMEButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                Home ho=new Home(null);
//                ho.setVisible(true);
//            }
//        });
//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Home ap=new Home();
//                ap.setVisible(true);
//            }
//        });
   }

    private void LoginUser()
    {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String utype = txtType.getSelectedItem().toString();
        String password = String.valueOf(txtPassword.getPassword());
        String confirm = String.valueOf(passwordField1.getPassword());

        if (id.isEmpty()||name.isEmpty() || email.isEmpty() ||utype.isEmpty() || password.isEmpty())
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

        user = addUserToDatabase(id,name,email,utype,password);
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
    private User addUserToDatabase(String id,String name, String email,String utype, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/signup";
        final String USERNAME = "root";
        final String PASSWORD = "1234";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO admin(id,name,email,utype,password) " +
                    "VALUES (?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, utype);
            preparedStatement.setString(5, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.id=id;
                user.name = name;
                user.email = email;
                user.utype = utype;
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
       Signup myForm = new Signup(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.name);
        }
        else {
            System.out.println("Registration canceled");
        }


    }
}



