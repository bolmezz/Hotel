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
import javax.swing.JCheckBox;
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
	private JButton vardiyadegistir;
	private JButton expenses;
	private JButton izinGunu;
	private JFrame frame;
	private JFrame frame2;
	private JFrame frame3;
	private JFrame frame4;
	private JFrame frame5;




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


	//kirli odalarý listeler;
	//cleanlines == 0 = KÝRLÝ ODA

	public ArrayList<Integer> dirtyRooms() throws SQLException {

		Connection();


		ArrayList<Integer> rooms = new ArrayList<>();
		pst = con.prepareStatement("SELECT room_no FROM rooms where cleanliness = 0 Order BY room_no ASC");
		rs = pst.executeQuery();

		while (rs.next()) {

			rooms.add(rs.getInt("room_no"));


		}
		return rooms;
	}



	//odanýn cleanliness durumunu 1 yapar. (yani oda artýk temiz göürünür);

	public void cleanRoom(int roomID) throws SQLException
	{
		Connection();

		int roomNo;

		roomNo =dirtyRooms().get(roomID);



		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rooms SET cleanliness ="+1+" Where room_no ="+roomNo);

		JOptionPane.showMessageDialog(frame5, "Room has been cleaned !");


		frame5.dispose();
		dirty();



	}


	//odadaki stok sayýsýný 1 arttýrýr;

	public void addStock(int roomID, int towelA, int soapA, int waterA, int shampooA) throws SQLException
	{

		Connection();

		int roomNo =0;

		roomNo =dirtyRooms().get(roomID);


		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rooms SET towel_room="+(towelA+1)+",water_room ="+(waterA+1)+",soap_room ="+(soapA+1)+
				",shampoo_room ="+(shampooA+1)+" Where room_no ="+roomNo);




	}

	//odadaki stok sayýsýný 1 azaltýr;

	public void removeStock(int roomID, int towelA, int soapA, int waterA, int shampooA) throws SQLException
	{

		Connection();

		int roomNo =0;

		roomNo =dirtyRooms().get(roomID);

		if(towelA != 1 && soapA != 1 && waterA !=1 && shampooA != 1)
		{

			Statement st =  (Statement) con.createStatement();
			((java.sql.Statement) st).executeUpdate("UPDATE rooms SET towel_room="+(towelA-1)+",water_room ="+(waterA-1)+",soap_room ="+(soapA-1)+
					",shampoo_room ="+(shampooA-1)+" Where room_no ="+roomNo);


		}


		else
		{

			JOptionPane.showMessageDialog(frame5, "Stocks can not remove !");


			frame5.dispose();

		}
	}


	public int towel =0;
	public int soap =0;
	public int water =0;
	public int shampoo=0;



	//kirli odanýn stok bilgisi gösterir;

	public void roomDetail(int roomID) throws SQLException
	{
		Connection();

		int roomNo;

		roomNo =dirtyRooms().get(roomID);



		pst = con.prepareStatement("SELECT towel_room FROM rooms where room_no = "+roomNo);
		rs = pst.executeQuery();

		while(rs.next()) 
			towel = rs.getInt("towel_room");


		pst = con.prepareStatement("SELECT soap_room FROM rooms where room_no = "+roomNo);
		rs = pst.executeQuery();

		while(rs.next())
			soap = rs.getInt("soap_room");

		pst = con.prepareStatement("SELECT water_room FROM rooms where room_no = "+roomNo);
		rs = pst.executeQuery();

		while(rs.next())
			water = rs.getInt("water_room");

		pst = con.prepareStatement("SELECT shampoo_room FROM rooms where room_no = "+roomNo);
		rs = pst.executeQuery();

		while(rs.next())
			shampoo = rs.getInt("shampoo_room");



		frame5 = new JFrame("Room : "+roomNo);
		//frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.setSize(500,500);
		frame5.setLayout(null);



		JLabel toweltext = new JLabel("Towel amount :");
		JLabel soaptext = new JLabel("Soap amount :");
		JLabel watertext = new JLabel("Water amount :");
		JLabel shampootext = new JLabel("Shampoo amount :");




		JTextField towelT = new JTextField();
		JTextField soapT = new JTextField();
		JTextField shampooT = new JTextField();
		JTextField waterT = new JTextField();

		toweltext.setBounds(20, 60, 100, 30);
		towelT.setBounds(130, 60, 100, 30);

		soaptext.setBounds(20, 100, 100, 30);
		soapT.setBounds(130, 100, 100, 30);

		watertext.setBounds(20, 140, 100, 30);
		waterT.setBounds(130, 140, 100, 30);

		shampootext.setBounds(20, 180, 100, 30);
		shampooT.setBounds(130, 180, 100, 30);

		towelT.setText(towel+"");
		soapT.setText(soap+"");
		waterT.setText(water+"");
		shampooT.setText(shampoo+"");

		JButton ekle = new JButton("Ekle");
		JButton kaydet = new JButton("Temizliði Bitir");
		JButton cikar = new JButton("Geri al");

		ekle.setBounds(110, 240, 120, 30);
		kaydet.setBounds(110, 320, 120, 30);
		cikar.setBounds(110,280,120,30);

		frame5.add(toweltext);
		frame5.add(towelT);


		frame5.add(soaptext);
		frame5.add(soapT);


		frame5.add(watertext);
		frame5.add(waterT);


		frame5.add(shampootext);
		frame5.add(shampooT);

		frame5.add(ekle);
		frame5.add(kaydet);
		frame5.add(cikar);

		frame5.setVisible(true);
		ekle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					addStock(roomID,towel,soap,water,shampoo);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				frame5.dispose();

				try {
					roomDetail(roomID);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
		});


		cikar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					removeStock(roomID,towel,soap,water,shampoo);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				frame5.dispose();

				try {
					roomDetail(roomID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});


		kaydet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					cleanRoom(roomID);


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});


	}



	// kirli odalarý listeler ;

	public void dirty() throws SQLException
	{


		//GridLayout layout = new GridLayout(6,6);
		frame2 = new JFrame("Dirty Rooms");
		//frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(500,500);
		frame2.setLayout(null);




		int[] room = new int[dirtyRooms().size()];

		String[] room2 = new String[dirtyRooms().size()];

		try {

			for (int i = 0; i<dirtyRooms().size();i++)
				room[i] = dirtyRooms().get(i);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(int i = 0; i<room.length; i++) {
			room2[i] = String.valueOf(room[i]);

			//System.out.println(room2[i]);

		}

		JComboBox cmbRooms = new JComboBox(room2);
		JLabel lblText = new JLabel("Oda No :");

		cmbRooms.setBounds(100,60,100,30);
		lblText.setBounds(20,60,100,30);


		cmbRooms.setSelectedIndex(0);
		cmbRooms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int selectedRoom = cmbRooms.getSelectedIndex();

				try {

					roomDetail(selectedRoom);
					frame2.dispose();

				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		});




		frame2.add(cmbRooms);
		frame2.add(lblText);



		frame2.setVisible(true);


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

		//GridLayout layout = new GridLayout(6,8);
		frame3 = new JFrame("Expenses");
		//frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setSize(500,500);
		frame3.setLayout(null);

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

		label1.setBounds(20, 60, 200, 30);
		label2.setBounds(20, 80, 200, 30);
		label3.setBounds(20, 100, 200, 30);
		label4.setBounds(20, 120, 200, 30);
		label5.setBounds(20, 140, 200, 30);
		label6.setBounds(20, 160, 200, 30);


		frame3.add(label1);
		frame3.add(label2);
		frame3.add(label3);
		frame3.add(label4);
		frame3.add(label5);
		frame3.add(label6);

		frame3.setVisible(true);

		return frame3;
	}

	public void izinGunuDegistir() {
		JFrame vardiya = new JFrame("Ýzin Günü Deðiþtirme");
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
					vardiya.setVisible(false);

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
					vardiya.setVisible(false);
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



	public void showGUI1(boolean isMgr) throws SQLException {



		kirliodalar = new JButton("Kirli Odalar");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		izinGunu = new JButton("Set Day Off");

		frame = new JFrame("Cleaning Departmant");
		//frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


		frame.setSize(500,500);
		frame.setLayout(null);

		if(isMgr)
		{

			vardiyadegistir.setBounds(20, 60, 160, 30);
			izinGunu.setBounds(20, 100, 160, 30);
			expenses.setBounds(20, 140, 160, 30);
			kirliodalar.setBounds(20, 180, 160, 30);

			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.add(kirliodalar);
			frame.add(izinGunu);
			frame.setVisible(true);


			kirliodalar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						dirty();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

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


			izinGunu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					izinGunuDegistir();
				}
			});

			vardiyadegistir.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					vardiyaDegistir();
				}
			});


		}//if(isMng)

		else {

			kirliodalar.setBounds(20, 60, 160, 30);

			frame.add(kirliodalar);
			frame.setVisible(true);


			kirliodalar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						dirty();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}				
			});




		}//else


	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
