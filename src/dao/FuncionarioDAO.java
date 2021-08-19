/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import connection.ConnectionFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Funcionario;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import types.Sexo;

/**
 *
 * @author lucas
 */
public class FuncionarioDAO {
    
    public void insert(Funcionario fun){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO funcionarios (codFun, cpf, nome, dataNasc, sexo, salario, dataAdmissao, codCargo, codEmp) VALUE (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setLong(1, fun.getCpf());
            stmt.setString(2, fun.getNome());
            stmt.setDate(3, fun.getDataNasc());
            stmt.setString(4, fun.getSexo().name());
            stmt.setDouble(5, fun.getSalario());
            stmt.setDate(6, fun.getDataAdmissao());
            stmt.setInt(7, fun.getCargo());
            stmt.setInt(8, fun.getEmpresa());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cadastro do funcionário efetuado com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void update(Funcionario fun){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("UPDATE funcionarios SET cpf = ?, nome = ?, dataNasc = ?, sexo = ?, salario = ?, dataAdmissao = ?, codCargo = ?, codEmp = ? WHERE codFun = ?");
            stmt.setLong(1, fun.getCpf());
            stmt.setString(2, fun.getNome());
            stmt.setDate(3, fun.getDataNasc());
            stmt.setString(4, fun.getSexo().name());
            stmt.setDouble(5, fun.getSalario());
            stmt.setDate(6, fun.getDataAdmissao());
            stmt.setInt(7, fun.getCargo());
            stmt.setInt(8, fun.getEmpresa());
            stmt.setInt(9, fun.getCodFuncionario());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionário alterado com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex, "Erro!", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
    
    public void updateSalario(Funcionario fun){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("UPDATE funcionarios SET salario = ? WHERE codFun = ?");
            stmt.setDouble(1, fun.getSalario());
            stmt.setInt(2, fun.getCodFuncionario());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
        
    public void delete(Funcionario fun){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("DELETE FROM funcionarios WHERE codFun = ?");
            stmt.setInt(1, fun.getCodFuncionario());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
    
    public List<Funcionario> selectAll(){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Funcionario fun = new Funcionario();
                
                fun.setCodFuncionario(rs.getInt("codFun"));
                fun.setCpf(rs.getLong("cpf"));
                fun.setNome(rs.getString("nome"));
                fun.setDataNasc(rs.getDate("dataNasc"));
                fun.setSexo(Enum.valueOf(Sexo.class, rs.getString("sexo")));
                fun.setSalario(rs.getDouble("salario"));
                fun.setDataAdmissao(rs.getDate("dataAdmissao"));
                fun.setCargo(rs.getInt("codCargo"));
                fun.setEmpresa(rs.getInt("codEmp"));
                
                
                funcionarios.add(fun);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }
    
    public List<Funcionario> selectByCodFun(int Cod){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios WHERE codFun=" + Cod);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Funcionario fun = new Funcionario();
                
                fun.setCodFuncionario(rs.getInt("codFun"));
                fun.setCpf(rs.getLong("cpf"));
                fun.setNome(rs.getString("nome"));
                fun.setDataNasc(rs.getDate("dataNasc"));
                fun.setSexo(Enum.valueOf(Sexo.class, rs.getString("sexo")));
                fun.setSalario(rs.getDouble("salario"));
                fun.setDataAdmissao(rs.getDate("dataAdmissao"));
                fun.setCargo(rs.getInt("codCargo"));
                fun.setEmpresa(rs.getInt("codEmp"));
                
                funcionarios.add(fun);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }    
    
    public List<Funcionario> selectByCodCargo(int Cod){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT codFun, salario FROM funcionarios WHERE codCargo=" + Cod);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Funcionario fun = new Funcionario();
                
                fun.setCodFuncionario(rs.getInt("codFun"));
                fun.setSalario(rs.getDouble("salario"));
                
                funcionarios.add(fun);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }
    
    public List<Funcionario> selectByCodEmp(int Cod){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT codFun, salario FROM funcionarios WHERE codEmp=" + Cod);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Funcionario fun = new Funcionario();
                
                fun.setCodFuncionario(rs.getInt("codFun"));
                fun.setSalario(rs.getDouble("salario"));
                
                funcionarios.add(fun);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }
}
