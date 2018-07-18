import java.awt.BorderLayout;
import java.text.ParseException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.ComboBoxUI;

public class GeneralManager extends JFrame implements ActionListener {


	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;


	private JButton elemanEkle;
	private JButton managerAtama;
	private JButton expenses;
	private JButton dismiss;
	private JFrame frame;
	private JFrame frame2;
	private JFrame frame3;
	private JFrame frame4;

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


	// Numarasý verilen departmandaki en yüksek no'lu ID'yi döner;
	public int[] max_ID(int dept_no) throws SQLException
	{

		Connection();

		int[] ID = new int[1];

		pst = con.prepareStatement("SELECT max(emp_id) FROM employees WHERE dno ="+dept_no);
		rs = pst.executeQuery();

		while(rs.next())
			ID[0] = rs.getInt("max");

		return ID;


	}

	// Numarasý verilen departmandaki en yüksek no'lu ID'yi döner;
	public int[] min_ID(int dept_no) throws SQLException
	{

		Connection();

		int[] ID = new int[1];

		pst = con.prepareStatement("SELECT min(emp_id) FROM employees WHERE dno ="+dept_no);
		rs = pst.executeQuery();

		while(rs.next())
			ID[0] = rs.getInt("min");

		return ID;


	}



	public void add_emp_DB(int id1, String name, String lastName, String shift1, String days, String tc1, String addr, String sal1, String gender1, String bdate, String dno1) throws SQLException
	{


		int sal = Integer.parseInt(sal1);
		int gen = Integer.parseInt(gender1);
		int dno = Integer.parseInt(dno1);
		int shift = Integer.parseInt(shift1);
		String f_name = name;
		String l_name = lastName;
		String dayOf = days;
		String birth_date = bdate;
		String address = addr;
		int id = id1;




		// tc String veriliyo , db'de sorun çýkmýyor ama.
		// is_manager default olarak 0 atanýyor

		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("INSERT INTO employees VALUES ("+id+",'"+f_name+"','"+l_name+"',"+shift+",'"+dayOf+"',"
				+tc1+",'"+address+"',"+sal+","+gen+",'"+birth_date+"',0,"+dno+");");


		JOptionPane.showMessageDialog(frame2, "Kaydedildi !");

		frame2.dispose();
		showGui10();


	}




	public JFrame add_emp() throws SQLException
	{


		Connection();

		GridLayout layout = new GridLayout(16,16);
		frame2 = new JFrame("Add New Employee");
		//frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(1000,700);
		frame2.setLayout(layout);


		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField tcno = new JTextField();
		JTextField addr = new JTextField();
		JTextField salary = new JTextField();
		JTextField gender = new JTextField();
		JTextField bdate = new JTextField();
		JTextField deptNo = new JTextField();
		JTextField dayof = new JTextField();
		JTextField shift1 = new JTextField();

		JButton save = new JButton("Kaydet");
		save.setSize(20, 20);




		JLabel label1 = new JLabel("Ýsim :");
		JLabel label2 = new JLabel("Soyisim :");
		JLabel label3 = new JLabel("Tc No :");
		JLabel label4 = new JLabel("Adres :");
		JLabel label5 = new JLabel("Salary :");
		JLabel label6 = new JLabel("Gender (1/0):");
		JLabel label7 = new JLabel("Doðum Tarihi (yy-aa-gg):");
		JLabel label8 = new JLabel("Departman No :");
		JLabel label9 = new JLabel("Shift :");
		JLabel label10 = new JLabel("Day Offs :");






		frame2.add(label1);
		frame2.add(fname);

		frame2.add(label2);
		frame2.add(lname);

		frame2.add(label3);
		frame2.add(tcno);

		frame2.add(label4);
		frame2.add(addr);

		frame2.add(label5);
		frame2.add(salary);

		frame2.add(label6);
		frame2.add(gender);

		frame2.add(label7);
		frame2.add(bdate);

		frame2.add(label8);
		frame2.add(deptNo);

		frame2.add(label9);
		frame2.add(shift1);

		frame2.add(label10);
		frame2.add(dayof);

		frame2.add(save);






		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				String f_name = fname.getText();
				String l_name = lname.getText();
				String tc_no = tcno.getText();
				String address = addr.getText();
				String salary1 = salary.getText();
				String gender1 = gender.getText();
				String birth_date = bdate.getText();
				String d_no = deptNo.getText();
				String dayof1 = dayof.getText();
				String shift = shift1.getText();


				try {
					String max = deptNo.getText()+"999";

					if(max_ID(Integer.parseInt(d_no))[0] == Integer.parseInt(max) )
						System.out.println("Yeni ID üretilemiyor");

					add_emp_DB(max_ID(Integer.parseInt(d_no))[0]+1,f_name, l_name,shift,dayof1,tc_no, address, salary1, gender1, birth_date, d_no);

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});



		frame2.setVisible(true);


		return frame2;
	}


	//girilen dept no'nun manager'ýnýn ID'sini döner;
	public int[] getOldID(int dept_no) throws SQLException
	{

		Connection();

		int[] ID = new int[1];

		pst = con.prepareStatement("SELECT emp_id FROM employees WHERE dno ="+dept_no+"and is_manager=1");
		rs = pst.executeQuery();

		while(rs.next())
			ID[0] = rs.getInt("emp_id");

		return ID;


	}



	public ArrayList<Integer> getIDs(int dno) throws SQLException
	{

		Connection();

		ArrayList<Integer> ids =new ArrayList<Integer>();

		pst = con.prepareStatement("SELECT emp_id FROM employees where dno ="+dno);
		rs = pst.executeQuery();

		while (rs.next()) {

			ids.add(rs.getInt("emp_id"));


		}


		return ids;
	}


	//eski managr'ýn is_mng =0 yeni manager'ýn is_mg=1 yapýlýyor;

	public void setManager(int id,int old, int deptID) throws SQLException
	{

		//eklenen ýd'nin olup olmadýðýný kontrol et

		ArrayList<Integer> ids = getIDs(deptID);


		if(id>min_ID(deptID)[0]-1 && id<max_ID(deptID)[0]+1)
		{

			for(int i = 0; i< ids.size(); i++) {


				if(id == ids.get(i)) {

					System.out.println(ids.get(i)+","+i);

					Statement st =  (Statement) con.createStatement();
					((java.sql.Statement) st).executeUpdate("UPDATE employees SET is_manager = 0 Where emp_id ="+old+";");

					Statement st2 =  (Statement) con.createStatement();
					((java.sql.Statement) st2).executeUpdate("UPDATE employees SET is_manager = 1 Where emp_id ="+id+";");

					JOptionPane.showMessageDialog(frame3, "New manager assigned !");


					frame3.dispose();
					showGui10();

					break;
				}


			}//for

		}
		else {


			JOptionPane.showMessageDialog(frame3, "There is no such id equals to "+id+" !");

			frame3.dispose();
			assignManager();

		}


	}



	public void assignManager() throws NumberFormatException, SQLException
	{



		Connection();

		GridLayout layout = new GridLayout(16,16);
		frame3 = new JFrame("Assign New Manager");
		//frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setSize(500,500);
		frame3.setLayout(layout);


		JTextField deptID = new JTextField();
		JTextField manID = new JTextField();
		JTextArea oldID = new JTextArea();


		JButton  discharge = new JButton("Discharge");
		JButton assign = new JButton("Assign");

		JLabel label1 = new JLabel("Deptartment ID :");
		JLabel label2 = new JLabel("New manager ID :");
		JLabel label3 = new JLabel("Old manager ID :");

		frame3.add(label1);
		frame3.add(deptID);

		frame3.add(discharge);


		discharge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame3.add(label3);


				try {

					oldID.setText(getOldID(Integer.parseInt(deptID.getText()))[0]+"");
					frame3.add(oldID);

					frame3.setVisible(true);

				} catch (NumberFormatException | SQLException e1) {

					e1.printStackTrace();
				}

			}
		});




		frame3.add(label2);
		frame3.add(manID);

		frame3.add(assign);

		assign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//yeni id'yi alýp is_manageer = 1 yap
				//eðer ýd db'de yoksa hata mesajý bas



				try {

					oldID.setText(getOldID(Integer.parseInt(deptID.getText()))[0]+"");
					String newID = manID.getText();
					String old = oldID.getText();

					setManager(Integer.parseInt(newID), Integer.parseInt(old),Integer.parseInt(deptID.getText()));


				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}






			}
		});




		frame3.setVisible(true);

	}


	public int calculateComp(int id) throws SQLException
	{

		int comp=0;

		int sal = 0;
		String start="";

		pst = con.prepareStatement("SELECT salary FROM employees where emp_id ="+id);
		rs = pst.executeQuery();

		while(rs.next())
			sal= rs.getInt("salary");


		pst = con.prepareStatement("SELECT emp_start FROM employees where emp_id ="+id);
		rs = pst.executeQuery();

		while(rs.next())
			start = rs.getString("emp_start");


		Date simdikiZaman = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String zaman = df.format(simdikiZaman);


		if(start != "") {

			int startYear = Integer.parseInt(start.substring(0, 4));
			int todayYear = Integer.parseInt(zaman.substring(0, 4));

			int startMonth = Integer.parseInt(start.substring(5, 7));
			int todayMonth = Integer.parseInt(zaman.substring(5, 7));



			int yýlFark = todayYear - startYear;
			int ayFark = todayMonth - startMonth;

			if(yýlFark == 0)
				if(ayFark < 0)
					comp = sal/2;
				else
					comp = sal;

		}

		return comp;

	}


	public boolean dismissMan(int id) throws SQLException
	{
		Connection();
		int deptID = id/1000;
		ArrayList<Integer> ids = getIDs(deptID);


		if(id>min_ID(deptID)[0]-1 && id<max_ID(deptID)[0]+1)
		{

			int mng= 0;

			for(int i = 0; i< ids.size(); i++) {


				if(id == ids.get(i)) {

					System.out.println(ids.get(i));

					pst = con.prepareStatement("SELECT is_manager FROM employees where emp_id ="+id);
					rs = pst.executeQuery();

					while(rs.next())
						mng= rs.getInt("is_manager");

					if(mng == 1) {

						if(JOptionPane.showConfirmDialog(null, "You are now discharging a manager.", "WARNING",JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION)
						{
							Statement st =  (Statement) con.createStatement();
							((java.sql.Statement) st).executeUpdate("Delete from employees where emp_id = "+id);

							JOptionPane.showMessageDialog(frame4, "Manager ("+id+") has been discharged !");

							frame4.dispose();
							showGui10();

						}
						else
						{
							JOptionPane.showMessageDialog(frame4, "Manager has not been discharged !");

							frame4.dispose();
							showGui10();


						}

						return true;

					}//if=mng

				}
			}//for
		}

		return false;
	}


	public void dismissEmp(int id) throws SQLException
	{


		int deptID = id/1000;


		ArrayList<Integer> ids = getIDs(deptID);


		if(dismissMan(id) == false)
		{

			
			if(id>min_ID(deptID)[0]-1 && id<max_ID(deptID)[0]+1)
			{

				

				for(int i = 0; i< ids.size(); i++) {


					if(id == ids.get(i)) {

						System.out.println(ids.get(i)+","+i);




						/*pst = con.prepareStatement("SELECT is_manager FROM employees where emp_id ="+id);
					rs = pst.executeQuery();

					while(rs.next())
						mng= rs.getInt("is_manager");

					if(mng == 1) {

						if(JOptionPane.showConfirmDialog(null, "You are now discharging a manager.", "WARNING",JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION)
						{
							Statement st =  (Statement) con.createStatement();
							((java.sql.Statement) st).executeUpdate("Delete from employees where emp_id = "+id);

							JOptionPane.showMessageDialog(frame4, "Manager ("+id+") has been discharged !");

							frame4.dispose();
							showGui10();
							break;
						}
						else
						{
							JOptionPane.showMessageDialog(frame4, "Manager has not been discharged !");

							frame4.dispose();
							showGui10();
							break;

						}

					}//çalýþan manager'sa if'i
						 */
						//else// çalýþan manager deðilse
						//{
					

						Statement st =  (Statement) con.createStatement();
						((java.sql.Statement) st).executeUpdate("Delete from employees where emp_id = "+id);

						JOptionPane.showMessageDialog(frame4, "Employee ("+id+") has been discharged !");

						frame4.dispose();
						showGui10();

						
						break;
						//	}

					}//id eþitliðini kontrol eden if

					else if(i == ids.size()-1)
					{
						System.out.println(id);
						
						JOptionPane.showMessageDialog(frame4, "There is no such id equals to "+id+" !");

						frame4.dispose();
						dismissEmployee();
						break;
					}

				}//for

			}
			else {

				
				JOptionPane.showMessageDialog(frame4, "There is no such id equals to "+id+" !");

				frame4.dispose();
				dismissEmployee();

			}

		}//man deðilse
	}


	public void dismissEmployee()
	{


		Connection();

		GridLayout layout = new GridLayout(16,16);
		frame4 = new JFrame("Discharge Employee");
		//frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.setSize(500,500);
		frame4.setLayout(layout);


		JTextField empID = new JTextField();
		JTextArea tazminat = new JTextArea();


		JButton  discharge = new JButton("Discharge");
		JButton pay = new JButton("Pay");

		JLabel label1 = new JLabel("Employee ID :");
		JLabel label2 = new JLabel("Compensation :");

		frame4.add(label1);
		frame4.add(empID);

		frame4.add(discharge);


		discharge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame4.add(label2);
				frame4.add(tazminat);


				try {

					int id = Integer.parseInt(empID.getText());

					tazminat.setText(calculateComp(id)+" TL");

					dismissEmp(id);




				} catch (NumberFormatException | SQLException e1) {

					e1.printStackTrace();
				}

			}
		});



		frame4.setVisible(true);

	}






	public void showGui10() throws SQLException
	{

		elemanEkle = new JButton("Eleman Ekle");
		managerAtama = new JButton("Yönetici Ata");
		expenses = new JButton("Expenses");
		dismiss = new JButton("Eleman Çýkar");


		GridLayout layout = new GridLayout(4,2);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame("General Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(500,500);
		frame.setLayout(layout);

		frame.add(elemanEkle);
		frame.add(expenses);
		frame.add(managerAtama);
		frame.add(dismiss);


		elemanEkle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					add_emp();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});


		managerAtama.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					assignManager();

				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});




		dismiss.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dismissEmployee();


			}
		});


		frame.setVisible(true);



	}//showGui



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}


}
