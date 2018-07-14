import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReceptionDepartment {

	JFrame checkIn = new JFrame();
	
	private JFrame frame;
	private JButton check_in;
	private JButton check_out;
	private JButton save;
	private JButton calisanekle;
	private JButton vardiyadegistir;
	private JButton expenses;
	
	public void showGUI7(boolean isMgr)
	{
		check_in = new JButton("Check-in");
		check_out = new JButton("Check-out");
		calisanekle = new JButton("Calisan Ekle");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		save = new JButton("Save");
		
		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame("ReceptionDepartmant");
		checkIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkIn.setSize(900,700);
		checkIn.setLayout(null);
		checkIn.setLocationRelativeTo(null); 
		
		
		frame.setSize(500,500);
		frame.setLayout(layout);

		frame.setVisible(true);
		frame.add(check_in);
		frame.add(check_out);

		if(isMgr)
		{
			frame.setVisible(true);
			frame.add(calisanekle);
			frame.add(vardiyadegistir);
			frame.add(expenses);
		}
		else {
			frame.setVisible(true);
			frame.add(check_in);
			frame.add(check_out);
			
			
			check_in.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					JTextField name = new JTextField();
					JTextField last_name = new JTextField();
					JTextField tel_no = new JTextField();
					JTextField title = new JTextField();
			
					
					JLabel name_Lab = new JLabel("Name : ");
					JLabel lastName_Lab = new JLabel("Last Name : ");
					JLabel telNo_Lab = new JLabel("Tel No : ");
					JLabel title_Lab = new JLabel("Title : ");
					
					JLabel mus_No = new JLabel("Guest Number 1 ");
					mus_No.setBounds(30, 30, 150, 30);
					
					name_Lab.setBounds(20, 60, 100, 30);
					lastName_Lab.setBounds(20, 100, 100, 30);
					telNo_Lab.setBounds(20, 140, 100, 30);
					title_Lab.setBounds(20, 180, 100, 30);
					
					
					name.setBounds(120, 60, 150, 30);
					last_name.setBounds(120, 100, 150, 30);
					tel_no.setBounds(120, 140, 150, 30);
					title.setBounds(120, 180, 150, 30);
					save.setBounds(400, 250, 100, 30);
					
					checkIn.add(mus_No);
					checkIn.add(name);
					checkIn.add(last_name);
					checkIn.add(tel_no);
					checkIn.add(title);
					
					checkIn.add(name_Lab);
					checkIn.add(lastName_Lab);
					checkIn.add(telNo_Lab);
					checkIn.add(title_Lab);
					checkIn.add(save);
	
					//musteri2
					JTextField name2 = new JTextField();
					JTextField last_name2 = new JTextField();
					JTextField tel_no2 = new JTextField();
					JTextField title2 = new JTextField();
			
					
					JLabel name_Lab2 = new JLabel("Name : ");
					JLabel lastName_Lab2 = new JLabel("Last Name : ");
					JLabel telNo_Lab2 = new JLabel("Tel No : ");
					JLabel title_Lab2 = new JLabel("Title : ");
					
					JLabel mus_No2 = new JLabel("Guest Number 2 ");
					mus_No2.setBounds(330, 30, 150, 30);
					
					name_Lab2.setBounds(320, 60, 100, 30);
					lastName_Lab2.setBounds(320, 100, 100, 30);
					telNo_Lab2.setBounds(320, 140, 100, 30);
					title_Lab2.setBounds(320, 180, 100, 30);
					
					
					name2.setBounds(420, 60, 150, 30);
					last_name2.setBounds(420, 100, 150, 30);
					tel_no2.setBounds(420, 140, 150, 30);
					title2.setBounds(420, 180, 150, 30);
					
					checkIn.add(mus_No2);
					checkIn.add(name2);
					checkIn.add(last_name2);
					checkIn.add(tel_no2);
					checkIn.add(title2);
					
					checkIn.add(name_Lab2);
					checkIn.add(lastName_Lab2);
					checkIn.add(telNo_Lab2);
					checkIn.add(title_Lab2);
	
					//musteri3
					JTextField name3 = new JTextField();
					JTextField last_name3 = new JTextField();
					JTextField tel_no3 = new JTextField();
					JTextField title3 = new JTextField();
			
					
					JLabel name_Lab3 = new JLabel("Name : ");
					JLabel lastName_Lab3 = new JLabel("Last Name : ");
					JLabel telNo_Lab3 = new JLabel("Tel No : ");
					JLabel title_Lab3 = new JLabel("Title : ");
					
					JLabel mus_No3 = new JLabel("Guest Number 3 ");
					mus_No3.setBounds(630, 30, 150, 30);
					
					name_Lab3.setBounds(620, 60, 100, 30);
					lastName_Lab3.setBounds(620, 100, 100, 30);
					telNo_Lab3.setBounds(620, 140, 100, 30);
					title_Lab3.setBounds(620, 180, 100, 30);
					
					
					name3.setBounds(720, 60, 150, 30);
					last_name3.setBounds(720, 100, 150, 30);
					tel_no3.setBounds(720, 140, 150, 30);
					title3.setBounds(720, 180, 150, 30);
					
					checkIn.add(mus_No3);
					checkIn.add(name3);
					checkIn.add(last_name3);
					checkIn.add(tel_no3);
					checkIn.add(title3);
					
					checkIn.add(name_Lab3);
					checkIn.add(lastName_Lab3);
					checkIn.add(telNo_Lab3);
					checkIn.add(title_Lab3);
	
					checkIn.setVisible(true);			
					
				}
			});
			
		
		}
		
		
		
		
	}
}
