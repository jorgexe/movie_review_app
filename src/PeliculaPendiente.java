import java.io.Serializable;

public class PeliculaPendiente implements Serializable{
    private String titulo;

    public PeliculaPendiente(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo; // Solo se muestra el título en la lista de la UI
    }
}
