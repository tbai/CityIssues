<%
  def totalVotes =  issue?.totalVotes;
  def userVote = bai.currentVote(issue:issue);
%>
<div class="user-vote user-vote-${userVote}">
  <a class="link-icon vote-link vote-negative${userVote.equals('negative')?' selected':''}" href="#"></a>
  <a class="link-icon vote-link vote-positive${userVote.equals('positive')?' selected':''}" href="#"></a>
  <span class="vote-text ${totalVotes>0?"positive-text":"negative-text"}">${totalVotes>0?'+':''}${totalVotes}</span>
</div>
