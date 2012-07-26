package rs.gov.dadosabertos;

import java.io.CharArrayWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class ConsomeXML extends DefaultHandler {

  private static List<TipoEstatED> tipos = new ArrayList();
  private List<IndicadorED> indicadores = null;
  private TipoEstatED currentTipo;
  private IndicadorED currentInd;
  private CharArrayWriter contents = new CharArrayWriter();

  public void startElement( String namespaceURI,String localName,String qName,Attributes attr ) throws SAXException {
    contents.reset();
    if ( localName.equals( "label" ) ) {
      //Inicia novo array de indicadores
      indicadores = new ArrayList();
      //Inicia novo tipo
      currentTipo = new TipoEstatED();
      tipos.add( currentTipo );
      currentTipo.setListaIndicadorED(indicadores);         

    }

    if ( localName.equals( "indicador" ) ) {
      currentInd = new IndicadorED();
      indicadores.add(currentInd);
    }             
  }

  public void endElement( String namespaceURI,String localName,String qName ) throws SAXException { 
    if ( localName.equals( "nomeTipo" ) ) {
      currentTipo.setNome( contents.toString() );
    }

    if ( localName.equals( "periodo" ) ) {
      currentTipo.setPeriodo( contents.toString() );
    }

    if ( localName.equals( "nomeInd" ) ) {
      currentInd.setNome( contents.toString() );       
    }

    if ( localName.equals( "qtde" ) ) {
      currentInd.setQtde( contents.toString() );
    }
  }

  public void characters( char[] ch, int start, int length ) throws SAXException {
    contents.write( ch, start, length );
  }  


  public static ArrayList getTipos() {
    return (ArrayList)tipos;
  }


  public static void main( String[] argv ){    
    try {
      XMLReader xr = XMLReaderFactory.createXMLReader(); 
      ConsomeXML consumidor = new ConsomeXML();
      xr.setContentHandler( consumidor );
      String arquivo_csv;
      if ( argv != null && argv.length > 0 ) {
        arquivo_csv = argv[0];
        xr.parse( new InputSource( new FileReader( arquivo_csv )) );
      }
      else {        
        xr.parse( new InputSource( new FileReader( "C:/documentacao_tecnica/rs_dados_abertos/OcorIndicadores2011.xml" )) );
      }
      List<TipoEstatED> listaTipos;
      listaTipos = getTipos();
      List<IndicadorED> listaIndicadoresDoTipoCorrente;

      for ( TipoEstatED auxTipo : listaTipos ) {
        listaIndicadoresDoTipoCorrente = auxTipo.getListaIndicadorED();
        System.out.println("#########################################################");
        System.out.println( auxTipo.getNome() + " | " + auxTipo.getPeriodo() );        
        for ( IndicadorED auxInd : listaIndicadoresDoTipoCorrente ) {
          System.out.println("Ind. Nome: " + auxInd.getNome() );
          System.out.println("Ind. Qtde: " + auxInd.getQtde() );
        }
      }               
    }catch ( Exception e )  {
      e.printStackTrace();
    }
  }

}
