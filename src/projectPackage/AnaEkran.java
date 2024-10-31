package projectPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AnaEkran extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnaEkran frame = new AnaEkran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnaEkran() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 600);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel labelAnaMenu = new JLabel("Ana Menü");
		labelAnaMenu.setFont(new Font("Constantia", Font.BOLD | Font.ITALIC, 40));
		labelAnaMenu.setBounds(310, 70, 191, 65);
		panel.add(labelAnaMenu);
		
		JButton btnTarifler = new JButton("Tarifler");
		btnTarifler.setFont(new Font("Dubai Light", Font.PLAIN, 25));
		btnTarifler.setForeground(Color.WHITE);
		btnTarifler.setBackground(new Color(45, 199, 110));
		btnTarifler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TariflerEkran tariflerpaneli = new TariflerEkran();
				tariflerpaneli.setVisible(true);
				AnaEkran.this.dispose();
			}
		});
		btnTarifler.setBounds(250, 210, 300, 50);
		panel.add(btnTarifler);
		
		JButton btnTarifYonetimi = new JButton("Tarif Yönetimi");
		btnTarifYonetimi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TarifYonetimiEkran tarifyonetimpaneli = new TarifYonetimiEkran();
				tarifyonetimpaneli.setVisible(true);
				AnaEkran.this.dispose();
			}
		});
		btnTarifYonetimi.setForeground(Color.WHITE);
		btnTarifYonetimi.setFont(new Font("Dubai Light", Font.PLAIN, 25));
		btnTarifYonetimi.setBackground(new Color(45, 199, 110));
		btnTarifYonetimi.setBounds(250, 300, 300, 50);
		panel.add(btnTarifYonetimi);
		
		JButton btnTarifArama = new JButton("Tarif Arama");
		btnTarifArama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TarifAramaEkran tarifaramapaneli = new TarifAramaEkran();
				tarifaramapaneli.setVisible(true);
				AnaEkran.this.dispose();
			}
		});
		btnTarifArama.setForeground(Color.WHITE);
		btnTarifArama.setFont(new Font("Dubai Light", Font.PLAIN, 25));
		btnTarifArama.setBackground(new Color(45, 199, 110));
		btnTarifArama.setBounds(250, 390, 300, 50);
		panel.add(btnTarifArama);
	}
}
