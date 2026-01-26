package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;

public class LeasePlugin implements IPlugin {
    public boolean init() {
        System.out.println(">>> PLUGIN DE LOCAÇÃO: Fui carregado com sucesso!");
        return true;
    }
}