package br.com.digidev.service.dto.dashboard;

import br.com.digidev.domain.enumeration.Tipo;

import java.io.Serializable;
import java.math.BigDecimal;

public class GraficoSimplesDashboardTipoEnumDTO implements Serializable{

    private BigDecimal value;
    private Tipo name;

    public GraficoSimplesDashboardTipoEnumDTO(BigDecimal value, Tipo name){
        this.value = value;
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Tipo getName() {
        return name;
    }

    public void setName(Tipo name) {
        this.name = name;
    }
}
