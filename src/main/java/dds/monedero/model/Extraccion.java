package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento {

	public Extraccion(LocalDate fecha, double monto) {
		this.fecha = fecha;
		this.monto = monto;
	}

	public boolean fueDepositado(LocalDate fecha) {
		return false;
	}

	public boolean fueExtraido(LocalDate fecha) {
		return esDeLaFecha(fecha);
	}

	public boolean isDeposito() {
		return false;
	}
}
