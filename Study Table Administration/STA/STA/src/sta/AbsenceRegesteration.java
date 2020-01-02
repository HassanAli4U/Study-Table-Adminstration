package sta;

import java.awt.Color;
import java.awt.ComponentOrientation;
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

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import javax.swing.SwingConstants;

import oracle.jdbc.OracleDriver;

public class AbsenceRegesteration implements ActionListener{
    JFrame frame;
    JComboBox selectTeacher;
    JButton register;
    JTextField textFieldDate,textFieldDay;
    JLabel labelDate,labelDay;
    
    static Connection           conn=null;
    static Statement            stmt=null;
    static String        url="jdbc:oracle:thin:@localhost:1521:XE";
    public void display(){
        Main main=new Main();
            frame=new JFrame("تسجيل الغياب");
            frame.setSize(main.percent_width(50),main.percent_height(50));
            frame.setLocation(main.percent_width(30), main.percent_height(25));
            try {
                frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
            
            selectTeacher = new JComboBox(getAllteachers());
            selectTeacher.setSelectedIndex(-1);
            selectTeacher.setBackground(Color.white);
            selectTeacher.setSize(frame.getWidth()*60/100, frame.getHeight()*10/100);
            selectTeacher.setLocation(frame.getWidth()*20/100,frame.getHeight()*15/100);
            selectTeacher.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
            selectTeacher.setFont(new Font("Arial", Font.PLAIN, 15));
               frame.add(selectTeacher);
               
               
            register=new JButton("تسجيل");
            register.setSize(frame.getWidth()*20/100, frame.getHeight()*10/100);
            register.setLocation(frame.getWidth()*40/100, frame.getHeight()*70/100);
            register.setFont(new Font("Arial", Font.PLAIN, 30));
            register.setForeground(Color.white);
            register.setBackground(Color.black);
            register.addActionListener(this);
                    frame.add(register);
            
            
            
            labelDate = new JLabel("التاريخ",SwingConstants.RIGHT);
            labelDate.setSize(frame.getWidth()*15/100,frame.getHeight()*10/100);
            labelDate.setLocation(frame.getWidth()*80/100, frame.getHeight()*35/100);
            labelDate.setFont(new Font("Arial", Font.PLAIN, 25));
            frame.add(labelDate);
            
            labelDay = new JLabel("اليوم",SwingConstants.RIGHT);
            labelDay.setSize(frame.getWidth()*15/100,frame.getHeight()*10/100);
            labelDay.setLocation(frame.getWidth()*80/100, frame.getHeight()*50/100);
            labelDay.setFont(new Font("Arial", Font.PLAIN, 25));
            frame.add(labelDay);
            
            textFieldDate=new JTextField(SwingConstants.RIGHT);
            textFieldDate.setSize(frame.getWidth()*40/100, frame.getHeight()*10/100);
            textFieldDate.setLocation(frame.getWidth()*40/100, frame.getHeight()*35/100);
            textFieldDate.setFont(new Font("Arial", Font.PLAIN, 25));
            textFieldDate.setEnabled(false);
            textFieldDate.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            textFieldDate.addActionListener(this);
            frame.add(textFieldDate);
            
            
            textFieldDay=new JTextField(SwingConstants.RIGHT);
            textFieldDay.setSize(frame.getWidth()*40/100, frame.getHeight()*10/100);
            textFieldDay.setLocation(frame.getWidth()*40/100, frame.getHeight()*50/100);
            textFieldDay.setFont(new Font("Arial", Font.PLAIN, 25));
            textFieldDay.setEnabled(false);
            textFieldDay.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            textFieldDay.addActionListener(this);
            frame.add(textFieldDay);
            
            textFieldDate.setText(getCurrentDayAnDate()[0]);
            textFieldDay.setText(getCurrentDayAnDate()[1]);
            
            frame.setLayout(null);
            frame.setVisible(true);
        
        }
    
    //method to return all registered teachers
    public String[] getAllteachers(){
           
        openConnection();
           
            String name="";
            ArrayList<String>listNames=new ArrayList<String>();

        try {
           ResultSet r= stmt.executeQuery("select name from teacher");
            while(r.next())
            {
                name=r.getString("name");
                listNames.add(name);

                
            }
        } catch (SQLException e) {
        }
        
        String []arrayNames=new String [listNames.size()];
         for(int i=0;i<arrayNames.length;i++)
          arrayNames[i]=listNames.get(i);

        try {
            stmt.close();
            conn.close();

        } catch (SQLException e) {
        }
        return arrayNames;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==register)
        { openConnection();
            String date=getCurrentDayAnDate()[0];
            String day=getCurrentDayAnDate()[1];

       boolean registered=false;
       ArrayList<ArrayList>dateANdDay=dateAndDayForAbsents(selectTeacher.getSelectedItem().toString());
       ArrayList<String>registeredDate=dateANdDay.get(0);
       ArrayList<String>registeredDay=dateANdDay.get(1);
       
       
       //check if the teacher registered before in this date
       for(int i=0;i<registeredDate.size();i++)
           if(registeredDate.get(i).equalsIgnoreCase(date))
           {for(int j=0;j<registeredDay.size();j++)
                if(registeredDay.get(j).equalsIgnoreCase(day))
                { registered=true;
                   break;
                   
                   }
               }
       
       // if not register, register new absent into database
       if(registered==false)
       {
       openConnection();
       String addAbsence="insert into ABSENCE (TEACHERNAMES, DATES, DAYS) values ('"+selectTeacher.getSelectedItem().toString()+"', '"+date+"', '"+day+"')";

            try {
                stmt.execute(addAbsence);
                infoBox("success !", "done");
            } catch (SQLException f) {
            }
           closeConnection();
            
        }
       
       if(registered==true)
       {
           infoBox("teacher registered already  !!", "error !!");
           }
        }
       
    }
    
    
    //method to return all dates and days registerded for a teacher
    public ArrayList<ArrayList> dateAndDayForAbsents(String teacherName){
        String getdataAndDay="select DATES , DAYS from ABSENCE where TEACHERNAMES='"+teacherName+"'";
        ResultSet resultSet;
        String date="",day="";
        ArrayList<String>listDates=new ArrayList<String>();
        ArrayList<String>listDays=new ArrayList<String>();
        ArrayList<ArrayList>listAll=new ArrayList<ArrayList>();

        try {
            resultSet = stmt.executeQuery(getdataAndDay);
            while(resultSet.next())
            {
                date=resultSet.getString("DATES");
                listDates.add(date);
                System.out.println(date);
                day=resultSet.getString("DAYS");
                listDays.add(day);
                
            }
        } catch (SQLException e) {
        }
        listAll.add(listDates);
        listAll.add(listDays);
        return listAll;
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
    
    //method to return date and day at the runtime
    public String[] getCurrentDayAnDate(){
            DateFormat dateFormat=new SimpleDateFormat();
              Date date=new Date();
              String dateFormate=dateFormat.format(date);
              dateFormate=dateFormate.substring(0,8);
              
            Calendar cal=Calendar.getInstance();
            String engDay=new SimpleDateFormat("EEE").format(cal.getTime());
            System.out.println(dateFormate);
            System.out.println(engDay);
            String arabDay=null;
            if(engDay.equalsIgnoreCase("sat"))
                arabDay="السبت";
            else if(engDay.equalsIgnoreCase("sun"))
                arabDay="الاحد";
            else if(engDay.equalsIgnoreCase("mon"))
                arabDay="الاتنين";
            else if(engDay.equalsIgnoreCase("tue"))
                arabDay="الثلاثاء";
            
            else if(engDay.equalsIgnoreCase("wed"))
                arabDay="الاربعاء";
            
            else if(engDay.equalsIgnoreCase("thu"))
                arabDay="الخميس";
            else if(engDay.equalsIgnoreCase("fri"))
                arabDay="الجمعة";
            else
            {
                  infoBox("! not allowed day", "error"); 
                }
            String [] dayAndDate=new String[2];
            dayAndDate[0]=dateFormate;
            dayAndDate[1]=arabDay;
            return dayAndDate;
        
        }
    

}
