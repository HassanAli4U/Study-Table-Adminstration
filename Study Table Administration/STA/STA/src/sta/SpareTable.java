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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import oracle.jdbc.OracleDriver;

public class SpareTable implements ActionListener{
    JFrame frame;
    JComboBox comboboxAbsentTeachers,conboboxAttendantTeachersWithSameSpeciality,comboboxOtherTeachers;
    
    JTable absentTable,AttendantTable ,AssignSharesTable;
    JScrollPane scrollPaneAbsent,scrollPaneAttendant,scrollPaneAssignShares;
    JButton save,cancel,getTodayAbsents,buttonGetselectedAbsence,buttonGetSelectedAttendantWithSameSpec;
    JLabel labelTeachersWithSameSpeciality,labelOtherTeachers;
    
    Object[] columnNames = {"","الاولى", "التانية", "الثالثة", "الرابعة", "الخامسة","السادسة","السابعة","الثامنة"};

    static Connection           conn=null;
    static Statement            stmt=null;
    static String        url="jdbc:oracle:thin:@localhost:1521:XE";
    public void display(){
        frame=new JFrame("اعداد جدول الاحتياطى");
        Main main=new Main();
        
        frame.setSize(main.percent_width(100),main.percent_height(100));
        try {
            frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
           
        getTodayAbsents=new JButton("غياب اليوم");
        getTodayAbsents.setSize(frame.getWidth()*20/100, frame.getHeight()*7/100);
        getTodayAbsents.setLocation(frame.getWidth()*75/100, frame.getHeight()*5/100);
        getTodayAbsents.setFont(new Font("Arial", Font.PLAIN, 30));
        getTodayAbsents.setForeground(Color.white);
        getTodayAbsents.setBackground(Color.black);
        getTodayAbsents.addActionListener(this);
                frame.add(getTodayAbsents);
        
       
        
       
        comboboxAbsentTeachers = new JComboBox();
        comboboxAbsentTeachers.setSelectedIndex(-1);
        comboboxAbsentTeachers.addActionListener(this);
        comboboxAbsentTeachers.setBackground(Color.white);
        comboboxAbsentTeachers.setSize(frame.getWidth()*25/100, frame.getHeight()*5/100);
        comboboxAbsentTeachers.setLocation(frame.getWidth()*70/100,frame.getHeight()*20/100);
        comboboxAbsentTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
        comboboxAbsentTeachers.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.add(comboboxAbsentTeachers);
        
           
        buttonGetselectedAbsence=new JButton("تم");
        buttonGetselectedAbsence.setSize(frame.getWidth()*15/100, frame.getHeight()*5/100);
        buttonGetselectedAbsence.setLocation(frame.getWidth()*80/100, frame.getHeight()*30/100);
        buttonGetselectedAbsence.setFont(new Font("Arial", Font.PLAIN, 30));
        buttonGetselectedAbsence.setForeground(Color.white);
        buttonGetselectedAbsence.setBackground(Color.black);
        buttonGetselectedAbsence.addActionListener(this);
                frame.add(buttonGetselectedAbsence);
        
        labelTeachersWithSameSpeciality = new JLabel("حضور من نفس التخصص",SwingConstants.CENTER);
        labelTeachersWithSameSpeciality.setSize(frame.getWidth()*20/100,frame.getHeight()*5/100);
        labelTeachersWithSameSpeciality.setLocation(frame.getWidth()*40/100, frame.getHeight()*12/100);
        labelTeachersWithSameSpeciality.setFont(new Font("Arial", Font.PLAIN, 25));
        frame.add(labelTeachersWithSameSpeciality);
        
        
        conboboxAttendantTeachersWithSameSpeciality = new JComboBox();
        conboboxAttendantTeachersWithSameSpeciality.setSelectedIndex(-1);
        conboboxAttendantTeachersWithSameSpeciality.addActionListener(this);
        conboboxAttendantTeachersWithSameSpeciality.setBackground(Color.white);
        conboboxAttendantTeachersWithSameSpeciality.setSize(frame.getWidth()*25/100, frame.getHeight()*5/100);
        conboboxAttendantTeachersWithSameSpeciality.setLocation(frame.getWidth()*37/100,frame.getHeight()*20/100);
        conboboxAttendantTeachersWithSameSpeciality.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
        conboboxAttendantTeachersWithSameSpeciality.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.add(conboboxAttendantTeachersWithSameSpeciality);
        
       
        
        labelOtherTeachers = new JLabel("حضور من تخصصات اخرى",SwingConstants.CENTER);
        labelOtherTeachers.setSize(frame.getWidth()*20/100,frame.getHeight()*5/100);
        labelOtherTeachers.setLocation(frame.getWidth()*10/100, frame.getHeight()*12/100);
        labelOtherTeachers.setFont(new Font("Arial", Font.PLAIN, 25));
        frame.add(labelOtherTeachers);
        
        comboboxOtherTeachers = new JComboBox();
        comboboxOtherTeachers.setSelectedIndex(-1);
        comboboxOtherTeachers.addActionListener(this);
        comboboxOtherTeachers.setBackground(Color.white);
        comboboxOtherTeachers.setSize(frame.getWidth()*25/100, frame.getHeight()*5/100);
        comboboxOtherTeachers.setLocation(frame.getWidth()*6/100,frame.getHeight()*20/100);
        comboboxOtherTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
        comboboxOtherTeachers.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.add(comboboxOtherTeachers);
        
        buttonGetSelectedAttendantWithSameSpec=new JButton("تم");
        buttonGetSelectedAttendantWithSameSpec.setSize(frame.getWidth()*15/100, frame.getHeight()*5/100);
        buttonGetSelectedAttendantWithSameSpec.setLocation(frame.getWidth()*26/100, frame.getHeight()*30/100);
        buttonGetSelectedAttendantWithSameSpec.setFont(new Font("Arial", Font.PLAIN, 30));
        buttonGetSelectedAttendantWithSameSpec.setForeground(Color.white);
        buttonGetSelectedAttendantWithSameSpec.setBackground(Color.black);
        buttonGetSelectedAttendantWithSameSpec.addActionListener(this);
                frame.add(buttonGetSelectedAttendantWithSameSpec);
        
        
        
        
       
                
                
        save=new JButton("حفظ");
        save.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
        save.setLocation(frame.getWidth()*35/100, frame.getHeight()*85/100);
        save.setFont(new Font("Arial", Font.PLAIN, 30));
        save.setForeground(Color.white);
        save.setBackground(Color.black);
        save.addActionListener(this);
                frame.add(save);
                
        cancel=new JButton("الغاء");
        cancel.setSize(frame.getWidth()*20/100, frame.getHeight()*5/100);
        cancel.setLocation(frame.getWidth()*10/100, frame.getHeight()*85/100);
        cancel.setFont(new Font("Arial", Font.PLAIN, 30));
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.black);
        cancel.addActionListener(this);
                frame.add(cancel);
                
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //frame.repaint();
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==getTodayAbsents)
        {
            if(comboboxAbsentTeachers.getItemAt(0)!=null)
            {comboboxAbsentTeachers.removeAllItems();
                }

                if(conboboxAttendantTeachersWithSameSpeciality.getItemAt(0)!=null)
                {conboboxAttendantTeachersWithSameSpeciality.removeAllItems();
                    }
                if(comboboxAbsentTeachers.getItemAt(0)!=null)
                {comboboxAbsentTeachers.removeAllItems();
                    }
                frame.repaint();
            AbsenceRegesteration absent=new AbsenceRegesteration();
            String date=absent.getCurrentDayAnDate()[0];
            String day=absent.getCurrentDayAnDate()[1];
                
                for(int i=0;i<getAbsentTeachers(date, day).length;i++)
                comboboxAbsentTeachers.addItem(getAbsentTeachers(date, day)[i]);
                comboboxAbsentTeachers.setSelectedIndex(-1);
                comboboxAbsentTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                frame.repaint();
                
                
              
                
                
            }
        
        
        if(e.getSource()==buttonGetselectedAbsence){
                if(absentTable!=null)
                    scrollPaneAbsent.remove(absentTable);
                if(scrollPaneAbsent!=null)
                 frame.remove(scrollPaneAbsent);
                if(conboboxAttendantTeachersWithSameSpeciality.getItemAt(0)!=null)
                {conboboxAttendantTeachersWithSameSpeciality.removeAllItems();
                    }
                if(comboboxOtherTeachers.getItemAt(0)!=null)
                {comboboxOtherTeachers.removeAllItems();
                    }
                frame.repaint();


                if(comboboxAbsentTeachers.getSelectedItem()!=null){
                    AbsenceRegesteration absence=new AbsenceRegesteration();
                    String day=absence.getCurrentDayAnDate()[1];
                    openConnection();
                String teacherName=comboboxAbsentTeachers.getSelectedItem().toString();
                ArrayList<String>teacherClasses=getClassesAndSharesForAbsent(teacherName, day).get(0);
                    ArrayList<String>teacherSharesNum=getClassesAndSharesForAbsent(teacherName, day).get(1);
                //    System.out.println(teacherSharesNum.get(0));
                ArrayList<String>teacherClasses2=getClassesAndSharesForAbsent(teacherName, day).get(0);;
                    for(int i=0;i<teacherClasses.size();i++)
                        for(int j=i;j<teacherClasses.size();j++)
                             if(teacherClasses.get(i).equals(teacherClasses.get(j)))
                             {teacherClasses.remove(j);
                                 }
                    
                    for(int j=0;j<teacherClasses2.size();j++)
                        System.out.println(teacherClasses2.get(j));
                    
                                  
                    
                    
                Object [][] data2=new Object[teacherClasses.size()][9];
                
                String[]array=new String [teacherClasses.size()];
                    for(int i=0;i<teacherClasses.size();i++)
                         array[i]=teacherClasses.get(i);
                    
                    TablesPrint print=new TablesPrint();
                array=print.RearrangementClasses(array);
                for(int i=0;i<array.length;i++)
                 data2[i][0]=array[i];
                
                 DefaultTableModel model2 = new DefaultTableModel(data2, columnNames){
                     @Override
                     public String getColumnName(int col) {
                         return (String) columnNames[col];
                     }
                 };
                 absentTable = new JTable(model2) {
                     
                     
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
                 absentTable.setRowHeight(30);
                 absentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                 absentTable.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
                 scrollPaneAbsent = new JScrollPane(absentTable);
                 absentTable.setFillsViewportHeight(true);
                 scrollPaneAbsent.setSize(frame.getWidth()*40/100, frame.getHeight()*30/100);
                 scrollPaneAbsent.setLocation(frame.getWidth()*55/100, frame.getHeight()*37/100);
                 frame.add(scrollPaneAbsent);
                 
                   
                 
                    for(int z=0;z<teacherClasses2.size();z++) 
                    for(int j=0;j<teacherClasses.size();j++) {
                    if(teacherClasses2.get(z).equals(absentTable.getModel().getValueAt(j, 0)))  
                    for(int i=1;i<9;i++){
                       {
                        if(Integer.parseInt(teacherSharesNum.get(z))==i)
                        { 
                               absentTable.setValueAt(teacherName, j, i);
                            System.out.println("j= "+j+"   ,i="+i);

                        }
                       

                       }
                    }
                        
                    }
                    if(conboboxAttendantTeachersWithSameSpeciality.getSelectedItem()!=null)
                        conboboxAttendantTeachersWithSameSpeciality.removeAllItems();
                        
                    if(comboboxOtherTeachers.getSelectedItem()!=null)
                        comboboxOtherTeachers.removeAllItems();
                    int count=0;
                    String []gettenTeachers=getTeachers(teacherName);
                    for(int i=0;i<gettenTeachers.length;i++)
                        if(!gettenTeachers[i].equals(teacherName))
                        { conboboxAttendantTeachersWithSameSpeciality.addItem(gettenTeachers[i]);
                        count++;
                        }
                    conboboxAttendantTeachersWithSameSpeciality.setSelectedIndex(-1);
                    conboboxAttendantTeachersWithSameSpeciality.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                    
                    String[]Allteacher=new TableModification().getAllTeachers();
                    for(int i=0;i<Allteacher.length;i++)
                        for(int j=0;j<count;j++)
                        if(!Allteacher[i].equals(teacherName)&&!Allteacher[i].equals(conboboxAttendantTeachersWithSameSpeciality.getItemAt(j)))
                        { comboboxOtherTeachers.addItem(Allteacher[i]);
                         break; 
                        }
                    
                    comboboxOtherTeachers.setSelectedIndex(-1);
                    comboboxOtherTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                    
                 closeConnection();
                  frame.repaint();
                }
          
            }
        
        
        
    if(e.getSource()==buttonGetSelectedAttendantWithSameSpec)
    {        
            
            
            
            
            if(AttendantTable!=null)
                scrollPaneAttendant.remove(AttendantTable);
            if(scrollPaneAttendant!=null)
             frame.remove(scrollPaneAttendant);
             frame.repaint();
             
             
             if(conboboxAttendantTeachersWithSameSpeciality.getSelectedItem()!=null&&comboboxOtherTeachers.getSelectedItem()!=null){
                 infoBox("من �?ضلك اختر مدرس واحد", "خطأ");
                     comboboxOtherTeachers.setSelectedIndex(-1);
                             comboboxOtherTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                     conboboxAttendantTeachersWithSameSpeciality.setSelectedIndex(-1);
                             conboboxAttendantTeachersWithSameSpeciality.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                 }
             
            
             
             else{
                String teacherName="";
                if(conboboxAttendantTeachersWithSameSpeciality.getSelectedItem()!=null)
                {
                 teacherName= conboboxAttendantTeachersWithSameSpeciality .getSelectedItem().toString(); 
                    }
                if(comboboxOtherTeachers.getSelectedItem()!=null)
                {
                 teacherName= comboboxOtherTeachers .getSelectedItem().toString(); 
                    }
                 /*
                comboboxOtherTeachers.setSelectedIndex(-1);
                        comboboxOtherTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                        comboboxOtherTeachers.setEnabled(false);
                */
                
                AbsenceRegesteration absence=new AbsenceRegesteration();
                String day=absence.getCurrentDayAnDate()[1];
                openConnection();
            ArrayList<String>teacherClasses=getClassesAndSharesForAbsent(teacherName, day).get(0);
                ArrayList<String>teacherSharesNum=getClassesAndSharesForAbsent(teacherName, day).get(1);
            //    System.out.println(teacherSharesNum.get(0));
            ArrayList<String>teacherClasses2=getClassesAndSharesForAbsent(teacherName, day).get(0);;
                for(int i=0;i<teacherClasses.size();i++)
                    for(int j=i;j<teacherClasses.size();j++)
                         if(teacherClasses.get(i).equals(teacherClasses.get(j)))
                         {teacherClasses.remove(j);
                             }
                
                for(int j=0;j<teacherClasses2.size();j++)
                    System.out.println(teacherClasses2.get(j));
                
                              
                
                
            Object [][] data2=new Object[teacherClasses.size()][9];
            
            String[]array=new String [teacherClasses.size()];
                for(int i=0;i<teacherClasses.size();i++)
                { array[i]=teacherClasses.get(i);
                        System.out.println("row "+array[i]);

                    }
                
                TablesPrint print=new TablesPrint();
            array=print.RearrangementClasses(array);
            for(int i=0;i<array.length;i++)
            {data2[i][0]=array[i];
            }
             DefaultTableModel model2 = new DefaultTableModel(data2, columnNames){
                 @Override
                 public String getColumnName(int col) {
                     return (String) columnNames[col];
                 }
             };
             AttendantTable = new JTable(model2) {
                 
                 
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
             AttendantTable.setRowHeight(30);
             AttendantTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
             AttendantTable.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
             scrollPaneAttendant = new JScrollPane(AttendantTable);
             AttendantTable.setFillsViewportHeight(true);
             scrollPaneAttendant.setSize(frame.getWidth()*40/100, frame.getHeight()*30/100);
             scrollPaneAttendant.setLocation(frame.getWidth()*5/100, frame.getHeight()*37/100);
             frame.add(scrollPaneAttendant);
             
                for(int z=0;z<teacherClasses2.size();z++) 
                for(int j=0;j<teacherClasses.size();j++) {
                if(teacherClasses2.get(z).equals(AttendantTable.getModel().getValueAt(j, 0)))  
                for(int i=1;i<9;i++){
                   {
                    if(Integer.parseInt(teacherSharesNum.get(z))==i)
                    { 
                           AttendantTable.setValueAt(teacherName, j, i);
                        System.out.println("j= "+j+"   ,i="+i);

                    }
                   

                   }
                }
                    
                }
            
            
        
        
            
            AbsenceRegesteration absence2=new AbsenceRegesteration();
            String day2=absence2.getCurrentDayAnDate()[1];
            openConnection();
            String absentTeacherName=comboboxAbsentTeachers.getSelectedItem().toString();

            ArrayList<String>teacherClasses3=getClassesAndSharesForAbsent(absentTeacherName, day2).get(0);
            //    System.out.println(teacherSharesNum.get(0));
           // ArrayList<String>teacherClasses2=getClassesAndSharesForAbsent(absentTeacherName, day).get(0);;
            for(int i=0;i<teacherClasses3.size();i++)
                for(int j=i;j<teacherClasses3.size();j++)
                     if(teacherClasses3.get(i).equals(teacherClasses3.get(j)))
                     {teacherClasses3.remove(j);
                         }
            
            Object [][] data3=new Object[teacherClasses3.size()][9];
            String[]array2=new String [teacherClasses3.size()];
                for(int i=0;i<teacherClasses3.size();i++)
                     array2[i]=teacherClasses3.get(i);
                
                TablesPrint print2=new TablesPrint();
            array2=print2.RearrangementClasses(array2);
            for(int i=0;i<array2.length;i++)
             data3[i][0]=array2[i];
            
            DefaultTableModel model3 = new DefaultTableModel(data3, columnNames){
                @Override
                public String getColumnName(int col) {
                    return (String) columnNames[col];
                }
            };
            
            AssignSharesTable = new JTable(model3) {
                
                
                @Override
                      public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                          Component component = super.prepareRenderer(renderer, row, column);
                          int rendererWidth = component.getPreferredSize().width;
                          TableColumn tableColumn = getColumnModel().getColumn(column);
                          tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                          
                          return component;
                       }
                
                
            public boolean isCellEditable(int row, int column){
                  
                  
                  
                  
                  
                  
                  
                  /*
                  
                  AbsenceRegesteration absence=new AbsenceRegesteration();
                  String day=absence.getCurrentDayAnDate()[1];
                  openConnection();
             
                  String absentTeacherName=comboboxAbsentTeachers.getSelectedItem().toString();

                  ArrayList<String>teacherSharesNum=getClassesAndSharesForAbsent(absentTeacherName, day).get(1);
                  ArrayList<String>teacherClasses=getClassesAndSharesForAbsent(absentTeacherName, day).get(0);
                  ArrayList<String>teacherClasses3=getClassesAndSharesForAbsent(absentTeacherName, day).get(0);
                                        String []array=new String[teacherClasses3.size()];
                  System.out.println("teacherClasses3  "+teacherClasses3.size());
                                        for(int i=0;i<teacherClasses3.size();i++)
                                        { array[i]=teacherClasses3.get(i);
                      System.out.println("array befor  "+array[i]);}
                  System.out.println("f size="+array.length);
                  ArrayList<String>teacherClasses2=getClassesAndSharesForAbsent(absentTeacherName, day).get(0);;
                  for(int i=0;i<teacherClasses.size();i++)
                      for(int j=i+1;j<teacherClasses.size();j++)
                           if(teacherClasses.get(i).equals(teacherClasses.get(j)))
                           {teacherClasses.remove(j);
                               }
                  System.out.println("b size="+array.length);
                  for(int i=0;i<array.length;i++)
                  { 
                  System.out.println("array after  "+array[i]);}
                  
                  
                  /*
                  for(int z=0;z<array.length;z++) 
                  for(int j=0;j<teacherClasses.size();j++) {
                  if(array[z].equals(AssignSharesTable.getModel().getValueAt(j, 0)))  
                  for(int i=1;i<9;i++){
                     {
                      if(Integer.parseInt(teacherSharesNum.get(z))==i)
                      {                   
                          if(row == j && column == i)
                            {
                              System.out.println("enabled "+ row+"   ");
                              return true; 
                                            }   

                      }
                     

                     }
                  }
                      
                  }
            
           
           
                  
                  /*
                  String attendanttTeacherName=conboboxAttendantTeachersWithSameSpeciality.getSelectedItem().toString();

                  ArrayList<String>teacherSharesNum2=getClassesAndSharesForAbsent(attendanttTeacherName, day).get(1);
                  ArrayList<String>teacherClasses3=getClassesAndSharesForAbsent(attendanttTeacherName, day).get(0);

                  //    System.out.println(teacherSharesNum.get(0));
                  ArrayList<String>teacherClasses4=getClassesAndSharesForAbsent(attendanttTeacherName, day).get(0);;
                  for(int i=0;i<teacherClasses3.size();i++)
                      for(int j=i+1;j<teacherClasses3.size();j++)
                           if(teacherClasses3.get(i).equals(teacherClasses3.get(j)))
                           {teacherClasses3.remove(j);
                               }
                  for(int z=0;z<teacherClasses4.size();z++) 
                  for(int j=0;j<teacherClasses3.size();j++) {
                  if(teacherClasses4.get(z).equals(AssignSharesTable.getModel().getValueAt(j, 0)))  
                  for(int i=1;i<9;i++){
                     {
                      if(Integer.parseInt(teacherSharesNum2.get(z))==i)
                      {                   
                          if(row == j && column == i)
                            {
                              return false;
                                            
                                            }   

                      }
                     

                     }
                  }
                      
                  }*/
                  
           
           
           
           
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
            AssignSharesTable.setRowHeight(30);
            AssignSharesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            AssignSharesTable.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
            scrollPaneAssignShares = new JScrollPane(AssignSharesTable);
            AssignSharesTable.setFillsViewportHeight(true);
            scrollPaneAssignShares.setSize(frame.getWidth()*40/100, frame.getHeight()*25/100);
            scrollPaneAssignShares.setLocation(frame.getWidth()*55/100, frame.getHeight()*68/100);
            frame.add(scrollPaneAssignShares);
        frame.repaint();
                 
                 conboboxAttendantTeachersWithSameSpeciality.setEnabled(false);
                 comboboxOtherTeachers.setEnabled(false);
             }
      
           
      
        }
       


    if(e.getSource()==save)
    {
        if(conboboxAttendantTeachersWithSameSpeciality.getSelectedItem()!=null||comboboxOtherTeachers.getSelectedItem()!=null)
        {
                String teachername="";
                 if(conboboxAttendantTeachersWithSameSpeciality.getSelectedItem()!=null)
                    teachername=conboboxAttendantTeachersWithSameSpeciality.getSelectedItem().toString();
            
            if(comboboxOtherTeachers.getSelectedItem()!=null)
                teachername=comboboxOtherTeachers.getSelectedItem().toString();
            
            
                AbsenceRegesteration absence=new AbsenceRegesteration();
                String date=absence.getCurrentDayAnDate()[0];
                
                System.out.println("rows  "+AssignSharesTable.getRowCount());
                System.out.println("col   "+AssignSharesTable.getColumnCount());
                openConnection();
                
                boolean found=false;
            for(int i=0;i<AssignSharesTable.getRowCount();i++)
                for(int j=1;j<AssignSharesTable.getColumnCount();j++)
                    if(AssignSharesTable.getValueAt(i, j)!=null&&AssignSharesTable.getValueAt(i, j).equals(true))
                    {  
                        System.out.println(AssignSharesTable.getValueAt(i, 0));
                        System.out.println("inner");
                        ResultSet resultSet;

                            try {
                               
                                resultSet = stmt.executeQuery("select  classname from sparetable where dates='"+date+"' and teachername='"+teachername+"' and classname='"+AssignSharesTable.getValueAt(i, 0)+"' and sharenumber='"+j+"'");
                                while(resultSet.next())
                                {
                                   if( resultSet.getString("classname")!=null)
                                   { infoBox("share number "+j+" registerd", "");
                                     found=true;
                                     break;
                                     }
                                }
                            } catch (SQLException f) {
                            }
                          
                    }
                
                if(found==false)
                {
                        for(int i=0;i<AssignSharesTable.getRowCount();i++)
                            for(int j=1;j<AssignSharesTable.getColumnCount();j++)
                                if(AssignSharesTable.getValueAt(i, j)!=null&&AssignSharesTable.getValueAt(i, j).equals(true))
                                {  
                                    try {
                                    
                                        stmt.executeUpdate("insert into sparetable (dates, teachername, classname, sharenumber) values('"+date+"',  '"+teachername+"', '"+AssignSharesTable.getValueAt(i, 0)+"','"+j+"')");
                                    
                                    } catch (SQLException f) {
                                    infoBox(f.getMessage(), "error !");
                                    }  
                                      
                                }
                    }
                
               
            }
     
        
        }
    if(e.getSource()==cancel)
       {
        conboboxAttendantTeachersWithSameSpeciality.setEnabled(true);
    
   
        buttonGetSelectedAttendantWithSameSpec.setEnabled(true);
    
    
    comboboxOtherTeachers.setEnabled(true);
    
    
    
    if(AttendantTable!=null)
        frame.remove(AttendantTable);
    if(scrollPaneAttendant!=null)
        frame.remove(scrollPaneAttendant);
    
        
        if(AssignSharesTable!=null)
            frame.remove(scrollPaneAssignShares);
        if(scrollPaneAssignShares!=null)
            frame.remove(scrollPaneAssignShares);
    
        conboboxAttendantTeachersWithSameSpeciality.setSelectedIndex(-1);
        conboboxAttendantTeachersWithSameSpeciality.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
        
        comboboxOtherTeachers.setSelectedIndex(-1);
        comboboxOtherTeachers.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
    frame.repaint();}
    }
    
    public String [] getAbsentTeachers(String date ,String day){
       openConnection();
       ResultSet resultSet;
        ArrayList<String> listAbsentTeachers =new ArrayList<String>();


        try {
            resultSet = stmt.executeQuery("select TEACHERNAMES from ABSENCE where DATES='"+date+"'"+" and DAYS='"+day+"'");
            while(resultSet.next())
            {
            String teacherName=resultSet.getString("TEACHERNAMES");
                listAbsentTeachers.add(teacherName);
            }
        
        } catch (SQLException e) {
        }
        String[] arrayTeacherNames=new String[listAbsentTeachers.size()];
        for(int i=0;i<arrayTeacherNames.length;i++)
        {
            arrayTeacherNames[i]=listAbsentTeachers.get(i);
            }
        return arrayTeacherNames;
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
public ArrayList<ArrayList> getClassesAndSharesForAbsent(String TeacherName,String day)
{
    ArrayList<String>listClasses=new ArrayList<String>();
        ArrayList<String>listSharesNumber=new ArrayList<String>();
        ArrayList<ArrayList>all=new ArrayList<ArrayList>();
openConnection();
        try {
           ResultSet resultSet = stmt.executeQuery("select CLASSNAME , SHARENUMBER from shares where TEACHERNAME='"+TeacherName+"'"+" and DAY='"+day+"'");
            while(resultSet.next())
            {
            String classes=resultSet.getString("CLASSNAME");
                listClasses.add(classes);
            System.out.println("classes   "+classes);
                String SHARENUMBER=resultSet.getString("SHARENUMBER");
                    listSharesNumber.add(SHARENUMBER);
            }
    } catch (SQLException e) {
        }
all.add(listClasses);
all.add(listSharesNumber);
return all;
    }
    public String [] getTeachers(String teacherName){
            ArrayList <String>list=new ArrayList<String>();
            String teacher="select teacher.Name from teacher where teacher.SPECIALIST=(select SPECIALIST from teacher where name='"+teacherName+"')";
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
    public static void infoBox(String infoMessage, String titleBar)
        {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }

}