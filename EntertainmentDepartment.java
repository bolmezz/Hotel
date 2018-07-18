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
import javax.swing.JTextField;

public class EntertainmentDepartment {

	private JButton izinGunu;
	private JButton vardiyadegistir;
	private JButton expenses, aktiviteEkle;
	private JFrame frame;
	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;

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
	
	public void showGUI3(boolean isMgr)
	{
		izinGunu = new JButton("Change Day Off");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		aktiviteEkle = new JButton("New Activity");
		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame("Entertainment Department");
		
		frame.setSize(500,500);
		frame.setLayout(layout);

		frame.setVisible(true);

		if(isMgr)
		{
			frame.add(izinGunu);
			frame.add(vardiyadegistir);
			frame.add(expenses);
			
			expenses.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					expenses();
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
		}
		else {
			frame.add(aktiviteEkle);
			frame.setVisible(true);
			aktiviteEkle.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					aktivite_Ekle();
				}
			});
			}
	}
	
	public void aktivite_Ekle() {
		JFrame aktivite = new JFrame("Add Activity");
		aktivite.setSize(500,500);
		aktivite.setLayout(null);

		JLabel activityDescLab = new JLabel("Activity Description");
		JTextField activityDesc = new JTextField();
		JLabel activityPriceLab = new JLabel("Activity Price");
		JTextField activityPrice = new JTextField();
		
		JButton add = new JButton("Add Activity");
		
		activityDescLab.setBounds(20, 60, 200, 30);
		activityDesc.setBounds(230, 60, 200, 30);
		activityPriceLab.setBounds(20, 100, 200, 30);
		activityPrice.setBounds(230, 100, 200, 30);
		add.setBounds(20, 150, 160, 30);

		aktivite.add(activityDescLab);
		aktivite.add(activityDesc);
		aktivite.add(activityPriceLab);
		aktivite.add(activityPrice);
		aktivite.add(add);
		aktivite.setVisible(true);
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				Connection();
				
				String a = "INSERT INTO entertainment_dept (activity_desc, entertainment_expenses) VALUES ('"+ activityDesc.getText() + "', '" + activityPrice.getText() + "')";
				try {
					int update;
					pst = con.prepareStatement(a);
					System.out.println(pst);
					update = pst.executeUpdate();
					System.out.println("updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	public void izinGunuDegistir() {
		JFrame vardiya = new JFrame("Shift Changer");
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
		String a = "UPDATE employees SET day_offs = '" + dayOff + "' WHERE emp_id = " + id;
		pst = con.prepareStatement(a);
		update = pst.executeUpdate();
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
		String a = "UPDATE employees SET shift = " + shift + " WHERE emp_id = " + id;
		pst = con.prepareStatement(a);
		update = pst.executeUpdate();
	}
	
	public void expenses() {
		JFrame expenses = new JFrame("Entertainment Department Expenses");
		expenses.setSize(700,700);
		expenses.setLayout(new GridLayout(6,6));
		
		String msgOilPrice = "";
		String entertainmentTotalPrice = "";
		
		try {
			msgOilPrice = "Massage Oil expenses: "+ Integer.toString(msg_amount()[0]*msg_price()[0])+" TL";
			entertainmentTotalPrice = "Total Expense of Entertainment: "+ expenses_price()[0]+" TL";

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel msgOil = new JLabel(msgOilPrice);
		JLabel eExpenses = new JLabel(entertainmentTotalPrice);
		
		expenses.add(msgOil);
		expenses.add(eExpenses);
		
		expenses.setVisible(true);
		
	}

	public int[] msg_amount() throws SQLException {

		Connection();

		int[] msg_amount = new int[1];
		pst = con.prepareStatement("SELECT massage_oil_amount FROM entertainment_dept");
		rs = pst.executeQuery();

		while(rs.next())
			msg_amount[0] += rs.getInt("massage_oil_amount");


		return msg_amount;
	}
	
	public int[] msg_price() throws SQLException
	{

		int[] msg_price = new int[1];

		pst = con.prepareStatement("SELECT massage_oil_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			msg_price[0] = rs.getInt("massage_oil_price");

		return msg_price;

	}
	
	public int[] expenses_price() throws SQLException {

		Connection();

		int[] price = new int[1];
		pst = con.prepareStatement("SELECt entertainment_expenses FROM entertainment_dept");
		rs = pst.executeQuery();

		while(rs.next())
			price[0] += rs.getInt("entertainment_expenses");


		return price;
	}

}
