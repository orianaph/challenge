package com.oriana.challenge.dto;

public class PuntoVentaEvent {

        private Long ventaId;
        private Long PuntoVentaId;
        private Double monto;

        public PuntoVentaEvent(Long saleId, Long sellingPointId, Double amount) {
            this.ventaId = saleId;
            this.PuntoVentaId = sellingPointId;
            this.monto = amount;
        }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Long getPuntoVentaId() {
        return PuntoVentaId;
    }

    public void setPuntoVentaId(Long puntoVentaId) {
        PuntoVentaId = puntoVentaId;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }
}
