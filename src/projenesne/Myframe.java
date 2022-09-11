package projenesne;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import static projenesne.MainPanel.Panel2;
import static projenesne.MainPanel.Tablo;
import static projenesne.MainPanel.tablemodel;

public class Myframe extends JFrame {
    public static BeyazEsya Esya=new BeyazEsya();
   
    public static int case_;
    static boolean secim;
    public static String Secilen_esya;
    public static MainPanel Ana_Panel;

    public Myframe() {
        
        //FRAME
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Beyaz Esya");
        setSize(1000, 625);
        setLocation(200, 50);
        setResizable(false);
        getContentPane().setBackground(new Color(235, 237, 239));
        getContentPane().setLayout(null);
       

        //Ana Panel
        Ana_Panel = new MainPanel();
        Ana_Panel.removeAll();
        getContentPane().add(Ana_Panel);
        getContentPane().repaint();
        
        //MENU BAR
        JMenuBar MenuBar = new JMenuBar();
        Border border = new LineBorder(new Color(46, 46, 46));
        MenuBar.setBackground(new Color(245, 176, 65));
        MenuBar.setBorder(border);
        JMenu jm = new JMenu();
        jm.setText("Version No ");
        JMenu jm2 = new JMenu();
        jm2.setText("Gelistirici");
        JMenuItem item =new JMenuItem("ver. final");
        JMenuItem item2 =new JMenuItem("OGUZ EREN ACAR");
        
        jm2.add(item2);
        jm.add(item);
        MenuBar.add(jm);
        MenuBar.add(jm2);
        setJMenuBar(MenuBar);

        //SEARCH BAR
        JTextField arama_cub = new JTextField();
        arama_cub.setOpaque(false);
        arama_cub.setBounds(200, 41, 400, 30);
       
        
        //BUTONLAR 
        
        
        // Arama buton
        JButton Arab = new JButton();
        Arab.setText("Ara");
        Arab.setBounds(625, 40, 80, 30);
        Arab.setBackground(new Color(46, 46, 46));
        Arab.setForeground(Color.WHITE);

        Arab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //arama islemi ve panelde gosterimi
                //tablo yeni aramalar icin temizlenir 
              Esya.Arama(arama_cub.getText().toUpperCase());
              SetTableforMainPanel("Arama_sonuc");
              Esya.clearTable("Arama_sonuc", 1);
             
            }

        });
        
        //Gelismis Arama buton
        JButton Gelismis_Arab = new JButton();
        Gelismis_Arab.setText("Gelismis Arama");
        Gelismis_Arab.setBounds(730, 40, 150, 30);
        Gelismis_Arab.setBackground(new Color(46, 46, 46));
        Gelismis_Arab.setForeground(Color.WHITE);
        
        Gelismis_Arab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
            }

        });

        
        //uRuN EKLE buton
        JButton urun_ekleB = new JButton();
        urun_ekleB.setText("Urun Ekle");
        urun_ekleB.setBounds(40, 120, 100, 25);
        urun_ekleB.setBackground(new Color(46, 46, 46));
        urun_ekleB.setForeground(Color.WHITE);

        urun_ekleB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Ana panel uretilir ve esya seciliminden sonraki 
               //islem secimi  icin bir case gonderilir
               getContentPane().remove(Ana_Panel);
                case_ = 1;//public
                Ana_Panel = new MainPanel();

                getContentPane().add(Ana_Panel);
                getContentPane().repaint();
            }
        });
        
        
        //URUN SAT buton
        JButton urun_satB = new JButton();
        urun_satB.setText("Urun Sat");
        urun_satB.setBounds(40, 160, 100, 25);
        urun_satB.setBackground(new Color(46, 46, 46));
        urun_satB.setForeground(Color.WHITE);

        urun_satB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ana panel uretilir ve esya seciliminden sonraki 
               //islem secimi icin bir case gonderilir
                getContentPane().remove(Ana_Panel);
                case_ = 2;//public
                Ana_Panel = new MainPanel();

                getContentPane().add(Ana_Panel);
                getContentPane().repaint();
            }
        });

        //STOK Arsiv buton
        JButton stok_arvB = new JButton();
        stok_arvB.setText("Stok Arsiv");
        stok_arvB.setBounds(40, 200, 100, 25);
        stok_arvB.setBackground(new Color(46, 46, 46));
        stok_arvB.setForeground(Color.WHITE);

        stok_arvB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //İlgili Tablo Ana panelde gozukur
                SetTableforMainPanel("TabloStok_Kayıtları");

            }

        });
        
        
        //SATIS Arsiv buton
        JButton satis_arvB = new JButton();
        satis_arvB.setText("Satıs Arsiv");
        satis_arvB.setBounds(40, 240, 100, 25);
        satis_arvB.setBackground(new Color(46, 46, 46));
        satis_arvB.setForeground(Color.WHITE);

        satis_arvB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //İlgili Tablo Ana panelde gozukur
                    SetTableforMainPanel("TabloSatıs_Kayıtları");
            }

        });
        
         //Eklemeler
        getContentPane().add(Arab);
        getContentPane().add(Gelismis_Arab);
        getContentPane().add(arama_cub);
        getContentPane().add(urun_ekleB);
        getContentPane().add(urun_satB);
        getContentPane().add(stok_arvB);
        getContentPane().add(satis_arvB);
        setVisible(true);

    }
    public void SetTableforMainPanel(String TableName){
                
                //Panel2 ,tablo tanımlanması ve ekrana 
                //update table ile basilir.
                Ana_Panel.removeAll();
         
                Tablo = new JTable();
                Esya.updateTable(TableName);
                Tablo.setModel(tablemodel);
                Tablo.setEnabled(false);

                JScrollPane Scroll_pane = new JScrollPane(Tablo);
                Scroll_pane.setBounds(10, 10, 660, 340);

                Panel2 = new JPanel();
                Panel2.setLayout(null);
                Panel2.setBounds(20, 20, 680, 360);
                Panel2.add(Scroll_pane);

                Ana_Panel.add(Panel2);
                Ana_Panel.repaint();
            
        }

}
