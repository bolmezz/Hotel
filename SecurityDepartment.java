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

public class SecurityDepartment { 

	private JButton vardiyadegistir;
	private JButton izinGunu;
	private JFrame frame;


	public  Connection con = null; 
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;
	
	public SecurityDepartment(){}
	
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
	
	public void showGUI6(boolean isMgr)
	{
		vardiyadegistir = new JButton("Vardiya Degistir");
		izinGunu = new JButton("Set Day Off");
		/*GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);*/
		frame = new JFrame();
		
		frame.setSize(500,500);
		frame.setLayout(null);

		frame.setVisible(true);

		if(isMgr)
		{
			
			vardiyadegistir.setBounds(20, 60, 160, 30);
			izinGunu.setBounds(20, 100, 160, 30);
			
			frame.add(vardiyadegistir);
			frame.add(izinGunu);
			
			izinGunu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					izinGunuDegistir();
					
				}
			});
			
			vardiyadegistir.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event)
				{
					vardiyaDegistir();
				}
			});
		}
		else {
			frame.setVisible(true);
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
			String a = "UPDATE employees SET shift = " + shift + " WHERE emp_id = " + id;
			pst = con.prepareStatement(a);
			update = pst.executeUpdate();
		}
	
}