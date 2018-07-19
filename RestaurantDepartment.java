import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RestaurantDepartment {

	private JButton acikBufe;
	private JButton restaurant;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JButton izinGunu;
	private JButton temizle;
	private JButton siparisAl;
	private JButton ac;
	private JButton kapat;
	private JButton full;
	private JButton empty;

	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;

	private JFrame frame;
	private JFrame frame2;
	private JFrame frame3;
	private JFrame frame4;
	private JFrame frame5;
	private JFrame frame6;
	private JFrame frame7;

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


	//tüm masalarý getirir;

	public ArrayList<String> tables() throws SQLException {

		Connection();

		ArrayList<String> tables = new ArrayList<>();
		pst = con.prepareStatement("SELECT table_no FROM rest_tables Order BY table_no ASC");
		rs = pst.executeQuery();


		while (rs.next()) {

			tables.add(rs.getInt("table_no")+"");


		}

		return tables;
	}


	public void siparis(int tableID,String newOrder) throws SQLException
	{

		//Connection();

		int tableNo ;

		tableNo = Integer.parseInt(tables().get(tableID));

		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_tables SET table_order =1,order_detail = '"+newOrder +"', cleanliness = 0 Where table_no ="+tableNo);

		JOptionPane.showMessageDialog(frame3, "Sipariþ alýndý !");

		frame3.dispose();
		con.close();
		restaurant();

	}



	//masa için sipariþ alýnýr ;

	public void tableDetail(int tableID) throws SQLException
	{
		//Connection();


		int tableNo ;

		tableNo = Integer.parseInt(tables().get(tableID));

		frame3 = new JFrame("Masa :"+tableNo);
		frame3.setSize(500, 500);
		frame3.setLayout(null);

		JLabel orderT = new JLabel("Order :");
		JTextField newOrder = new JTextField();
		JButton getOrder = new JButton("Sipariþ Alýndý");

		orderT.setBounds(20, 60, 60, 30);
		newOrder.setBounds(100, 60, 160, 30);
		getOrder.setBounds(100, 100, 160, 30);

		String ord ="" ;

		pst = con.prepareStatement("SELECT order_detail FROM rest_tables where table_no ="+tableNo);
		rs = pst.executeQuery();


		while (rs.next()) 

			ord = rs.getString("order_detail");


		newOrder.setText(ord);
		frame3.add(orderT);
		frame3.add(newOrder);
		frame3.add(getOrder);





		frame3.setVisible(true);

		getOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					siparis(tableID, newOrder.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}






			}
		});



	}



	public void cleanTables() throws SQLException
	{

		Connection();

		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_tables SET cleanliness =1 ,table_order = 0 ,order_detail= '' ");


		JOptionPane.showMessageDialog(frame2, "Tables has been cleaned !");

		frame2.dispose();
		restaurant();

	}
	
	
	public void cleanTables2() throws SQLException
	{

		Connection();

		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_tables SET cleanliness =1 ,table_order = 0 ,order_detail= '' ");


		JOptionPane.showMessageDialog(frame6, "Tables has been cleaned !");

		frame6.dispose();
		frame6.setVisible(false);
		acikBuf();

	}
	
	public String[] bosStandlar() throws SQLException
	{
		Connection();
		
		String[] stand = new String[5];
		
		pst = con.prepareStatement("SELECT stand_name FROM rest_stands where is_empty = 0 and is_open = 1 Order BY stand_name ASC");
		rs = pst.executeQuery();

		int count =0;
		while (rs.next()) {

			stand[count] = rs.getString("stand_name");
			count++;

		}

		return stand;
		
		
	}

	public void makeFull(String stand) throws SQLException
	{
		
		Connection();
		
		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_stands set is_empty = 1 where stand_name = '"+ stand +"'");
		
		
		JOptionPane.showMessageDialog(frame6, stand+ " has been filled!");
		
		frame6.dispose();
		frame6.setVisible(false);
		acikBuf();
		
	}
	
	
	//açikbüfe çalýþan sayfasý;
	
	public void acikBuf() throws SQLException
	{
		Connection();


		frame6 = new JFrame("Açýk Büfe");
		frame6.setSize(500,500);
		frame6.setLayout(null);
		
		
		
		JComboBox<String> standNames = new JComboBox<>(bosStandlar());
		JLabel sName = new JLabel("Standlar : ");
		JButton full = new JButton("Doldur");
		JButton clean = new JButton("Masalarý temizle");
		
		
		sName.setBounds(20, 60, 100, 30);
		standNames.setBounds(100, 60, 160, 30);
		full.setBounds(100, 100, 100, 30);
		clean.setBounds(100, 140, 160, 30);
		
		frame6.add(sName);
		frame6.add(standNames);
		frame6.add(full);
		frame6.add(clean);
		
		full.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selectedStand = (String) standNames.getSelectedItem();
				
				try {
					makeFull(selectedStand);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		clean.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					cleanTables2();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		frame6.setVisible(true);
		
	}
	
	
	//restaurant sayfasýný açar masalar görünür;

	public void restaurant() throws SQLException
	{

		Connection();


		frame2 = new JFrame("Restaurant");
		frame2.setSize(500,500);
		frame2.setLayout(null);

		String[] tables = new String[tables().size()];

		for(int i =0; i<tables().size(); i++)
			tables[i] = tables().get(i);


		JComboBox<String> table = new JComboBox<>(tables);
		JLabel tableNo = new JLabel("Masalar :");

		JLabel conceptDetail = new JLabel("Concept : ");
		JTextArea concept = new JTextArea();
		JButton cleanTables = new JButton("Masalarý Temizle");


		String conc ="" ;

		pst = con.prepareStatement("SELECT concept FROM restaurant_dept");
		rs = pst.executeQuery();


		while (rs.next()) 

			conc = rs.getString("concept");

		concept.setText(conc);



		tableNo.setBounds(20,60,120,30);
		table.setBounds(140, 60, 120, 30);

		conceptDetail.setBounds(20, 100, 120, 30);
		concept.setBounds(140, 100, 120, 30);

		cleanTables.setBounds(140, 140, 160, 30);


		frame2.add(conceptDetail);
		frame2.add(concept);

		frame2.add(tableNo);
		frame2.add(table);
		frame2.add(cleanTables);

		table.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int selectedTable = table.getSelectedIndex();


				try {

					tableDetail(selectedTable);


					frame2.dispose();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});


		cleanTables.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					cleanTables();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});


		frame2.setVisible(true);
		con.close();
	}



	public void restManager()
	{

			Connection();

		frame4 = new JFrame("Restaurant Manager Page");
		frame4.setSize(500, 500);
		frame4.setLayout(null);

		JLabel conceptT = new JLabel("Concept");
		JTextArea conc = new JTextArea();
		JButton setConcept = new JButton("Set");

		conceptT.setBounds(20, 60, 100, 30);
		conc.setBounds(100, 60, 100, 30);
		setConcept.setBounds(100, 140, 100, 30);

		frame4.add(setConcept);
		frame4.add(conceptT);
		frame4.add(conc);


		setConcept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					Statement st =  (Statement) con.createStatement();
					((java.sql.Statement) st).executeUpdate("UPDATE restaurant_dept SET concept =  '"+conc.getText()+"'");
					
					JOptionPane.showMessageDialog(frame4, "Concept has been changed !");
					frame4.dispose();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});

		frame4.setVisible(true);

	}
	
	public String[] standlar() throws SQLException
	{
		
		Connection();
		
		String[] stand = new String[5];
		
		pst = con.prepareStatement("SELECT stand_name FROM rest_stands Order BY stand_name ASC");
		rs = pst.executeQuery();

		int count =0;
		while (rs.next()) {

			stand[count] = rs.getString("stand_name");
			count++;

		}

		return stand;
		
		
	}
	
	
	
	public void makeEmpty(String stand) throws SQLException
	{
		Connection();
		
		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_stands set is_empty = 0 where stand_name = '"+ stand +"'");
		
		
		JOptionPane.showMessageDialog(frame5, stand+ " is empty now !");
		
		frame5.dispose();
		acikManager();
		
		
	}
	
	public void makeOpen(String stand) throws SQLException
	{
		
		Connection();
		
		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_stands set is_open = 1 where stand_name = '"+ stand +"'");
		
		
		JOptionPane.showMessageDialog(frame5, stand+ " is open now !");
		
		frame5.dispose();
		acikManager();
		
	}
	
	public void makeClose(String stand) throws SQLException
	{
		
		Connection();
		
		Statement st =  (Statement) con.createStatement();
		((java.sql.Statement) st).executeUpdate("UPDATE rest_stands set is_open = 0 where stand_name = '"+ stand +"'");
		
		
		JOptionPane.showMessageDialog(frame5, stand+ "  is close now!");
		
		frame5.dispose();
		acikManager();
		
	}

	public void acikManager() throws SQLException
	{


		frame5 = new JFrame("Acik Büfe Manager Page");
		frame5.setSize(500, 500);
		frame5.setLayout(null);
		
		JComboBox<String> standNames = new JComboBox<>(standlar());
		JLabel sName = new JLabel("Standlar : ");
		JButton empty = new JButton("Boþ");
		JButton open = new JButton("Aç");
		JButton close = new JButton("Kapat");
		
		sName.setBounds(20, 60, 100, 30);
		standNames.setBounds(100, 60, 160, 30);
		empty.setBounds(20, 100, 100, 30);
		open.setBounds(140, 100, 100, 30);
		close.setBounds(260, 100, 100, 30);
		
		frame5.add(sName);
		frame5.add(standNames);
		frame5.add(close);
		frame5.add(open);
		frame5.add(empty);
		
	
		
		empty.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String selectedStand = (String) standNames.getSelectedItem();
				try {
					makeEmpty(selectedStand);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String selectedStand = (String) standNames.getSelectedItem();
				try {
					makeOpen(selectedStand);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String selectedStand = (String) standNames.getSelectedItem();
				try {
					makeClose(selectedStand);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		frame5.setVisible(true);

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
					if(!empID.getText().startsWith("14")) {
						JOptionPane.showMessageDialog(null, "You can only change your departments employees !");
					}
					else {
						String selectedDayOff = (String) newDay.getSelectedItem();
						setIzinGunu(empID.getText(),selectedDayOff);
						vardiya.setVisible(false);
					}
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
					if(!empID.getText().startsWith("14")) {
						JOptionPane.showMessageDialog(null, "You can only change your departments employees !");
					}
					else {
						int selectedVardiya = newShift.getSelectedIndex();
						setVardiya(empID.getText(),selectedVardiya);
						vardiya.setVisible(false);
					}
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

	
	public int vegetableAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT vegetables_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("vegetables_amount");


		return vegAmount;
	}
	
	public int fruitAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT fruits_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("fruits_amount");


		return vegAmount;
	}
	
	public int kitchenItAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT kitchen_items_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("kitchen_items_amount");


		return vegAmount;
	}
	
	public int meatAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT meat_products_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("meat_products_amount");


		return vegAmount;
	}
	
	public int dairyAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT dairy_products_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("dairy_products_amount");


		return vegAmount;
	}
	
	public int alcholicAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT alchol_products_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("alchol_products_amount");


		return vegAmount;
	}
	
	public int nonalcoholAmount() throws SQLException
	{
		
		Connection();

		int vegAmount= 0  ;
		pst = con.prepareStatement("SELECT nonalcholic_products_amount FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegAmount = rs.getInt("nonalcholic_products_amount");


		return vegAmount;
	}
	public int fruitPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT fruit_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("fruit_price");

		return vegPrice;
	}

	
	public int vegetablePrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT vegetable_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("vegetable_price");

		return vegPrice;
	}
	public int kitchenItPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT kitchen_items_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("kitchen_items_price");

		return vegPrice;
	}
	
	public int meatPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT meat_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("meat_price");

		return vegPrice;
	}
	public int dairyPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT dairy_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("dairy_price");

		return vegPrice;
	}
	public int alcoholPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT alcohol_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("alcohol_price");

		return vegPrice;
	}
	public int nonAlcoholPrice() throws SQLException
	{
		

		int vegPrice = 0;

		pst = con.prepareStatement("SELECT nonalcohol_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("nonalcohol_price");

		return vegPrice;
	}
	
	
	public int altPrice() throws SQLException
	{
		int vegPrice=0;
		pst = con.prepareStatement("SELECT alteration_expenses FROM restaurant_dept");
		rs = pst.executeQuery();

		while(rs.next())
			vegPrice = rs.getInt("alteration_expenses");
		
		return vegPrice;
	}
	
	public void expenses() throws SQLException
	{
		
		frame7 = new JFrame("Expenses");
		//frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame7.setSize(500,500);
		frame7.setLayout(null);
		
		
		
		
		JLabel veg = new JLabel("Vegetables :"+vegetableAmount()*vegetablePrice()+" TL");
		JLabel fru = new JLabel("Fruits :"+fruitAmount()*fruitPrice()+" TL");
		JLabel kitchenIt = new JLabel("Kitchen Items :"+kitchenItAmount()*kitchenItPrice()+" TL");
		JLabel alt = new JLabel("Alteration :"+altPrice()+" TL");
		JLabel meat = new JLabel("Meat products :"+meatAmount()*meatPrice()+" TL");
		JLabel dairy = new JLabel("Dairy products :"+dairyAmount()*dairyAmount()+" TL");
		JLabel alcohol = new JLabel("Alcoholic products :"+alcholicAmount()*alcoholPrice()+" TL");
		JLabel nonalcohol = new JLabel("Non-alcoholic products :"+nonalcoholAmount()*nonAlcoholPrice()+" TL");
		
		veg.setBounds(20, 60, 200, 30);
		fru.setBounds(20, 80, 200, 30);
		kitchenIt.setBounds(20, 100, 200, 30);
		alt.setBounds(20, 120, 200, 30);
		meat.setBounds(20, 140, 200, 30);
		dairy.setBounds(20, 160, 200, 30);
		alcohol.setBounds(20, 180, 200, 30);
		nonalcohol.setBounds(20, 200, 200, 30);
		
		frame7.add(nonalcohol);
		frame7.add(alcohol);
		frame7.add(dairy);
		frame7.add(meat);
		frame7.add(alt);
		frame7.add(kitchenIt);
		frame7.add(veg);
		frame7.add(fru);
		
		frame7.setVisible(true);
	}


	public void showGUI4(boolean isMgr)
	{
		acikBufe = new JButton("Açýk Büfe");
		restaurant = new JButton("Restaurant");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		izinGunu = new JButton("Set Day Off");

		/*GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);*/
		frame = new JFrame("Restaurant Department");

		frame.setSize(500,500);
		frame.setLayout(null);

		frame.setVisible(true);


		if(isMgr)
		{

			vardiyadegistir.setBounds(20,60,160,30);
			izinGunu.setBounds(20,100,160,30);
			expenses.setBounds(20,140,160,30);
			acikBufe.setBounds(20,200,160,30);
			restaurant.setBounds(20,240,160,30);
			
			
			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.add(izinGunu);
			frame.add(acikBufe);
			frame.add(restaurant);



			restaurant.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					restManager();
				}
			});

			vardiyadegistir.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					vardiyaDegistir();
				}
			});
			izinGunu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					izinGunuDegistir();
				}
			});


			acikBufe.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						acikManager();
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


		}


		else 
		{


			acikBufe.setBounds(20,60,160,30);
			restaurant.setBounds(20,100,160,30);
			
			frame.add(acikBufe);
			frame.add(restaurant);

			restaurant.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						restaurant();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			
			acikBufe.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						acikBuf();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});


			frame.setVisible(true);


		}
	}
}
