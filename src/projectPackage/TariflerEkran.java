package projectPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class TariflerEkran extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelTarifler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TariflerEkran frame = new TariflerEkran();
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
	public TariflerEkran() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelTarifler = new JPanel();
		panelTarifler.setBounds(0, 0, 800, 600);
		contentPane.add(panelTarifler);
		panelTarifler.setLayout(null);	
		tarifleriListele();
		
		
		JButton btnGeriTarifler = new JButton("Geri");
		btnGeriTarifler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnaEkran anapanel = new AnaEkran();
				anapanel.setVisible(true);
				TariflerEkran.this.dispose();
			}
		});
		btnGeriTarifler.setForeground(Color.WHITE);
		btnGeriTarifler.setFont(new Font("Dubai Light", Font.PLAIN, 25));
		btnGeriTarifler.setBackground(new Color(255, 85, 76));
		btnGeriTarifler.setBounds(250, 530, 300, 30);
		panelTarifler.add(btnGeriTarifler);

		
	}
	
	private void tarifleriListele()
	{
	    DatabaseManager dbManager = new DatabaseManager();
	    List<Tarif> tarifler = dbManager.getTarifler();

	    JPanel tariflerPanel = new JPanel(new GridLayout(0, 3, 5, 5)); 
	    //tariflerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    for(Tarif tarif : tarifler)
	    {
	        JPanel tarifPanel = new JPanel();
	        tarifPanel.setLayout(new BoxLayout(tarifPanel, BoxLayout.Y_AXIS));
	        tarifPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	        ImageIcon imageIcon = new ImageIcon(tarif.getTarifYolu());
	        Image scaledImage = imageIcon.getImage().getScaledInstance(240, 250, Image.SCALE_SMOOTH); // Resize to fit
	        JLabel fotoLabel = new JLabel(new ImageIcon(scaledImage));
	        fotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        tarifPanel.add(fotoLabel);

	        JLabel adLabel = new JLabel(tarif.getTarifAdi());
	        adLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        tarifPanel.add(adLabel);

	        JLabel sureLabel = new JLabel("Hazırlama Süresi: " + tarif.getHazirlamaSuresi() + " dk");
	        sureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        tarifPanel.add(sureLabel);
	        
	        String maliyet = String.format("%.2f TL", dbManager.tarifMaliyet(tarif));
	        JLabel maliyetLabel = new JLabel("Maliyet: " + maliyet);
	        maliyetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        tarifPanel.add(maliyetLabel);


	        tarifPanel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	tarifDetaylari(tarif);
	            }
	        });

	        tariflerPanel.add(tarifPanel);
	    }

	    JScrollPane scrollPane = new JScrollPane(tariflerPanel);
	    scrollPane.setBounds(10, 15, 760, 510);
	    panelTarifler.add(scrollPane);
	}

	private void tarifDetaylari(Tarif tarif)
	 {
		 DatabaseManager dbManager = new DatabaseManager();
		 ImageIcon imageIcon = new ImageIcon(tarif.getTarifYolu()); // Load image
		 JLabel imageLabel = new JLabel(imageIcon);
		 String formattedTalimatlar = yaziAyarla(tarif.getTalimatlar(), 50);
		 
		 List<Malzeme> tarifMalzemeleri = dbManager.getTarifinMalzemeleri(tarif);
		 
		 StringBuilder malzemelerText = new StringBuilder();
		 for (Malzeme malzeme : tarifMalzemeleri)
		 {			 
			 malzemelerText.append(malzeme.getMalzemeAdi()).append(", ");
		 }
		 if(malzemelerText.length() > 0)
			 malzemelerText.setLength(malzemelerText.length() - 2);
		 
		 JOptionPane.showMessageDialog(this,
				 "Tarif Adı: " + tarif.getTarifAdi() + "\n" +
				 "Kategori: " + tarif.getKategori() + "\n" +
				 "Süre: " + tarif.getHazirlamaSuresi() + " dk\n" +
				 "Malzemeler: " + malzemelerText.toString() + "\n" +
				 "\nHazırlanışı: " + formattedTalimatlar,
				 "Tarif Detayları",
				 JOptionPane.INFORMATION_MESSAGE
		 );
	 }
	 
	private String yaziAyarla(String text, int lineLength)
	 {
		 StringBuilder metin = new StringBuilder();
		 String[] kelimeler = text.split(" ");
		 int mevcutSatır = 0;
		    
		 for (String kelime : kelimeler)
		 {			 
		     if(mevcutSatır + kelime.length() > lineLength)
		     {	    	 
		    	 metin.append("\n");
		    	 mevcutSatır = 0;
		     }
		     metin.append(kelime).append(" ");
		     mevcutSatır += kelime.length() + 1;
		  }
		  return metin.toString().trim();
	 }
}
