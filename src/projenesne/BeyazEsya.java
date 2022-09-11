package projenesne;

import DataPackage.Database;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.String.valueOf;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import static projenesne.MainPanel.tablemodel;
import static projenesne.Myframe.Secilen_esya;

public class BeyazEsya {

    long millis = System.currentTimeMillis();
    java.sql.Date date = new java.sql.Date(millis);
    String Ad;
    int id;
    int Adet;
    int Satis_fiyat;
    int Alis_fiyat;
    static int Son_id;
    String TableName;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date_ = new Date();
    boolean bool = false;
    boolean bool2=true;
    static Database DB = new Database("MyData");

    
    //Databese katmani ve kullanıcı girdileri ile Esya tabloları arasında ki islemleri yapan classtir 
    BeyazEsya() {
        
        //İlk kullanım icin tablo uretimleri 
        
        // DB.executeSQL("CREATE TABLE TabloBuzdolabi(id int, AD varchar(40) ,Adet int ,Satış_fiyatı int  ,Alış_fiyatı int ,Tarih date)", "XXX");
        // DB.executeSQL("CREATE TABLE TabloCamasirM(id int, AD varchar(40) ,Adet int ,Satış_fiyatı int  ,Alış_fiyatı int ,Tarih date)", "XXX");
        // DB.executeSQL("CREATE TABLE TabloBulasikM(id int, AD varchar(40) ,Adet int ,Satış_fiyatı int  ,Alış_fiyatı int ,Tarih date)", "XXX");
        // DB.executeSQL("CREATE TABLE TabloStok_Kayıtları(id int,Tarih nvarchar(20),Katagori nvarchar(15),Açıklama nvarchar(80) ,Alıs_fiyat int )", "XXX");
        // DB.executeSQL("CREATE TABLE TabloSatıs_Kayıtları(id int,Tarih nvarchar(20),Katagori nvarchar(15),Açıklama nvarchar(80) ,Satış_fiyatı int )", "XXX");

    }

    public void Ekle(String Secilen_Esya) {
        TableName = "Tablo" + Secilen_Esya;
        //Tablodaki adlarin arrayi
        ArrayList<ArrayList<String>> urun_ad_list = DB.executeQuerySQL("Select * From " + TableName, "", "AD");
       
        for (int i = 0; i < urun_ad_list.size(); i++) {
       
              if(urun_ad_list.get(i).get(0).equals(Ad)){//Gelen tabloda varsa
              
                    bool=true;
                    break;
                }
        }
       
        if (bool&&bool2) {//Gelen tabloda varsa
              Dialog();
   
        } else {//Gelen tabloda yoksa
            int lastid = DB.getLastId(TableName) + 1;
            
            //Gerekli tabloya ekleme islemi
            DB.executeSQL("Insert into " + TableName + " VALUES(" + lastid + ",'" + Ad + "'," + Adet + "," + Satis_fiyat + "," + Alis_fiyat + ",'" + date + "')", "AKİKİKİ");
            update_id_numbers(TableName);
            JOptionPane.showMessageDialog(null, "Urun Kaydedildi...", "", JOptionPane.WARNING_MESSAGE);
           
            //Stok kayiti
            try {
                lastid = DB.getLastId("TabloStok_Kayıtları") + 1;
            } catch (Exception d) {
                lastid = 0;
            }
                DB.executeSQL("Insert into TabloStok_Kayıtları VALUES(" + lastid + ",'" + formatter.format(date) + "','" + Secilen_Esya + "','"+Ad+" adında urunden"+Adet+" adet eklendi.'," + Alis_fiyat + ")", "BBB");
                update_id_numbers(TableName);
        }
        bool = false;
    }

    public void Sat(String Secilen_Esya) {
         TableName = "Tablo" + Secilen_Esya;
        
        //Son id ayari
        int lastid;
        try {
            lastid = DB.getLastId("TabloSatıs_Kayıtları") + 1;
        } catch (Exception d) {
            lastid = 0;
        }

        ArrayList<ArrayList<String>> queryList = null;
        boolean bool = false;
        
        //id için arraylist
        queryList = DB.executeQuerySQL("Select* From " + TableName, "", "id");
      
        for (int i = 0; i < queryList.size(); i++) {
            if (id == Integer.parseInt(queryList.get(i).get(0))) {//Sat için girilen id de urun varsa
                bool = true;
            }
        }
        if (bool) {//girilen id de urun varsa
            queryList.clear();
            queryList = DB.executeQuerySQL("Select* From " + TableName + " WHERE id=" + id, "", "Adet");
            String str = queryList.get(0).get(0);
            int Adet_sayisi = Integer.parseInt(str) - Adet;

            //Adet sayisina göre urun satis akis kontrolleri 
            if (Adet_sayisi < 0) {
                JOptionPane.showMessageDialog(null, "Girilen adet kadar urun yok ", "MyDB", JOptionPane.WARNING_MESSAGE);
            } else if (Adet_sayisi == 0) {
                DB.deleteRow(TableName, id);
                update_id_numbers(TableName);
                JOptionPane.showMessageDialog(null, "Ürün satıldı.", "MyDB", JOptionPane.WARNING_MESSAGE);
                DB.executeSQL("Insert into TabloSatıs_Kayıtları VALUES(" + lastid + ",'" + formatter.format(date) + "','" + Secilen_Esya + "','"+Ad+" adında urunden"+Adet+" adet satıldı.',"+  Satis_fiyat + ")", "BBB");
                update_id_numbers("TabloSatıs_Kayıtları");
            } else {
                DB.updateCell(TableName, id, "Adet", valueOf(Adet_sayisi));
                update_id_numbers(TableName);
                JOptionPane.showMessageDialog(null, "Ürün satıldı.", "MyDB", JOptionPane.WARNING_MESSAGE);

                DB.executeSQL("Insert into TabloSatıs_Kayıtları VALUES(" + lastid + ",'" + formatter.format(date) + "','" + Secilen_Esya  + "','"+Ad+" adında urunden"+Adet+" adet satıldı.',"+ Satis_fiyat + ")", "BBB");
                update_id_numbers("TabloSatıs_Kayıtları");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Bu id de ürün bulunamadı", "MyDB", JOptionPane.WARNING_MESSAGE);
        }

    }

    public boolean Arama(String Aranan_kelime) {
        boolean ayni_ad = false;
        ArrayList<ArrayList<String>> urun_ad_list = null;
        
        ArrayList<ArrayList<String>> hucrelist = null;
        String[] sutun_adlist = {"id", "AD", "Adet", "Satış_fiyatı", "Alış_fiyatı", "Tarih"};
        String[] yenilist = new String[6];
        int j = 1;
        Son_id = 1;//primary keydir id aynı olamaz
       
        while (j < 4) {
            //Her esya tablosu icin while
            String Table_Name = DB.getTableList().get(j);
            urun_ad_list = DB.executeQuerySQL("Select * From " + Table_Name, "DENEM", "AD");
           //O tabloda ad listesi icin for
            for (int i = 0; i < urun_ad_list.size(); i++) {
                    String ad=urun_ad_list.get(i).get(0).toUpperCase();
                int Index = ad.indexOf(Aranan_kelime);
                    
                  //String icinde aranan kelime varsa 
                if (Index != -1) {
                    int k = 0;
                    while (k < 6) {
                        hucrelist = DB.executeQuerySQL("Select * From " + Table_Name + " WHERE AD='" + urun_ad_list.get(i).get(0) + "'", "E", sutun_adlist[k]);
                        yenilist[k] = hucrelist.get(0).get(0);
                        k++;
                    }
                    
                    //ilk calistirma icin tablo olusumu 
//                    DB.createTable("Arama_sonuc");
//                    DB.addColumn("id", "Arama_sonuc");
//                    DB.addColumn("AD", "Arama_sonuc");
//                    DB.addColumn("Adet", "Arama_sonuc");
//                    DB.addColumn("Satıs_f", "Arama_sonuc");
//                    DB.addColumn("Alıs_f", "Arama_sonuc");
//                    DB.addColumn("Tarih", "Arama_sonuc"); 
                    
                    //Arama tablosuna ekleler
                    DB.executeSQL("Insert into Arama_sonuc VALUES('" + Son_id + "','" + yenilist[1] + "','" + yenilist[2] + "','" + yenilist[3] + "','" + yenilist[4] + "','" + yenilist[5] + "')", "XXX");
                    Son_id++;
                   
                }
            }

            j++;
        }
        
        return ayni_ad;

    }
    public void clearTable(String Tablename, int ilk_id) {//Arama tablosu hep bos kalması ıcın tablo temızleme
        for (int i = ilk_id; i <= Son_id; i++) {
            DB.deleteRow(Tablename, i);
        }

    }
    
    public void update_id_numbers(String Tablename) {//guncellenen tablolar icin idleri de gunceller
     ArrayList<ArrayList<String>> hucrelist=DB.executeQuerySQL("Select * from "+Tablename,"update id hatasi","id");
        for (int i = 0; i < hucrelist.size(); i++) {
         DB.updateCell(Tablename,Integer.parseInt(hucrelist.get(i).get(0)) , "id",Integer.toString(i));
            
        }
    }

    public void updateTable(String Table_Name) {//Tabloyu ekrana gosterir
        TableName = Table_Name;
        Object columnTitle[] = DB.getColumnList(TableName).toArray();
        Object rows[][] = DB.getObjectArray(DB.getDefaultTable(TableName));
        tablemodel.setDataVector(rows, columnTitle);

    }
    private void Dialog() {
        //Sat icin bir dialog
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(500, 300, 420, 100);
        dialog.setLayout(new FlowLayout());
        dialog.setModal(true);
        JLabel label = new JLabel("bu isimde  ürün satıştadır yine de yeni ürün eklemek istiyormusunuz ? ");

        JButton evet = new JButton();
        evet.setText("Evet");
        evet.setBounds(80, 60, 100, 30);
        evet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool2 =false;
                Ekle(Secilen_esya);
                dialog.setVisible(false);
            }

        });

        JButton hayir = new JButton();
        hayir.setText("Hayır");
        hayir.setBounds(200, 60, 100, 30);
        hayir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.add(label);
        dialog.add(evet);
        dialog.add(hayir);
        label.setVisible(true);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }
}
