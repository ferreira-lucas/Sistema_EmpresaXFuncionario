/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EmpresaDAO;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import main.Empresa;
import model.PesquisaTableModel;

/**
 *
 * @author lucas
 */
public class CadEmpresas extends javax.swing.JInternalFrame {

    /**
     * Creates new form CadEmpresas
     */
    private String titulo = "Pesquisar Empresas";
    private final String[] colunas = {"Código", "CNPJ", "Razão Social", "Nome Fantasia", "Ramo de Atuação"};
    private final String modelo = "Empresa";
    private TelaPrincipal auxTP;

    public CadEmpresas(TelaPrincipal aux) {
        initComponents();
        String []ramos = {"Indústria", "Comércio", "Serviços"};
        jComboRamo.setModel(new DefaultComboBoxModel(ramos));
        auxTP = aux;
        formatarCampoCNPJ(1);
    }
    
    public JComboBox<Object> getjComboRamo() {
        return jComboRamo;
    }

    public JFormattedTextField getjFTFCNPJ() {
        return jFTFCNPJ;
    }

    public JTextField getjTFCod() {
        return jTFCod;
    }

    public JTextField getjTFFantasia() {
        return jTFFantasia;
    }

    public JTextField getjTFRazao() {
        return jTFRazao;
    }
    
    public void limparCampos(){
        jTFCod.setText("");
        jFTFCNPJ.setText("");
        jTFRazao.setText("");
        jTFFantasia.setText("");
        jComboRamo.setSelectedItem(null);
    }
    
    public void cadastrarEmpresa(){
        Empresa emp = new Empresa();
        EmpresaDAO empDAO = new EmpresaDAO();
        
        emp.setCnpj(Long.parseLong(formatarCampoCNPJ(2)));
        emp.setRazaoSocial(jTFRazao.getText());
        emp.setNomeFantasia(jTFFantasia.getText());
        emp.setRamoAtuacao(jComboRamo.getSelectedItem().toString());
        
        empDAO.insert(emp);
        
        limparCampos();
        jTFCod.requestFocus();
    }
    
    public void alterarEmpresa(){
        Empresa emp = new Empresa();
        EmpresaDAO empDAO = new EmpresaDAO();
        
        emp.setCnpj(Long.parseLong(formatarCampoCNPJ(2)));
        emp.setRazaoSocial(jTFRazao.getText());
        emp.setNomeFantasia(jTFFantasia.getText());
        emp.setRamoAtuacao(jComboRamo.getSelectedItem().toString());
        emp.setCodEmpresa(Integer.parseInt(jTFCod.getText()));
        
        empDAO.update(emp);
        
        limparCampos();
        jTFCod.requestFocus();
    }
    
    public void excluirEmpresa(){
        Empresa emp = new Empresa();
        EmpresaDAO empDAO = new EmpresaDAO();
        
        emp.setCodEmpresa(Integer.parseInt(jTFCod.getText()));
        
        empDAO.delete(emp);
        
        limparCampos();
    }
    
    public void preencherCampos(){
        EmpresaDAO empDAO = new EmpresaDAO();
        List<Empresa> dados = empDAO.selectByCod(Integer.parseInt(jTFCod.getText()));
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(null, "Novo registro!", "Aviso", JOptionPane.OK_OPTION);
            limparCampos();
            jFTFCNPJ.requestFocus();
        }else{
            jFTFCNPJ.setText(String.valueOf(dados.get(0).getCnpj()));
            jTFRazao.setText(String.valueOf(dados.get(0).getRazaoSocial()));
            jTFFantasia.setText(String.valueOf(dados.get(0).getNomeFantasia()));
            jComboRamo.setSelectedItem(String.valueOf(dados.get(0).getRamoAtuacao()));
            jFTFCNPJ.requestFocus();
        }
    }
    
    private String formatarCampoCNPJ(int opcao){//opção 1 = formata, opção 2 = desformata
        String retorno = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
                mask.install(jFTFCNPJ);
            }else if(opcao == 2){
                retorno = jFTFCNPJ.getText().replace(".", "").replace("/", "").replace("-", "").replace(" ", "");
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTFFantasia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboRamo = new javax.swing.JComboBox<>();
        jTFRazao = new javax.swing.JTextField();
        jBtnPesquisar = new javax.swing.JButton();
        jBtnSalvar = new javax.swing.JButton();
        jBtnExcluir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTFCod = new javax.swing.JTextField();
        jFTFCNPJ = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Cadastro de Empresas");

        jTFFantasia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFFantasiaKeyPressed(evt);
            }
        });

        jLabel1.setText("Razão Social");

        jLabel3.setText("CNPJ");

        jLabel4.setText("Nome Fantasia");

        jLabel5.setText("Ramo de atuação");

        jComboRamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboRamoKeyPressed(evt);
            }
        });

        jTFRazao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFRazaoKeyPressed(evt);
            }
        });

        jBtnPesquisar.setText("...");
        jBtnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarActionPerformed(evt);
            }
        });

        jBtnSalvar.setText("Salvar");
        jBtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalvarActionPerformed(evt);
            }
        });
        jBtnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBtnSalvarKeyPressed(evt);
            }
        });

        jBtnExcluir.setText("Excluir");
        jBtnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExcluirActionPerformed(evt);
            }
        });

        jLabel6.setText("Código");

        jTFCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFCodKeyPressed(evt);
            }
        });

        jFTFCNPJ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFCNPJFocusGained(evt);
            }
        });
        jFTFCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTFCNPJKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(82, Short.MAX_VALUE)
                        .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jBtnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFRazao)
                            .addComponent(jTFFantasia)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTFCod, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jFTFCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboRamo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jFTFCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboRamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jBtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalvarActionPerformed
        if (!jTFCod.getText().equalsIgnoreCase("")) {
            alterarEmpresa();
        }else{
            cadastrarEmpresa();
        }
    }//GEN-LAST:event_jBtnSalvarActionPerformed

    private void jFTFCNPJFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFCNPJFocusGained
        if(jFTFCNPJ.getText().equals("")){
            formatarCampoCNPJ(1);
        }
    }//GEN-LAST:event_jFTFCNPJFocusGained

    private void jBtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluirActionPerformed
        if(jTFCod.getText() != null){
            excluirEmpresa();
        }
    }//GEN-LAST:event_jBtnExcluirActionPerformed

    private void jTFCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFCodKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFCod.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Novo registro!", "Aviso", JOptionPane.OK_OPTION);
            limparCampos();
            jFTFCNPJ.requestFocus();
        }else if(evt.getKeyCode() == evt.VK_ENTER && jTFCod.getText() != null){
            preencherCampos();
        }
    }//GEN-LAST:event_jTFCodKeyPressed

    private void jFTFCNPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTFCNPJKeyPressed
        String aux = formatarCampoCNPJ(2);
        if(evt.getKeyCode() == evt.VK_ENTER && aux.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER && aux.length() < 14){
            JOptionPane.showMessageDialog(null, "CNPJ inválido!", "Aviso", JOptionPane.OK_OPTION);
            jFTFCNPJ.setText("");
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jTFRazao.requestFocus();
        }
    }//GEN-LAST:event_jFTFCNPJKeyPressed

    private void jTFRazaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFRazaoKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFRazao.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jTFFantasia.requestFocus();
        }
    }//GEN-LAST:event_jTFRazaoKeyPressed

    private void jTFFantasiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFantasiaKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFFantasia.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jComboRamo.requestFocus();
        }
    }//GEN-LAST:event_jTFFantasiaKeyPressed

    private void jComboRamoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboRamoKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jComboRamo.getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jBtnSalvar.requestFocus();
        }
    }//GEN-LAST:event_jComboRamoKeyPressed

    private void jBtnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarActionPerformed
        EmpresaDAO empDAO = new EmpresaDAO();
        PesquisaTableModel etm = new PesquisaTableModel(empDAO.selectAll(), colunas, modelo);
        Pesquisar p = new Pesquisar(titulo, etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarActionPerformed

    private void jBtnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnSalvarKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            if (!jTFCod.getText().equalsIgnoreCase("")) {
                alterarEmpresa();
            }else{
                cadastrarEmpresa();
            }
        }
    }//GEN-LAST:event_jBtnSalvarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnExcluir;
    private javax.swing.JButton jBtnPesquisar;
    private javax.swing.JButton jBtnSalvar;
    private javax.swing.JComboBox<Object> jComboRamo;
    private javax.swing.JFormattedTextField jFTFCNPJ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTFCod;
    private javax.swing.JTextField jTFFantasia;
    private javax.swing.JTextField jTFRazao;
    // End of variables declaration//GEN-END:variables
}