<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Market Trade Processor</title>

<style>
	label.tahoma {
	    font-family: Calibri;
	    color: white;
	    margin-left: 10px;
	    font-weight: bold;
	}
	
	 .rounded {
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
		border-bottom-right-radius: 10px;
		border-bottom-left-radius: 10px;
	} 
	
	.roundedButton {
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		border-bottom-right-radius: 5px;
		border-bottom-left-radius: 5px;
		background-color: #f15925;
		color: white; 
		margin-left: 10px;
		width: 50px;
		font-weight: bold;
	} 
</style>

<script type="text/javascript" src="js/canvasjs.min.js"></script>
<script type="text/javascript" src="js/jquery.canvasjs.min.js"></script>
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>

<script type="text/javascript">

var updateInterval = 15000;
var from = '';
var to = '';

var idIntervalRates, idIntervalAmounts, idIntervalCountries = '';

function sendRequest() {

	var correct = true; 
	
	if (document.getElementById("fromSelect").value == 0) {
		
		alert("The From field is required");
		correct = false;
		
	} 
	
	else if (document.getElementById("toSelect").value == 0) {
		
		alert("The To field is required");
		correct = false;
		
	} 
	
	else if (document.getElementById("fromSelect").value == document.getElementById("toSelect").value) {
		
		alert("You must pick different options");
		correct = false;
		
	}
	
	if (from.length == 0 && to.length == 0 && correct){
		
		clearInterval(idIntervalRates);
		clearInterval(idIntervalAmounts);
		clearInterval(idIntervalCountries);
		
		from = document.getElementById("fromSelect").value;		
		to = document.getElementById("toSelect").value;

		//Call method check for each chart
		checkRatesAvg();
		checkAmountsSum();
		checkCountriesSum();
	}
	
}

</script>

<script type="text/javascript">

/************** Rates/Time Chart *******************/

var xMLHttpRequestRates = new XMLHttpRequest();

function checkRatesAvg() {
	
	if (from.length > 0 && to.length > 0) {
		
		xMLHttpRequestRates.open("Get", "/MarketTradeViewer/RatesAvgServlet?currencyFrom=" + from + "&currencyTo=" + to, true)
		xMLHttpRequestRates.onreadystatechange = checkRatesAvgResult;
		xMLHttpRequestRates.send(null);
	
	}

}

function checkRatesAvgResult() {
	
	if (xMLHttpRequestRates.readyState == 4 && xMLHttpRequestRates.status == 200) {
				
		generateRatesAvgChart(xMLHttpRequestRates.responseText);
		
	}
	
}

function generateRatesAvgChart(responseText) {
	
	var rateDataPoints = [];
	ratesAvgListObj = JSON.parse(responseText);
	for (var k in ratesAvgListObj.rates) {
		
		var res = ratesAvgListObj.rates[k].yearMonth.split("-");
		rateDataPoints.push({
	        x: new Date(res[0], res[1], res[2]),
	        y: ratesAvgListObj.rates[k].rateAvg
	    });
		
	}
	
	var chart = new CanvasJS.Chart("chartContainer",{
		title :{
			text: "Rate / Time",
			fontColor: "#183744"
		},
		theme: "theme1", 
		axisX: {
	        valueFormatString: "MMM-YY",
	        interval:1,
	        intervalType: "month"
		},
		toolTip:{
			   content:"rate: {y}" ,
			 },
		//animationEnabled: true,
		exportEnabled: true,
		zoomEnabled: true,
		data: [{
			type: "line",
			color: "#f15925",
			dataPoints : rateDataPoints
		}]
	});
	 
	chart.render();	
	
}

idIntervalRates = setInterval(function(){checkRatesAvg()}, updateInterval); 

/************** Amounts/Time Chart *******************/

var xMLHttpRequestAmounts = new XMLHttpRequest();

function checkAmountsSum() {
	
	if (from.length > 0 && to.length > 0) {
		
		xMLHttpRequestAmounts.open("Get", "/MarketTradeViewer/AmountsSumServlet?currencyFrom=" + from + "&currencyTo=" + to, true)
		xMLHttpRequestAmounts.onreadystatechange = checkAmountsSumResult;
		xMLHttpRequestAmounts.send(null);
	
	}

}

function checkAmountsSumResult() {
	
	if (xMLHttpRequestAmounts.readyState == 4 && xMLHttpRequestAmounts.status == 200) {
				
		generateAmountsSumChart(xMLHttpRequestAmounts.responseText);
		
	}
	
}

function generateAmountsSumChart(responseText) {

	var amountSellDataPoints = [];	
	var amountBuyDataPoints = [];
	amountsSumListObj = JSON.parse(responseText);
	
	for (var k in amountsSumListObj.amounts) {
		
		var res = amountsSumListObj.amounts[k].yearMonth.split("-");		
		
		amountSellDataPoints.push({
	        x: new Date(res[0], res[1], "01"),
	        y: amountsSumListObj.amounts[k].sellSum        
	    });
		
		amountBuyDataPoints.push({
	        x: new Date(res[0], res[1], "01"),
	        y: amountsSumListObj.amounts[k].buySum
	    });
		
	}
	
	var chartAmounts = new CanvasJS.Chart("chartContainer2",{
		title :{
			text: "Amounts / Time",
			fontColor: "#183744"
		},
		theme: "theme1", 
		axisX: {
	        valueFormatString: "MMM-YY",
	        interval:1,
	        intervalType: "month"
		},
		
		//animationEnabled: true,
		exportEnabled: true,
		zoomEnabled: true,		
		data: [{
			type: "stackedArea",
			name: "Sell",
	        showInLegend: "true",
			dataPoints : amountSellDataPoints
		},
		{
			type: "stackedArea",	
			name: "Buy",
	        showInLegend: "true",
			dataPoints : amountBuyDataPoints
		}
		
		]
	});
	 
	chartAmounts.render();	
	
}

idIntervalAmounts = setInterval(function(){checkAmountsSum()}, updateInterval);


/************** Countries Sell/Buy Chart *******************/

var xMLHttpRequestCountries = new XMLHttpRequest();

function checkCountriesSum() {
	
	if (from.length > 0 && to.length > 0) {
		
		xMLHttpRequestCountries.open("Get", "/MarketTradeViewer/CountriesSumServlet?currencyFrom=" + from + "&currencyTo=" + to, true)
		xMLHttpRequestCountries.onreadystatechange = checkCountriesSumResult;
		xMLHttpRequestCountries.send(null);
	
	}

}

function checkCountriesSumResult() {
	
	if (xMLHttpRequestCountries.readyState == 4 && xMLHttpRequestCountries.status == 200) {
				
		generateCountriesSumChart(xMLHttpRequestCountries.responseText);
		
	}
	
}

function generateCountriesSumChart(responseText) {
	
	var countriesSellDataPoints = [];
	var countriesBuyDataPoints = [];
	countriesSumListObj = JSON.parse(responseText);
	
	for (var k in countriesSumListObj.countries) {
		
		countriesSellDataPoints.push({
	        x: 10*k,
	        y: countriesSumListObj.countries[k].sellSum, 
	        label: countriesSumListObj.countries[k].originatingCountry
	    });
		
		countriesBuyDataPoints.push({
	        x: 10*k,
	        y: countriesSumListObj.countries[k].buySum, 
	        label: countriesSumListObj.countries[k].originatingCountry
	    });
		
	}
	
	var chart = new CanvasJS.Chart("chartContainer3",{
		title :{
			text: "Countries Sell/Buy",
			fontColor: "#183744"
		},
		theme: "theme1",	
		axisX: {
	        interval:10
		},
		//animationEnabled: true,
		exportEnabled: true,
		zoomEnabled: true,
		data: [{
			type: "column",
			dataPoints : countriesSellDataPoints,
			name: "Sell",
	        showInLegend: "true",
		},
		{
			type: "column",
			dataPoints : countriesBuyDataPoints,
			name: "Buy",
	        showInLegend: "true",
		}]
	});
	 
	chart.render();	
	
}

idIntervalCountries = setInterval(function(){checkCountriesSum()}, updateInterval); 

</script>

</head>
<body>
	
	<img src="images/cf-price-logo.png" alt="CurrencyFair">
	
	<br><br>
	
	<div style="background-color: #183744;" class="rounded">
	
		<br>
		
		<form id="form" name="form" method="get" action="ViewerServlet"> 
	
			<label class="tahoma">From:</label>
			<select id="fromSelect" name="fromSelect">
				<option value="0" selected>-- Select --</option>
				<c:forEach var="fromOption" items="${currencyFromList}">
					<option value="${fromOption}">${fromOption}</option>
				</c:forEach>
			</select>
			
			<label class="tahoma">To:</label>
			<select id="toSelect" name="toSelect">
				<option value="0" selected>-- Select --</option>
				<c:forEach var="toOption" items="${currencyToList}">
					<option value="${toOption}">${toOption}</option>
				</c:forEach>
			</select>
			 
			<!-- <input type="submit" name="Submit" value="GO!">  -->
			<input type="button" onclick='sendRequest();' value="GO!" class="roundedButton"></input>  
	        	     
		</form>
		<br><br>
		
		<div style="width: 98%">
			<div id="chartContainer" style="width: 50%; height: 300px; float:left; margin-left: 10px; margin-right: 10px;"></div> 
			<div id="chartContainer2" style="width: 50%; height: 300px; margin-left: 10px; margin-right: 10px;"></div> <br/>
			<div id="chartContainer3" style="width: 100%; height: 300px; margin-left: 1%"></div>
		</div>
		<br><br>
		
	</div>

</body>
</html>