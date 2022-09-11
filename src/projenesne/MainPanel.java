package projenesne;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import static projenesne.Myframe.Ana_Panel;

import static projenesne.Myframe.Secilen_esya;

import static projenesne.Myframe.case_;

public class MainPanel extends JPanel {

    static JRadioButton K_B1;
    static JRadioButton K_B2;
    static JRadioButton K_B3;
    static JPanel Panel2;
    static ButtonGroup button_g1;
    public boolean bool_1 = true;
    public boolean bool_2 = true;
    public static JTable Tablo;

    public static DefaultTableModel tablemodel = new DefaultTableModel();
    public static BeyazEsya Esya=new BeyazEsya();
    ;

    public MainPanel() {
        
        //Sol taraftan secilen butonların sag panelde ki ıslemleri icindir.

        //Panel ayarı
        setLayout(null);
        setBounds(180, 100, 720, 400);
        setBackground(new Color(245, 176, 65));
        setBorder(new LineBorder(Color.black, 2, false));

        JLabel L_Katagori = new JLabel("Katagori    :");
        L_Katagori.setBounds(100, 5, 150, 80);
        L_Katagori.setFont(new Font("", Font.ROMAN_BASELINE, 20));

//      Katagori seç için nesneler
        K_B1 = new JRadioButton("Buzdolabı");
        K_B1.setBounds(250, 30, 100, 30);
        K_B1.setBackground(new Color(245, 176, 65));
        K_B1.setFont(new Font("", Font.LAYOUT_RIGHT_TO_LEFT, 15));

        K_B2 = new JRadioButton("Camasir M.");
        K_B2.setBounds(370, 30, 120, 30);
        K_B2.setBackground(new Color(245, 176, 65));
        K_B2.setFont(new Font("", Font.LAYOUT_RIGHT_TO_LEFT, 15));

        K_B3 = new JRadioButton("Bulasik M.");
        K_B3.setBounds(490, 30, 100, 30);
        K_B3.setBackground(new Color(245, 176, 65));
        K_B3.setFont(new Font("", Font.LAYOUT_RIGHT_TO_LEFT, 15));

        button_g1 = new ButtonGroup();
        button_g1.add(K_B1);
        button_g1.add(K_B2);
        button_g1.add(K_B3);

        add(L_Katagori);
        add(K_B1);
        add(K_B2);
        add(K_B3);

        //KATAGORORİ TÜRÜ VE GEREKLİ FONKSİYON (İŞLEM)SEÇİMİ
        //Myframe den gelen case bura kullanilir.
        K_B1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Secilen_esya = "Buzdolabi";
                IslemSec(case_);
            }

        });

        K_B2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Secilen_esya = "CamasirM";
                IslemSec(case_);
            }
        });

        K_B3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Secilen_esya = "BulasikM";
                IslemSec(case_);

            }
        });

    }

    public void IslemSec(int Sayı) {

        switch (Sayı) {

            case 1:

                if (bool_1) {
                    UrunEkle();       //kullaniciden girdileri almak için gereken fonksiyon
                    bool_1 = false;
                }
                break;
            case 2:
                //Urun sat için gelen tablo ayarlanmasi
                Esya = new BeyazEsya();

                Tablo = new JTable();
                Esya.updateTable("Tablo" + Secilen_esya);
                Tablo.setModel(tablemodel);
                Tablo.setEnabled(false);

                JScrollPane Scroll_pane = new JScrollPane(Tablo);
                Scroll_pane.setBounds(10, 10, 600, 220);

                Panel2 = new JPanel();
                Panel2.setLayout(null);
                Panel2.setBounds(50, 70, 625, 240);
                Panel2.add(Scroll_pane);

                Ana_Panel.add(Panel2);

                if (bool_2) {
                    UrunSat();      //kullaniciden girdileri almak için gereken fonksiyon
                    bool_2 = false;
                }

                break;
        }
    }

    public static void UrunEkle() {

        //Labels
        JLabel L_Ad = new JLabel("Ad             :");
        JLabel L_Adet = new JLabel("Adet          :");
        JLabel L_Satisf = new JLabel("Satis Fiyat :");
        JLabel L_Alisf = new JLabel("Alis Fiyat   :");

        L_Ad.setBounds(50, 50, 150, 100);
        L_Adet.setBounds(50, 100, 150, 100);
        L_Satisf.setBounds(50, 150, 150, 100);
        L_Alisf.setBounds(50, 200, 150, 100);

        L_Ad.setFont(new Font("", Font.ROMAN_BASELINE, 20));
        L_Adet.setFont(new Font("", Font.ROMAN_BASELINE, 20));
        L_Satisf.setFont(new Font("", Font.ROMAN_BASELINE, 20));
        L_Alisf.setFont(new Font("", Font.ROMAN_BASELINE, 20));

        //TextBars
        JTextField TextBar2 = new JTextField();
        TextBar2.setBounds(200, 85, 300, 30);

        JTextField TextBar3 = new JTextField();
        TextBar3.setBounds(200, 135, 50, 30);

        JTextField TextBar4 = new JTextField();
        TextBar4.setBounds(200, 185, 100, 30);

        JTextField TextBar5 = new JTextField();
        TextBar5.setBounds(200, 235, 100, 30);

       
         //Eklemeler
        Ana_Panel.add(L_Ad);
        Ana_Panel.add(L_Adet);
        Ana_Panel.add(L_Satisf);
        Ana_Panel.add(L_Alisf);
        Ana_Panel.add(TextBar2);
        Ana_Panel.add(TextBar3);
        Ana_Panel.add(TextBar4);
        Ana_Panel.add(TextBar5);
        Ana_Panel.repaint();
        
        //  Kaydet islem Button 
        JButton KaydetB = new JButton();
        KaydetB.setText("Kaydet");
        KaydetB.setBounds(500, 300, 80, 25);
        Ana_Panel.add(KaydetB);
        
        KaydetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean urunekle = true;
                BeyazEsya Esya = new BeyazEsya();
                
                //Bu buton da girdiler alınır türleri ayarlanır ve 
                //database de islem yapmak uzere Esya nesnesinden gerekli fonksiyonlar cagrilir

                try {

                    Esya.Ad = TextBar2.getText();
                    Esya.Adet = Integer.parseInt(TextBar3.getText());
                    Esya.Satis_fiyat = Integer.parseInt(TextBar4.getText());
                    Esya.Alis_fiyat = Integer.parseInt(TextBar5.getText());

                } catch (Exception d) {
                    urunekle = false;
                    JOptionPane.showMessageDialog(null, "Lutfen butun bilgileri uygun karakterlerle doldurunuz...", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
                }
                if (urunekle) {
                    Esya.Ekle(Secilen_esya);   //Database de ekleme yapan fonksiyon
                    TextBar2.setText("");
                    TextBar3.setText("");
                    TextBar4.setText("");
                    TextBar5.setText("");
                    Ana_Panel.revalidate();//?
                    Ana_Panel.repaint();
                }
                Esya = null;
            }

        });
       

    }

    public static void UrunSat() {
        
        //Labels
        JLabel L_No = new JLabel("idNo:");
        L_No.setBounds(50, 310, 80, 100);
        L_No.setFont(new Font("", Font.ROMAN_BASELINE, 20));

        JLabel L_SatisF = new JLabel("Satis F:");
        L_SatisF.setBounds(180, 310, 150, 100);
        L_SatisF.setFont(new Font("", Font.ROMAN_BASELINE, 20));

        JLabel L_Adet = new JLabel("Adet:");
        L_Adet.setBounds(390, 310, 50, 100);
        L_Adet.setFont(new Font("", Font.ROMAN_BASELINE, 20));

        //TextBars
        JTextField TextBar1 = new JTextField();
        TextBar1.setBounds(110, 340, 50, 30);

        JTextField TextBar2 = new JTextField();
        TextBar2.setBounds(260, 340, 100, 30);

        JTextField TextBar3 = new JTextField();
        TextBar3.setBounds(450, 340, 100, 30);

       

        //Eklemeler
        Ana_Panel.add(L_No);
        Ana_Panel.add(L_SatisF);
        Ana_Panel.add(L_Adet);
        Ana_Panel.add(TextBar1);
        Ana_Panel.add(TextBar2);
        Ana_Panel.add(TextBar3);
        
        
         //Sat islem Button 
        JButton SatB = new JButton();
        SatB.setText("Sat");
        SatB.setBounds(600, 345, 80, 25);
        Ana_Panel.add(SatB);
        
        SatB.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
           
                BeyazEsya Esya = new BeyazEsya();
                 //Bu buton da girdiler alınır türleri ayarlanır ve 
                //database de islem yapmak uzere Esya nesnesinden gerekli fonksiyonlar cagrilir

                try {
                Esya.id=Integer.parseInt(TextBar1.getText());
                Esya.Satis_fiyat=Integer.parseInt(TextBar2.getText());
                Esya.Adet=Integer.parseInt(TextBar3.getText());
                  
                Esya.Sat( Secilen_esya);            //Database de silme yapan fonksiyon
                Esya.updateTable(Esya.TableName);   //tablo yenilenir 
                Panel2.repaint();

                } catch (Exception d) {
                    
                    JOptionPane.showMessageDialog(null, "Lutfen butun bilgileri uygun karakterlerle doldurunuz...", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
                }
               
            }

        });
        Esya = null;
        Ana_Panel.repaint();
    }

}
