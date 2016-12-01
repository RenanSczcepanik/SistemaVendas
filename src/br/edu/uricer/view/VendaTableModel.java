/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.view;

import br.edu.uricer.model.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author renan
 */
public class VendaTableModel extends AbstractTableModel{
    
    private List<Venda> vendas = new ArrayList();

    public VendaTableModel(List<Venda> vendas) {
        this.vendas = vendas;
    }
    public void setVendas(List<Venda> vendas){
        this.vendas = vendas;
    }
    @Override
    public int getRowCount() {
        return vendas.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Venda venda = vendas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return venda.getId();
            case 1:
                return venda.getIdCli().getNome();
            case 2:
                return venda.getIdFornec().getNome();
            case 3:
                return venda.getIdProd().getNome();
            case 4:
                return venda.getValorVenda();
            case 5:
                return venda.getQtdItens();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id Venda";
            case 1:
                return "Nome Cliente";
            case 2:
                return "Nome Fornecedor";
            case 3:
                return "Nome Produto";
            case 4:
                return "Valor Total";
            case 5:
                return "Qtd Produtos";
            
            default:
                throw new AssertionError();
        }
    }
}
