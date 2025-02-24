import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class RegisterFrame extends JFrame implements ActionListener {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnRegister;

    public RegisterFrame() {
        setTitle("Registro - Reseñas de Películas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Establece un borde vacío alrededor del panel principal para crear margen.
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Usuario
        JLabel lblUsername = new JLabel("Usuario:");
        tfUsername = new JTextField(20);
        tfUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, tfUsername.getPreferredSize().height));
        panel.add(lblUsername);
        panel.add(tfUsername);

        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        pfPassword = new JPasswordField(20);
        pfPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, pfPassword.getPreferredSize().height));
        panel.add(lblPassword);
        panel.add(pfPassword);

        return panel;
    }

    private JPanel createButtonPanel() {
        // Panel para el botón que usa FlowLayout para centrar el botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegister = new JButton("Registrar");
        btnRegister.addActionListener(this);
        buttonPanel.add(btnRegister);

        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            performRegistration();
        }
    }

    private void performRegistration() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        // Validación básica
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario y la contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si el nombre de usuario ya existe
        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

            // Crear el nuevo usuario
            User newUser = new User(username, User.encryptPassword(password));

        try {
            // Guardar el usuario en la base de datos
            Database.saveUser(newUser);
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

         // Cerrar la ventana de registro y volver a la de inicio de sesión
            this.dispose();
            new LoginFrame().setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean isUsernameTaken(String username) {
        List<User> users = Database.loadUsers();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

}
