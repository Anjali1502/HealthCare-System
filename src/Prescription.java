import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Prescription {
    private JPanel PrescriptionPanel;
    private JTextField txtPat;
    private JTextField txtDoc;
    private JTextField txtRoom;
    private JScrollPane table_1;
    private JTable table1;
    private JTextField txtApp;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnHome;
    private JButton SEARCHButton;
    private JButton btnDelete;
    private JButton UPDATEButton;
    private JTextField textField1;
    private JTextField textField2;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor");
        frame.setContentPane(new Prescription().PrescriptionPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/prescription", "root", "1234");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from disease");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Prescription() {
        connect();
        table_load();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String app, pname, dname, room;
                app = txtApp.getText();
                pname = txtPat.getText();
                dname = txtDoc.getText();
                room = txtRoom.getText();

                try {
                    pst = con.prepareStatement("insert into disease(app,pname,dname,room)values(?,?,?,?)");  //name match with table column
                    pst.setString(1,app);
                    pst.setString(2, pname);
                    pst.setString(3, dname);
                    pst.setString(4, room);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    txtApp.setText("");
                    txtPat.setText("");
                    txtDoc.setText("");
                    txtRoom.setText("");
                    txtPat.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String app = txtApp.getText();

                    pst = con.prepareStatement("select app,pname,dname,room from disease where id = ?");
                    pst.setString(1, app);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        app = rs.getString(1);
                        String pname = rs.getString(2);
                        String dname = rs.getString(3);
                        String room = rs.getString(4);

                        txtApp.setText(app);
                        txtPat.setText(pname);
                        txtDoc.setText(dname);
                        txtRoom.setText(room);

                    } else {
                        txtApp.setText("");
                        txtPat.setText("");
                        txtDoc.setText("");
                        txtRoom.setText("");
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
                id = txtApp.getText();
                name = txtPat.getText();
                spe = txtDoc.getText();
                exp = txtRoom.getText();


                try {
                    pst = con.prepareStatement("update disease set id = ?,name = ?,spe = ?,exp=? where id = ?");
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, spe);
                    pst.setString(4, exp);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtApp.setText("");
                    txtPat.setText("");
                    txtRoom.setText("");
                    txtDoc.setText("");
                    txtPat.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtApp.getText();

                try {
                    pst = con.prepareStatement("delete from disease  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtApp.setText("");
                    txtPat.setText("");
                    txtRoom.setText("");
                    txtDoc.setText("");
                    txtPat.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
    }

    public void setVisible(boolean b)
    {

    }
}

