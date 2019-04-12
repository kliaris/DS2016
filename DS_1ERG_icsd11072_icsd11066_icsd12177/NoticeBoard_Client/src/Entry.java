// Giannis Kosmas   icsd11072
// Vagelis Kliaris  icsd11066
// Kon/nos Stafylas icsd12177
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
                                                                // i klasi auti einai gia tin eisodo tou xristi stin efarmogi
public class Entry extends JDialog implements ActionListener{
        Member login;                                           // edw tha oristhei o xristis pou thelei na sindethei kathe fora
        
        private JLabel usernamelabel,passwordlabel,usernamelabelSign,passwordlabelSign,repeatpasswordlabelSign,namelabelSign,surnamelabelSign;
        private JTextField usernamefield,usernamefieldSign,namefieldSign,surnamefieldSign;
        private JPasswordField passwordfield,passwordfieldSign,repeatpasswordfieldSign;
        
        private JButton loginbut,cancelbut,signinbut,cancelbutSign;
        
        static private Socket sock;                                 // dilwnoume to socket 
        
        
        static ObjectInputStream instream ;                         // dilwnoume streams gia ton dialogo me ton server
        static ObjectOutputStream outstream ;
        public Entry(JFrame parent,Object selectedValue){
            super(parent, "Login", true);          
            JPanel buttonsPanel = new JPanel();
            JPanel infoPanel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();                              
            cs.fill = GridBagConstraints.HORIZONTAL;
                                                                    // an o xristis apantisei pws einai idi melos
            if(selectedValue==(Object)JOptionPane.YES_OPTION){      // tote mesw enos dialogou tha tou zitithei to username kai o kwdikos

                usernamelabel = new JLabel("Username: ");
                cs.gridx = 0;
                cs.gridy = 0;
                cs.gridwidth = 1;
                infoPanel.add(usernamelabel, cs);

                usernamefield = new JTextField(20);
                cs.gridx = 1;
                cs.gridy = 0;
                cs.gridwidth = 2;
                infoPanel.add(usernamefield, cs);

                passwordlabel = new JLabel("Password: ");
                cs.gridx = 0;
                cs.gridy = 1;
                cs.gridwidth = 1;
                infoPanel.add(passwordlabel, cs);

                passwordfield = new JPasswordField(20);
                cs.gridx = 1;
                cs.gridy = 1;
                cs.gridwidth = 2;
                infoPanel.add(passwordfield, cs);
                infoPanel.setBorder(new LineBorder(Color.GRAY));
                    
                loginbut = new JButton("Login");
                cancelbut = new JButton("Cancel");
                
                
                buttonsPanel.add(loginbut);
                buttonsPanel.add(cancelbut);
                
                loginbut.addActionListener(this);
                cancelbut.addActionListener(this);
        
            }                                                   // an den einai idi melos tote mesw mia formas tha tou zitithoun 
            if(selectedValue==(Object)JOptionPane.NO_OPTION){   // to username o kwdikos ,ton opoio tha prepei na epalitheusei
                setTitle("Sign In");                            // to onoma kai to epitheto tou
                usernamelabelSign = new JLabel("Username: ");
                cs.gridx = 0;
                cs.gridy = 0;
                cs.gridwidth = 1;
                infoPanel.add(usernamelabelSign, cs);

                usernamefieldSign = new JTextField(20);
                cs.gridx = 1;
                cs.gridy = 0;
                cs.gridwidth = 2;
                infoPanel.add(usernamefieldSign, cs);

                passwordlabelSign = new JLabel("Password: ");
                cs.gridx = 0;
                cs.gridy = 1;
                cs.gridwidth = 1;
                infoPanel.add(passwordlabelSign, cs);

                passwordfieldSign = new JPasswordField(20);
                cs.gridx = 1;
                cs.gridy = 1;
                cs.gridwidth = 2;
                infoPanel.add(passwordfieldSign, cs);
                infoPanel.setBorder(new LineBorder(Color.GRAY));
                
                repeatpasswordlabelSign = new JLabel("Verify Password: ");
                cs.gridx = 0;
                cs.gridy = 2;
                cs.gridwidth = 1;
                infoPanel.add(repeatpasswordlabelSign, cs);
                
                repeatpasswordfieldSign = new JPasswordField(20);
                cs.gridx = 1;
                cs.gridy = 2;
                cs.gridwidth = 2;
                infoPanel.add(repeatpasswordfieldSign, cs);
                infoPanel.setBorder(new LineBorder(Color.GRAY));
                
                namelabelSign = new JLabel("First Name: ");
                cs.gridx = 0;
                cs.gridy = 3;
                cs.gridwidth = 1;
                infoPanel.add(namelabelSign, cs);

                namefieldSign = new JTextField(20);
                cs.gridx = 1;
                cs.gridy = 3;
                cs.gridwidth = 2;
                infoPanel.add(namefieldSign, cs);
                
                surnamelabelSign = new JLabel("Last Name: ");
                cs.gridx = 0;
                cs.gridy = 4;
                cs.gridwidth = 1;
                infoPanel.add(surnamelabelSign, cs);

                surnamefieldSign = new JTextField(20);
                cs.gridx = 1;
                cs.gridy = 4;
                cs.gridwidth = 2;
                infoPanel.add(surnamefieldSign, cs);
                    
                signinbut = new JButton("Sign in");
                cancelbutSign = new JButton("Cancel");
                
                
                buttonsPanel.add(signinbut);
                buttonsPanel.add(cancelbutSign);
                
                signinbut.addActionListener(this);
                cancelbutSign.addActionListener(this);
            }

            getContentPane().add(infoPanel, BorderLayout.CENTER);
            getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
            
            pack();
            setResizable(false);
            setLocationRelativeTo(parent);
            setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
     
        if (source==loginbut){                      // otan patithei to koumpi login tote prokeitai gia idi uparxwn melos
            String user=usernamefield.getText();    // apothikeuoume to username kai to password ppou edwse o xristis
            String pass=passwordfield.getText();
            if(user!=null && pass!=null){
                login =new Member(user,pass);
                
                try {
                    sock = new Socket ("localhost",8080);   // kanoume tin sindesi
                    
                    outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                    instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn
                    
                    outstream.writeObject(new Request("LOGIN",login));
                    outstream.flush();                                          // stelnoume ston server login kai ta stoixeia tou xristi
                    
                    Request req=(Request)instream.readObject();
                    if(req.getMessage().equals("CONNECTED") || req.getMessage().equals("Invalid Username or Password")){
                        System.out.println(req.getMessage());
                        if(req.getMessage().equals("CONNECTED")){               // an apantisei connected 
                            if(req.getMember().isAdmin()==true){                // elegxoume an o xristis einai admin
                                login.setisAdmin(); 
                            }
                            login.setisConnected();                             // kai thetoume ton xristi ws sindemeno
                        }
                        outstream.writeObject(new Request("OK"));               // apantame sto server me ok kai termatizoume tin sindesi
                        outstream.flush();
                        sock.close();
                    }else{
                        System.out.println("Lathos minima apo server.");
                        System.out.println("I sindesi termatistike");
                        sock.close();
                    }
                    dispose();                                                  // kleinei o dialogos kai kalwsorizoume ton xristi
                    JOptionPane.showMessageDialog(null,"Welcome, "+req.getMember().getName(), "Server Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | ClassNotFoundException ex) {
                    try {
                        sock.close();                                           // an prokipsei kapoio lathos trmatizoume tin sindesi
                    } catch (IOException ex1) {
                        Logger.getLogger(Entry.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }

                
            }else{
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if(source==signinbut){                              // an patithei to koumpi sign in tote prokeitai gia neo melos
            String user=usernamefieldSign.getText();
            String pass=passwordfieldSign.getText();        // apothikeusoume ta stoixeia pou edwse o xristis sti forma eggrafis
            String repeatpass=repeatpasswordfieldSign.getText();
            String name=namefieldSign.getText();
            String surname=surnamefieldSign.getText();
            if(!(user.equals("") && pass.equals("") && repeatpass.equals("") && name.equals("") && surname.equals(""))){
                if(pass.equals(repeatpass)){
                    Member newmember =new Member(user,pass,name,surname);
                    try {
                        sock = new Socket ("localhost",8080);                   // kanoume tin sindesi
                        outstream = new ObjectOutputStream(sock.getOutputStream()) ;
                        instream = new ObjectInputStream(sock.getInputStream()) ;   // o dialogos me ton server tha ginei mew antikeimenwn
                        
                        outstream.writeObject(new Request("SIGNIN",newmember));
                        outstream.flush();                                      // stoume signin kai to antikeimeno me ta stoixeia tou xristi
                        
                        Request req=(Request)instream.readObject();
                        login=req.getMember();                                  // an mas apantisei connected tote i eggrafi egine me epitixia
                        if(req.getMessage().equals("CONNECTED") ){
                            System.out.println(req.getMessage());
                            
                            if(req.getMember().isAdmin()==true){
                                login.setisAdmin(); 
                            }
                            login.setisConnected();
                                                                                // apantame me ok kai kleinoume tin sindesi
                            outstream.writeObject(new Request("OK"));
                            outstream.flush();
                            sock.close();
                        }else{
                            System.out.println("Lathos minima apo server.");
                            System.out.println("I sindesi termatistike");
                            sock.close();
                        }
                        dispose();
                        JOptionPane.showMessageDialog(null,"Welcome, "+req.getMember().getName(), "Server Message", JOptionPane.INFORMATION_MESSAGE);
                        
                    } catch (IOException | ClassNotFoundException ex) {
                        try {
                            sock.close();                                           // an prokipsei kapoio lathos trmatizoume tin sindesi
                        } catch (IOException ex1) {
                            Logger.getLogger(Entry.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }                         
                }else{
                    JOptionPane.showMessageDialog(null,"Password Verification Error ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Elleipis stoixeia ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(source==cancelbut || source==cancelbutSign){
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }                                       // an patithei se mia apo tis dio autes periptwseis to cancel tote kleinei to parathiro
        
    }
    
    public Member getLogin(){return login;}
        
        
}