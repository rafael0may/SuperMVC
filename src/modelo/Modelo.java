package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import java.sql.*;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static vista.Vista.Combo1;


public class Modelo {
    Vista v;
    Controlador c;
    Connection cc;
    Connection cn = Conexion();
    
   public void CargarMetodos(){
        MostrarTabla("");
    }
   
    public Connection Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cc = DriverManager.getConnection("jdbc:mysql://localhost/sistema","root","");
            System.out.println("Conexion Exitosa");
        }catch(Exception e){
            System.out.println(e);
        }
        return cc;
    }
    
//    public static void main(String[] args) {
//        Modelo m = new Modelo();
//        m.Conexion();
//    }
    
    
    public void Cancelar(){
        v.txtId.setText("");
        v.txtNombre.setText("");
        v.txtApellido.setText("");
        v.txtEdad.setText("");
        v.Combo1.setSelectedItem("Selecciona-");
        v.txtFecha.setText("");
        v.Fecha1.setDateFormatString("");
    }
    
    public void GenerarReporte(){
        try{
           String ruta = "C:\\Users\\NidamWarrior\\Documents\\NetBeansProjects\\SuperMVC\\src\\vista\\ReporteUsuarios.jrxml";
           JasperReport st =JasperCompileManager.compileReport(ruta);
           JasperPrint mr =JasperFillManager.fillReport(st, null, cn);
           JasperViewer view = new JasperViewer(mr, false);
           view.setVisible(true);
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, "No se puede mostrar el Reporte"); 
        }
    }
    
    public void Buscar(){
        if(v.txtId.equals("")){
            JOptionPane.showMessageDialog(null, "Campo en vacio");
        }
        else{
            MostrarTabla(v.txtId.getText());
        }
    }
    
    public void Eliminar(){
        int fila = v.Tabla.getSelectedRow();
        if(fila>=0){
            try{
                String id = v.Tabla.getValueAt(fila,0).toString();
                PreparedStatement ppt = cn.prepareStatement("DELETE FROM usuario "
                        + "WHERE id = '"+ id + "'");
                ppt.executeUpdate();
                MostrarTabla("");
                JOptionPane.showMessageDialog(null, "Dato eliminar");
            }catch(SQLException e){
                
            }
        }
        else{
           JOptionPane.showMessageDialog(null, "Selecciones una fila"); 
        }
    }
    
    public void Actualizar(){
        String id = v.txtId.getText();
        if(v.txtNombre.getText().equals("")|| v.txtApellido.getText().equals("") || 
                v.txtEdad.getText().equals("")){  
            JOptionPane.showMessageDialog(null, "Faltan datos por Rellenar");
           }
        else{
        try{
            PreparedStatement ppt = cn.prepareStatement("UPDATE usuario SET "
                    + "Nombre = '"+ v.txtNombre.getText()+ "',"
                    + "Apellido = '"+ v.txtApellido.getText()+ "',"
                    + "Edad = '"+ v.txtEdad.getText()+ "',"
                    + "Sexo = '"+ Combo1.getSelectedItem()+ "',"
                    + "Fecha = '"+ v.txtFecha.getText()+ "' "
                    +"WHERE Id ='"+ id +"'");
            
            ppt.executeUpdate();
            MostrarTabla("");
            JOptionPane.showMessageDialog(null, "Datos Actualizados");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Datos No Actualizados");
        }
       }
    }
    
    public void Consular(){
        int fila = v.Tabla.getSelectedRow();
        if(fila>=0){
            v.txtId.setText(v.Tabla.getValueAt(fila, 0).toString());
            v.txtNombre.setText(v.Tabla.getValueAt(fila, 1).toString());
            v.txtApellido.setText(v.Tabla.getValueAt(fila, 2).toString());
            v.txtEdad.setText(v.Tabla.getValueAt(fila, 3).toString());
            v.Combo1.setSelectedItem(v.Tabla.getValueAt(fila, 4).toString());
            v.txtFecha.setText(v.Tabla.getValueAt(fila,5).toString());
            v.Fecha1.setDateFormatString(v.Tabla.getValueAt(fila,5).toString());
        }
        else{
           JOptionPane.showMessageDialog(null, "Selecciones una fila"); 
        }
    }
    
    void MostrarTabla(String valor){
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.addColumn("Id");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       modelo.addColumn("Edad");
       modelo.addColumn("Sexo");
       modelo.addColumn("fecha");
       v.Tabla.setModel(modelo);
       
       v.Combo1.addItem("Selecciona-");
       v.Combo1.addItem("Hombre");
       v.Combo1.addItem("Mujer");
       
       String sql = "";
       if(valor.equals("")){
          sql = "SELECT * FROM usuario";
        }
       else{
           sql = "SELECT * FROM usuario WHERE id ='"+ valor + "'";
       }
       
       String datos[] = new String[6];       
       try{
           Statement st = cn.createStatement();
           ResultSet rs = st.executeQuery(sql);
           while(rs.next()){
               datos[0] = rs.getString(1);
               datos[1] = rs.getString(2);
               datos[2] = rs.getString(3);
               datos[3] = rs.getString(4);
               datos[4] = rs.getString(5);
               datos[5] = rs.getString(6); 
               modelo.addRow(datos);
               
          }
           v.Tabla.setModel(modelo);
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "No se pudo cargar los datos");
       }
    }
    
    public void  EnviarDatos(){
        String dia = Integer.toString(v.Fecha1.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.Fecha1.getCalendar().get(Calendar.MONTH) + 1);
        String year = Integer.toString(v.Fecha1.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + " / "+ mes + " / "+ year);
        
        if(v.txtNombre.getText().equals("")|| v.txtApellido.getText().equals("") || 
                v.txtEdad.getText().equals("")){  
            JOptionPane.showMessageDialog(null, "Faltan datos por Rellenar");
           }
        else{
        try{
            PreparedStatement ppt = cn.prepareStatement("INSERT INTO usuario(Nombre,Apellido,Edad,Sexo,Fecha)"
            + "VALUES(?,?,?,?,?)");
            
            ppt.setString(1,v.txtNombre.getText());
            ppt.setString(2,v.txtApellido.getText());
            ppt.setString(3,v.txtEdad.getText());
        if(v.Combo1.getSelectedItem().toString().equals("-Selecciona-")){
            JOptionPane.showMessageDialog(null, "Debes elegir un sexo");}
        else{ v.txtNombre.setText("");
              v.txtApellido.setText("");
              v.txtEdad.setText("");
              ppt.setString(4, v.Combo1.getSelectedItem().toString());
              ppt.setString(5, fecha);
              ppt.executeUpdate();
              MostrarTabla("");
              JOptionPane.showMessageDialog(null, "Datos Guardados");
            }
         }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Datos no Guardados");
        }    
      }
   }       
}                                      
    