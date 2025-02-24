import javax.swing.*;
import java.awt.*;

public class ReviewDetailsDialog extends JDialog {
    public ReviewDetailsDialog(Frame parent, Review review) {
        super(parent, "Detalles de la Reseña", true);
        setSize(400, 300);

        setLayout(new BorderLayout());
        add(createDetailsPanel(review), BorderLayout.CENTER);
    }

    private JPanel createDetailsPanel(Review review) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Película: " + review.getMovieName()));
        panel.add(new JLabel("Año: " + review.getYear()));
        panel.add(new JLabel("Categoría: " + review.getCategory()));
        panel.add(new JLabel("Duración: " + review.getDuration() + " minutos"));
        panel.add(new JLabel("Calificación: " + review.getRating() + "/5"));
        panel.add(new JLabel("Fecha de Reseña: " + review.getReviewDate().toString()));

        JTextArea taReviewText = new JTextArea(5, 20);
        taReviewText.setText(review.getReviewText());
        taReviewText.setWrapStyleWord(true);
        taReviewText.setLineWrap(true);
        taReviewText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taReviewText);
        panel.add(scrollPane);

        return panel;
    }
}

