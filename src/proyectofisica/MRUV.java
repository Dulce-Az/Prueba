/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectofisica;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author dulce
 */
public class MRUV extends javax.swing.JFrame {
    private JComboBox<String> variableCombo;
    private JTextField viField, vfField, aField, tField, dField,vmField;
    private JButton calcularButton;
    private JTextArea resultadoArea;
    /**
     * Creates new form MRUV
     */
    public MRUV() {
        initComponents();
        configurarInterfaz();
    }
    private void configurarInterfaz() {
        // Configurar comboBox
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Velocidad Inicial (v₀)");
        jComboBox1.addItem("Velocidad Final (v)");
        jComboBox1.addItem("Aceleración (a)");
        jComboBox1.addItem("Tiempo (t)");
        jComboBox1.addItem("Distancia (d)");
        jComboBox1.addItem("Velocidad Media (vₘ)");

        // Configurar acción del botón calcular
        jButton1.addActionListener(this::calcularActionPerformed);
    }
    private void calcularActionPerformed(ActionEvent evt) {
        try {
            MRUVCalculator calculadora = new MRUVCalculator();

            // Establecer valores conocidos
            if (!jTextFieldVelInicial.getText().isEmpty()) calculadora.setVelocidadInicial(Double.parseDouble(jTextFieldVelInicial.getText()));
            if (!jTextFieldVelFinal.getText().isEmpty()) calculadora.setVelocidadFinal(Double.parseDouble(jTextFieldVelFinal.getText()));
            if (!jTextFieldAceleracion.getText().isEmpty()) calculadora.setAceleracion(Double.parseDouble(jTextFieldAceleracion.getText()));
            if (!jTextFieldTiempo.getText().isEmpty()) calculadora.setTiempo(Double.parseDouble(jTextFieldTiempo.getText()));
            if (!jTextFieldDistancia.getText().isEmpty()) calculadora.setDistancia(Double.parseDouble(jTextFieldDistancia.getText()));
            if (!jTextFieldVelMedia.getText().isEmpty()) calculadora.setVelocidadMedia(Double.parseDouble(jTextFieldVelMedia.getText()));
            
            calculadora.calcularVariablesIntermedias();

            String variable = (String) jComboBox1.getSelectedItem();
            double resultado = 0;
            String formula = "";

            switch (variable) {
                case "Velocidad Inicial (v₀)":
                    resultado = calculadora.calcularVelocidadInicial();
                    formula = "Vo = Vf - a * t";
                    break;
                case "Velocidad Final (v)":
                    resultado = calculadora.calcularVelocidadFinal();
                    formula = "Vf = Vo + a * t";
                    break;
                case "Aceleración (a)":
                    resultado = calculadora.calcularAceleracion();
                    formula = "a = (Vf - Vo) / t";
                    break;
                case "Tiempo (t)":
                    resultado = calculadora.calcularTiempo();
                    formula = "t = (Vf - Vo) / a";
                    break;
                case "Distancia (d)":
                    resultado = calculadora.calcularDistancia();
                    formula = "d = (Vf + Vo)/2 * t";
                    break;
                case "Velocidad Media (vₘ)":
                    resultado = calculadora.calcularVelocidadMedia();
                    formula = "v = (Vf + Vo) / 2";
                    break;
            }

            jTextFieldResultado.setText("Resultado: " + calculadora.formatearResultado(resultado));
            jTextFieldFormula.setText(formula);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     // Clase interna para los cálculos del MRUV
    private static class MRUVCalculator {
        private Double velocidadInicial;
        private Double velocidadFinal;
        private Double aceleracion;
        private Double tiempo;
        private Double distancia;
        private Double velocidadMedia;

        public void setVelocidadInicial(Double velocidadInicial) {
            this.velocidadInicial = velocidadInicial;
        }

        public void setVelocidadFinal(Double velocidadFinal) {
            this.velocidadFinal = velocidadFinal;
        }

        public void setAceleracion(Double aceleracion) {
            this.aceleracion = aceleracion;
        }

        public void setTiempo(Double tiempo) {
            if (tiempo < 0) throw new IllegalArgumentException("El tiempo no puede ser negativo");
            this.tiempo = tiempo;
        }

        public void setDistancia(Double distancia) {
            this.distancia = distancia;
        }

        public void setVelocidadMedia(Double velocidadMedia) {
            this.velocidadMedia = velocidadMedia;
        }

       public double calcularVelocidadInicial() {
        // Vo = Vf - a * t
        if (velocidadFinal != null && aceleracion != null && tiempo != null) {
            return velocidadFinal - aceleracion * tiempo;
        }
        throw new IllegalArgumentException("Faltan datos: Requiere Vf, a y t");
        }

        public double calcularVelocidadFinal() {
        // Vf = Vo + a * t
        if (velocidadInicial != null && aceleracion != null && tiempo != null) {
            return velocidadInicial + aceleracion * tiempo;
        }
        throw new IllegalArgumentException("Faltan datos: Requiere Vo, a y t");
        }

        public double calcularAceleracion() {
        // a = (Vf - Vo) / t
        if (velocidadFinal != null && velocidadInicial != null && tiempo != null) {
            return (velocidadFinal - velocidadInicial) / tiempo;
        }
        throw new IllegalArgumentException("Faltan datos: Requiere Vf, Vo y t");
        }

       public double calcularTiempo() {
        // t = (Vf - Vo) / a
        if (velocidadFinal != null && velocidadInicial != null && aceleracion != null) {
            return (velocidadFinal - velocidadInicial) / aceleracion;
        }
        throw new IllegalArgumentException("Faltan datos: Requiere Vf, Vo y a");
        }

        public double calcularDistancia() {
        // d = (Vf + Vo)/2 * t  (usando velocidad media)
        if (velocidadFinal != null && velocidadInicial != null && tiempo != null) {
            return ((velocidadFinal + velocidadInicial) / 2) * tiempo;
        }
        // Alternativa: d = Vo * t + 1/2 * a * t² 
        else if (velocidadInicial != null && aceleracion != null && tiempo != null) {
            return velocidadInicial * tiempo + 0.5 * aceleracion * Math.pow(tiempo, 2);
        }
        throw new IllegalArgumentException("Faltan datos: Requiere (Vf y Vo y t) o (Vo y a y t)");
        }

        public double calcularVelocidadMedia() {
        // v = (Vf + Vo) / 2
        if (velocidadFinal != null && velocidadInicial != null) {
            return (velocidadFinal + velocidadInicial) / 2;
        }
        throw new IllegalArgumentException("Datos insuficientes. Intenta ingresar:\n" +
            "- Vo, a y t\n" + 
            "O\n" + 
            "- Vf, a y t");
        }

        public String getFormulaUsada(String variable) {
            switch (variable.toLowerCase()) {
                case "velocidad inicial":
                    return "Fórmulas posibles:\n1) v₀ = v - a*t\n2) v₀ = (d - 0.5*a*t²)/t\n3) v₀ = √(v² - 2*a*d)\n4) v₀ = 2*vₘ - v";
                case "velocidad final":
                    return "Fórmulas posibles:\n1) v = v₀ + a*t\n2) v = √(v₀² + 2*a*d)\n3) v = 2*vₘ - v₀";
                case "aceleracion":
                    return "Fórmulas posibles:\n1) a = (v - v₀)/t\n2) a = 2*(d - v₀*t)/t²\n3) a = (v² - v₀²)/(2*d)";
                case "tiempo":
                    return "Fórmulas posibles:\n1) t = (v - v₀)/a\n2) t = d/vₘ\n3) t = 2*d/(v₀ + v)";
                case "distancia":
                    return "Fórmulas posibles:\n1) d = v₀*t + 0.5*a*t²\n2) d = v*t - 0.5*a*t²\n3) d = vₘ*t\n4) d = (v² - v₀²)/(2*a)";
                case "velocidad media":
                    return "Fórmulas posibles:\n1) vₘ = (v₀ + v)/2\n2) vₘ = d/t";
                default:
                    return "Fórmula no disponible";
            }
        }

        public String formatearResultado(double valor) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(valor);
        }
        // Método principal para calcular cualquier variable
    public double calcular(String variableSolicitada) {
        calcularVariablesIntermedias(); // Primero intenta despejar variables
        
        switch (variableSolicitada.toLowerCase()) {
            case "aceleración":
                return calcularAceleracion();
            case "distancia":
                return calcularDistancia();
            // ... otros casos
            default:
                throw new IllegalArgumentException("Variable no válida");
        }
    }
        public void calcularVariablesIntermedias() {
        // Si tenemos vm y t, podemos hallar d (d = vm * t)
        if (velocidadMedia != null && tiempo != null && distancia == null) {
            distancia = velocidadMedia * tiempo;
        }
        
        // Si parte del reposo (vo = 0) y tenemos vm, hallamos vf (vm = (vo + vf)/2 → vf = 2*vm)
        if (velocidadInicial != null && velocidadInicial == 0 && velocidadMedia != null && velocidadFinal == null) {
            velocidadFinal = 2 * velocidadMedia;
        }
        
        // Si tenemos vo, vf y t, hallamos aceleración (a = (vf - vo)/t)
        if (velocidadInicial != null && velocidadFinal != null && tiempo != null && aceleracion == null) {
            aceleracion = (velocidadFinal - velocidadInicial) / tiempo;
        } 
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldVelInicial = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldVelFinal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldAceleracion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTiempo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldDistancia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldResultado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldFormula = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldVelMedia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jLabel1.setText("MRUV");

        jLabel2.setText("¿Que deseas encontrar? ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("¿Que datos conocemos?");

        jLabel4.setText("Velocidad Inicial (vo)");

        jLabel5.setText("Velocidad Final (vf)");

        jLabel6.setText("Aceleracion");

        jLabel7.setText("Tiempo");

        jLabel8.setText("Distancia");

        jLabel9.setText("Resultado");

        jTextFieldResultado.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N

        jLabel10.setText("Formula");

        jTextFieldFormula.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N

        jButton1.setText("Calcular");

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel11.setText("Velocidad Media");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(84, 84, 84)
                                                .addComponent(jTextFieldVelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jTextFieldVelMedia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldFormula, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addComponent(jTextFieldResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldAceleracion, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jTextFieldVelInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(227, 227, 227)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldVelInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldAceleracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldVelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldResultado)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFormula, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldVelMedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19)
                .addComponent(jButton1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextFieldAceleracion;
    private javax.swing.JTextField jTextFieldDistancia;
    private javax.swing.JTextField jTextFieldFormula;
    private javax.swing.JTextField jTextFieldResultado;
    private javax.swing.JTextField jTextFieldTiempo;
    private javax.swing.JTextField jTextFieldVelFinal;
    private javax.swing.JTextField jTextFieldVelInicial;
    private javax.swing.JTextField jTextFieldVelMedia;
    // End of variables declaration//GEN-END:variables
}
