import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Doctor extends JDialog {
    private JPanel DoctorPanel;
    private JTextField txtName;
    private JTextField txtSpe;
    private JTextField txtExp;
    private JTable table1;
    private JTextField txtId;
    private JButton btnSave;
    private JButton btnCancel;
    private JScrollPane table_1;
    private JButton SEARCHButton;
    private JButton btnDelete;
    private JButton UPDATEButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor");
        frame.setContentPane(new Doctor().DoctorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/demo", "root", "1234");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from doctor");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Doctor() {
        connect();
        table_load();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, spe, exp;
                id = txtId.getText();
                name = txtName.getText();
                spe = txtSpe.getText();
                exp = txtExp.getText();

                try {
                    pst = con.prepareStatement("insert into doctor(id,name,spe,exp)values(?,?,?,?)");  //name match with table column
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, spe);
                    pst.setString(4, exp);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtSpe.setText("");
                    txtExp.setText("");
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

                    pst = con.prepareStatement("select id,name,spe,exp, from doctor where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        id = rs.getString(1);
                        String name = rs.getString(2);
                        String spe = rs.getString(3);
                        String exp = rs.getString(4);

                        txtId.setText(id);
                        txtName.setText(name);
                        txtSpe.setText(spe);
                        txtExp.setText(exp);

                    } else {
                        txtId.setText("");
                        txtName.setText("");
                        txtSpe.setText("");
                        txtExp.setText("");
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
                String id, name, spe, exp;
                id = txtId.getText();
                name = txtName.getText();
                spe = txtSpe.getText();
                exp = txtExp.getText();


                try {
                    pst = con.prepareStatement("update doctor set id = ?,name = ?,spe = ?,exp=? where id = ?");
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, spe);
                    pst.setString(4, exp);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtExp.setText("");
                    txtSpe.setText("");
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
                    pst = con.prepareStatement("delete from employee  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtExp.setText("");
                    txtSpe.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
    }}