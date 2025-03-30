import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  @author Owen Reid
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
    private final int FRAME_WIDTH = 1080;
    private final int FRAME_HEIGHT = 800;
    private Customer selectedCustomer;

    //declaring panels and objects (such as labels etc.) that will be used
    private JPanel customerDataPanel, orderingPanel;

    //Customer Data Panel components
    private JTable customerTable, pastOrderTable;
    private JLabel searchByEmail, sandwichType, sandwichSize, sandwichHasBacon, sandwichToasted, sandwichToppings, toysIncluded;
    private JScrollPane customerScroll, pastOrderScrollPane, orderSummaryScrollPane;
    private JTextField searchEmail;
    private JComboBox<String> emailEndings;
    private DefaultTableModel orderModelTable;

    //Constructor for the Frame class, creates the frame when a new frame object is made
    public SandwichFrame() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //call save function where when implemented
                closeWindow();
            }
        });

        //initialize customerDataPanel
        customerDataPanel = new JPanel();
        customerDataPanel.setLayout(null);
        customerDataPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        //Adding stuff to the customerDataPanel

        //initialize customer table

        String[] colNames = {"Customer Name", "Email"};
        Object[][] tableData = new Object[Main.customers.size()][2];

        for (int i = 0; i < Main.customers.size(); i++) {
            tableData[i][0] = Main.customers.get(i);
            tableData[i][1] = Main.customers.get(i).getEmail();
        }

        DefaultTableModel modelTable = new DefaultTableModel(tableData, colNames) {
            //Override modelTable's isCellEditable function to control edit ability
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        customerTable = new JTable(modelTable);
        /*
            Allows for table to be clickable, overriding this way because easier to keep track of for multiple tables, and
            more type safe because I know a customer will always be in the 0th column and String in other column
         */
        customerTable.addMouseListener(new MouseAdapter() {
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
        customerScroll.setBounds(0, 100, 500, 350);

        //Initialize search by email label
        searchByEmail = new JLabel("Search By Email");
        searchByEmail.setBounds(0, 15, 250, 25);
        searchByEmail.setFont(new Font("Arial" ,Font.PLAIN, 20));

        //Initialize searchEmail text field
        searchEmail = new JTextField();
        searchEmail.setBounds(0, 45, 350, 50);
        searchEmail.setFont(new Font("Arial", Font.PLAIN, 18));

        //Email Ending Box
        String[] endings = {"@gmail.com", "@outlook.com", "@yahoo.com", "Other"};
        emailEndings = new JComboBox<>(endings);
        emailEndings.setBounds(350, 45, 150, 50);
        emailEndings.setFont(new Font("Arial", Font.PLAIN, 18));
        emailEndings.setBackground(Color.white);
        emailEndings.addActionListener(this);

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
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        pastOrderTable = new JTable(orderModelTable);

        //adds mouse detection for the table
        pastOrderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = pastOrderTable.rowAtPoint(e.getPoint());
                //calls the method to change the values in the extended information area
                updateSummary((Order) pastOrderTable.getValueAt(row, 0));
            }
        });


        //initialize past order scroll pane
        pastOrderScrollPane = new JScrollPane(pastOrderTable);
        pastOrderScrollPane.setBounds(550, 100, 500, 350);

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

        //add labels to panel
        summaryPanel.add(sandwichType);
        summaryPanel.add(sandwichSize);
        summaryPanel.add(sandwichHasBacon);
        summaryPanel.add(sandwichToasted);
        summaryPanel.add(sandwichToppings);

        //add JPanel to Summary Panel
        orderSummaryScrollPane = new JScrollPane(summaryPanel);
        orderSummaryScrollPane.setBounds(550, 475, 500, 275);


        //add Elements to customerDataPanel
        customerDataPanel.add(customerScroll);
        customerDataPanel.add(searchByEmail);
        customerDataPanel.add(searchEmail);
        customerDataPanel.add(emailEndings);
        customerDataPanel.add(pastOrderScrollPane);
        customerDataPanel.add(orderSummaryScrollPane);


        //add customerData panel to Frame and show (Customer data panel is default window)
        this.add(customerDataPanel);
        this.setVisible(true);


    }

    //Overrides tableChanged method
    @Override
    public void tableChanged(TableModelEvent e) {

    }

    //Overrides actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == emailEndings) {
            String emailEnding = (Objects.equals(emailEndings.getSelectedItem(), "Other")) ? "" : (String) emailEndings.getSelectedItem();
            System.out.println(emailEnding);
        }
    }

    //ran when window closes, closes Frame then terminates the program
    private void closeWindow() {
        this.dispose();
        System.exit(0);
    }

    //refreshes the past order table with the selected customers' past order data
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

        }
        orderSummaryScrollPane.getViewport().removeAll();
        orderSummaryScrollPane.getViewport().add(newSummaryPanel);
    }
}