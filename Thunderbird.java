 /******************************************************************************
 
 * This application utilizes the HttpRequest.java library developed by 
 * Eric Pogue
 * This application utilizes the ThunderBirdLite.java  developed by 
 * Eric Pogue
 *****************************************************************************/
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

class ContactTile extends JPanel {
    private int red, green, blue;
    private ThunderbirdContact contactInSeat = null;

    private Boolean isAnAisle = false;
    public void setAisle() { isAnAisle = true; }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // Todo: Implement visually appealing colors for aisles and seats.

        // DP- I made all the background colors to purple

        // DP- Removed SetRandomValues 
        //SetRandomValues(); 
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();

        //SetRandomValues(); -DP removed SetRandomValues
        red = 153;
        green = 51;
        blue = 255;
        contactInSeat = contactInSeatIn;
    }

    final public void SetRandomValues() {
        red = GetNumberBetween(0,255);
        green = GetNumberBetween(0,255);
        blue = GetNumberBetween(0,255);
    }

    private static int GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return min + myRandom.nextInt(max-min+1);
    }   

     public void paintComponent(Graphics g) {
        super.paintComponent(g); 

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (isAnAisle) {
            g.setColor(new Color(0,0,0));
        } else {
            g.setColor(new Color(red,green,blue));
        }
        
        g.fillRect (10, 10, panelWidth-20, panelHeight-20);

        g.setColor(new Color(GetContrastingColor(red),GetContrastingColor(green),GetContrastingColor(blue)));

        final int fontSize=18;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int stringX = (panelWidth/2)-60;
        int stringY = (panelHeight/2)+30;
        if (contactInSeat != null) {

            
            // DP- Displaying preferred name instead of first and Last name
            String displayName = contactInSeat.getPreferredName();
            g.drawString(displayName,stringX,stringY);
        }
    }

    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
}

class ThunderbirdFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList;

    public ThunderbirdFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton reverseView = new JButton("Reverse View");
        buttonPanel.add(reverseView);
        reverseView.addActionListener(this);

        JPanel contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);
        contactGridPanel.setLayout(new GridLayout(4,8)); // 4 rows and 8coloums in seating charts

        ThunderbirdModel tbM = new ThunderbirdModel();
        tbM.LoadIndex();

        // DP-implemented multithreaded version of loading contacts  
        tbM.LoadContactsThreaded(); // DP-changed to threaded.. makes the programm much faster

       
        
        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();   


        // looking for seat numbers 
        tileList = new ArrayList<ContactTile>();
        for(int i=1; i<33; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);
            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat);

            //Todo: Place all the aisle seats in a array or an ArrayList instead of hard coding them. 
            if ((i==4)||(i==12)||(i==20)||(i==28)||(i==31)) {
                tile.setAisle();
            }

            tileList.add(tile);
            contactGridPanel.add(tile);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for(ContactTile tile : tileList) {
         
            //tile.SetRandomValues(); // dp - removed randomization functionality 

            // Todo: Implement reverse view where it looks like you are looking at the room from the back instead of the front 
            //     of the room. 
        }

        repaint();
    }
}



// Renamed the class to Thunderbird - DP
public class Thunderbird {
    public static void main(String[] args) {

        
        // Updated printing message to Thunderbird - DP

        if ((args.length < 1)){
            System.out.println("Thunderbird Starting...");

        ThunderbirdFrame myThunderbirdFrame = new ThunderbirdFrame();
        myThunderbirdFrame.setVisible(true); 
            
        } 
       
        // responsing the httprequest and printing the data to the command line
        else if (args[0].equalsIgnoreCase("-HttpRequest")) {
            String url = args[1];
     
            long start = System.currentTimeMillis();
            HttpRequest request = new HttpRequest();
            System.out.println("-executing request");
            
            if (request.readURL(url)) {
                
                System.out.println(request);
                
                   
            } else {
                System.out.format("Unable to access", url);
            } 

            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println("Elapsed time in miliseconds: "+ elapsedTime);
            System.exit(0);
            
           
        }         
    
}
    }


