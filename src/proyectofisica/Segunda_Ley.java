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
import javax.swing.JTextField;
/**
 *
 * @author dulce
 */
public class Segunda_Ley extends javax.swing.JFrame {
    // Componentes de la interfaz (deben coincidir con tu diseño)
    private JComboBox<String> variaComboBox;
    private JTextField fuerzaField, masaField, aceleracionField;
    private JTextField tensionField, friccionField, anguloField;
    private JTextField resultadoField, formulaField;
    private JButton calcularButton;
    
    // Constante física
    private static final double GRAVEDAD = 9.81;
    private final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Creates new form Segunda_Ley_de_Newton
     */
    public Segunda_Ley() {
        
        initComponents();
        configurarInterfaz();
    }
    private void configurarInterfaz(){
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Fuerza (F)");
        jComboBox1.addItem("Masa (m)");
        jComboBox1.addItem("Aceleracion (a)");
        jComboBox1.addItem("Tension (T)");
        jComboBox1.addItem("Coeficiente Friccion (μk)");
        jComboBox1.addItem("Angulo (θ)");
       
        jButton1.addActionListener(this::calcularActionPerformed);
        
        jTextFieldRespuesta.setEditable(false);
        jTextFieldFormula.setEditable(false);
    }
    private void calcularActionPerformed(ActionEvent evt) {
        try {
            SegundaLeyCalculator calculadora = new SegundaLeyCalculator();

            // Establecer valores conocidos
            if (!jTextFieldFuerza.getText().isEmpty()) calculadora.setFuerza(Double.parseDouble(jTextFieldFuerza.getText()));
            if (!jTextFieldMasa.getText().isEmpty()) calculadora.setMasa(Double.parseDouble(jTextFieldMasa.getText()));
            if (!jTextFieldAceleracion.getText().isEmpty()) calculadora.setAceleracion(Double.parseDouble(jTextFieldAceleracion.getText()));
            if (!jTextFieldTension.getText().isEmpty()) calculadora.setTension(Double.parseDouble(jTextFieldTension.getText()));
            if (!jTextFieldUK.getText().isEmpty()) calculadora.setCoeficienteFriccion(Double.parseDouble(jTextFieldUK.getText()));
            if (!jTextFieldAngulo.getText().isEmpty()) calculadora.setAngulo(Double.parseDouble(jTextFieldAngulo.getText()));
            
            calculadora.calcularVariablesIntermedias();

            String variable = (String) jComboBox1.getSelectedItem();
            double resultado = 0;
            String formula = "";

            switch (variable) {
                case "Fuerza (F)":
                    resultado = calculadora.calcularFuerza();
                    formula = calculadora.getFormulaUsada("fuerza");
                    break;
                case "Masa (m)":
                    resultado = calculadora.calcularMasa();
                    formula = calculadora.getFormulaUsada("masa");
                    break;
                case "Aceleracion (a)":
                    resultado = calculadora.calcularAceleracion();
                    formula = calculadora.getFormulaUsada("aceleracion");
                    break;
                case "Tension (T)":
                    resultado = calculadora.calcularTension();
                    formula = calculadora.getFormulaUsada("tension");
                    break;
                case "Coeficiente Friccion (μk)":
                    resultado = calculadora.calcularCoeficienteFriccion();
                    formula = calculadora.getFormulaUsada("friccion");
                    break;
                case "Angulo (θ)":
                    resultado = calculadora.calcularAngulo();
                    formula = calculadora.getFormulaUsada("angulo");
                    break;
            }

            jTextFieldRespuesta.setText(df.format(resultado));
            jTextFieldFormula.setText(formula);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static class SegundaLeyCalculator {
        private Double fuerza;
        private Double masa;
        private Double aceleracion;
        private Double tension;
        private Double coeficienteFriccion;
        private Double angulo; // en grados

        // Setters
        public void setFuerza(Double fuerza) {
            this.fuerza = fuerza;
        }
        public void setMasa(Double masa) {
            if (masa <= 0) throw new IllegalArgumentException("La masa debe ser mayor que cero");
            this.masa = masa;
        }

        public void setAceleracion(Double aceleracion) {
            this.aceleracion = aceleracion;
        }

        public void setTension(Double tension) {
            this.tension = tension;
        }

        public void setCoeficienteFriccion(Double coeficienteFriccion) {
            if (coeficienteFriccion < 0) throw new IllegalArgumentException("El coeficiente de fricción no puede ser negativo");
            this.coeficienteFriccion = coeficienteFriccion;
        }

        public void setAngulo(Double angulo) {
            if (angulo < 0 || angulo > 90) {
                throw new IllegalArgumentException("El ángulo debe estar entre 0° y 90°");
            }
            this.angulo = angulo;
        }
        // Métodos de cálculo
        public double calcularFuerza() {
            // Caso 1: Fuerza neta básica (F = m*a)
            if (masa != null && aceleracion != null) {
                return masa * aceleracion;
            }
            
            // Caso 2: Fuerza con fricción (F = μk*N + m*a)
            if (coeficienteFriccion != null && masa != null && aceleracion != null) {
                double fuerzaNormal = calcularFuerzaNormal();
                return (coeficienteFriccion * fuerzaNormal) + (masa * aceleracion);
            }
            
            throw new IllegalArgumentException("Datos insuficientes. Se requiere:\n- m y a\nO\n- μk, m y a");
        }

        public double calcularMasa() {
            // m = F/a
            if (fuerza != null && aceleracion != null) {
                return fuerza / aceleracion;
            }
            throw new IllegalArgumentException("Faltan datos: Requiere F y a");
        }

       private double calcularAceleracion() {
        // Caso 1: Sin fricción
        if (fuerza != null && masa != null && coeficienteFriccion == null) {
            return fuerza / masa;
        }
        
        // Caso 2: Con fricción
        if (fuerza != null && coeficienteFriccion != null && masa != null) {
            double fuerzaNormal = calcularFuerzaNormal();
            double fuerzaFriccion = coeficienteFriccion * fuerzaNormal;
            return (fuerza - fuerzaFriccion) / masa;
        }
        
        // Caso 3: Con tensión (para sistemas verticales)
        if (tension != null && masa != null) {
            return (tension - (masa * GRAVEDAD)) / masa;
        }
        
        throw new IllegalArgumentException("Datos insuficientes para calcular aceleración");
    }

        public double calcularTension() {
            // Caso 1: Tensión en equilibrio (T = m*g)
            if (masa != null && (aceleracion == null || aceleracion == 0)) {
                return masa * GRAVEDAD;
            }
            
            // Caso 2: Tensión con aceleración hacia arriba (T = m*(g + a))
            if (masa != null && aceleracion != null && aceleracion > 0) {
                return masa * (GRAVEDAD + aceleracion);
            }
            
            // Caso 3: Tensión con aceleración hacia abajo (T = m*(g - a))
            if (masa != null && aceleracion != null && aceleracion < 0) {
                return masa * (GRAVEDAD - Math.abs(aceleracion));
            }
            
            // Caso 4: Tensión con fricción (T = μk*N + m*a)
            if (coeficienteFriccion != null && masa != null && aceleracion != null) {
                double fuerzaNormal = calcularFuerzaNormal();
                return (coeficienteFriccion * fuerzaNormal) + (masa * aceleracion);
            }
            
            throw new IllegalArgumentException("Datos insuficientes. Se requiere:\n- m\nO\n- m y a\nO\n- μk, m y a");
        }
        public double calcularCoeficienteFriccion() {
            // μk = (F - m*a)/N
            if (fuerza != null && masa != null && aceleracion != null) {
                double fuerzaNormal = calcularFuerzaNormal();
                return (fuerza - (masa * aceleracion)) / fuerzaNormal;
            }
            
            throw new IllegalArgumentException("Se requieren F, m y a para calcular μk");
        }
        private double calcularFuerzaNormal() {
            if (angulo != null) {
                // Plano inclinado: N = m*g*cosθ
                return masa * GRAVEDAD * Math.cos(Math.toRadians(angulo));
            } else {
                // Superficie plana: N = m*g
                return masa * GRAVEDAD;
            }
        }

        public double calcularAngulo() {
            // θ = arcsen(T/(m*g))
            if (tension != null && masa != null) {
                return Math.toDegrees(Math.asin(tension / (masa * GRAVEDAD)));
            }
            throw new IllegalArgumentException("Faltan datos: Requiere T y m");
        }
        public void calcularVariablesIntermedias() {
            // Si tenemos F y m, podemos calcular a
            if (fuerza != null && masa != null && aceleracion == null) {
                aceleracion = fuerza / masa;
            }
            
            // Si tenemos T y m, podemos verificar equilibrio
            if (tension != null && masa != null && aceleracion == null) {
                if (Math.abs(tension - masa * GRAVEDAD) < 0.001) {
                    aceleracion = 0.0;
                }
            }
        }
        public String getFormulaUsada(String variable) {
            switch (variable.toLowerCase()) {
                case "fuerza":
                    if (coeficienteFriccion != null) return "F = μk·N + m·a";
                    return "F = m·a";
                case "masa": return "m = F/a";
                case "aceleracion":
                    if (coeficienteFriccion != null) return "a = (F - μk·N)/m";
                    if (tension != null) return "a = (T - m·g)/m";
                    return "a = F/m";
                case "tension":
                    if (aceleracion != null) {
                        if (aceleracion > 0) return "T = m·(g + a)";
                        if (aceleracion < 0) return "T = m·(g - |a|)";
                    }
                    if (coeficienteFriccion != null) return "T = μk·N + m·a";
                    return "T = m·g";
                case "coeficiente":
                case "friccion":
                case "μk": return "μk = (F - m·a)/N";
                case "angulo":
                case "θ": return "θ = arcsin(T/(m·g))";
                default: return "Fórmula no disponible";
            }
        }
        public String formatearResultado(double valor) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(valor);
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
        jTextFieldFuerza = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldMasa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldAceleracion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTension = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldRespuesta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldFormula = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldUK = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldAngulo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 36)); // NOI18N
        jLabel1.setText("Segunda Ley de Newton");

        jLabel2.setText("¿Que deseas encontrar?");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("¿Que datos conocemos?");

        jLabel4.setText("Fuerza");

        jLabel5.setText("Masa");

        jLabel6.setText("Aceleracion");

        jLabel7.setText("Tension");

        jLabel8.setText("Respuesta");

        jLabel9.setText("Formula");

        jButton1.setText("Calcular");

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setText("UK");

        jLabel11.setText("Angulo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(192, 226, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(19, 19, 19)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldMasa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldUK, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextFieldTension, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextFieldAceleracion, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(79, 79, 79)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(75, 75, 75)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextFieldAngulo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)))
                                .addGap(56, 56, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldFormula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addComponent(jTextFieldRespuesta, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAceleracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldMasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldUK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAngulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(1, 1, 1)
                        .addComponent(jTextFieldFormula, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap(15, Short.MAX_VALUE))
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
    private javax.swing.JTextField jTextFieldAngulo;
    private javax.swing.JTextField jTextFieldFormula;
    private javax.swing.JTextField jTextFieldFuerza;
    private javax.swing.JTextField jTextFieldMasa;
    private javax.swing.JTextField jTextFieldRespuesta;
    private javax.swing.JTextField jTextFieldTension;
    private javax.swing.JTextField jTextFieldUK;
    // End of variables declaration//GEN-END:variables
}
