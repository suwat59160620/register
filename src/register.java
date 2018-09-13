import com.mongodb.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class register {
    private JPanel mainPanel;
    private JTextField name;
    private JButton cancelButton;
    private JButton registerButton;
    private JLabel avatar1;
    private JLabel avatar2;
    private JLabel avatar3;
    private JLabel avatar4;
    private JPanel panelA1;
    private JPanel panelA2;
    private JPanel panelA3;
    private JPanel panelA4;
    private JTextField username;
    private JPasswordField pass;
    private static int avatar = 1;
    static MongoClientURI uri ;
    static MongoClient mongo ;
    static DB db;
    static DBCollection user;
    static DBObject dockUser;

    public register() {
        panelA1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar=1;
                setBGicon();
                panelA1.setBackground(new java.awt.Color(242,242,30));
            }
        });
        panelA2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar=2;
                setBGicon();
                panelA2.setBackground(new java.awt.Color(242,242,30));
            }
        });
        panelA3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar=3;
                setBGicon();
                panelA3.setBackground(new java.awt.Color(242,242,30));
            }
        });
        panelA4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avatar=4;
                setBGicon();
                panelA4.setBackground(new java.awt.Color(242,242,30));
            }
        });
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submitRegis();


            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetText();
                setBGicon();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        register form = new register();
        frame.setContentPane(form.mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        form.connect();
        frame.setVisible(true);

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
    public  void resetText(){
        name.setText("");
        username.setText("");
        pass.setText("");
    }
    public void submitRegis() {
        BasicDBObject searchQuery  = new BasicDBObject();
        searchQuery.put("username",name.getText());
        dockUser = user.findOne(searchQuery);
        if(dockUser!=null){
            JOptionPane.showMessageDialog(null, "username ซ้ำ");

        }else if(name.getText().isEmpty()||pass.getText().isEmpty()||username.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ใส่ข้อมูลให้ครบ");
        }else{
            BasicDBObject add = new BasicDBObject();
            add.put("name", name.getText());
            add.put("username", username.getText());
            add.put("password", new String(pass.getPassword()));
            add.put("avatar", avatar);
            add.put("online", false);
            add.put("status", "waiting");
            user.insert(add);
            JOptionPane.showMessageDialog(null, "สมัครสมาชิกสำเร็จ");
            resetText();
            setBGicon();
        }
    }
    public void setBGicon(){
        panelA1.setBackground(new java.awt.Color(66,65,66));
        panelA2.setBackground(new java.awt.Color(66,65,66));
        panelA3.setBackground(new java.awt.Color(66,65,66));
        panelA4.setBackground(new java.awt.Color(66,65,66));
    }
    public  void connect(){
        try {
            uri = new MongoClientURI("mongodb://tictactoe:softdev2@ds155492.mlab.com:55492/tictactoe");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("users");

        }catch (IOException ex) {

        }
    }


}
