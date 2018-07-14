import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.plaf.ComboBoxUI;

public class CleaningDepartment extends JFrame implements ActionListener {

	private JButton kirliodalar;
	private JButton calisanekle;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JFrame frame;
	private JFrame frame2;




	private int room_no;
	private int cleeanliness;
	private int room_floor;
	private int room_type;
	private int towel_room;
	private int soap_room;
	private int shamp_room;
	private int water_room;

	public  Connection con = null;
	public  PreparedStatement pst = null;
	public  ResultSet rs = null;

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






// kirli odalarý combobox haline getirir;
	
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
					
					System.out.println(room2[i]);
				
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
			
			
			
			
			
			
			
			
			
			

		}

		else {
			
			
			frame.add(kirliodalar);
			frame.setVisible(true);
			
			
			kirliodalar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						
							dirty();

				}				
			});
			
		
			
		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
