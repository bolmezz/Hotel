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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class ReceptionDepartment {

	JFrame checkIn = new JFrame();

	private JFrame frame;
	private JButton check_in,add_in;
	private JButton check_out;
	private JButton save;
	private JButton izinGunu;
	private JButton vardiyadegistir;
	private JButton expenses;
	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;

	private  void Connection() throws SQLException {

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

	public void showGUI7(boolean isMgr) throws SQLException
	{
		check_in = new JButton("Check-in");
		check_out = new JButton("Check-out");
		izinGunu = new JButton("Change Day-Off");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Price Table");
		save = new JButton("Save");
		add_in = new JButton("Add-in");
		JButton puanGor = new JButton("Rooms Points");

		/*GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);*/
		frame = new JFrame("Reception Departmant");
		checkIn.setSize(900,700);
		checkIn.setLayout(null);
		checkIn.setLocationRelativeTo(null); 


		frame.setSize(500,500);
		frame.setLayout(null);



		izinGunu.setBounds(20, 60, 160, 30);
		vardiyadegistir.setBounds(20, 100, 160, 30);
		expenses.setBounds(20, 140, 160, 30);

		check_in.setBounds(20, 60, 120, 30);
		check_out.setBounds(20, 100, 120, 30);
		add_in.setBounds(20, 140, 120, 30);
		puanGor.setBounds(20, 180, 120, 30);

		if(isMgr)
		{
			frame.setVisible(true);
			frame.add(izinGunu);
			frame.add(vardiyadegistir);
			frame.add(expenses);

			izinGunu.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					izinGunuDegistir();				
				}
			});

			vardiyadegistir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					vardiyaDegistir();				
				}
			});

			expenses.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					try {
						expenses();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			});

		}
		else {
			frame.setVisible(true);
			frame.add(check_in);
			frame.add(check_out);
			frame.add(add_in);
			frame.add(puanGor);

			check_in.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					try {
						onCheckInClicked();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			});

			check_out.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					try {
						onCheckOutClicked();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			});
			add_in.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{	
					try {

						add();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			});

			puanGor.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						getPoints();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
	
		}	

	}

	public int breakfast_num;
	public int dinner_num;
	public int loundry_num;
	public int massage_num;
	public int transportation_num;

	public void roomDetail(String roomID) throws SQLException
	{	
		Connection();
		pst = con.prepareStatement("SELECT breakfast_number FROM rooms where room_no = "+roomID);
		rs = pst.executeQuery();

		while(rs.next()) 
			breakfast_num = rs.getInt("breakfast_number");


		pst = con.prepareStatement("SELECT dinner_number FROM rooms where room_no = "+roomID);
		rs = pst.executeQuery();

		while(rs.next())
			dinner_num = rs.getInt("dinner_number");

		pst = con.prepareStatement("SELECT laundry_number FROM rooms where room_no = "+roomID);
		rs = pst.executeQuery();

		while(rs.next())
			loundry_num = rs.getInt("laundry_number");

		pst = con.prepareStatement("SELECT massage_number FROM rooms where room_no = "+roomID);
		rs = pst.executeQuery();

		while(rs.next())
			massage_num = rs.getInt("massage_number");

		pst = con.prepareStatement("SELECT transportation_number FROM rooms where room_no = "+roomID);
		rs = pst.executeQuery();

		while(rs.next())
			transportation_num = rs.getInt("transportation_number");
	}


	public void add() throws SQLException{
		JFrame add2 = new JFrame ("Add");
		add2.setSize(700,700);
		add2.setLayout(null);
		JComboBox<String> roomNo = new JComboBox<String>();
		JLabel roomLab = new JLabel("Select Room: ");
		roomLab.setBounds(20, 20, 100, 30);
		roomNo.setBounds(140, 20, 100, 30);

		JLabel breakfastLab = new JLabel("Add Breakfast");
		JButton breakfast = new JButton("Add");
		JLabel dinnerLab = new JLabel("Add Dinner");
		JButton dinner = new JButton("Add");
		JLabel laundryLab = new JLabel("Add Laundry");
		JButton laundry = new JButton("Add");
		JLabel massageLab = new JLabel("Add Massage");
		JButton massage = new JButton("Add");
		JLabel transportationLab = new JLabel("Add Transportation");
		JButton transportation = new JButton("Add");

		breakfastLab.setBounds(20, 60, 100, 30);
		breakfast.setBounds(140, 60, 100, 30);
		dinnerLab.setBounds(20, 100, 100, 30);
		dinner.setBounds(140, 100, 100, 30);
		laundryLab.setBounds(20, 140, 100, 30);
		laundry.setBounds(140, 140, 100, 30);
		massageLab.setBounds(20, 180, 100, 30);
		massage.setBounds(140, 180, 100, 30);
		transportationLab.setBounds(20, 220, 100, 30);
		transportation.setBounds(140, 220, 100, 30);

		add2.add(roomNo);
		add2.add(roomLab);
		add2.add(breakfastLab);
		add2.add(breakfast);
		add2.add(dinnerLab);
		add2.add(dinner);
		add2.add(laundryLab);
		add2.add(laundry);
		add2.add(massageLab);
		add2.add(massage);
		add2.add(transportationLab);
		add2.add(transportation);

		Connection();

		int[] msg_amount = new int[1];
		pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty=0 ORDER BY room_no ASC");
		rs = pst.executeQuery();

		while(rs.next()) {
			msg_amount[0] = rs.getInt("room_no");
			roomNo.addItem(msg_amount[0]+"");
		}

		breakfast.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedRoom = (String)roomNo.getSelectedItem();

					roomDetail(selectedRoom);

					int update;
					breakfast_num++;

					String a = "UPDATE rooms SET breakfast_number ="+ (breakfast_num) +"WHERE room_no = " + selectedRoom;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		dinner.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedRoom = (String)roomNo.getSelectedItem();

					roomDetail(selectedRoom);

					int update;
					dinner_num++;

					String a = "UPDATE rooms SET dinner_number = "+ (dinner_num) +"WHERE room_no = " + selectedRoom;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		laundry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedRoom = (String)roomNo.getSelectedItem();

					roomDetail(selectedRoom);

					int update;
					loundry_num++;

					String a = "UPDATE rooms SET laundry_number = "+ (loundry_num) +"WHERE room_no = " + selectedRoom;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		massage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedRoom = (String)roomNo.getSelectedItem();

					roomDetail(selectedRoom);

					int update;
					massage_num++;

					String a = "UPDATE rooms SET massage_number = "+ (massage_num) +"WHERE room_no = " + selectedRoom;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		transportation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedRoom = (String)roomNo.getSelectedItem();

					roomDetail(selectedRoom);

					int update;
					transportation_num++;
					String a = "UPDATE rooms SET transportation_number = "+ (transportation_num) +"WHERE room_no = " + selectedRoom;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		add2.setVisible(true);
	}

	public void onCheckOutClicked() throws SQLException {
		JFrame expenses = new JFrame("Reception Department Expenses");
		expenses.setSize(300,300);
		expenses.setLayout(null);

		JLabel odaLab = new JLabel("Choose Room");
		odaLab.setBounds(20,20,100,30);

		Connection();


		JComboBox<String> rooms = new JComboBox();
		int[] msg_amount = new int[1];
		pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty=0 ORDER BY room_no ASC");
		rs = pst.executeQuery();

		while(rs.next()) {
			msg_amount[0] = rs.getInt("room_no");
			rooms.addItem(msg_amount[0]+"");
		}

		rooms.setBounds(140,20,100,30);

		JLabel puanLab = new JLabel("Room Point");
		JTextField puan = new JTextField();
		puanLab.setBounds(20,60,100,30);
		puan.setBounds(140,60,100,30);

		expenses.add(puanLab);
		expenses.add(puan);

		JButton remove = new JButton("Remove Guests");
		remove.setBounds(60,140,150,30);
		expenses.add(rooms);
		expenses.add(odaLab);
		expenses.add(remove);
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{	
				try {
					
					if(!puan.getText().contains("1") && !puan.getText().contains("2") && !puan.getText().contains("3") && !puan.getText().contains("4") && !puan.getText().contains("5") 
							&& !puan.getText().contains("6") && !puan.getText().contains("7") && !puan.getText().contains("8") && !puan.getText().contains("9") && !puan.getText().contains("0") 
							|| puan.getText().contains("+") || puan.getText().contains("-") || puan.getText().contains("*") || puan.getText().contains("/") || puan.getText().equals(""))
					{


						JOptionPane.showMessageDialog(null, "You must give a score to the room !");

					}
					else {
						String room = (String) rooms.getSelectedItem();
		
						Date now1 = new Date();
						DateFormat gf = new SimpleDateFormat("yyyy-MM-dd");
						String out = gf.format(now1);
						System.out.println("asj: " + out);
						int update;
						String a;					
		
						a = "UPDATE guest SET check_out_date = '" + out + "' WHERE g_room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
						System.out.println(pst);
						System.out.println("updated");
		
						expenses2(room);
		
						a = "UPDATE rooms SET isempty = 1 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
						System.out.println("updated");
		
						a = "UPDATE rooms SET cleanliness = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
		
						a = "DELETE FROM guest WHERE g_room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
		
						a = "UPDATE rooms SET score = score +" + Integer.parseInt(puan.getText()) + " WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET breakfast_number = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET dinner_number = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET laundry_number = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET massage_number = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET transportation_number = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET towel_room = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET soap_room = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET shampoo_room = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						a = "UPDATE rooms SET water_room = 0 WHERE room_no = " + room;
						pst = con.prepareStatement(a);
						update = pst.executeUpdate();
		
						expenses.dispose();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		expenses.setVisible(true);
	}

	public void expenses2(String room) throws SQLException {
		JFrame expenses = new JFrame("Reception Department Expenses");
		expenses.setSize(700,700);
		expenses.setLayout(new GridLayout(7,7));

		Connection();

		String pkTotalPrice = "";
		String bandageTotalPrice = "";
		String syringeTotalPrice = "";
		String frTotalPrice = "";
		String maskTotalPrice= "";
		String roomPrice = "";


		int totalPrice = 0;
		try {

			int a = pk_amount(room)[0]*pk_price()[0];
			int b = bandage_amount(room)[0]*bandage_price()[0];
			int c = syringe_amount(room)[0]*syringe_price()[0];
			int d = fr_amount(room)[0]*fr_price()[0];
			int e = mask_amount(room)[0]*mask_price()[0];
			int price = Integer.parseInt(roomPrice(room));

			pkTotalPrice = "Breakfast Extra expenses: "+ Integer.toString(a)+" TL";
			bandageTotalPrice = "Dinner Extra expenses: "+ Integer.toString(b)+" TL";
			syringeTotalPrice = "Laundry Extra expenses: "+ Integer.toString(c)+" TL";
			frTotalPrice = "Massage Extra expenses: "+ Integer.toString(d)+" TL";
			maskTotalPrice = "Transportation Extra expenses: "+ Integer.toString(e)+" TL";
			roomPrice = "Room price: " + price;

			totalPrice = a+b+c+d+e+price;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel pk = new JLabel(pkTotalPrice);
		JLabel band = new JLabel(bandageTotalPrice);
		JLabel syr = new JLabel(syringeTotalPrice);
		JLabel fr = new JLabel(frTotalPrice);
		JLabel mask = new JLabel(maskTotalPrice);
		JLabel roomP = new JLabel(roomPrice);
		JLabel price = new JLabel("TOTAL PRICE = " + totalPrice + "TL");
		expenses.add(pk);
		expenses.add(band);
		expenses.add(syr);
		expenses.add(fr);
		expenses.add(mask);
		expenses.add(roomP);
		expenses.add(price);
		expenses.setVisible(true);

	}

	public void expenses() throws SQLException {
		JFrame expenses = new JFrame("Reception Department Expenses");
		expenses.setSize(700,700);
		expenses.setLayout(new GridLayout(7,7));
		JComboBox<String> roomNo = new JComboBox<String>();

		Connection();


		//dolu odalari alan kod
		int[] msg_amount = new int[1];
		pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty=0 ORDER BY room_no ASC");
		rs = pst.executeQuery();

		while(rs.next()) {
			msg_amount[0] = rs.getInt("room_no");
			roomNo.addItem(msg_amount[0]+"");
		}
		//

		String pkTotalPrice = "";
		String bandageTotalPrice = "";
		String syringeTotalPrice = "";
		String frTotalPrice = "";
		String maskTotalPrice= "";
		String roomPrice = "";


		int totalPrice = 0;
		try {

			String room = (String) roomNo.getSelectedItem();
			int a = pk_price()[0];
			int b = bandage_price()[0];
			int c = syringe_price()[0];
			int d = fr_price()[0];
			int e = mask_price()[0];

			pkTotalPrice = "Breakfast Extra expenses: "+ Integer.toString(a)+" TL";
			bandageTotalPrice = "Dinner Extra expenses: "+ Integer.toString(b)+" TL";
			syringeTotalPrice = "Laundry Extra expenses: "+ Integer.toString(c)+" TL";
			frTotalPrice = "Massage Extra expenses: "+ Integer.toString(d)+" TL";
			maskTotalPrice = "Transportation Extra expenses: "+ Integer.toString(e)+" TL";


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel pk = new JLabel(pkTotalPrice);
		JLabel band = new JLabel(bandageTotalPrice);
		JLabel syr = new JLabel(syringeTotalPrice);
		JLabel fr = new JLabel(frTotalPrice);
		JLabel mask = new JLabel(maskTotalPrice);
		JLabel roomP = new JLabel(roomPrice);
		expenses.add(pk);
		expenses.add(band);
		expenses.add(syr);
		expenses.add(fr);
		expenses.add(mask);
		expenses.add(roomP);
		expenses.setVisible(true);

	}

	public String roomPrice(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT room_type FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] = rs.getInt("room_type");

		int expense = 0;

		pst = con.prepareStatement("SELECT check_out_date FROM guest WHERE g_room_no = "+ room);
		rs = pst.executeQuery();

		String sonGun = null;
		while(rs.next())
			sonGun = rs.getString("check_out_date");

		pst = con.prepareStatement("SELECT check_in_date FROM guest WHERE g_room_no = "+ room);
		rs = pst.executeQuery();

		String ilkGun = null;
		while(rs.next())
			ilkGun = rs.getString("check_in_date");

		System.out.println("burak"+sonGun);
		System.out.println(ilkGun);

		int kalinanGun = 0;


		try {
			Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(ilkGun);
			Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(sonGun);  

			long diff = date2.getTime() - date1.getTime();

			kalinanGun = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if(kalinanGun == 0) {
				kalinanGun = 1;
			}
			else {
				kalinanGun = kalinanGun;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  


		if(pk_amount[0] == 1) {
			expense += kalinanGun*200;
		}
		else if (pk_amount[0] == 2) {
			expense += kalinanGun*300;
		}
		else if (pk_amount[0] == 3) {
			expense += kalinanGun*400;			
		}
		else {
			expense += kalinanGun*500;
		}



		return expense+"";

	}

	public int[] pk_amount(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT breakfast_number FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] += rs.getInt("breakfast_number");


		return pk_amount;
	}

	public int[] pk_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT breakfast_price FROM extra_services");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("breakfast_price");

		return pk_price;

	}

	public int[] bandage_amount(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT dinner_number FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] += rs.getInt("dinner_number");


		return pk_amount;
	}

	public int[] bandage_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT dinner_price FROM extra_services");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("dinner_price");
		return pk_price;

	}

	public int[] syringe_amount(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT laundry_number FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] += rs.getInt("laundry_number");


		return pk_amount;
	}

	public int[] syringe_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT laundry_price FROM extra_services");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("laundry_price");

		return pk_price;

	}

	public int[] fr_amount(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT massage_number FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] += rs.getInt("massage_number");



		return pk_amount;
	}

	public int[] fr_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT massage_price FROM extra_services");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("massage_price");

		return pk_price;

	}

	public int[] mask_amount(String room) throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT transportation_number FROM rooms WHERE room_no = "+ room);
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] += rs.getInt("transportation_number");


		return pk_amount;
	}

	public int[] mask_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT transportation_price FROM extra_services");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("transportation_price");

		return pk_price;

	}


	public void onCheckInClicked() throws SQLException {

		Connection();

		JTextField tcNo = new JTextField();
		JTextField name = new JTextField();
		JTextField last_name = new JTextField();
		JTextField tel_no = new JTextField();
		JTextField title = new JTextField();

		JLabel tcNoLab = new JLabel("Tc No : ");
		JLabel name_Lab = new JLabel("Name : ");
		JLabel lastName_Lab = new JLabel("Last Name : ");
		JLabel telNo_Lab = new JLabel("Tel No : ");
		JLabel title_Lab = new JLabel("Title : ");

		JLabel mus_No = new JLabel("Guest Number 1 ");
		mus_No.setBounds(30, 210, 150, 30);

		tcNoLab.setBounds(20, 20, 100, 30);
		name_Lab.setBounds(20, 60, 100, 30);
		lastName_Lab.setBounds(20, 100, 100, 30);
		telNo_Lab.setBounds(20, 140, 100, 30);
		title_Lab.setBounds(20, 180, 100, 30);

		tcNo.setBounds(120, 20, 150, 30);
		name.setBounds(120, 60, 150, 30);
		last_name.setBounds(120, 100, 150, 30);
		tel_no.setBounds(120, 140, 150, 30);
		title.setBounds(120, 180, 150, 30);
		save.setBounds(400, 250, 100, 30);


		checkIn.add(tcNo);
		checkIn.add(tcNoLab);
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
		JTextField tcNo2 = new JTextField();
		JTextField name2 = new JTextField();
		JTextField last_name2 = new JTextField();
		JTextField tel_no2 = new JTextField();
		JTextField title2 = new JTextField();

		JLabel tcNoLab2 = new JLabel("Tc No : ");
		JLabel name_Lab2 = new JLabel("Name : ");
		JLabel lastName_Lab2 = new JLabel("Last Name : ");
		JLabel telNo_Lab2 = new JLabel("Tel No : ");
		JLabel title_Lab2 = new JLabel("Title : ");

		JLabel mus_No2 = new JLabel("Guest Number 2 ");
		mus_No2.setBounds(330, 210, 150, 30);

		tcNoLab2.setBounds(320, 20, 100, 30);
		name_Lab2.setBounds(320, 60, 100, 30);
		lastName_Lab2.setBounds(320, 100, 100, 30);
		telNo_Lab2.setBounds(320, 140, 100, 30);
		title_Lab2.setBounds(320, 180, 100, 30);

		tcNo2.setBounds(420, 20, 150, 30);	
		name2.setBounds(420, 60, 150, 30);
		last_name2.setBounds(420, 100, 150, 30);
		tel_no2.setBounds(420, 140, 150, 30);
		title2.setBounds(420, 180, 150, 30);

		checkIn.add(tcNoLab2);
		checkIn.add(tcNo2);
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
		JTextField tcNo3 = new JTextField();
		JTextField name3 = new JTextField();
		JTextField last_name3 = new JTextField();
		JTextField tel_no3 = new JTextField();
		JTextField title3 = new JTextField();

		JLabel tcNoLab3 = new JLabel("Tc No : ");	
		JLabel name_Lab3 = new JLabel("Name : ");
		JLabel lastName_Lab3 = new JLabel("Last Name : ");
		JLabel telNo_Lab3 = new JLabel("Tel No : ");
		JLabel title_Lab3 = new JLabel("Title : ");

		JLabel mus_No3 = new JLabel("Guest Number 3 ");
		mus_No3.setBounds(630, 210, 150, 30);

		tcNoLab3.setBounds(620, 20, 100, 30);
		name_Lab3.setBounds(620, 60, 100, 30);
		lastName_Lab3.setBounds(620, 100, 100, 30);
		telNo_Lab3.setBounds(620, 140, 100, 30);
		title_Lab3.setBounds(620, 180, 100, 30);

		tcNo3.setBounds(720, 20, 150, 30);		
		name3.setBounds(720, 60, 150, 30);
		last_name3.setBounds(720, 100, 150, 30);
		tel_no3.setBounds(720, 140, 150, 30);
		title3.setBounds(720, 180, 150, 30);

		checkIn.add(tcNoLab3);
		checkIn.add(tcNo3);
		checkIn.add(mus_No3);
		checkIn.add(name3);
		checkIn.add(last_name3);
		checkIn.add(tel_no3);
		checkIn.add(title3);

		checkIn.add(name_Lab3);
		checkIn.add(lastName_Lab3);
		checkIn.add(telNo_Lab3);
		checkIn.add(title_Lab3);


		JComboBox<String> freeRooms = new JComboBox<String>();
		int[] msg_amount = new int[1];
		pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty = 1 and cleanliness=1 ORDER BY room_no ASC");
		rs = pst.executeQuery();

		while(rs.next()) {
			msg_amount[0] = rs.getInt("room_no");
			freeRooms.addItem(msg_amount[0]+"");
		}

		freeRooms.setBounds(100, 250, 100, 30);
		checkIn.add(freeRooms);	
		checkIn.setVisible(true);			

		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{	
				Date now = new Date();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String zaman = df.format(now);

				long date1=now.getTime()+7*24*60*60*1000;
				Date outDate = new Date(date1);
				DateFormat of = new SimpleDateFormat("yyyy-MM-dd");
				String outDate2 = of.format(outDate);


				String roomNo = (String) freeRooms.getSelectedItem();
				try {
					int update=0;
					String a = "UPDATE rooms SET isempty = 0, times=times+1 WHERE room_no = " + roomNo;
					pst = con.prepareStatement(a);
					update = pst.executeUpdate();
					System.out.println("UPDATED");

					String gTC;
					String gname = "";
					String gsurName = "";
					String gtitle = "";
					String gtelNo = "";

					//first guest
					if(!name.getText().equals("")) {
						gTC = tcNo.getText();
						gname = name.getText();
						gsurName = last_name.getText();
						gtitle = title.getText();
						gtelNo = tel_no.getText();

						a = "INSERT INTO guest (g_tcno, g_fname, g_lname,g_room_no,g_title,g_telno,check_in_date,check_out_date) VALUES (" + gTC +",'"+gname+"','"+gsurName+"',"+ roomNo +",'"+gtitle+"','"+gtelNo+"','"+zaman+"','"+outDate2+"')";
						try {
							pst = con.prepareStatement(a);
							System.out.println(pst);
							update = pst.executeUpdate();
							System.out.println("updated");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					//second guest
					if(!name2.getText().equals("")) {
						gTC = tcNo2.getText();
						gname = name2.getText();
						gsurName = last_name2.getText();
						gtitle = title2.getText();
						gtelNo = tel_no2.getText();

						a = "INSERT INTO guest (g_tcno, g_fname, g_lname,g_room_no,g_title,g_telno,check_in_date,check_out_date) VALUES (" + gTC +",'"+gname+"','"+gsurName+"',"+ roomNo +",'"+gtitle+"','"+gtelNo+"','"+zaman+"','"+outDate2+"')";
						try {
							pst = con.prepareStatement(a);
							System.out.println(pst);
							update = pst.executeUpdate();
							System.out.println("updated");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					//third guest
					if(!name3.getText().equals("")) {
						gTC = tcNo3.getText();
						gname = name3.getText();
						gsurName = last_name3.getText();
						gtitle = title3.getText();
						gtelNo = tel_no3.getText();

						a = "INSERT INTO guest (g_tcno, g_fname, g_lname,g_room_no,g_title,g_telno,check_in_date,check_out_date) VALUES (" + gTC +",'"+gname+"','"+gsurName+"',"+ roomNo +",'"+gtitle+"','"+gtelNo+"','"+zaman+"','"+outDate2+"')";
						try {
							pst = con.prepareStatement(a);
							System.out.println(pst);
							update = pst.executeUpdate();
							System.out.println("updated");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JOptionPane.showMessageDialog(checkIn, "Guests are added to the database !");

				tcNo.setText("");
				name.setText("");
				last_name.setText("");
				tel_no.setText("");
				title.setText("");

				tcNo2.setText("");
				name2.setText("");
				last_name2.setText("");
				tel_no2.setText("");
				title2.setText("");

				tcNo3.setText("");
				name3.setText("");
				last_name3.setText("");
				tel_no3.setText("");
				title3.setText("");

				freeRooms.removeAllItems();
				int[] msg_amount = new int[1];
				try {
					pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty = 1 and cleanliness=1 ORDER BY room_no ASC");
					rs = pst.executeQuery();

					while(rs.next()) {
						msg_amount[0] = rs.getInt("room_no");
						freeRooms.addItem(msg_amount[0]+"");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}			
		});


	}

	public void izinGunuDegistir() {
		JFrame vardiya = new JFrame("Ýzin Günü Dedðiþtirme");
		vardiya.setSize(500,500);
		vardiya.setLayout(null);

		JButton change = new JButton("Change Day-Off");
		JLabel empIDLab = new JLabel("Employee ID:");
		JTextField empID = new JTextField();
		JLabel newDayLab = new JLabel("Select day to change:");
		JComboBox<String> newDay = new JComboBox<String>();
		newDay.addItem("Monday");
		newDay.addItem("Tuesday");
		newDay.addItem("Wednesday");
		newDay.addItem("Thursday");
		newDay.addItem("Friday");
		newDay.addItem("Saturday");
		newDay.addItem("Sunday");
		newDay.setSelectedIndex(0);

		empIDLab.setBounds(20, 60, 100, 30);
		empID.setBounds(130, 60, 100, 30);
		newDayLab.setBounds(20, 100, 100, 30);
		newDay.setBounds(130, 100, 100, 30);
		change.setBounds(20, 150, 160, 30);
		vardiya.add(empIDLab);
		vardiya.add(empID);
		vardiya.add(newDayLab);
		vardiya.add(newDay);
		vardiya.add(change);
		vardiya.setVisible(true);
		change.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try {
					String selectedDayOff = (String) newDay.getSelectedItem();
					setIzinGunu(empID.getText(),selectedDayOff);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void setIzinGunu(String id, String dayOff) throws SQLException {
		Connection();

		int update=0;

		if(!id.contains("1") && !id.contains("2") && !id.contains("3") && !id.contains("4") && !id.contains("5") 
				&& !id.contains("6") && !id.contains("7") && !id.contains("8") && !id.contains("9") && !id.contains("0") 
				|| id.contains("+") || id.contains("-") || id.contains("*") || id.contains("/"))
		{


			JOptionPane.showMessageDialog(null, "Geçerli ID girin !");

		}
		else {

			if(Integer.parseInt(id)<17008 && Integer.parseInt(id)>11000) {

				String a = "UPDATE employees SET day_offs = '" + dayOff + "' WHERE emp_id = " + id;
				pst = con.prepareStatement(a);
				update = pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Day-off has been changed !");
			}
			else
				JOptionPane.showMessageDialog(null, "Geçerli ID girin !");

		}
	}
	public void vardiyaDegistir() {
		JFrame vardiya = new JFrame("Vardiya Degistirme");
		vardiya.setSize(500,500);
		vardiya.setLayout(null);

		JButton change = new JButton("Change Shift");
		JLabel empIDLab = new JLabel("Employee ID:");
		JTextField empID = new JTextField();
		JLabel newShiftLab = new JLabel("Select shift to change:");
		JComboBox<String> newShift = new JComboBox<String>();
		newShift.addItem("Morning");
		newShift.addItem("Evening");
		newShift.addItem("Night");
		newShift.setSelectedIndex(0);

		empIDLab.setBounds(20, 60, 100, 30);
		empID.setBounds(130, 60, 100, 30);
		newShiftLab.setBounds(20, 100, 100, 30);
		newShift.setBounds(130, 100, 100, 30);
		change.setBounds(20, 150, 160, 30);
		vardiya.add(empIDLab);
		vardiya.add(empID);
		vardiya.add(newShiftLab);
		vardiya.add(newShift);
		vardiya.add(change);
		vardiya.setVisible(true);
		change.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try {
					int selectedVardiya = newShift.getSelectedIndex();
					setVardiya(empID.getText(),selectedVardiya);
					//JOptionPane.showMessageDialog(vardiya, "Shift has been changed !");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


	}

	public void setVardiya(String id, int shift) throws SQLException {
		Connection();

		int update=0;

		if(!id.contains("1") && !id.contains("2") && !id.contains("3") && !id.contains("4") && !id.contains("5") 
				&& !id.contains("6") && !id.contains("7") && !id.contains("8") && !id.contains("9") && !id.contains("0") 
				|| id.contains("+") || id.contains("-") || id.contains("*") || id.contains("/"))
		{


			JOptionPane.showMessageDialog(null, "Geçerli ID girin !");

		}
		else {

			if(Integer.parseInt(id)<17008 && Integer.parseInt(id)>11000) {
				String a = "UPDATE employees SET shift = " + shift + " WHERE emp_id = " + id;
				pst = con.prepareStatement(a);
				update = pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Shift has been changed !");
			}
			else
				JOptionPane.showMessageDialog(null, "Geçerli ID girin !");
		}

	}

	public void getPoints() throws SQLException
	{
		Connection();

		JFrame pointScreen = new JFrame("Rooms Point");
		pointScreen.setLayout(null);
		pointScreen.setSize(500, 500);

		JComboBox<String> Rooms = new JComboBox<String>();
		int[] roomNo = new int[1];
		pst = con.prepareStatement("SELECT room_no FROM rooms WHERE isempty = 1 ORDER BY room_no ASC");
		rs = pst.executeQuery();

		while(rs.next())
		{
			roomNo[0]=rs.getInt("room_no");
			Rooms.addItem(roomNo[0]+"");
		}

		Rooms.setBounds(20, 60, 160, 30);
		JTextField point = new JTextField();
		JLabel ortPoint = new JLabel("Avarage point :");

		ortPoint.setBounds(20, 100, 160, 30);
		point.setBounds(120, 100, 120, 30);
		pointScreen.add(ortPoint);
		pointScreen.add(point);
		pointScreen.add(Rooms);

		Rooms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String selectedRoom = (String) Rooms.getSelectedItem();

				try {

					point.setText(getScore(Integer.parseInt(selectedRoom))+"");

				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



			}
		});

		pointScreen.setVisible(true);

	}

	public double getScore(int no) throws SQLException
	{

		Connection();
		double score = 0;
		int time = 0;
		double ort = 0;

		pst = con.prepareStatement("SELECT score FROM rooms where room_no = "+no);
		rs = pst.executeQuery();

		while(rs.next())
			score = score+rs.getInt("score");

		pst = con.prepareStatement("SELECT times FROM rooms where room_no = "+no);
		rs = pst.executeQuery();

		while(rs.next())
			time = time+rs.getInt("times");
		
		System.out.println(score);
		System.out.println(time);

		if(time != 0)
			ort = score/time;
		else
			ort=0;

		return ort;


	}
	


}
