/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Lector_Datos;

import EDD.Cola;
import analizador_lexico.Token;
import analizador_lexico.Tokenizer;
import análisis_sintáctico.Sintáctico;
import ejecución_sentencias.Ejecución;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author utente
 */
public class Menu_Tablas extends javax.swing.JFrame {
    
    ArrayList<Tabla> baseTabla;
    /**
     * Creates new form Menu_Tablas
     * @param baseTabla
     */
    public Menu_Tablas(ArrayList<Tabla> baseTabla) {
        this.baseTabla = baseTabla;
        initComponents();
        llenarComboBox();
        obtenerModeloTabla(baseTabla.get(0).getNombreTabla());
    }
    
    
    private void llenarComboBox() {
        
        for (Tabla ta : baseTabla) {
            selectTablaComboBox.addItem(ta.getNombreTabla());
        }  
        
        selectTablaComboBox.setSelectedItem(0);
    }
        
    
    private void obtenerModeloTabla(String nombre){
 
        Tabla tabla = null;
        
        for (Tabla ta : baseTabla) {
            if (nombre.equals(ta.getNombreTabla())) {
                tabla = ta;
            }
        }
            
        DefaultTableModel modelo =new DefaultTableModel(); 

        String[] nombreColumnas = new String[tabla.getListaAtributos().size()];
        
        for (int i = 0 ; i < tabla.getListaAtributos().size() ; i++) {
            nombreColumnas[i] = tabla.getListaAtributos().get(i).getNombre();
        }
        
        modelo.setColumnIdentifiers(nombreColumnas);      
     
        try{

            Object[] fila = new Object[modelo.getColumnCount()];
            
            for (int i = 0 ; i < tabla.getListaAtributos().get(0).getListaValores().size() ; i++) { 
                
                for (int j = 0 ; j < modelo.getColumnCount() ; j++) {
                    
                    fila[j] = tabla.getListaAtributos().get(j).getListaValores().get(i);                    
                }
                modelo.addRow(fila);
                
            }

            tablaJTable.setModel(modelo); 
        }
        catch (Exception ex) {
            System.err.println("Ha ocurrido un error al leer la tabla");
        }  
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaJTable = new javax.swing.JTable();
        selectTablaComboBox = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaJTable.setModel(new javax.swing.table.DefaultTableModel(baseTabla.get(0).getListaAtributos().get(0).getListaValores().size(), baseTabla.get(0).getListaAtributos().size())
        );
        jScrollPane1.setViewportView(tablaJTable);

        selectTablaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTablaComboBoxActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("EJECUTAR");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(selectTablaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectTablaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectTablaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTablaComboBoxActionPerformed
        obtenerModeloTabla(selectTablaComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_selectTablaComboBoxActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        
        //Extraer el codigo SQL en mayusculas (el analizador lexico lo requiere) .toUpperCase()
        
        String sqlCode = jTextField1.getText().toUpperCase();

        System.out.println(sqlCode);    
        
        Cola<Token> tokenList = Tokenizer.lex(sqlCode);

        tokenList.imprimirCola();

        Sintáctico sint = new Sintáctico(tokenList);
        Object[] parametros = new Object[6];
        
         
        // DEBUG
//        System.out.println(sint.parser().length);
        
        parametros = sint.parser();
      
        // DEBUG
        System.out.println(sint.contar_elementos(parametros));
        
        // DEBUG
        for (int i = 0 ; i < sint.contar_elementos(parametros) ; i++) {
            System.out.print(parametros[i] + " ");
        }
        
//        Cola<Token> colaColumna = (Cola<Token>) parametros[0];
//        colaColumna.imprimirCola();
//        
//        Cola<Token> colaTokensLogicos = (Cola<Token>) parametros[2];
//        colaTokensLogicos.imprimirCola();
        
        Ejecución ej = new Ejecución(baseTabla);
        DefaultTableModel mo = ej.crear_modelo_tabla(parametros, sint.contar_elementos(parametros));
        tablaJTable.setModel(mo);
    }//GEN-LAST:event_jButton1MousePressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> selectTablaComboBox;
    private javax.swing.JTable tablaJTable;
    // End of variables declaration//GEN-END:variables
}
