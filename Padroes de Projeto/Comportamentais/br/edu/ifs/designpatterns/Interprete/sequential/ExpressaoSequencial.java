package comportamental.br.edu.ifs.designpatterns.interpreter.sequential;

import comportamental.br.edu.ifs.designpatterns.interpreter.Expressao;
import comportamental.br.edu.ifs.designpatterns.interpreter.ExpressaoNaoTerminal;

public class ExpressaoSequencial extends ExpressaoNaoTerminal {

    public ExpressaoSequencial() {
        super();
    }

    public void addExpressao(Expressao expressao) {
        this.expressoes.add(expressao);
    }

    @Override
    public boolean interpretar() {
        for (Expressao exp : expressoes) {
            if (!exp.interpretar()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("execute:\n");
        for (Expressao exp : expressoes) {
            builder.append("\t").append(exp.toString()).append("\n");
        }
        return builder.toString();
    }
}