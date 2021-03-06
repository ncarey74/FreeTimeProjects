
/**
 * The user interface for the Units Converter.
 * The user interface is used to input the measurement amount, the units of said
 * amount, and the units to convert to. The user can convert between two 
 * imperial units, two metric units, from imperial to metric units, from 
 * metric to imperial units, and between temperature units.
 * A convert button performs the conversion. The converted measurement is then 
 * displayed. An error message can be displayed if the user tries to convert a 
 * negative length, mass, or volume; if the user tries to convert one type of 
 * unit to another (like from meters to liters); or if the user inputs a unit 
 * incorrectly (units must be spelled out, abbreviations are not allowed). 
 * A clear button clears the text fields and an exit button closes the program.
 * @author Carey Norslien
 */
public class UI extends javax.swing.JFrame {
   
   /**
    * Creates new form UI
    */
   public UI() {
      initComponents();
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      clearButton = new javax.swing.JButton();
      exitButton = new javax.swing.JButton();
      conversionField = new javax.swing.JTextField();
      convertedField = new javax.swing.JTextField();
      convertButton = new javax.swing.JButton();
      convertFromLB = new javax.swing.JLabel();
      titleLB = new javax.swing.JLabel();
      answerLB = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      clearButton.setText("Clear");
      clearButton.setToolTipText("");
      clearButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            clearButtonActionPerformed(evt);
         }
      });

      exitButton.setText("Exit");
      exitButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            exitButtonActionPerformed(evt);
         }
      });

      conversionField.setToolTipText("<html>\n<p>Enter the desired conversion witht the keyword \"to\" to separate the original amount and the units to convert to.</p>\n<p>Use \"per\" or \"/\" for composite units like mpg. If using \"/\", use a space before and after \"/\".</p>\n<p></p>\n<p> For example:</p>\n<p>25 miles to kilometers\n<p> 16.3 ml to fl o\n<p> 78 Fahrenheit to Celsius\n<p> 2 GB to KB </p>\n<p></p>\n<p>Negative amounts other than temperatures are not allowed.</p>\n");
      conversionField.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            conversionFieldActionPerformed(evt);
         }
      });

      convertedField.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            convertedFieldActionPerformed(evt);
         }
      });

      convertButton.setText("Convert");
      convertButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            convertButtonActionPerformed(evt);
         }
      });

      convertFromLB.setText("Enter the conversion:");

      titleLB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      titleLB.setText("Units Converter");

      answerLB.setText("The converted measurement:");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addComponent(convertFromLB)
                           .addComponent(conversionField, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                     .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(titleLB))
                     .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(convertedField, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                  .addGap(0, 0, Short.MAX_VALUE))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addGap(0, 0, Short.MAX_VALUE)
                  .addComponent(convertButton)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(clearButton)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(exitButton)))
            .addContainerGap())
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(answerLB)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(titleLB)
            .addGap(18, 18, 18)
            .addComponent(convertFromLB)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(conversionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(answerLB)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(convertedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(exitButton)
               .addComponent(clearButton)
               .addComponent(convertButton))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void convertedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertedFieldActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_convertedFieldActionPerformed

   /**
    * Clears all text fields when the clear button is clicked on.
    * @param evt the clear button is clicked on
    */
   private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
      conversionField.setText(null);
      convertedField.setText(null);
   }//GEN-LAST:event_clearButtonActionPerformed

   /**
    * Converts the measurement when the convert button is clicked on.
    * @param evt the convert button is clicked on
    */
   private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
      String finalAnswer;      
      String input = conversionField.getText();
      Converter converter = new Converter();
      
      converter.processInput(input);
      finalAnswer = converter.convert();
      convertedField.setText(finalAnswer);
   }//GEN-LAST:event_convertButtonActionPerformed

   /**
    * Exits the program when the exit button is clicked on.
    * @param evt  the exit button is clicked on
    */
   private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
      System.exit(0);
   }//GEN-LAST:event_exitButtonActionPerformed

   private void conversionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conversionFieldActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_conversionFieldActionPerformed
   
   /**
    * @param args the command line arguments
    */
   public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new UI().setVisible(true);
         }
      });
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel answerLB;
   private javax.swing.JButton clearButton;
   private javax.swing.JTextField conversionField;
   private javax.swing.JButton convertButton;
   private javax.swing.JLabel convertFromLB;
   private javax.swing.JTextField convertedField;
   private javax.swing.JButton exitButton;
   private javax.swing.JLabel titleLB;
   // End of variables declaration//GEN-END:variables
}
