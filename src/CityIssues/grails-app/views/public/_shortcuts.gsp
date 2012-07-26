<%@page import="com.cityissues.models.IssueType"%>
<%
  def dateFilterList = ["day", "month", "year"];
  def issueTypeFilterList =  IssueType.findAllByShowShortcut(true).sort{ 
    it.name.equals("info") ? "0" : it.label;
  };
  
  issueTypeFilterList.add(
    0,[name:"all", isOpenData:false, label:message(code:'issueType.all.label')]);
  
  def inputId, msg, checked, selected;
%>
<div id="shortcuts">
  <form name="shortcutsForm" id="shortcutsViewForm" method="POST">
    <!-- Date filter -->
    <div id="scDateFilter" class="sc-section selectButton noicons ui-corner-left">
      <div class="sc-section-title">
        <p>Data</p>
      </div>
      <div class="sc-section-body selectButtonMenu">
        <ul class="dataFilter">
          <%
            selected = false;
            msg = "";
            checked = params.fd ?: "year";
          %>
          <g:each in="${dateFilterList}" var="type">
            <% 
              inputId = "scInputDateFilter-${type}";
              selected = type.equals(checked);
              msg = message(code:'issueDate.'+type+'.label');
            %>
            <li>
              <input id="${inputId}" type="radio" name="issueDate" value="${type}" ${selected?'checked="true"':''}></input>
              <label title="${msg}" for="${inputId}" class="${type.equals('day')?'first':''} ui-corner-left issueDate-${type}${selected?' selected':''}">${msg}</label>
            </li>
          </g:each>
        </ul>
      </div>
    </div>

    <!-- Issue type filter -->
    <div id="scIssueTypeFilter" class="sc-section issueTypeSelect selectButton ui-corner-left">
      <div class="sc-section-title">
        <p>Categorias</p>
      </div>
      <div class="sc-section-body selectButtonMenu">
        <ul class="issueTypeFilter">
          <%
            selected = false;
            msg = "";
            checked = params.ft ?: "all";
            def type;
          %>
          <g:each in="${issueTypeFilterList}" var="it" status="i">
          <g:if test="${!it.isOpenData}">
            <% 
              type = it.name;
              msg = it.label;
              inputId = "scInputIssueType-${type}";
              selected = type.equals(checked);
              if(type.equals("all")) stl = "";
              else stl = "background-image:url(${resource(dir:'images/markers',file:type+'-simple.png')});";
            %>
            <li>
              <input id="${inputId}" class="issueType issueType-${type}" type="radio" name="issueType" value="${type}" ${selected?'checked="true"':''}></input>
              <label title="${msg}" for="${inputId}" class="${type.equals('all')?'first':''} 
                    ui-corner-left issueType-${type}${selected?' selected':''}" itclass="issueType-${type}" 
                    style="${stl}">${msg}</label>
            </li>
          </g:if>
          </g:each>
        </ul>
      </div>
    </div>

     <!-- Issue type filter -->
    <div id="scIssueTypeFilterOD" class="sc-section issueTypeSelect selectButton ui-corner-left">
      <div class="sc-section-title">
        <p>Dados Abertos</p>
      </div>
      <div class="sc-section-body selectButtonMenu">
        <ul class="issueTypeFilter">
          <%
            selected = false;
            msg = "";
            checked = params.ft ?: "all";            
          %>
          <g:each in="${issueTypeFilterList}" var="it" status="i">
          <g:if test="${it.isOpenData}">
            <% 
              type = it.name;
              msg = it.label;
              inputId = "scInputIssueTypeOD-${type}";
              selected = type.equals(checked);
              stl = "background-image:url(${resource(dir:'images/markers',file:it.icon+'-simple.png')});";
            %>
            <li>
              <input id="${inputId}" class="issueType issueType-${type}" type="radio" name="issueType" value="od-${type}" ${selected?'checked="true"':''}></input>
              <label title="${msg}" for="${inputId}" class="${type.equals('all')?'first':''} 
                    ui-corner-left issueType-${type}${selected?' selected':''}" itclass="issueType-${type}" 
                    style="${stl}">${msg}</label>
            </li>
          </g:if>
          </g:each>
        </ul>
      </div>
    </div>
  </form>
  <!--
  <div class="sc-section ui-corner-left">    
  </div>-->
</div>
  
