import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddReviewForm extends JDialog {
    private JTextField tfMovieName;
    private JTextField tfYear;
    private JTextField tfCategory;
    private JTextField tfDuration;
    private JSpinner spnRating;
    private JTextArea taReviewText;
    private JButton btnSave;
    private JButton btnCancel;
    private Review newReview;

    public AddReviewForm(Frame parent) {
        super(parent, "Agregar Reseña", true);
        setSize(400, 500);

        setLayout(new BorderLayout());
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Nombre de Película:"));
        tfMovieName = new JTextField(20);
        panel.add(tfMovieName);

        panel.add(new JLabel("Año:"));
        tfYear = new JTextField(4);
        panel.add(tfYear);

        panel.add(new JLabel("Categoría:"));
        tfCategory = new JTextField(20);
        panel.add(tfCategory);

        panel.add(new JLabel("Duración (min):"));
        tfDuration = new JTextField(20);
        panel.add(tfDuration);

        panel.add(new JLabel("Calificación (1-5):"));
        spnRating = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        panel.add(spnRating);

        panel.add(new JLabel("Reseña:"));
        taReviewText = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(taReviewText);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        btnSave = new JButton("Guardar");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReview();
            }
        });
        panel.add(btnSave);

        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar el diálogo
            }
        });
        panel.add(btnCancel);

        return panel;
    }

    private void saveReview() {
        try {
            String movieName = tfMovieName.getText();
            int year = Integer.parseInt(tfYear.getText());
            String category = tfCategory.getText();
            int duration = Integer.parseInt(tfDuration.getText());
            int rating = (Integer) spnRating.getValue();
            String reviewText = taReviewText.getText();

            // Crear un objeto Review con los datos ingresados
            newReview = new Review(movieName, year, category, duration, rating, reviewText, new Date());

            // No guardamos la reseña en la base de datos aquí. En su lugar, se manejará en MovieReviewFrame.
            JOptionPane.showMessageDialog(this, "Reseña creada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar el diálogo después de crear la reseña
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa datos numéricos válidos para el año y la duración.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear la reseña: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener la nueva reseña
    public Review getNewReview() {
        return newReview;
    }
    
}
