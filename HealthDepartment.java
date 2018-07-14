import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HealthDepartment {

	private JButton calisanEkle;
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
	
		calisanEkle = new JButton("Calisan Ekle");
		hastaEkle = new JButton("Hasta Ekle");
		vardiyadegistir = new JButton("Vardiya Degistir");
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
			frame.add(calisanEkle);
			frame.add(vardiyadegistir);
			frame.add(expenses);
			frame.setVisible(true);
			expenses.addActionListener(new ActionListener()
			
			{
				public void actionPerformed(ActionEvent event)
				{
					expenses();
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
					JTextField name = new JTextField();
					JTextField last_name = new JTextField();
					JTextField tel_no = new JTextField();
					JTextField description = new JTextField();
			
					
					JLabel name_Lab = new JLabel("Name : ");
					JLabel lastName_Lab = new JLabel("Last Name : ");
					JLabel telNo_Lab = new JLabel("Tel No : ");
					JLabel description_Lab = new JLabel("Description : ");
					
					name_Lab.setBounds(20, 60, 100, 30);
					lastName_Lab.setBounds(20, 100, 100, 30);
					telNo_Lab.setBounds(20, 140, 100, 30);
					description_Lab.setBounds(20, 180, 100, 30);
					
					
					name.setBounds(120, 60, 150, 30);
					last_name.setBounds(120, 100, 150, 30);
					tel_no.setBounds(120, 140, 150, 30);
					description.setBounds(120, 180, 150, 30);
					save.setBounds(100, 250, 100, 30);
					
					hastaFrame.add(name);
					hastaFrame.add(last_name);
					hastaFrame.add(tel_no);
					hastaFrame.add(description);
					
					hastaFrame.add(name_Lab);
					hastaFrame.add(lastName_Lab);
					hastaFrame.add(telNo_Lab);
					hastaFrame.add(description_Lab);
					hastaFrame.add(save);
					
					hastaFrame.setVisible(true);			

				}
			});
		}

	}
	
	public void expenses() {
		JFrame expenses = new JFrame();
		expenses.setSize(700,700);
		expenses.setLayout(new GridLayout(6,6));
		
		String pkTotalPrice = "";
		String bandageTotalPrice = "";
		String syringeTotalPrice = "";
		String frTotalPrice = "";
		String maskTotalPrice= "";
		
		try {
			pkTotalPrice = "Painkiller expenses: "+ Integer.toString(pk_amount()[0]*pk_price()[0]);
			bandageTotalPrice = "Bandage expenses: "+ Integer.toString(bandage_amount()[0]*bandage_price()[0]);
			syringeTotalPrice = "Syringe expenses: "+ Integer.toString(syringe_amount()[0]*syringe_price()[0]);
			frTotalPrice = "Fever Reducer expenses: "+ Integer.toString(fr_amount()[0]*fr_price()[0]);
			maskTotalPrice = "Mask expenses: "+ Integer.toString(mask_amount()[0]*mask_price()[0]);

			
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
