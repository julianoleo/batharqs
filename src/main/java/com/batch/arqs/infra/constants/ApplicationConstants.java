package com.batch.arqs.infra.constants;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConstants {

    protected static List<String> arquivos = new ArrayList<>();

    public static void setArquivos(String arquivo) {
        arquivos.add(arquivo);
    }

    public static List<String> getArquivos() {
        return arquivos;
    }

    public static void limpaDadosArquivos() {
        arquivos.clear();
    }

}
