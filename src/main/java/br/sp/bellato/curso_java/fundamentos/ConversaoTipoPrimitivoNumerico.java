package br.sp.bellato.curso_java.fundamentos;

public class ConversaoTipoPrimitivoNumerico
{
  public static void main(String[] args)
  {
    //implícito
    double a = 1;
    System.out.println(a);

    float b = (float) 1.12345678888;
    System.out.println(b);

    //explícita
    int c = 340;
    byte d = (byte) c;
    System.out.println(d);

    //explícita
    double e = 1.999999;
    int f = (int) e;
    System.out.println(f);
  }
}
