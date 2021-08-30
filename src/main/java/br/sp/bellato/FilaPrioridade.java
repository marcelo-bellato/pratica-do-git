package br.sp.bellato;

public class FilaPrioridade {
    public static void main(String[] args) {

        int idade = -1;

        if(idade>=60){
            System.out.println("prioritario");
        }else{
            System.out.println("normal");
        }


        if(idade>=60){
            System.out.println("prioritario");
        }else if(idade >=0 && idade<=60){
            System.out.println("normal");
        }else
            System.out.println("não nasceu");
    }
}
