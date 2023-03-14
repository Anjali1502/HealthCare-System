import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class New_Patient {
    private JPanel DoctorPanel;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtContact;
    private JScrollPane table_1;
    private JTable table1;
    private JTextField txtId;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnHome;
    private JButton SEARCHButton;
    private JButton btnDelete;
    private JButton UPDATEButton;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor");
        frame.setContentPane(new New_Patient().DoctorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/patient", "root", "1234");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from new_patient");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public New_Patient() {
        connect();
        table_load();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, age, contact;
                id = txtId.getText();
                name = txtName.getText();
                age= txtAge.getText();
                contact = txtContact.getText();

                try {
                    pst = con.prepareStatement("insert into new_patient(id,name,age,contact)values(?,?,?,?)");  //name match with table column
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, age);
                    pst.setString(4, contact);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtAge.setText("");
                    txtContact.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String id = txtId.getText();

                    pst = con.prepareStatement("select id,name,age,contact, from new_patient where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        id = rs.getString(1);
                        String name = rs.getString(2);
                        String age = rs.getString(3);
                        String contact = rs.getString(4);

                        txtId.setText(id);
                        txtName.setText(name);
                        txtAge.setText(age);
                        txtContact.setText(contact);

                    } else {
                        txtId.setText("");
                        txtName.setText("");
                        txtAge.setText("");
                        txtContact.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee No");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, age, contact;
                id = txtId.getText();
                name = txtName.getText();
                age= txtAge.getText();
               contact = txtContact.getText();


                try {
                    pst = con.prepareStatement("update new_patient set id = ?,name = ?,age = ?,contact=? where id = ?");
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, age);
                    pst.setString(4, contact);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtContact.setText("");
                    txtAge.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from new_patient  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtContact.setText("");
                    txtAge.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
    }}