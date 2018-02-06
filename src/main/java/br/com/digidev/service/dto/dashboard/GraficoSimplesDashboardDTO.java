package br.com.digidev.service.dto.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;

public class GraficoSimplesDashboardDTO implements Serializable{

    private BigDecimal value;
    private String name;

    public GraficoSimplesDashboardDTO(BigDecimal value, String name){
        this.value = value;
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
