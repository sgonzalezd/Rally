package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controladores.*;

public class Aplicacion extends Application {
    private Scene scn_Josephus;
    private Scene scn_Menu;
    private Stage stage;
    private Scene scn_Cafetera;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        try {
            var instream = getClass().getClassLoader().getResourceAsStream("layouts/JosephusVentana.fxml");
            FXMLLoader loader = new FXMLLoader();
            JosephusControlador controlador = new JosephusControlador(this);
            loader.setController(controlador);
            scn_Josephus = new Scene(loader.load(instream));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            var instream = getClass().getClassLoader().getResourceAsStream("layouts/MenuPrincipal.fxml");
            FXMLLoader loader = new FXMLLoader();
            MenuControlador controlador = new MenuControlador(this);
            loader.setController(controlador);
            scn_Menu = new Scene(loader.load(instream));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            var instream = getClass().getClassLoader().getResourceAsStream("layouts/CafeteraVentana.fxml");
            FXMLLoader loader = new FXMLLoader();
            CafeteraControlador controlador = new CafeteraControlador(this);
            loader.setController(controlador);
            scn_Cafetera = new Scene(loader.load(instream));
        } catch (Exception e) {
            System.out.println(e);
        }

        stage.setResizable(true);
        stage.setScene(scn_Menu);
        stage.sizeToScene();
        stage.setResizable(false);

        stage.show();
    }

    public void cambiarJosephus() {
        stage.setResizable(true);
        stage.setScene(scn_Josephus);
        stage.sizeToScene();
        stage.setResizable(false);
    }

    public void cambiarMenu() {
        stage.setResizable(true);
        stage.setScene(scn_Menu);
        stage.sizeToScene();
        stage.setResizable(false);
    }

    public void cambiarCafetera() {
        stage.setResizable(true);
        stage.setScene(scn_Cafetera);
        stage.sizeToScene();
        stage.setResizable(false);
    }
}