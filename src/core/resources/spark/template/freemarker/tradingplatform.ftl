<#assign content>

<h1>
${title}
</h1>

<form method="GET" action="/results">

<h3>
Select Rules
</h3>
<select name ="rules">
  <option value="CallMarket">CallMarket</option>
  <option value="ClockAuction">Clock Auction</option>
  <option value="LemonadeAnonRules">Lemonade</option>
  <option value="SSFPNoRecord">First Price</option>
  <option value="SSSPRules">Second Price</option>
</select><br>

<h3>
Select ValConfig
</h3>
<select name ="valConfig">
  <option value="AdditiveLab2Config">Lab 2</option>
  <option value="AdditiveUniformConfig">Uniform</option>
  <option value="LemonadeConfig">Lemonade</option>
  <option value="PredictionMarketDecoysConfig">Prediction Market with Decoys</option>
  <option value="SpecValV3Config">Spectrum Auction</option>
</select><br>

<h3>
Select Agent
</h3>
<select name ="agent">
  <option value="CallMarketLagAgent">Call Market Lag Agent</option>
  <option value="FixedAgent">Fixed Agent</option>
  <option value="Lab02Agent">Lab 02 Agent</option>
  <option value="Lab06Demo">Lab 06 Agent</option>
  <option value="LemonadeAgent">Lemonade Agent</option>
  <option value="RandomAgent">Random Agent</option>
  <option value="SimpleSealedAgent">Simple Sealed Agent</option>
  <option value="T1CombAgent">T1 Combinatorial Agent</option>
  <option value="T2CombAgent">T2 Combinatorial Agent</option>
  <option value="TestCallMarketAgent">Test Call Market Agent</option>
  <option value="TestPredictionMarketAgent">Test Prediction Market Agent</option>
  <option value="UpdateAgent">Update Agent</option>
</select><br>

<textarea name="numSims" placeholder="Number of Simulations"></textarea><br>
<textarea name="numTradeables" placeholder="number of Tradeables"></textarea><br>
<textarea name="lag" placeholder="Lag time"></textarea><br>

<button name="submit">Submit</button>
</form>
</#assign>
<#include "main.ftl">