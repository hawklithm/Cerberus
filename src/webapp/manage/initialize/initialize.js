$(function () {
	var content = $("#content");
	var num = 4;
	var div = $("<div class = 'realTimeInformation'></div>");
	for( var i = 0; i < num; i++ ){
		var div = $("<div class = 'realTimeInformation'></div>");
		content.append(div);
	}
	var width = 95/num;
	var realTimeInformationDiv = $(".realTimeInformation");
	realTimeInformationDiv.css({
		width: width+"%"
	});
	var textP1 = $("<p>订单信息实时显示</p>");
	realTimeInformationDiv.eq(0).append(textP1);
	var textP2 = $("<p>运单信息实时显示</p>");
	realTimeInformationDiv.eq(1).append(textP2);
	var textP3 = $("<p>生产过程信息实时显示</p>");
	realTimeInformationDiv.eq(2).append(textP3);
	var textP4 = $("<p>视频信息实时显示</p>");
	realTimeInformationDiv.eq(3).append(textP4);
	for( var i = 0; i < realTimeInformationDiv.length; i++) {
		var ul = $("<ul></ul>");
		realTimeInformationDiv.eq(i).append(ul);
	}
	var text1 = $("<li>华西医院提交了订单</li>");
	realTimeInformationDiv.eq(0).find("ul").append(text1);
	var text2 = $("<li>华西医院提交了订单</li>");
	realTimeInformationDiv.eq(1).find("ul").append(text2);
	var text3 = $("<li>华西医院提交了订单</li>");
	realTimeInformationDiv.eq(2).find("ul").append(text3);
	var text4 = $("<li>华西医院提交了订单</li>");
	realTimeInformationDiv.eq(3).find("ul").append(text4);

});