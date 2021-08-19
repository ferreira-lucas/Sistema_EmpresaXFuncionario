/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package types;

import java.text.DecimalFormat;

/**
 *
 * @author lucas
 */
public class FormatosNumero {
    public DecimalFormat formatacaoRealBR(){
        return new DecimalFormat("#,###.00");
    }
}
