package circolo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty nome;
    private StringProperty password;
    private SimpleIntegerProperty tipo;

    public User() {
    }

    //getter and setters

    public String getNome() {
        return nome.get();
    }
    public String getPassword() {
        return password.get();
    }
    public int getTipo() {
        return tipo.get();
    }

    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }
    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }
    public void setTipo(int tipo) {
        this.tipo = new SimpleIntegerProperty(tipo);
    }

    public StringProperty getNomeProperty() {
        return nome;
    }
    public StringProperty getPasswordProperty() {
        return password;
    }
    public IntegerProperty getTipoProperty() {
        return tipo;
    }
}
