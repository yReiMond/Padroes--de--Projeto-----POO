package comportamental.br.edu.ifs.designpatterns.state;

public class MarioMorto implements EstadoMario{
    @Override
    public void pegarCogumelo(Mario mario) {
        // Ignora a ação, Mario está morto
    }

    @Override
    public void pegarFlor(Mario mario) {
        // Ignora a ação, Mario está morto
    }

    @Override
    public void pegarPena(Mario mario) {
        // Ignora a ação, Mario está morto
    }

    @Override
    public void sofrerDano(Mario mario) {
        // Ignora a ação, Mario já está morto
    }

    @Override
    public String obterAtaque() {
        return "Sem ataque (Mario morto)";
    }

    @Override
    public String obterEstado() {
        return "Mario morto";
    }
}