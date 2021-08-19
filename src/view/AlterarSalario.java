/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.CargoDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import main.Cargo;
import main.Empresa;
import main.Funcionario;
import types.Sexo;
import model.PesquisaTableModel;

/**
 *
 * @author lucas
 */
public class AlterarSalario extends javax.swing.JInternalFrame {

    /**
     * Creates new form AlterarSalario
     */
    private final DecimalFormat df = new DecimalFormat("#,###.00");
    private final String[] colunasFuncionario = {"Código", "CPF", "Nome", "Data Nascimento", "Sexo", "Salário", "Data de Admissão", "Cargo", "Empresa"};
    private final String[] colunasCargo = {"Código", "Cargo"};
    private final String[] colunasEmpresa = {"Código", "CNPJ", "Razão Social", "Nome Fantasia", "Ramo de Atuação"};
    private final TelaPrincipal auxTP;
    
    public AlterarSalario(TelaPrincipal aux) {
        initComponents();
        auxTP = aux;
        formatarCampoSalarioAtual(1);
        formatarCampoSalarioNovo(1);
    }

    public JTextField getjTFCargo() {
        return jTFCargo;
    }

    public JTextField getjTFEmpresa() {
        return jTFEmpresa;
    }

    public JTextField getjTFFuncionario() {
        return jTFFuncionario;
    }
    
    public void limparCampos(){
        jTFEmpresa.setText("");
        jTFCargo.setText("");
        jTFFuncionario.setText("");
        jTFPercent.setText("");
        jTFValor.setText("");
        jFTFSalarioAtual.setText("");
        jFTFSalarioNovo.setText("");
        formatarCampoSalarioAtual(1);
        formatarCampoSalarioNovo(1);
    }
    
    public void preencherCampoEmpresa(){
        EmpresaDAO empDAO = new EmpresaDAO();
        List<Empresa> dados = empDAO.selectByCod(Integer.parseInt(jTFEmpresa.getText()));
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(null, "Empresa não cadastrada", "Aviso", JOptionPane.OK_OPTION);
            jTFEmpresa.setText("");
        }else{
            limparCampos();
            jTFEmpresa.setText(String.valueOf(dados.get(0).getCodEmpresa()) + " " + String.valueOf(dados.get(0).getRazaoSocial()));
            jTFCargo.setEnabled(false);
            jTFFuncionario.setEnabled(false);
            jTFPercent.requestFocus();
        }
    }
    
    public void preencherCampoCargo(){
        CargoDAO cargoDAO = new CargoDAO();
        List<Cargo> dados = cargoDAO.selectByCod(Integer.parseInt(jTFCargo.getText()));
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(null, "Cargo não cadastrado", "Aviso", JOptionPane.OK_OPTION);
            jTFCargo.setText("");
        }else{
            limparCampos();
            jTFCargo.setText(String.valueOf(dados.get(0).getCodCargo()) + " " + String.valueOf(dados.get(0).getCargo()));
            jTFEmpresa.setEnabled(false);
            jTFFuncionario.setEnabled(false);
            jTFPercent.requestFocus();
        }
    }
    
    public void preencherCampoFuncionario(){
        FuncionarioDAO funDAO = new FuncionarioDAO();
        List<Funcionario> dados = funDAO.selectByCodFun(Integer.parseInt(jTFFuncionario.getText()));
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(null, "Funcionário não cadastrado", "Aviso", JOptionPane.OK_OPTION);
            jTFFuncionario.setText("");
        }else{
            limparCampos();
            jTFFuncionario.setText(String.valueOf(dados.get(0).getCodFuncionario()) + " " + String.valueOf(dados.get(0).getNome()));
            jFTFSalarioAtual.setText(String.valueOf(df.format(dados.get(0).getSalario())));
            jTFEmpresa.setEnabled(false);
            jTFCargo.setEnabled(false);
            jTFPercent.requestFocus();
        }
    }
    
    public void alterarSalario(String empresa, String cargo, String funcionario){
        Funcionario fun = new Funcionario();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        List<Funcionario> dados = null;
        
        if(empresa.equalsIgnoreCase("<...TODAS...>") || cargo.equalsIgnoreCase("<...TODOS...>") || funcionario.equalsIgnoreCase("<...TODOS...>")){
             dados = funDAO.selectAll();
        }else if(!empresa.isEmpty()){
            int codEmp = Integer.parseInt(String.valueOf(jTFEmpresa.getText().charAt(0)).replace(" ", ""));
            dados = funDAO.selectByCodEmp(codEmp);
        }else if(!cargo.isEmpty()){
            int codCargo = Integer.parseInt(String.valueOf(jTFCargo.getText().charAt(0)).replace(" ", ""));
            dados = funDAO.selectByCodCargo(codCargo);
        }else if(!funcionario.isEmpty()){
            int codFun = Integer.parseInt(String.valueOf(jTFFuncionario.getText().charAt(0)).replace(" ", ""));
            dados = funDAO.selectByCodFun(codFun);
        }
            if(!jTFPercent.getText().isEmpty()){
                for(int i=0; i < dados.size(); i++){
                    Double salario = (dados.get(i).getSalario()*(100 + Double.parseDouble(jTFPercent.getText())))/100;
                    fun.setSalario(salario);
                    fun.setCodFuncionario(dados.get(i).getCodFuncionario());
                    funDAO.updateSalario(fun);
                }
            }else if(!jTFValor.getText().isEmpty()){
                for(int i=0; i < dados.size(); i++){
                    Double salario = (dados.get(i).getSalario() + Double.parseDouble(jTFValor.getText()));
                    fun.setSalario(salario);
                    fun.setCodFuncionario(dados.get(i).getCodFuncionario());
                    funDAO.updateSalario(fun);
                }
            }
            limparCampos();
            jTFEmpresa.requestFocus();
    }
    
    private Double formatarCampoSalarioAtual(int opcao){// opção 1 = formata, opção 2 = desformata
        Double salarioAtual = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("R$**********");
                mask.install(jFTFSalarioAtual);
            }else if(opcao == 2){
                salarioAtual = Double.parseDouble(jFTFSalarioAtual.getText().replace("R$", "").replace(".", "").replace(",", ".").replace(" ", ""));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salarioAtual;
    }
    
    private Double formatarCampoSalarioNovo(int opcao){// opção 1 = formata, opção 2 = desformata
        Double salarioNovo = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("R$**********");
                mask.install(jFTFSalarioNovo);
            }else if(opcao == 2){
                salarioNovo = Double.parseDouble(jFTFSalarioNovo.getText().replace("R$", "").replace(",", ".").replace(" ", ""));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salarioNovo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTFEmpresa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFCargo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFFuncionario = new javax.swing.JTextField();
        jBtnPesquisarEmpresa = new javax.swing.JButton();
        jBtnPesquisarCargo = new javax.swing.JButton();
        jBtnPesquisarFuncionario = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jTFPercent = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFValor = new javax.swing.JTextField();
        jBtnSalvar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jFTFSalarioAtual = new javax.swing.JFormattedTextField();
        jFTFSalarioNovo = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Alterar Salários");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTFEmpresa.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFEmpresaCaretUpdate(evt);
            }
        });
        jTFEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFEmpresaKeyPressed(evt);
            }
        });

        jLabel1.setText("Empresa");

        jLabel2.setText("Cargo");

        jTFCargo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFCargoCaretUpdate(evt);
            }
        });
        jTFCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFCargoKeyPressed(evt);
            }
        });

        jLabel3.setText("Funcionário");

        jTFFuncionario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFFuncionarioCaretUpdate(evt);
            }
        });
        jTFFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFFuncionarioKeyPressed(evt);
            }
        });

        jBtnPesquisarEmpresa.setText("...");
        jBtnPesquisarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarEmpresaActionPerformed(evt);
            }
        });

        jBtnPesquisarCargo.setText("...");
        jBtnPesquisarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarCargoActionPerformed(evt);
            }
        });

        jBtnPesquisarFuncionario.setText("...");
        jBtnPesquisarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarFuncionarioActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jTFEmpresa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFCargo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFFuncionario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisarEmpresa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisarCargo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisarFuncionario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTFCargo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFFuncionario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPesquisarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jBtnPesquisarEmpresa))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarCargo))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarFuncionario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTFPercent.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFPercentCaretUpdate(evt);
            }
        });
        jTFPercent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFPercentKeyPressed(evt);
            }
        });

        jLabel4.setText("Percentual");

        jLabel5.setText("%");

        jLabel6.setText("Valor R$");

        jTFValor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFValorCaretUpdate(evt);
            }
        });
        jTFValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFValorKeyPressed(evt);
            }
        });

        jBtnSalvar.setText("Salvar");
        jBtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalvarActionPerformed(evt);
            }
        });

        jLabel7.setText("Salário atual");

        jLabel8.setText("Novo salário");

        jLayeredPane2.setLayer(jTFPercent, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jTFValor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jBtnSalvar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jFTFSalarioAtual, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jFTFSalarioNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jTFPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel7))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addComponent(jTFValor, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFTFSalarioAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTFSalarioNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jFTFSalarioAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jFTFSalarioNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane2)
                    .addComponent(jLayeredPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnPesquisarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarEmpresaActionPerformed
        EmpresaDAO empDAO = new EmpresaDAO();
        PesquisaTableModel etm = new PesquisaTableModel(empDAO.selectAll(), colunasEmpresa, "AltEmp");
        Pesquisar p = new Pesquisar("Pesquisar Empresas", etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarEmpresaActionPerformed

    private void jBtnPesquisarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarFuncionarioActionPerformed
        FuncionarioDAO funDAO = new FuncionarioDAO();
        PesquisaTableModel etm = new PesquisaTableModel(funDAO.selectAll(), colunasFuncionario, "AltFun");
        Pesquisar p = new Pesquisar("Pesquisar Funcionários", etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarFuncionarioActionPerformed

    private void jBtnPesquisarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarCargoActionPerformed
        CargoDAO cargoDAO = new CargoDAO();
        PesquisaTableModel etm = new PesquisaTableModel(cargoDAO.selectAll(), colunasCargo, "AltCarg");
        Pesquisar p = new Pesquisar("Pesquisar Cargos", etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarCargoActionPerformed

    private void jTFEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFEmpresaKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFEmpresa.getText().equalsIgnoreCase("")){
            limparCampos();
            jTFEmpresa.setText("<...TODAS...>");
            jTFCargo.setEnabled(false);
            jTFFuncionario.setEnabled(false);
            jTFPercent.requestFocus();
        }else if(evt.getKeyCode() == evt.VK_ENTER && jTFEmpresa.getText() != null){
            preencherCampoEmpresa();
        }
    }//GEN-LAST:event_jTFEmpresaKeyPressed

    private void jTFCargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFCargoKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFCargo.getText().equalsIgnoreCase("")){
            limparCampos();
            jTFCargo.setText("<...TODOS...>");
            jTFEmpresa.setEnabled(false);
            jTFFuncionario.setEnabled(false);
            jTFPercent.requestFocus();
        }else if(evt.getKeyCode() == evt.VK_ENTER && jTFCargo.getText() != null){
            preencherCampoCargo();
        }
    }//GEN-LAST:event_jTFCargoKeyPressed

    private void jTFFuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFuncionarioKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFFuncionario.getText().equalsIgnoreCase("")){
            limparCampos();
            jTFFuncionario.setText("<...TODOS...>");
            jTFEmpresa.setEnabled(false);
            jTFCargo.setEnabled(false);
            jTFPercent.requestFocus();
            jFTFSalarioAtual.setEnabled(false);
            jFTFSalarioNovo.setEnabled(false);
        }else if(evt.getKeyCode() == evt.VK_ENTER && jTFFuncionario.getText() != null){
            String aux = jTFFuncionario.getText();
            limparCampos();
            jTFFuncionario.setText(aux);
            jTFEmpresa.setEnabled(false);
            jTFCargo.setEnabled(false);
            jFTFSalarioAtual.setEnabled(true);
            jFTFSalarioNovo.setEnabled(true);
            preencherCampoFuncionario();
        }
    }//GEN-LAST:event_jTFFuncionarioKeyPressed

    private void jTFValorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFValorCaretUpdate
        if(!jTFValor.getText().equalsIgnoreCase("")){
            jTFPercent.setEnabled(false);
        }else{
            jTFPercent.setEnabled(true);
        }
    }//GEN-LAST:event_jTFValorCaretUpdate

    private void jTFPercentCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFPercentCaretUpdate
        if(!jTFPercent.getText().equalsIgnoreCase("")){
            jTFValor.setEnabled(false);
        }else{
            jTFValor.setEnabled(true);
        }
    }//GEN-LAST:event_jTFPercentCaretUpdate

    private void jBtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalvarActionPerformed
        if(!jFTFSalarioNovo.getText().isEmpty()){
            alterarSalario(jTFEmpresa.getText(), jTFCargo.getText(), jTFFuncionario.getText());
            JOptionPane.showMessageDialog(null, "Um ou mais salários alterados com sucesso!", "Aviso!", JOptionPane.OK_OPTION);
            limparCampos();
        }
    }//GEN-LAST:event_jBtnSalvarActionPerformed

    private void jTFEmpresaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFEmpresaCaretUpdate
        if(jTFEmpresa.getText().equalsIgnoreCase("")){
           jTFCargo.setEnabled(true);
           jTFFuncionario.setEnabled(true);
        }
    }//GEN-LAST:event_jTFEmpresaCaretUpdate

    private void jTFCargoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFCargoCaretUpdate
        jTFEmpresa.setEnabled(true);
        jTFFuncionario.setEnabled(true);
    }//GEN-LAST:event_jTFCargoCaretUpdate

    private void jTFFuncionarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFFuncionarioCaretUpdate
        jTFEmpresa.setEnabled(true);
        jTFCargo.setEnabled(true);
        jFTFSalarioAtual.setText("");
        jFTFSalarioNovo.setText("");
    }//GEN-LAST:event_jTFFuncionarioCaretUpdate

    private void jTFPercentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFPercentKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && !jTFFuncionario.getText().equalsIgnoreCase("<...TODOS...>") && !jTFFuncionario.getText().isEmpty() && !jTFPercent.getText().isEmpty()){
            if(!jTFPercent.getText().isEmpty()){
                Double novoSalario = (formatarCampoSalarioAtual(2)*(100 + Double.parseDouble(jTFPercent.getText())))/100;
                jFTFSalarioNovo.setText(df.format(novoSalario));
                jFTFSalarioNovo.requestFocus();
            }
        }else if(evt.getKeyCode() == evt.VK_ENTER && !jTFPercent.getText().isEmpty()){
            jBtnSalvar.requestFocus();
        }
    }//GEN-LAST:event_jTFPercentKeyPressed

    private void jTFValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFValorKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && !jTFFuncionario.getText().equalsIgnoreCase("<...TODOS...>") && !jTFFuncionario.getText().isEmpty() && !jTFValor.getText().isEmpty()){
            if(!jTFValor.getText().isEmpty()){
                Double novoSalario = (formatarCampoSalarioAtual(2)+ Double.parseDouble(jTFValor.getText()));
                jFTFSalarioNovo.setText(df.format(novoSalario));
                jFTFSalarioNovo.requestFocus();
            }
        }else if(evt.getKeyCode() == evt.VK_ENTER && !jTFValor.getText().isEmpty()){
            jBtnSalvar.requestFocus();
        }
    }//GEN-LAST:event_jTFValorKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnPesquisarCargo;
    private javax.swing.JButton jBtnPesquisarEmpresa;
    private javax.swing.JButton jBtnPesquisarFuncionario;
    private javax.swing.JButton jBtnSalvar;
    private javax.swing.JFormattedTextField jFTFSalarioAtual;
    private javax.swing.JFormattedTextField jFTFSalarioNovo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JTextField jTFCargo;
    private javax.swing.JTextField jTFEmpresa;
    private javax.swing.JTextField jTFFuncionario;
    private javax.swing.JTextField jTFPercent;
    private javax.swing.JTextField jTFValor;
    // End of variables declaration//GEN-END:variables
}
