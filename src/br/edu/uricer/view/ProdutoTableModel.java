/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.view;

import br.edu.uricer.dao.ProdutoDAO;
import br.edu.uricer.model.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author renan
 */
public class ProdutoTableModel extends AbstractTableModel {

    private List<Produto> produtos = new ArrayList();

    public ProdutoTableModel(List<Produto> produtos) {
        this.produtos = produtos;
    }
    public void setProdutos(List<Produto> produtos){
        this.produtos = produtos;
    }
    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = produtos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return produto.getId();
            case 1:
                return produto.getNome();
            case 2:
                return produto.getPrecoCusto();
            case 3:
                return produto.getPrecoVenda();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Nome";
            case 2:
                return "Preco Custo";
            case 3:
                return "Preco Venda";
            default:
                throw new AssertionError();
        }
    }
}
