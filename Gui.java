//Imports are listed in full to show what's being used
	//could just import javax.swing.* and java.awt.* etc..
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
	public class Gui {


		
	public static void main(String[] args) throws SQLException {
	new Gui();
	}

	
	
	
	public Gui() throws SQLException
	{
	
		
		JFrame checkIn = new JFrame();
		JFrame receptionMan = new JFrame();
		
		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		
		//make sure the program exits when the frame closes
		checkIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkIn.setTitle("Example GUI");
		checkIn.setSize(900,700);
		checkIn.setLayout(null);
		checkIn.setLocationRelativeTo(null); 	//This will center the JFrame in the middle of the screen
		
		receptionMan.setSize(900,700);
		receptionMan.setTitle("Reception Dept.");
		receptionMan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		receptionMan.setLayout(null);
		receptionMan.setLocationRelativeTo(null);
	
		
		JButton employeesButton = new JButton( "Employees");	
		JButton expensesButton = new JButton( "Expenses");
		JButton receptionButton = new JButton( "Reception Dept.");
		JButton healthButton = new JButton( "Health Dept.");
		JButton entertainmentButton = new JButton( "Entertainment Dept.");
		JButton restaurantButton = new JButton( "Restaurant Dept.");
		JButton cleaningButton = new JButton( "Cleaning Dept.");
		JButton transportationButton = new JButton( "Transportation Dept.");
		JButton securityButton = new JButton( "Security Dept.");	
		JButton stockButton = new JButton( "Stocks");	
		JButton checkinButton = new JButton( "Check-In");	
		JButton checkoutButton = new JButton( "Check-Out");	
		JButton saveButton = new JButton( "Save");	
		

		//receptionButton.setBackground(Color.orange);
		
		
		// Giris
		
		 // kullanýcý giriþlerinde her EmployeeID sini girecek
		//EmployeeID'ye göredepartmaný belirlenip o departmanýn sayfasý ekrana gelecek
		
		String user = JOptionPane.showInputDialog("Çalýþan ID");
	
      
              	

		if(user.equals("beyza"))
		{
			 
		
			
			receptionMan.setVisible(true);
			
		}
		else {
			
			receptionMan.setVisible(false);
			JOptionPane.showMessageDialog(restaurantButton, "Hatalý Giriþ");
		
		}
	
		employeesButton.setBounds(20, 20, 200, 30);
		expensesButton.setBounds(20, 60, 200, 30);
		checkinButton.setBounds(20, 100, 200, 30);
		checkoutButton.setBounds(20, 140, 200, 30);
		
		
		receptionMan.add(employeesButton);// sadece departman müdürleri buna ulaþabilir
		receptionMan.add(expensesButton);// sadece departman müdürleri buna ulaþabilir
		receptionMan.add(checkinButton);
		receptionMan.add(checkoutButton);
		
		
		
		
		// checkin butonuna basýlýnca yeni bir sayfa aç
		//musteri bilgilerinin alýndýgý sayfa  / musteriye oda'nýn atandýgý yer
		
		checkinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
			
				/*JPopupMenu menu = new JPopupMenu("Menu");
				JMenuItem item = new JMenuItem("item");*/
				
				
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
				saveButton.setBounds(400, 250, 100, 30);
				
				checkIn.add(mus_No);
				checkIn.add(name);
				checkIn.add(last_name);
				checkIn.add(tel_no);
				checkIn.add(title);
				
				checkIn.add(name_Lab);
				checkIn.add(lastName_Lab);
				checkIn.add(telNo_Lab);
				checkIn.add(title_Lab);
				checkIn.add(saveButton);

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
