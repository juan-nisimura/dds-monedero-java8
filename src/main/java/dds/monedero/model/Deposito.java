package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento {
	
	public Deposito(LocalDate fecha, double monto) {
		this.fecha = fecha;
		this.monto = monto;
	}

	public boolean fueDepositado(LocalDate fecha) {
		return esDeLaFecha(fecha);
	}

	public boolean fueExtraido(LocalDate fecha) {
		return false;
	}
	
	public boolean isDeposito() {
		return true;
	}

}
