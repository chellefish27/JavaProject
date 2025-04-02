import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Creates graphical user interface for the sandwich tracker using {@link JFrame}
 * @see java.awt.event.ActionListener
 * @see javax.swing.event.TableModelListener
 * @author Owen Reid
 *
 */

/*
    Date : 2025-03-26

    This Class creates the graphical interface for the "Sandwich Tracker" using the javax swing library
    It extends JFrame which allows itself to be referenced as the frame. For ease of creation many different
    parts will be put onto JPanels which will then be placed on to the frame as needed.
*/


public class SandwichFrame extends JFrame implements ActionListener, TableModelListener {
    //normal variables
    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 800;
    private Customer selectedCustomer;

    //declaring panels and objects (such as labels etc.) that will be used
    private JPanel customerDataPanel, orderingPanel;

    //Customer Data Panel components
    private final JTable customerTable, pastOrderTable;
    private JLabel searchByEmail, sandwichType, sandwichSize, sandwichHasBacon, sandwichToasted, sandwichToppings, toysIncluded, logoLabel;
    private final JScrollPane customerScroll, pastOrderScrollPane, orderSummaryScrollPane, rightScroll;
    private final JTextField searchEmail;
    private final JComboBox<String> emailEndings;
    private final DefaultTableModel orderModelTable, modelTable;
    private final JButton customerDataButton = new JButton("Customer Data"), createOrderButton = new JButton("Create Order");
    private BufferedImage logo, icon;
    private Timer timer;

    /**
     *
     * Constructor for Sandwich Frame - responsible for creating initial instance of the frame
     *
     */

    public SandwichFrame() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Joaquin's Filipino Sandwich Shop");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //Main.writeCustomers(Main.customers);
                closeWindow();
            }
        });
        //initialize Icon Image - I could use a throws declaration, but then the frame wouldn't open if there was an exception
        try {
            icon = ImageIO.read(new File("Sandwich Icon.png"));
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
        if (logo != null) {
            logoLabel = new JLabel(new ImageIcon(logo));
            logoLabel.setBounds(25, 0, 150, 150);
        }

        //Adding stuff to the customerDataPanel

        //Responsible for adding the "Customer Data" button to the side panel

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
            }

            /**
             * Un-highlights the button when the mouse is no longer hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseExited(MouseEvent e) {
                customerDataButton.setContentAreaFilled(false);
                customerDataButton.setOpaque(false);
            }
        });
        customerDataButton.addActionListener(this);

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
            }

            /**
             * Un-highlights the button when the mouse is no longer hovering over it
             * @param e the event to be processed
             */

            @Override
            public void mouseExited(MouseEvent e) {
                createOrderButton.setContentAreaFilled(false);
                createOrderButton.setOpaque(false);
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
             * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int) 
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
                int row = customerTable.rowAtPoint(e.getPoint());

                Customer customer = (Customer) customerTable.getValueAt(row, 0);
                String email = (String) customerTable.getValueAt(row, 1);

                selectedCustomer = customer;
                changePastOrders();
            }
        });

        //initialize scrollPane and add table to it
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
                //System.out.println("changedUpdate");
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                Customer[] customerArr = Main.sortEmails(searchEmail.getText());
                updateTable(customerArr);

            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                Customer[] customerArr = Main.sortEmails(searchEmail.getText());
                updateTable(customerArr);

            }
            
        });


        //Email Ending Box
        String[] endings = {"@gmail.com", "@outlook.com", "@yahoo.com", "Other"};
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
            pastOrderData[i][1] = savedOrders.get(i).calcTotal();
            pastOrderData[i][2] = savedOrders.get(i).getDate();
        }
        orderModelTable = new DefaultTableModel(pastOrderData, pastOrderCol) {
            //already java given java doc above
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
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
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.decode("#C2C5BB"));
                g.fillRect(0, 0, 533, 741);
            }
        };
        rightPanel.setLayout(null);
        rightPanel.setPreferredSize(new Dimension(532, 2500));

        rightPanel.add(customerScroll);
        rightPanel.add(pastOrderScrollPane);
        rightPanel.add(orderSummaryScrollPane);
        rightPanel.add(searchByEmail);
        rightPanel.add(searchEmail);
        rightPanel.add(emailEndings);


        rightScroll = new JScrollPane(rightPanel);
        rightScroll.setBounds(230, 10, 532, 740);
        rightScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        rightScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setBorder(BorderFactory.createEmptyBorder());



        //add Elements to customerDataPanel
        //customerDataPanel.add(customerScroll);
        //customerDataPanel.add(searchByEmail);
        //customerDataPanel.add(searchEmail);
        //customerDataPanel.add(emailEndings);
        //customerDataPanel.add(pastOrderScrollPane);
        //customerDataPanel.add(orderSummaryScrollPane);
        customerDataPanel.add(customerDataButton);
        customerDataPanel.add(createOrderButton);
        customerDataPanel.add(rightScroll);
        if (logoLabel != null) customerDataPanel.add(logoLabel);

        //add customerData panel to Frame and show (Customer data panel is default window)
        this.add(customerDataPanel);
        this.setVisible(true);


    }

    //Overrides tableChanged method
    @Override
    public void tableChanged(TableModelEvent e) {

    }

    /**
     * Used to add functionality to items in the GUI
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == emailEndings) {
            String emailEnding = (Objects.equals(emailEndings.getSelectedItem(), "Other")) ? "" : (String) emailEndings.getSelectedItem();
            System.out.println(emailEnding);
        }
        else if (e.getSource() == createOrderButton) {
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightScroll.getVerticalScrollBar().setValue(rightScroll.getVerticalScrollBar().getValue()+10);

                    if (rightScroll.getVerticalScrollBar().getValue() == 1000) {
                        timer.stop();
                    }
                }
            });
            timer.start();
        }
        else if (e.getSource() == customerDataButton) {
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightScroll.getVerticalScrollBar().setValue(rightScroll.getVerticalScrollBar().getValue()-10);

                    if (rightScroll.getVerticalScrollBar().getValue() == 0) {
                        timer.stop();
                    }
                }
            });
            timer.start();
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
            orderModelTable.addRow(new Object[] {pastOrders.get(i), pastOrders.get(i).calcTotal(), pastOrders.get(i).getDate()});
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
        newSummaryPanel.setPreferredSize(new Dimension(450, 300*sandwiches.size() + 200*drinks.size()));


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

            JLabel sandwichSize = new JLabel("Sandwich Size: " + sandwiches.get(i).getSize());
            sandwichSize.setBounds(25, 50 + 50*(i) + 250*i, 450, 30);
            sandwichSize.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichSize);

            JLabel sandwichHasBacon = new JLabel("Sandwich Has Bacon: " + ((sandwiches.get(i).hasBacon()) ? "Yes" : "No"));
            sandwichHasBacon.setBounds(25, 100 + 50*i + 250*i, 450, 30);
            sandwichHasBacon.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichHasBacon);

            JLabel sandwichToasted = new JLabel("Sandwich is Toasted: " + ((sandwiches.get(i).isToasted()) ? "Yes" : "No"));
            sandwichToasted.setBounds(25, 150 + 50*i + 250*i, 450, 30);
            sandwichToasted.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichToasted);

            //makes a string that contains all the toppings separated by commas
            StringBuilder toppings = new StringBuilder();
            for (String s : sandwiches.get(i).getToppings()) {
                toppings.append(s).append(", ");
            }
            toppings = new StringBuilder(toppings.substring(0, toppings.length() - 2));

            JLabel sandwichToppings = new JLabel("Sandwich Toppings: " + toppings);
            sandwichToppings.setBounds(25, 200 + 50*i + 250*i, 450, 30);
            sandwichToppings.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(sandwichToppings);

            JLabel toyIncluded = new JLabel("Toy Included: " + sandwiches.get(i).getToy());
            toyIncluded.setBounds(25, 250 + 50*i + 250*i, 450, 30);
            toyIncluded.setFont(new Font("Arial", Font.PLAIN, 15));
            newSummaryPanel.add(toyIncluded);

        }
        orderSummaryScrollPane.getViewport().removeAll();
        orderSummaryScrollPane.getViewport().add(newSummaryPanel);
    }

    /**
     * updates the customer table based on the given array
     * @param customerList
     */
    public void updateTable(Customer[] customerList) {

        for (int i = modelTable.getRowCount()-1; i >= 0; i--) {
            modelTable.removeRow(i);
        }
        for (Customer c : customerList) {
            modelTable.addRow(new Object[] {c, c.getEmail()});
            //System.out.println(c);
        }
    }
}
