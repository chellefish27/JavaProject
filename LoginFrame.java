import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Base64;

/**
 * Uses JFrame to create a login window - only has one login credentials (just wanted to make this for fun)
 * @author Owen Reid
 * @see JFrame
 * @see ActionListener
 *
 */

public class LoginFrame extends JFrame implements ActionListener {
    private String username;
    private transient String password; //transient so Java doesn't serialize
    private String encryptedPassword;
    private byte[] passwordBytes;
    private String storeID;

    private JTextField usernameField, storeIDField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame (boolean blank) {

    }

    /**
     * Creates the login Frame
     */
    public LoginFrame () {
        //sets up frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 350);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Login Page");

        decodePassword(); //gets password from text file

        //makes username field
        usernameField = new JTextField("Username");
        usernameField.setBounds(30, 30, 200, 30);
        this.add(usernameField);

        //makes store ID field
        storeIDField = new JTextField("StoreID");
        storeIDField.setBounds(30, 110, 200, 30);
        this.add(storeIDField);

        //makes password field
        passwordField = new JPasswordField("Password");
        passwordField.setBounds(30, 70, 200, 30);
        this.add(passwordField);

        //login button
        loginButton = new JButton("Login");
        loginButton.setBounds(90, 160, 100, 40);
        loginButton.addActionListener(this);
        this.add(loginButton);

        this.setVisible(true);
    }

    /**
     * Activates when the button is pressed
     * @param e the event to be processed
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        //when login button pressed, get password from pass field (returns char array for some reason)
        char[] enteredPass = passwordField.getPassword();
        String passToCompare = new String(enteredPass); //turn it to string
        //compare entered password to stored password, and username with stored username
        if (passToCompare.equals(password) && usernameField.getText().equals(username)) {
            Order.setStoreID(storeIDField.getText());
            SwingUtilities.invokeLater(SandwichFrame::new);
            this.dispose();
        }
    }

    /**
     * Encodes the password before saving it to the text file - using Base64
     * @param password the password to be encoded
     */

    public void encodePassword(String password) {
        this.password = password;
        //converts password to array of bytes
        passwordBytes = this.password.getBytes();
        //uses base64 encoder to encode
        encryptedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        try {
            //writes login info to text file
            BufferedWriter writer = new BufferedWriter(new FileWriter("loginInfo.txt"));
            writer.write(username + ";" + encryptedPassword);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("ERROR WRITING FILE");
        }
    }

    /**
     * decodes the password so it can be compared with the inputted password
     */
    private void decodePassword() {
        try {
            //gets login info from text file
            BufferedReader reader = new BufferedReader(new FileReader("loginInfo.txt"));
            String[] split = reader.readLine().split(";");

            //sets username and encoded password to respective variables
            username = split[0];
            encryptedPassword = split[1];
            reader.close();
        }
        catch(IOException e) {
            System.err.println("ERROR READING FILE");
        }
        //converts encoded password to array of bytes
        byte[] passwordBytes = Base64.getDecoder().decode(encryptedPassword);
        //converts array of bytes back to string
        password = new String(passwordBytes);
    }

    /**
     * Sets the username of loginFrame object (will write it to text file when saved)
     * @param username username to set
     */
    public void setUser (String username) {
        this.username = username;
    }
}
