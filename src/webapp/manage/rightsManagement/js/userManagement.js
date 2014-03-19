var tdValue = [];
var table;
var yesNo = ["是","否"];
var states = ["未激活","已激活"];
var rights = ["所有权限","最高权限","等级一","等级二","等级三","等级四","等级五"];
$(document).ready(function() {
	$("tr").each(function() {
		$(this).children().first().hide();
	});

	$("#edit").hide();

	$("#yes").click(function() {
		var x = 0,y = 0;
		$("tr").each(toggleFirst).children("td").each(function() {
			var index = $("table").find("td").index(this);
			tdValue[index] = $(this).html();
		});
		toggleBtn();
		$("input[type=checkbox]").attr("checked",false);
		table = $("table").html();
		$("tr td").each(function() {
			var index = $(this).index();
			switch(index) {
				case 2:case 3:case 4:case 6: $(this).dblclick(toEditable);
			}
		});
	});

	$("#all").click(chooseAll);

	$("#opposite").click(reverse);

	$("#delete").click(deleteChosen);

	$("#cancel").click(function() {
		// $("tr").children("td").css("color","#000").each(function() {
		// 	var index = $("table").find("td").index(this);
		// 	$(this).html(tdValue[index]);
		// });
		$("table").html(table);
		$("tr").each(toggleFirst);
		$("tr td").each(function() {
			$(this).unblind(toEditable);
		});
		toggleBtn();
	});

	$("#verify").click(function() {
		$("tr").each(toggleFirst);
		$("tr td").each(function() {
			$(this).unblind(toEditable);
		});
		toggleBtn();
	});

// 	$("tr td").each(function() {
// 		var index = $(this).index();
// 		switch(index) {
// 			case 2:(item=rights,$(this).dblclick(toChoosable));break;
// 			case 3:$(this).dblclick(toEditable);break;
// 			case 4:(item=yesNo,$(this).dblclick(toChoosable));break;
// 			case 6:(item=states,$(this).dblclick(toChoosable));
// 		}
// 	});
 });

function getSelect(now,pos,index,value,w,h) {
	var item = [];
	switch(pos) {
		case 2:item = rights.concat();break;
		case 4:item = yesNo.concat();break;
		case 6:item = states.concat();
	}
	// console.log(index);
	// console.log(item);
	// console.log(value);
	var select = $("<select></select>");
	for(var i=0;i<item.length;i++) {
		var option = $("<option>"+item[i]+"</option>");
		if(item[i] == value) {
			option.attr("selected",true);
		}
		select.append(option);
	}
	var width = parseInt(w.substring(0,w.indexOf('p')))-10;
	var height = parseInt(h.substring(0,h.indexOf('p')))-10;
	select.css("width",width+"px").css("height",height+"px");
	select.blur(function() {
		now.html($(this).children("option:selected").html());
		now.dblclick(toEditable);
		$(this).remove();
		if(now.html() != tdValue[index]) {
			now.css("color","#890101");
		}
		else {
			now.css("color","#000");
		}
	});	
	return select;
}

function getInput(now,index,value,w,h) {
	var input = $("<input type='text'/>");
	var width = parseInt(w.substring(0,w.indexOf('p')))-2;
	var height = parseInt(h.substring(0,h.indexOf('p')))-2;
	input.css("width",width+"px").css("height",height+"px").val(value);
	input.blur(function() {
		now.html($(this).val());
		now.dblclick(toEditable);
		$(this).remove();
		if(now.html() != tdValue[index]) {
			now.css("color","#890101");
		}
		else {
			now.css("color","#000");
		}
	});
	return input;
}

function toEditable() {
	var now =$(this);
	var index = $("table").find("td").index(now);
	var pos = $(this).index();
	var value = now.html();
	// console.log(index);
	// console.log(tdValue[index]);
	// console.log(value);
	// console.log(pos);
	now.html(null);
	var w = now.css("width");
	var h = now.css("height");
	var input;
	switch(pos) {
		case 3: input = getInput(now,index,value,w,h);break;
		case 2:case 4:case 6: input = getSelect(now,pos,index,value,w,h);
	}
	input.css("border","none").css("background-color","#fff")
	.css("font-size",now.css("font-size")).css("outline","none")
	.appendTo(now).get(0).focus();
	now.unbind("dblclick");
}

// function toChoosable() {
// 	var now = $(this);
// 	var index = $("table").find("td").index(now);
// 	var content = now.html();
// 	var w = now.css("width");
// 	var h = now.css("height");
// 	var width = parseInt(w.substring(0,w.indexOf('p')))-10;
// 	var height = parseInt(h.substring(0,h.indexOf('p')))-10;
// 	now.html(null);
// 	var input = $("<select></select>");
// 	for(var i=0;i<item.length;i++) {
// 		var option = $("<option>"+item[i]+"</option>");
// 		if(item[i]==tdValue[index]) {
// 			option.attr("checked",true);
// 		}
// 		input.append(option);
// 	}
// 	input.css("border","none").css("background-color","#fff")
// 	.css("font-size",now.css("font-size")).css("width",width+"px")
// 	.css("height",height+"px").css("outline","none")
// 	.val(content).appendTo(now).get(0).select();
// 	input.blur(function() {
// 		now.html($(this).val());
// 		now.dblclick(toChoosable);
// 		$(this).remove();
// 		if(now.html() != tdValue[index]) {
// 			now.css("color","#890101");
// 		}
// 		else {
// 			now.css("color","#000");
// 		}
// 	});
// 	now.unbind("dblclick");
// }

// function toEditable() {
// 	var now = $(this);
// 	var content = now.html();
// 	var w = now.css("width");
// 	var h = now.css("height");
// 	var width = parseInt(w.substring(0,w.indexOf('p')))-2;
// 	var height = parseInt(h.substring(0,h.indexOf('p')))-2;
// 	now.html(null);
// 	var input = $("<input type='text'/>");
// 	input.css("border","none").css("background-color","#fff")
// 	.css("font-size",now.css("font-size")).css("width",width+"px")
// 	.css("height",height+"px").css("outline","none")
// 	.val(content).appendTo(now).get(0).select();
// 	input.blur(function() {
// 		now.html($(this).val());
// 		now.dblclick(toEditable);
// 		$(this).remove();
// 		var index = $("table").find("td").index(now);
// 		if(now.html() != tdValue[index]) {
// 			now.css("color","#890101");
// 		}
// 		else {
// 			now.css("color","#000");
// 		}
// 	});
// 	now.unbind("dblclick");
// }

function toggleBtn() {
	$("#init").toggle(0);
	$("#edit").toggle(0);
	$(".page").toggle(0);
}

function toggleFirst() {
	$(this).children().first().toggle(0);
}

function chooseAll() {
	$('table input[type=checkbox]').attr("checked",true);
}

function reverse() {
	$('table input[type="checkbox"]').each(function() {
		var now = $(this);
		if(now.attr("checked")) {
			now.attr("checked",false);
		}
		else {
			now.attr("checked",true);
		}
	});
}

function deleteChosen() {
	$("table input[type='checkbox']").each(function() {
		if($(this).attr("checked")) {
			$(this).parent().parent().remove();
		}
	});
}