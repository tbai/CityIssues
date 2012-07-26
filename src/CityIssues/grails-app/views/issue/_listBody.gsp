<g:each in="${issues}">  
  <g:render template="/issue/listItem" model="${[issue:it]}"/>
</g:each>