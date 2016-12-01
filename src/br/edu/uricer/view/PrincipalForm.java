/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.view;

import br.edu.uricer.dao.ClienteDAO;
import br.edu.uricer.dao.FornecedorDAO;
import br.edu.uricer.dao.ProdutoDAO;
import br.edu.uricer.dao.VendaDAO;
import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Cliente;
import br.edu.uricer.model.Fornecedor;
import br.edu.uricer.model.Produto;
import br.edu.uricer.model.Venda;
import java.math.BigDecimal;
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
public class PrincipalForm extends javax.swing.JFrame {

    /**
     * Creates new form PrincipalForm
     */
    public PrincipalForm() {
        initComponents();
        inicializar();
        
    }
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaVendasPU");
        private void inicializar() {
        
        vendaDAO = new VendaDAO(emf);
        venda = new Venda();
        vendas = new ArrayList<>();
        vendaTM = new VendaTableModel(vendas);
        tbVendas.setModel(vendaTM);

        atualizarTabela();

        edVenda.setEnabled(false);
        edFornec.setEnabled(false);
        edProduto.setEnabled(false);
        edQtdProdutos.setEnabled(false);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(false);
        btNovo.setEnabled(true);
        
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
        edVenda = new javax.swing.JTextField();
        btNovo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edProduto = new javax.swing.JTextField();
        edFornec = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edCliente = new javax.swing.JTextField();
        edQtdProdutos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        edValorTotal = new javax.swing.JTextField();
        btGravar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVendas = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        Opcoes = new javax.swing.JMenu();
        mnCadastroProduto = new javax.swing.JMenuItem();
        mnCadastroFornecedor = new javax.swing.JMenuItem();
        mnCadastroCliente = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Vendas");

        jLabel1.setText("Id Venda:");

        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        jLabel2.setText("Id Produto:");

        jLabel3.setText("Id Fornecedor:");

        edFornec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edFornecActionPerformed(evt);
            }
        });

        jLabel4.setText("Id Cliente:");

        jLabel5.setText("Qtd Produtos:");

        jLabel6.setText("Valor Total:");

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

        tbVendas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbVendas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(edVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(edProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edFornec, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btNovo)
                        .addGap(50, 50, 50)
                        .addComponent(btGravar)
                        .addGap(52, 52, 52)
                        .addComponent(btExcluir)
                        .addGap(51, 51, 51)
                        .addComponent(btCancelar)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edQtdProdutos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edValorTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(edCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(edProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edQtdProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edFornec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(edValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNovo)
                    .addComponent(btGravar)
                    .addComponent(btExcluir)
                    .addComponent(btCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Opcoes.setText("Opções");

        mnCadastroProduto.setText("Produtos");
        mnCadastroProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCadastroProdutoActionPerformed(evt);
            }
        });
        Opcoes.add(mnCadastroProduto);

        mnCadastroFornecedor.setText("Fornecedores");
        mnCadastroFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCadastroFornecedorActionPerformed(evt);
            }
        });
        Opcoes.add(mnCadastroFornecedor);

        mnCadastroCliente.setText("Clientes");
        mnCadastroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCadastroClienteActionPerformed(evt);
            }
        });
        Opcoes.add(mnCadastroCliente);

        jMenuBar1.add(Opcoes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnCadastroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCadastroClienteActionPerformed
        // TODO add your handling code here:
        new OpcoesClienteForm().setVisible(true);
    }//GEN-LAST:event_mnCadastroClienteActionPerformed

    private void mnCadastroProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCadastroProdutoActionPerformed
        // TODO add your handling code here:
        new OpcoesProdutoForm().setVisible(true);
    }//GEN-LAST:event_mnCadastroProdutoActionPerformed

    private void mnCadastroFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCadastroFornecedorActionPerformed
        // TODO add your handling code here:
        new OpcoesFornecedorForm().setVisible(true);
    }//GEN-LAST:event_mnCadastroFornecedorActionPerformed

    private void edFornecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edFornecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edFornecActionPerformed

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        // TODO add your handling code here:
        venda = new Venda();
        limparEdits();
        edProduto.grabFocus();
        edVenda.setEnabled(false);
        edFornec.setEnabled(true);
        edProduto.setEnabled(true);
        edQtdProdutos.setEnabled(true);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(true);
        btNovo.setEnabled(false);
        
        btGravar.setEnabled(true);
        btCancelar.setEnabled(true);
        btExcluir.setEnabled(false);
    }//GEN-LAST:event_btNovoActionPerformed

    private void btGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarActionPerformed
        // TODO add your handling code here:
        editParaVenda();
        Integer id = vendaDAO.create(venda);
        edVenda.setText(id.toString());
        edValorTotal.setText(venda.getValorVenda().toString());

        btNovo.setEnabled(true);
        btCancelar.setEnabled(true);

        atualizarTabela();

        JOptionPane.showMessageDialog(this, "Gravado com sucesso! id: " + id, "Informação", JOptionPane.INFORMATION_MESSAGE);
        
        edVenda.setEnabled(false);
        edFornec.setEnabled(false);
        edProduto.setEnabled(false);
        edQtdProdutos.setEnabled(false);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(false);
        btNovo.setEnabled(true);
        
        btGravar.setEnabled(false);
        btCancelar.setEnabled(false);
        btExcluir.setEnabled(false);
    }//GEN-LAST:event_btGravarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        int resultado = JOptionPane.showConfirmDialog(this, "Confirma exclusão", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resultado == 0) {
            editParaVenda();
            int i = Integer.parseInt(edVenda.getText());
            try {
                vendaDAO.destroy(i);
                JOptionPane.showMessageDialog(this, "Excluido com sucesso", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(OpcoesClienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            atualizarTabela();
        }
        
        edVenda.setEnabled(false);
        edFornec.setEnabled(false);
        edProduto.setEnabled(false);
        edQtdProdutos.setEnabled(false);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(false);
        btNovo.setEnabled(true);
        
        btGravar.setEnabled(false);
        btCancelar.setEnabled(false);
        btExcluir.setEnabled(false);
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        // TODO add your handling code here:
        venda = null;
        limparEdits();
        
        edVenda.setEnabled(false);
        edFornec.setEnabled(false);
        edProduto.setEnabled(false);
        edQtdProdutos.setEnabled(false);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(false);
        btNovo.setEnabled(true);
        
        btGravar.setEnabled(false);
        btCancelar.setEnabled(false);
        btExcluir.setEnabled(false);
    }//GEN-LAST:event_btCancelarActionPerformed

    private void tbVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVendasMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            venda = vendas.get(tbVendas.getSelectedRow());
            vendaParaEdit();
        }
        edVenda.setEnabled(false);
        edFornec.setEnabled(true);
        edProduto.setEnabled(true);
        edQtdProdutos.setEnabled(true);
        edValorTotal.setEnabled(false);
        edCliente.setEnabled(true);
        btNovo.setEnabled(false);
        
        btGravar.setEnabled(false);
        btCancelar.setEnabled(true);
        btExcluir.setEnabled(true);
    }//GEN-LAST:event_tbVendasMouseClicked

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
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Opcoes;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btNovo;
    private javax.swing.JTextField edCliente;
    private javax.swing.JTextField edFornec;
    private javax.swing.JTextField edProduto;
    private javax.swing.JTextField edQtdProdutos;
    private javax.swing.JTextField edValorTotal;
    private javax.swing.JTextField edVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mnCadastroCliente;
    private javax.swing.JMenuItem mnCadastroFornecedor;
    private javax.swing.JMenuItem mnCadastroProduto;
    private javax.swing.JTable tbVendas;
    // End of variables declaration//GEN-END:variables
    private Venda venda = new Venda();
    private VendaDAO vendaDAO = new VendaDAO(emf);
    private List<Venda> vendas = new ArrayList<>();
    private VendaTableModel vendaTM;
    private Cliente cliente;
    private Produto produto;
    private Fornecedor fornec;
    private ClienteDAO clienteDAO;
    private FornecedorDAO fornecDAO;
    private ProdutoDAO produtoDAO;
    
    
        private void limparEdits() {
        edVenda.setText("");
        edFornec.setText("");
        edProduto.setText("");
        edQtdProdutos.setText("");
        edValorTotal.setText("");
        edCliente.setText("");
    }

    private void editParaVenda() {
        venda = new Venda();
        
        clienteDAO = new ClienteDAO(emf);
        cliente = clienteDAO.findCliente(Integer.parseInt(edCliente.getText()));
        
        produtoDAO = new ProdutoDAO(emf);
        produto = produtoDAO.findProduto(Integer.parseInt(edProduto.getText()));
        
        fornecDAO = new FornecedorDAO(emf);
        fornec = fornecDAO.findFornecedor(Integer.parseInt(edFornec.getText()));
        
        if (edVenda.getText() != null && !edVenda.getText().isEmpty()) {
            venda.setId(Integer.parseInt(edVenda.getText()));
        }
        
        venda.setIdCli(cliente);
        venda.setIdProd(produto);
        venda.setIdFornec(fornec);
        venda.setQtdItens(new BigDecimal(edQtdProdutos.getText()));
        venda.setValorVenda(new BigDecimal(edQtdProdutos.getText()).multiply(produto.getPrecoVenda()));
        
    }

    private void vendaParaEdit() {
        edVenda.setText(venda.getId().toString());
        edFornec.setText(venda.getIdFornec().getId().toString());
        edProduto.setText(venda.getIdProd().getId().toString());
        edCliente.setText(venda.getIdCli().getId().toString());
        edQtdProdutos.setText(venda.getQtdItens().toString());
        edValorTotal.setText(venda.getValorVenda().toString());                
    }
    private void atualizarTabela(){
        vendas = vendaDAO.findVendaEntities();
        vendaTM.setVendas(vendas);
        vendaTM.fireTableDataChanged();
    }
}
