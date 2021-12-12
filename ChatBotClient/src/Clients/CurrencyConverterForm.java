package Clients;

//import API.currencyConverter;
//import static API.currencyConverter.getListCodeCity;

import static Clients.FormChat.txtArea;
import MaHoa.AESClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Admin
 */
public class CurrencyConverterForm extends javax.swing.JFrame {

    ArrayList<String> listCity1 = new ArrayList<>();
    ArrayList<String> listCity2 = new ArrayList<>();
    public static String selected_1;
    public static String selected_2;
    public static String luongTien;
    public static String result;
    private  BufferedWriter out = null;
    private  BufferedReader in = null;
    
  //  private Client c ;

//    private static ServerSocket server = null;
//    private static Socket socket = null;
//    private static BufferedReader in = null;
//    private static BufferedWriter out = null;

    public CurrencyConverterForm(Client c) {
        initComponents();
        out = c.getOut();
        in = c.getIn();
        this.setLocationRelativeTo(null);
        LoadData();
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
        comboboxListCity2 = new javax.swing.JComboBox<>();
        comboboxListCity1 = new javax.swing.JComboBox<>();
        txtLuongTien = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnChuyenDoi = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        comboboxListCity2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VND" }));
        comboboxListCity2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxListCity2ActionPerformed(evt);
            }
        });
        jPanel1.add(comboboxListCity2);
        comboboxListCity2.setBounds(110, 170, 510, 50);

        comboboxListCity1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USD" }));
        comboboxListCity1.setToolTipText("");
        comboboxListCity1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxListCity1ActionPerformed(evt);
            }
        });
        jPanel1.add(comboboxListCity1);
        comboboxListCity1.setBounds(110, 70, 510, 50);

        txtLuongTien.setText("1");
        txtLuongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongTienActionPerformed(evt);
            }
        });
        jPanel1.add(txtLuongTien);
        txtLuongTien.setBounds(110, 280, 510, 40);

        jLabel1.setText("Lượng tiền:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 250, 80, 30);

        jLabel2.setText("Từ:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(110, 50, 21, 16);

        jLabel3.setText("Sang:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(110, 150, 41, 16);

        btnChuyenDoi.setText("Chuyển đổi");
        btnChuyenDoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenDoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnChuyenDoi);
        btnChuyenDoi.setBounds(110, 370, 510, 50);

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);
        btnThoat.setBounds(610, 480, 65, 25);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboboxListCity1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxListCity1ActionPerformed
        selected_1 = (String) comboboxListCity1.getSelectedItem();
    }//GEN-LAST:event_comboboxListCity1ActionPerformed

    private void comboboxListCity2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxListCity2ActionPerformed
        selected_2 = (String) comboboxListCity2.getSelectedItem();
        // FormChat.txtArea.append(selected_2);
    }//GEN-LAST:event_comboboxListCity2ActionPerformed

    public static String sendDataToServer(String chuoi1, String chuoi2, String chuoi3) {
        return chuoi1 + " " + chuoi2 + " " + chuoi3;
    }
    private void btnChuyenDoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenDoiActionPerformed
        luongTien = txtLuongTien.getText();
        result = "currencyConverter" +" "+ sendDataToServer(selected_1, selected_2, luongTien);
        result = AESClient.encrypt(result);
      //  txtArea.append(result);
        //Client c = new Client();
        FormChat.c.sendClient(result);
//      
//        try {
//
//            out.write(result + "\r\n");
//            out.flush();
//
//            // server response:
//            FormChat.txtArea.append(in.readLine() + "\r\n");
//
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//          System.out.println(result);
//          System.out.println(result.contains("chuyendoitien"));
    }//GEN-LAST:event_btnChuyenDoiActionPerformed

    private void txtLuongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongTienActionPerformed
        this.btnChuyenDoi.requestFocus();
        this.btnChuyenDoi.doClick();
        this.btnChuyenDoi.setText("");
    }//GEN-LAST:event_txtLuongTienActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnThoatActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[], Client c) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CurrencyConverterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CurrencyConverterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CurrencyConverterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CurrencyConverterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CurrencyConverterForm(c).setVisible(true);
//            }
//        });
//    }

    public void LoadData() {

      //  listCity1 = currencyConverter.getListCodeCity();
      //  listCity2 = currencyConverter.getListCodeCity();
      
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenDoi;
    private javax.swing.JButton btnThoat;
    public static javax.swing.JComboBox<String> comboboxListCity1;
    public static javax.swing.JComboBox<String> comboboxListCity2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtLuongTien;
    // End of variables declaration//GEN-END:variables
}
