package rs.gov.dadosabertos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class GeraXML {   
  static BufferedWriter out;

  public static void main( String args[] ) throws Exception
  {
    if ( args != null && args.length > 0 ) {
      geraArquivoXML(args[0]);
    }
    else {
      geraArquivoXML2("C:/documentacao_tecnica/rs_dados_abertos/estupros_mulher_2006_2011.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2004.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2005.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2006.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2007.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2008.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2009.csv");
      //geraArquivoXML("C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2010.csv");
    }
  }

  
  private static void geraArquivoXML( String nomeArquivo ) throws IOException {           
    File inFile;
    if ( nomeArquivo != null && nomeArquivo.length() > 0 ) {
      inFile = new File(nomeArquivo);
    }
    else {
      nomeArquivo = "C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2003.csv";
      inFile = new File(nomeArquivo);
    }
    String outFile = nomeArquivo.substring(0,nomeArquivo.length()-3) + "xml";
    BufferedReader Lote;
    Lote = new BufferedReader( new FileReader( inFile ));
    String Linha = Lote.readLine();
    String[] aux;
    int x=0;
    String periodo;
    String municipio;
    String indNome;
    String indQtde;    
    periodo=municipio=indNome=indQtde="";
    String XML_header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<tns:governo_aberto_rs xmlns:tns=\"http://www.example.org/governo_aberto_rs\" " +
                        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                        "xsi:schemaLocation=\"http://www.example.org/governo_aberto_rs governo_aberto_rs.xsd \">\n" +
                        "  <tns:estatistica_ssp>\n";
    String XML_tail   = "  </tns:estatistica_ssp>\n" + "</tns:governo_aberto_rs>\n";
    
    out = new BufferedWriter( new FileWriter(outFile , false) );
    out.write( XML_header );        
    
    String auxMunic   = "";    
    String auxPeriodo = "";
    boolean criaLabel = false;
    while (Linha != null){
      if (x==0) {
        Linha = Lote.readLine();
      }      
      Linha = Linha.replaceAll(",", ";");
      Linha = Linha.replaceAll(";\"", "");
      Linha = Linha.replaceAll(",", "");     //substitui "," por ""
      Linha = Linha.replaceAll("\"\"", ","); //substitui '""' por ","      
      Linha = Linha.substring(1);            //1° carac. é ",". Não interessa... 
      Linha = Linha.replaceAll("\"", ",");   //substitui '"' por ","      
      aux = Linha.split(",");
      for (int i = 0; i < aux.length; i++) {                
        municipio = aux[0];
        periodo   = aux[1];
        indNome   = aux[2];
        indQtde   = aux[3];
      }
      criaLabel = !auxMunic.equalsIgnoreCase(municipio) || !auxPeriodo.equalsIgnoreCase(periodo);
      if ( criaLabel ) {
       //Fecha label
       if ( !"".equals(auxMunic) ) {
         out.write( "  </tns:label>\n" );  
       }        
       out.write( "  <tns:label>\n" );
       out.write( "    <tns:nomeTipo>"    + municipio + "</tns:nomeTipo>\n" );
       out.write( "    <tns:periodo>" + periodo   + "</tns:periodo>\n" );
      }      
      auxMunic = municipio;
      auxPeriodo = periodo;                
      out.write( "    <tns:indicador>\n" );
      out.write( "      <tns:nomeInd>" + indNome.replaceAll(";", ",") + "</tns:nomeInd>\n" );
      out.write( "      <tns:qtde>" + indQtde + "</tns:qtde>\n" );    
      out.write( "    </tns:indicador>\n" );                   
      //if ( x == 100 ) {
      //  break;
      //}
      Linha = Lote.readLine();  // Proxima Linha
      x++;
    }          
    
    out.write( "  </tns:label>\n" );
    out.write( XML_tail );
    out.close();
  }  
  
  
  private static void geraArquivoXML2( String nomeArquivo ) throws IOException {           
    File inFile;
    if ( nomeArquivo != null && nomeArquivo.length() > 0 ) {
      inFile = new File(nomeArquivo);
    }
    else {
      nomeArquivo = "C:/documentacao_tecnica/rs_dados_abertos/estupros_mulher_2006_2011.csv";
      inFile = new File(nomeArquivo);
    }
    String outFile = nomeArquivo.substring(0,nomeArquivo.length()-3) + "xml";
    BufferedReader Lote;
    Lote = new BufferedReader( new FileReader( inFile ));
    String Linha = Lote.readLine();
    String[] aux;
    int x=0;
    String ano;
    String mes;
    String dia_mes;
    String dia_semana;
    String turno;
    String hora;
    String tentado_consumado;
    String tipoLocal;
    String municipio;    
    String idadeVitima;
    String idadeAutor;    
    ano=mes=dia_mes=dia_semana=turno=hora=municipio=idadeVitima=idadeAutor="";
    tentado_consumado=tipoLocal="";
    String XML_header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<tns:governo_aberto_rs xmlns:tns=\"http://www.example.org/governo_aberto_rs\" " +
                        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                        "xsi:schemaLocation=\"http://www.example.org/governo_aberto_rs governo_aberto_rs.xsd \">\n" +
                        "  <tns:estatistica_ssp>\n";
    String XML_tail   = "  </tns:estatistica_ssp>\n" + "</tns:governo_aberto_rs>\n";
    
    out = new BufferedWriter( new FileWriter(outFile , false) );
    out.write( XML_header );        
    
    String auxMunic = "";    
    String auxMes   = "";
    String auxAno   = "";
    boolean criaLabel = false;
    while (Linha != null){
      if (x==0) {
        Linha = Lote.readLine();
      }      
      //Linha = Linha.replaceAll(",", ";");
      //Linha = Linha.replaceAll(";\"", "");
      //Linha = Linha.replaceAll(",", "");     //substitui "," por ""
      //Linha = Linha.replaceAll("\"\"", ","); //substitui '""' por ","      
      //Linha = Linha.substring(1);            //1° carac. é ",". Não interessa... 
      //Linha = Linha.replaceAll("\"", ",");   //substitui '"' por ","      
      aux = Linha.split(";");
      //System.out.println(Linha);
      //System.out.println(aux.length);
      //Municipio;Ano;Mes;Dia do Mes;Dia da Semana;Turno;Hora;Tentado;Tipo Local;Idade vitima;Idade Autor
      for (int i = 0; i < aux.length; i++) {                
        municipio  = aux[0].trim();
        ano        = aux[1].trim();
        mes        = aux[2].trim();
        dia_mes    = aux[3].trim();
        dia_semana = aux[4].trim();
        turno      = aux[5].trim();
        hora       = aux[6].trim();
        tentado_consumado = aux[7].trim();
        tipoLocal  = aux[8].trim();
        idadeVitima= aux[9].trim();
        idadeAutor = aux[10].trim();
      }
      criaLabel = !auxMunic.equalsIgnoreCase(municipio) || !auxAno.equalsIgnoreCase(ano) || !auxMes.equalsIgnoreCase(mes);
      if ( criaLabel ) {
       //Fecha label
       if ( !"".equals(auxMunic) ) {
         out.write( "  </tns:estupro>\n" );  
       }        
       out.write( "  <tns:estupro>\n" );
       out.write( "    <tns:municOcorr>"       + municipio   + "</tns:municOcorr>\n" );
       out.write( "    <tns:anoOcorr>"         + ano         + "</tns:anoOcorr>\n" );
       out.write( "    <tns:mesOcorr>"         + mes         + "</tns:mesOcorr>\n" );
      }
      auxMunic = municipio;
      auxAno   = ano;
      auxMes   = mes;
       
      out.write( "    <tns:dadosOcorr>\n" );
      out.write( "     <tns:diaMesOcorr>"      + dia_mes     + "</tns:diaMesOcorr>\n" );
      out.write( "     <tns:diaSemanaOcorr>"   + dia_semana  + "</tns:diaSemanaOcorr>\n" );
      out.write( "     <tns:turnoOcorr>"       + turno       + "</tns:turnoOcorr>\n" );
      out.write( "     <tns:horaOcorr>"        + hora        + "</tns:horaOcorr>\n" );
      out.write( "     <tns:tentadoConsumado>" + tentado_consumado  + "</tns:tentadoConsumado>\n" );
      out.write( "     <tns:tipoLocal>"        + tipoLocal          + "</tns:tipoLocal>\n" );
      out.write( "     <tns:idadeVitima>"      + idadeVitima + "</tns:idadeVitima>\n" );
      out.write( "     <tns:idadeAutor>"       + idadeAutor  + "</tns:idadeAutor>\n" );
      out.write( "    </tns:dadosOcorr>\n" );
      Linha = Lote.readLine();  // Proxima Linha
      x++;
    }          
    
    out.write( "  </tns:estupro>\n" );
    out.write( XML_tail );
    out.close();
  }   

}                                                                         

