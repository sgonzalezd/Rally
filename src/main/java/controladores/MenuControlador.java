package controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Aplicacion;

public class MenuControlador {
    @FXML
    private Button btn_josephus;
    @FXML
    private Button btn_cafetera;

    private Aplicacion ventana;

    public MenuControlador(Aplicacion ventana){
        this.ventana = ventana;
    }

    public void initialize() {
        btn_josephus.setOnAction(e -> {
            ventana.cambiarJosephus();
        });
        btn_cafetera.setOnAction(e -> {
            ventana.cambiarCafetera();
        });
    }
}