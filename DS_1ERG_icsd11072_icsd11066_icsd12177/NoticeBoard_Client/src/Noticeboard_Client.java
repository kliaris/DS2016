// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;


public class Noticeboard_Client extends JFrame implements ActionListener{
    Member yourself=null;                           // stin metavliti yourself tha kratithei o xristis pou sindeetai kathe fora
    
    GridBagConstraints cs;
    static Noticeboard_Client mainFrame;            // dilwsi panel
    JPanel topPanel,westPanel,subjectPanel,noticePanel,buttonPanel;
    JPanel eastPanel;
    
    JLabel search_label,from_label,to_label;        // dilwsi labels kai textfield
    private JTextField fromField,toField,subject;
    
    ArrayList<Notice> resultsOfNotices;
    DefaultListModel listModel;                     //dilwsi listwn
    JList list;
    int index;
    boolean from_delete=false;
    ListSelectionEvent evt;
    JTextArea textArea;
    
    private JButton save_edit=null;                 // dilwsi twn button
    private JButton delete=null;
    private JButton edit=null;
    private JButton cancel_edit=null;
    private JButton reset=null;
    private JButton create_button=null;
    private JButton modify_button=null;
    private JButton read_button=null;
    private JButton save=null;
    private JButton cancel_create=null;
    private JButton cancel_search=null;
    private JButton search=null;
    private JButton close=null;
    private JButton read=null;
    private JButton cancel_read=null;
   
    static private Socket sock;                                 // dilwnoume to socket 
    static ObjectInputStream instream ;                         // dilwnoume streams gia ton dialogo me ton server
    static ObjectOutputStream outstream ;
    Noticeboard_Client(){ 
       
            setTitle("NoticeBoard" );
            setSize( 640, 335 );                                    // Stoixeia tou frame
            //setResizable(false);                                    // to frame den tha mporei na metavalei to megethos tou
            
 
            topPanel = new JPanel();                                //arxiko panel
            topPanel.setLayout(null);               
            getContentPane().add(topPanel);
            
            westPanel=new JPanel();
            eastPanel=new ImagePanel(new ImageIcon("C:\\Users\\Kliaris\\Documents\\NetBeansProjects\\NoticeBoard_Client\\src\\noticeboard.png").getImage()); 
                                                                    // dimiourgw 2 Panel
       
            westPanel.setBounds(0, 0, 150,310);                     // vazw to westPanel deksia tou topPanel
            eastPanel.setBounds(150,0,640,310);
            topPanel.add(westPanel);                                // vazw to eastPanel sto aristero meros tou topPanel
            topPanel.add(eastPanel);
                                                                    // thetw sto westPanel
            westPanel.setLayout( new GridLayout(3,1));              // Grid diataksi 3 grammwn se mia stili(kathe grammi kai ena koumpi)
            
          
                                                                    // vazw sta 3 arxika button ipogrammisi kai xrwma sto onoma tou
            create_button = new JButton("<html>"+ "<font color=\"#FF0000\"><u>Create a announcement</u></font>"+"<html>");
            modify_button = new JButton("<html>"+ "<font color=\"#FF0000\"><u>Modify or Delete a announcment</u></font>"+"<html>");
            read_button = new JButton("<html>"+ "<font color=\"#FF0000\"><u>Read a announcement</u></font>"+"<html>");
            create_button.setBackground(Color.LIGHT_GRAY);
            modify_button.setBackground(Color.LIGHT_GRAY);
            read_button.setBackground(Color.LIGHT_GRAY);
            westPanel.add(create_button);                           // ftiaxnw ta tria koumpia
            westPanel.add(modify_button);                           // ta opoia einai oi treis arxikes energies pou mporei na kanei
            westPanel.add(read_button);                             // o xristis
            
            create_button.addActionListener(this);                  // vazw sta koumpia mou na kanoun leitourgies
            modify_button.addActionListener(this);                  // otan patithoun
            read_button.addActionListener(this);
            
            //pack();
            setLocationRelativeTo(null);
            setVisible(true);
       
        
        
    }
    
    public static void main(String[] args) {
        mainFrame = new Noticeboard_Client();                       // dimiourgoume to frame tis efarmogis
        mainFrame.setVisible( true );                               // to thetoume emfanisimo
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // to programma kleinei an patisoume [x]
    
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        try{
            
            if(source==create_button || source==modify_button || source==read_button){  // an patithei to koumpi tis dimiourgias anakoinwsis i tis tropopohshs i tou diavasmatos
                create_button.setEnabled(false);
                modify_button.setEnabled(false);
                read_button.setEnabled(false);

                if(yourself==null){                                                     // an den exei oristei xristis sti metavliti yourself
                    JOptionPane optionPane = new JOptionPane("Are you a Member?",       // To sustima epikoinwnei me to xristi me minimata Joptionpane
                    JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);           // gia na dei an o xristis einai melos i oxi
                    JDialog dialog = optionPane.createDialog(mainFrame, "NoticeBoard Question");
                    dialog.setVisible(true);                                             
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    Object selectedValue = optionPane.getValue();
                    if(selectedValue!=null){                                            // dimiourgoume ena antikeimeno yourEntry kai stelnoume 
                        Entry yourEntry =new Entry(mainFrame,selectedValue);            // tin apantisi tou xristi stin erwtisi an einai idi melos 
                        yourself=yourEntry.getLogin();                                  // vazoume stin metavliti yourself to antikeimeno Member pou antistoixei
                    }                                                                   // ston xristi auton
                    
                }

                if(yourself.isConnected()==true){                           // otan tethei kai episima sindemenos o xristis                                         
                    if(source==create_button){                              // an patithei tou koumpi tis dimiourgias anakoinwseis
                        noticePanel=new JPanel();  
                        noticePanel.setBounds(20, 40, 400, 200);            // dimiourgoume katallilo panel gia tin katagrafi tis anakoinwsis
                        noticePanel.setLayout(new BorderLayout());
                        
                        textArea = new JTextArea(10,34);                    // dimiourgoume textarea pou tha grapsoume panw
                        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        textArea.setLineWrap(true);                         // kai to vazoume panw se scrollpane
                        textArea.setText("Type your notice ...");
                        noticePanel.add(scrollPane,BorderLayout.CENTER);
    
                        subject=new JTextField(100);                        // episis ena textfield gia ton orismo tou thematos
                        subject.setBounds(20, 10, 400, 20);
                        subject.setText("Subject Of Notice");
                       
                        save=new JButton("Save");                           // koumpi save gia tin apothikeusi
                        save.setBounds(90, 250, 120,30);
                        cancel_create=new JButton("Cancel");                // kai koumpi cancel gia tin akurwsi
                        cancel_create.setBounds(230, 250, 120, 30);
                        
                        eastPanel.add(subject);                             // ola auta ta vazoume panw sto eastPanel
                        eastPanel.add(save);
                        eastPanel.add(cancel_create);
                        eastPanel.add(noticePanel);
                        
                        repaint();
                        eastPanel.revalidate();                             // ananewnoume to panel
                        save.addActionListener(this);                       // kai vazoume energeies sta koumpia
                        cancel_create.addActionListener(this);
                        
                       
                    }
                    if(source==modify_button){                              // an patithei to koumpi tis epeksergasias
                        sock = new Socket ("localhost",8080);               
                        outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                        instream = new ObjectInputStream(sock.getInputStream()) ;                                                    // epikoinwnoume me to server kai stelnoume minima na psaksei
                        outstream.writeObject(new Request("FIND",yourself,null,null));  // na mas emfanisei ppoies anakoinwseis mporei na epeksergastei
                        outstream.flush();                                              // o xristis(youself)
                    
                        Request req=(Request)instream.readObject();                     // pernoume tin apantisi kai an auti einai founded
                        System.out.println("edw");
                        if(req.getMessage().equals("FOUNDED")){
                            System.out.println(req.getMessage());                       // tou leme ok kai kleinoume tis sindesi
                            outstream.writeObject(new Request("OK"));
                            outstream.flush();   
                        }
                        sock.close();
                        resultsOfNotices=req.getNoticeList();                           // pernoume tin lista me tis anakoinwseis
                        
                        subjectPanel=new JPanel();                                      // ftiaxnoume panel gia to subject
                        subjectPanel.setBounds(20,10,300,50);
                        subjectPanel.setLayout(new BorderLayout());
                        listModel=new DefaultListModel();  
                        for(int i=0;i<resultsOfNotices.size();i++){                            
                            listModel.addElement(resultsOfNotices.get(i).get_subject());
                        }                                                               // ftiaxnoume Jlist pou tha periexei ola ta subject
                        list = new JList(listModel);
                                                                                        // kai ta vazoume panw se ena scrollpane
                        JScrollPane scrollPane1 = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        subjectPanel.add(scrollPane1,BorderLayout.CENTER);
                      
                       
                        
                        noticePanel=new JPanel();                                       // kanoume panel gia tis anakoinwseis
                        noticePanel.setBounds(20, 70, 400, 170);
                        noticePanel.setLayout(new BorderLayout());  
                        textArea = new JTextArea(10,34);                        // kanoume textarea kai to vazoume panw se scrollpane
                        JScrollPane scrollPane2 = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        textArea.setLineWrap(true);                             // thetoume arxiko keimeno
                        textArea.setText(" ---- Select One Subject ----");
                        textArea.setEditable(false);                            // kai to thetoume na min ginetai epeksergasia
                        noticePanel.add(scrollPane2,BorderLayout.CENTER);       // (mexri na patithei to koumpi edit)
                        
                        edit=new JButton("Edit");                               // ftiaxnoume ta buttons
                        edit.setBounds(330, 10, 90, 25);
                        cancel_edit=new JButton("Cancel");
                        cancel_edit.setBounds(330, 35, 90, 25);
                        delete=new JButton("Delete");
                        delete.setBounds(160, 250, 120,30);
                        save_edit=new JButton("Save");
                        save_edit.setBounds(30, 250, 120, 30);
                        reset=new JButton("Cancel");
                        reset.setBounds(290, 250, 120, 30);
                        delete.setEnabled(false);                               // mexri na patithei to edit, to delete to save kai to reset
                        save_edit.setEnabled(false);                            // tha einai apenergopoihmena
                        reset.setEnabled(false);
                        if(listModel.getSize() <1){                             // an den iparxoun apotelesmata apo ton server 
                            textArea.setText(" ---- No Notices For You ----");  // tha emfanizetai mesa sto textarea katallilo minima 
                            edit.setEnabled(false);                             
                        }
                        
                        eastPanel.add(noticePanel);                             // ola ayta ta components ta vazoume sto eastPanel
                        eastPanel.add(subjectPanel);
                        eastPanel.add(delete);
                        eastPanel.add(edit);
                        eastPanel.add(save_edit);
                        eastPanel.add(reset);
                        eastPanel.add(cancel_edit);
                        
                        
                        repaint();
                        eastPanel.revalidate();
                        
                        save_edit.addActionListener(this);                      // thetoume energeies sta koumpia 
                        delete.addActionListener(this);
                        reset.addActionListener(this);
                        edit.addActionListener(this);
                        cancel_edit.addActionListener(this);                    // thetoume energeia kai sti lista
                        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        list.addListSelectionListener((ListSelectionEvent evt) -> {
                            index=list.getSelectedIndex();                      //epistrefei -1 otan den iparxei selection
                            if (listModel.getSize() > 0){
                                if(index>-1 && index< listModel.getSize()){     // otan epilexthei ena apo ta subject,sto textarea tha 
                                    textArea.setText(resultsOfNotices.get(index).get_notice()); // grafete i antistoixi anakoinwsi
                                }else{
                                    list.setSelectedIndex(0);
                                    textArea.setText(resultsOfNotices.get(0).get_notice());   
                                }
                            }else{
                                
                                textArea.setText(" No Notices For You ");
                            }   
                           
                        });
                    }
                    if(source==read_button){                        // an patisthei to koumpi gia anagnwsi anakoinwseis
                        noticePanel=new JPanel();                   // ftiaxnoume panel
                        noticePanel.setBounds(20, 30, 400, 140);
                        noticePanel.setLayout(null);
                        
                        search_label=new JLabel("Search notices "); // sto panel vazoume components gia tin eisagwgi tou eurous
                        search_label.setBounds(20, 20, 100, 20);    // twn imerominiwn pou theloume na psaksoume
                        from_label=new JLabel("from (HH/MM/YYYY) : ");
                        from_label.setBounds(120, 20, 120, 20);
                        to_label=new JLabel("to      (HH/MM/YYYY) : ");
                        to_label.setBounds(120, 60, 120, 20);
                        fromField=new JTextField(12);
                        fromField.setBounds(240, 20, 120, 20);
                        toField=new JTextField(12);
                        toField.setBounds(240,60,120,20);
                        search=new JButton("SEARCH");               // ftiaxnoume kai ta antistoixa buttons
                        search.setBounds(60, 90, 120, 30);
                        cancel_search=new JButton("CANCEL");
                        cancel_search.setBounds(200, 90, 120, 30);
                        
                        
                        noticePanel.add(search_label);              // ta vazoume panw sto noticePanel
                        noticePanel.add(from_label,2,1);
                        noticePanel.add(fromField);
                        noticePanel.add(to_label);
                        noticePanel.add(toField);
                        noticePanel.add(search);
                        noticePanel.add(cancel_search);
                        eastPanel.add(noticePanel);
                        
                        search.addActionListener(this);
                        cancel_search.addActionListener(this);
                        
                        repaint();
                        eastPanel.revalidate();
                    }
                    
                    
                }
                
            }
        }catch(NullPointerException NPE){
        
        } catch (IOException ex) {
            Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
        if(source==save){               // an patithei to koumpi save apo tin dimiourgia anakoinwsis
            String subj,notice;         
            
             
            Date published = new Date();    // apothikeuetai i imerominia dimosiopihsis
            subj=subject.getText();         // pernoume to subject kai tin anakoinwsi pou edwse o xristis
            notice=textArea.getText();
           
            if(!(subj.equals("")||notice.equals(""))){  // an ta exei simplirwsei
                Notice theNotice=new Notice(yourself,published,subj,notice);
                                                        // ftiaxnoume antikeimeno theNotice me ta stoixeia tou
                try {
                    sock = new Socket ("localhost",8080);     
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                    instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn
                    
                    outstream.writeObject(new Request("SAVE",theNotice));          
                    outstream.flush();                                          // stelnoume tin anakoinwsi sto server kai tou zitame na tin apothikeusi
                    
                    Request req=(Request)instream.readObject();
                    if(req.getMessage().equals("SAVED")){                       // an autos mas apantisei me saved tou stelnoume ok
                        System.out.println(req.getMessage());               
                        outstream.writeObject(new Request("OK"));
                        outstream.flush(); 
                    }
                    sock.close();                                               // kai termatizoume tin sindesi
                    eastPanel.remove(noticePanel);                              // afairoume ta panel kai ta buttons pou xrisimopoihsame
                    eastPanel.remove(subject);
                    eastPanel.remove(save);
                    eastPanel.remove(cancel_create);
                    create_button.setEnabled(true);
                    modify_button.setEnabled(true);
                    read_button.setEnabled(true);
                    repaint();
                } catch (IOException | ClassNotFoundException ex) {
                    try {
                        sock.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    
            }else{                                  // an den simplirwsei ta pedia emfanizetai katallilo minima
                JOptionPane.showMessageDialog(null,"Elleipis Stoixeia", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(source==cancel_create){                  // an patithei to cancel apo tin dimiourgeia anakoinwsis                          
            eastPanel.remove(noticePanel);
            eastPanel.remove(subject);              // afairoume ta panel kai ta buttons pou xrisimopoihsame
            eastPanel.remove(save);
            eastPanel.remove(cancel_create);
            create_button.setEnabled(true);         // kai energopoioume pali ta tria arxika buttons
            modify_button.setEnabled(true);
            read_button.setEnabled(true);
            repaint();
        }
        
        if(source==edit){                           // an patithei to koumpi edit apo tin tropopoihsh anakoinwnsis
            cancel_edit.setEnabled(false);          // thetoume to cancel apenergopoihmeno
            try {               
                sock = new Socket ("localhost",8080);
                outstream = new ObjectOutputStream(sock.getOutputStream()) ;// kanoume tin sindesioutstream 
                instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn
                
                
                outstream.writeObject(new Request("EDIT",resultsOfNotices.get(index)));
                outstream.flush();              // stelnoume ston server tin anakoinwsi kai ton proidopoioume oti tha tin epeksergastoume
                Request req=(Request)instream.readObject();//(tou zitame na tin kleidwsei diladi)
                if(req.getMessage().equals("LOCKED")){  // an mas apantisei oti i anakoinwsi einai idi kleidwmeni
                    JOptionPane.showMessageDialog(null,"Kapoios allos diavazei i epeksergazetai tin simiwsi auti", "Error",
                            JOptionPane.ERROR_MESSAGE); // tote emfanizetai katallilo minima
                    sock.close();                       // kai kleinei i sindesi
                    
                    eastPanel.remove(noticePanel);      // afairoume ta panel
                    eastPanel.remove(subjectPanel);
                    eastPanel.remove(save_edit);
                    eastPanel.remove(reset);
                    eastPanel.remove(delete);
                    eastPanel.remove(edit);
                    eastPanel.remove(cancel_edit);      // kai i efarmogi erxete stin arxiki tis katastasi
                    create_button.setEnabled(true);
                    modify_button.setEnabled(true);
                    read_button.setEnabled(true);
                    repaint();
                                                        // an o server mas apantisi waiting seimainei oti i anakoinwsi itan eleutheri
                }else if(req.getMessage().equals("WAITING")){   // kai pws tin kleidwse gia emas,opote perimenei na tou poume 
                    save_edit.setEnabled(true);                 // ti energeia tha kanoume panw stin anakoinwsi(epekergasia/diagrafi)
                    delete.setEnabled(true);
                    textArea.setEditable(true);
                    reset.setEnabled(true);
                    edit.setEnabled(false);
                    list.setEnabled(false);
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        if(source==save_edit){                                                  // an patithei to koumpi save apo tin tropopoihsh anakoinwnsis
            Member author=resultsOfNotices.get(index).get_author();             // pernoume ta nea stoixeia tis anakoinwsis
            Date published=resultsOfNotices.get(index).get_published();
            Date last_edit=new Date();                                          // pernoume tin imerominia tis epeksergasias
            String subject=resultsOfNotices.get(index).get_subject();
            String notice=textArea.getText();
            Notice saveChange =new Notice(author,published,last_edit,subject,notice);
            saveChange.set_ID(resultsOfNotices.get(index).get_ID());
            try {                                                               // stelnoume ston server oti tha epeksergastoume kai tou stelnoume
                    outstream.writeObject(new Request("CHANGE",saveChange));    // kai tin nea anakoinwsi,gia na antikatastisi tin palia
                    Request req=(Request)instream.readObject();
                                                                                // an mas apantisei pws apothikeutike
                    if(req.getMessage().equals("CHANGES SAVED")){ 
                        System.out.println(req.getMessage());                   // tou apantame ok kai kleinoume tin sindesi
                        outstream.writeObject(new Request("OK"));
                        outstream.flush(); 
                    }
                   
                sock.close();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            eastPanel.remove(noticePanel);                                      // kai fernoume tin efarmogi stin arxiki tou katastasi
            eastPanel.remove(subjectPanel);
            eastPanel.remove(save_edit);
            eastPanel.remove(reset);
            eastPanel.remove(delete);
            eastPanel.remove(edit);
            eastPanel.remove(cancel_edit);
            create_button.setEnabled(true);
            modify_button.setEnabled(true);
            read_button.setEnabled(true);
            repaint();
        }
        if(source==delete){                                                     // an patithei to koumpi tis diagrafis 
            
            try {                                                               // stelnoume ston server pws theloume na diagrapsoume 
                outstream.writeObject(new Request("DELETE",resultsOfNotices.get(index)));   // tin sigkekrimeni anakoinwsi
                Request req=(Request)instream.readObject();
                if(req.getMessage().equals("REMOVED")){                         // an mas apantisei pws diagraftike
                    System.out.println(req.getMessage());
                    outstream.writeObject(new Request("OK"));                   // tou stelnoume ok kai termatizoume tin sindesi
                    outstream.flush(); 
                }
                sock.close();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            resultsOfNotices.remove(index);
            listModel.remove(index);   
            
            
            eastPanel.remove(noticePanel);                                      // kai fernoume tin efarmogi stin arxiki katastasi tis
            eastPanel.remove(subjectPanel);
            eastPanel.remove(save_edit);
            eastPanel.remove(reset);
            eastPanel.remove(delete);
            eastPanel.remove(edit);
            eastPanel.remove(cancel_edit);
            create_button.setEnabled(true);
            modify_button.setEnabled(true);
            read_button.setEnabled(true);
            repaint();
        }
        if(source==reset){                          // an patithei to koumpi reset apo tin tropopoihsh anakoinwsis
            textArea.setText(resultsOfNotices.get(index).get_notice());
            save_edit.setEnabled(false);            // to keimeno tha parei tin arxiki katastasi pou eixe prin tin epeksergasia     
            delete.setEnabled(false);               // tha apenergopoihthoun ta save delete kai reset
            textArea.setEditable(false);
            reset.setEnabled(false);
            edit.setEnabled(true);                  // kai tha energopoihthei kai pali to edit kai to cancel
            list.setEnabled(true);
            cancel_edit.setEnabled(true);
        }
        if(source==cancel_edit){                    // an patithei to koumpi cancel apo tin tropopoihsh anakoinwsis
            eastPanel.remove(noticePanel);
            eastPanel.remove(subjectPanel);
            eastPanel.remove(save_edit);            // i efarmogi tha erthei stin arxiki tis katastasi
            eastPanel.remove(reset);
            eastPanel.remove(delete);
            eastPanel.remove(edit);
            eastPanel.remove(cancel_edit);
            create_button.setEnabled(true);
            modify_button.setEnabled(true);
            read_button.setEnabled(true);
            repaint();
        }
        
        if(source==cancel_search){              // an patithei to cancel apo tin ananwsi anakoinwsis se sigkekrimeno diastima
            create_button.setEnabled(true);
            modify_button.setEnabled(true);     // i efarmogi tha erthei stin arxiki tis katastasi
            read_button.setEnabled(true);
            
            eastPanel.remove(noticePanel);
            repaint();
        }   
        if (source==search){                    // an patithei to search apo tin ananwsi anakoinwsis se sigkekrimeno diastima
            boolean error_flag=false;
            DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");    
            DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            Date from=null;
            Date to=null;
            
            try {                                       // tha paroume tis dio imerominies pou ethese o xristis (strings)       
                from= df1.parse(fromField.getText());   // kai tha ta metatrepsoume se morfi date
                to = df2.parse(toField.getText());      

                long diff=to.getTime() -from.getTime();
                if(diff<1){error_flag=true;}            // elegxoume an to checkin einai pio prin apo to check out
            } catch (ParseException e) { 
                error_flag=true;
            }
            if(error_flag==false){                      // an den iparksi kappoio lathos 
                try {               
                    sock = new Socket ("localhost",8080);
                    
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;// kanoume tin sindesioutstream 
                    instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn

                    outstream.writeObject(new Request("FIND",yourself,from ,to));
                    outstream.flush();                                          // stelnoume ston server minima na vrei tis anakoinwseis 
                                                                                // kai ta diastimata
                    Request req=(Request)instream.readObject();
                    resultsOfNotices=new ArrayList();
                    
                    resultsOfNotices=req.getNoticeList();
                    System.out.println(resultsOfNotices.size());
                    if(req.getMessage().equals("FOUNDED")){                     // an mas pei founded tote tha mas steilei mazi kai
                        System.out.println(req.getMessage());                   // tin lista me autes
                        outstream.writeObject(new Request("OK"));               // tha tou poume ok
                        outstream.flush();                      
                    }
                    sock.close();                                               // kai tha kleisoume tin sindesi
                    eastPanel.remove(noticePanel);
                    
                    
                        
                    subjectPanel=new JPanel();                                  // ftiaxnoume panel 
                    subjectPanel.setBounds(20,10,300,50);
                    subjectPanel.setLayout(new BorderLayout());                 // kai components opws pio panw gia tin tropopoihsh
                    listModel=new DefaultListModel();   
                    for(int i=0;i<resultsOfNotices.size();i++){                            
                        listModel.addElement(resultsOfNotices.get(i).get_subject());
                    }          
                    list = new JList(listModel);
                        
                    JScrollPane scrollPane1 = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    subjectPanel.add(scrollPane1,BorderLayout.CENTER);
                      
                    //list.setSelectedIndex(0);
                         
                                                                                // ftiaxnoume buttons
                    close=new JButton("CLOSE");
                    close.setBounds(330, 35, 90, 25);
                    read=new JButton("READ");                    
                    read.setBounds(330, 10, 90, 25);
                    
                    eastPanel.add(close);                                       // kai ola auta ta vazoume sto east panel
                    eastPanel.add(read);
                    eastPanel.add(subjectPanel);
                    
                        
                    repaint();
                    eastPanel.revalidate();       
                    read.addActionListener(this);
                    close.addActionListener(this);                              // vazoume energies sta koumpia
                    
                    
                    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    list.addListSelectionListener((ListSelectionEvent evt) -> {
                        index=list.getSelectedIndex();                          //epistrefei -1 otan den iparxei selection        
                    });  
                    
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }else{
            
            }
        }
        
        if(source==read){                       // otan epilexthei mia anakoinwsi kai patithei to read
            create_button.setEnabled(false);
            modify_button.setEnabled(false);
            read_button.setEnabled(false);
            
            try {
                    sock = new Socket ("localhost",8080);
                    
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;// kanoume tin sindesi
                    instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn

                    outstream.writeObject(new Request("READ",resultsOfNotices.get(index)));
                    outstream.flush();                                          // stelnoume minima ston server oti tha diavasoume
                                                                                // tin anakoinwsi pou tha tou stelnoume
                    Request req=(Request)instream.readObject();                 // an apantisei read this seimainei 
                                                                                // pws tha tin kleidwsei gia na min mporesei na tin epeksergastei
                    if ( req.getMessage().equals("READ THIS")){                 // kapoios i na tin diagrapsei enw tin diavazoume
                        System.out.println(req.getMessage());
                        noticePanel=new JPanel();   
                        noticePanel.setBounds(20, 70, 400, 170);                // ftiaxnoume perivallon gia na tin emfanisoume tin anakoinwnsi
                        noticePanel.setLayout(new BorderLayout());  
                        textArea = new JTextArea(10,34);  
                        JScrollPane scrollPane2 = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        textArea.setLineWrap(true);

                        textArea.setEditable(false);                            // apenergopoioume to textarea gia na min grafei o xristis
                        noticePanel.add(scrollPane2,BorderLayout.CENTER);       // panw stin anakoinwsi
                      
                        if(index>-1 && index< listModel.getSize()){
                            textArea.setText(req.getNotice().get_notice());     
                        }  
                        noticePanel.add(scrollPane2);
                        eastPanel.add(noticePanel);                             // vazoume to perivallon panw sto eastPanel
                        eastPanel.repaint();
                        eastPanel.revalidate();
                        
                        outstream.writeObject(new Request("OK"));               // stelnoume ok ston server kai kleinoume tin sindesi
                        outstream.flush();  
                
                    }else if(req.getMessage().equals("LOCKED") ){               // an o server mas apantisei me locked tote 
                        JOptionPane.showMessageDialog(null,"Kapoios allos diavazei i epeksergazetai tin simiwsi auti", "Error",
                            JOptionPane.ERROR_MESSAGE);                         // auto seimainei pws den mporoume na diavasoume tin anakoinwsi
                        sock.close();                                           // kathws kapoios tin epeksergazetai i tinn diagrafei
                        
                        eastPanel.remove(subjectPanel);                         // kai etsi i efarmogi erxetai stin arxiki tis othoni
                        eastPanel.remove(noticePanel);
                        eastPanel.remove(close);
                        eastPanel.remove(read);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            
             
        }
        if(source==close){                      // an patithei to koumpi close apo tin anagnwsi anakoinwsen
            eastPanel.remove(subjectPanel);
            eastPanel.remove(noticePanel);      // i efarmogi tha epanerthei sti arxiki tis katastasi
            eastPanel.remove(close);
            eastPanel.remove(read);
           
            
            try {
                sock = new Socket ("localhost",8080);
                
                outstream = new ObjectOutputStream(sock.getOutputStream()) ;// kanoume tin sindesi
                instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn
                outstream.writeObject(new Request("READED",resultsOfNotices.get(index)));
                                                                            // stelnoume ston xristi oti diavastike i anakoinwsi
                outstream.flush();

                Request req=(Request)instream.readObject();                 // ean mas apantisei readed this tote apeleutherwse tin anakoinwsi
                if(req.getMessage().equals("READED THIS")){                 // wste na mporei na epeksergastei i na diagrafei apo pithno xristi
                    System.out.println(req.getMessage());
                    outstream.writeObject(new Request("OK"));               // tou apantame ok kai kleinoume tin sindesi
                    outstream.flush();  
                }
                sock.close();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Noticeboard_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            
            create_button.setEnabled(true);
            modify_button.setEnabled(true);
            read_button.setEnabled(true);
           
            repaint();
        }
        
    }
    
}
