package sta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class Main implements ActionListener {
JFrame mainFrame;
JLabel directorate, management,school,projectName;
JButton table_prep,tableModif,
AbsenceRegister,AssignSpareTable,table_Printing;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m=new Main();
		m.display();

	}
	public void display(){
		mainFrame =new JFrame("اعداد الجدول الدراسى");
	    mainFrame.setLayout(new BorderLayout());

        try {
            mainFrame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
               // mainFrame.getContentPane().setBackground(Color.white); 
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		directorate=new JLabel("محافظة  بنى سويف",SwingConstants.CENTER);
		directorate.setFont(new Font("Arial", Font.PLAIN, 40));
		directorate.setSize(percent_width(20),percent_height(5));
		directorate.setLocation(percent_width(40), percent_height(2));
	        directorate.setForeground(Color.white);
		mainFrame.add(directorate);
		
                

                
                
		management=new JLabel("ادارة  ببا التعليمية",SwingConstants.CENTER);
		management.setFont(new Font("Arial", Font.PLAIN, 40));
		management.setSize(percent_width(20),percent_height(5));
		management.setLocation(percent_width(40), percent_height(7));
	        management.setForeground(Color.white);

		mainFrame.add(management);
		
		school=new JLabel("مدرسة بلال ابن رباح الابتدائية المشتركة",SwingConstants.CENTER);
		school.setFont(new Font("Arial", Font.PLAIN, 40));
		school.setSize(percent_width(50),percent_height(6));
		school.setLocation(percent_width(25), percent_height(12));
	        school.setForeground(Color.white);
		mainFrame.add(school);
		
                
	    projectName=new JLabel("ادارة الجدول الدراسى",SwingConstants.CENTER);
	    projectName.setFont(new Font("Arial", Font.PLAIN, 40));
	    projectName.setSize(percent_width(40),percent_height(6));
	    projectName.setLocation(percent_width(30), percent_height(20));
	    projectName.setForeground(Color.white);
                mainFrame.add(projectName);
                
		table_prep=new JButton("- اعداد الجدول العام");
		table_prep.setSize(percent_width(20),percent_height(7));
		table_prep.setLocation(percent_width(60), percent_height(40));
		table_prep.setFont(new Font("Arial", Font.PLAIN, 30));
		table_prep.setForeground(Color.white);
		table_prep.setBackground(Color.black);
		table_prep.addActionListener(this);
		mainFrame.add(table_prep);
		

		
		
		tableModif=new JButton("- تعديل الجدول");
		tableModif.setSize(percent_width(20),percent_height(7));
		tableModif.setLocation(percent_width(60), percent_height(52));
		tableModif.setFont(new Font("Arial", Font.PLAIN, 30));
	        tableModif.addActionListener(this);
		tableModif.setForeground(Color.white);
		tableModif.setBackground(Color.black);
		mainFrame.add(tableModif);
		
		
		
		AbsenceRegister=new JButton("تسجيل الغياب");
		AbsenceRegister.setSize(percent_width(20),percent_height(7));
		AbsenceRegister.setLocation(percent_width(20), percent_height(40));
		AbsenceRegister.setFont(new Font("Arial", Font.PLAIN, 30));
                AbsenceRegister.addActionListener(this);
		AbsenceRegister.setForeground(Color.white);
		AbsenceRegister.setBackground(Color.black);
		mainFrame.add(AbsenceRegister);
		
		AssignSpareTable=new JButton("اعداد جدول الاحتياطى");
		AssignSpareTable.setSize(percent_width(20),percent_height(7));
		AssignSpareTable.setLocation(percent_width(20), percent_height(52));
		AssignSpareTable.setFont(new Font("Arial", Font.PLAIN, 30));
                AssignSpareTable.addActionListener(this);
		AssignSpareTable.setForeground(Color.white);
		AssignSpareTable.setBackground(Color.black);
		mainFrame.add(AssignSpareTable);
		

		table_Printing=new JButton("- طباعة الجداول");
		table_Printing.setSize(percent_width(16),percent_height(7));
		table_Printing.setLocation(percent_width(42), percent_height(65));
		table_Printing.setFont(new Font("Arial", Font.PLAIN, 30));
                table_Printing.addActionListener(this);
		table_Printing.setForeground(Color.white);
		table_Printing.setBackground(Color.black);
		mainFrame.add(table_Printing);
		
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
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
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		if(action.getSource()==table_prep)
		{
			TablePreparation preparation=new TablePreparation();
			preparation.display();
		}
               
                if(action.getSource()==table_Printing)
                {
                    mainFrame.setVisible(false);
                 TablesPrint print=new TablesPrint();
                 print.display();
                }
                if(action.getSource()==tableModif)
                {
                    TableModification modify=new TableModification();
                    modify.display();
                    }
                if(action.getSource()==AbsenceRegister)
                {
                    AbsenceRegesteration absence=new AbsenceRegesteration();
                    absence.display();
                    }
                if(action.getSource()==AssignSpareTable)
                {
                    SpareTable spareTable=new SpareTable();
                    spareTable.display();
                    }
		
	}

}
