import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MovieReviewFrame extends JFrame {
    private User currentUser;
    private JList<Review> reviewList;
    private DefaultListModel<Review> reviewListModel;
    private JButton btnAddReview, btnViewReview, btnLogOut, btnDeleteReview;
    private DefaultListModel<PeliculaPendiente> modeloPeliculasPendientes;
    private JList<PeliculaPendiente> listaPeliculasPendientes;
    private JButton btnAgregarPeliculaPendiente, btnEliminarPeliculaPendiente;

    public MovieReviewFrame(User user) {
        this.currentUser = user;
        
        setTitle("Reseñas de Películas - Usuario: " + currentUser.getUsername());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Inicializar modelos de lista
        reviewListModel = new DefaultListModel<>();
        modeloPeliculasPendientes = new DefaultListModel<>();
        listaPeliculasPendientes = new JList<>(modeloPeliculasPendientes);

        // Crear y configurar botones para películas pendientes ANTES de configurar los paneles
        btnAgregarPeliculaPendiente = new JButton("Agregar Película Pendiente");
        btnAgregarPeliculaPendiente.addActionListener(e -> agregarPeliculaPendiente());
        btnEliminarPeliculaPendiente = new JButton("Eliminar Película Vista");
        btnEliminarPeliculaPendiente.addActionListener(e -> eliminarPeliculaPendiente());

        // Configurar los componentes de la UI
        add(createReviewListPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        // Configurar y agregar la lista y botones de películas pendientes
        configuraPeliculasPendientes();

        // Cargar datos del usuario
        loadUserReviews();
        loadUserPeliculasPendientes();
    }

    private JPanel createReviewListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        reviewListModel = new DefaultListModel<>();
        reviewList = new JList<>(reviewListModel);
        reviewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(reviewList);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Botón para agregar reseña
        btnAddReview = new JButton("Agregar Reseña");
        btnAddReview.addActionListener(e -> showAddReviewForm());
        buttonPanel.add(btnAddReview);

        // Botón para ver detalles de reseña
        btnViewReview = new JButton("Ver Detalles de Reseña");
        btnViewReview.addActionListener(e -> showReviewDetails());
        buttonPanel.add(btnViewReview);

        // Botón para borrar reseña
        btnDeleteReview = new JButton("Borrar Reseña");
        btnDeleteReview.addActionListener(e -> deleteSelectedReview());
        buttonPanel.add(btnDeleteReview);

        // Botón para cerrar sesión
        btnLogOut = new JButton("Cerrar Sesión");
        btnLogOut.addActionListener(e -> logoutAndReturnToLogin());
        buttonPanel.add(btnLogOut);

        return buttonPanel;
    }
    
    private void loadUserReviews() {
        reviewListModel.clear();
        for (Review review : currentUser.getReviews()) {
            reviewListModel.addElement(review);
        }
    }
    

    private void showAddReviewForm() {
    AddReviewForm addReviewForm = new AddReviewForm(this);
    addReviewForm.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            Review newReview = addReviewForm.getNewReview();
            if (newReview != null) {
                currentUser.addReview(newReview); // Agrega la nueva reseña al usuario
                reviewListModel.addElement(newReview); // Agrega la nueva reseña al modelo
                try {
                    Database.saveUser(currentUser); // Guarda el usuario con la nueva reseña
                } catch (IOException ex) {
                    ex.printStackTrace(); // Manejar la excepción de manera adecuada
                }
            }
        }
    });
    addReviewForm.setVisible(true);
}

     

    private void showReviewDetails() {
        Review selectedReview = reviewList.getSelectedValue();
        if (selectedReview == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una reseña para ver sus detalles.", "Ninguna Reseña Seleccionada", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ReviewDetailsDialog detailsDialog = new ReviewDetailsDialog(this, selectedReview);
        detailsDialog.setVisible(true);
    }

    private void logoutAndReturnToLogin() {
        // Cerrar la ventana actual
        this.dispose();

        // Abrir la ventana de inicio de sesión
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

    private void deleteSelectedReview() {
        Review selectedReview = reviewList.getSelectedValue();
        if (selectedReview != null) {
            int response = JOptionPane.showConfirmDialog(
                this, 
                "¿Estás seguro de que deseas borrar esta reseña?", 
                "Confirmar Borrado", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
    
            if (response == JOptionPane.YES_OPTION) {
                currentUser.getReviews().remove(selectedReview); // Eliminar de las reseñas del usuario
                reviewListModel.removeElement(selectedReview); // Eliminar de la lista en la interfaz de usuario
                try {
                    Database.saveUser(currentUser); // Guardar los cambios en el usuario
                } catch (IOException ex) {
                    ex.printStackTrace(); // Manejar la excepción de manera adecuada
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una reseña para borrar.", "Ninguna Reseña Seleccionada", JOptionPane.INFORMATION_MESSAGE);
        }
    }   

    private void loadUserPeliculasPendientes() {
        if (currentUser != null) {
            modeloPeliculasPendientes.clear();
            for (PeliculaPendiente pelicula : currentUser.getPeliculasPendientes()) {
                modeloPeliculasPendientes.addElement(pelicula);
            }
        }
    }
    
    private void configuraPeliculasPendientes() {
        // Panel para la lista de películas pendientes
        JScrollPane scrollPanePeliculas = new JScrollPane(listaPeliculasPendientes);
        
        // Panel para los botones de películas pendientes
        JPanel panelBotonesPeliculas = new JPanel();
        panelBotonesPeliculas.setLayout(new BoxLayout(panelBotonesPeliculas, BoxLayout.X_AXIS));
        panelBotonesPeliculas.add(btnAgregarPeliculaPendiente);
        panelBotonesPeliculas.add(btnEliminarPeliculaPendiente);

        // Panel que incluye tanto la lista como los botones
        JPanel panelPeliculas = new JPanel();
        panelPeliculas.setLayout(new BorderLayout());
        panelPeliculas.add(scrollPanePeliculas, BorderLayout.CENTER);
        panelPeliculas.add(panelBotonesPeliculas, BorderLayout.SOUTH);

        // Agregar el panel de películas pendientes al frame en la parte derecha (EAST)
        add(panelPeliculas, BorderLayout.EAST);
    }

    private void agregarPeliculaPendiente() {
        String titulo = JOptionPane.showInputDialog(this, "Ingresa el título de la película:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            PeliculaPendiente nuevaPelicula = new PeliculaPendiente(titulo.trim());
            currentUser.addPeliculaPendiente(nuevaPelicula);
            modeloPeliculasPendientes.addElement(nuevaPelicula);
            try {
                Database.saveUser(currentUser);
                listaPeliculasPendientes.revalidate();
                listaPeliculasPendientes.repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la película pendiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarPeliculaPendiente() {
        PeliculaPendiente peliculaSeleccionada = listaPeliculasPendientes.getSelectedValue();
        if (peliculaSeleccionada != null) {
            currentUser.removePeliculaPendiente(peliculaSeleccionada);
            modeloPeliculasPendientes.removeElement(peliculaSeleccionada);
            try {
                Database.saveUser(currentUser);
                listaPeliculasPendientes.revalidate();
                listaPeliculasPendientes.repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una película para eliminar.", "Ninguna Película Seleccionada", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
