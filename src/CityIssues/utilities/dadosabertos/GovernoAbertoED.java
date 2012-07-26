package rs.gov.dadosabertos;

import java.util.List;

public class GovernoAbertoED {  
  List<EstatisticaSspED> listaEstatisticaSspED;
  List<OcorrEstuproED>   listaEstuproED;

  public List<EstatisticaSspED> getListaEstatisticaSspED() {
    return listaEstatisticaSspED;
  }

  public void setListaEstatisticaSspED(List<EstatisticaSspED> listaEstatisticaSspED) {
    this.listaEstatisticaSspED = listaEstatisticaSspED;
  }

  public List<OcorrEstuproED> getListaEstuproED() {
    return listaEstuproED;
  }

  public void setListaEstuproED(List<OcorrEstuproED> listaEstuproED) {
    this.listaEstuproED = listaEstuproED;
  }

}
