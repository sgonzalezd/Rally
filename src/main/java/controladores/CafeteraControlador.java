package controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import logica.Becario;
import logica.Lista;
import logica.ListaCircular;
import main.Aplicacion;
import otros.ErrorV;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

public class CafeteraControlador {
    @FXML 
    private TextField in_nombre;
    @FXML 
    private ComboBox in_dia;
    @FXML 
    private Button btn_regresar;
    @FXML 
    private Button btn_generar;
    @FXML 
    private Button btn_agregar;
    @FXML 
    private ListView listaBecarios;
    @FXML 
    private ListView listaPendiente;
    @FXML 
    private DatePicker fecha;

    private Lista<String> lista_trabajo;
    private ListaCircular<Becario> lista_becarios;
    
    private Aplicacion ventana;

    public CafeteraControlador(Aplicacion ventana){
        this.ventana = ventana;
        this.lista_becarios = new ListaCircular<>();
        this.lista_trabajo = new Lista<>();
    }

    public void initialize() {
        btn_regresar.setOnAction(e -> {
            ventana.cambiarMenu();
        });
        lista_becarios.agrega(new Becario("Ricardo", 2));
        lista_becarios.agrega(new Becario("Alejandro", 4));
        lista_becarios.agrega(new Becario("Nestaly", 5));
        lista_becarios.agrega(new Becario("Alma", 6));
        this.actualizaBecarios();
        fecha.setValue(LOCAL_DATE("11-02-2020"));
        btn_generar.setOnAction(e -> {
            LocalDate value = fecha.getValue();
            this.actulizaListaPendiente(value.getDayOfMonth(), value.getMonthValue(), value.getYear());
        });

        in_dia.getItems().addAll(
            "Lunes",
            "Martes",
            "Miercoles",
            "Jueves",
            "Viernes",
            "Sabado", 
            "Domingo"
        );
        in_dia.getSelectionModel().selectFirst();
        btn_agregar.setOnAction(e -> {
            int dia = 0;
            for (String diaS : Becario.semana) {
                if(diaS.equals(in_dia.getValue().toString()))
                    break;
                dia++;
            }
            if(in_nombre.getText().isBlank()){
                new ErrorV("Pon algo en el nombre");
                return;
            }
            lista_becarios.agrega(new Becario(in_nombre.getText(),dia));
            this.actualizaBecarios();
        });
    }

    public void actulizaListaPendiente(int dia, int mes, int ano){
        lista_trabajo = this.repartirLimpieza(lista_becarios, dia, mes, ano);
        listaPendiente.getItems().clear();
        for (String obj: lista_trabajo) {
            listaPendiente.getItems().add(listaPendiente.getItems().size(), obj);
        }
    }
    public void actualizaBecarios(){
        listaBecarios.getItems().clear();
        ListaCircular<Becario>.IteradorListaCircular it = lista_becarios.iteratorCircular();
        int contador1 = 0;
        listaBecarios.getItems().clear();
        while (contador1 < lista_becarios.getTamanio()) {
            listaBecarios.getItems().add(listaBecarios.getItems().size(), it.elem().getNombre()+ '-'+ Becario.semana[it.elem().getDia()]);
            it.next();
            contador1++;
        }
    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public Lista<String> repartirLimpieza(ListaCircular<Becario> becarios, int dia, int mes, int ano) {
        Lista<String> lista = new Lista<>();
        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(ano, mes-1, dia);

        String[] meses = new String[]{"Enero","Febrero","Marzo"
                                     ,"Abril","Mayo","Junio","Julio"
                                     ,"Agosto","Septiembre","Octubre","Noviembre", "Diciembre"}; 
        
        int n = becarios.getTamanio();
        Iterator it = becarios.iterator();
        int semana = 16;
        int semanaA = calendar.get(Calendar.WEEK_OF_MONTH);
        if (it.hasNext()) {
            for (int i = 0; i < becarios.getTamanio()-1; i++) {
                it.next();
            }
            Becario becario = (Becario) it.next();
            boolean falta_asear = true;
            while (semana > 0) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                if(semanaA != calendar.get(Calendar.WEEK_OF_MONTH)){
                    falta_asear = true;
                }
                if(falta_asear && calendar.get(Calendar.DAY_OF_WEEK) == becario.getDia()){
                    lista.agregarAlFinal(Becario.semana[calendar.get(Calendar.DAY_OF_WEEK)] +" "+ calendar.get(Calendar.DATE) + " de "+ meses[calendar.get(Calendar.MONTH)] +": " + becario.getNombre());
                    becario = (Becario) it.next();
                    semana--;
                }
            }
        }
        return lista;
    }
}