import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Creates graphical user interface for the sandwich tracker using {@link JFrame}
 * @see ActionListener
 * @author Owen Reid
 *
 */

/*
    Date : 2025-03-26

    This Class creates the graphical interface for the "Sandwich Tracker" using the javax swing library
    It extends JFrame which allows itself to be referenced as the frame. For ease of creation many different
    parts will be put onto JPanels which will then be placed on to the frame as needed.

    TODO For future, use inheritance on things like buttons that have repetitive traits to clean up code (not huge difference but is good to do)

*/


public class SandwichFrame extends JFrame implements ActionListener {
    //normal variables
    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 800;
    private static Customer selectedCustomer;
    private static ArrayList<Sandwich> currSandwiches = new ArrayList<>();
    private static ArrayList<Drink> currDrinks = new ArrayList<>();

    //declaring panels and objects (such as labels etc.) that will be used
    private final JPanel customerDataPanel;

    //Customer Data Panel components
    private final JTable customerTable, pastOrderTable;
    private JLabel searchByEmail, sandwichType, sandwichSize, sandwichHasBacon, sandwichToasted, sandwichToppings, toysIncluded, logoLabel;
    private final JScrollPane customerScroll, pastOrderScrollPane, orderSummaryScrollPane, rightScroll;
    private final JTextField searchEmail;
    private final JComboBox<String> emailEndings;
    private final DefaultTableModel orderModelTable, modelTable;
    private final JButton customerDataButton = new JButton("Customer Data"), createOrderButton = new JButton("Create Order"), addCustomerButton = new JButton("Add Customer");
    private BufferedImage logo, icon;
    private Timer timer;

    //Components for Order creation area
    private JButton italianBMT, turkeyBreast, roastBeef, coldCutCombo, steakAndCheese, ham, confirmSandwich, confirmDrink, confirmOrder, pepsiButton;
    private BufferedImage italianIMG, turkeyIMG, roastIMG, coldCutIMG, steakIMG, hamIMG, pepsiIMG;
    private  JCheckBox tomato, lettuce, onion, cheese, peppers, upgraded, bacon, toy, toasted, pepsi, coke, sevenUp, drPepper, sprite;
    private int mouseX, mouseY; //going to be used to track mouse cords for dragging frame
    private final JComboBox<String> sizes;
    private JFrame customizationFrame, drinkFrame;
    private String sandwichTypeForSave;

    //components for add customer
    private JTextField name, email;
    private JButton createCustomer;
    private JLabel nameLabel, emailLabel;

    /**
     *
     * Constructor for Sandwich Frame - responsible for creating initial instance of the frame
     *
     */

    public SandwichFrame() {
        //sets all the attributes of the frame (self explanatory what each one does)
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Joaquin's Filipino Sandwich Shop");
        this.addWindowListener(new WindowAdapter() {
            /**
             * This method is activated when the window is supposed to close
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                //calls the method to save all the data
                Main.sandwichIO.customerWrite();
                //closes the window
                closeWindow();
            }
        });
        //initialize Icon Image - I could use a throws declaration, but then the frame wouldn't open if there was an exception
        try {
            icon = ImageIO.read(new File("Sandwich Icon.png"));
            //sets the little icon in the window (won't be there on mac)
            this.setIconImage(icon);
        }
        catch (IOException IOE) {
            System.err.println("Error loading Icon Image");
        }

        //initialize customerDataPanel
        customerDataPanel = new JPanel() {

            /**
             *
             * Paints the rectangles on the GUI
             * @param g the <code>Graphics</code> object to protect
             */

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.decode("#46756F"));
                g.fillRect(0, 0, 200, 800);
            }
        };
        //setting the attributes of the customer data panel
        customerDataPanel.setLayout(null);
        customerDataPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        customerDataPanel.setBackground(Color.decode("#77B28C"));

        //initialize logo image

        try {
            logo = ImageIO.read(new File("cool sandwich.png"));
        }
        catch (IOException IOE) {
            System.err.println(IOE);
        }
        //checks to make sure the logo was actually initialized before trying to create logoLabel
        if (logo != null) {
            logoLabel = new JLabel(new ImageIcon(logo));
            logoLabel.setBounds(25, 0, 150, 150);
        }

        //Adding stuff to the customerDataPanel

        //Responsible for adding the "Customer Data" button to the side panel -setting attributes

        customerDataButton.setBounds(0, 140, 200, 50);
        customerDataButton.setOpaque(false);
        customerDataButton.setContentAreaFilled(false);
        customerDataButton.setBorderPainted(false);
        customerDataButton.setFocusPainted(false);
        customerDataButton.setFont(new Font("Arial", Font.PLAIN, 20));
        customerDataButton.setForeground(Color.white);
        customerDataButton.addMouseListener(new MouseAdapter() {

            /**
             * Highlights the button when the mouse is hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseEntered(MouseEvent e) {
                customerDataButton.setContentAreaFilled(true);
                customerDataButton.setOpaque(true);
                customerDataButton.setBackground(Color.decode("#65A49C"));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            /**
             * Un-highlights the button when the mouse is no longer hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseExited(MouseEvent e) {
                customerDataButton.setContentAreaFilled(false);
                customerDataButton.setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        customerDataButton.addActionListener(this);

        //initialize add customer button

        addCustomerButton.setBounds(0, 250, 200, 50);
        addCustomerButton.setOpaque(false);
        addCustomerButton.setContentAreaFilled(false);
        addCustomerButton.setBorderPainted(false);
        addCustomerButton.setFocusPainted(false);
        addCustomerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        addCustomerButton.setForeground(Color.white);
        addCustomerButton.addMouseListener(new MouseAdapter() {

            /**
             * Highlights the button when the mouse is hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseEntered(MouseEvent e) {
                addCustomerButton.setContentAreaFilled(true);
                addCustomerButton.setOpaque(true);
                addCustomerButton.setBackground(Color.decode("#65A49C"));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            /**
             * Un-highlights the button when the mouse is no longer hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseExited(MouseEvent e) {
                addCustomerButton.setContentAreaFilled(false);
                addCustomerButton.setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        addCustomerButton.addActionListener(this);


        //setup order button
        createOrderButton.setBounds(0, 195, 200, 50);
        createOrderButton.setOpaque(false);
        createOrderButton.setContentAreaFilled(false);
        createOrderButton.setBorderPainted(false);
        createOrderButton.setFocusPainted(false);
        createOrderButton.setFont(new Font("Arial", Font.PLAIN, 20));
        createOrderButton.setForeground(Color.white);
        createOrderButton.addMouseListener(new MouseAdapter() {

            /**
             * Highlights the button when the mouse is hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseEntered(MouseEvent e) {
                createOrderButton.setContentAreaFilled(true);
                createOrderButton.setOpaque(true);
                createOrderButton.setBackground(Color.decode("#65A49C"));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //changes cursor type to the little hand thing
            }

            /**
             * Un-highlights the button when the mouse is no longer hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseExited(MouseEvent e) {
                createOrderButton.setContentAreaFilled(false);
                createOrderButton.setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        createOrderButton.addActionListener(this);


        //initialize customer table

        String[] colNames = {"Customer Name", "Email"};
        Object[][] tableData = new Object[Main.customers.size()][2];

        for (int i = 0; i < Main.customers.size(); i++) {
            tableData[i][0] = Main.customers.get(i);
            tableData[i][1] = Main.customers.get(i).getEmail();
        }

        modelTable = new DefaultTableModel(tableData, colNames) {

            /**
             * Overrides isCellEditable in anonymous class to prevent editing of modelTable
             * @see DefaultTableModel#isCellEditable(int, int)
             * @param row          the row whose value is to be queried
             * @param col          the column whose value is to be queried
             * @return             returns false
             */

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        customerTable = new JTable(modelTable);
        customerTable.setBackground(Color.white);
        /*
            Allows for table to be clickable, overriding this way because easier to keep track of for multiple tables, and
            more type safe because I know a customer will always be in the 0th column and String in other column
         */
        customerTable.addMouseListener(new MouseAdapter() {

            /**
             * Declares anonymous class to override MouseAdapters' mouseClicked method to store rows clicked in table
             *
             * @param e the event to be processed
             */

            @Override
            public void mouseClicked(MouseEvent e) {
                //get row clicked in customer table
                int row = customerTable.rowAtPoint(e.getPoint());

                //get the associated customer and email from the table row
                Customer customer = (Customer) customerTable.getValueAt(row, 0);
                String email = (String) customerTable.getValueAt(row, 1);

                //refreshes current order if customer is changed
                if (selectedCustomer != null && !selectedCustomer.equals(customer)) {
                    currSandwiches = new ArrayList<>();
                    currDrinks = new ArrayList<>();
                }

                //changes the selected customer and calls method to display their past orders
                selectedCustomer = customer;
                changePastOrders();
            }
        });

        //initialize customer scrollPane and add table to it (allows table to be scrollable)
        customerScroll = new JScrollPane(customerTable);
        customerScroll.setBounds(15, 100, 500, 200);
        //customerScroll.getViewport().setBackground(Color.decode("#C2C5BB"));

        //Initialize search by email label
        searchByEmail = new JLabel("Search By Email");
        searchByEmail.setBounds(15, 15, 250, 25);
        searchByEmail.setFont(new Font("Arial" ,Font.PLAIN, 20));
        searchByEmail.setForeground(Color.white);

        //Initialize searchEmail text field
        searchEmail = new JTextField();
        searchEmail.setBounds(15, 45, 350, 50);
        searchEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        searchEmail.setBorder(BorderFactory.createLineBorder(Color.white));
        searchEmail.getDocument().addDocumentListener(new DocumentListener() {
            //String ending = ();
            @Override
            public void changedUpdate(DocumentEvent e) {
                //this isn't used but has to be here because it's a part of the interface and needs implementation
            }

            /**
             * Detects the removal of a character from the searchEmail text box
             * @param e the document event
             */
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                //call customer searching method in main and update the customer table
                Customer[] customerArr = Main.sortEmails(searchEmail.getText(), getAppendedEmailEnding());
                updateTable(customerArr);

            }

            /**
             * Detects the addition of a character to the searchEmail text box
             * @param e the document event
             */
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                //call customer searching method in main and update the customer table
                Customer[] customerArr = Main.sortEmails(searchEmail.getText(), getAppendedEmailEnding());
                updateTable(customerArr);

            }
            
        });


        //Email Ending Box
        String[] endings = {"@gmail.com", "@outlook.com", "@yahoo.com", "Other"}; //displayed endings
        emailEndings = new JComboBox<>(endings);
        emailEndings.setBounds(365, 45, 150, 50);
        emailEndings.setFont(new Font("Arial", Font.PLAIN, 18));
        emailEndings.setBackground(Color.white);
        emailEndings.addActionListener(this);
        emailEndings.setBorder(BorderFactory.createLineBorder(Color.white));

        //initialize Past Orders Table
        String[] pastOrderCol = {"Item", "Cost", "Date of Purchase"};
        ArrayList<Order> savedOrders = (selectedCustomer != null) ? selectedCustomer.getPastOrders() : new ArrayList<>();
        //will change to object array to be able to use Order object when ready
        Object[][] pastOrderData = new Object[savedOrders.size()][3];

        for (int i = 0; i < savedOrders.size(); i++) {
            //for now just works by using string input, will probably change to take in values from order class
            // --> also will be able to get more data when clicked (using order object in first slot)
            pastOrderData[i][0] = savedOrders.get(i);
            pastOrderData[i][1] = savedOrders.get(i).getTotal();
            pastOrderData[i][2] = savedOrders.get(i).getDate();
        }
        orderModelTable = new DefaultTableModel(pastOrderData, pastOrderCol) {
            //already java given java doc above (just makes table un-editable because otherwise you can type in it)
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        //adds the model table to the actual table
        pastOrderTable = new JTable(orderModelTable);

        //adds mouse detection for the table
        pastOrderTable.addMouseListener(new MouseAdapter() {
            //already java given java doc above
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = pastOrderTable.rowAtPoint(e.getPoint());
                //calls the method to change the values in the extended information area
                updateSummary((Order) pastOrderTable.getValueAt(row, 0));
            }
        });


        //initialize past order scroll pane
        pastOrderScrollPane = new JScrollPane(pastOrderTable);
        pastOrderScrollPane.setBounds(15, 315, 500, 200);

        //make JPanel to add to Scroll Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(null);
        summaryPanel.setPreferredSize(new Dimension(450, 275));

        //initialize JLabels for past order summary

        sandwichType = new JLabel("Sandwich Type: ");
        sandwichType.setFont(new Font("Arial", Font.PLAIN, 15));
        sandwichType.setBounds(25, 0, 450, 30);

        sandwichSize = new JLabel("Sandwich Size: ");
        sandwichSize.setFont(new Font("Arial", Font.PLAIN, 15));
        sandwichSize.setBounds(25, 50, 450, 30);

        sandwichHasBacon = new JLabel("Sandwich Has Bacon: ");
        sandwichHasBacon.setFont(new Font("Arial", Font.PLAIN, 15));
        sandwichHasBacon.setBounds(25, 100, 450, 30);

        sandwichToasted = new JLabel("Sandwich is Toasted: ");
        sandwichToasted.setFont(new Font("Arial", Font.PLAIN, 15));
        sandwichToasted.setBounds(25, 150, 450, 30);

        sandwichToppings = new JLabel("Sandwich Toppings: ");
        sandwichToppings.setFont(new Font("Arial", Font.PLAIN, 15));
        sandwichToppings.setBounds(25, 200, 450, 30);

        toysIncluded = new JLabel("Toy Included: ");
        toysIncluded.setFont(new Font("Arial", Font.PLAIN, 15));
        toysIncluded.setBounds(25, 250, 450, 30);

        //add labels to panel
        summaryPanel.add(sandwichType);
        summaryPanel.add(sandwichSize);
        summaryPanel.add(sandwichHasBacon);
        summaryPanel.add(sandwichToasted);
        summaryPanel.add(sandwichToppings);
        summaryPanel.add(toysIncluded);

        //add JPanel to Summary Panel
        orderSummaryScrollPane = new JScrollPane(summaryPanel);
        orderSummaryScrollPane.setBounds(15, 530, 500, 200);

        //initialize rightScroll and addElements
        JPanel rightPanel = new JPanel() {

            /**
             * Paints rectangle on JPanel
             * @param g the <code>Graphics</code> object to protect
             */
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.decode("#C2C5BB"));
                g.fillRect(0, 0, 533, 4200);
            }
        };
        rightPanel.setLayout(null);
        //sets preferred dimensions (can resize, but will choose to be these dimensions if possible, good when adding to scroll pane)
        rightPanel.setPreferredSize(new Dimension(532, 4200));

        rightPanel.add(customerScroll);
        rightPanel.add(pastOrderScrollPane);
        rightPanel.add(orderSummaryScrollPane);
        rightPanel.add(searchByEmail);
        rightPanel.add(searchEmail);
        rightPanel.add(emailEndings);

        //---- add elements for order area ----

        //initialize confirm order button
        confirmOrder = new JButton("Confirm Order");
        confirmOrder.setBackground(Color.WHITE);
        confirmOrder.setBounds(40,1700, 445, 30);
        confirmOrder.setBorder(BorderFactory.createEmptyBorder());
        confirmOrder.setFocusPainted(false);
        confirmOrder.addActionListener(this);

        //---- Initialize sandwich buttons ----

        //italianBMT, turkeyBreast, roastBeef, coldCutCombo, steakAndCheese, ham

        try {
            //initialize italianBMT
            italianBMT = new JButton();
            italianIMG = ImageIO.read(new File("ItalianBMT.png"));
            italianBMT.setIcon(new ImageIcon(italianIMG));
            italianBMT.setBounds(40, 1020, 180, 180);
            italianBMT.setBorder(BorderFactory.createEmptyBorder());
            italianBMT.addMouseListener(new MouseAdapter() {
                /**
                 * Detects when the mouse in hovering the button, if so change the cursor to the middle hand icon
                 * @param e the event to be processed
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                /**
                 * When cursor goes from hovering to not hover, change back to regular cursor
                 * @param e the event to be processed
                 */

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            italianBMT.addActionListener(this);
            //adds client property, (Key, object to return), this is used to differential the buttons from each other, returns enum
            italianBMT.putClientProperty("MenuItem", MenuItems.ITALIAN_BMT);

            //Every thing else in this try catch is the exact same so im not going to comment it all

            //initialize turkey
            turkeyBreast = new JButton();
            turkeyIMG = ImageIO.read(new File("TurkeyBreast.png"));
            turkeyBreast.setIcon(new ImageIcon(turkeyIMG));
            turkeyBreast.setBounds(300, 1020, 180, 180);
            turkeyBreast.setBorder(BorderFactory.createEmptyBorder());
            turkeyBreast.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            turkeyBreast.addActionListener(this);
            turkeyBreast.putClientProperty("MenuItem", MenuItems.TURKEY_BREAST);

            //initialize Roast Beef
            roastBeef = new JButton();
            roastIMG = ImageIO.read(new File("RoastBeefSandwich.png"));
            roastBeef.setIcon(new ImageIcon(roastIMG));
            roastBeef.setBounds(40, 1235, 180, 180);
            roastBeef.setBorder(BorderFactory.createEmptyBorder());
            roastBeef.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            roastBeef.addActionListener(this);
            roastBeef.putClientProperty("MenuItem", MenuItems.ROAST_BEEF);

            //initialize Pepsi
            pepsiButton = new JButton();
            pepsiIMG = ImageIO.read(new File("PepsiCanCool.png"));
            pepsiButton.setIcon(new ImageIcon(pepsiIMG));
            pepsiButton.setBounds(40, 1450, 180, 180);
            pepsiButton.setBorder(BorderFactory.createEmptyBorder());
            pepsiButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            pepsiButton.addActionListener(this);

            //initialize Steak and Cheese
            steakAndCheese = new JButton();
            steakIMG = ImageIO.read(new File("SteakAndCheeseSandwich.png"));
            steakAndCheese.setIcon(new ImageIcon(steakIMG));
            steakAndCheese.setBounds(300, 1235, 180, 180);
            steakAndCheese.setBorder(BorderFactory.createEmptyBorder());
            steakAndCheese.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            steakAndCheese.addActionListener(this);
            steakAndCheese.putClientProperty("MenuItem", MenuItems.STEAK_AND_CHEESE);


            //add right in the try catch so nothing breaks if exception is thrown
            rightPanel.add(turkeyBreast);
            rightPanel.add(italianBMT);
            rightPanel.add(roastBeef);
            rightPanel.add(steakAndCheese);
            rightPanel.add(pepsiButton);
        }
        catch (Exception IOE) {
            System.err.println("Error loading image files");
        }

        //initialization for components to be used in order frame - the checkboxes for toppings

        tomato = new JCheckBox("Tomato");
        tomato.setBounds(260, 80, 100, 25);
        tomato.setBackground(Color.white);
        tomato.setBorder(BorderFactory.createEmptyBorder());
        tomato.setFocusPainted(false);

        lettuce = new JCheckBox("Lettuce");
        lettuce.setBounds(260, 110, 100, 25);
        lettuce.setBackground(Color.white);
        lettuce.setBorder(BorderFactory.createEmptyBorder());
        lettuce.setFocusPainted(false);

        onion = new JCheckBox("Onion");
        onion.setBounds(260, 140, 100, 25);
        onion.setBackground(Color.white);
        onion.setBorder(BorderFactory.createEmptyBorder());
        onion.setFocusPainted(false);

        cheese = new JCheckBox("Cheese");
        cheese.setBounds(260, 170, 100, 25);
        cheese.setBackground(Color.white);
        cheese.setBorder(BorderFactory.createEmptyBorder());
        cheese.setFocusPainted(false);

        peppers = new JCheckBox("Peppers");
        peppers.setBounds(260, 200, 100, 25);
        peppers.setBackground(Color.white);
        peppers.setBorder(BorderFactory.createEmptyBorder());
        peppers.setFocusPainted(false);

        upgraded = new JCheckBox("Upgrade to Sandwich Plus");
        upgraded.setBounds(260, 230, 180, 25);
        upgraded.setBackground(Color.white);
        upgraded.setBorder(BorderFactory.createEmptyBorder());
        upgraded.setFocusPainted(false);
        upgraded.addActionListener(this);

        bacon = new JCheckBox("Bacon");
        bacon.setBounds(260, 260, 100, 25);
        bacon.setBackground(Color.white);
        bacon.setBorder(BorderFactory.createEmptyBorder());
        bacon.setFocusPainted(false);

        toy = new JCheckBox("Toy Included");
        toy.setBounds(260, 290, 100, 25);
        toy.setBackground(Color.white);
        toy.setBorder(BorderFactory.createEmptyBorder());
        toy.setFocusPainted(false);
        toy.setEnabled(false); //makes it so user can't change if checked

        toasted = new JCheckBox("Toasted");
        toasted.setBounds(260, 320, 100, 25);
        toasted.setBackground(Color.white);
        toasted.setBorder(BorderFactory.createEmptyBorder());
        toasted.setFocusPainted(false);

        //initialize size drop down menu
        String[] sizeArr = {"6 inch", "12 inch"}; //items in drop down
        sizes = new JComboBox<>(sizeArr);
        sizes.setBounds(260, 350, 180, 25);
        sizes.setBackground(Color.WHITE);
        sizes.setBorder(BorderFactory.createEmptyBorder());

        //initialize confirm sandwich button
        confirmSandwich = new JButton("Confirm Sandwich");
        confirmSandwich.setBackground(Color.lightGray);
        confirmSandwich.setBounds(25,500, 450, 30);
        confirmSandwich.addActionListener(this);

        //initialize confirm drinks button
        confirmDrink = new JButton("Confirm Drinks");
        confirmDrink.setBackground(Color.lightGray);
        confirmDrink.setBounds(25,260, 150, 30);
        confirmDrink.addActionListener(this);


        //---- Create area for adding new customers ----

        name = new JTextField();
        name.setBounds(150, 3050, 300, 30);
        name.setBorder(BorderFactory.createEmptyBorder());

        email = new JTextField();
        email.setBounds(150, 3100, 300, 30);
        email.setBorder(BorderFactory.createEmptyBorder());

        createCustomer = new JButton("Create Customer");
        createCustomer.setBounds(150, 3300, 200, 50);
        createCustomer.setBackground(Color.WHITE);
        createCustomer.addActionListener(this);

        nameLabel = new JLabel("Customer Name: ");
        nameLabel.setBounds(20, 3050, 300, 30);

        emailLabel = new JLabel("Customer Email: ");
        emailLabel.setBounds(20, 3100, 300, 30);

        //add to rightPanel
        rightPanel.add(name);
        rightPanel.add(email);
        rightPanel.add(createCustomer);
        rightPanel.add(nameLabel);
        rightPanel.add(emailLabel);
        rightPanel.add(confirmOrder);


        //--------------------------

        //scroll pane that the right panel goes in (to make it scrollable)

        rightScroll = new JScrollPane(rightPanel);
        rightScroll.setBounds(230, 10, 532, 740);
        rightScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        rightScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setBorder(BorderFactory.createEmptyBorder());



        //add Elements to customerDataPanel - entire screen
        customerDataPanel.add(customerDataButton);
        customerDataPanel.add(createOrderButton);
        customerDataPanel.add(addCustomerButton);
        customerDataPanel.add(rightScroll);
        if (logoLabel != null) customerDataPanel.add(logoLabel);




        //add customerData panel to Frame and show (Customer data panel is default window)
        this.add(customerDataPanel);
        this.setVisible(true);


    }

    /**
     * Used to add functionality to items in the GUI
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //e.getSource() returns the object that called the actionListener

        //email ending box has different value selected
        if (e.getSource() == emailEndings) {
            //String emailEnding = (Objects.equals(emailEndings.getSelectedItem(), "Other")) ? "" : (String) emailEndings.getSelectedItem();
            //calls the search method for customers, and returns only those with the selected email endings, other = any ending
            if (searchEmail.getText() != null)  {
                Customer[] arr = Main.sortEmails(searchEmail.getText(), getAppendedEmailEnding());
                updateTable(arr);
            }
        }
        else if (e.getSource() == createOrderButton) { //side button (only going to comment one bc all same
            //return if already at expected spot in scroll pane
            if (rightScroll.getVerticalScrollBar().getValue() == 1000) return;
            //initial timer, activates every 1ms and calls overridden actionListener
            timer = new Timer(1, new ActionListener() {
                /**
                 * Controls scrolling of right scroll pane when button pressed - Timer version
                 * @param e the event to be processed
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    //because this spot in the pane can either go up or down, a ternary is used to determine which way it needs to go to get to the 'create order' area
                    rightScroll.getVerticalScrollBar().setValue(rightScroll.getVerticalScrollBar().getValue()+((rightScroll.getVerticalScrollBar().getValue() < 1000) ? 20 : -20));

                    //check to see if the scroll pane is at the right spot in the pane (if so stop timer and set it directly to 1000)
                    if (rightScroll.getVerticalScrollBar().getValue() >= 1000 && Math.abs(rightScroll.getVerticalScrollBar().getValue()-1000) <= 30 ){
                        timer.stop();
                        rightScroll.getVerticalScrollBar().setValue(1000); //in case of surpassing 1000
                    }
                }
            });
            timer.start(); //starts the timer
        }
        else if (e.getSource() == customerDataButton) {
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightScroll.getVerticalScrollBar().setValue(rightScroll.getVerticalScrollBar().getValue()-20);

                    if (rightScroll.getVerticalScrollBar().getValue() == 0) {
                        timer.stop();
                    }
                }
            });
            timer.start();
        }
        else if (e.getSource() == addCustomerButton) {
            if (rightScroll.getVerticalScrollBar().getValue() == 3000) return;
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightScroll.getVerticalScrollBar().setValue(rightScroll.getVerticalScrollBar().getValue()+20);

                    if (rightScroll.getVerticalScrollBar().getValue() >= 3000) {
                        timer.stop();
                    }
                }
            });
            timer.start();
        }
        else if (e.getSource() == createCustomer) {
            //make customer with inputted data, return if one of the fields is blank (don't make the customer)
            if (name.getText().isBlank() || email.getText().isBlank()) return;
            Customer newCustomer = new Customer(name.getText(), email.getText());
            Main.customers.add(newCustomer);
            /*
               Passing in a customer array of size 0 to toArray allows it to convert the arrayList to type customer
               rather than Object.
             */
            //does all the needed stuff like updating file io
            updateTable(Main.customers.toArray(new Customer[0]));
            Main.sandwichIO.addCustomer(newCustomer);
            name.setText("");
            email.setText("");
        }
        else if (e.getSource() == upgraded) {
            //if sandwich plus is selected, auto select certain features, if deselected remove certain features
            bacon.setSelected(upgraded.isSelected());
            toy.setSelected(upgraded.isSelected());
            //gray out size drop down
            if (upgraded.isSelected()) {
                sizes.setEnabled(false);
                sizes.addItem("15 inch");
                sizes.setSelectedItem("15 inch");
            }
            else {
                sizes.setEnabled(true);
                sizes.removeItem("15 inch");
                sizes.setSelectedItem("6 inch");
            }
        }
        else if (e.getSource() == confirmSandwich) {
            //adds sandwich to currOrder and closes customization frame
            addSandwich();
            customizationFrame.dispose();
        }
        else if (e.getSource() == confirmDrink) {
            //adds drink to currDrink and gets rid of drink selection frame
            addDrink();
            drinkFrame.dispose();
        }
        else if (e.getSource() == confirmOrder) {
            //make order
            createOrder();
        }
        else if (e.getSource() == pepsiButton) {
            //open drink frame
            createDrinkFrame();
        }
        else if (e.getSource() instanceof JButton){
            //if this activates its 100% going to be one of the sandwich buttons, however makes sure the button has a client property (with key MenuItem) before passing it through
            if (((JButton) e.getSource()).getClientProperty("MenuItem") != null) customizationFrame((JButton) e.getSource());
        }
    }

    /**
     *
     * closes window and terminates program, implemented to make calling saving method before closing possible
     *
     */
    private void closeWindow() {
        this.dispose();
        System.exit(0);
    }

    /**
     *
     * called when row is selected inside customer table removes all rows and replaces past orders with current selected customers' past orders
     *
     */
    private void changePastOrders() {
        //check to make sure selectedCustomer exists
        if (selectedCustomer == null) return;

        //remove all current rows
        for (int i = orderModelTable.getRowCount()-1; i >= 0; i--) {
            orderModelTable.removeRow(i);
        }

        //replace rows with selected customer past orders
        ArrayList<Order> pastOrders = selectedCustomer.getPastOrders();
        for (int i = 0; i < selectedCustomer.getPastOrders().size(); i++) {
            orderModelTable.addRow(new Object[] {pastOrders.get(i), pastOrders.get(i).getTotal(), pastOrders.get(i).getDate()});
        }
    }

    /**
     *
     * Updates the order summary scroll pane which displays the expanded information of the selected customers past orders
     * @param order An Order Object, passed in by the past order table
     *
     */

    private void updateSummary(Order order) {
        //sandwichType, sandwichSize, sandwichHasBacon, sandwichToasted, sandwichToppings, toysIncluded;
        //get all the sandwiches and drinks stored in the order

        List<Sandwich> sandwiches = order.getSandwiches();
        List<Drink> drinks = order.getDrinks();

        //create a new JPanel to be added to the Scroll Pane
        JPanel newSummaryPanel = new JPanel();
        newSummaryPanel.setLayout(null);
        newSummaryPanel.setPreferredSize(new Dimension(450, 300*sandwiches.size() + 30*drinks.size()));


        //create and add all the sandwich labels (data) to the JPanel
        for (int i = 0; i < sandwiches.size(); i++) {
            Sandwich currSandwich = sandwiches.get(i);

            //creates new JLabel
            //sets the location in the JPanel
            //sets the font for the label
            JLabel sandwichType = new JLabel("Sandwich Type: " + sandwiches.get(i).getType());
            sandwichType.setBounds(25, 50*(i) + 250*i, 450, 30);
            sandwichType.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichType);

            //info about sandwich size
            JLabel sandwichSize = new JLabel("Sandwich Size: " + sandwiches.get(i).getSize());
            sandwichSize.setBounds(25, 50 + 50*(i) + 250*i, 450, 30);
            sandwichSize.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichSize);

            //info about if sandwich has bacon
            JLabel sandwichHasBacon = new JLabel("Sandwich Has Bacon: " + ((sandwiches.get(i).hasBacon()) ? "Yes" : "No"));
            sandwichHasBacon.setBounds(25, 100 + 50*i + 250*i, 450, 30);
            sandwichHasBacon.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichHasBacon);

            //info about if sandwich is toasted
            JLabel sandwichToasted = new JLabel("Sandwich is Toasted: " + ((sandwiches.get(i).isToasted()) ? "Yes" : "No"));
            sandwichToasted.setBounds(25, 150 + 50*i + 250*i, 450, 30);
            sandwichToasted.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichToasted);

            //makes a string that contains all the toppings separated by commas
            StringBuilder toppings = new StringBuilder();
            for (String s : sandwiches.get(i).getToppings()) {
                toppings.append(s).append(", ");
            }
            if (toppings.length()-2 >= 0) toppings = new StringBuilder(toppings.substring(0, toppings.length() - 2));

            JLabel sandwichToppings = new JLabel("Sandwich Toppings: " + toppings);
            sandwichToppings.setBounds(25, 200 + 50*i + 250*i, 450, 30);
            sandwichToppings.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichToppings);

            //info about if order has toy
            JLabel toyIncluded = new JLabel("Toy Included: " + sandwiches.get(i).getToy());
            toyIncluded.setBounds(25, 250 + 50*i + 250*i, 450, 30);
            toyIncluded.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(toyIncluded);

            //drinks in order
            StringBuilder drinksOrdered = new StringBuilder();
            for (Drink d : drinks) {
                drinksOrdered.append(d.getType()).append(", ");
            }
            if (drinksOrdered.length() >= 2) drinksOrdered = new StringBuilder(drinksOrdered.substring(0, drinksOrdered.length()-2));
            JLabel drinkLabel = new JLabel("Drinks Ordered: " + drinksOrdered);
            drinkLabel.setBounds(25, 300 + 50*i, 450, 30);
            drinkLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(drinkLabel);
        }
        if (sandwiches.isEmpty() && !drinks.isEmpty()) {
            //just easy way to display drink only orders
            //drinks in order
            StringBuilder drinksOrdered = new StringBuilder();
            for (Drink d : drinks) {
                drinksOrdered.append(d.getType()).append(", ");
            }
            if (drinksOrdered.length() >= 2) drinksOrdered = new StringBuilder(drinksOrdered.substring(0, drinksOrdered.length()-2));
            JLabel drinkLabel = new JLabel("Drinks Ordered: " + drinksOrdered);
            drinkLabel.setBounds(25,  0, 450, 30);
            drinkLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(drinkLabel);

        }
        //swaps the panel in scroll pane to newly created one (calls viewport to do so)
        orderSummaryScrollPane.getViewport().removeAll();
        orderSummaryScrollPane.getViewport().add(newSummaryPanel);
    }

    /**
     * updates the customer table based on the given array
     * @param customerList list of customers to be displayed in table
     */
    public void updateTable(Customer[] customerList) {

        //delete rows, starts from bottom and goes to top (otherwise table will shrink and loop will go out of bounds)
        for (int i = modelTable.getRowCount()-1; i >= 0; i--) {
            modelTable.removeRow(i);
        }
        for (Customer c : customerList) {
            //creates nwe object array containing a customer, and their email to add to the table as a row
            modelTable.addRow(new Object[] {c, c.getEmail()});
            //System.out.println(c);
        }
    }

    /**
     * Gets selected email ending from Email Endings drop down box
     * @return email ending selected
     */
    /*
        -- how this works is the ternary checks if the selected value is not other, in which it returns the selected value
           if the value is other, it returns the string literal other, additionally if there is no selected value, the
           resulting object from the JComboBox will be null, this will result in the selected value being null, resulting in false and
           which results in the "other" term being returned.
     */
    private String getAppendedEmailEnding() {
        return (!(emailEndings.getSelectedItem() == null) && !((String) emailEndings.getSelectedItem()).equalsIgnoreCase("other")) ? (String) emailEndings.getSelectedItem() : "other";
    }

    /**
     * Responsible for the creation of the ordering window
     * @param pressedButton the button pressed to call the method, used to determine which values to display
     */

    private void customizationFrame(JButton pressedButton) {

        if (selectedCustomer == null) return; //makes sure there is a customer associated with the order

        if (customizationFrame != null) customizationFrame.dispose(); //if a customization frame is already open, close it

        clearToppings(); //resets check boxes for toppings

        //initializes customization frame
        customizationFrame = new JFrame();
        customizationFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        customizationFrame.setUndecorated(true); //turns off window's default window border
        customizationFrame.setLayout(null);
        customizationFrame.setResizable(false);
        customizationFrame.setLocation(this.getLocation()); //sets location to main frame
        customizationFrame.setSize(500, 600);
        customizationFrame.setTitle("Customize Sandwich");
        customizationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                customizationFrame.dispose();
            }
        });

        //---- Create new window border ---- (replaces the top bar with a custom one)

            JPanel decoration = new JPanel();
            decoration.setLayout(null);
            decoration.setBounds(0, 0, customizationFrame.getWidth(), 30);
            decoration.setBackground(Color.WHITE);

            decoration.addMouseListener(new MouseAdapter() {
                /**
                 * Gets mouse position at time of click on decoration bar
                 * @param e the event to be processed
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                }
            });
            decoration.addMouseMotionListener(new MouseMotionAdapter() {
                /**
                 * Gets location of window to mouse cursor when dragging window
                 * @param e the event to be processed
                 */
                @Override
                public void mouseDragged(MouseEvent e) {
                    customizationFrame.setLocation(e.getXOnScreen()-mouseX, e.getYOnScreen()-mouseY);
                }
            });

            //---- Initialize X Icon ----
            try {
                BufferedImage xIcon = ImageIO.read(new File("XIcon.png"));

                //add x icon - acts as close button

                JButton xButton = new JButton();
                xButton.setIcon(new ImageIcon(xIcon));
                xButton.setBounds(3, 3, 30, 30);
                xButton.setFocusPainted(false);
                xButton.setBackground(Color.WHITE);
                xButton.setBorder(BorderFactory.createEmptyBorder());
                xButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //close frame when clicked
                        customizationFrame.dispose();
                    }
                });
                decoration.add(xButton);
            }
            catch (IOException e) {
                System.out.println("Error loading ordering menu - X Icon not found");
            }


            //add the decoration bar to the top of the screen (that's that the BorderLayout.NORTH does)
            customizationFrame.add(decoration, BorderLayout.NORTH);

        //---- Declare Components Locally (That don't need to access other frame) ----

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(0, decoration.getHeight(), customizationFrame.getWidth(), customizationFrame.getHeight());
            panel.setBackground(Color.WHITE);

            JLabel sandwichIcon = new JLabel();
            sandwichIcon.setBounds(20, 20, 180, 180);
            JLabel title = new JLabel();
            title.setBounds(260, 20, 250, 50);
            title.setFont(new Font("Arial", Font.PLAIN, 32));

            //add all the checkboxes
            panel.add(tomato);
            panel.add(lettuce);
            panel.add(onion);
            panel.add(cheese);
            panel.add(peppers);
            panel.add(upgraded);
            panel.add(bacon);
            panel.add(toy);
            panel.add(toasted);
            panel.add(sizes);
            panel.add(confirmSandwich);

        //------------------------------------------

        //checks to see which button has been passed in - slightly different creation of frame
        switch (pressedButton.getClientProperty("MenuItem")) {
            case MenuItems.ITALIAN_BMT -> {
                if (italianIMG != null) {
                    sandwichIcon.setIcon(new ImageIcon(italianIMG));
                }
                title.setText("Italian BMT");

                panel.add(title);
                panel.add(sandwichIcon);

                //set checkboxes to default values for this sandwich (false by default so only need to change to true)
                tomato.setSelected(true);
                lettuce.setSelected(true);


                customizationFrame.add(panel);
                customizationFrame.setVisible(true);
                sandwichTypeForSave = "Italian BMT";
            }
            case MenuItems.TURKEY_BREAST -> {
                if (turkeyIMG != null) {
                    sandwichIcon.setIcon(new ImageIcon(turkeyIMG));
                }
                title.setText("Turkey Breast");

                panel.add(title);
                panel.add(sandwichIcon);

                //set checkboxes to default values for this sandwich (false by default so only need to change to true)
                tomato.setSelected(true);
                lettuce.setSelected(true);
                onion.setSelected(true);


                customizationFrame.add(panel);
                customizationFrame.setVisible(true);
                sandwichTypeForSave = "Turkey Breast";
            }
            case MenuItems.COLD_CUT_COMBO -> {

            }
            case MenuItems.ROAST_BEEF -> {
                if (roastIMG != null) {
                    sandwichIcon.setIcon(new ImageIcon(roastIMG));
                }
                title.setText("Roast Beef");

                panel.add(title);
                panel.add(sandwichIcon);

                //set checkboxes to default values for this sandwich (false by default so only need to change to true)
                tomato.setSelected(true);
                lettuce.setSelected(true);
                onion.setSelected(true);


                customizationFrame.add(panel);
                customizationFrame.setVisible(true);
                sandwichTypeForSave = "Roast Beef";
            }
            case MenuItems.STEAK_AND_CHEESE -> {
                if (steakIMG != null) {
                    sandwichIcon.setIcon(new ImageIcon(steakIMG));
                }
                title.setText("Steak and Cheese");

                panel.add(title);
                panel.add(sandwichIcon);

                //set checkboxes to default values for this sandwich (false by default so only need to change to true)
                cheese.setSelected(true);
                bacon.setSelected(true);


                customizationFrame.add(panel);
                customizationFrame.setVisible(true);
                sandwichTypeForSave = "Steak and Cheese";
            }
            case MenuItems.HAM -> {
                //f
            }

            default -> {
                System.err.println("Unexpected Value Received by Button Input");
                customizationFrame.dispose();
            }

        }
    }

    /**
     * creates the drink ordering panel
     */

    private void createDrinkFrame() {
        //sets up frame - refer to other frame (same stuff)
        drinkFrame = new JFrame();
        drinkFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        drinkFrame.setUndecorated(true); //turns off window's default window border
        drinkFrame.setLayout(null);
        drinkFrame.setResizable(false);
        drinkFrame.setLocation(this.getLocation());
        drinkFrame.setSize(200, 300);
        drinkFrame.setTitle("Customize Sandwich");
        drinkFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //close frame
                drinkFrame.dispose();
            }
        });

        JPanel decoration = new JPanel();
        decoration.setLayout(null);
        decoration.setBounds(0, 0, drinkFrame.getWidth(), 30);
        decoration.setBackground(Color.WHITE);
        //allows panel to be dragged
        decoration.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

            }
        });
        decoration.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drinkFrame.setLocation(e.getXOnScreen()-mouseX, e.getYOnScreen()-mouseY);
            }
        });

        //---- Initialize X Icon ----
        try {
            BufferedImage xIcon = ImageIO.read(new File("XIcon.png"));

            //add x icon

            JButton xButton = new JButton();
            xButton.setIcon(new ImageIcon(xIcon));
            xButton.setBounds(3, 3, 30, 30);
            xButton.setFocusPainted(false);
            xButton.setBackground(Color.WHITE);
            xButton.setBorder(BorderFactory.createEmptyBorder());
            xButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //close frame when clicked
                    drinkFrame.dispose();
                }
            });
            decoration.add(xButton);
        }
        catch (IOException e) {
            System.out.println("Error loading ordering menu - X Icon not found");
        }

        //add bar to top
        drinkFrame.add(decoration, BorderLayout.NORTH);

        //set up panel and all the drink check boxes
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, drinkFrame.getWidth(), drinkFrame.getHeight());
        panel.setLayout(null);

        pepsi = new JCheckBox("Pepsi");
        pepsi.setSelected(false);
        pepsi.setBounds(100, 40, 100, 25);
        pepsi.setBackground(Color.WHITE);
        pepsi.setBorder(BorderFactory.createEmptyBorder());
        pepsi.setFocusPainted(false);

        coke = new JCheckBox("Coke");
        coke.setSelected(false);
        coke.setBounds(100, 70, 100, 25);
        coke.setBackground(Color.WHITE);
        coke.setBorder(BorderFactory.createEmptyBorder());
        coke.setFocusPainted(false);

        sevenUp = new JCheckBox("7-Up");
        sevenUp.setSelected(false);
        sevenUp.setBounds(100, 100, 100, 25);
        sevenUp.setBackground(Color.WHITE);
        sevenUp.setBorder(BorderFactory.createEmptyBorder());
        sevenUp.setFocusPainted(false);

        drPepper = new JCheckBox("Dr.Pepper");
        drPepper.setSelected(false);
        drPepper.setBounds(100, 130 ,100, 25);
        drPepper.setBackground(Color.WHITE);
        drPepper.setBorder(BorderFactory.createEmptyBorder());
        drPepper.setFocusPainted(false);

        sprite = new JCheckBox("Sprite");
        sprite.setSelected(false);
        sprite.setBounds(100, 160 ,100, 25);
        sprite.setBackground(Color.WHITE);
        sprite.setBorder(BorderFactory.createEmptyBorder());
        sprite.setFocusPainted(false);

        //adds checkboxes to panel
        panel.add(pepsi);
        panel.add(coke);
        panel.add(sevenUp);
        panel.add(drPepper);
        panel.add(sprite);
        panel.add(confirmDrink);

        //add panel to frame
        drinkFrame.add(panel);
        drinkFrame.setVisible(true);
    }

    /**
     * takes all the sandwiches and drinks and creates and order then adds it to the selected customers past orders
     */

    //creates order object
    private void createOrder() {
        if (selectedCustomer == null || (currSandwiches.isEmpty() && currDrinks.isEmpty())) return;
        Order order = new Order();

        //loops through sandwich array and adds it to order
        for (Sandwich sandwich : currSandwiches) {
            order.addSandwich(sandwich);
        }
        //loops through drink array and adds it to drink array
        for (Drink drink : currDrinks) {
            order.addDrink(drink);
        }
        //calculate cost of order, and update the customers past orders
        order.calcTotal();
        selectedCustomer.addOrder(order);
        changePastOrders();
    }

    /**
     * adds all the ordered sandwiches to the sandwiches array
     */

    private void addSandwich() {
        if (upgraded.isSelected()) {
            //sandwich plus
            SandwichPlus sandwichPlus = new SandwichPlus();
            sandwichPlus.setPrice(12);
            sandwichPlus.setType(sandwichTypeForSave);
            //create linked list of toppings
            LinkedList<String> toppings = new LinkedList<>();
            //look to see what toppings were selected
            if (tomato.isSelected()) {
                toppings.add("Tomato");
            }
            if (lettuce.isSelected()) {
                toppings.add("Lettuce");
            }
            if (onion.isSelected()) {
                toppings.add("Onion");
            }
            if (cheese.isSelected()) {
                toppings.add("Cheese");
            }
            if (peppers.isSelected()) {
                toppings.add("Peppers");
            }
            if (!toppings.isEmpty()) sandwichPlus.setToppings(toppings);

            //add sandwich to curr sandwich array
            currSandwiches.add(sandwichPlus);
        }
        else {
            //normal sandwich
            Sandwich sandwich = new Sandwich();
            sandwich.setType(sandwichTypeForSave);
            sandwich.setHasBacon(bacon.isSelected());
            sandwich.setToasted(toasted.isSelected());
            if (Objects.equals(sizes.getSelectedItem(), "6 inch")) {
                sandwich.setPrice(6);
                sandwich.setSize((byte) 6);
            } else if (Objects.equals(sizes.getSelectedItem(), "12 inch")) {
                sandwich.setPrice(10);
                sandwich.setSize((byte) 12);
            }
            //create linked list of toppings
            LinkedList<String> toppings = new LinkedList<>();
            //add selected toppings
            if (tomato.isSelected()) {
                toppings.add("Tomato");
            }
            if (lettuce.isSelected()) {
                toppings.add("Lettuce");
            }
            if (onion.isSelected()) {
                toppings.add("Onion");
            }
            if (cheese.isSelected()) {
                toppings.add("Cheese");
            }
            if (peppers.isSelected()) {
                toppings.add("Peppers");
            }

            if (!toppings.isEmpty()) sandwich.setToppings(toppings);

            //add sandwich to curr sandwich array
            currSandwiches.add(sandwich);
        }
    }

    /**
     * adds drink Object to arraylist (all that were checked)
     */

    //create drink object to add to arraylist
    private void addDrink() {
        //check to see which drinks were selected, make an object for that drink and add it to drink array
        if (pepsi.isSelected()) {
            Drink drink = new Drink();
            drink.setType("Pepsi");
            drink.setPrice(2);
            currDrinks.add(drink);
        }
        if (coke.isSelected()) {
            Drink drink = new Drink();
            drink.setType("Coke");
            drink.setPrice(2);
            currDrinks.add(drink);
        }
        if (sevenUp.isSelected()) {
            Drink drink = new Drink();
            drink.setType("7-Up");
            drink.setPrice(2);
            currDrinks.add(drink);
        }
        if (drPepper.isSelected()) {
            Drink drink = new Drink();
            drink.setType("Dr.Pepper");
            drink.setPrice(2);
            currDrinks.add(drink);
        }
        if (sprite.isSelected()) {
            Drink drink = new Drink();
            drink.setType("Sprite");
            drink.setPrice(2);
            currDrinks.add(drink);
        }
    }

    /**
     * Resets the topping checkboxes to false
     */

    private void clearToppings() {
        if (tomato == null) return; //just checking the first one declared (if this exists the others will too)
        tomato.setSelected(false);
        lettuce.setSelected(false);
        onion.setSelected(false);
        cheese.setSelected(false);
        peppers.setSelected(false);
        upgraded.setSelected(false);
        toy.setSelected(false);
        bacon.setSelected(false);
        toasted.setSelected(false);
    }

}
