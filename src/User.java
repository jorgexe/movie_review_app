import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Review> reviews;
    private List<PeliculaPendiente> peliculasPendientes;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.reviews = new ArrayList<>();
        this.peliculasPendientes = new ArrayList<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<PeliculaPendiente> getPeliculasPendientes() {
        return peliculasPendientes;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void addPeliculaPendiente(PeliculaPendiente pelicula) {
        peliculasPendientes.add(pelicula);
    }

    public static String encryptPassword(String password) {
        return password; 
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // removePeliculaPendiente da erro para iniciar sesion
    public void removePeliculaPendiente(PeliculaPendiente pelicula) {
        peliculasPendientes.remove(pelicula);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
