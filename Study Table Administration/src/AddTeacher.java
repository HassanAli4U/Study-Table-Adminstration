

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;


public class AddTeacher implements ActionListener{
	JFrame frame;
JLabel label_name, label_id,attendantDays;
JTextField textField_name,textField_id;
String[]special=new String []{"السبت","الاحد","الاتنين","الثلاثاء","الاربعاء","الخميس"};
JComboBox specialist;
JRadioButton days[]=new JRadioButton [7];
JButton add;
Main main=new Main();

Connection           conn=null;
Statement            stmt=null;
static String        sql;
static String        driver="jdbc:oracle:thin:&localhost:1521:orcl";

public void displays(){
	frame=new JFrame("اضافة مدرس");
	frame.setSize(main.percent_width(50),main.percent_height(60));
	frame.setLocation(main.percent_width(25), main.percent_height(20));
	
	label_name = new JLabel("الاسم",SwingConstants.RIGHT);
	label_name.setSize(frame.getWidth()*10/100,frame.getHeight()*7/100);
	label_name.setLocation(frame.getWidth()*85/100, frame.getHeight()*5/100);
	label_name.setFont(new Font("Arial", Font.PLAIN, 25));
	frame.add(label_name);
	
	textField_name=new JTextField(SwingConstants.RIGHT);
	textField_name.setSize(frame.getWidth()*50/100, frame.getHeight()*7/100);
	textField_name.setLocation(frame.getWidth()*25/100, frame.getHeight()*5/100);
	textField_name.setFont(new Font("Arial", Font.PLAIN, 25));
	textField_name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textField_name.addActionListener(this);
	frame.add(textField_name);
	

	label_id = new JLabel("الرقم السرى",SwingConstants.RIGHT);
	label_id.setSize(frame.getWidth()*15/100,frame.getHeight()*7/100);
	label_id.setLocation(frame.getWidth()*80/100, frame.getHeight()*14/100);
	label_id.setFont(new Font("Arial", Font.PLAIN, 25));
	frame.add(label_id);
	
	textField_id=new JTextField(SwingConstants.RIGHT);
	textField_id.setSize(frame.getWidth()*50/100, frame.getHeight()*7/100);
	textField_id.setLocation(frame.getWidth()*25/100, frame.getHeight()*14/100);
	textField_id.setFont(new Font("Arial", Font.PLAIN, 25));
	textField_id.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textField_id.addActionListener(this);
	frame.add(textField_id);
	
	 specialist = new JComboBox(special);
	 specialist.setSelectedIndex(-1);
	 specialist.addActionListener(this);
	 specialist.setSize(120,30);
	 
	 specialist.setLocation(frame.getWidth()*78/100,frame.getHeight()*22/100);
	 specialist.setRenderer(new MyComboBoxRenderer("التخصص"));
     specialist.setFont(new Font("Arial", Font.PLAIN, 20));
	    //environment.setEditable(false);
	    frame.add(specialist,0);
	
	    attendantDays = new JLabel("ايام الحضور",SwingConstants.RIGHT);
	    attendantDays.setSize(frame.getWidth()*15/100,frame.getHeight()*5/100);
	    attendantDays.setLocation(frame.getWidth()*80/100, frame.getHeight()*30/100);
	    attendantDays.setFont(new Font("Arial", Font.PLAIN, 25));
		frame.add(attendantDays);
		
		int heightCol=frame.getHeight()*35/100;
		for(int i=0;i<7;i++)
			{days[i]=new JRadioButton();
		    days[i].setSize(frame.getWidth()*20/100, frame.getHeight()*3/100);
		    days[i].setHorizontalTextPosition(SwingConstants.LEFT);
		    days[i].setHorizontalAlignment(SwingConstants.LEFT);
		    days[i].setLocation(frame.getWidth()*70/100, heightCol);
		    days[i].addActionListener(this);
		    frame.add(days[i]);
		    heightCol+=frame.getHeight()*4/100;
			}
		days[0].setText("كل الايام");
		days[1].setText("السبت");
		days[2].setText("الاحد");
		days[3].setText("الاثنيت");
		days[4].setText("الثلاثاء");
		days[5].setText("الاربعاء");
		days[6].setText("الخميس");
		
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
	if(action.getSource()==days[0])
	{
		for(int i=1;i<=6;i++)
			days[i].setSelected(true);
	}
	if(action.getSource()==add)
	{
		String id=textField_id.getText().toString();
		String name=textField_name.getText().toString();
		String special=specialist.getSelectedItem().toString();
		sql="inser into teacher (id, name, specialist) values ("+id+", "+name+", "+special+")";
	}
	
}
private void insert_teacher(){
    try {
        DriverManager.registerDriver(new OracleDriver());
        
    } catch (SQLException e) {
    }
}
class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
	  private String _title;

	  public MyComboBoxRenderer(String title) {
	    _title = title;
	  }

	  @Override
	  public Component getListCellRendererComponent(JList list, Object value,
	      int index, boolean isSelected, boolean hasFocus) {
	    if (index == -1 && value == null)
	      setText(_title);
	    else
	      setText(value.toString());
	    return this;
	  }
	}
}
