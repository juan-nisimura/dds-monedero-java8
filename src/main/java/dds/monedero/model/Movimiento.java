package dds.monedero.model;

import java.time.LocalDate;

abstract public class Movimiento {
	protected LocalDate fecha;
	// En ningun lenguaje de programacion usen jamas doubles para modelar dinero
	// en el mundo real
	// siempre usen numeros de precision arbitraria, como BigDecimal en Java y
	// similares
	protected double monto;

	public double getMonto() {
		return monto;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	abstract public boolean fueDepositado(LocalDate fecha);

	abstract public boolean fueExtraido(LocalDate fecha);

	public boolean esDeLaFecha(LocalDate fecha) {
		return this.fecha.equals(fecha);
	}
	
	abstract public double calcularValor(Cuenta cuenta);
	
	abstract public boolean isDeposito();
}
