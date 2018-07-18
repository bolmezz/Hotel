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
import javax.swing.JTextField;

public class HealthDepartment {

	private JButton izinGunu;
	private JButton hastaEkle;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JButton save;
	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;
	private JFrame frame, hastaFrame;

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
	
	public void showGUI5(boolean isMgr) {
	
		izinGunu = new JButton("Set Day Off");
		hastaEkle = new JButton("Add Patient");
		vardiyadegistir = new JButton("Change Shift");
		expenses = new JButton("Expenses");
		save = new JButton("Save");
		
		
		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame("Health Department");
		hastaFrame = new JFrame("Hasta Kayit");
		
		hastaFrame.setSize(700,700);
		hastaFrame.setLayout(null);
		hastaFrame.setLocationRelativeTo(null); 
		
		frame.setSize(500,500);
		frame.setLayout(layout);

		if(isMgr)
		{
			frame.add(izinGunu);
			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.setVisible(true);
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
			frame.setVisible(true);
			frame.add(hastaEkle);
			hastaEkle.addActionListener(new ActionListener()
			
			{
				public void actionPerformed(ActionEvent event)
				{	
					JTextField tc = new JTextField();
					JTextField tel_no = new JTextField();
					JTextField description = new JTextField();
			
					
					JLabel tc_Lab = new JLabel("TC No : ");
					JLabel telNo_Lab = new JLabel("Tel No : ");
					JLabel description_Lab = new JLabel("Description : ");
					
					tc_Lab.setBounds(20, 60, 100, 30);
					telNo_Lab.setBounds(20, 140, 100, 30);
					description_Lab.setBounds(20, 180, 100, 30);
					
					
					tc.setBounds(120, 60, 150, 30);
					tel_no.setBounds(120, 140, 150, 30);
					description.setBounds(120, 180, 150, 30);
					save.setBounds(100, 250, 100, 30);
					
					hastaFrame.add(tc);
					hastaFrame.add(tel_no);
					hastaFrame.add(description);
					
					hastaFrame.add(tc_Lab);
					hastaFrame.add(telNo_Lab);
					hastaFrame.add(description_Lab);
					hastaFrame.add(save);
					
					hastaFrame.setVisible(true);			

					save.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent event)
						{
							String tcInfo = tc.getText();
							String desc = description.getText();
							String telNoInfo = tel_no.getText();
							kayitAl(tcInfo, desc, telNoInfo);

							JOptionPane.showMessageDialog(hastaFrame, "Patients info has been taken !");
						}
					});
				}
			});
		}

	}
	public void kayitAl(String tc, String desc, String telNo) {
		Connection();
		
		String a = "INSERT INTO health_service (d_comment, p_tc, p_telno) VALUES ('"+ desc + "', '" + tc + "', '" + telNo + "')";
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
					JOptionPane.showMessageDialog(vardiya, "Shift has been changed !");
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
					JOptionPane.showMessageDialog(vardiya, "Day-off has been changed !");
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
		JFrame expenses = new JFrame("Health Department Expenses");
		expenses.setSize(700,700);
		expenses.setLayout(new GridLayout(6,6));
		
		String pkTotalPrice = "";
		String bandageTotalPrice = "";
		String syringeTotalPrice = "";
		String frTotalPrice = "";
		String maskTotalPrice= "";
		
		try {
			pkTotalPrice = "Painkiller expenses: "+ Integer.toString(pk_amount()[0]*pk_price()[0])+" TL";
			bandageTotalPrice = "Bandage expenses: "+ Integer.toString(bandage_amount()[0]*bandage_price()[0])+" TL";
			syringeTotalPrice = "Syringe expenses: "+ Integer.toString(syringe_amount()[0]*syringe_price()[0])+" TL";
			frTotalPrice = "Fever Reducer expenses: "+ Integer.toString(fr_amount()[0]*fr_price()[0])+" TL";
			maskTotalPrice = "Mask expenses: "+ Integer.toString(mask_amount()[0]*mask_price()[0])+" TL";

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel pk = new JLabel(pkTotalPrice);
		JLabel band = new JLabel(bandageTotalPrice);
		JLabel syr = new JLabel(syringeTotalPrice);
		JLabel fr = new JLabel(frTotalPrice);
		JLabel mask = new JLabel(maskTotalPrice);
		
		expenses.add(pk);
		expenses.add(band);
		expenses.add(syr);
		expenses.add(fr);
		expenses.add(mask);
		
		expenses.setVisible(true);
		
	}
	
	public int[] pk_amount() throws SQLException {

		Connection();

		int[] pk_amount = new int[1];
		pst = con.prepareStatement("SELECT painkiller_amount FROM health_dept");
		rs = pst.executeQuery();

		while(rs.next())
			pk_amount[0] = rs.getInt("painkiller_amount");


		return pk_amount;
	}
	
	public int[] pk_price() throws SQLException
	{

		int[] pk_price = new int[1];

		pst = con.prepareStatement("SELECT painkiller_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			pk_price[0] = rs.getInt("painkiller_price");

		return pk_price;

	}
	
	public int[] bandage_amount() throws SQLException {

		Connection();

		int[] bandage_amount = new int[1];
		pst = con.prepareStatement("SELECT bandage_amount FROM health_dept");
		rs = pst.executeQuery();

		while(rs.next())
			bandage_amount[0] = rs.getInt("bandage_amount");


		return bandage_amount;
	}
	
	public int[] bandage_price() throws SQLException
	{

		int[] bandage_price = new int[1];

		pst = con.prepareStatement("SELECT bandage_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			bandage_price[0] = rs.getInt("bandage_price");

		return bandage_price;

	}
	
	public int[] syringe_amount() throws SQLException {

		Connection();

		int[] syringe_amount = new int[1];
		pst = con.prepareStatement("SELECT syringe_amount FROM health_dept");
		rs = pst.executeQuery();

		while(rs.next())
			syringe_amount[0] = rs.getInt("syringe_amount");


		return syringe_amount;
	}
	
	public int[] syringe_price() throws SQLException
	{

		int[] syringe_price = new int[1];

		pst = con.prepareStatement("SELECT syringe_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			syringe_price[0] = rs.getInt("syringe_price");

		return syringe_price;

	}

	public int[] fr_amount() throws SQLException {

		Connection();

		int[] fr_amount = new int[1];
		pst = con.prepareStatement("SELECT feverreducer_amount FROM health_dept");
		rs = pst.executeQuery();

		while(rs.next())
			fr_amount[0] = rs.getInt("feverreducer_amount");


		return fr_amount;
	}
	
	public int[] fr_price() throws SQLException
	{

		int[] fr_price = new int[1];

		pst = con.prepareStatement("SELECT fever_reducer_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			fr_price[0] = rs.getInt("fever_reducer_price");

		return fr_price;

	}

	public int[] mask_amount() throws SQLException {

		Connection();

		int[] mask_amount = new int[1];
		pst = con.prepareStatement("SELECT mask_amount FROM health_dept");
		rs = pst.executeQuery();

		while(rs.next())
			mask_amount[0] = rs.getInt("mask_amount");


		return mask_amount;
	}
	
	public int[] mask_price() throws SQLException
	{

		int[] mask_price = new int[1];

		pst = con.prepareStatement("SELECT mask_price FROM stocks");
		rs = pst.executeQuery();

		while(rs.next())
			mask_price[0] = rs.getInt("mask_price");

		return mask_price;

	}

}
