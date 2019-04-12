// icsd11066 Vaggelis Kliaris
import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class DisplayTable implements Serializable{
    DisplayTable(ArrayList<Reservation> reserv){          // o constractor dexetai ws parametro mia lista me ta antikeimena prws ektupwsi
        
        if(reserv.isEmpty()==false){
            JFrame frame = new JFrame();                // dimiourgoume frame

            Object [][]rowData=new Object[reserv.size()][9] ;   // dimourgoume disdiastato pinaka gia na mpoun oi idiotites kathe kratisis
            for(int i=0;i<reserv.size();i++){                   // kathe grammi sto JTable einai kai mia kratisi
                rowData[i][0]=reserv.get(i).getId();
                rowData[i][1]=reserv.get(i).getName();
                rowData[i][2]=reserv.get(i).getSurname();
                rowData[i][3]=reserv.get(i).getTel();
                rowData[i][4]=reserv.get(i).getCheckin();
                rowData[i][5]=reserv.get(i).getCheckout();
                rowData[i][6]=reserv.get(i).getRoomtype();
                rowData[i][7]=reserv.get(i).getBreakfast();
                rowData[i][8]=reserv.get(i).getPrice();
            }                                                   // dimiourgoume ta onomata tis kathe stilis
            Object columnNames[] = { "ID","Name", "Surname", "Phone","Check-in","Check-out","Room type","Breakfast","Cost"};
            JTable table = new JTable(rowData, columnNames);    // Dimiourgoume to JTable


            JScrollPane scrollPane = new JScrollPane(table);    // Dimirgoume scrollPane sto opoio vazoume to table mas
            frame.add(scrollPane, BorderLayout.CENTER);         // vazoume sto scrollPane me to table pou exei mesa sto frame
            frame.setSize(700, 150);                        
            frame.setVisible(true);                             // thetoume megethos sto frame kai to kanoume emfanisimo

        }else{
            JOptionPane.showMessageDialog(null,"Den vrethikan apotelesmata", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
}
