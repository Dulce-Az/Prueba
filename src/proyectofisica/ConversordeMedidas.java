/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectofisica;

/**
 *
 * @author dulce
 */
public class ConversordeMedidas extends javax.swing.JFrame {

    /**
     * Creates new form ConversordeMedidas
     */
    public ConversordeMedidas() {
        initComponents();
        llenarCombobox1();
        configurarEventos1();
        llenarCombobox2();
        configurarEventos2();
        llenarCombobox3();
        configurarEventos3();
      
    }
    private void llenarCombobox1(){
        comboLongitud1.removeAllItems();
        comboLongitud1.addItem("Metros");
        comboLongitud1.addItem("Kilometros");
        comboLongitud1.addItem("Millas");
        comboLongitud1.addItem("Pies");
        comboLongitud1.addItem("Centimetros");
        comboLongitud1.addItem("Pulgadas");
        
        ComboLongitud2.removeAllItems();
        ComboLongitud2.addItem("Metros");
        ComboLongitud2.addItem("Kilometros");
        ComboLongitud2.addItem("Millas");
        ComboLongitud2.addItem("Pies");
        ComboLongitud2.addItem("Centimetros");
        ComboLongitud2.addItem("Pulgadas");
    }
    private void llenarCombobox2(){
        ComboTiempo1.removeAllItems();
        ComboTiempo1.addItem("Segundos");
        ComboTiempo1.addItem("Minutos");
        ComboTiempo1.addItem("Horas");
        ComboTiempo1.addItem("Días");
        ComboTiempo1.addItem("Años");
        
        ComboTiempo2.removeAllItems();
        ComboTiempo2.addItem("Segundos");
        ComboTiempo2.addItem("Minutos");
        ComboTiempo2.addItem("Horas");
        ComboTiempo2.addItem("Días");
        ComboTiempo2.addItem("Años");
    }
    private void llenarCombobox3 (){
        ComboMasa1.removeAllItems();
        ComboMasa1.addItem("Kilogramo");
        ComboMasa1.addItem("Gramo");
        ComboMasa1.addItem("Libra");
        ComboMasa1.addItem("Onza");
        
        ComboMasa2.removeAllItems();
        ComboMasa2.addItem("Kilogramo");
        ComboMasa2.addItem("Gramo");
        ComboMasa2.addItem("Libra");
        ComboMasa2.addItem("Onza");
    }
    private void configurarEventos1(){
        comboLongitud1.addActionListener(e -> convertirLongitud());
        ComboLongitud2.addActionListener(e -> convertirLongitud());
    }
    
    private void configurarEventos2(){
        ComboTiempo1.addActionListener(e -> convertirTiempo());
        ComboTiempo2.addActionListener(e -> convertirTiempo());
        jTextField3.addActionListener(e -> convertirTiempo());
    }
    
    private void configurarEventos3(){
        ComboMasa1.addActionListener(e -> convertirMasa());
        ComboMasa2.addActionListener(e -> convertirMasa());
        jTextField5.addActionListener(e -> convertirMasa());
    }
    private void convertirLongitud(){
        try {
            double cantidad = Double.parseDouble(jTextField1.getText());
            String de = comboLongitud1.getSelectedItem().toString();
            String a = ComboLongitud2.getSelectedItem().toString();
            
            double enMetros = 0;
            
            switch (de) {
                case "Metros": enMetros = cantidad; break;
                case "Kilometros" : enMetros = cantidad * 1000; break;
                case "Millas" : enMetros = cantidad * 1609.34; break;
                case "Pies" : enMetros = cantidad * 0.3048; break;
                case "Centimetros" : enMetros = cantidad *0.01; break;
                case "Pulgadas" : enMetros = cantidad * 0.0254; break;
            }
            
            double resultado = 0;
            switch (a) {
                case "Metros": resultado = enMetros; break;
                case "Kilometros" : resultado = enMetros /1000; break;
                case "Millas" : resultado = enMetros / 1609.34; break;
                case "Pies" : resultado = enMetros /0.3048; break;
                case "Centimetros" : resultado = enMetros / 0.01; break;
                case "Pulgadas" : resultado = enMetros / 0.0254; break;
            
            }
            
            jTextField2.setText(String.format("%.4f", resultado));
        } catch (Exception e) {
            jTextField2.setText("Ingesa solo números");
        }
    }
    
    private void convertirTiempo(){
        try {
            double cantidad = Double.parseDouble(jTextField3.getText());
            String de = ComboTiempo1.getSelectedItem().toString();
            String a = ComboTiempo2.getSelectedItem().toString();
            
            double Tiempo = 0; 
            
            switch (de) {
                case "Segundos": Tiempo = cantidad; break;
                case "Minutos" : Tiempo = cantidad * 60; break;
                case "Horas" : Tiempo = cantidad *3600; break;
                case "Días" : Tiempo = cantidad * 86400; break;
                case "Años" : Tiempo = cantidad * 31536000; break;
            }
            double resultado = 0;
            
            switch (a) {
                case "Segundos": resultado = Tiempo; break;
                case " Minutos" : resultado = Tiempo / 60; break;
                case "Horas" : resultado = Tiempo /3600; break;
                case "Días" : resultado = Tiempo/86400; break;
                case "Años" : resultado = Tiempo /31536000; break;
            }
            
            jTextField4.setText(String.format("%.6f", resultado));
        } catch (Exception e) {
            jTextField4.setText("Ingresa solo números");
        }
    }
    private void convertirMasa(){
        try {
            double cantidad = Double.parseDouble(jTextField5.getText());
            String de = ComboMasa1.getSelectedItem().toString();
            String a = ComboMasa2.getSelectedItem().toString();
            
            final double Gramos_por_onza = 28.34952;
            final double Gramos_por_libra = 453.592;
            
            double  enGramos = 0; 
            
            switch (de) {
                case "Kilogramo" : enGramos = cantidad * 1000; break;
                case "Gramo" : enGramos = cantidad; break;
                case "Libra" : enGramos = cantidad * Gramos_por_libra; break;
                case "Onza" : enGramos = cantidad * Gramos_por_onza;  break;
            }
            
            double resultado = 0; 
            
            switch (a) {
                case "Kilogramo": resultado = enGramos / 1000; break;
                case "Gramo" : resultado = enGramos; break;
                case "Libra" : resultado = enGramos / Gramos_por_libra; break;
                case "Onza" : resultado = enGramos / Gramos_por_onza; break;
  
            }
            jTextField6.setText(String.format("%.6f", resultado));
        } catch (Exception e) {
            jTextField6.setText("Ingresa solo numeros");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        comboLongitud1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        ComboLongitud2 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboTiempo1 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ComboTiempo2 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ComboMasa1 = new javax.swing.JComboBox<>();
        jTextField5 = new javax.swing.JTextField();
        ComboMasa2 = new javax.swing.JComboBox<>();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 0, 36)); // NOI18N
        jLabel1.setText("Conversor");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        jLabel2.setText("Medidas de Longitud ");

        comboLongitud1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel3.setText("= ");

        ComboLongitud2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        jLabel4.setText("Medidas de Tiempo ");

        ComboTiempo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel5.setText("= ");

        ComboTiempo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        jLabel6.setText("Medidas de Masa");

        ComboMasa1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboMasa2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel7.setText("=");

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(comboLongitud1, 0, 101, Short.MAX_VALUE))
                                .addGap(82, 82, 82)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField3)
                                            .addComponent(ComboTiempo1, 0, 108, Short.MAX_VALUE))
                                        .addGap(70, 70, 70))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ComboMasa1, 0, 99, Short.MAX_VALUE)
                                            .addComponent(jTextField5))
                                        .addGap(73, 73, 73)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))))
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboLongitud2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboTiempo2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboMasa2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4)
                            .addComponent(jTextField6))))
                .addGap(96, 96, 96))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboLongitud2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboLongitud1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(ComboTiempo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ComboTiempo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboMasa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboMasa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboLongitud2;
    private javax.swing.JComboBox<String> ComboMasa1;
    private javax.swing.JComboBox<String> ComboMasa2;
    private javax.swing.JComboBox<String> ComboTiempo1;
    private javax.swing.JComboBox<String> ComboTiempo2;
    private javax.swing.JComboBox<String> comboLongitud1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
