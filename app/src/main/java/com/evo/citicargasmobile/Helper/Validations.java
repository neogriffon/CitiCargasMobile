package com.evo.citicargasmobile.Helper;

/**
 * Created by Daniel on 01/04/2015.
 */
public class Validations {

    public static  boolean ValidarCpfCnpj(String _cpfCnpj){

        if(_cpfCnpj.length() == 11)
        {
            return isValidCPF(_cpfCnpj);
        }
        else if(_cpfCnpj.length() == 14)
        {
            return isValidCNPJ(_cpfCnpj);
        }

        return false;
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static boolean isValidCPF(String cpf) {
        final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    private static boolean isValidCNPJ(String cnpj) {
        final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
    }
}