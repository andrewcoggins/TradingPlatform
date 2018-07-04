<#assign content>

<h1>
${title}
</h1>

<form method="GET" action="/results">

<textarea name="rules" placeholder="Select Rules"></textarea><br>
<textarea name="numSims" placeholder="Number of Simulations"></textarea><br>
<textarea name="numTradeables" placeholder="number of Tradeables"></textarea><br>
<textarea name="lag" placeholder="Lag time"></textarea><br>
<textarea name="valConfig" placeholder="Valuation type"></textarea><br>
<textarea name="agent" placeholder="Agent Type"></textarea><br>
<button name="submit">Submit</button>
</form>
</#assign>
<#include "main.ftl">