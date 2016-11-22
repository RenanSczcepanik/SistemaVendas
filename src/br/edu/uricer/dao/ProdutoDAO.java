/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author renan
 */
public class ProdutoDAO {

    private Connection con;

    public ProdutoDAO(Connection con) {
        this.con = con;
    }

    public Integer create(Produto produto) throws SQLException {
        String sql = "insert into Produtos(nome, preco_venda, preco_custo) values (?,?,?)";
        Integer idCriado = 0;
        try (PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, produto.getNome());
            stm.setBigDecimal(2, produto.getPrecoVenda());
            stm.setBigDecimal(3, produto.getPrecoCusto());
            stm.execute();

            try (ResultSet resultSet = stm.getGeneratedKeys()) {
                while (resultSet.next()) {
                    idCriado = resultSet.getInt(1);
                }
            }
            con.commit();
        } catch (Exception ex) {
            System.out.println("Erro ao tentar executar insercao: " + ex.getMessage());
            con.rollback();
        }

        return idCriado;
    }

    public Produto findById(Integer id) throws SQLException {
        String sql = "Select * from produtos p where p.id = ?";
        Produto produto = null;
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.execute();

            try (ResultSet resultSet = stm.getResultSet()) {
                while (resultSet.next()) {
                    produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setPrecoVenda(resultSet.getBigDecimal("preco_venda"));
                    produto.setPrecoCusto(resultSet.getBigDecimal("preco_custo"));
                }
            }
        }

        return produto;
    }

    public List<Produto> findByNome(String nome) throws SQLException {
        String sql = "Select * from produtos p where upper(p.nome) like ?";
        List<Produto> produtos = new ArrayList<>();
        Produto produto = null;
        try (PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setString(1, "%" + nome.toUpperCase() + "%");
            stm.execute();

            try (ResultSet resultSet = stm.getResultSet()) {
                while (resultSet.next()) {
                    produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setPrecoVenda(resultSet.getBigDecimal("preco_venda"));
                    produto.setPrecoCusto(resultSet.getBigDecimal("preco_custo"));
                    produtos.add(produto);
                }
            }
        }

        return produtos;
    }

    public void update(Produto produto) throws SQLException {
        String sql = "update produtos set nome = ?, preco_venda = ?, preco_custo = ? where id = ?";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, produto.getNome());
            stm.setBigDecimal(2, produto.getPrecoVenda());
            stm.setBigDecimal(3, produto.getPrecoCusto());
            stm.setInt(4, produto.getId());
            stm.executeUpdate();

            con.commit();
        } catch (Exception ex) {
            System.out.println("Erro ao tentar executar atualização: " + ex.getMessage());
            con.rollback();
        }
    }

    public void delete(Produto produto) throws SQLException {
        String sql = "delete from produtos where id = ?";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, produto.getId());
            stm.executeUpdate();

            con.commit();
        } catch (Exception ex) {
            System.out.println("Erro ao tentar excluir: " + ex.getMessage());
            con.rollback();
        }
    }
}
