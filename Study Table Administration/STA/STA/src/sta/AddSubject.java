package sta;



import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import oracle.jdbc.OracleDriver;


public class AddSubject implements ActionListener{
	JFrame frame;
JLabel label_name, label_sharesNumber;
JTextField textField_name,textField_sharesNumber;
String[]special=new String []{"لغة عربية","لغة انجليزية","رياضيات","دراسات اجتماعية","علوم","حاسب ألى"};
JComboBox specialist;
JButton add;
Main main=new Main();

static Connection           conn=null;
static Statement            stmt=null;
static String        sql;
static String        url="jdbc:oracle:thin:@localhost:1521:XE";

public void displays(){
	frame=new JFrame("إضافة مادة");
	frame.setSize(main.percent_width(50),main.percent_height(60));
	frame.setLocation(main.percent_width(25), main.percent_height(20));
    try {
        frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
    } catch (IOException e) {
        System.out.print(e.getMessage());
    }
        
	label_name = new JLabel("الاسم",SwingConstants.RIGHT);
	label_name.setSize(frame.getWidth()*10/100,frame.getHeight()*8/100);
	label_name.setLocation(frame.getWidth()*85/100, frame.getHeight()*5/100);
        label_name.setForeground(Color.white);
	label_name.setFont(new Font("Arial", Font.PLAIN, 25));
	frame.add(label_name);
	
	textField_name=new JTextField(SwingConstants.RIGHT);
	textField_name.setSize(frame.getWidth()*50/100, frame.getHeight()*8/100);
	textField_name.setLocation(frame.getWidth()*10/100, frame.getHeight()*5/100);
	textField_name.setFont(new Font("Arial", Font.PLAIN, 25));
	textField_name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textField_name.addActionListener(this);
	frame.add(textField_name);
	

	label_sharesNumber = new JLabel("عدد الحصص الاسبوعية ",SwingConstants.RIGHT);
	label_sharesNumber.setSize(frame.getWidth()*30/100,frame.getHeight()*10/100);
	label_sharesNumber.setLocation(frame.getWidth()*65/100, frame.getHeight()*20/100);
	label_sharesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
        label_sharesNumber.setForeground(Color.white);
	frame.add(label_sharesNumber);
	
	textField_sharesNumber=new JTextField(SwingConstants.RIGHT);
	textField_sharesNumber.setSize(frame.getWidth()*50/100, frame.getHeight()*8/100);
	textField_sharesNumber.setLocation(frame.getWidth()*10/100, frame.getHeight()*20/100);
	textField_sharesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
	textField_sharesNumber.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textField_sharesNumber.addActionListener(this);
        textField_sharesNumber.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e) {
               char caracter = e.getKeyChar();
               if (((caracter < '0') || (caracter > '9'))
                       && (caracter != '\b')) {
                   e.consume();
               }
           }
       });
	frame.add(textField_sharesNumber);
	
	 specialist = new JComboBox(special);
	 specialist.setSelectedIndex(-1);
	 specialist.addActionListener(this);
	 specialist.setSize(frame.getWidth()*50/100, frame.getHeight()*8/100);
         specialist.setBackground(Color.white);
	 specialist.setLocation(frame.getWidth()*10/100, frame.getHeight()*33/100);
	 specialist.setRenderer(new MyComboBoxRenderer("التخصص"));
     specialist.setFont(new Font("Arial", Font.PLAIN, 20));
	    //environment.setEditable(false);
	    frame.add(specialist,0);
	
		
		
	add=new JButton("حفظ");
	add.setSize(frame.getWidth()*20/100, frame.getHeight()*10/100);
	add.setLocation(frame.getWidth()*40/100, frame.getHeight()*70/100);
	
	add.setFont(new Font("Arial", Font.PLAIN, 30));
	add.setForeground(Color.white);
	add.setBackground(Color.black);
	add.addActionListener(this);
		frame.add(add);
	frame.setLayout(null);
	frame.setVisible(true);
	
	
}
@Override
public void actionPerformed(ActionEvent action) {
	// TODO Auto-generated method stub
	
	if(action.getSource()==add)
	{
		String sharesNum=textField_sharesNumber.getText().toString();
		String name=textField_name.getText().toString();
		String special=specialist.getSelectedItem().toString();
		sql="insert into supject (id, name, specialist, sharesNumber) values (SEQ_ID2.NEXTVAL, "+"'"+name+"', '"+special+"', '"+sharesNum+"')";
                insert_subject(sql);
                
               infoBox("تم تسجيل المادة", "");
	}
	
}
private void insert_subject(String sqll){
    try {
        DriverManager.registerDriver(new OracleDriver());
        conn=DriverManager.getConnection(url,"hr2","hr2");
        stmt=conn.prepareStatement(sqll);
        stmt.executeUpdate(sqll);
    } catch (SQLException e) {
        System.out.println(e.getErrorCode());
        System.out.println(e.getMessage());
    }
}
    public static void infoBox(String infoMessage, String titleBar)
        {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
}

