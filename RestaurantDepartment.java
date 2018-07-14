import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RestaurantDepartment {

	private JButton stok;
	private JButton calisanekle;
	private JButton vardiyadegistir;
	private JButton expenses;
	private JFrame frame;
	
	public void showGUI4(boolean isMgr)
	{
		stok = new JButton("Stok");
		calisanekle = new JButton("Calisan Ekle");
		vardiyadegistir = new JButton("Vardiya Degistir");
		expenses = new JButton("Expenses");
		GridLayout layout = new GridLayout(6,6);
		layout.setHgap(60);
		layout.setVgap(60);
		frame = new JFrame();
		
		frame.setSize(500,500);
		frame.setLayout(layout);

		frame.setVisible(true);
		frame.add(stok);

		if(isMgr)
		{
			frame.add(calisanekle);
			frame.add(vardiyadegistir);
			frame.add(expenses);
		}
		else {
			frame.setVisible(true);
			}
	}
}
