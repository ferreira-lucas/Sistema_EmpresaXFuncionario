/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import main.Cargo;
import main.Empresa;
import main.Funcionario;

/**
 *
 * @author lucas
 */
public class PesquisaTableModel extends AbstractTableModel{

    private final List dados;
    private final String[] colunas;
    private final String auxModel;
    private final DecimalFormat df = new DecimalFormat("R$#,###.00");
    
    public PesquisaTableModel(List data, String[] columns, String model){
        dados = data;
        colunas = columns;
        auxModel = model;
    }

    public String getAuxModel() {
        return auxModel;
    }
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(auxModel){
            case "Empresa","FunXEmpresa", "AltEmp":
                List<Empresa> dadosEmpresa = (List<Empresa>) dados;
                switch(coluna){
                case 0:
                    return dadosEmpresa.get(linha).getCodEmpresa();
                case 1: 
                    return dadosEmpresa.get(linha).getCnpj();
                case 2:
                    return dadosEmpresa.get(linha).getRazaoSocial();
                case 3:
                    return dadosEmpresa.get(linha).getNomeFantasia();
                case 4:
                    return dadosEmpresa.get(linha).getRamoAtuacao();
                }
            case "Cargo","FunXCargo", "AltCarg":
                List<Cargo> dadosCargo = (List<Cargo>) dados;
                switch(coluna){
                    case 0:
                        return dadosCargo.get(linha).getCodCargo();
                    case 1:
                        return dadosCargo.get(linha).getCargo();
                }
            case "Funcionario", "AltFun":
                List<Funcionario> dadosFuncionario = (List<Funcionario>) dados;
                switch(coluna){
                    case 0:
                        return dadosFuncionario.get(linha).getCodFuncionario();
                    case 1:
                        return dadosFuncionario.get(linha).getCpf();
                    case 2:
                        return dadosFuncionario.get(linha).getNome();
                    case 3:
                        String data = new SimpleDateFormat("dd/MM/yyyy").format(dadosFuncionario.get(linha).getDataNasc());
                        return data;
                    case 4:
                        return dadosFuncionario.get(linha).getSexo().name();
                    case 5:
                        return df.format(dadosFuncionario.get(linha).getSalario());
                    case 6:
                        data = new SimpleDateFormat("dd/MM/yyyy").format(dadosFuncionario.get(linha).getDataAdmissao());
                        return data;
                    case 7:
                        return dadosFuncionario.get(linha).getCargo();
                    case 8:
                        return dadosFuncionario.get(linha).getEmpresa();
                }
        }
        
        return null;
    }
}
