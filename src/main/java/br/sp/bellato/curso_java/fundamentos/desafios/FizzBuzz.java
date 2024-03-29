package br.sp.bellato.curso_java.fundamentos.desafios;


public class FizzBuzz
{
  public static void main(String[] args)
  {
    for (int i = 1; i < 30; i++)
    {
      if (i % 3 == 0 && i % 5 == 0)
      {
        System.out.println("FizzBuzz");
      }
      else if (i % 5 == 0)
      {
        System.out.println("Buzz");
      }
      else if (i % 3 == 0)
      {
        System.out.println("Fizz");
      }
      else
      {
        System.out.println(i);
      }
    }
  }


  public static class DesafioAritimeticos
  {
    public static void main(String[] args)
    {
      double numA = Math.pow(6 * (double)(3 + 2), 2);
      double denA = 3 *(double) 2;

      double numB = (1 - 5) *(double) (2 - 7);
      double denB = 2;

      double superiorA = numA / denA;

      double superiorB = Math.pow(numB / denB, 2);

      double superior = Math.pow(superiorA - superiorB, 3);

      double inferior = Math.pow(10, 3);

      double resultado = superior / inferior;

      System.out.println(resultado);

    }
  }
}
