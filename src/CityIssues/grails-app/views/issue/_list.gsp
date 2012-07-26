<%
  def sortByValue=params.sb?.equals("score") ? "score":"date";
%>
<div id="issueList" class="">
  <div id="issueListHeader" class="ui-corner-tr">
    <div style="float:right;">
      <form id="issueListSortByForm" name="sortBy" action="#" method="POST">
        <div id="issueListSortBy">
          <input id="issueListSortByDate" type="radio" name="sortListBy" value="date" ${sortByValue.equals('date')?'checked="true"':''}/>
          <label title="${message(code:'issueList.sortBy.date.label')}" for="issueListSortByDate"><span class="ui-icon ui-icon-calendar">&nbsp;</span></label>
          <input id="issueListSortByScore" type="radio" name="sortListBy" value="score" ${sortByValue.equals('score')?'checked="true"':''}/>
          <label title="${message(code:'issueList.sortBy.score.label')}" for="issueListSortByScore"><span class="ui-icon ui-icon-heart">&nbsp;</span></label>
        </div>
      </form>
    </div>
    <div>
      <span id="issueListLocation">&nbsp;</span>
    </div>
    <div style="clear:both;"></div>
  </div>
  <div id="issueListBody" class="ui-corner-br">
    <g:render template="/issue/listBody" model="${[issues:issues]}"/>
  </div>
</div>