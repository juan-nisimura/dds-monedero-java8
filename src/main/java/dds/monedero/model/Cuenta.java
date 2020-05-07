package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {

	private double saldo = 0;
	private List<Movimiento> movimientos = new ArrayList<>();

	public Cuenta() {
		saldo = 0;
	}

	public Cuenta(double montoInicial) {
		saldo = montoInicial;
	}

	public void chequearMontoNegativo(double cuanto) {
		if (cuanto <= 0) {
			throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
		}
	}

	public void chequearMaximaCantidadDepositos() {
		if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
			throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
		}
	}
	
	public void chequearSaldoMenor(double cuanto) {
		if (getSaldo() - cuanto < 0) {
			throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
		}
	}
	
	public void chequearMaximoExtraccionDiario(double cuanto) {
		if (cuanto > limite()) {
			throw new MaximoExtraccionDiarioException(
					"No puede extraer mas de $ " + 1000 + " diarios, límite: " + limite());
		}
	}
	
	public double limite() {
		return 1000 - getMontoExtraidoA(LocalDate.now());
	}

	public void poner(double cuanto) {
		chequearMontoNegativo(cuanto);
		chequearMaximaCantidadDepositos();
		saldo += cuanto;
		agregarMovimiento(new Deposito(LocalDate.now(), cuanto));
	}

	public void sacar(double cuanto) {
		chequearMontoNegativo(cuanto);
		chequearSaldoMenor(cuanto);
		chequearMaximoExtraccionDiario(cuanto);
		saldo -= cuanto;
		agregarMovimiento(new Extraccion(LocalDate.now(), cuanto));
	}

	public void agregarMovimiento(Movimiento movimiento) {
		movimientos.add(movimiento);
	}

	public double getMontoExtraidoA(LocalDate fecha) {
		return getMovimientos().stream().filter(movimiento -> movimiento.fueExtraido(fecha))
				.mapToDouble(Movimiento::getMonto).sum();
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

}
