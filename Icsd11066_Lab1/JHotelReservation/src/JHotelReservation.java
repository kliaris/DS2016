//icsd11066 Vaggelis Kliaris
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JHotelReservation extends JFrame implements ActionListener{
    final private JTabbedPane tabbedPane;           
    private JTabbedPane tabbedPane2;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
                                                            //dilwsei twn components
    private JTextField namefield;
    private JTextField surnamefield;
    private JTextField phonefield;
    private JTextField checkinfield;
    private JTextField checkoutfield;
    private JTextField snamefield;
    private JTextField ssurnamefield;
    private JTextField scheckinfield;
    private JTextField scheckoutfield;
    private JComboBox<String> cb;
    private JCheckBox breakfastBox;
    
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
                                                            
    ArrayList<JReservation> list=new ArrayList<JReservation>(); // dimiourgia listas gia tin apothikeusi twn kratisewn kata tin ektelesi tou programmatos
    ArrayList<JReservation> reserv;                             // dimiourgia listas gia tin apothikeusi twn ktatisewn pou anazitei o xristis
    ObjectOutputStream out;
    ObjectInputStream in = null;
    public JHotelReservation(){
        setTitle("Hotel Reservation" );
	setSize( 640, 330 );                                    // Stoixeia tou frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                                
        JPanel topPanel = new JPanel();                         //arxiko panel
	topPanel.setLayout( new BorderLayout() );               
	getContentPane().add(topPanel);
        
        reservationTab();                                       // kwdikas gia to tab pou kanei apothikeuei mia kratisi 
	searchTab();                                            // kwdikas gia to tab pou kanei anazitei mia kratisi
        
        
        
        tabbedPane = new JTabbedPane();                         // dimiourgia enos tabbedPane
	tabbedPane.addTab( "Make a Reservation", panel1 );      //prothetoume ta dio tab me ta antistoixa panel tous
	tabbedPane.addTab( "Search a Reservation", panel2 );
	topPanel.add( tabbedPane, BorderLayout.CENTER );        // prosthetoume to rabbedPane sto arxiko panel
    }
    public void reservationTab(){                               // gia to tab apothikeusis mias kratisis
        panel1 = new JPanel();                                  // dimiourgia enos panel
	panel1.setLayout(null);                                 // den vazoume kapoia diataksi
                                                                //epeita bazoume components stin katallili thesi sto panel
        JLabel label1 = new JLabel(" Name: " );
        label1.setBounds( 20, 15, 80, 20 );
	panel1.add( label1 );
        namefield = new JTextField();
	namefield.setBounds( 90, 15, 100, 20 );
	panel1.add(namefield);

	JLabel label2 = new JLabel( " Surname :" );
	label2.setBounds( 220, 15, 80, 20 );
	panel1.add( label2 );
	surnamefield = new JTextField();
	surnamefield.setBounds( 290, 15, 100, 20 );
	panel1.add( surnamefield);
                
        JLabel label3 = new JLabel(" Phone: " );
	label3.setBounds( 410, 15, 80, 20 );
	panel1.add(label3);
	phonefield = new JTextField();
	phonefield.setBounds( 490, 15, 100, 20 );
	panel1.add(phonefield);
                 
        JLabel label4 = new JLabel(" Check-in (HH/MM/YYYY): " );
        label4.setBounds(20,55,180,20);
        panel1.add(label4);        
        checkinfield = new JTextField();
	checkinfield.setBounds( 290, 55, 100, 20 );
	panel1.add(checkinfield);
                
        JLabel label5 = new JLabel(" Check-out (HH/MM/YYYY): " );
        label5.setBounds(20,95,250,20);
        panel1.add(label5);       
        checkoutfield = new JTextField();
	checkoutfield.setBounds( 290, 95, 100, 20 );
	panel1.add(checkoutfield);
                
        JLabel label6 = new JLabel(" Room type :" );
        label6.setBounds(20,135,250,20);
        panel1.add(label6);          
        String[] choices = { "Single room","Double room", "Triple room"};
        cb = new JComboBox<>(choices);                          // gia tin apothikeusi tou tupou dwmatiou xreisimopoieitai JCombobox 
        cb.setBounds(150, 135, 120, 20);                        // me tis epiloges tou pinaka choices
        cb.setVisible(true);
        panel1.add(cb);
                
                
        breakfastBox = new JCheckBox("Breakfast");              // gia tin apothikeusi tou prwinou xreisimopoieitai JCheckBox 
        breakfastBox.setHorizontalTextPosition(SwingConstants.LEFT);    // To "breakfast" mpainei aristera apo to koutaki
        breakfastBox.setSelected(true);                         // vazoume arxiki katastasi tou koutiou ws chekarismena
        breakfastBox.setBounds(290,135, 100, 20);
        panel1.add(breakfastBox);
    
        b1 = new JButton("Save");  
        b1.setBounds(220, 200, 80, 30);
        panel1.add(b1);
        b2 = new JButton("Cancel");  
        b2.setBounds(340, 200, 80, 30);
        panel1.add(b2);
                                                                
        b1.addActionListener(this);                             // vazoume energeies sta koumpia Save kai Cancel
        b2.addActionListener(this);
    }
    
    public void searchTab(){                                    // gia to tab tis anazitisis mias ktatisis
        panel2 = new JPanel();                                  //dimiourgoume ena panel
        panel2.setLayout( new BorderLayout() );                 // xrisimopoioume BorderLayout diataksi
        
        byNameTab();                                            //Sto tab tis anazitisis emfanizontai dio akoma tab ta apoia aforoun stin epilogi
        byCheckinTab();                                         // me ton opoio thes na kaneis tis anazitisi
        
        tabbedPane2 = new JTabbedPane();                        //dimiourgeia neou JTabbedPane
	tabbedPane2.addTab( "By name", panel3 );                // prosthiki twn dio tab kai twn panel tous
	tabbedPane2.addTab( "By check in", panel4 );
        panel2.add( tabbedPane2, BorderLayout.CENTER );
   
        
    }
    
    public void byCheckinTab(){                                 // gia to tab anazitisis mia kratisis me vasi to Check-in
        
        panel4 = new JPanel();                                  //dimiourgia panel
        panel4.setLayout(null);                                 // den xrisimopoioume kapoia diataksi sto panel
                                                                // kai vazoume ta components pou xreiazomaste sto panel
        JLabel label9 = new JLabel(" Check-in (HH/MM/YYYY): " );
        label9.setBounds(20,15,180,20);
        panel4.add(label9);        
        scheckinfield = new JTextField();
	scheckinfield.setBounds( 290, 15, 100, 20 );
	panel4.add(scheckinfield);
        
        b3 = new JButton("Search");  
        b3.setBounds(220, 120, 80, 30);
        panel4.add(b3);
        b4 = new JButton("Cancel");  
        b4.setBounds(340, 120, 80, 30);
        panel4.add(b4);
        
        b3.addActionListener(this);                             // vazoume energeies sta koumpia save kai cancel
        b4.addActionListener(this);
    }
    
    public void byNameTab(){                                    // gia to tab anazitisis mia kratisis me vasi to Onom/no
        panel3 = new JPanel();                                  // dimiourgia neou panel
        panel3.setLayout(null);                                 //den xreisimopoietai kapoia diataksi
        
        JLabel label7 = new JLabel(" Name: " );
	label7.setBounds( 20, 15, 80, 20 );
	panel3.add( label7 );
	snamefield = new JTextField();
	snamefield.setBounds( 90, 15, 100, 20 );
	panel3.add(snamefield);

	JLabel label8 = new JLabel( " Surname :" );
	label8.setBounds( 220, 15, 80, 20 );
	panel3.add( label8 );
        ssurnamefield = new JTextField();
	ssurnamefield.setBounds( 290, 15, 100, 20 );
	panel3.add( ssurnamefield);
        
        b5 = new JButton("Search");  
        b5.setBounds(220, 120, 80, 30);
        panel3.add(b5);
        b6 = new JButton("Cancel");  
        b6.setBounds(340, 120, 80, 30);
        panel3.add(b6);
        
        b5.addActionListener(this);                             // vazoume energeies sta koumpia save kai cancel
        b6.addActionListener(this);
    }
    
    public static void main(String[] args) {
        JHotelReservation mainFrame = new JHotelReservation();  // dimiourgoume to frame tis efarmogis
	mainFrame.setVisible( true );                           // to thetoume emfanisimo
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // to programma kleinei an patisoume [x]
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {               // gia tis energeies twn koumpiwn
        Object source = ae.getSource();
        
        if(source==b1){                                         // an patithei to Save tou tab me tin apothikeusi mias kratisis
            
            String name,surname,tel,checkin,checkout;
            String roomtype;
            String breakfast;
            int cost;
                                                                // apothikeuoume tis eisodous pou edwse o xristis
            name=namefield.getText();
            surname=surnamefield.getText();
            tel=phonefield.getText();
            checkin=checkinfield.getText();
            checkout=checkoutfield.getText();
            roomtype=Arrays.toString(cb.getSelectedObjects());
            if(breakfastBox.isSelected()==true){breakfast="With Breakfast";}
            else{breakfast="No Breakafast";}
                                                                // dimiourgeitai antikeimeno kostos 
            CalculateCost aboutCost =new CalculateCost(checkin,checkout,roomtype,breakfast);
            cost=aboutCost.getCost();                           //ipologizoume to kostos diamonis
            if(cost>0){                                         // an to kostos einai thetiko noumero(diladi Check > in<Check-out)
                JReservation reserv=new JReservation(name,surname,tel,checkin,checkout,roomtype,breakfast,cost);
                list.add(reserv);                               // dimiourgeitai antikeimeno kratisi kai apothikeuetai sti lista
                try {                                           // kanoume wrapping ta streams gia to grapsimo antikeimenwn sto arxeio
                    out=new ObjectOutputStream(new FileOutputStream("reservations.txt"));
                    for(int i=0;i<list.size();i++){             // grafoume ta antikeimena tis listas sto arxeio
                        out.writeObject(list.get(i));
                    }
                    out.flush();
                    out.close();
                } catch (IOException ex) {                      // pianoume ola ta exceptions pou mporei na prokupsoun
                    Logger.getLogger(JHotelReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{                                              // an to kostos vgei arnitiko i miden emfanizetai minima mesw JOptionPane
                JOptionPane.showMessageDialog(null, "To Chech-out prepei na einai meta to Check-in", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(source==b2 || source ==b4 || source==b6){            // an patithei to koumpi Cancel se opoiodipote Tab to programma kleinei
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if(source==b3){                                         // an patithei to koumpei Search sto tab anazitisis mias kratisis mesw check-in
            JReservation res;
            String searchCheckin;                               // apothikeuoume to checkin anazitisis pou edwse ws eisodo o xrisis
            searchCheckin=scheckinfield.getText();
            try {                                               // kanoume wrapping ta streams gia na diavasoume antikeimena apo to arxeio 
                in = new ObjectInputStream(new FileInputStream("reservations.txt"));
                reserv=new ArrayList<>();                       // dimiourgia listas gia tin apothikeusi twn antikeimenwn prws emfanisi
                while((res=(JReservation) in.readObject())!=null){  // diavazoume antikeimeno-antikeimeno mexri to telos tou arxeiou
                    if(res.getCheckin().equals(searchCheckin)){;    // an vrethei antikeimeno kratisis me chec-in idio me auto pou edwse o xristis 
                        reserv.add(res);                            // ws kritirio anazitisisi apothikeuoume to antikeimeno sti lista
                    }
                }
            } catch(EOFException ex){                               // pianoume tis eksereseis pou mporei n aprokupsoun 
                if(reserv.size()>0){                                // otan ftasoume sto telos tou arxeiou kai i lista periexei toulaxiston    
                    Table t=new Table(reserv);                      // dimiourgoume antikeino table me parametro tin lista twn apotelesmatwn
                }
                System.out.println("End of file!"); 
            }catch(InvalidClassException ex){
                System.out.println("Invalid Class"); 
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(JHotelReservation.class.getName()).log(Level.SEVERE, null, ex);
            }   catch (IOException ex) {
                    Logger.getLogger(JHotelReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(source==b5){                                 // an epileksei to koumpi Search apo to tab anazitisis mias kratisis me vasi to onom/no
            JReservation res;                           
            String searchName,searchSurname;
            searchName=snamefield.getText();            // apothikeuoume to onom/no pou edwse ws kritirio anazitisis o xristis
            searchSurname=ssurnamefield.getText();
            try {                                       // kanoume wrapping ta streams gia na diavasoume antikeimena apo to arxeio
                in = new ObjectInputStream(new FileInputStream("reservations.txt"));    
                reserv=new ArrayList<>();               // dimiourgoume lista gia tin apothikeusi twn antikeimenwn pros anazitisi
                while((res=(JReservation) in.readObject())!=null){  // diavazoume antikeimeno-antikeimeno mexri na ftasoume sto telos tou arxeio
                    if(res.getName().equals(searchName) && res.getSurname().equals(searchSurname)){;
                        reserv.add(res);                // an vrethei antikeimeno me onom/no idio me auto pou edwse o xristis ws kritirio anazitisis
                    }                                   // to apothikeuoume sti lista
                }
            } catch(EOFException ex){                   // otan ftasoume sto telos tou arxeio
                if(reserv.size()>0){                    // an i lista periexei toulaxiston mia ktatisi
                    Table t=new Table(reserv);          //dimiourgoume antikeimeno tupou table me pararmetro tin lista
                }
                System.out.println("End of file!"); 
            }catch(InvalidClassException ex){
                System.out.println("Invalid Class"); 
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(JHotelReservation.class.getName()).log(Level.SEVERE, null, ex);
            }   catch (IOException ex) {
                    Logger.getLogger(JHotelReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
