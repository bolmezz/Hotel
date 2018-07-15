import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javassist.tools.framedump;
import net.bytebuddy.asm.Advice.Enter;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Giris {

	private int id;
	private String f_name;
	private String l_name;
	private int is_mng;
	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;




	public int getID() {return id;}

	public void setID(int id1) {	id = id1;}

	public int getMng() {return is_mng;}

	public void setMng(int mng1) {is_mng = mng1; }


//Database'e baðlantý kurulmasý;
	private  void Connection() {

		try {

			con = DriverManager.getConnection(
					"jdbc:postgresql://localhost/Hotel", "postgres",
					"08040094");


		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}
		if (con == null) {
			System.out.println("Failed to make connection!");
		}
	}


	
	public static void main(String args[]) throws SQLException
	{

		new Giris();
	}


	//employees tablosundan tüm çalýþanarýn id'leri çekilir :  "ids" arrayinde tutulur
	public ArrayList IDs() throws SQLException {
		
		Connection();

		ArrayList<Integer> ids =new ArrayList<Integer>();
		pst = con.prepareStatement("SELECT emp_id FROM employees");
		rs = pst.executeQuery();
		while (rs.next()) {

			ids.add(rs.getInt("emp_id"));


		}
		return ids;
	}

	//verilen id'ye sahip çalýþanýn manager olup aolmadýðýna bakar
	public int Mng(int id) throws SQLException {
		
		Connection();
		
		int mngr=0;
		ArrayList<Integer> ids =new ArrayList<Integer>();
		pst = con.prepareStatement("SELECT emp_id FROM employees where is_manager = 1");
		rs = pst.executeQuery();
		while (rs.next()) {
			ids.add(rs.getInt("emp_id"));


		}

		for(int i=0;i<ids.size();i++)
			if(id == ids.get(i))
				mngr = id;

		return mngr;
	}



	public Giris() throws SQLException
	{


		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		
		JFrame frame = new JFrame();
		CleaningDepartment cd = new CleaningDepartment();
		ReceptionDepartment rd = new ReceptionDepartment();
		RestaurantDepartment resd = new RestaurantDepartment();
		SecurityDepartment sd = new SecurityDepartment();
		TransportationDepartment td = new TransportationDepartment();
		EntertainmentDepartment ed = new EntertainmentDepartment();
		HealthDepartment hd = new HealthDepartment();
		GeneralManager gm = new GeneralManager();
		
		metod(cd,rd,resd,sd,td,ed,hd,gm);

	

		
	}//giris const.
	
	
	
	
	public void metod(CleaningDepartment cd, ReceptionDepartment rd, RestaurantDepartment resd,
			SecurityDepartment sd, TransportationDepartment td, EntertainmentDepartment ed, HealthDepartment hd, GeneralManager gm) throws HeadlessException, SQLException
	{
		
		
		// general admin'den þifre istenmesi;
		String passwrd = "admin";
		
		JFrame f=new JFrame("Password");    
	     final JLabel label = new JLabel();            
	     label.setBounds(20,150, 200,50);  
	     final JPasswordField value = new JPasswordField();   
	     value.setBounds(100,75,100,30);   
	        JLabel l2=new JLabel("Password:");    
	        l2.setBounds(20,75, 80,30);    
	        JButton b = new JButton("Login");  
	        b.setBounds(100,120, 80,30);    
	        final JTextField text = new JTextField();  
	        text.setBounds(100,20, 100,30);    
	                f.add(value); f.add(l2); f.add(b);  
	                f.setSize(300,300);    
	                f.setLayout(null);   
	                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                
	                   
		
		int user = Integer.parseInt(JOptionPane.showInputDialog(null,"ID : ","Giriþ",JOptionPane.INFORMATION_MESSAGE));{
			if(11000<user && user<17008 || user ==10000) {
			
			ArrayList<Integer> ids = IDs();
			

			for (int i = 0; i<ids.size(); i++) {

				//girilen id db'deyse hangi dept olduðu bulunur
				if(user == ids.get(i) ) {
					
					if(user == 10000) // general man.
					{
						System.out.println("General Manager");
						
						 f.setVisible(true); 
						 
						 b.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								String data = new String(value.getPassword());
								
								if(data.equals(passwrd))
								{
									try {
										
										gm.showGui10();
										
									} catch (SQLException e1) {
										
										e1.printStackTrace();
									}
								}//if
								
								else
								{
									try {
										
										f.dispose();
										metod(cd, rd, resd, sd, td, ed, hd, gm);
										
									} catch (HeadlessException | SQLException e1) {
										
										e1.printStackTrace();
									}
								}
								
							}//action
						});
						
					
						
						
					}//if user = general manager

					if(user/1000 == 11) {//cleaning Dept.

						if(user == Mng(user)) {
							System.out.println("Cleaning Manager");
							
							//BURADA ÞÝFRE AL !
	
							cd.showGUI1(true);
								break;
										
						
						}
						
						else//departman çalýþan sayfasý
						{
							
							cd.showGUI1(false);
							
						}

						System.out.println("cleaning");
						break;

					}
					else if(user/1000 == 12) {//Transport Dept.

						if(user == Mng(user)) {
							System.out.println("Transportation Manager");
	
							td.showGUI2(true);
								
							}
							
							else//departman çalýþan sayfasý
							{
								
								td.showGUI2(false);
								
							}
						
						System.out.println("Transport");
						break;
						}
						
					}
					else if(user/1000 == 13) {//Entertainment Dept.

						if(user == Mng(user)) {
							
							System.out.println("Entertainment Manager");
			
							ed.showGUI3(true);	
							
							}
							
							else//departman çalýþan sayfasý
							{
								
								ed.showGUI3(false);	
								
							}
						
						
						System.out.println("Ent.");
						break;
					}
				
					else if(user/1000 == 14) {//Restaurant Dept.

						if(user == Mng(user)) {
							System.out.println("Restaurant Manager");
							
	
								resd.showGUI4(true);
								
							}
							
							else//departman çalýþan sayfasý
							{
								
								resd.showGUI4(false);
								
							}
							
						
						System.out.println("Restaurant");
						break;
					}
				
					else if(user/1000 == 15) {//Health Dept.

						if(user == Mng(user)) {
							System.out.println("Health Manager");

								hd.showGUI5(true);
								
							}
							
							else//departman çalýþan sayfasý
							{
								
								hd.showGUI5(false);
								
							}
						
						
						System.out.println("Health");
						
						break;
						
					}
					else if(user/1000 == 16) {//Security Dept.

						if(user == Mng(user)) {
							System.out.println("Securty Manager");

								
							sd.showGUI6(true);
							}
							
							else//departman çalýþan sayfasý
							{
								
								sd.showGUI6(false);
								
							}
						
						
						System.out.println("Security");
						
						break;
						
					}
					
					else if(user/1000 == 17) {//Reception Dept.

						if(user == Mng(user)) {
							System.out.println("Reception Manager");

								
							rd.showGUI7(true);
							
							}
							
							else//departman çalýþan sayfasý
							{
								
								rd.showGUI7(false);
								
							}
						

						System.out.println("Reception");
						
						break;
					}


				
				
				
			}//for
			}//if

				else 
				{
					
					metod(cd,rd,resd,sd,td,ed,hd,gm);


				}

		

		}//int user
	

	
		
		
	}//metod
	
}
