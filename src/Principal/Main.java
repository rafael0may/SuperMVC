package Principal;
import controlador.Controlador;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.Modelo;
import vista.Vista;

public class Main {
    
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);
        }
        
        Modelo m = new Modelo();
        Vista v =  new Vista();
        Controlador c =  new Controlador(m,v);
        c.Inicial();
        
    }
}
