<div id="infoWindow" class="info-window">
  <div class="header">
  </div>
  <div class="body">
    <g:if test="${issue}">
      <div class="date"><g:formatDate format="${message(code:'default.date.format')}" date="${issue.dateStart?:issue.dateCreated}" /></div>
      <div class="description"><bai:printDescription issue="${issue.id}"/></div>
      <div class="address">${issue.address?.formatted}</div>
    </g:if>
    <g:else>
      <div class="title">Problema desconhecido</div>
    </g:else>
  </div>
  <div class="footer">
    <g:if test="${issue}">
      <div style="float:left; line-height:28px; height: 28px; ">
        <a href="#${issue.id}" id="infoWindowCommentsLink">
          <span class="ui-icon ui-icon-comment" style="display:inline-block;">&nbsp;</span>
          <g:message code="link.comments"/>
          <span class="number">(...)</span>
        </a>
      </div>
      <div style="float:left; padding-top:3px; margin-left:20px;">
          <% def twittUrl = createLink(absolute:true, controller:'public', action:'index', params:[i:issue.id]); %>
          <a href="https://twitter.com/share?url=${twittUrl}" target="_blank" url="${twittUrl}" data-url="${twittUrl}" class="twitter-share-button" data-count="none" data-via="suricatourbano"><img src="${resource(dir:'images', file:'tweetn.png')}"></img></a>
          <script type="text/javascript" src="//platform.twitter.com/widgets.js"></script>
        </div>
      <div class="icons-container">
     
        <div class="vote-container">
          <g:render template="/issue/infoWindowThumbs" model="${[issue:issue]}"/>
        </div>
        
        <div class="clear"></div>
      </div>
      <div style="clear:both;"></div>
      <bai:isIssueOwner issue="${issue.id}">
        <div class="ui-state-highlight ui-corner-all owner">
          <a href="#${issue.id}" id="infoWindowDeleteLink">
            <span class="ui-icon ui-icon-trash" style="display:inline-block;">&nbsp;</span>
            <g:message code="link.delete"/>
          </a>
        </div>
      </bai:isIssueOwner>
    </g:if>
  </div>
</div>
