package ipcalc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Okno extends JFrame implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JButton JB1, JB2, JB3, JBSzukaj;
  Subnet subnet = new Subnet();

  JMenuBar MenuBar;

  JMenu menuPlik, menuInfo;
  JMenuItem mOtworz, mZapisz, mInfoVer;

  private Font Duza = new Font(Font.SERIF, Font.BOLD, 20);
  private Font Srednia = new Font(Font.SERIF, Font.PLAIN, 16);
  JPanel Panel, PanelCen, PanelCenC, PanelCenL, PanelNor, PanelNorL, PanelNorP, PanelSou;
  JTextArea text1, text2;
  JScrollPane JS1, JS2;
  JButton B1, B2, B3, B4, B5;
  JTextField T1, T2;
  JLabel Opis, Opis2, Opis3, Opis4;

  public Okno() {
    setSize(1200, 800);
    super.setTitle("IP Mapper");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    MenuBar = new JMenuBar();
    setJMenuBar(MenuBar);

    menuPlik = new JMenu("File");
    menuPlik.setFont(Srednia);
    menuInfo = new JMenu("Info");
    menuInfo.setFont(Srednia);

    mOtworz = new JMenuItem("Open");
    mOtworz.setFont(Srednia);
    mOtworz.addActionListener(this);
    mZapisz = new JMenuItem("Save");
    mZapisz.setFont(Srednia);
    mZapisz.addActionListener(this);
    
    mInfoVer = new JMenuItem("Ver 1.0");

    MenuBar.add(menuPlik);
    MenuBar.add(menuInfo);
    menuPlik.add(mOtworz);
    menuPlik.add(mZapisz);
    menuInfo.add(mInfoVer);

    Panel = new JPanel(new BorderLayout());
    //	JPanel PanelCen = new JPanel(new GridLayout(1,2,10,10));
    PanelCen = new JPanel(new BorderLayout());
    PanelCenL = new JPanel(new BorderLayout());
    PanelCenC = new JPanel(new BorderLayout());
    //PanelNor = new JPanel(new GridLayout(0,2,10,10));
    PanelNor = new JPanel(new BorderLayout());

    PanelNorL = new JPanel(new GridLayout(3, 0, 0, 0));
    PanelNorP = new JPanel(new GridLayout(3, 0, 0, 0));
    PanelSou = new JPanel(new GridLayout(1, 0, 20, 50));


    text1 = new JTextArea();
    text1.setFont(Srednia);
    JS1 = new JScrollPane(text1);


    text2 = new JTextArea();
    text2.setFont(Srednia);
    JS2 = new JScrollPane(text2);

    B1 = new JButton();
    B1.setFont(Duza);
    //B1.addActionListener(this);

    B2 = new JButton("Szukaj w polu Opis");
    B2.addActionListener(this);
    B2.setFont(Duza);

    B3 = new JButton("Czysc pola");
    B3.setFont(Duza);
    B3.addActionListener(this);

    B4 = new JButton("Szukaj w pliku");
    B4.setFont(Duza);
    B4.addActionListener(this);

    B5 = new JButton();
    B5.setFont(Duza);
    //B5.addActionListener(this);

    T1 = new JTextField();
    T1.setFont(Duza);
    T1.addActionListener(this);

    T2 = new JTextField();
    T2.setFont(Duza);

    //Panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    JLabel Opis = new JLabel("Wprowadz pojedyncze IP:");
    Opis.setFont(Duza);

    JLabel Opis2 = new JLabel("Wprowadz liste IP:");
    Opis2.setFont(Duza);

    JLabel Opis3 = new JLabel("Opis IP"
        + "                                        "
        + "                                         "
        + "                                        "
        + "                                        "
        + "           ");
    Opis3.setFont(Duza);

    JLabel Opis4 = new JLabel("Opis listy IP:"
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "   ");
    Opis4.setFont(Duza);


    PanelNorL.add(Opis);
    PanelNorL.add(T1);
    //PanelNorL.add(Opis2);
    PanelNor.add(PanelNorL, BorderLayout.CENTER);

    PanelNorP.add(Opis3);
    PanelNorP.add(T2);
    //PanelNorP.add(Opis4);
    PanelNor.add(PanelNorP, BorderLayout.EAST);

    Panel.add(PanelNor, BorderLayout.NORTH);
    Panel.add(PanelCen, BorderLayout.CENTER);

    PanelCenL.add(Opis4, BorderLayout.NORTH);
    PanelCenL.add(JS2, BorderLayout.CENTER);
    PanelCen.add(PanelCenL, BorderLayout.EAST);

    PanelCenC.add(Opis2, BorderLayout.NORTH);
    PanelCenC.add(JS1, BorderLayout.CENTER);
    PanelCen.add(PanelCenC, BorderLayout.CENTER);

    PanelSou.add(B1);
    PanelSou.add(B2);
    PanelSou.add(B3);
    PanelSou.add(B4);
    PanelSou.add(B5);
    Panel.add(PanelSou, BorderLayout.SOUTH);
    add(Panel);


  }


  public void actionPerformed(ActionEvent e) {

    Object source = e.getSource();
    if (source == mOtworz) { //Wczytuje plik do tabeli po lewej
      JFileChooser fc = new JFileChooser();
      if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        File plik = fc.getSelectedFile();
        //JOptionPane.showMessageDialog(null, "Wybrany plik to:" +plik.getAbsolutePath());
        try {
          Scanner skaner = new Scanner(plik);
          while (skaner.hasNext()) {
            text1.append(skaner.nextLine() + "\n");
          }
          skaner.close();
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
      }
    } else if (source == mZapisz) { //Zapisuje wyniki z textarea po prawej
      JFileChooser fc = new JFileChooser();
      if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        File plik = fc.getSelectedFile();
        //JOptionPane.showMessageDialog(null, "Wybrany plik to:"+ plik);
        try {
          PrintWriter pw = new PrintWriter(plik);
          Scanner skaner = new Scanner(text2.getText());
          while (skaner.hasNext()) {
            pw.println(skaner.nextLine() + "\n");
          }
          pw.close();
          skaner.close();
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
      }
    } else if (source == B1) {
      //System.out.println("NACISNIETO B1");
    } else if (source == B2) {
      //System.out.println("Szukaj NOWE B2");
      String tekst = text2.getText();
      tekst.trim();
      if (tekst.length() != 0) {
        text2.append("\n");
        tekst = text2.getText();
        //System.out.println("Tekst>"+tekst+"<");
      }
      //System.out.println("Szukaj NOWE");
      //text2.append("\n");

      //text2.append("\n");
      //String tekst = text2.getText();
      int[] kolejka = new int[1000];
      String szukane = "\n";
      int k = 1;
      int index = 0;
      int startIndex = 0;
      int ind = 0;
      while ((index = tekst.indexOf(szukane, startIndex)) != -1) {
        startIndex = index + szukane.length();
        kolejka[k] = index;
        k++;
        //text2.append(test+"\n");
      }
      szukane = T1.getText();
      index = tekst.indexOf(szukane);
      //System.out.println("W0");
      if (index != -1) {
        //System.out.println("W1");
        while (ind < k) {
          //System.out.println("W2");
          if (kolejka[ind] > index) {
            T2.setText(tekst.substring(kolejka[ind - 1], kolejka[ind]));
            //System.out.println("ind="+ind+"pocz= "+kolejka[ind-1]+"kon= "+kolejka[ind]);
            break;
          }
          ind++;

        }
      }


      System.out.println(index + " ");
      //JOptionPane.showMessageDialog(null, "Zanleziono" +wystapienia+ "index" + index);

    } else if (source == B3) {
      text1.setText("");
      text2.setText("");
      T1.setText("");
      T2.setText("");
    } else if (source == B4) {

//      System.out.println("Szukaj NOWE");
      String mainTemp;
      mainTemp = text1.getText();
      String tekst = text1.getText();
      tekst = tekst.trim();
//      System.out.println("Tekst>" + tekst + "<");
      if (tekst.length() != 0) {
        text1.append("\n");
        tekst = text1.getText();
      }
//      System.out.println("Tekst>" + tekst + "<");

      String znakKoncaLini = "\n";

      int index = 0;
      int startIndex = 0;
      int ind = 0;
      String szukany_IP = "";

      while ((index = tekst.indexOf(znakKoncaLini, startIndex)) != -1) {
        startIndex = index + znakKoncaLini.length();
        if (tekst.substring(ind, index).charAt(0) == '\n') {
          ind++;
        }
        szukany_IP = tekst.substring(ind, index);
//        System.out.println("WIERSZ: " + szukany_IP + ' ');
        text2.append("IP: " + szukany_IP + ' ');
        boolean anyFindings = false;
        if (subnet.validateIP(szukany_IP)) {
          System.out.println("Validated:"+szukany_IP);
          anyFindings = false;
          Scanner skaner;
          StringJoiner foundIPlist = new StringJoiner(" ", "Found in (IP):", ". ");
          System.out.println("foundIPlist len: " + foundIPlist.length());
          try {
            skaner = new Scanner(new File("listaIP.txt"));
            while (skaner.hasNext()) { // nastÄ™pna linia
//              System.out.println("W3");
              String aktualnyWiersz = skaner.nextLine();             //aktualny wiersz z pliku
              if (aktualnyWiersz.contains(szukany_IP)) {
                foundIPlist.add(aktualnyWiersz.replace(szukany_IP, ""));
                anyFindings = true;
                //break;//przerywa poszukiwanie w pliku konretnego ip i przechodzi do nastepnego POMIJAJÄ„C ZDUBLOWANE INFORMACJE o IP
              }
            }
            if (foundIPlist.length() != 16) {
              text2.append(foundIPlist.toString());
            }
            skaner.close();
          } catch (FileNotFoundException e1) {
            text2.append("!!! nie znaleziono pliku: listaIP.txt");
          }
          StringJoiner excludedFromIPlist = new StringJoiner(" ", "Excluded from (IP):", ". ");
//          System.out.println("foundIPlist len: " + excludedFromIPlist.length());
          try {
            skaner = new Scanner(new File("listaExIP.txt"));
            while (skaner.hasNext()) { // nastÄ™pna linia
//              System.out.println("W3");
              String aktualnyWiersz = skaner.nextLine();             //aktualny wiersz z pliku listaExIP
              if (aktualnyWiersz.contains(szukany_IP)) {
                excludedFromIPlist.add(aktualnyWiersz.replace(szukany_IP, ""));
                anyFindings = true;
              }
            }
            if (excludedFromIPlist.length() != 21) {
              text2.append(excludedFromIPlist.toString());
            }
            skaner.close();
          } catch (FileNotFoundException e1) {
            text2.append("!!! nie znaleziono pliku: listaExIP.txt");
          }

          StringJoiner foundSubnetlist = new StringJoiner(" ", "Found in (Subnet):", ". ");
          System.out.println("foundSubnetlist len: " + foundSubnetlist.length());
          try {
            skaner = new Scanner(new File("listaSubnet.txt"));
            while (skaner.hasNext()) { // nastÄ™pna linia
              String aktualnyWiersz = skaner.nextLine();             //aktualny wiersz z pliku
              if (subnet.validateIP(szukany_IP)) {
                if (subnet.belongsToSubnetInRow(aktualnyWiersz, szukany_IP)) {
                  foundSubnetlist.add(aktualnyWiersz);
                  anyFindings = true;
                  //break;//przerywa poszukiwanie w pliku konretnego ip i przechodzi do nastepnego POMIJAJÄ„C ZDUBLOWANE INFORMACJE o IP
                }
              }
            }
            if (foundSubnetlist.length() != 20) {
              text2.append(foundSubnetlist.toString());
            }
            skaner.close();
          } catch (FileNotFoundException e1) {
            text2.append("!!! nie znaleziono pliku: listaSubnet.txt");
          }

          StringJoiner foundExSubnetlist = new StringJoiner(" ", "Excluded from (Subnet):", ". ");
          System.out.println("foundExSubnetlist len: " + foundExSubnetlist.length());
          try {
            skaner = new Scanner(new File("listaExSubnet.txt"));
            while (skaner.hasNext()) { // nastÄ™pna linia
              System.out.println("W3");
              String aktualnyWiersz = skaner.nextLine();             //aktualny wiersz z pliku
              System.out.println("wczytano wiersz");
              if (subnet.validateIP(szukany_IP)) {
//                System.out.println("validate IP");
                if (subnet.belongsToSubnetInRow(aktualnyWiersz, szukany_IP)) {
//                  System.out.println("belongs");
                	foundExSubnetlist.add(aktualnyWiersz);
                  anyFindings = true;
                }
              }
            }
            if (foundExSubnetlist.length() != 25) {
              text2.append(foundExSubnetlist.toString());
            }
            skaner.close();
          } catch (FileNotFoundException e1) {
            text2.append("!!! nie znaleziono pliku: listaExSubnet.txt");
          }

        } else {
          text2.append("Bledny fomat IP");
          anyFindings = true;
        }
        ind = index;
        if (!anyFindings) {
          text2.append(" Brak wynikow\n");
        } else {
          text2.append("\n");
        }
      }
      //JOptionPane.showMessageDialog(null, szukane + " wystapilo " +i+" razy: " +wystapienia);
      text1.setText(mainTemp);
    }
  }
}
