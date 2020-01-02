package sta;

import java.awt.Component;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SupervisionTable implements ActionListener {
    JFrame frame;
    Main main=new Main();
    JComboBox Teacher , days;
    public void display(){
            frame=new JFrame("اضافة فصل");
            frame.setSize(main.percent_width(40),main.percent_height(50));
            frame.setLocation(main.percent_width(30), main.percent_height(25));
            try {
                frame. setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("D:\\java\\projects\\STA\\STA\\src\\3d1.jpg")))));
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
            
            
            Teacher = new JComboBox();
            Teacher.setSelectedIndex(-1);
            Teacher.addActionListener(this);
            Teacher.setSize(120,30);
            Teacher.setLocation(frame.getWidth()*60/100,frame.getHeight()*33/100);
            Teacher.setRenderer(new MyComboBoxRenderer("اختر مدرس"));
            Teacher.setFont(new Font("Arial", Font.PLAIN, 20));
               //environment.setEditable(false);
               frame.add(Teacher);
            
            
            days = new JComboBox();
            days.setSelectedIndex(-1);
            days.addActionListener(this);
            days.setSize(120,30);
            days.setLocation(frame.getWidth()*20/100,frame.getHeight()*33/100);
            days.setRenderer(new MyComboBoxRenderer("اختر يوم"));
            days.setFont(new Font("Arial", Font.PLAIN, 20));
               //environment.setEditable(false);
               frame.add(days);
            
            frame.setLayout(null);
            frame.setVisible(true);
        }

    public void actionPerformed(ActionEvent e) {
    }
   
}
