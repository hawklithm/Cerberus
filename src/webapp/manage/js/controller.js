$(function(){
	var iframe = $("iframe");
	$(".orderDetail").on("click",function(){
		iframe.attr("src","orderManagement/html/orderDetail.html");
	});
	// $(".orderPicking").on("click",function(){
	// 	iframe.attr("src","orderManagement/html/orderPicking.html");
	// });
	// $(".orderSentOut").on("click",function(){
	// 	iframe.attr("src","orderManagement/html/orderSentOut.html");
	// });
	// $(".hospitalSign").on("click",function(){
	// 	iframe.attr("src","orderManagement/html/hospitalSign.html");
	// });
	$(".transportOrder").on("click",function(){
		iframe.attr("src","LogisticsTracking/html/transportOrder.html");
	});
	$(".allocationOfPersonnelAndVehicles").on("click",function(){
		iframe.attr("src","LogisticsTracking/html/allocationOfPersonnelAndVehicles.html");
	});
	$(".ordersPath").on("click",function(){
		iframe.attr("src","LogisticsTracking/html/ordersPath.html");
	});
	$(".itemInformation").on("click",function(){
		iframe.attr("src","productionManagement/html/itemInformation.html");
	});
	$(".packageInformation").on("click",function(){
		iframe.attr("src","productionManagement/html/packageInformation.html");
	});
	// $(".equipmentPretreatment").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/equipmentPretreatment.html");
	// });
	// $(".cleaningDisinfection").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/cleaningDisinfection.html");
	// });
	// $(".equipmentInspection").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/equipmentInspection.html");
	// });
	// $(".bale").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/bale.html");
	// });
	// $(".sterilization").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/sterilization.html");
	// });
	// $(".sterileStorage").on("click",function(){
	// 	iframe.attr("src","productionManagement/html/sterileStorage.html");
	// });
	$(".alarmInformation").on("click",function(){
		iframe.attr("src","sectorManagement/html/alarmInformation.html");
	});
	$(".orderInformation").on("click",function(){
		iframe.attr("src","sectorManagement/html/orderInformation.html");
	});
	$(".equipmentUtilization").on("click",function(){
		iframe.attr("src","sectorManagement/html/equipmentUtilization.html");
	});
	$(".itemManage").on("click",function() {
		iframe.attr("src","sectorManagement/html/itemManage.html");
	});
	$(".userManagement").on("click",function(){
		iframe.attr("src","rightsManagement/html/userManagement.html");
	});
	$(".adduser").on("click",function(){
		iframe.attr("src","rightsManagement/html/adduser.html");
	});
	$(".staffQueries").on("click",function(){
		iframe.attr("src","rightsManagement/html/staffQueries.html");
	});
	$(".addStaff").on("click",function(){
		iframe.attr("src","rightsManagement/html/addStaff.html");
	});

});