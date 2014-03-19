$(document).ready(function() {
	var date = new Date();
	var today = date.getFullYear()+"-";
	var month = date.getMonth()+1;
	month = month<10?("0"+month):month;
	var dt = date.getDate();
	dt = dt<10?("0"+dt):dt;
	today += (month+"-"+dt);
	$('#beginTime').attr("min","2013-01-01").attr("max",today).attr("value",today);
	$('#endTime').attr("min","2013-01-01").attr("max",today).attr("value",today);
});