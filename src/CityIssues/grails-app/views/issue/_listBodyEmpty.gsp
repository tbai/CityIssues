<%@page import="com.cityissues.models.IssueType"%>
<div class="textMessage issueListMessage">
  <g:if test="${requestParams.filterDate != 'year'}">
    <p>
      Nenhum alerta foi encontrado na região <span class="bold">${requestParams.listLocationFormatted}</span> durante este período.
    </p>
  </g:if>
  <g:elseif test="${requestParams.filterType == 'all'}">
    <p>
      Nenhum alerta foi encontrado na região <span class="bold">"${requestParams.listLocationFormatted}"</span>
    </p>
  </g:elseif>
  <g:else>
    <%
        def msgCode = 'issueType.'+requestParams.filterType+'.label'; 
        def issueType = IssueType.findByName(requestParams.filterType);
        def category = message(code:msgCode);
        if(issueType && issueType.description){
            category = issueType.description;
        }
    %>
    <p>
      Nenhum alerta da categoria <span class="bold">"${category}"</span> foi encontrado na região
      <span class="bold">"${requestParams.listLocationFormatted}"</span>
    </p>
  </g:else>
  <p>Para listar alertas de outras regiões digite o endereço acima e clique em "Ir"</p>
</div>