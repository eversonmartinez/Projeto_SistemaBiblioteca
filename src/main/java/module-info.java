module com.projeto_sistemabiblioteca {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.projeto_sistemabiblioteca to javafx.fxml;
    exports com.projeto_sistemabiblioteca;
}