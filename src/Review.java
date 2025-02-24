import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Review implements Serializable {
    private String movieName;
    private int year; // Año de la película
    private String category; // Género o categoría de la película
    private int duration; // Duración de la película en minutos
    private int rating; // Calificación de 1 a 5
    private String reviewText; // Texto de la reseña
    private Date reviewDate; // Fecha en que se realizó la reseña

    public Review(String movieName, int year, String category, int duration, int rating, String reviewText, Date reviewDate) {
        this.movieName = movieName;
        this.year = year;
        this.category = category;
        this.duration = duration;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    // Getters
    public String getMovieName() {
        return movieName;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    // Setters
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return movieName + " (" + year + ")";
    }

    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Review review = (Review) o;
    return Objects.equals(movieName, review.movieName)
        && Objects.equals(reviewText, review.reviewText)
        ;
    }

    @Override
    public int hashCode() {
    return Objects.hash(movieName, reviewText);
    }
    
    
}

