/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.view;

import br.edu.uricer.dao.FornecedorDAO;
import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author renan
 */
public class OpcoesFornecedorForm extends javax.swing.JFrame {

    /**
     * Creates new form CadastroFornecedorForm
     */
    public OpcoesFornecedorForm() {
        initComponents();
        inicializar();
    }

    private void inicializar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaVendasPU");
        fornecDAO = new FornecedorDAO(emf);
        fornec = new Fornecedor();
        fornecs = new ArrayList<Fornecedor>();
        fornecTableModel = new FornecedorTableModel(fornecs);
        tbFornecs.setModel(fornecTableModel);

        atualizarTabela();

        edNomeFor.setEnabled(false);
        edCidadeFor.setEnabled(false);
        edIdFor.setEnabled(false);
        btNovo.setEnabled(true);
        btEditar.setEnabled(false);
        btGravar.setEnabled(false);
        btCancelar.setEnabled(false);
        btExcluir.setEnabled(false);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        edNomeFor = new javax.swing.JTextField();
        edCidadeFor = new javax.swing.JTextField();
        edIdFor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btNovo = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();
        btGravar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFornecs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opções do Fornecedor");

        jLabel1.setText("Nome:");

        jLabel2.setText("Cidade:");

        jLabel3.setText("Id:");

        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btEditar.setText("Editar");
        btEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarActionPerformed(evt);
            }
        });

        btGravar.setText("Gravar");
        btGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        tbFornecs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbFornecs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFornecsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbFornecs);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edIdFor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edNomeFor, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edCidadeFor, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btGravar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar)
                        .addGap(20, 20, 20)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(edNomeFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(edCidadeFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edIdFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNovo)
                    .addComponent(btEditar)
                    .addComponent(btGravar)
                    .addComponent(btExcluir)
                    .addComponent(btCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        // TODO add your handling code here:
        fornec = new Fornecedor();
        limparEdits();
        edNomeFor.grabFocus();
        edNomeFor.setEnabled(true);
        edCidadeFor.setEnabled(true);
        btNovo.setEnabled(false);
        btGravar.setEnabled(true);
        btCancelar.setEnabled(true);
        btExcluir.setEnabled(false);
    }//GEN-LAST:event_btNovoActionPerformed

    private void btEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarActionPerformed
        // TODO add your handling code here:
        editParaFornecedor();
        try {
            fornecDAO.edit(fornec);
            JOptionPane.showMessageDialog(this, "Editado com sucesso", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(OpcoesProdutoForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        atualizarTabela();
    }//GEN-LAST:event_btEditarActionPerformed

    private void btGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarActionPerformed
        // TODO add your handling code here:
        int resultado = JOptionPane.showConfirmDialog(this, "Confirma Gravação", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resultado == 0) {
            editParaFornecedor();
            Integer id = fornecDAO.create(fornec);
            edIdFor.setText(id.toString());

            btNovo.setEnabled(true);
            btCancelar.setEnabled(true);

            atualizarTabela();

            JOptionPane.showMessageDialog(this, "Gravado com sucesso! id: " + id, "Informação", JOptionPane.INFORMATION_MESSAGE);
            btGravar.setEnabled(false);
        }
    }//GEN-LAST:event_btGravarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        int resultado = JOptionPane.showConfirmDialog(this, "Confirma exclusão", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resultado == 0) {
            editParaFornecedor();
            int i = Integer.parseInt(edIdFor.getText());
            try {
                fornecDAO.destroy(i);
                JOptionPane.showMessageDialog(this, "Excluido com sucesso", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(OpcoesFornecedorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            atualizarTabela();
            edNomeFor.setEnabled(false);
            edCidadeFor.setEnabled(false);
            btNovo.setEnabled(true);
            btGravar.setEnabled(false);
            btCancelar.setEnabled(true);
            btExcluir.setEnabled(false);
            btEditar.setEnabled(false);
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        // TODO add your handling code here:
        fornec = null;
        limparEdits();
        edNomeFor.setEnabled(false);
        edCidadeFor.setEnabled(false);
        btNovo.setEnabled(true);
        btGravar.setEnabled(false);
        btCancelar.setEnabled(false);
        btExcluir.setEnabled(false);
        btEditar.setEnabled(false);
    }//GEN-LAST:event_btCancelarActionPerformed

    private void tbFornecsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFornecsMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            fornec = fornecs.get(tbFornecs.getSelectedRow());
            fornecParaEdit();

            edNomeFor.setEnabled(true);
            edCidadeFor.setEnabled(true);
            btEditar.setEnabled(true);
            btNovo.setEnabled(false);
            btGravar.setEnabled(false);
            btCancelar.setEnabled(true);
            btExcluir.setEnabled(true);
        }
    }//GEN-LAST:event_tbFornecsMouseClicked

    /**
     *
     * @param args
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
            java.util.logging.Logger.getLogger(OpcoesFornecedorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpcoesFornecedorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpcoesFornecedorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpcoesFornecedorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpcoesFornecedorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btNovo;
    private javax.swing.JTextField edCidadeFor;
    private javax.swing.JTextField edIdFor;
    private javax.swing.JTextField edNomeFor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbFornecs;
    // End of variables declaration//GEN-END:variables
    private FornecedorDAO fornecDAO;
    private FornecedorTableModel fornecTableModel;
    private List<Fornecedor> fornecs;
    private Fornecedor fornec;

    private void limparEdits() {
        edIdFor.setText("");
        edNomeFor.setText("");
        edCidadeFor.setText("");
        fornec = null;
    }

    private void editParaFornecedor() {
        fornec = new Fornecedor();
        if (edIdFor.getText() != null && !edIdFor.getText().isEmpty()) {
            fornec.setId(Integer.parseInt(edIdFor.getText()));
        }
        fornec.setNome(edNomeFor.getText());
        fornec.setCidade(edCidadeFor.getText());
    }

    private void fornecParaEdit() {
        edIdFor.setText(fornec.getId().toString());
        edNomeFor.setText(fornec.getNome());
        edCidadeFor.setText(fornec.getCidade());
    }

    private void atualizarTabela() {
        fornecs = fornecDAO.findFornecedorEntities();
        fornecTableModel.setFornecs(fornecs);
        fornecTableModel.fireTableDataChanged();
    }
}
