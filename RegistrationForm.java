import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.*;

class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters (if needed)
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

public class RegistrationForm extends JFrame implements ActionListener {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JTextArea messageArea;

    public RegistrationForm() {
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Register button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        add(registerButton, gbc);

        // Message area
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        add(new JScrollPane(messageArea), gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        StringBuilder errors = new StringBuilder();

        // Validate name
        if (name.isEmpty() || !name.matches("[A-Za-z ]+")) {
            errors.append("Name must contain only letters and spaces.\n");
        }

        // Validate email
        if (!isValidEmail(email)) {
            errors.append("Invalid email format.\n");
        }

        // Validate password
        if (!isValidPassword(password)) {
            errors.append("Password must be at least 6 characters and contain at least one letter and one number.\n");
        }

        if (errors.length() > 0) {
            messageArea.setForeground(Color.RED);
            messageArea.setText(errors.toString());
        } else {
            User user = new User(name, email, password);
            messageArea.setForeground(Color.GREEN);
            messageArea.setText("Registration successful!\nWelcome, " + user.getName());
            clearFields();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) return false;
        boolean hasLetter = password.matches(".*[A-Za-z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        return hasLetter && hasNumber;
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
