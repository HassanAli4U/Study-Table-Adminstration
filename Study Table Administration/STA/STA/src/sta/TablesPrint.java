package sta;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import com.sun.org.apache.xml.internal.serialize.Printer;

import java.awt.Component;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.File;
import java.io.IOException;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.text.MessageFormat;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.print.PrintService;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableCellRenderer;

import javax.swing.table.TableColumn;

import oracle.jdbc.OracleDriver;

public class TablesPrint implements ActionListener{
    JTable table;
    JScrollPane scrollPane;
    JFrame form;
    JButton print,getTableType,show;
    JComboBox tableType,Item;
    Object[] columnNames = {"","الاولى", "التانية", "الثالثة", "الرابعة", "الخامسة","السادسة","السابعة","الثامنة"};


    static Connection           conn=null;
    static Statement            stmt=null;
    static String        sql;
    static String        url="jdbc:oracle:thin:@localhost:1521:XE";
    public TablesPrint(){
            try {
                DriverManager.registerDriver(new OracleDriver());
                conn=DriverManager.getConnection(url,"hr2","hr2");
                stmt = conn.createStatement();
            } catch (SQLException e) {
            }
        }


    public void display(){
            form=new JFrame();
            Main main=new Main();
            form.setSize(main.percent_width(100), main.percent_height(100));
            try {
                form. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
            
            
            String [] type={"جدول فصل","جدول  مدرس"," جدول يومى شامل","جدول الاحتياطى اليومى"};
            tableType = new JComboBox(type);
            tableType.setSelectedIndex(-1);
            tableType.addActionListener(this);
            tableType.setSize(form.getWidth()*20/100, form.getHeight()*5/100);
            tableType.setLocation(form.getWidth()*78/100,form.getHeight()*10/100);
            tableType.setRenderer(new MyComboBoxRenderer("اختر جدول"));
            tableType.setFont(new Font("Arial", Font.PLAIN, 15));
               //environment.setEditable(false);
            
            form.add(tableType);
            
            
            getTableType=new JButton("تم");
            getTableType.setSize(form.getWidth()*5/100,form.getHeight()*5/100);
            getTableType.setLocation(form.getWidth()*70/100,form.getHeight()*10/100);
            getTableType.addActionListener(this);
            getTableType.setVisible(false);

            form.add(getTableType);
            
            
            
            Item = new JComboBox();
            Item.setSelectedIndex(-1);
            Item.addActionListener(this);
            Item.setSize(form.getWidth()*20/100, form.getHeight()*5/100);
            Item.setLocation(form.getWidth()*78/100,form.getHeight()*20/100);
            Item.setRenderer(new MyComboBoxRenderer("اختر مادة"));
            Item.setVisible(false);
            Item.setFont(new Font("Arial", Font.PLAIN, 15));
            form.add(Item);   
            
            show=new JButton("عرض");
            show.setSize(form.getWidth()*12/100,form.getHeight()*5/100);
            show.setLocation(form.getWidth()*86/100,form.getHeight()*30/100);
            show.addActionListener(this);
            form.add(show);
            
            
            print=new JButton("طباعة");
            print.setSize(form.getWidth()*12/100,form.getHeight()*5/100);
            print.setLocation(form.getWidth()*70/100,form.getHeight()*30/100);
            print.addActionListener(this);
            form.add(print);
            
            //
            /*
                    Object[][] data = {
                    {"السبت"},
                    {"الاحد"},
                    {"الاثنين"},
                    {"الثلاثاء"},
                    {"الاربعاء"},
                    {"الخميس"}
                };
                DefaultTableModel model = new DefaultTableModel(data, columnNames){
                   
                    @Override
                    public String getColumnName(int col) {
                        return (String) columnNames[col];
                    }
                };
                table = new JTable(model) {
                    public boolean isCellEditable(int row, int column){
                        
                        return false;
                      }
                };
                table.setRowHeight(30);
                 scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true); 
                scrollPane.setSize(form.getWidth()*40/100, form.getHeight()*50/100);
                scrollPane.setLocation(form.getWidth()*5/100, form.getHeight()*5/100);
                form.add(scrollPane);
*/
          //  setTableContent();
            
            
            form.setExtendedState(JFrame.MAXIMIZED_BOTH);
                form.setLayout(null);
                form.setVisible(true);
        }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==tableType)
        {
            if(tableType.getSelectedIndex()==3)
            {  getTableType.setVisible(false);
               Item.setVisible(false);
               }
            else
                getTableType.setVisible(true);
            
            }
        
        
        
        if(e.getSource()==getTableType){




            //remove items from item combobox
            Item.removeAllItems();
           
            
            
            if(tableType.getSelectedIndex()!=-1){
                if(scrollPane!=null)
                    form.remove(scrollPane);
                 if(Item.getSelectedItem()!=null)
                     Item.setVisible(true);


                int selectedIndex=tableType.getSelectedIndex();
                if(selectedIndex==0){
                    AssigningShares assign=new AssigningShares();
                        String[] arrayclasses=assign.getClasses();
                    //get registered classes
                    String []gettenClasses=RearrangementClasses(arrayclasses);
                    for(int i=0;i<gettenClasses.length;i++)
                    Item.addItem(gettenClasses[i]);
                    Item.setSelectedIndex(-1);
                    Item.setRenderer(new MyComboBoxRenderer("اختر �?صل"));
                    Item.setVisible(true);

                   
                }
                    if(selectedIndex==1){
                        //get registered classes
                        TableModification modify=new TableModification();
                        String []gettenTeachers=modify.getAllTeachers();
                        for(int i=0;i<gettenTeachers.length;i++)
                        Item.addItem(gettenTeachers[i]);
                        Item.setSelectedIndex(-1);
                        Item.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
                        Item.setVisible(true);

                       
                    }
                    if(selectedIndex==2)
                    {
                        String days[]={"السبت","الاحد","الاتنين","الثلاثاء","الاربعاء","الخميس"};
                        for(int i=0;i<days.length;i++)
                        Item.addItem(days[i]);
                        Item.setSelectedIndex(-1);
                        Item.setRenderer(new MyComboBoxRenderer("اختر يوم"));
                        Item.setVisible(true);

                    }
                }

           form.repaint();
            }
        if(e.getSource()==show){
            
            
            
            //fill table with class data
           
                
                
                //setting new table
       if(scrollPane!=null)
        form.remove(scrollPane);
        form.repaint();
        
        Object[][] data = {
        {"السبت"},
        {"الاحد"},
        {"الاتنين"},
        {"الثلاثاء"},
        {"الاربعاء"},
        {"الخميس"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames){
        
        @Override
        public String getColumnName(int col) {
            return (String) columnNames[col];
        }
        };
        table = new JTable(model) {
            
            
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
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        
        
       
       
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setSize(form.getWidth()*90/100, form.getHeight()*50/100);
        scrollPane.setLocation(form.getWidth()*3/100, form.getHeight()*40/100);
        form.add(scrollPane);
        
        
        //fill table with class data
        if(tableType.getSelectedIndex()==0){
        getClassData(Item.getSelectedItem().toString(),table);
    }
        if(tableType.getSelectedIndex()==1)
            getTeacherShares(Item.getSelectedItem().toString(), table);
        
                if(tableType.getSelectedIndex()==2)
                {
                    if(table!=null)
                        scrollPane.remove(table);
                 if(scrollPane!=null)
                     form.remove(scrollPane);
                     AssigningShares assign=new AssigningShares();
                         String[] arrayclasses=assign.getClasses();
                 Object [][] data2=new Object[RearrangementClasses(arrayclasses).length][9];
                 for(int i=0;i<RearrangementClasses(arrayclasses).length;i++)
                     data2[i][0]=RearrangementClasses(arrayclasses)[i];
                 
                     DefaultTableModel model2 = new DefaultTableModel(data2, columnNames){
                         @Override
                         public String getColumnName(int col) {
                             return (String) columnNames[col];
                         }
                     };
                     table = new JTable(model2) {
                         
                         
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
                     table.setRowHeight(30);
                     table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                     table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
                     scrollPane = new JScrollPane(table);
                     table.setFillsViewportHeight(true);
                     scrollPane.setSize(form.getWidth()*90/100, form.getHeight()*50/100);
                     scrollPane.setLocation(form.getWidth()*3/100, form.getHeight()*40/100);
                     form.add(scrollPane);
                     
                     
                     getAllForDay(Item.getSelectedItem().toString(),table);
                      form.repaint();
                     
                 }
                   if(tableType.getSelectedIndex()==3)
                   {
                       if(Item!=null)
                           Item.setVisible(false);
                       
                           if(table!=null)
                               scrollPane.remove(table);
                           if(scrollPane!=null)
                            form.remove(scrollPane);
                           
                           openConnection();
                           AbsenceRegesteration absence=new AbsenceRegesteration();
                           String [] dateAndDay =absence.getCurrentDayAnDate();
                           
                           infoBox(dateAndDay[0], "");
                           ArrayList<ArrayList> all=getspareTable(dateAndDay[0]);

                           
                           ArrayList<String>teachers=all.get(0);
                          
                           
                          
                           ArrayList<String>classes=all.get(1);
                           ArrayList<String>shares=all.get(2);
                           
                          
                           
                         TablesPrint print=new TablesPrint();
                         
                         
                                  
                           for(int i=0;i<classes.size();i++)
                               for(int j=i;j<classes.size();j++)
                                    if(classes.get(i).equals(classes.get(j)))
                                    {classes.remove(j);
                                        }
                         
                         
                           String []arrayClasses=new String[classes.size()];
                           for(int i=0;i<arrayClasses.length;i++)
                               arrayClasses[i]=classes.get(i);
                                   
                                   arrayClasses=print.RearrangementClasses(arrayClasses);
                                  
                          
                                  
                           Object [][] data2=new Object[arrayClasses.length][9];
                           for(int i=0;i<arrayClasses.length;i++)
                               data2[i][0]=arrayClasses[i];
                           
                               DefaultTableModel model2 = new DefaultTableModel(data2, columnNames){
                                   @Override
                                   public String getColumnName(int col) {
                                       return (String) columnNames[col];
                                   }
                               };
                               table = new JTable(model2) {
                                   
                                   
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
                               table.setRowHeight(30);
                               table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                               table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
                               scrollPane = new JScrollPane(table);
                               table.setFillsViewportHeight(true);
                               scrollPane.setSize(form.getWidth()*50/100, form.getHeight()*40/100);
                               scrollPane.setLocation(form.getWidth()*10/100, form.getHeight()*25/100);
                               form.add(scrollPane); 
                                 
                                   
                                   
                           ArrayList<ArrayList> all2=getspareTable(dateAndDay[0]);
                           ArrayList<String>classes2=all2.get(1);
                           for(int z=0;z<classes2.size();z++)
                           for(int j=0;j<arrayClasses.length;j++) {
                           if(classes2.get(z).equals(table.getModel().getValueAt(j, 0)))  
                           for(int i=1;i<9;i++){
                              {
                               if(Integer.parseInt(shares.get(z))==i)
                               { 
                                  table.setValueAt("أ/"+teachers.get(z), j, i);
                               }
                              }
                           }
                               
                           }
                                   
                                 form.repaint();  
                       }
        
            }
        if(e.getSource()==print){
//                PrinterJob pjob = PrinterJob.getPrinterJob();
//                PageFormat preformat = pjob.defaultPage();
//                preformat.setOrientation(PageFormat.LANDSCAPE);
//                PageFormat postformat = pjob.pageDialog(preformat);
//                //If user does not hit cancel then print.
//                if (preformat != postformat) {
//                    //Set print component
//                    pjob.setPrintable(new printOrder(scrollPane), postformat);
//                    if (pjob.printDialog()) {
//                    try {
//                        pjob.print();
//                    } catch (PrinterException f) {
//                    }
//                }
//                }


            try {
    boolean complete = table.print();
    if (complete) {
        /* show a success message  */
        infoBox("printed", "");
    } else {
        /*show a message indicating that printing was cancelled */
        
    }
} catch (PrinterException pe) {
    /* Printing failed, report to the user */
    infoBox("error", "");
}
    }
    }
    public void getClassData(String getClass,JTable table2){
        
                 
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
                 
                  for(int z=0;z<listDay.size();z++)
                  for(int j=0;j<=5;j++) {
                  if(listDay.get(z).equals(table2.getModel().getValueAt(j, 0)))  
                  for(int i=1;i<9;i++){
                     {
                      if(Integer.parseInt(listShareNumber.get(z))==i)
                      { 
                         table2.setValueAt(listSubject.get(z)+"\nأ/"+listTeacher.get(z), j, i);
                      }
                     }
                  }
                      
                  }
            
        }
    
    public void getTeacherShares(String teacherName,JTable table){
             
            String busyShares="select SHARENUMBER , DAY , CLASSNAME ,SUBJECTNAME  from shares where TEACHERNAME='"+teacherName+"'";
              ResultSet r;
              String number="";
              String day="";
              String subject="";
              String classes="";
              ArrayList<String>listDay=new ArrayList<String>();
              ArrayList<String>listShareNumber=new ArrayList<String>();
              ArrayList<String>listSubject=new ArrayList<String>();
              ArrayList<String>listclasses=new ArrayList<String>();
              
              try {
              r = stmt.executeQuery(busyShares);
              while(r.next())
              {
                  number=r.getString("SHARENUMBER");
                  System.out.println(number);
                  listShareNumber.add(number);
                  day=r.getString("DAY");
                  System.out.println(day);

                  listDay.add(day);
                  subject=r.getString("SUBJECTNAME");
                  System.out.println(subject);

                  listSubject.add(subject);
                  classes=r.getString("CLASSNAME");
                  listclasses.add(classes);
              }
              } catch (SQLException ex) {
              }
            // System.out.println(listclasses.get(0)+"   "+listSubject.get(0));
              for(int z=0;z<listDay.size();z++)
              for(int j=0;j<=5;j++) {
              if(listDay.get(z).equals(table.getModel().getValueAt(j, 0)))  
              for(int i=1;i<9;i++){
                 {
                  if(Integer.parseInt(listShareNumber.get(z))==i)
                  { if(table.getValueAt(j, i)!=null)
                     table.setValueAt(table.getValueAt(j, i).toString()+listclasses.get(z)+"\nأ/"+listSubject.get(z), j, i);
                      else
                         table.setValueAt(listclasses.get(z)+"\n"+listSubject.get(z), j, i);
                  }
                 

                 }
              }
                  
              }
        }
    public String [] RearrangementClasses(String [] classes){
           
                for(int i=0;i<classes.length;i++){
                for(int j=i+1;j<classes.length;j++)
                {int firstRow= Character.getNumericValue(classes[i].charAt(0));
                    
                    int firstCol= Character.getNumericValue(classes[i].charAt(2));
                        int secondtRow= Character.getNumericValue(classes[j].charAt(0));
                        int secondCol= Character.getNumericValue(classes[j].charAt(2));

                    if(secondtRow<firstRow)
                    {
                        String temp=classes[i];
                        classes[i]=classes[j];
                        classes[j]=temp;
                        }
                    if((secondtRow)==firstRow)
                    {
                      if(secondCol<firstCol)
                      {
                          String temp=classes[i];
                          classes[i]=classes[j];
                          classes[j]=temp; 
                      }
                    }
                    }
                
                }
        return classes;
        
        }
    
    public void getAllForDay(String getDay,JTable table){
            String busyShares="select SHARENUMBER , TEACHERNAME , CLASSNAME ,SUBJECTNAME  from shares where day='"+getDay+"'";
              ResultSet r;
              String number="";
              String subject="";
              String classes="";
              String teacher="";
              ArrayList<String>listShareNumber=new ArrayList<String>();
              ArrayList<String>listSubject=new ArrayList<String>();
            ArrayList<String>listTeachers=new ArrayList<String>();
              ArrayList<String>listclasses=new ArrayList<String>();
              
              try {
              r = stmt.executeQuery(busyShares);
              while(r.next())
              {
                  number=r.getString("SHARENUMBER");
                  System.out.println(number);
                  listShareNumber.add(number);
                  teacher=r.getString("TEACHERNAME");

                  listTeachers.add(teacher);
                  subject=r.getString("SUBJECTNAME");

                  listSubject.add(subject);
                  classes=r.getString("CLASSNAME");
                  listclasses.add(classes);
              }
              } catch (SQLException ex) {
              }
            // System.out.println(listclasses.get(0)+"   "+listSubject.get(0));
            AssigningShares assign=new AssigningShares();
                String[] arrayclasses=assign.getClasses();
              for(int z=0;z<listclasses.size();z++)
              for(int j=0;j<RearrangementClasses(arrayclasses).length;j++) {
              if(listclasses.get(z).equals(table.getModel().getValueAt(j, 0)))  
              for(int i=1;i<9;i++){
                 {
                  if(Integer.parseInt(listShareNumber.get(z))==i)
                  { if(table.getValueAt(j, i)!=null)
                    { table.setValueAt(table.getValueAt(j, i).toString()+listclasses.get(z)+"\nأ/"+listSubject.get(z), j, i);
                      }
                      else
                         table.setValueAt(listSubject.get(z)+"\n"+listTeachers.get(z), j, i);
                      System.out.println("j= "+j+"   ,i="+i);

                  }
                 

                 }
              }
                  
              }
        
        }
    public ArrayList<ArrayList>getspareTable(String date){
        ArrayList<String>listTeacherName=new ArrayList<String>();
            ArrayList<String>listClasses=new ArrayList<String>();
            ArrayList<String>listSharesNumber=new ArrayList<String>();
        ArrayList<ArrayList>listAll=new ArrayList<ArrayList>();

            String teacherName="";
            String classes="";
            String shareNumber="";
           ResultSet resultSet;
        try {
            resultSet = stmt.executeQuery("select TEACHERNAME, CLASSNAME, SHARENUMBER from sparetable where DATES='"+date+"'");
            while(resultSet.next())
            {
                teacherName=resultSet.getString("TEACHERNAME");
                    listTeacherName.add(teacherName);
                    classes=resultSet.getString("CLASSNAME");
                    listClasses.add(classes);
                    shareNumber=resultSet.getString("SHARENUMBER");
                    listSharesNumber.add(shareNumber);
                }
            listAll.add(listTeacherName);
            listAll.add(listClasses);
            listAll.add(listSharesNumber);
        } catch (SQLException e) {
        }
        
        return listAll;
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
    
    public static void infoBox(String infoMessage, String titleBar)
        {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
}
