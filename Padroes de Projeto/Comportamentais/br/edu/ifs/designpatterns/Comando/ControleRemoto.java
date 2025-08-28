package comportamental.br.edu.ifs.designpatterns.command;

public class ControleRemoto {
    private Comando comando = null;

    public void pressionarBotao() {
        if (comando != null) {
            comando.executar();
        }
    }

    public void definirComando(Comando comando) {
        this.comando = comando;
    }
}