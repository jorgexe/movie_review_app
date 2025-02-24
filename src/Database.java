import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String USERS_FILE = "users.dat";
    private static final String REVIEWS_FILE = "reviews.dat";

    // Guardar usuario
    public static void saveUser(User user) throws IOException {
        List<User> users = loadUsers();
        // Eliminar el usuario antiguo si existe
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        users.add(user); // Añadir el usuario actualizado
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        }
    }

    // Cargar usuarios
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, retornar lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Guardar reseña
    public static void saveReview(Review review) throws IOException {
        List<Review> reviews = loadReviews();
        reviews.add(review);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REVIEWS_FILE))) {
            oos.writeObject(reviews);
        }
    }

    // Cargar reseñas
    public static List<Review> loadReviews() {
        List<Review> reviews = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REVIEWS_FILE))) {
            reviews = (List<Review>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, retornar lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Validar inicio de sesión
    public static User validateLogin(String username, String password) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public static void saveReviews(List<Review> reviews) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REVIEWS_FILE))) {
            oos.writeObject(reviews);
        } catch (IOException e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
        }
    }

    public static void deleteReview(Review reviewToDelete) {
        List<Review> reviews = loadReviews(); // Cargar las reseñas existentes
        reviews.removeIf(review -> review.equals(reviewToDelete)); // Eliminar la reseña que coincide
        saveReviews(reviews); // Guardar la lista actualizada de reseñas
    }         

    private static final String PELICULAS_PENDIENTES_FILE = "peliculasPendientes.dat";

    public static void savePeliculasPendientes(List<PeliculaPendiente> peliculas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PELICULAS_PENDIENTES_FILE))) {
            oos.writeObject(peliculas);
        } catch (IOException e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
        }
    }

    public static List<PeliculaPendiente> loadPeliculasPendientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PELICULAS_PENDIENTES_FILE))) {
            return (List<PeliculaPendiente>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si no existe el archivo, retornar una lista vacía
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar adecuadamente la excepción
            return new ArrayList<>();
        }
    }
}
