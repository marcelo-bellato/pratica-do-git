package br.sp.bellato.curso_java.fundamentos.operadores;

@SuppressWarnings("ALL")
public class Ternarios
{
  public static void main(String[] args)
  {
    double media = 6.9;
    String resultadoFinal = media >= 7.0 ? "aprovado." : "reprovado.";
    System.out.println("O aluno está " + resultadoFinal);

  }
}
