import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;


public class TablePreparation implements ActionListener{
	JFrame frame;
	JButton addRow, addSubject, addTeacher;
	public void display(){
     frame =new JFrame("اعداد الجدول العام");	
     frame.getContentPane().setBackground(Color.white); 
     frame.setSize(percent_width(50), percent_height(60));
     frame.setLocation(percent_width(25), percent_height(20));
     
     addRow=new JButton("اضافة صف");
     addRow.setSize(percent_width(30),percent_height(5));
     addRow.setLocation(frame.getWidth()*20/100, frame.getHeight()*25/100);
     addRow.setFont(new Font("Arial", Font.PLAIN, 30));
     addRow.setForeground(Color.white);
     addRow.setBackground(Color.black);
     addRow.addActionListener(this);
		frame.add(addRow);
		
		addSubject=new JButton("اضافة مادة");
		addSubject.setSize(percent_width(30),percent_height(5));
		addSubject.setLocation(frame.getWidth()*20/100, frame.getHeight()*35/100);
		addSubject.setFont(new Font("Arial", Font.PLAIN, 30));
		addSubject.setForeground(Color.white);
		addSubject.setBackground(Color.black);
		addSubject.addActionListener(this);
			frame.add(addSubject);

			addTeacher=new JButton("اضافة مدرس");
			addTeacher.setSize(percent_width(30),percent_height(5));
			addTeacher.setLocation(frame.getWidth()*20/100, frame.getHeight()*45/100);
			addTeacher.setFont(new Font("Arial", Font.PLAIN, 30));
			addTeacher.setForeground(Color.white);
			addTeacher.setBackground(Color.black);
			addTeacher.addActionListener(this);
				frame.add(addTeacher);
	 frame.setLayout(null);
	 frame.setVisible(true);
	}
	public int percent_width(int num)
	{
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int width = (int) screenSize.getWidth();
	        num=num*width/100;
	        return num;
	        
	}
	public  int percent_height(int num)
	{
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int height = (int) screenSize.getHeight();
	        num=num*height/100;
	        return num;
	        
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==addTeacher)
		{
			frame.setVisible(false);
			AddTeacher add=new AddTeacher();
			add.displays();
		}
		
	}

}
