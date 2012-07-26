<g:if test="${issue}">
  <div class="issueListItem ui-state-default issueType-${issue.type?.name}" id="il_${issue?.id}" style="background-image:url(images/markers/${issue.type.icon}.png)">
    <div class="line">
      <div class="right thumbs-container">
        <span class="text score">${issue.totalVotes>0?'+':''}${issue.totalVotes}</span>
        <span class="ui-icon ui-icon-heart"></span>
      </div>
      <div class="left line1 dateCreated"><span class="date"><g:formatDate format="${message(code:'default.date.shortFormat')}" date="${issue.dateStart}" /></span> - ${issue.address?.formatted}</div>
      <div style="clear:both;"></div>
    </div>
    <div class="description">
      <g:if test="${issue.type.isOpenData}">
        ${issue.description}
      </g:if>
      <g:else>
        <bai:printDescription issue="${issue.id}"/>
      </g:else>
    </div>
  </div>
</g:if>