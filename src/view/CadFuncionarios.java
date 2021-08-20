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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import main.Funcionario;
import types.Sexo;
import model.PesquisaTableModel;

/**
 *
 * @author lucas
 */
public class CadFuncionarios extends javax.swing.JInternalFrame {

    private final String titulo = "Pesquisar Funcionários";
    private final String[] colunasFuncionario = {"Código", "CPF", "Nome", "Data Nascimento", "Sexo", "Salário", "Data de Admissão", "Cargo", "Empresa"};
    private final String[] colunasCargo = {"Código", "Cargo"};
    private final String[] colunasEmpresa = {"Código", "CNPJ", "Razão Social", "Nome Fantasia", "Ramo de Atuação"};
    private final String modelo = "Funcionario";
    private final TelaPrincipal auxTP;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final DecimalFormat df = new DecimalFormat("#,###.00");
    /**
     * Creates new form CadEmpresas
     */
    public CadFuncionarios(TelaPrincipal aux) {
        initComponents();
        String []sexo = {"Masculino","Feminino"};
        jComboSexo.setModel(new DefaultComboBoxModel(sexo));
        auxTP = aux;
        formatarCampoCPF(1);
        formatarCampoDataNasc(1);
        formatarCampoDataAdm(1);
        formatarCampoSalario(1);
    }

    public JFormattedTextField getjFTFCPF() {
        return jFTFCPF;
    }

    public JFormattedTextField getjFTFDataAdm() {
        return jFTFDataAdm;
    }

    public JFormattedTextField getjFTFDataNasc() {
        return jFTFDataNasc;
    }

    public JTextField getjFTFSalario() {
        return jFTFSalario;
    }

    public JTextField getjTFCargo() {
        return jTFCargo;
    }

    public JTextField getjTFCod() {
        return jTFCod;
    }

    public JTextField getjTFEmpresa() {
        return jTFEmpresa;
    }

    public JTextField getjTFNome() {
        return jTFNome;
    }

    public JComboBox<String> getjComboSexo() {
        return jComboSexo;
    }
    
    public void limparCampos(){
        jTFCod.setText("");
        jFTFCPF.setText("");
        jTFNome.setText("");
        jFTFDataNasc.setText("");
        jComboSexo.setSelectedItem(null);
        jFTFSalario.setText("");
        jFTFDataAdm.setText("");
        jTFCargo.setText("");
        jTFEmpresa.setText("");
    }
    
    public void cadastrarFuncionario(){
        Funcionario fun = new Funcionario();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        
        fun.setCpf(Long.parseLong(formatarCampoCPF(2)));
        fun.setNome(jTFNome.getText());
        fun.setDataNasc(formatarCampoDataNasc(2));
        if(jComboSexo.getSelectedItem().toString().equalsIgnoreCase("Masculino")){
            fun.setSexo(Sexo.M);
        }else if(jComboSexo.getSelectedItem().toString().equalsIgnoreCase("Feminino")){
            fun.setSexo(Sexo.F);
        }
        fun.setSalario(formatarCampoSalario(2));
        fun.setDataAdmissao(formatarCampoDataAdm(2));
        fun.setCargo(Integer.parseInt(jTFCargo.getText()));
        fun.setEmpresa(Integer.parseInt(jTFEmpresa.getText()));
        
        funDAO.insert(fun);
        
        limparCampos();
        jTFCod.requestFocus();
    }
    
    public void alterarFuncionario(){
        Funcionario fun = new Funcionario();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        
        fun.setCpf(Long.parseLong(formatarCampoCPF(2)));
        fun.setNome(jTFNome.getText());
        fun.setDataNasc(formatarCampoDataNasc(2));
        if(jComboSexo.getSelectedItem().toString().equalsIgnoreCase("Masculino")){
            fun.setSexo(Sexo.M);
        }else if(jComboSexo.getSelectedItem().toString().equalsIgnoreCase("Feminino")){
            fun.setSexo(Sexo.F);
        }
        fun.setSalario(formatarCampoSalario(2));
        fun.setDataAdmissao(formatarCampoDataAdm(2));
        fun.setCargo(Integer.parseInt(jTFCargo.getText()));
        fun.setEmpresa(Integer.parseInt(jTFEmpresa.getText()));
        fun.setCodFuncionario(Integer.parseInt(jTFCod.getText()));
        
        funDAO.update(fun);
        
        limparCampos();
        jTFCod.requestFocus();
    }
    
    public void excluirFuncionario(){
        Funcionario fun = new Funcionario();
        FuncionarioDAO funDAO = new FuncionarioDAO();
        
        fun.setCodFuncionario(Integer.parseInt(jTFCod.getText()));
        
        funDAO.delete(fun);
        
        limparCampos();
    }
    
    public void preencherCampos() throws ParseException{
        FuncionarioDAO funDAO = new FuncionarioDAO();
        List<Funcionario> dados = funDAO.selectByCodFun(Integer.parseInt(jTFCod.getText()));
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(null, "Novo registro!", "Aviso", JOptionPane.OK_OPTION);
            limparCampos();
            jFTFCPF.requestFocus();
        }else{
            jFTFCPF.setText(String.valueOf(dados.get(0).getCpf()));
            jTFNome.setText(String.valueOf(dados.get(0).getNome()));
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dados.get(0).getDataNasc());
            jFTFDataNasc.setText(String.valueOf(data));
            if(dados.get(0).getSexo().name().matches("M")){
                jComboSexo.setSelectedItem("Masculino");
            }else if(dados.get(0).getSexo().name().matches("F")){
                jComboSexo.setSelectedItem("Feminino");
            }
            jFTFSalario.setText(String.valueOf(df.format(dados.get(0).getSalario())));
            data = new SimpleDateFormat("dd/MM/yyyy").format(dados.get(0).getDataAdmissao());
            jFTFDataAdm.setText(String.valueOf(data));
            jTFCargo.setText(String.valueOf(dados.get(0).getCargo()));
            jTFEmpresa.setText(String.valueOf(dados.get(0).getEmpresa()));
            jFTFCPF.requestFocus();
        }
    }

    private String formatarCampoCPF(int opcao){// opção 1 = formata, opção 2 = desformata
        String retorno = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("###.###.###-##");
                mask.install(jFTFCPF);
            }else if(opcao == 2){
                retorno = jFTFCPF.getText().replace(".", "").replace("-", "").replace(" ", "");
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private java.sql.Date formatarCampoDataNasc(int opcao){// opção 1 = formata, opção 2 = desformata
        java.sql.Date data = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("##/##/####");
                mask.install(jFTFDataNasc);
            }else if(opcao == 2){
                data = new java.sql.Date(sdf.parse(jFTFDataNasc.getText()).getTime());
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    private Double formatarCampoSalario(int opcao){// opção 1 = formata, opção 2 = desformata
        Double salario = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("R$**********");
                mask.install(jFTFSalario);
            }else if(opcao == 2){
                salario = Double.parseDouble(jFTFSalario.getText().replace("R$", "").replace(".", "").replace(",", ".").replace(" ", ""));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salario;
    }
    
    private java.sql.Date formatarCampoDataAdm(int opcao){// opção 1 = formata, opção 2 = desformata
        java.sql.Date data = null;
        try {
            if(opcao == 1){
                MaskFormatter mask = new MaskFormatter("##/##/####");
                mask.install(jFTFDataAdm);   
            }else if(opcao == 2){
                data = new java.sql.Date(sdf.parse(jFTFDataAdm.getText()).getTime());
            }
        } catch (ParseException ex) {
            Logger.getLogger(CadEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jBtnPesquisar = new javax.swing.JButton();
        jBtnSalvar = new javax.swing.JButton();
        jBtnExcluir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTFCod = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTFNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jFTFCPF = new javax.swing.JFormattedTextField();
        jFTFDataNasc = new javax.swing.JFormattedTextField();
        jFTFDataAdm = new javax.swing.JFormattedTextField();
        jTFCargo = new javax.swing.JTextField();
        jBtnPesquisarCargo = new javax.swing.JButton();
        jTFEmpresa = new javax.swing.JTextField();
        jBtnPesquisarEmpresa = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboSexo = new javax.swing.JComboBox<>();
        jFTFSalario = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Cadastro de Funcionários");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Salário");

        jLabel3.setText("Nome completo");

        jLabel4.setText("Data de Admissão");

        jLabel5.setText("Cargo");

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

        jLabel7.setText("CPF");

        jTFNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFNomeKeyPressed(evt);
            }
        });

        jLabel8.setText("Sexo");

        jLabel9.setText("Data de Nascimento");

        jFTFCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFCPFFocusGained(evt);
            }
        });
        jFTFCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTFCPFKeyPressed(evt);
            }
        });

        jFTFDataNasc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFDataNascFocusGained(evt);
            }
        });
        jFTFDataNasc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTFDataNascKeyPressed(evt);
            }
        });

        jFTFDataAdm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFDataAdmFocusGained(evt);
            }
        });
        jFTFDataAdm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTFDataAdmKeyPressed(evt);
            }
        });

        jTFCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFCargoKeyPressed(evt);
            }
        });

        jBtnPesquisarCargo.setText("...");
        jBtnPesquisarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarCargoActionPerformed(evt);
            }
        });

        jTFEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFEmpresaKeyPressed(evt);
            }
        });

        jBtnPesquisarEmpresa.setText("...");
        jBtnPesquisarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarEmpresaActionPerformed(evt);
            }
        });

        jLabel10.setText("Empresa");

        jComboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboSexoKeyPressed(evt);
            }
        });

        jFTFSalario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFSalarioFocusGained(evt);
            }
        });
        jFTFSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFTFSalarioKeyPressed(evt);
            }
        });

        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnSalvar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnExcluir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFCod, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFNome, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jFTFCPF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jFTFDataNasc, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jFTFDataAdm, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFCargo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisarCargo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTFEmpresa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBtnPesquisarEmpresa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboSexo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jFTFSalario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jBtnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel6)))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jFTFCPF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                            .addComponent(jTFCod, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jBtnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jFTFDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTFNome, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jFTFDataAdm)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jTFCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnPesquisarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jTFEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnPesquisarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jFTFSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFTFCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jFTFDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFTFSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jFTFDataAdm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTFCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFTFCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFCPFFocusGained
        if(jFTFCPF.getText().equalsIgnoreCase("")){
            formatarCampoCPF(1);
        }
    }//GEN-LAST:event_jFTFCPFFocusGained

    private void jFTFDataNascFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataNascFocusGained
        if(jFTFDataNasc.getText().equalsIgnoreCase("")){
            formatarCampoDataNasc(1);
        }
    }//GEN-LAST:event_jFTFDataNascFocusGained

    private void jFTFDataAdmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataAdmFocusGained
        if(jFTFDataAdm.getText().equalsIgnoreCase("")){
            formatarCampoDataAdm(1);
        }
    }//GEN-LAST:event_jFTFDataAdmFocusGained

    private void jBtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalvarActionPerformed
        if (!jTFCod.getText().equalsIgnoreCase("")) {
            alterarFuncionario();
        }else{
            cadastrarFuncionario();
        }
    }//GEN-LAST:event_jBtnSalvarActionPerformed

    private void jBtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluirActionPerformed
        if(jTFCod.getText() != null){
            excluirFuncionario();
        }
    }//GEN-LAST:event_jBtnExcluirActionPerformed

    private void jTFCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFCodKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFCod.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Novo registro!", "Aviso", JOptionPane.OK_OPTION);
            limparCampos();
            jFTFCPF.requestFocus();
        }else if(evt.getKeyCode() == evt.VK_ENTER && jTFCod.getText() != null){
            try {
                preencherCampos();
            } catch (ParseException ex) {
                Logger.getLogger(CadFuncionarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTFCodKeyPressed

    private void jFTFCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTFCPFKeyPressed
        String aux = formatarCampoCPF(2);
        if(evt.getKeyCode() == evt.VK_ENTER && aux.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER && aux.length() < 11){
            JOptionPane.showMessageDialog(null, "CPF inválido!", "Aviso", JOptionPane.OK_OPTION);
            jFTFCPF.setText("");
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jTFNome.requestFocus();
        }
    }//GEN-LAST:event_jFTFCPFKeyPressed

    private void jTFNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFNomeKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFNome.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jFTFDataNasc.requestFocus();
        }
    }//GEN-LAST:event_jTFNomeKeyPressed

    private void jFTFDataNascKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTFDataNascKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && formatarCampoDataNasc(2).toString().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jComboSexo.requestFocus();
        }
    }//GEN-LAST:event_jFTFDataNascKeyPressed

    private void jComboSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboSexoKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jComboSexo.getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jFTFSalario.requestFocus();
        }
    }//GEN-LAST:event_jComboSexoKeyPressed

    private void jFTFDataAdmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTFDataAdmKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && formatarCampoDataAdm(2).toString().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jTFCargo.requestFocus();
        }
    }//GEN-LAST:event_jFTFDataAdmKeyPressed

    private void jTFCargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFCargoKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFCargo.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jTFEmpresa.requestFocus();
        }
    }//GEN-LAST:event_jTFCargoKeyPressed

    private void jTFEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFEmpresaKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jTFEmpresa.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jBtnSalvar.requestFocus();
        }
    }//GEN-LAST:event_jTFEmpresaKeyPressed

    private void jBtnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnSalvarKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            if (!jTFCod.getText().equalsIgnoreCase("")) {
                alterarFuncionario();
            }else{
                cadastrarFuncionario();
            }
        }
    }//GEN-LAST:event_jBtnSalvarKeyPressed

    private void jBtnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarActionPerformed
        FuncionarioDAO funDAO = new FuncionarioDAO();
        PesquisaTableModel etm = new PesquisaTableModel(funDAO.selectAll(), colunasFuncionario, modelo);
        Pesquisar p = new Pesquisar(titulo, etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarActionPerformed

    private void jBtnPesquisarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarCargoActionPerformed
        CargoDAO carDAO = new CargoDAO();
        PesquisaTableModel etm = new PesquisaTableModel(carDAO.selectAll(), colunasCargo, "FunXCargo");
        Pesquisar p = new Pesquisar(titulo, etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarCargoActionPerformed

    private void jBtnPesquisarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarEmpresaActionPerformed
        EmpresaDAO empDAO = new EmpresaDAO();
        PesquisaTableModel etm = new PesquisaTableModel(empDAO.selectAll(), colunasEmpresa, "FunXEmpresa");
        Pesquisar p = new Pesquisar(titulo, etm, this);
        auxTP.desktopPrincipal.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisarEmpresaActionPerformed

    private void jFTFSalarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFTFSalarioKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER && jFTFSalario.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Campo obrigatório!", "Aviso", JOptionPane.OK_OPTION);
        }else if(evt.getKeyCode() == evt.VK_ENTER){
            jFTFDataAdm.requestFocus();
        }
    }//GEN-LAST:event_jFTFSalarioKeyPressed

    private void jFTFSalarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFSalarioFocusGained
        if(jFTFSalario.getText().equals("")){
            formatarCampoSalario(1);
        }
    }//GEN-LAST:event_jFTFSalarioFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnExcluir;
    private javax.swing.JButton jBtnPesquisar;
    private javax.swing.JButton jBtnPesquisarCargo;
    private javax.swing.JButton jBtnPesquisarEmpresa;
    private javax.swing.JButton jBtnSalvar;
    private javax.swing.JComboBox<String> jComboSexo;
    private javax.swing.JFormattedTextField jFTFCPF;
    private javax.swing.JFormattedTextField jFTFDataAdm;
    private javax.swing.JFormattedTextField jFTFDataNasc;
    private javax.swing.JFormattedTextField jFTFSalario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JTextField jTFCargo;
    private javax.swing.JTextField jTFCod;
    private javax.swing.JTextField jTFEmpresa;
    private javax.swing.JTextField jTFNome;
    // End of variables declaration//GEN-END:variables
}
