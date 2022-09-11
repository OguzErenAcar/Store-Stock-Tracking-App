package projenesne;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Giris extends JFrame {

    public Giris() {
        //Giris Ekran
        Color clr = new Color(245, 176, 65);
        setTitle("Beyaz Esya");
        setSize(300, 300);
        setLocation(500, 200);
        setLayout(null);
        setVisible(true);

        JLabel labl = new JLabel();
        labl.setBackground(clr);

        getContentPane().setBackground(new Color(46, 46, 46));
        JLabel Ad = new JLabel("Adınız");
        Ad.setBounds(110, 10, 150, 100);

        Ad.setFont(new Font("", Font.ROMAN_BASELINE, 20));
        Ad.setForeground(Color.WHITE);
        JTextField TextBar = new JTextField();
        TextBar.setBounds(45, 100, 200, 30);

        //Buton ile diger frame e geciliyor
        JButton button = new JButton("Giris");
        button.setBounds(90, 180, 100, 30);
        button.setBackground(clr);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TextBar.getText().equals("") != true) {
                    Myframe mf = new Myframe();
                    setVisible(false);
                }

            }
        });

        getContentPane().add(button);
        getContentPane().add(Ad);
        getContentPane().add(TextBar);
        setResizable(false);

    }

}
