import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginFrame() {
        // Configuración inicial del frame
        setTitle("Inicio de Sesión - Reseñas de Películas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        // Configurar layout
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Etiquetas y campos de texto
        panel.add(new JLabel("Usuario:"));
        tfUsername = new JTextField(20);
        panel.add(tfUsername);

        panel.add(new JLabel("Contraseña:"));
        pfPassword = new JPasswordField(20);
        panel.add(pfPassword);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Botones
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(this);
        bottomPanel.add(btnLogin);

        btnRegister = new JButton("Registrar");
        btnRegister.addActionListener(this);
        bottomPanel.add(btnRegister);

        return bottomPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            login();
        } else if (e.getSource() == btnRegister) {
            register();
        }
    }

    private void login() {
        String username = tfUsername.getText();
        String password = new String(pfPassword.getPassword());

        // Lógica para verificar las credenciales y obtener el usuario
        User authenticatedUser = Database.validateLogin(username, password);
        if (authenticatedUser != null) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Cerrar la ventana de inicio de sesión

            // Cambiar a la ventana principal de la aplicación, pasando el usuario autenticado
            MovieReviewFrame mainFrame = new MovieReviewFrame(authenticatedUser);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        // Abrir la ventana de registro o manejar la creación de una nueva cuenta
        this.dispose();
        // Abrir la ventana de registro
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }
}
