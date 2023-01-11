package br.sp.bellato.curso_java.fundamentos.desafios;

public class DesafioOperadoresLogicos
{
  public static void main(String[] args)
  {
    // Trabalho na terça (V ou F)
   boolean trabalhoTerca = false;
    // Trabalho na quinta (V ou F)
    boolean trabalhoQuinta = false;

    boolean comprouTv50 = trabalhoTerca && trabalhoQuinta;
    System.out.println("Comprou Tv 50\" ? " + comprouTv50);
    boolean comprouTv32 = trabalhoTerca ^ trabalhoQuinta;
    System.out.println("Comprou Tv 32\" ? " + comprouTv32);
    boolean comprouSorvete = trabalhoTerca || trabalhoQuinta;
    System.out.println("Comprou sorvete ? " + comprouSorvete);
    boolean maisSaudavel = !comprouSorvete;
    System.out.println("Mais saudável ? " + maisSaudavel);
  }
}
