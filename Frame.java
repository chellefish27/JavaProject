import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/*
    Author : Owen Reid
    Date : 2025-03-26

    This Class creates the graphical interface for the "Sandwich Tracker" using the javax swing library
    It extends JFrame which allows itself to be referenced as the frame. For ease of creation many different
    parts will be put onto JPanels which will then be placed on to the frame as needed.

 */

public class Frame extends JFrame implements ActionListener, TableModelListener {
    //normal variables
    private final int FRAME_WIDTH = 1080;
    private final int FRAME_HEIGHT = 800;
    private Customer selectedCustomer;

    //declaring panels and objects (such as labels etc.) that will be used
    private JPanel customerDataPanel, orderingPanel;

    //Customer Data Panel components
    private JTable customerTable, pastOrderTable;
    private JLabel searchByEmail;
    private JScrollPane customerScroll, pastOrderScrollPane;
    private JTextField searchEmail;
    private JComboBox<String> emailEndings;
    private DefaultTableModel orderModelTable;

    //Constructor for the Frame class, creates the frame when a new frame object is made
    public Frame() {
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
        String[] endings = {"@gmail.com", "@outlook.com", "@yahoo.com"};
        emailEndings = new JComboBox<>(endings);
        emailEndings.setBounds(350, 45, 150, 50);
        emailEndings.setFont(new Font("Arial", Font.PLAIN, 18));
        emailEndings.setBackground(Color.white);
        emailEndings.addActionListener(this);

        //initialize Past Orders Table
        String[] pastOrderCol = {"Item", "Cost", "Date of Purchase"};
        ArrayList<String> savedOrders = (selectedCustomer != null) ? selectedCustomer.getPastOrders() : new ArrayList<>();
        //will change to object array to be able to use Order object when ready
        Object[][] pastOrderData = new Object[savedOrders.size()][3];

        for (int i = 0; i < savedOrders.size(); i++) {
            //for now just works by using string input, will probably change to take in values from order class
            // --> also will be able to get more data when clicked (using order object in first slot)
            pastOrderData[i] = savedOrders.get(i).split(",");
        }
        orderModelTable = new DefaultTableModel(pastOrderData, pastOrderCol) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        pastOrderTable = new JTable(orderModelTable);


        //initialize past order scroll pane
        pastOrderScrollPane = new JScrollPane(pastOrderTable);
        pastOrderScrollPane.setBounds(550, 100, 500, 350);


        //add Elements to customerDataPanel
        customerDataPanel.add(customerScroll);
        customerDataPanel.add(searchByEmail);
        customerDataPanel.add(searchEmail);
        customerDataPanel.add(emailEndings);
        customerDataPanel.add(pastOrderScrollPane);


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
            System.out.println(emailEndings.getSelectedItem());
        }
    }
    //ran when window closes, closes Frame then terminates the program
    public void closeWindow() {
        this.dispose();
        System.exit(0);
    }

    public void changePastOrders() {
        //check to make sure selectedCustomer exists
        if (selectedCustomer == null) return;

        //remove all current rows
        for (int i = orderModelTable.getRowCount()-1; i >= 0; i--) {
            orderModelTable.removeRow(i);
        }

        //replace rows with selected customer past orders
        for (int i = 0; i < selectedCustomer.getPastOrders().size(); i++) {
            Object[] data = selectedCustomer.getPastOrders().get(i).split(",");
            orderModelTable.addRow(new Object[] {data[0], data[1], data[2]});
        }
    }
}
