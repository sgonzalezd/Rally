package controladores;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logica.Diccionario;
import logica.ListaCircular;
import logica.Soldado;
import main.Aplicacion;
import otros.ErrorV;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class JosephusControlador {
    @FXML
    private Button btn_simular;
    @FXML
    private Pane pane_simulacion;
    @FXML
    private TextField txti_soldados;
    @FXML
    private TextField txti_saltos;
    @FXML 
    private ListView lista;
    @FXML 
    private Button btn_regresar;

    private Aplicacion ventana;

    public JosephusControlador(Aplicacion ventana){
        this.ventana = ventana;
    }

    public void initialize() {
        btn_simular.setOnAction(e -> {
            try {
                int soldados = Integer.parseInt(txti_soldados.getText());
                int saltos = Integer.parseInt(txti_saltos.getText());
                if(soldados >=4 && saltos>=0){
                    btn_simular.setDisable(true);
                    btn_regresar.setDisable(true);
                    matanzaCircular(saltos, soldados);
                }else{
                    throw new Exception("Mal numero");
                }
            } catch (Exception ex) {
                new ErrorV("Ingresa solo numeros positivos, con soldados >=4 && saltos>=0 (La condicion de soldados es para que no se ve mal la parte grafica)");
            }
        });
        btn_regresar.setOnAction(e -> {
            ventana.cambiarMenu();
        });
    }

    public void matanzaCircular(int saltos, int soldados) {
        int division = soldados / 4;
        int nombre = 0;
        int apellido = 0;
        int apellido_paterno = 0;
        ListaCircular<Soldado> l = new ListaCircular<>();
        Diccionario diccionario = new Diccionario();
        pane_simulacion.getChildren().clear();
        int ancho = (623 / (division + 2));
        int alto = (581 / (division + 2));
        int x = 10 + ancho;
        int y = 10;
        int fase = 0;
        // Dimensiones del area de trabajo: W: 623, H: 581
        for (int i = 0; i < soldados; i++) {
            Rectangle rect = new Rectangle(x, y, ancho, alto);
            rect.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
            rect.setFill(Color.CADETBLUE);
            Text text = this.generarText(Integer.toString(i + 1), x + (ancho / 3), y + (2 * alto / 3), (ancho / 3));
            Soldado soldado = new Soldado(diccionario.nombres[nombre] + " " + diccionario.apellidos[apellido] + " "
                    + diccionario.apellidos[apellido_paterno], i + 1, rect);
            pane_simulacion.getChildren().add(rect);
            pane_simulacion.getChildren().add(text);
            l.agrega(soldado);
            switch (fase) {
                case 0:
                    if (i == division) {
                        fase = 1;
                        y = y + alto;
                    } else {
                        x = x + ancho;
                    }
                    break;
                case 1:
                    if (i == (2 * (division + 1)) - 1) {
                        fase = 2;
                        x = x - ancho;
                    } else {
                        y = y + alto;
                    }
                    break;
                case 2:
                    if (i == (3 * (division + 1)) - 1) {
                        fase = 3;
                        y = y - alto;
                    } else {
                        x = x - ancho;
                    }
                    break;
                case 3:
                    y = y - alto;
                    break;
                default:
                    break;
            }
            if (nombre < 285) {
                nombre++;
            } else {
                if (apellido < 406) {
                    apellido++;
                    if (apellido_paterno < 406) {
                        apellido_paterno++;
                    } else {
                        apellido_paterno = 0;
                    }
                } else {
                    apellido = 0;
                }
                nombre = 0;
            }
        }
        
        ListaCircular<Soldado>.IteradorListaCircular it2 = l.iteratorCircular();
        int contador1 = 0;
        lista.getItems().clear();
        while (contador1 < l.getTamanio()) {
            lista.getItems().add(lista.getItems().size(), it2.elem().toString());
            it2.next();
            contador1++;
        }


        ListaCircular<Soldado>.IteradorListaCircular it = l.iteratorCircular();
        it = l.iteratorCircular();
        int numero = l.getTamanio();
        int contador = 0;
        while (l.getTamanio() > 1) {
            for (int i = 0; i < saltos; i++) {
                it.next();
            }
            contador++;
            new Thread(new Hilo(it.elem(), contador, numero-1)).start();
            it.remove();
        }
    }

    private class Hilo implements Runnable {
        private Soldado soldado;
        private int paso;
        private int finalC;
        public Hilo(Soldado soldado, int paso, int finalC ) {
            this.soldado = soldado;
            this.paso = paso;
            this.finalC = finalC;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(paso*1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    soldado.getRect().setFill(Color.RED);
                    if(paso == finalC){
                        btn_simular.setDisable(false);
                        btn_regresar.setDisable(false);
                    }
                }
            });
        }
     }

    private Text generarText(String soldado, int x, int y, double size) {
        Text text = new Text(soldado);
        text.setFont(new Font("Arial", size));
        text.setX(x);
        text.setY(y);
        return text;
    }
}