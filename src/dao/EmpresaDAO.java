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
import main.Empresa;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class EmpresaDAO {
    
    public void insert(Empresa emp){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO empresas (codEmp, cnpj, razaoSocial, nomeFantasia, ramoAtuacao) VALUE (DEFAULT, ?, ?, ?, ?)");
            stmt.setLong(1, emp.getCnpj());
            stmt.setString(2, emp.getRazaoSocial());
            stmt.setString(3, emp.getNomeFantasia());
            stmt.setString(4, emp.getRamoAtuacao());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cadastro da empresa efetuado com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void update(Empresa emp){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("UPDATE empresas SET cnpj = ?, razaoSocial = ?, nomeFantasia = ?, ramoAtuacao = ? WHERE codEmp = ?");
            stmt.setLong(1, emp.getCnpj());
            stmt.setString(2, emp.getRazaoSocial());
            stmt.setString(3, emp.getNomeFantasia());
            stmt.setString(4, emp.getRamoAtuacao());
            stmt.setInt(5, emp.getCodEmpresa());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Empresa alterada com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
        
    public void delete(Empresa emp){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("DELETE FROM empresas WHERE codEmp = ?");
            stmt.setInt(1, emp.getCodEmpresa());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Empresa excluída com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
    
    public List<Empresa> selectAll(){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Empresa> empresas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM empresas");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Empresa emp = new Empresa();
                
                emp.setCodEmpresa(rs.getInt("codEmp"));
                emp.setCnpj(rs.getLong("cnpj"));
                emp.setRazaoSocial(rs.getString("razaoSocial"));
                emp.setNomeFantasia(rs.getString("nomeFantasia"));
                emp.setRamoAtuacao(rs.getString("ramoAtuacao"));
                
                empresas.add(emp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return empresas;
    }
    
    public List<Empresa> selectByCod(int Cod){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Empresa> empresas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM empresas WHERE codEmp=" + Cod);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Empresa emp = new Empresa();
                
                emp.setCodEmpresa(rs.getInt("codEmp"));
                emp.setCnpj(rs.getLong("cnpj"));
                emp.setRazaoSocial(rs.getString("razaoSocial"));
                emp.setNomeFantasia(rs.getString("nomeFantasia"));
                emp.setRamoAtuacao(rs.getString("ramoAtuacao"));
                
                empresas.add(emp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return empresas;
    }
    
}
