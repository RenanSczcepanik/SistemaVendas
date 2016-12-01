/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.view;

import br.edu.uricer.model.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author renan
 */
public class FornecedorTableModel extends AbstractTableModel {

    private List<Fornecedor> fornecs = new ArrayList();

    public FornecedorTableModel(List<Fornecedor> fornecs) {
        this.fornecs = fornecs;
    }
    public void setFornecs(List<Fornecedor> fornecs){
        this.fornecs = fornecs;
    }
    @Override
    public int getRowCount() {
        return fornecs.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fornecedor fornec = fornecs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return fornec.getId();
            case 1:
                return fornec.getNome();
            case 2:
                return fornec.getCidade();
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
                return "Cidade";
            default:
                throw new AssertionError();
        }
    }
    
}
