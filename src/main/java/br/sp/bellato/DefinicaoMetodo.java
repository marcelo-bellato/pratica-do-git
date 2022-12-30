package br.sp.bellato;

public class DefinicaoMetodo {

    static void decidirPorIdade(Integer idade){
        if(idade>=60){
            System.out.println("prioritario");
        }else if(idade >=0 && idade<=60){
            System.out.println("normal");
        }else
            System.out.println("não nasceu");
    }

    static void decidirPorIdadeeGravidez(Integer idade, boolean gravida){
        if(idade>=60 || gravida){
            System.out.println("prioritario");
        }else{
            System.out.println("normal");
        }
    }

    static void ola(String saudacao){
        System.out.println(saudacao);
    }

    static void saudar(String tratamento, String nome){
        System.out.println("ola, " + tratamento + "." + nome );
    }

    static void decidirPorGravidez(boolean gravida){
        if(gravida){
            System.out.println("prioritario");
        }
        else{
            System.out.println("normal");
        }
    }

    public static void main(String[] args) {
        Integer idade = 59;
        decidirPorIdade(idade);

        String saudacao = "Bom dia";
        ola(saudacao);

        saudar("Sr", "Marcelo");

        decidirPorGravidez(true);

        decidirPorIdadeeGravidez(59, false);
    }

}
