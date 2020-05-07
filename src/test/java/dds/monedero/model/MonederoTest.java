package dds.monedero.model;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;


public class MonederoTest {
  private Cuenta cuenta;
  private Movimiento extraccion;
  private Movimiento deposito;
  

  @Before
  public void init() {
    cuenta = new Cuenta();
    extraccion = new Movimiento(LocalDate.now(),1000,false);
    deposito = new Movimiento(LocalDate.now(),1000,true);
  }

  @Test
  public void laExtracciónFueExtraídaHoy() {
    Assert.assertTrue(extraccion.fueExtraido(LocalDate.now()));
  }
  
  @Test
  public void laExtracciónNoFueDepositada() {
    Assert.assertFalse(extraccion.fueDepositado(LocalDate.now()));
  }
  
  @Test
  public void elDepositoFueDepositadoHoy() {
    Assert.assertTrue(deposito.fueDepositado(LocalDate.now()));
  }
  
  @Test
  public void elDepositoNoFueExtraido() {
    Assert.assertFalse(deposito.fueExtraido(LocalDate.now()));
  }
  
  @Test
  public void Poner() {
    cuenta.poner(1500);
    Assert.assertEquals(1500, cuenta.getSaldo(),0.01);
  }

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(-1500);
  }

  @Test
  public void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    Assert.assertEquals(3856, cuenta.getSaldo(),0.01);
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }
  
  @Test
  public void agregarMovimiento() {
	  cuenta.agregarMovimiento(deposito.getFecha(),deposito.getMonto(),true);
	  Assert.assertTrue(cuenta.getMovimientos().contains(deposito));
  }

}