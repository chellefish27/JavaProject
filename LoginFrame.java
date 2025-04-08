import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Base64;

/**
 * Uses JFrame to create a login window
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
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 350);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Login Page");

        decodePassword();

        usernameField = new JTextField("Username");
        usernameField.setBounds(30, 30, 200, 30);
        this.add(usernameField);

        storeIDField = new JTextField("StoreID");
        storeIDField.setBounds(30, 110, 200, 30);
        this.add(storeIDField);

        passwordField = new JPasswordField("Password");
        passwordField.setBounds(30, 70, 200, 30);
        this.add(passwordField);

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
        char[] enteredPass = passwordField.getPassword();
        String passToCompare = new String(enteredPass);
        if (passToCompare.equals(password) && usernameField.getText().equals(username)) {
            Order.setStoreID(storeIDField.getText());
            SwingUtilities.invokeLater(SandwichFrame::new);
            this.dispose();
        }
        else {
            System.out.println(passToCompare + " " + password);
        }
    }

    /**
     * Encodes the password before saving it to the text file
     * @param password the password to be encoded
     */

    public void encodePassword(String password) {
        this.password = password;
        passwordBytes = this.password.getBytes();
        encryptedPassword = Base64.getEncoder().encodeToString(passwordBytes);

        try {
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
            BufferedReader reader = new BufferedReader(new FileReader("loginInfo.txt"));
            String[] split = reader.readLine().split(";");

            username = split[0];
            encryptedPassword = split[1];
            reader.close();
        }
        catch(IOException e) {
            System.err.println("ERROR READING FILE");
        }

        byte[] passwordBytes = Base64.getDecoder().decode(encryptedPassword);
        password = new String(passwordBytes);
    }
    public void setUser (String username) {
        this.username = username;
    }
}
