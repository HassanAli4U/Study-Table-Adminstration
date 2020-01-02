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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.ComboBoxModel;
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


public class AddClass implements ActionListener{
   
	JFrame frame;
JLabel label_RowNumber,label_SharesNumber,label_ClassesNumber;
JTextField textField_RowNumber,textfieldSharesNumber,textfield_ClassesNumber;
JButton add;
JComboBox arabic,english,comboboxMathSpeciality,comboboxSciencesSpeciality,comboboxSocialStudiesSpeciality,comboboxComputerSpeciality;
Main main=new Main();

static Connection           conn=null;
static Statement            stmt=null;
static String        sql;
static String        url="jdbc:oracle:thin:@localhost:1521:XE";

   
public void displays(){
        openConnection();
    
    
	frame=new JFrame("إضافة فصل");
	frame.setSize(main.percent_width(80),main.percent_height(60));
	frame.setLocation(main.percent_width(10), main.percent_height(20));
    try {
        frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
    } catch (IOException e) {
        System.out.print(e.getMessage());
    }
        
        
	label_RowNumber = new JLabel("رقم الص�?",SwingConstants.RIGHT);
	label_RowNumber.setSize(frame.getWidth()*10/100,frame.getHeight()*7/100);
	label_RowNumber.setLocation(frame.getWidth()*85/100, frame.getHeight()*5/100);
	label_RowNumber.setFont(new Font("Arial", Font.PLAIN, 25));
	frame.add(label_RowNumber);
	
	textField_RowNumber=new JTextField(SwingConstants.RIGHT);
	textField_RowNumber.setSize(frame.getWidth()*30/100, frame.getHeight()*7/100);
	textField_RowNumber.setLocation(frame.getWidth()*40/100, frame.getHeight()*5/100);
	textField_RowNumber.setFont(new Font("Arial", Font.PLAIN, 25));
	textField_RowNumber.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textField_RowNumber.addActionListener(this);
        
    textField_RowNumber.addKeyListener(new KeyAdapter() {
               public void keyTyped(KeyEvent e) {
                   char caracter = e.getKeyChar();
                   if (((caracter < '0') || (caracter > '9'))
                           && (caracter != '\b')) {
                       e.consume();
                   }
               }
           });
	frame.add(textField_RowNumber);
	
        
    label_ClassesNumber = new JLabel("عدد الفصول",SwingConstants.RIGHT);
    label_ClassesNumber.setSize(frame.getWidth()*30/100,frame.getHeight()*7/100);
    label_ClassesNumber.setLocation(frame.getWidth()*65/100, frame.getHeight()*14/100);
    label_ClassesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
    frame.add(label_ClassesNumber);
    
    textfield_ClassesNumber=new JTextField(SwingConstants.RIGHT);
    textfield_ClassesNumber.setSize(frame.getWidth()*30/100, frame.getHeight()*7/100);
    textfield_ClassesNumber.setLocation(frame.getWidth()*40/100, frame.getHeight()*14/100);
    textfield_ClassesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
    textfield_ClassesNumber.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    textfield_ClassesNumber.addActionListener(this);
        
    textfield_ClassesNumber.addKeyListener(new KeyAdapter() {
               public void keyTyped(KeyEvent e) {
                   char caracter = e.getKeyChar();
                   if (((caracter < '0') || (caracter > '9'))
                           && (caracter != '\b')) {
                       e.consume();
                   }
               }
           });
    frame.add(textfield_ClassesNumber);
        

	label_SharesNumber = new JLabel("عدد الحصص اليومية",SwingConstants.RIGHT);
	label_SharesNumber.setSize(frame.getWidth()*30/100,frame.getHeight()*7/100);
	label_SharesNumber.setLocation(frame.getWidth()*65/100, frame.getHeight()*23/100);
	label_SharesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
	frame.add(label_SharesNumber);
	
	textfieldSharesNumber=new JTextField(SwingConstants.RIGHT);
	textfieldSharesNumber.setSize(frame.getWidth()*30/100, frame.getHeight()*7/100);
	textfieldSharesNumber.setLocation(frame.getWidth()*40/100, frame.getHeight()*23/100);
	textfieldSharesNumber.setFont(new Font("Arial", Font.PLAIN, 25));
	textfieldSharesNumber.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	textfieldSharesNumber.addActionListener(this);
        textfieldSharesNumber.addKeyListener(new KeyAdapter() {
               public void keyTyped(KeyEvent e) {
                   char caracter = e.getKeyChar();
                   if (((caracter < '0') || (caracter > '9'))
                           && (caracter != '\b')) {
                       e.consume();
                   }
               }
           });
	frame.add(textfieldSharesNumber);
	

    
    
		
    //System.out.println(getArabicSubject().size());	
    String [] gettenArabicSubject=new String[getSubjects().get(0).size()];
    String [] english_subject=new String[getSubjects().get(1).size()];
    
    
    for(int i=0;i<gettenArabicSubject.length;i++)
    gettenArabicSubject[i] = (String)getSubjects().get(0).get(i);
    arabic = new JComboBox(gettenArabicSubject);
    arabic.setSelectedIndex(-1);
    arabic.addActionListener(this);
    arabic.setBackground(Color.white);
    arabic.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    arabic.setLocation(frame.getWidth()*80/100,frame.getHeight()*33/100);
    arabic.setRenderer(new MyComboBoxRenderer("اللغه العربية"));
    arabic.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(arabic);


    for(int i=0;i<english_subject.length;i++)
    english_subject[i] = (String)getSubjects().get(1).get(i);
    english = new JComboBox(english_subject);
    english.setSelectedIndex(-1);
    english.addActionListener(this);
    english.setBackground(Color.white);
    english.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    english.setLocation(frame.getWidth()*60/100,frame.getHeight()*33/100);
    english.setRenderer(new MyComboBoxRenderer("اللغه الانجليزية"));
    english.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(english);   
       
       
       
    String [] math_subject=new String[getSubjects().get(2).size()];
    for(int i=0;i<math_subject.length;i++)
    math_subject[i] = (String)getSubjects().get(2).get(i);
    comboboxMathSpeciality = new JComboBox(math_subject);
    comboboxMathSpeciality.setSelectedIndex(-1);
    comboboxMathSpeciality.addActionListener(this);
    comboboxMathSpeciality.setBackground(Color.white);
    comboboxMathSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    comboboxMathSpeciality.setLocation(frame.getWidth()*80/100,frame.getHeight()*45/100);
    comboboxMathSpeciality.setRenderer(new MyComboBoxRenderer("رياضيات"));
    comboboxMathSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(comboboxMathSpeciality);


       
    String [] socialStudys_subject=new String[getSubjects().get(3).size()];
    for(int i=0;i<socialStudys_subject.length;i++)
    socialStudys_subject[i] = (String)getSubjects().get(3).get(i);
    comboboxSocialStudiesSpeciality = new JComboBox(socialStudys_subject);
    comboboxSocialStudiesSpeciality.setSelectedIndex(-1);
    comboboxSocialStudiesSpeciality.addActionListener(this);
    comboboxSocialStudiesSpeciality.setBackground(Color.white);

    comboboxSocialStudiesSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    comboboxSocialStudiesSpeciality.setLocation(frame.getWidth()*60/100,frame.getHeight()*45/100);
    comboboxSocialStudiesSpeciality.setRenderer(new MyComboBoxRenderer("دراسات اجتماعية"));
    comboboxSocialStudiesSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(comboboxSocialStudiesSpeciality);
       
       
    String [] science_subject=new String[getSubjects().get(4).size()];
    for(int i=0;i<science_subject.length;i++)
    science_subject[i] = (String)getSubjects().get(4).get(i);
    comboboxSciencesSpeciality = new JComboBox(science_subject);
    comboboxSciencesSpeciality.setSelectedIndex(-1);
    comboboxSciencesSpeciality.addActionListener(this);
    comboboxSciencesSpeciality.setBackground(Color.white);
    comboboxSciencesSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    comboboxSciencesSpeciality.setLocation(frame.getWidth()*80/100,frame.getHeight()*57/100);
    comboboxSciencesSpeciality.setRenderer(new MyComboBoxRenderer("علوم"));
    comboboxSciencesSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(comboboxSciencesSpeciality);
                
                
    String [] computers_subjects=new String[getSubjects().get(5).size()];
    for(int i=0;i<computers_subjects.length;i++)
    computers_subjects[i] = (String)getSubjects().get(5).get(i);
    comboboxComputerSpeciality = new JComboBox(computers_subjects);
    comboboxComputerSpeciality.setSelectedIndex(-1);
    comboboxComputerSpeciality.addActionListener(this);
    comboboxComputerSpeciality.setBackground(Color.white);
    comboboxComputerSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
    comboboxComputerSpeciality.setLocation(frame.getWidth()*60/100,frame.getHeight()*57/100);
    comboboxComputerSpeciality.setRenderer(new MyComboBoxRenderer("حاسب ألى"));
    comboboxComputerSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
       frame.add(comboboxComputerSpeciality);
                
	add=new JButton("ح�?ظ");
	add.setSize(frame.getWidth()*20/100, frame.getHeight()*10/100);
	add.setLocation(frame.getWidth()*40/100, frame.getHeight()*70/100);
	add.setFont(new Font("Arial", Font.PLAIN, 30));
	add.setForeground(Color.white);
	add.setBackground(Color.black);
	add.addActionListener(this);
		frame.add(add);
                
   
                
              
	frame.setLayout(null);
	frame.setVisible(true);
	
        
        closeConnection();
	
}
@Override
public void actionPerformed(ActionEvent action) {
	// TODO Auto-generated method stub
	
	if(action.getSource()==add)
	{
            openConnection();
            boolean found=false;
                int sharesNum=Integer.parseInt(textfieldSharesNumber.getText().toString());
		int rowNumber=Integer.parseInt(textField_RowNumber.getText().toString());
	    int classNumber=Integer.parseInt(textfield_ClassesNumber.getText().toString());


              //make inser into all classes in this row
                for(int i=1;i<=classNumber;i++)
                {
                    sql="insert into class2 (id,name, sharesNumber) values (CLASS2_SEQ.NEXTVAL, '"+rowNumber+"/"+i+"', '"+sharesNum+"')";
                insert_subject(sql);
                if(arabic.getSelectedItem()!=null){
                String subjectName=arabic.getSelectedItem().toString();
           
                String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
                 try {
                stmt.executeUpdate(sql3);
                 } catch (SQLException e) {
                        }
                }
                
                
	    if(english.getSelectedItem()!=null){
	    String subjectName=english.getSelectedItem().toString();
	    
	    String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
	     try {
	    stmt.executeUpdate(sql3);
	     } catch (SQLException e) {
	            }
	    }
             
             
                
            if(comboboxMathSpeciality.getSelectedItem()!=null){
            String subjectName=comboboxMathSpeciality.getSelectedItem().toString();
            
            String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
             try {
            stmt.executeUpdate(sql3);
             } catch (SQLException e) {
                    }
            }   
            
            
            if(comboboxSocialStudiesSpeciality.getSelectedItem()!=null){
            String subjectName=comboboxSocialStudiesSpeciality.getSelectedItem().toString();
            
            String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
             try {
            stmt.executeUpdate(sql3);
             } catch (SQLException e) {
                    }
            }  

            if(comboboxSciencesSpeciality.getSelectedItem()!=null){
            String subjectName=comboboxSciencesSpeciality.getSelectedItem().toString();
            
            String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
             try {
            stmt.executeUpdate(sql3);
             } catch (SQLException e) {
                    }
            }  

               
            if(comboboxComputerSpeciality.getSelectedItem()!=null){
            String subjectName=comboboxComputerSpeciality.getSelectedItem().toString();
            
            String sql3="insert into classSubject2 (className, subjectName) values ('"+rowNumber+"/"+i+"', '"+subjectName+"')";
             try {
            stmt.executeUpdate(sql3);
                 if(found==false){
                 found=true;
                 infoBox("تم تسجيل الص�?", "");}
             } catch (SQLException e) {
                    }
            }  
           
           
                    
        } 
	    closeConnection();
            
	}
	
}
private void insert_subject(String sqll){
    try {
               stmt.executeUpdate(sqll);
        
       
    } catch (SQLException e) {
        System.out.println(e.getErrorCode());
        System.out.println(e.getMessage());
    }
    
}

public ArrayList<ArrayList> getSubjects()
{
    ArrayList all=new ArrayList();
    try {
       ResultSet r=stmt.executeQuery("select name from supject where specialist='"+"لغة عربية'");
       ArrayList <String>listArabicSubject=new ArrayList<String>();
        while(r.next()){
                          listArabicSubject.add(r.getString("name"));
        }
        
        ResultSet r2=stmt.executeQuery("select name from supject where specialist='"+"لغة انجليزية'");
        ArrayList <String>listEnglishSubject=new ArrayList<String>();
         while(r2.next()){
                           listEnglishSubject.add(r2.getString("name"));
                           System.out.println(r2.getString("name"));
         }
        ResultSet r3=stmt.executeQuery("select name from supject where specialist='"+"رياضيات'");
        ArrayList <String>listMathSubject=new ArrayList<String>();
         while(r3.next()){
                           listMathSubject.add(r3.getString("name"));
         }
        ResultSet r4=stmt.executeQuery("select name from supject where specialist='"+"دراسات اجتماعية'");
        ArrayList <String>listSocialStudiesSubject=new ArrayList<String>();
         while(r4.next()){
                           listSocialStudiesSubject.add(r4.getString("name"));
         }
        
        ResultSet r5=stmt.executeQuery("select name from supject where specialist='"+"علوم'");
        ArrayList <String>listScienceSubject=new ArrayList<String>();
         while(r5.next()){
                           listScienceSubject.add(r5.getString("name"));
         }
        
        ResultSet r6=stmt.executeQuery("select name from supject where specialist='"+"حاسب ألى'");
        ArrayList <String>listComputerSubject=new ArrayList<String>();
         while(r6.next()){
                           listComputerSubject.add(r6.getString("name"));
         }


       all.add(listArabicSubject);
        all.add(listEnglishSubject);
        all.add(listMathSubject);  
        all.add(listSocialStudiesSubject);  
        all.add(listScienceSubject);  
        all.add(listComputerSubject);  

        return (all);
    } catch (SQLException e) {
        System.out.println(e.getErrorCode());
        System.out.println(e.getMessage());
    }
    return null; 

}
    private int getcount(){
     
         try {
             
             ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM supject");
             r.next();
             int count = r.getInt("rowcount");
             r.close();
             textfieldSharesNumber.setText(""+count);
             System.out.println("MyTable has " + count + " row(s).");
             
            
            
             
             return(count);
         } catch (SQLException e) {
             System.out.println(e.getErrorCode());
             System.out.println(e.getMessage());
         }
         return 0;
     }
    
    
    public static void infoBox(String infoMessage, String titleBar)
        {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
    public void openConnection(){
            try {
                DriverManager.registerDriver(new OracleDriver());
                conn=DriverManager.getConnection(url,"hr2","hr2");
                stmt = conn.createStatement();
            } catch (SQLException e) {
            }
        
        }
    public void closeConnection(){
            try {
                conn.close();
                stmt.close();
            } catch (SQLException f) {
            }
        }


}

