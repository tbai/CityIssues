package rs.gov.dadosabertos;

import java.util.List;

public class TipoEstatED {
  String nome;
  String periodo;
  List<IndicadorED> listaIndicadorED;
  
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getPeriodo() {
    return periodo;
  }
  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }
  public List<IndicadorED> getListaIndicadorED() {
    return listaIndicadorED;
  }
  public void setListaIndicadorED(List<IndicadorED> listaIndicadorED) {
    this.listaIndicadorED = listaIndicadorED;
  }
  
  
  


}
