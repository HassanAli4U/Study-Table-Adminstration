package sta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import oracle.dms.table.Table;

import oracle.jdbc.OracleDriver;

public class AssigningShares implements ActionListener {
    JTable table;
    JFrame form;
    JButton save,getSubjects,getTeachers,getAllowedShares;
    JComboBox classes,subjects,teacher;
    JScrollPane scrollPane;

    Object[] columnNames = {"","الاولى", "التانية", "الثالثة", "الرابعة", "الخامسة","السادسة","السابعة","الثامنة"};
    
    static Connection           conn=null;
    static Statement            stmt=null;
    static String        sql;
    static String        url="jdbc:oracle:thin:@localhost:1521:XE";

        public AssigningShares(){
            try {
                DriverManager.registerDriver(new OracleDriver());
                conn=DriverManager.getConnection(url,"hr2","hr2");
                stmt = conn.createStatement();
            } catch (SQLException e) {
            }
            
            }
      
    public void display(){
            form=new JFrame();
    form.setSize(percent_width(100), percent_height(100));
        try {
            form. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
  

    save=new JButton("حفظ");
    save.setSize(form.getWidth()*20/100,form.getHeight()*5/100);
    save.setLocation(form.getWidth()*40/100,form.getHeight()*80/100);
    save.setFont(new Font("Arial", Font.PLAIN, 30));
    save.setForeground(Color.white);
    save.setBackground(Color.black);
    save.addActionListener(this);
    form.add(save);

    
    classes = new JComboBox(getClasses());
    classes.setSelectedIndex(-1);
    classes.addActionListener(this);
    classes.setBackground(Color.white);
    classes.setSize(form.getWidth()*20/100, form.getHeight()*5/100);
    classes.setLocation(form.getWidth()*78/100,form.getHeight()*10/100);
    classes.setRenderer(new MyComboBoxRenderer("اختر فصل"));
    classes.setFont(new Font("Arial", Font.PLAIN, 15));
       //environment.setEditable(false);
    
    form.add(classes);
     
    getSubjects=new JButton("تم");
    getSubjects.setSize(form.getWidth()*5/100,form.getHeight()*5/100);
    getSubjects.setLocation(form.getWidth()*70/100,form.getHeight()*10/100);
    getSubjects.addActionListener(this);
        getSubjects.setFont(new Font("Arial", Font.PLAIN, 30));
        getSubjects.setForeground(Color.white);
        getSubjects.setBackground(Color.black);
    form.add(getSubjects);
                            
        subjects = new JComboBox();
        subjects.setSelectedIndex(-1);
        subjects.addActionListener(this);
        subjects.setBackground(Color.white);
        subjects.setSize(form.getWidth()*20/100, form.getHeight()*5/100);
        subjects.setLocation(form.getWidth()*78/100,form.getHeight()*20/100);
        subjects.setRenderer(new MyComboBoxRenderer("اختر مادة"));
        
        subjects.setFont(new Font("Arial", Font.PLAIN, 15));
           //environment.setEditable(false);
        form.add(subjects);   
        
        
        getTeachers=new JButton("تم");
        getTeachers.setSize(form.getWidth()*5/100,form.getHeight()*5/100);
        getTeachers.setLocation(form.getWidth()*70/100,form.getHeight()*20/100);
        getTeachers.addActionListener(this);
        getTeachers.setFont(new Font("Arial", Font.PLAIN, 30));
        getTeachers.setForeground(Color.white);
        getTeachers.setBackground(Color.black);
        form.add(getTeachers);

        teacher = new JComboBox();
        teacher.setSelectedIndex(-1);
        teacher.addActionListener(this);
        teacher.setBackground(Color.white);
        teacher.setSize(form.getWidth()*20/100, form.getHeight()*5/100);
        teacher.setLocation(form.getWidth()*78/100,form.getHeight()*30/100);
        teacher.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
        
        teacher.setFont(new Font("Arial", Font.PLAIN, 15));
          
        form.add(teacher); 
       
       
        getAllowedShares=new JButton("تم");
        getAllowedShares.setSize(form.getWidth()*5/100,form.getHeight()*5/100);
        getAllowedShares.setLocation(form.getWidth()*70/100,form.getHeight()*30/100);
        getAllowedShares.addActionListener(this);
        getAllowedShares.setFont(new Font("Arial", Font.PLAIN, 30));
        getAllowedShares.setForeground(Color.white);
        getAllowedShares.setBackground(Color.black);
        form.add(getAllowedShares);
       
       
        form.setExtendedState(JFrame.MAXIMIZED_BOTH);
            form.setLayout(null);
            form.setVisible(true);
            
     

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
            public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if(e.getSource()==save)
                    {
                        boolean registered=false;
                        //get all assigned columns in tables and specify them for selected class, subject and teacher
            String selectedClass=classes.getSelectedItem().toString();
                        String selectedSubject=subjects.getSelectedItem().toString();
                        String selectedTeacher=teacher.getSelectedItem().toString();

                            for(int row =0;row<6;row++)
                            {
                                    for(int col=1;col<=8;col++)
                                    {
                                            if(table.getModel().getValueAt(row, col).equals(true))
                                                    {System.out.println("row= "+row);
                                                System.out.println("col= "+col);
                                               System.out.println( table.getModel().getValueAt(row, 0));
                                               
                                                String sql3="insert into shares ( ID,  SHARENUMBER, DAY, CLASSNAME, SUBJECTNAME, TEACHERNAME)values(shares2.nextval, "+col+", '"+table.getModel().getValueAt(row, 0)+"', '"+selectedClass+"', '"+selectedSubject+"', '"+selectedTeacher+"')";
                                              
                                                        try {
                                                            
                                                        stmt=conn.prepareStatement(sql3);
                                                        stmt.executeUpdate(sql3);
                                                            if(registered==false){
                                                            infoBox("تم تسجيل الحصص بنجاح", "");
                                                            registered=true;
                                                            }
                                                        } catch (SQLException ex) {
                                                            registered=true;
                                                                   infoBox("اختر حصص اولا", "خطأ");
                                                               }
                                                       
                                                        
                                                    
                                                    }
                                            
                                                    
                                    }
                            }
                   
                 
                   
                     
                           
                    }
                   
                    
                   if(e.getSource()==getSubjects)
                    {
                       
                       //remove all found items and add new items in this combobox
                        subjects.removeAllItems();
                        teacher.removeAllItems();
                        if(getSubjects().length>0){
                        for(int i=0;i<getSubjects().length;i++)
                            subjects.addItem(getSubjects()[i]);
                           
                        }
                        subjects.setSelectedIndex(-1);
                        subjects.setRenderer(new MyComboBoxRenderer("اختر مادة"));
                       
                    }
                    
                    if(e.getSource()==getTeachers)
                    {
                        //remove all found items and add new items in this combobox
                        
                        teacher.removeAllItems();
                        if(getTeachers().length>0){
                        for(int i=0;i<getTeachers().length;i++)
                            teacher.addItem(getTeachers()[i]);
                            teacher.setSelectedIndex(-1);
                            teacher.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                        }
                       
                    }
                    
               if(e.getSource()==getAllowedShares)
               {
                       if(scrollPane!=null)
                       form.remove(scrollPane);
                        {
                            Object[][] data = {
                            {"السبت", false, false, false, false,false, false, false,false},
                            {"الاحد", false, false, false, false,false, false, false,false},
                            {"الاتنين", false, false,false, false,false, false, false,false},
                            {"الثلاثاء", false, false,false, false,false, false, false,false},
                            {"الاربعاء", false, false, false, false,false, false, false,false},
                            {"الخميس", false, false, false, false,false, false, false,false}
                            };
                            DefaultTableModel model = new DefaultTableModel(data, columnNames){
                           
                            @Override
                            public String getColumnName(int col) {
                                return (String) columnNames[col];
                            }
                            };
                            table = new JTable(model) {
                                
                               

                            public boolean isCellEditable(int row, int column){
                                
                                //determine class busy shares
                                String busyShares="select SHARENUMBER , DAY from shares where CLASSNAME='"+classes.getSelectedItem().toString()+"'";
                                  ResultSet r;
                                  String number="";
                                  String day="";
                                  ArrayList<String>listShareNumber=new ArrayList<String>();
                                  ArrayList<String>listDay=new ArrayList<String>();
                                  
                                  try {
                                  r = stmt.executeQuery(busyShares);
                                  while(r.next())
                                  {
                                      number=r.getString("SHARENUMBER");
                                      listShareNumber.add(number);
                                      day=r.getString("DAY");
                                      listDay.add(day);
                                  }
                                  } catch (SQLException e) {
                                  }
                                 
                                 //determine teacher busy shares
                                  for(int z=0;z<listDay.size();z++)
                                  for(int j=0;j<=5;j++) {
                                  if(listDay.get(z).equals(table.getModel().getValueAt(j, 0)))  
                                  for(int i=1;i<9;i++){
                                     {
                                      if(Integer.parseInt(listShareNumber.get(z))==i)
                                      { 
                                          if(row == j && column == i)
                                          {
                                              return false;
                                            
                                            }   
                                      }
                                     }
                                  }
                                      
                                  }
                                  
                                
                                ArrayList<ArrayList> teacherBusyShares= checkIfTeacherBusy(teacher.getSelectedItem().toString());
                                ArrayList <String>days=teacherBusyShares.get(0);
                                 ArrayList <String>sharesNumber=teacherBusyShares.get(1);
                                  for(int z=0;z<days.size();z++)
                                  for(int j=0;j<=5;j++) {
                                  if(days.get(z).equals(table.getModel().getValueAt(j, 0)))  
                                  for(int i=1;i<9;i++){
                                     {
                                        
                                      if(Integer.parseInt(sharesNumber.get(z))==i)
                                      { 
                                          if(row == j && column == i)
                                          {
                                              return false;
                                            
                                            }   
                                      }
                                     }
                                  }
                                      
                                  }
                                  
                                  return true;

                              }
                              @Override
                              public Class getColumnClass(int column) {
                                  switch (column) {
                                      case 0:
                                          return String.class;
                                      case 1:
                                          return Boolean.class;
                                      case 2:
                                          return Boolean.class;
                                      case 3:
                                          return Boolean.class;
                                      case 4:
                                          return Boolean.class;
                                      case 5:
                                          return Boolean.class;
                                      default:
                                          return Boolean.class;
                                  }
                                  
                              }
                            };
                            
                            table.setRowHeight(30);
                            // table.setLocation(10, 30);
                            //  table.setSize(400, 300);
                            //table.setPreferredScrollableViewportSize(table.getPreferredSize());

                             scrollPane = new JScrollPane(table);
                            table.setFillsViewportHeight(true);
                            scrollPane.setSize(form.getWidth()*40/100, form.getHeight()*50/100);
                            scrollPane.setLocation(form.getWidth()*5/100, form.getHeight()*5/100);
                            form.add(scrollPane); 
                            
                           
                        }
                   }
            }
            
   
    public String[] getClasses(){
            String classes="select name from class2";
            ResultSet r;
            String name="";
            ArrayList <String>list=new ArrayList();
            try {
            r = stmt.executeQuery(classes);
            while(r.next())
            {
                name=r.getString("name");
                list.add(name);

                
            }
            
            } catch (SQLException e) {
            }
        String [] names=new String [list.size()];
        for(int i=0;i<list.size();i++)
            names[i]=list.get(i);
        
        return names;
        }   
    
    
    // method to return all registered subjects for class
    public String []getSubjects(){
            ArrayList <String>list=new ArrayList<String>();
            String selectedClass=classes.getSelectedItem().toString();
            String subject="select SUBJECTNAME from classSubject2 where CLASSNAME='"+selectedClass+"'";
            ResultSet r;
            String name="";
          
            try {
            r = stmt.executeQuery(subject);
            while(r.next())
            {
                name=r.getString("SUBJECTNAME");
                list.add(name);
            }
            
            } catch (SQLException e) {
            }
            
            String [] names=new String [list.size()];
            for(int i=0;i<list.size();i++)
            names[i]=list.get(i);
            
            return names;
        }
    
    //method to return all teachers has the same speciality of a subject
    public String [] getTeachers(){
            ArrayList <String>list=new ArrayList<String>();
            String selectedSubject=subjects.getSelectedItem().toString();
            String teacher="select teacher.Name from teacher where teacher.SPECIALIST=(select SPECIALIST from supject where name='"+selectedSubject+"')";
            ResultSet r;
            String name="";
           
            try {
            r = stmt.executeQuery(teacher);
            while(r.next())
            {
                name=r.getString("name");
                list.add(name);

                
            }
            
            } catch (SQLException e) {
            }
            
            String [] names=new String [list.size()];
            for(int i=0;i<list.size();i++)
            names[i]=list.get(i);
            
            return names;
        }
    
    //method return all registerd shares for teacher
    public ArrayList<ArrayList> checkIfTeacherBusy(String teacherName){
            String busyShares="select SHARENUMBER , DAY   from shares where TEACHERNAME='"+teacherName+"'";
              ResultSet r;
              String number="";
              String day="";
              
              ArrayList<ArrayList> allData=new ArrayList<ArrayList>();
              ArrayList<String>listDay=new ArrayList<String>();
              ArrayList<String>listShareNumber=new ArrayList<String>();
              
              
              try {
              r = stmt.executeQuery(busyShares);
              while(r.next())
              {
                  number=r.getString("SHARENUMBER");
                  System.out.println("teacher number"+number);
                  listShareNumber.add(number);
                  day=r.getString("DAY");
                  System.out.println("teacher day"+day);
                listDay.add(day);
                 
              }
              } catch (SQLException ex) {
                  ex.getMessage();
              }
              
              allData.add(listDay);
              allData.add(listShareNumber);
            
              return allData;
        }
  
      public static void infoBox(String infoMessage, String titleBar)
          {
              JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
          }
    
  }
