//Vaggelis kliaris   icsd11066
import java.awt.BorderLayout;               // prosthiki vivliothikiwn
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Hotel_Client extends JFrame implements ActionListener{
  
    final private JTabbedPane tabbedPane;                   // dilwsi twn tab
    private JTabbedPane tabbedPane2;
    private JPanel panel1,panel2,panel3,panel4,panel5;      // kathe tab kai ena panel + ena pou tha ta periexei ola auta
     
                                                            //dilwsei twn components
    private JTextField namefield;
    private JTextField surnamefield;
    private JTextField phonefield;
    private JTextField checkinfield;
    private JTextField checkoutfield;
    private JTextField snamefield;
    private JTextField ssurnamefield;
    private JTextField scheckinfield;
    private JTextField idfield;
    private JComboBox<String> cb;
    private JCheckBox breakfastBox;
   
    
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b7;
    private JButton b8;
    
                                                            
    
    static private Socket sock;                                 // dilwnoume to socket 
    static ObjectInputStream instream ;                         // dilwnoume streams gia ton dialogo me ton server
    static ObjectOutputStream outstream ;
    
    public Hotel_Client (){
        
        setTitle("Hotel Reservation" );
	setSize( 640, 330 );                                    // Stoixeia tou frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                                
        JPanel topPanel = new JPanel();                         //arxiko panel
	topPanel.setLayout( new BorderLayout() );               
	getContentPane().add(topPanel);
        
        reservationTab();                                       // kwdikas gia to tab pou kanei apothikeuei mia kratisi 
	searchTab();                                            // kwdikas gia to tab pou kanei anazitei mia kratisi
        deleteTab();
        
        
        tabbedPane = new JTabbedPane();                         // dimiourgia enos tabbedPane
	tabbedPane.addTab( "Make a Reservation", panel1 );      //prothetoume ta tria tab me ta antistoixa panel tous
	tabbedPane.addTab( "Search a Reservation", panel2 );
        tabbedPane.addTab( "Delete a Reservation", panel5);
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
     public void deleteTab(){                                    // gia to tab tis diagrafis mias ktatisis
        panel5 = new JPanel();                                  //dimiourgoume ena panel
        panel5.setLayout( null );                               // xrisimopoioume null diataksi
        
        JLabel label10 = new JLabel(" Reservation ID : " );
	label10.setBounds( 20, 15, 100, 20 );
	panel5.add( label10 );
	idfield = new JTextField();
	idfield.setBounds( 170, 15, 100, 20 );
	panel5.add(idfield);
        
        b7 = new JButton("Delete");  
        b7.setBounds(220, 120, 80, 30);
        panel5.add(b7);
        b8= new JButton("Cancel");  
        b8.setBounds(340, 120, 80, 30);
        panel5.add(b8);
        
        b7.addActionListener(this);                             // vazoume energeies sta koumpia save kai cancel
        b8.addActionListener(this);
        
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
       
        Hotel_Client mainFrame = new Hotel_Client();  // dimiourgoume to frame tis efarmogis
        mainFrame.setVisible( true );                           // to thetoume emfanisimo
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // to programma kleinei an patisoume [x]
    }

    @Override
    public void actionPerformed(ActionEvent ae) {               // gia tis energeies twn koumpiwn
        Object source = ae.getSource();  
        boolean error;
        
        if(source==b1){                                         // an patithei to Save tou tab me tin apothikeusi mias kratisis
            
            
            String name,surname,tel,checkin,checkout;
            String roomtype;
            String breakfast;
       
            name=namefield.getText();                           // apothikeuoume tis eisodous pou edwse o xristis
            surname=surnamefield.getText();
            tel=phonefield.getText();
            checkin=checkinfield.getText();
            checkout=checkoutfield.getText();                   // elegxoume an einai ola ta pedia simpliromena
            if(!("".equals(namefield.getText()) || "".equals(surnamefield.getText()) || "".equals(phonefield.getText()) 
                    && "".equals(checkinfield.getText()) || "".equals(checkoutfield.getText()))){
                roomtype=Arrays.toString(cb.getSelectedObjects());
                if(breakfastBox.isSelected()==true){breakfast="With Breakfast";}
                else{breakfast="No Breakafast";}
                                                                // dimiourgoume ena antikeimeno kratisis
                Reservation reserv=new Reservation(name,surname,tel,checkin,checkout,roomtype,breakfast);
                error=reserv.getError_flag();                   // an den iparksei kapoio provlima kata tin dimiourgia tou antikeimenou sunexixoume
                if(error==false){                               // me tin sindesi me ton server
                    try {
                        sock = new Socket ("localhost",8080);   // kanoume tin sindesi

                        outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                        instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn

                        outstream.writeObject(new Request("START"));
                        outstream.flush();                                          // stelnoume minima start ston server

                        System.out.println(instream.readObject());                  // ektiponoume tin apantisi tou server

                        outstream.writeObject(new Request("INSERT",reserv));        //stelnoume insert kai to antikeimeno  
                        outstream.flush();
                        Request req =(Request)instream.readObject();                // diavazoume tin apantisi tou server
                        if(req.get_Reserv().getId()>0 && req.get_Reserv().getPrice()>0){
                            JOptionPane.showMessageDialog(null, "To id tis kratisis einai "+req.get_Reserv().getId()+" kai i timi tis kratisis einai  "
                                    +req.get_Reserv().getPrice()+" eurw","Info",    // emfanizoume me joptionpane to id kai tin timi tis kratisis
                                JOptionPane.INFORMATION_MESSAGE);
                            
                            String res =(String) instream.readObject();             // kai diavazoume ena akoma minima tou server
                            if(res.equals("OK")){                                   // an einai ok  
                                System.out.println(res);                            // to emfanizoume
                                outstream.writeObject("END");                       // kai stelnoume end        
                                sock.close();                                       // kai kleinoume tin sindesi
                            }
                        }

                    } catch (IOException | ClassNotFoundException ex) {
                        try {
                            sock.close();                                           // an prokipsei kapoio lathos trmatizoume tin sindesi
                        } catch (IOException ex1) {
                            Logger.getLogger(Hotel_Client.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                }else{                                                              // an i imerominia den einai swsti emfanizoume minima lathous
                    JOptionPane.showMessageDialog(null,"Lathos Eisagwgi Imerominias", "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }else{                                                                  // an ta kena den einai ola simplirwmena emfanizoume minima
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(source==b2 || source ==b4 || source==b6 ){            // an patithei to koumpi Cancel se opoiodipote Tab to programma kleinei
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if(source==b3){                                         // an patithei to koumpei Search sto tab anazitisis mias kratisis mesw check-in
           
            String searchCheckin;                               // apothikeuoume to checkin anazitisis pou edwse ws eisodo o xrisis
            searchCheckin=scheckinfield.getText();
            
            if(!(scheckinfield.getText().equals(""))){
                try {                                           // i sindesi kai o dialogos ginetai opws pio panw stin apothikeusi kratisis
                    sock = new Socket ("localhost",8080);

                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                    instream = new ObjectInputStream(sock.getInputStream()) ;

                    outstream.writeObject(new Request("START"));
                    outstream.flush();

                    System.out.println(instream.readObject());
                                                                // metatrepoume to string tis imerominias anazitis se morfi date
                    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                    Date checkin = null;
                    try {
                        checkin = df1.parse(searchCheckin);
                    } catch (ParseException ex) {
                        Logger.getLogger(Hotel_Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    outstream.writeObject(new Request("SEARCH",checkin));
                    outstream.flush();                          // stelnoume search kai tin imerominia anazitisis
                   
                    new DisplayTable((ArrayList)instream.readObject());
                    String res =(String) instream.readObject(); // tin apantisi me ta apotelesmata ta stelnoume sin displayTable
                        if(res.equals("OK")){
                            System.out.println(res);
                            outstream.writeObject("END");
                            sock.close();
                        }
                } catch (IOException | ClassNotFoundException ex) {
                    try {
                        sock.close();                           // an egerthei kapoia ekseresi kleinoume tin sindesi
                    } catch (IOException ex1) {
                        Logger.getLogger(Hotel_Client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(source==b5){                                 // an epileksei to koumpi Search apo to tab anazitisis mias kratisis me vasi to onom/no
                                  
            String searchName,searchSurname;
            searchName=snamefield.getText();            // apothikeuoume to onom/no pou edwse ws kritirio anazitisis o xristis
            searchSurname=ssurnamefield.getText();
            if(!("".equals(searchName) || "".equals(searchSurname))) {
                try {
                    
                    sock = new Socket ("localhost",8080);
                                                        // kanoume tin sindesi opws kai pio panw me tin apothikeusi tis kratisis
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                    instream = new ObjectInputStream(sock.getInputStream()) ;

                    outstream.writeObject(new Request("START"));
                    outstream.flush();
                                
                    System.out.println(instream.readObject());
                                                        // stelnoume searck kai to onoma kai to epitheto tis anazitisis
                    outstream.writeObject(new Request("SEARCH",searchName,searchSurname));
                    outstream.flush();
                                                        // ta apotelesmata ta stelnoume stin displayTable
                    new DisplayTable((ArrayList)instream.readObject());
                    String res =(String) instream.readObject();
                        if(res.equals("OK")){           // an to meinima pou tha mas steilei meta o server einai ok
                            System.out.println(res);    // to mefanizoume
                            outstream.writeObject("END");   // stelnoume end kai 
                            sock.close();               // kleinoume tin sindesi
                        }
                } catch (IOException | ClassNotFoundException ex) {
                    try {
                        sock.close();                   // an egerthei kapoia ekseresi kleinoume tin sindesi
                    } catch (IOException ex1) {
                        Logger.getLogger(Hotel_Client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            
            }else{
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }   
        if(source==b7){                         // an patithei to koumpi tis diagrafis
            String deletedid;       
            deletedid=idfield.getText();
            if(!("".equals(deletedid))){        // vevewnomaste pws to pedio me to id einai simpliromeno
                int deleteId =Integer.parseInt(deletedid);
                try {                           // kanoume tin sindesi kai ton dialogo opws pio panw
                    sock = new Socket ("localhost",8080);
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                    instream = new ObjectInputStream(sock.getInputStream()) ;

                    outstream.writeObject(new Request("START"));
                    outstream.flush();
                    System.out.println(instream.readObject());
                                                    // stelnoume delete kai to id pou einai sto pedio
                    outstream.writeObject(new Request("DELETE",deleteId));
                    outstream.flush();
                                                    // emfanbizoume to minima pou mas esteile o server
                    JOptionPane.showMessageDialog(null,instream.readObject(), "Delete Info", JOptionPane.ERROR_MESSAGE);
                    String res =(String) instream.readObject();
                        if(res.equals("OK")){
                            System.out.println(res);
                            outstream.writeObject("END");
                            outstream.flush();
                            sock.close();
                        }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Hotel_Client.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }   
}
