import javax.swing.*;
import java.sql.*;

public class Connect {
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
}

//public static void main(String[] args)
//  try {
////                    Class.forName("com.mysql.jdbc.Driver");
////                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
////                    System.out.println("Success");
//          String sql = "insert into demo.doctor('IdDoctor','Name','Specialist')values(?,?,?)";
//          PreparedStatement pst = con.prepareStatement(sql);
//          pst.setString(1, id);
//          pst.setString(2, name);
//          pst.setString(3, spe);
//          pst.executeUpdate();
//          JOptionPane.showMessageDialog(null,"Record Added Successfully!!!");
