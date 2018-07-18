import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TransportationDepartment extends JFrame implements ActionListener{

	private JButton cars_amount;
	private JButton fuel_expenses;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JButton izinGunu;
	private JFrame frame;
	private JFrame frame2;


	public  Connection con = null; 
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;

	public TransportationDepartment(){}

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


	public int [] fuelExpenses() throws SQLException {

		Connection();

		int [] fuelexp = new int[1];
		pst = con.prepareStatement("SELECT fuel_expenses FROM transportation_dept ");
		rs = pst.executeQuery();


		while (rs.next()) {		
			fuelexp[0] = rs.getInt("fuel_expenses");
		}

		return fuelexp;
	}
	public int [] fuelPrice() throws SQLException {

		Connection();

		int [] fuel_price = new int[1];
		pst = con.prepareStatement("SELECT fuel_price FROM stocks");
		rs = pst.executeQuery();

		while (rs.next()) {
			fuel_price[0] = rs.getInt("fuel_price");
		}
		return fuel_price;
	}

	public JFrame fuel() throws SQLException
	{


		GridLayout layout = new GridLayout(6,6);
		frame2 = new JFrame("Fuel Expenses");
		//frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(500,500);
		frame2.setLayout(layout);

		String fuel_expenses = "Total expenses : "+fuelExpenses()[0]*fuelPrice()[0]+"";

		JLabel label1 = new JLabel(fuel_expenses);
		frame2.add(label1);

		frame2.setVisible(true);

		return frame2;

	}
	public int carsAmount() throws SQLException {

		Connection();

		int  cars =0;
		pst = con.prepareStatement("SELECT cars_amount FROM transportation_dept ");
		rs = pst.executeQuery();

		while(rs.next())
			cars = rs.getInt("cars_amount");

		return cars;
	}

	public JFrame Car() throws SQLException
	{

		frame2 = new JFrame("Cars");
		//frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(500,500);
		frame2.setLayout(null);

		String carCount = String.valueOf(carsAmount());
		System.out.println(carsAmount());		

		JLabel cText = new JLabel("Cars amount :");
		JTextArea l = new JTextArea();
		l.setEditable(false);
		l.setText(carCount);
		
		cText.setBounds(20, 60, 120, 30);
		l.setBounds(120, 60, 100, 30);
		
		frame2.add(cText);
		frame2.add(l);
		frame2.setVisible(true);
		return frame2;

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


	public void showGUI2(boolean isMgr) throws SQLException
	{
		cars_amount = new JButton("Cars Amount");
		fuel_expenses = new JButton("Fuel Expenses");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		izinGunu = new JButton("Set day Off");

		frame = new JFrame("Transportation Department");

		frame.setSize(500,500);
		frame.setLayout(null);

		frame.setVisible(true);


		if(isMgr)
		{
			
			
			vardiyadegistir.setBounds(20, 60, 160, 30);
			izinGunu.setBounds(20, 100, 160, 30);
			expenses.setBounds(20, 140, 160, 30);
			cars_amount.setBounds(20, 180, 160, 30);
			
			
			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.add(izinGunu);
			frame.add(cars_amount);

			cars_amount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						Car();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

			});

			vardiyadegistir.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					vardiyaDegistir();
				}
			});
			expenses.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						fuel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

			});
			
			izinGunu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
						izinGunuDegistir();
				}
			});
			
			
		}
		else {
			
			
			cars_amount.setBounds(20, 60, 160, 30);
			fuel_expenses.setBounds(20, 100, 160, 30);
		
			frame.add(cars_amount);
			frame.add(fuel_expenses);

			
			frame.setVisible(true);
			cars_amount.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						Car();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

			});
			fuel_expenses.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						fuel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

			});
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


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}