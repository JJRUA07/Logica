import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class frmCajaRegistradora extends JFrame {
    JComboBox cbDenominacion;
    JTextField txtExistencia, txtValorDevolver;
    JTable tblDevolucion;
    JButton btnActualizar, btnDevolver;
    String[] denominaciones = { "100000", "50000", "20000", "10000", "5000", "2000", "1000", "500", "200", "100","50" };
    String[] columnas = { "Cantidad", "Billete/Moneda", "Denominación" };
    int[] existencia = new int[denominaciones.length];
    DefaultTableModel modeloTabla;

    public frmCajaRegistradora() {
        setSize(500, 350);
        setTitle("Caja Registradora");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblDenominacion = new JLabel("Denominación");
        lblDenominacion.setBounds(90, 10, 100, 25);
        getContentPane().add(lblDenominacion);

        cbDenominacion = new JComboBox(denominaciones);
        cbDenominacion.setBounds(180, 10, 100, 25);
        getContentPane().add(cbDenominacion);



        btnActualizar = new JButton("Actualizar Existencia");
        btnActualizar.setBounds(80, 50, 150, 25);
        getContentPane().add(btnActualizar);
        btnActualizar.addActionListener(new btnActualizarListener());

        txtExistencia = new JTextField();
        txtExistencia.setBounds(240, 50, 100, 25);
        getContentPane().add(txtExistencia);



        JLabel lblValorDevolver = new JLabel("Valor a Devolver");
        lblValorDevolver.setBounds(20, 90, 120, 25);
        getContentPane().add(lblValorDevolver);

        txtValorDevolver = new JTextField();
        txtValorDevolver.setBounds(120, 90, 100, 25);
        getContentPane().add(txtValorDevolver);

        btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(230, 90, 100, 25);
        getContentPane().add(btnDevolver);
        btnDevolver.addActionListener(new btnDevolverListener());

        modeloTabla = new DefaultTableModel(null, columnas);
        tblDevolucion = new JTable(modeloTabla);
        JScrollPane spTabla = new JScrollPane(tblDevolucion);
        spTabla.setBounds(10, 130, 460, 150);
        getContentPane().add(spTabla);
    }

    private class btnActualizarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            actualizacionCan();
        }
    }

    private class btnDevolverListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            calcularDevuelta();
        }
    }

    private void actualizacionCan() {
        int den = Integer.parseInt((String)cbDenominacion.getSelectedItem());
        int cant = -1;
        try {
            
            for (int i = 0; i < denominaciones.length; i++) {
                if (Integer.parseInt(denominaciones[i]) == den) {
                    cant = i;
                    break;
                }
            }
            if (cant != -1) {
                int existencias = Integer.parseInt(txtExistencia.getText());
                if (existencias > 0) {
                    existencia[cant] += existencias;
                    JOptionPane.showMessageDialog(null, "La cantidad de " + denominaciones[cant] + " ha sido actualizada a " + existencia[cant]);
                } else {
                    JOptionPane.showMessageDialog(null, "Error no se puede ingresar un numero negativo");
                    txtExistencia.setText("");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ingrese un numero entero");
            txtExistencia.setText("");
        }
    }

    private void calcularDevuelta() {
        int valorDevolver = Integer.parseInt(txtValorDevolver.getText());
        String[][] datos = new String[denominaciones.length][3];
        int contador = 0;

        for (int i = 0; i < denominaciones.length; i++) {
            int denominacion = Integer.parseInt(denominaciones[i]);
            int cantidadDisponible = existencia[i];
            int cantidad;
            if (valorDevolver / denominacion < cantidadDisponible) {
                cantidad = valorDevolver / denominacion;
            } else {
                cantidad = cantidadDisponible;
            }
            if (cantidad > 0) {
                valorDevolver -= cantidad * denominacion;
                existencia[i] -= cantidad;
                datos[contador][0] = String.valueOf(cantidad);
                if (denominacion >= 1000) {
                    datos[contador][1] = "Billete";
                } else {
                    datos[contador][1] = "Moneda";
                }
                datos[contador][2] = String.valueOf(denominacion);
                contador++;
            }
        }

        String[][] datosFinales = new String[contador][3];
        for (int i = 0; i < contador; i++) {
            datosFinales[i] = datos[i];
        }

        DefaultTableModel modelo = new DefaultTableModel(datosFinales, columnas);
        tblDevolucion.setModel(modelo);
    }
}
