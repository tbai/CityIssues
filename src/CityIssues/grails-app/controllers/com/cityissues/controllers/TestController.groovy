package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import java.util.ResourceBundle
import com.cityissues.models.*
import com.cityissues.services.*
import grails.converters.*
import groovyx.net.http.ContentType

import rs.gov.dadosabertos.*
import rs.gov.dadosabertos.ConsomeXML
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

class TestController {
    def networkService
    def broadcastService
    def googleMapsService
    def addressService
    def issueService
    
   /* def index = {
        render "test controller"
    }*/

    def index = {
        def arquivo_csv = "/home/tbai/Dropbox/CityIssues/utilities/dadosabertos/OcorIndicadores2011.xml"
        try {
          XMLReader xr = XMLReaderFactory.createXMLReader(); 
          ConsomeXML consumidor = new ConsomeXML();
          xr.setContentHandler( consumidor );
          
          xr.parse( new InputSource( new FileReader( arquivo_csv )) );
                    
          List<TipoEstatED> listaTipos;
          listaTipos = ConsomeXML.getTipos();
          List<IndicadorED> listaIndicadoresDoTipoCorrente;

          def issueAddr, issueType, issueDesc, issueDate

          for ( TipoEstatED auxTipo : listaTipos ) {
            listaIndicadoresDoTipoCorrente = auxTipo.getListaIndicadorED();

            
            issueAddr = addressService.create("${auxTipo.getNome()} - RS, Brazil")
            issueDate = Date.parse("yyyy-MM", "${auxTipo.getPeriodo()}")
            
            for ( IndicadorED auxInd : listaIndicadoresDoTipoCorrente ) {
              issueDesc = auxInd.getNome()
              issueType = "${issueDesc.toLowerCase().trim()}"
                if(!IssueType.findByName(issueType)){
                    new IssueType(name:issueType, icon:issueType, label:issueDesc, description:issueDesc, isOpenData:true).save()
                }
              for( def i =0; i< auxInd.getQtde().toInteger(); i++ ){
                

                def result = issueService.createIssue(issueType, issueDesc, issueAddr)
                result.issue.dateCreated = issueDate
                result.issue.save()
                println "New issue: ${result.issue.dateCreated}" 
              }
            }
            
          }               
        }catch ( Exception e )  {
          e.printStackTrace();
        }

        render "Dados Carregados"
    }
    
    def clear = {
        def owner = User.findByEmail("suricatourbano@gmail.com")
        println "deleting issues from $owner"
        println "Issues " + Issue.findAllByUser(owner).size()
        Issue.findAllByUser(owner).collect{ issue-> {->
            println "deleting issue $issue"
            issue.delete()
        }}.each { it() }
        render "clear"
    }
    
    def broadcast = {
        def broadcast = Broadcast.findByName("EPTC_POA")
        broadcastService.makeRequest(broadcast)
        render "ok"
    }
    
/*
    def findAddress = {
        def html = ""
        broadcastService.testAddressess.each{ addr ->
        //def addr = broadcastService.testAddressess[0]
            html += "<div>${addr}</div>"
            html += "<div>${broadcastService.findAddress(addr)}</div>"
            html += "<div>${'-'*160}</div>"
        }
        
        render text:html, contentType:"text/html"
    }*/

    def twitter = {
        def q = "from:EPTC_POA"
        def json = [:]
        withHttp(uri: "http://search.twitter.com", contentType:ContentType.JSON) {
           json = get(path: '/search.json', query: [q:q,rpp:100])
        }
        
        def html = "["
        json.results?.each{
            html += "<div>\"${it.text}\",</div>"
        }
        html+= "]"
        render text:html, contentType:"text/html"
    }

    def geo = {
        def broadcast = Broadcast.list()[0]
        def json = googleMapsService.geocode("rio de janeiro, RJ - Brasil")
        println "json=" + json
        def html = ""
        json.results?.each{
            html += "<div>----------------------------------------------------------</div>"
            html += "<div>formatted_address:"+it.formatted_address +"</div>"
            html += "<div>types:"+it.types +"</div>"
        }
        def geocodeResult = json.results[0]
        def address = googleMapsService.getAddressFromGeocodeResult(geocodeResult)
        println "Address="+address
        println "broadcast=" + broadcast
        println "result=" + broadcastService.createIssue(broadcast, address, "teste intersection" )
        render text:html, contentType:"text/html"
    }
    
    def geocode = {
        def text = "07h58 - Colisão entre dois carros. Av. Pernambuco, prox. João Inácio. Uma vítima com ferimentos leves. SAMU em deslocamento. EPTC no local."
        def html = "<div>text:</div>"
        html += "<div>$text</div>"
        
        def addressList = broadcastService.findAddress(text )
        html += "<div>Address List[0]: ${addressList[0]}</div>"
        
        def json = googleMapsService.geocode(addressList[0] + ", Porto Alegre, RS - Brasil")
        println "json=" + json
        json.results?.each{
            html += "<div>----------------------------------------------------------</div>"
            html += "<div>formatted_address:"+it.formatted_address +"</div>"
            html += "<div>types:"+it.types +"</div>"
        }
        render text:html, contentType:"text/html"
    }
    
    def places = {
        //def result = placesService.search( "-30.0474313,-51.1836734") 
        def result = googleMapsService.search( "-30.0474313,-51.1836734") 
        render result as JSON
    }
    
    def location = {
        def location = networkService.getGeoIpLocation("187.107.0.156")
        render location.countryName + " " + location.city
    }

    @Secured(['ROLE_ADMIN'])
    def admins = {
        render 'Logged in with ROLE_ADMIN'
    }

    @Secured(['ROLE_USER'])
    def users = {
        render 'Logged in with ROLE_USER'
    }

    def facebook = {

    }

    def js = {
        ResourceBundle rb = ResourceBundle.getBundle("/messages", new Locale("pt", "BR"));
        println rb
        render "ok"
    }

    def numberOfComments = {
        def json
        withHttp(uri: "http://http://maps.googleapis.com/maps/api/geocode/json", contentType:ContentType.JSON) {
            json = get(path: '/', query: [address:"rua felizardo furtado, porto alegre, brazil"])
          
        }
        render json
    }
}
