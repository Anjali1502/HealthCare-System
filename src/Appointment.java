import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Appointment {
    private JPanel AppointmentPanel;
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
    private JTextField txtId;
    private JTextField txtPid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Appiontment");
        frame.setContentPane(new Appointment().AppointmentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/appointment", "root", "1234");
            System.out.println("Successs");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from schedule");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment() {
        connect();
        table_load();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,app, pname, dname, room;
                pid=txtPid.getText();
                app=txtApp.getText();
                pname=txtPat.getText();
                dname=txtDoc.getText();
                room=txtRoom.getText();

                try {
                    pst = con.prepareStatement("insert into schedule(pid,app,pname,dname,room)values(?,?,?,?,?)");  //name match with table column
                    pst.setString(1, pid);
                    pst.setString(2, app);
                    pst.setString(3, pname);
                    pst.setString(4, dname);
                    pst.setString(5, room);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Saved!!");
                    table_load();
                    txtPid.setText("");
                    txtApp.setText("");
                    txtPat.setText("");
                    txtDoc.setText("");
                    txtRoom.setText("");

                    txtPid.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String pid = txtPid.getText();

                    pst = con.prepareStatement("select pid,app,pname,dname,room from schedule where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true)
                    {
                        String app = rs.getString(1);
                        String app = rs.getString(1);
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
                String id, app, pname, dname, room;
                app = txtApp.getText();
                pname = txtPat.getText();
                dname = txtDoc.getText();
                room = txtRoom.getText();
                id = txtId.getText();


                try {
                    pst = con.prepareStatement("update view set app = ?,pname = ?,dname = ?,room=? where id = ?");
                    pst.setString(1, app);
                    pst.setString(2, pname);
                    pst.setString(3, dname);
                    pst.setString(4, room);
                    pst.setString(5, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!!!!!");
                    table_load();
                    txtApp.setText("");
                    txtPat.setText("");
                    txtRoom.setText("");
                    txtDoc.setText("");
                    txtPat.requestFocus();
                  //  txtId.setText("");

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
                    pst = con.prepareStatement("delete from schedule where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
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
}