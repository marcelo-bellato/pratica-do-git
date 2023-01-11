package br.sp.bellato.curso_java.fundamentos.desafios;

import java.util.Scanner;

public class DesafioCalculadora
{
  public static void main(String[] args)
  {
    Scanner entrada = new Scanner(System.in);
    // ler num1
    System.out.print("Informe o primeiro número: ");
    double num1 = entrada.nextDouble();
    // ler num2
    System.out.print("Informe o segundo número: ");
    double num2 = entrada.nextDouble();
    // conversao
    System.out.print("Informe a operação: ");
    String operacao = entrada.next();

    //Lógica
    double resultado = "+".equals(operacao) ? num1 + num2 :0;
    resultado = "-".equals(operacao) ? num1 - num2 : resultado;
    resultado = "*".equals(operacao) ? num1 * num2 : resultado;
    resultado = "/".equals(operacao) ? num1 / num2 : resultado;
    resultado = "%".equals(operacao) ? num1 % num2 : resultado;

    System.out.printf("%.2f %s %.2f = %.2f", num1, operacao, num2, resultado);
    entrada.close();

  }


}
