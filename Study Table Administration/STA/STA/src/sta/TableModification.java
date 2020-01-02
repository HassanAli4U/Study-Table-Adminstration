package sta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import oracle.jdbc.OracleDriver;

public class TableModification implements ActionListener{
  Main main=new Main();
  JFrame frame;
  JComboBox ComboboxDelete,comboboxSelectClass;
  JComboBox arabic,english,comboboxMathSpeciality,comboboxSciencesSpeciality,comboboxSocialStudiesSpeciality,comboboxComputerSpeciality;
  JComboBox comboboxAllTeachers;
  JTable classDataTable,exchangeSharesTable;
  JScrollPane classDataScrollpane,exchangeSharesScrollpane;
  JButton save,buttonExchangeShares;
  
  
  
    Object[] columnNames = {"","الاولى", "التانية", "الثالثة", "الرابعة", "الخامسة","السادسة","السابعة","الثامنة"};

  
  
    static Connection           conn=null;
    static Statement            stmt=null;
    static String        url="jdbc:oracle:thin:@localhost:1521:XE";

        public TableModification(){
            try {
                DriverManager.registerDriver(new OracleDriver());
                conn=DriverManager.getConnection(url,"hr2","hr2");
                stmt = conn.createStatement();
            } catch (SQLException e) {
            }
            
            }
  public void display(){
          frame=new JFrame("تعديل الجدول العام");
          frame.setSize(main.percent_width(100),main.percent_height(100));
          try {
              frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
          } catch (IOException e) {
              System.out.print(e.getMessage());
          }
          
          

        
          
          
          buttonExchangeShares = new JButton("تبديل الحصص");
          buttonExchangeShares.addActionListener(this);
          buttonExchangeShares.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
          buttonExchangeShares.setLocation(frame.getWidth()*75/100, frame.getHeight()*5/100);
          buttonExchangeShares.setFont(new Font("Arial", Font.PLAIN, 20));
          buttonExchangeShares.setForeground(Color.black);
          buttonExchangeShares.setBackground(Color.white);
          frame.add(buttonExchangeShares);   
                  
                  
                  String[] deleteTypes={"حذف مادة","حذف مدرس","حذف فصل"};
          ComboboxDelete = new JComboBox(deleteTypes);
          ComboboxDelete.setSelectedIndex(-1);
          ComboboxDelete.addActionListener(this);
          ComboboxDelete.setBackground(Color.white);
          ComboboxDelete.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
          ComboboxDelete.setLocation(frame.getWidth()*75/100,frame.getHeight()*15/100);
          ComboboxDelete.setRenderer(new MyComboBoxRenderer("حذف"));
          ComboboxDelete.setFont(new Font("Arial", Font.PLAIN, 20));
             frame.add(ComboboxDelete);

         
          save=new JButton("حفظ");
          save.setSize(frame.getWidth()*20/100,frame.getHeight()*5/100);
          save.setLocation(frame.getWidth()*40/100,frame.getHeight()*80/100);
          save.addActionListener(this);
          save.setFont(new Font("Arial", Font.PLAIN, 30));
          save.setForeground(Color.white);
          save.setBackground(Color.black);
          frame.add(save);
          
          
          frame.setLayout(null);
          frame.setVisible(true);
      }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buttonExchangeShares)
        {
                if(ComboboxDelete!=null)
                frame.remove(ComboboxDelete);
                if(comboboxSelectClass!=null)
                frame.remove(comboboxSelectClass);
                if(classDataScrollpane!=null)
                frame.remove(classDataScrollpane);
                if(exchangeSharesScrollpane!=null)
                frame.remove(exchangeSharesScrollpane);
                if(arabic!=null)
                frame.remove(arabic);
                if(english!=null)
                frame.remove(english);
                if(comboboxMathSpeciality!=null)
                frame.remove(comboboxMathSpeciality);
                if(comboboxSocialStudiesSpeciality!=null)
                frame.remove(comboboxSocialStudiesSpeciality);
                if(comboboxSciencesSpeciality!=null)
                frame.remove(comboboxSciencesSpeciality);
                if(comboboxComputerSpeciality!=null)
                frame.remove(comboboxComputerSpeciality);
                if(comboboxAllTeachers!=null)
                frame.remove(comboboxAllTeachers);
                
                frame.repaint();
            {
               
                    AssigningShares assign=new AssigningShares();
                        String[] arrayclasses=assign.getClasses();
                        TablesPrint print=new TablesPrint();
                        comboboxSelectClass = new JComboBox(print.RearrangementClasses(arrayclasses));
                        comboboxSelectClass.setSelectedIndex(-1);
                        comboboxSelectClass.addActionListener(this);
                        comboboxSelectClass.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
                        comboboxSelectClass.setLocation(frame.getWidth()*75/100,frame.getHeight()*15/100);
                        comboboxSelectClass.setRenderer(new MyComboBoxRenderer("اختر فصل"));
                        comboboxSelectClass.setFont(new Font("Arial", Font.PLAIN, 15));
                        frame.add(comboboxSelectClass);
                        frame.repaint();
                    
                
                
                    
                }
           
            }
        if(e.getSource()==comboboxSelectClass&&save.getText().equals("حفظ")){
                
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                 /*   buttonExchangeShares.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
                   buttonExchangeShares.setLocation(frame.getWidth()*75/100, frame.getHeight()*5/100);
                   
                    comboboxSelectClass.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
                    comboboxSelectClass.setLocation(frame.getWidth()*75/100,frame.getHeight()*15/100);*/
                   // ComboboxDelete.setLocation(frame.getWidth()*85/100,frame.getHeight()*10/100);
                   if(classDataScrollpane!=null)
                   frame.remove(classDataScrollpane);
                   frame.repaint();
                    {
                        Object[][] data = {
                        {"السبت", false, false, false, false,false, false, false,false},
                        {"الاحد", false, false, false, false,false, false, false,false},
                        {"الاتنين", false, false, false, false,false, false, false,false},
                        {"الثلاثاء", false, false, false, false,false, false, false,false},
                        {"الاربعاء", false, false, false, false,false, false, false,false},
                        {"الخميس", false, false, false, false,false, false, false,false}
                        };
                        DefaultTableModel model = new DefaultTableModel(data, columnNames){
                       
                        @Override
                        public String getColumnName(int col) {
                            return (String) columnNames[col];
                        }
                        };
                        classDataTable = new JTable(model) {
                        public boolean isCellEditable(int row, int column){
                           
                              
                              return true;

                          }
                          @Override
                          public Class getColumnClass(int column) {
                              switch (column) {
                                  case 0:
                                      return String.class;
                                  default:
                                      return Boolean.class;
                              }
                              
                          }
                        };
                        classDataTable.setRowHeight(50);
                        // table.setLocation(10, 30);
                        //  table.setSize(400, 300);
                        //table.setPreferredScrollableViewportSize(table.getPreferredSize());

                         classDataScrollpane = new JScrollPane(classDataTable);
                        classDataTable.setFillsViewportHeight(true);
                        classDataScrollpane.setSize(frame.getWidth()*25/100, frame.getHeight()*50/100);
                        classDataScrollpane.setLocation(frame.getWidth()*2/100, frame.getHeight()*30/100);
                        frame.add(classDataScrollpane); 
               
               System.out.println(classDataTable.getModel().getValueAt(0, 2));
               
                       

                        Object[][] data2 = {
                        {"السبت"},
                        {"الاحد"},
                        {"الاتنين"},
                        {"الثلاثاء"},
                        {"الاربعاء"},
                        {"الخميس"}
                        };
                        DefaultTableModel model2 = new DefaultTableModel(data2, columnNames){
                        
                        @Override
                        public String getColumnName(int col) {
                            return (String) columnNames[col];
                        }
                        };
                        exchangeSharesTable = new JTable(model2) {
                            
                            
                            @Override
                                  public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                                      Component component = super.prepareRenderer(renderer, row, column);
                                      int rendererWidth = component.getPreferredSize().width;
                                      TableColumn tableColumn = getColumnModel().getColumn(column);
                                      tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                                      
                                      return component;
                                   }
                            
                            
                        public boolean isCellEditable(int row, int column){
                            
                            return false;
                          }
                        };
                        exchangeSharesTable.setRowHeight(50);
                        exchangeSharesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                        exchangeSharesTable.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
                        
                        
                        
                        
                        exchangeSharesScrollpane = new JScrollPane(exchangeSharesTable);
                        exchangeSharesTable.setFillsViewportHeight(true);
                        exchangeSharesScrollpane.setSize(frame.getWidth()*65/100, frame.getHeight()*50/100);
                        exchangeSharesScrollpane.setLocation(frame.getWidth()*30/100, frame.getHeight()*30/100);
                        frame.add(exchangeSharesScrollpane);
                        TablesPrint Tprint=new TablesPrint();
                        Tprint.getClassData(comboboxSelectClass.getSelectedItem().toString(), exchangeSharesTable);
                    frame.repaint();
                    }
                    exchangeSharesTable.repaint();
                    classDataTable.repaint();
                 frame.repaint();  
            
            }
        
       if(e.getSource()==ComboboxDelete)
       {
               save.setText("حذف");

           if(ComboboxDelete.getSelectedIndex()==0)
           {
               
               AddClass add=new AddClass();
              
                   String [] gettenArabicSubject=new String[add.getSubjects().get(0).size()];
                   String [] english_subject=new String[add.getSubjects().get(1).size()];
               
                   for(int i=0;i<gettenArabicSubject.length;i++)
                   gettenArabicSubject[i] = (String)add.getSubjects().get(0).get(i);
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
                   english_subject[i] = (String)add.getSubjects().get(1).get(i);
                   english = new JComboBox(english_subject);
                   english.setSelectedIndex(-1);
                   english.addActionListener(this);
                   english.setBackground(Color.white);

                   english.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
                   english.setLocation(frame.getWidth()*60/100,frame.getHeight()*33/100);
                   english.setRenderer(new MyComboBoxRenderer("اللغه الانجليزية"));
                   english.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(english);   
                      
                      
                      
                   String [] math_subject=new String[add.getSubjects().get(2).size()];
                   for(int i=0;i<math_subject.length;i++)
                   math_subject[i] = (String)add.getSubjects().get(2).get(i);
                   comboboxMathSpeciality = new JComboBox(math_subject);
                   comboboxMathSpeciality.setSelectedIndex(-1);
                   comboboxMathSpeciality.addActionListener(this);
                   comboboxMathSpeciality.setBackground(Color.white);

                   comboboxMathSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
                   comboboxMathSpeciality.setLocation(frame.getWidth()*80/100,frame.getHeight()*45/100);
                   comboboxMathSpeciality.setRenderer(new MyComboBoxRenderer("رياضيات"));
                   comboboxMathSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(comboboxMathSpeciality);


                      
                   String [] socialStudys_subject=new String[add.getSubjects().get(3).size()];
                   for(int i=0;i<socialStudys_subject.length;i++)
                   socialStudys_subject[i] = (String)add.getSubjects().get(3).get(i);
                   comboboxSocialStudiesSpeciality = new JComboBox(socialStudys_subject);
                   comboboxSocialStudiesSpeciality.setSelectedIndex(-1);
                   comboboxSocialStudiesSpeciality.addActionListener(this);
                   comboboxSocialStudiesSpeciality.setBackground(Color.white);

                   comboboxSocialStudiesSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
                   comboboxSocialStudiesSpeciality.setLocation(frame.getWidth()*60/100,frame.getHeight()*45/100);
                   comboboxSocialStudiesSpeciality.setRenderer(new MyComboBoxRenderer("دراسات اجتماعية"));
                   comboboxSocialStudiesSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(comboboxSocialStudiesSpeciality);
                      
                      
                   String [] science_subject=new String[add.getSubjects().get(4).size()];
                   for(int i=0;i<science_subject.length;i++)
                   science_subject[i] = (String)add.getSubjects().get(4).get(i);
                   comboboxSciencesSpeciality = new JComboBox(science_subject);
                   comboboxSciencesSpeciality.setSelectedIndex(-1);
                   comboboxSciencesSpeciality.addActionListener(this);
                   comboboxSciencesSpeciality.setBackground(Color.white);

                   comboboxSciencesSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
                   comboboxSciencesSpeciality.setLocation(frame.getWidth()*80/100,frame.getHeight()*57/100);
                   comboboxSciencesSpeciality.setRenderer(new MyComboBoxRenderer("علوم"));
                   comboboxSciencesSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(comboboxSciencesSpeciality);
                               
                               
                   String [] computers_subjects=new String[add.getSubjects().get(5).size()];
                   for(int i=0;i<computers_subjects.length;i++)
                   computers_subjects[i] = (String)add.getSubjects().get(5).get(i);
                   comboboxComputerSpeciality = new JComboBox(computers_subjects);
                   comboboxComputerSpeciality.setSelectedIndex(-1);
                   comboboxComputerSpeciality.addActionListener(this);
                   comboboxComputerSpeciality.setBackground(Color.white);

                   comboboxComputerSpeciality.setSize(frame.getWidth()*15/100, frame.getHeight()*7/100);
                   comboboxComputerSpeciality.setLocation(frame.getWidth()*60/100,frame.getHeight()*57/100);
                   comboboxComputerSpeciality.setRenderer(new MyComboBoxRenderer("حاسب ألى"));
                   comboboxComputerSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(comboboxComputerSpeciality);
                      
                      
                   //remove teachers combobox
                   if(comboboxAllTeachers!=null)
                   frame.remove(comboboxAllTeachers);
                   
                   if(comboboxSelectClass!=null)
                   frame.remove(comboboxSelectClass);
               frame.repaint();
               }
           //if selecteItem="حذ�? مدرس"
           if(ComboboxDelete.getSelectedIndex()==1)
           {
                  
                   comboboxAllTeachers = new JComboBox(getAllTeachers());
                   comboboxAllTeachers.setSelectedIndex(-1);
                   comboboxAllTeachers.addActionListener(this);
                   comboboxAllTeachers.setBackground(Color.white);
                   comboboxAllTeachers.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
                   comboboxAllTeachers.setLocation(frame.getWidth()*75/100,frame.getHeight()*25/100);
                   comboboxAllTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                   comboboxAllTeachers.setFont(new Font("Arial", Font.PLAIN, 15));
                      frame.add(comboboxAllTeachers);
                      
                   if(arabic!=null)
                   frame.remove(arabic);
                   if(english!=null)
                   frame.remove(english);
                   if(comboboxMathSpeciality!=null)
                   frame.remove(comboboxMathSpeciality);
                   if(comboboxSocialStudiesSpeciality!=null)
                   frame.remove(comboboxSocialStudiesSpeciality);
                   if(comboboxSciencesSpeciality!=null)
                   frame.remove(comboboxSciencesSpeciality);
                   if(comboboxComputerSpeciality!=null)
                   frame.remove(comboboxComputerSpeciality);
                   if(comboboxSelectClass!=null)
                       frame.remove(comboboxSelectClass);
                   frame.repaint();
               }
           if(ComboboxDelete.getSelectedIndex()==2)
           {
                   if(arabic!=null)
                   frame.remove(arabic);
                   if(english!=null)
                   frame.remove(english);
                   if(comboboxMathSpeciality!=null)
                   frame.remove(comboboxMathSpeciality);
                   if(comboboxSocialStudiesSpeciality!=null)
                   frame.remove(comboboxSocialStudiesSpeciality);
                   if(comboboxSciencesSpeciality!=null)
                   frame.remove(comboboxSciencesSpeciality);
                   if(comboboxComputerSpeciality!=null)
                   frame.remove(comboboxComputerSpeciality);
                   if(comboboxAllTeachers!=null)
                   frame.remove(comboboxAllTeachers);
                   frame.repaint();
               
                   AssigningShares assign=new AssigningShares();
                       String[] arrayclasses=assign.getClasses();               
                   TablesPrint print=new TablesPrint();
                   comboboxSelectClass = new JComboBox(print.RearrangementClasses(arrayclasses));
                   comboboxSelectClass.setSelectedIndex(-1);
                   comboboxSelectClass.addActionListener(this);
                   comboboxSelectClass.setBackground(Color.white);
                   comboboxSelectClass.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
                   comboboxSelectClass.setLocation(frame.getWidth()*75/100,frame.getHeight()*25/100);
                    comboboxSelectClass.setRenderer(new MyComboBoxRenderer("اختر فصل"));
                   comboboxSelectClass.setFont(new Font("Arial", Font.PLAIN, 15));
                   frame.add(comboboxSelectClass);
                   frame.repaint();
               }
           }
        if(e.getSource()==save&&save.getText().equals("حفظ"))
        {
            int count=0;
            int rowOfShareOne=-1,colOfShareOne=-1,rowOfShareTwo=-1,colOfShareTwo=-1;
                for(int row =0;row<6;row++)
                {if(count==2)
                     break;
                        for(int col=1;col<=8;col++)
                        {
                            if(count==2)
                                   break;
                                if(classDataTable.getModel().getValueAt(row, col).equals(true))
                                        {
                                    count++;
                                    if(rowOfShareOne==-1)
                                    {rowOfShareOne=row;
                                      colOfShareOne=col;
                                      System.out.println("rowOfShareOne="+rowOfShareOne);
                                        }
                                    else{
                                            rowOfShareTwo=row;
                                            colOfShareTwo=col;
                                            System.out.println("rowOfShareTwo="+rowOfShareTwo);
                                        }

                                        }
                                
                                        
                        }
                }
            getClassDataAndUbdate(comboboxSelectClass.getSelectedItem().toString(), classDataTable, rowOfShareOne, colOfShareOne, rowOfShareTwo, colOfShareTwo);
           
           
                TablesPrint Tprint=new TablesPrint();
                Tprint.getClassData(comboboxSelectClass.getSelectedItem().toString(), exchangeSharesTable);
                frame.repaint();
            }
        //check for delete subject
        if(e.getSource()==save&&save.getText().equals("حذف")&&ComboboxDelete.getSelectedIndex()==0)
        {
            System.out.println("inner");
        if(arabic.getSelectedItem()!=null)
            deleteSubject(arabic.getSelectedItem().toString());
            
        }
        //check for delete teacher
        if(e.getSource()==save&&save.getText().equals("حذف")&&ComboboxDelete.getSelectedIndex()==1)
        {
         if(comboboxAllTeachers.getSelectedItem()!=null)
         {
             System.out.println("teacher deleted");
        deleteTeacher(comboboxAllTeachers.getSelectedItem().toString());
        }
         
        }
        //check for delete class 
        if(e.getSource()==save&&save.getText().equals("حذف")&&ComboboxDelete.getSelectedIndex()==2)
        {
                System.out.println("class deleted");
           if(comboboxSelectClass.getSelectedItem()!=null)
           {
               deleteClass(comboboxSelectClass.getSelectedItem().toString());
               }
            }
        frame.repaint();
        
    }
    public String[] getAllTeachers(){
        
            ArrayList <String>listTeachersNames=new ArrayList<String>();
            String teacher="select Name from teacher";
            ResultSet r;
            String name="";
            
            try {
            r = stmt.executeQuery(teacher);
            while(r.next())
            {
                name=r.getString("name");
                listTeachersNames.add(name);

                
            }
            
            } catch (SQLException e) {
            }
            
            String [] names=new String [listTeachersNames.size()];
            for(int i=0;i<listTeachersNames.size();i++)
            names[i]=listTeachersNames.get(i);
            
            return names;
        }
    
    public void getClassDataAndUbdate(String getClass,JTable table2,int row1,int col1,int row2,int col2){
        
                 
                String busyShares="select SHARENUMBER , DAY , SUBJECTNAME , TEACHERNAME from shares where CLASSNAME='"+getClass+"'";
                  ResultSet r;
                  String number="";
                  String day="";
                  String subject="";
                  String teacher="";
                  ArrayList<String>listDay=new ArrayList<String>();
                  ArrayList<String>listShareNumber=new ArrayList<String>();
                  ArrayList<String>listSubject=new ArrayList<String>();
                  ArrayList<String>listTeacher=new ArrayList<String>();
                  
                  try {
                  r = stmt.executeQuery(busyShares);
                  while(r.next())
                  {
                      number=r.getString("SHARENUMBER");
                      listShareNumber.add(number);
                      day=r.getString("DAY");
                      listDay.add(day);
                      subject=r.getString("SUBJECTNAME");
                      listSubject.add(subject);
                      teacher=r.getString("TEACHERNAME");
                      listTeacher.add(teacher);
                  }
                  } catch (SQLException ex) {
                  }
                 
                 
                 String firstTeacher=null,secondTeacher=null,firstSubject=null,SecondSubject=null,day1=null,day2=null;
                 int shareNumber1=-1,shareNumber2=-1;
                 boolean dataFound=false;
                  for(int z=0;z<listDay.size();z++)
                      if(dataFound==false)
                  for(int j=0;j<=5;j++) {
                  if(listDay.get(z).equals(table2.getModel().getValueAt(j, 0))) 
                      if(row1==j||row2==j){
                      for(int i=1;i<9;i++){
                     {
                      if(Integer.parseInt(listShareNumber.get(z))==i)
                      { 
                          if(row1==j&&col1==i)
                          {
                              
                              shareNumber1=i;
                           day1=listDay.get(z);
                           System.out.println(day1=listDay.get(z));
                           firstTeacher=listTeacher.get(z);
                               System.out.println(firstTeacher=listTeacher.get(z));
                           firstSubject=listSubject.get(z);
                          
                           }
                          if(row2==j&&col2==i){
                              shareNumber2=i;
                              secondTeacher=listTeacher.get(z);
                              SecondSubject=listSubject.get(z);
                              System.out.println(secondTeacher);

                              day2=listDay.get(z);
                              }
                      }
                     }
                  }
                      
                  }
 
   }

if(firstSubject!=null&&firstTeacher!=null&&SecondSubject!=null&&secondTeacher!=null)
{

            try {
                stmt.executeUpdate("update shares set SUBJECTNAME='"+firstSubject+"' ,TEACHERNAME='"+firstTeacher+"' where CLASSNAME = '"+getClass+"' and SHARENUMBER = "+shareNumber2+" and DAY='"+day2+"'");
            } catch (SQLException e) {
            }
            try {
                stmt.executeUpdate("update shares set SUBJECTNAME='"+SecondSubject+"' ,TEACHERNAME='"+secondTeacher+"' where CLASSNAME = '"+getClass+"' and SHARENUMBER = "+shareNumber1+" and DAY='"+day1+"'");
            } catch (SQLException e) {
            }
        }
       
        }
    
    public void deleteSubject(String subjectName){
        try {
            stmt.executeUpdate("delete from supject where name='" +subjectName+"'" );
        } catch (SQLException e) {
        }
        try {
            stmt.executeUpdate("delete from shares where SUBJECTNAME='" +subjectName+"'" );
            System.out.println("deleted");
        } catch (SQLException e) {
        }
    }
    public void deleteTeacher(String teacherName){
        try {
            stmt.executeUpdate("delete from teacher where name='" +teacherName+"'" );
        } catch (SQLException e) {
        }
        try {
            stmt.executeUpdate("delete from shares where TEACHERNAME='" +teacherName+"'" );
            System.out.println("deleted");
        } catch (SQLException e) {
        }
    }
    
    public void deleteClass(String className){
        try {
            stmt.executeUpdate("delete from class2 where name='" +className+"'" );
        } catch (SQLException e) {
        }
        try {
            stmt.executeUpdate("delete from shares where CLASSNAME='" +className+"'" );
            System.out.println("deleted");
        } catch (SQLException e) {
        }
        
        
        try {
            stmt.executeUpdate("delete from classSubject2 where className='" +className+"'" );
            System.out.println("deleted");
        } catch (SQLException e) {
        }
    }

}
