package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.Vista;
import vista.Login;
import modelo.Modelo;

public class Controlador implements ActionListener{
    
    private Modelo m;
    private Vista v;
    private Login l;
    
    public Controlador(Modelo m, Vista v){
        this.m = m;
        this.v = v;
        this.v.EnviarDatos.addActionListener(this);
        this.v.Consultar.addActionListener(this);
        this.v.Actualizar.addActionListener(this);
        this.v.Eliminar.addActionListener(this);
        this.v.Buscar.addActionListener(this);
        this.v.Reporte.addActionListener(this);
        this.v.Cancelar.addActionListener(this);
    }
    
    public void Inicial(){
       v.setTitle("Sistema MVC");
       v.pack();
       v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       v.setLocationRelativeTo(null);
       v.setVisible(true);
       m.CargarMetodos();
    }
    
    public void  actionPerformed(ActionEvent e){
        if(v.EnviarDatos == e.getSource()){
          try{
              m.EnviarDatos();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
          } 
        }
        else if(v.Consultar == e.getSource()){
          try{
              m.Consular();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Consultar");
          }
       }
        else if(v.Actualizar == e.getSource()){
          try{
              m.Actualizar();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Actualizar");
          }
       }
        else if(v.Eliminar == e.getSource()){
          try{
              m.Eliminar();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Eliminar");
          }
       }
        else if(v.Buscar == e.getSource()){
          try{
              m.Buscar();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Buscar");
          }
       }
        else if(v.Reporte == e.getSource()){
          try{
              m.GenerarReporte();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Generar Reporte");
          }
       }
        else if(v.Cancelar == e.getSource()){
          try{
              m.Cancelar();
          }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo Cancelar");
          }
       }
    }

}
