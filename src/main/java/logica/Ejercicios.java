package logica;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

public class Ejercicios {
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
