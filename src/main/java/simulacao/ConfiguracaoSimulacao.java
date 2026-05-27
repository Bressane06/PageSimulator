package simulacao;

public class ConfiguracaoSimulacao {
    
    public String diretorio_das_paginas;
    public String algoritmo_de_substituicao_de_paginas;
    public String numero_de_frames_de_memoria;
    public String quantidade_de_paginas_unicas;
    public String quantidades_de_paginas_requeridas;

    public ConfiguracaoSimulacao (String diretorio_das_paginas, String algoritmo_de_substituicao_de_paginas, String numero_de_frames_de_memoria, String quantidade_de_paginas_unicas, String quantidades_de_paginas_requeridas) {
        this.diretorio_das_paginas = diretorio_das_paginas;
        this.algoritmo_de_substituicao_de_paginas = algoritmo_de_substituicao_de_paginas;
        this.numero_de_frames_de_memoria = numero_de_frames_de_memoria;
        this.quantidade_de_paginas_unicas = quantidade_de_paginas_unicas;
        this.quantidades_de_paginas_requeridas = quantidades_de_paginas_requeridas;
    }

    public String getDiretorio_das_paginas() {
        return diretorio_das_paginas;
    }

    public String getAlgoritmo_de_substituicao_de_paginas() {
        return algoritmo_de_substituicao_de_paginas;
    }

    public String getNumero_de_frames_de_memoria() {
        return numero_de_frames_de_memoria;
    }

    public String getQuantidade_de_paginas_unicas() {
        return quantidade_de_paginas_unicas;
    }

    public String getQuantidades_de_paginas_requeridas() {
        return quantidades_de_paginas_requeridas;
    }

    public void setDiretorio_das_paginas(String diretorio_das_paginas) {
        this.diretorio_das_paginas = diretorio_das_paginas;
    }

    public void setAlgoritmo_de_substituicao_de_paginas(String algoritmo_de_substituicao_de_paginas) {
        this.algoritmo_de_substituicao_de_paginas = algoritmo_de_substituicao_de_paginas;
    }
    
    public void setNumero_de_frames_de_memoria(String numero_de_frames_de_memoria) {
        this.numero_de_frames_de_memoria = numero_de_frames_de_memoria;
    }

    public void setQuantidade_de_paginas_unicas(String quantidade_de_paginas_unicas) {
        this.quantidade_de_paginas_unicas = quantidade_de_paginas_unicas;
    }

    public void setQuantidades_de_paginas_requeridas(String quantidades_de_paginas_requeridas) {
        this.quantidades_de_paginas_requeridas = quantidades_de_paginas_requeridas;
    }
    

}
