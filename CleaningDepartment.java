import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
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

public class CleaningDepartment extends JFrame implements ActionListener {

	private JButton kirliodalar;
	private JButton calisanekle;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JFrame frame;
	private JFrame frame2;
	private JFrame frame3;
	private JFrame frame4;




	private int room_no;
	private int cleeanliness;
	private int room_floor;
	private int room_type;
	private int towel_room;
	private int soap_room;
	private int shamp_room;
	private int water_room;

	//cleaning_dept tablosundan çekilecek bilgiler
	private int detergent_amount;
	private int shampoo_amount;
	private int soap_amount;
	private int towel_amount;
	private int gloves_amount;

	//stocks tablosundan çekilecek bilgiler
	private int detergent_price;
	private int shampoo_price;
	private int soap_price;
	private int towel_price;
	private int gloves_price;




	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;



	public int getDetergent_amount() {
		return detergent_amount;
	}

	public void setDetergent_amount(int detergent_amount) {
		this.detergent_amount = detergent_amount;
	}

	public int getShampoo_amount() {
		return shampoo_amount;
	}

	public void setShampoo_amount(int shampoo_amount) {
		this.shampoo_amount = shampoo_amount;
	}

	public int getSoap_amount() {
		return soap_amount;
	}

	public void setSoap_amount(int soap_amount) {
		this.soap_amount = soap_amount;
	}

	public int getTowel_amount() {
		return towel_amount;
	}

	public void setTowel_amount(int towel_amount) {
		this.towel_amount = towel_amount;
	}

	public int getGloves_amount() {
		return gloves_amount;
	}

	public void setGloves_amount(int gloves_amount) {
		this.gloves_amount = gloves_amount;
	}

	public int getDetergent_price() {
		return detergent_price;
	}

	public void setDetergent_price(int detergent_price) {
		this.detergent_price = detergent_price;
	}

	public int getShampoo_price() {
		return shampoo_price;
	}

	public void setShampoo_price(int shampoo_price) {
		this.shampoo_price = shampoo_price;
	}

	public int getSoap_price() {
		return soap_price;
	}

	public void setSoap_price(int soap_price) {
		this.soap_price = soap_price;
	}

	public int getTowel_price() {
		return towel_price;
	}

	public void setTowel_price(int towel_price) {
		this.towel_price = towel_price;
	}

	public int getGloves_price() {
		return gloves_price;
	}

	public void setGloves_price(int gloves_price) {
		this.gloves_price = gloves_price;
	}


	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public int getRoom_type() {
		return room_type;
	}

	public void setRoom_type(int room_type) {
		this.room_type = room_type;
	}

	public int getTowel_room() {
		return towel_room;
	}

	public void setTowel_room(int towel_room) {
		this.towel_room = towel_room;
	}

	public int getSoap_room() {
		return soap_room;
	}

	public void setSoap_room(int soap_room) {
		this.soap_room = soap_room;
	}

	public int getShamp_room() {
		return shamp_room;
	}

	public void setShamp_room(int shamp_room) {
		this.shamp_room = shamp_room;
	}

	public int getWater_room() {
		return water_room;
	}

	public void setWater_room(int water_room) {
		this.water_room = water_room;
	}




	public CleaningDepartment(){}

	private  void Connection() {

		try {

			con = DriverManager.getConnection(
					"jdbc:postgresql://localhost/Hotel", "postgres",
					"123412");


		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}
		if (con == null) {
			System.out.println("Failed to make connection!");
		}
	}


	//kirli odalarý listeler;
	//cleanlines == 0 = KÝRLÝ ODA

	public int[] dirtyRooms() throws SQLException {

		Connection();

		int[] rooms =new int[30];
		pst = con.prepareStatement("SELECT room_no FROM rooms where cleanliness = 1");
		rs = pst.executeQuery();

		int count =-1;
		while (rs.next()) {

			count ++;
			rooms[count] = rs.getInt("room_no");


		}
		return rooms;
	}






	// kirli odalarý listeler ;

	public JFrame dirty()
	{


		GridLayout layout = new GridLayout(6,6);
		frame2 = new JFrame("Dirty Rooms");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(500,500);
		frame2.setLayout(layout);




		int[] room = new int[30];

		String[] room2 = new String[30];

		try {
			room = dirtyRooms();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(int i = 0; i<room.length; i++) {
			room2[i] = String.valueOf(room[i]);

			//System.out.println(room2[i]);

		}

		JComboBox cmbRooms = new JComboBox(room2);
		JLabel lblText = new JLabel();


		cmbRooms.setSelectedIndex(1);
		cmbRooms.addActionListener(this);
		frame2.add(cmbRooms);
		frame2.add(lblText);



		frame2.setVisible(true);
		return frame2;

	}




	public int[] det_amount() throws SQLException {

		Connection();

		int[] det_amount = new int[1];
		pst = con.prepareStatement("SELECT detergent_amount FROM cleaning_dept");
		rs = pst.executeQuery();

		while(rs.next())
			det_amount[0] = rs.getInt("detergent_amount");


		return det_amount;
	}


	public int[] det_price() throws SQLException
	{

		int[] det_price = new int[1];

		pst = con.prepareStatement("SELECT detergent_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			det_price[0] = rs.getInt("detergent_price");

		return det_price;

	}





	public int[] shamp_amount() throws SQLException {

		Connection();

		int[] shamp_amount = new int[1];
		pst = con.prepareStatement("SELECT shampoo_amount FROM cleaning_dept");
		rs = pst.executeQuery();

		while(rs.next())
			shamp_amount[0] = rs.getInt("shampoo_amount");


		return shamp_amount;
	}


	public int[] shamp_price() throws SQLException
	{

		int[] shamp_price = new int[1];

		pst = con.prepareStatement("SELECT shampoo_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			shamp_price[0] = rs.getInt("shampoo_price");

		return shamp_price;

	}



	public int[] soap_amount() throws SQLException {

		Connection();

		int[] soap_amount = new int[1];
		pst = con.prepareStatement("SELECT soap_amount FROM cleaning_dept");
		rs = pst.executeQuery();

		while(rs.next())
			soap_amount[0] = rs.getInt("soap_amount");


		return soap_amount;
	}


	public int[] soap_price() throws SQLException
	{

		int[] soap_price = new int[1];

		pst = con.prepareStatement("SELECT soap_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			soap_price[0] = rs.getInt("soap_price");

		return soap_price;

	}


	public int[] towel_amount() throws SQLException {

		Connection();

		int[] towel_amount = new int[1];
		pst = con.prepareStatement("SELECT towel_amount FROM cleaning_dept");
		rs = pst.executeQuery();

		while(rs.next())
			towel_amount[0] = rs.getInt("towel_amount");


		return towel_amount;
	}


	public int[] towel_price() throws SQLException
	{

		int[] towel_price = new int[1];

		pst = con.prepareStatement("SELECT towel_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			towel_price[0] = rs.getInt("towel_price");

		return towel_price;

	}


	public int[] gloves_amount() throws SQLException {

		Connection();

		int[] gloves_amount = new int[1];
		pst = con.prepareStatement("SELECT gloves_amount FROM cleaning_dept");
		rs = pst.executeQuery();

		while(rs.next())
			gloves_amount[0] = rs.getInt("gloves_amount");


		return gloves_amount;
	}


	public int[] gloves_price() throws SQLException
	{

		int[] gloves_price = new int[1];

		pst = con.prepareStatement("SELECT gloves_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			gloves_price[0] = rs.getInt("gloves_price");

		return gloves_price;

	}




	//expenses'leri gösterir;

	public JFrame expenses() throws SQLException
	{

		GridLayout layout = new GridLayout(6,8);
		frame3 = new JFrame("Expenses");
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setSize(500,500);
		frame3.setLayout(layout);

		String det_expenses = "Detergent expenses : "+det_amount()[0]*det_price()[0]+"";
		String shamp_expenses = "Shampoo expenses : "+shamp_amount()[0]*shamp_price()[0]+"";
		String soap_expenses = "Soap expenses : "+soap_amount()[0]*soap_price()[0]+"";
		String towel_expenses = "Towel expenses : "+towel_amount()[0]*towel_price()[0]+"";
		String gloves_expenses = "Gloves expenses : "+gloves_amount()[0]*gloves_price()[0]+"";

		int total = det_amount()[0]*det_price()[0]+shamp_amount()[0]*shamp_price()[0]+soap_amount()[0]*soap_price()[0]+
				towel_amount()[0]*towel_price()[0]+gloves_amount()[0]*gloves_price()[0];

		String total_expenses = "Total expenses : "+total+"";




		JLabel label1 = new JLabel(det_expenses);
		JLabel label2 = new JLabel(shamp_expenses);
		JLabel label3 = new JLabel(towel_expenses);
		JLabel label4 = new JLabel(soap_expenses);
		JLabel label5 = new JLabel(gloves_expenses);
		JLabel label6 = new JLabel(total_expenses);

		//System.out.println(det_expenses);


		frame3.add(label1);
		frame3.add(label2);
		frame3.add(label3);
		frame3.add(label4);
		frame3.add(label5);
		frame3.add(label6);

		frame3.setVisible(true);

		return frame3;
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


		JOptionPane.showMessageDialog(frame4, "Kaydedildi !");

		// BURDAN SONRA KAYIT EKRANI KAPANMALI


	}



	// Numarasý verilen departmandaki en yüksek no'lu ID'yi döner;

	public int[] generate_ID(int dept_no) throws SQLException
	{

		Connection();

		int[] ID = new int[1];

		pst = con.prepareStatement("SELECT max(emp_id) FROM employees WHERE dno ="+dept_no);
		rs = pst.executeQuery();

		while(rs.next())
			ID[0] = rs.getInt("max");

		return ID;


	}


	public JFrame add_emp() throws SQLException
	{


		Connection();

		GridLayout layout = new GridLayout(16,16);
		frame4 = new JFrame("Add New Employee");
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.setSize(1000,700);
		frame4.setLayout(layout);


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






		frame4.add(label1);
		frame4.add(fname);

		frame4.add(label2);
		frame4.add(lname);

		frame4.add(label3);
		frame4.add(tcno);

		frame4.add(label4);
		frame4.add(addr);

		frame4.add(label5);
		frame4.add(salary);

		frame4.add(label6);
		frame4.add(gender);

		frame4.add(label7);
		frame4.add(bdate);

		frame4.add(label8);
		frame4.add(deptNo);

		frame4.add(label9);
		frame4.add(shift1);

		frame4.add(label10);
		frame4.add(dayof);

		frame4.add(save);






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

					add_emp_DB(generate_ID(Integer.parseInt(d_no))[0]+1,f_name, l_name,shift,dayof1,tc_no, address, salary1, gender1, birth_date, d_no);

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});




		frame4.setVisible(true);


		return frame4;
	}








	public void showGUI1(boolean isMgr) throws SQLException {

		kirliodalar = new JButton("Kirli Odalar");
		calisanekle = new JButton("Calisan Ekle");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");


		GridLayout layout = new GridLayout(4,2);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame("Cleaning Departmant");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(500,500);
		frame.setLayout(layout);






		if(isMgr)
		{
			frame.setVisible(true);
			frame.add(calisanekle);
			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.add(kirliodalar);



			kirliodalar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					dirty();

				}				
			});


			expenses.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						expenses();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}				
			});

			calisanekle.addActionListener(new ActionListener() {

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





		}//if(isMng)

		else {


			frame.add(kirliodalar);
			frame.setVisible(true);


			kirliodalar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					dirty();

				}				
			});




		}//else
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
