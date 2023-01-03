package br.sp.bellato.curso_java.fundamentos;

public class TipoString
{
  public static void main(String[] args)
  {
    System.out.println("Olá Osasco".charAt(6));

    String s = "Boa tarde";
    System.out.println(s.concat("!!!"));
    System.out.println(s + "!!!");

    String nome = "Pedro";
    String sobrenome = "Santos";
    int idade = 33;
    double salario = 12345.987;
    System.out.printf("Nome: %s %s tem %d anos e ganha R$ %.2f.",
        nome, sobrenome, idade, salario);

  }
}
