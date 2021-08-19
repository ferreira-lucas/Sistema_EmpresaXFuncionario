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
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import main.Cargo;

/**
 *
 * @author lucas
 */
public class CargoDAO {
    
    public void insert(Cargo cargo){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO cargos (codCargo, cargo) VALUE (DEFAULT, ?)");
            stmt.setString(1, cargo.getCargo());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cadastro do cargo efetuado com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void update(Cargo cargo){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("UPDATE cargos SET cargo = ? WHERE codCargo = ?");
            stmt.setString(1, cargo.getCargo());
            stmt.setInt(2, cargo.getCodCargo());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cargo alterado com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
        
    public void delete(Cargo cargo){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
       try {
            stmt = con.prepareStatement("DELETE FROM cargos WHERE codCargo = ?");
            stmt.setInt(1, cargo.getCodCargo());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cargo excluído com sucesso!", "Sucesso!", JOptionPane.OK_OPTION);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    } 
    
    public List<Cargo> selectAll(){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Cargo> cargos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM cargos");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Cargo carg = new Cargo();
                
                carg.setCodCargo(rs.getInt("codCargo"));
                carg.setCargo(rs.getString("cargo"));
                
                cargos.add(carg);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return cargos;
    }
    
    public List<Cargo> selectByCod(int Cod){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Cargo> cargos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM cargos WHERE codCargo=" + Cod);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Cargo carg = new Cargo();
                
                carg.setCodCargo(rs.getInt("codCargo"));
                carg.setCargo(rs.getString("cargo"));
                
                cargos.add(carg);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex, "Erro!", JOptionPane.ERROR);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return cargos;
    }
    
}
