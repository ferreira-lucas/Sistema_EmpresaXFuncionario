/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PesquisaTableModel;

/**
 *
 * @author lucas
 */
public class Pesquisar extends javax.swing.JInternalFrame {
    /**
     * Creates new form Pesquisar
     */
    private final PesquisaTableModel modelo;
    private final Object internalFrameAtual;
    
    public Pesquisar(String titulo, PesquisaTableModel tableModel, Object frameAtual) {
        initComponents();
        setTitle(titulo);
        
        modelo = tableModel;
        internalFrameAtual = frameAtual;
        jTablePesquisar.setModel(tableModel);
    }
    
    public void fecharJanela(){
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Pesquisar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePesquisar = new javax.swing.JTable();

        setClosable(true);
        setTitle("Pesquisar");

        jTablePesquisar.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePesquisarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablePesquisar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePesquisarMouseClicked
        if(evt.getClickCount() == 2){
            int index = jTablePesquisar.getSelectedRow();
            switch(modelo.getAuxModel()){
                case "Empresa":
                    CadEmpresas frameEmpresa = (CadEmpresas) internalFrameAtual;
                    frameEmpresa.getjTFCod().setText(modelo.getValueAt(index, 0).toString());
                    frameEmpresa.getjFTFCNPJ().setText(modelo.getValueAt(index, 1).toString());
                    frameEmpresa.getjTFRazao().setText(modelo.getValueAt(index, 2).toString());
                    frameEmpresa.getjTFFantasia().setText(modelo.getValueAt(index, 3).toString());
                    frameEmpresa.getjComboRamo().setSelectedItem(modelo.getValueAt(index, 4).toString());

                    frameEmpresa.updateUI();

                    fecharJanela();
                    break;
                case "Cargo":
                    CadCargos frameCargo = (CadCargos) internalFrameAtual;
                    frameCargo.getjTFCod().setText(modelo.getValueAt(index, 0).toString());
                    frameCargo.getjTFCargo().setText(modelo.getValueAt(index, 1).toString());

                    frameCargo.updateUI();

                    fecharJanela();
                    break;
                case "Funcionario":
                    CadFuncionarios frameFuncionario = (CadFuncionarios) internalFrameAtual;
                    frameFuncionario.getjTFCod().setText(modelo.getValueAt(index, 0).toString());
                    frameFuncionario.getjFTFCPF().setText(modelo.getValueAt(index, 1).toString());
                    frameFuncionario.getjTFNome().setText(modelo.getValueAt(index, 2).toString());
                    frameFuncionario.getjFTFDataNasc().setText(modelo.getValueAt(index, 3).toString());
                    if(modelo.getValueAt(index, 4).toString().contains("M")){
                        frameFuncionario.getjComboSexo().setSelectedItem("Masculino");
                    }else if(modelo.getValueAt(index, 4).toString().contains("F")){
                        frameFuncionario.getjComboSexo().setSelectedItem("Feminino");
                    }
                    frameFuncionario.getjFTFSalario().setText(modelo.getValueAt(index, 5).toString());
                    frameFuncionario.getjFTFDataAdm().setText(modelo.getValueAt(index, 6).toString());
                    frameFuncionario.getjTFCargo().setText(modelo.getValueAt(index, 7).toString());
                    frameFuncionario.getjTFEmpresa().setText(modelo.getValueAt(index, 8).toString());

                    frameFuncionario.updateUI();

                    fecharJanela();
                    break;
                case "FunXCargo":
                    frameFuncionario = (CadFuncionarios) internalFrameAtual;
                    frameFuncionario.getjTFCargo().setText(modelo.getValueAt(index, 0).toString());
                    
                    frameFuncionario.updateUI();

                    fecharJanela();
                    break;                    
                case "FunXEmpresa":
                    frameFuncionario = (CadFuncionarios) internalFrameAtual;
                    frameFuncionario.getjTFEmpresa().setText(modelo.getValueAt(index, 0).toString());
                    
                    frameFuncionario.updateUI();

                    fecharJanela();
                    break;
                case "AltEmp":
                    AlterarSalario frameAlterar = (AlterarSalario) internalFrameAtual;
                    frameAlterar.limparCampos();
                    frameAlterar.getjTFEmpresa().setText(modelo.getValueAt(index, 0).toString() + " " + modelo.getValueAt(index, 2).toString());
                    frameAlterar.getjTFCargo().setEnabled(false);
                    frameAlterar.getjTFFuncionario().setEnabled(false);
                    
                    frameAlterar.updateUI();

                    fecharJanela();
                    break;
                case "AltCarg":
                    frameAlterar = (AlterarSalario) internalFrameAtual;
                    frameAlterar.limparCampos();
                    frameAlterar.getjTFCargo().setText(modelo.getValueAt(index, 0).toString() + " " + modelo.getValueAt(index, 1).toString());
                    frameAlterar.getjTFEmpresa().setEnabled(false);
                    frameAlterar.getjTFFuncionario().setEnabled(false);
                    
                    frameAlterar.updateUI();

                    fecharJanela();
                    break;
                case "AltFun":
                    frameAlterar = (AlterarSalario) internalFrameAtual;
                    frameAlterar.limparCampos();
                    frameAlterar.getjTFFuncionario().setText(modelo.getValueAt(index, 0).toString() + " " + modelo.getValueAt(index, 2).toString());
                    frameAlterar.getjTFEmpresa().setEnabled(false);
                    frameAlterar.getjTFCargo().setEnabled(false);
                    
                    frameAlterar.updateUI();

                    fecharJanela();
                    break;
            }
        }
    }//GEN-LAST:event_jTablePesquisarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablePesquisar;
    // End of variables declaration//GEN-END:variables
}
