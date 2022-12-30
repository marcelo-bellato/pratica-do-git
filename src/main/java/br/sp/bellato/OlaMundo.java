package br.sp.bellato;

public class OlaMundo {
    public static void main(String[] args) {

        String saudacaoIngles = "hello world!\n";
        String saudacaoPortugues = "Ola mundo!";

        boolean faloIngles = false;
        boolean faloPortugues = false;

        if (faloIngles){
            System.out.println(saudacaoIngles);
        }
        if (faloPortugues) {
            System.out.println(saudacaoPortugues);
        }
        if (!faloIngles && !faloPortugues){
            System.out.println("erro");
        }
    }


}



