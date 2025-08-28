package comportamental.br.edu.ifs.designpatterns.mediator.impl;

import comportamental.br.edu.ifs.designpatterns.mediator.Colaborador;
import comportamental.br.edu.ifs.designpatterns.mediator.Mediador;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControladorTrafego implements Mediador {
    private List<Aeronave> pousos;
    private List<Aeronave> decolagens;
    private Metereologia metereologia;
    private Oficina oficina;

    public ControladorTrafego() {
        this.pousos = new ArrayList<>();
        this.decolagens = new ArrayList<>();
    }

    @Override
    public void registrarColaborador(Colaborador colaborador) {
        if (colaborador instanceof Metereologia && this.metereologia == null) {
            this.metereologia = (Metereologia) colaborador;
        } else if (colaborador instanceof Oficina && this.oficina == null) {
            this.oficina = (Oficina) colaborador;
        }
    }

    @Override
    public String notificar(Colaborador colaborador, String frase) {
        if (this.metereologia == null || this.oficina == null) {
            throw new IllegalStateException("Erro: ControladorTrafego não possui todas as referências (Metereologia/Oficina) configuradas.");
        }

        if (colaborador instanceof Aeronave) {
            Aeronave aeronave = (Aeronave) colaborador;
            boolean condicoesFavoraveis = metereologia.isCondicoesFavoraveis();
            boolean manutencaoNecessaria = oficina.necessitaManutencao(aeronave);

            if ("solicitarDecolagem".equals(frase)) {
                if (!condicoesFavoraveis) {
                    if (!decolagens.contains(aeronave)) {
                        decolagens.add(aeronave);
                    }
                    return aeronave.getId() + ": Decolagem negada - condições metereológicas.";
                } else if (manutencaoNecessaria) {
                    return aeronave.getId() + ": Decolagem negada - manutenção.";
                } else {
                    decolagens.remove(aeronave);
                    return aeronave.getId() + ": Decolagem autorizada.";
                }
            } else if ("solicitarPouso".equals(frase)) {
                if (!condicoesFavoraveis) {
                    if (!pousos.contains(aeronave)) {
                        pousos.add(aeronave);
                    }
                    return aeronave.getId() + ": Pouso negado - condições metereológicas.";
                } else {
                    pousos.remove(aeronave);
                    return aeronave.getId() + ": Pouso autorizado.";
                }
            }
        } else if (colaborador instanceof Metereologia) {
            List<String> resultadosPouso = new ArrayList<>();
            List<String> resultadosDecolagem = new ArrayList<>();

            boolean condicoesFavoraveis = metereologia.isCondicoesFavoraveis();

            Iterator<Aeronave> itPouso = pousos.iterator();
            while (itPouso.hasNext()) {
                Aeronave a = itPouso.next();
                if (condicoesFavoraveis) {
                    resultadosPouso.add(a.getId() + ": Pouso autorizado.");
                    itPouso.remove();
                } else {
                    resultadosPouso.add(a.getId() + ": Pouso negado - condições metereológicas.");
                }
            }

            Iterator<Aeronave> itDecolagem = decolagens.iterator();
            while (itDecolagem.hasNext()) {
                Aeronave a = itDecolagem.next();
                if (condicoesFavoraveis && !oficina.necessitaManutencao(a)) {
                    resultadosDecolagem.add(a.getId() + ": Decolagem autorizada.");
                    itDecolagem.remove();
                } else if (oficina.necessitaManutencao(a)) {
                    resultadosDecolagem.add(a.getId() + ": Decolagem negada - manutenção.");
                } else {
                    resultadosDecolagem.add(a.getId() + ": Decolagem negada - condições metereológicas.");
                }
            }

            return "[" + String.join(", ", resultadosPouso) + "]\n" + "[" + String.join(", ", resultadosDecolagem) + "]";
        }
        return "";
    }
}