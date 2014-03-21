var tdValue = [], table2post = [];
var table, delId = [];
var thName = ["check","userId","role","username","isEmployee","hospitalId","enable"];

var count = 0;
var defaultLengthPerPage=15;
/***
 * 
 * 修改URL
 * 
 ***/
var respectiveUrl = "/Cerberus/OrderManager";
//    var status = 0;
var originalTable=$("table").html();

$(document).ready(function() {
    $(".previous").on("click", function() {
        count++;
        console.log(count);
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl,content);
//        if(0 === status){
//            content = generateContent(count);
//        }else{
//            content = getFormValue(count);
//        }
//        var data = sentRequest(content);
//        var data = getData();//取假数据，连上服务器后注释掉这一行，让上一行取消注释。
//        var dataObject = jsonToObject(data);
//        render(dataObject);
    });
    $(".next").on("click",function(){
        if (count>0){
            count--;
            console.log(count);
            var content=generateRequest(count);
            ajaxSendRequest(respectiveUrl,content);
        }
    });

	$("tr").each(function() {
		$(this).children().first().hide();
	});

	$("#edit").hide();

	$("#yes").click(function() {
		delId = [];
		table2post = [];
		var x = 0,y = 0;
		// $("tr").each(toggleFirst).children("td").each(function() {
		// 	// var index = $("table").find("td").index(this);
		// 	tdValue[index] = $(this).html();
		// });
		$("tr").each(toggleFirst).each(function() {
			var id = $(this).children("td").eq(1).html();
			tdValue[id] = [];
			for(var i=2;i<thName.length;i++) {
				tdValue[id][i] = $(this).children("td").eq(i).html();
			}
		});
		// console.log(tdValue);
		toggleBtn();
		$("input[type=checkbox]").attr("checked",false);
		table = $("table").html();
		$("tr td").each(function() {
			var index = $(this).index();
			switch(index) {
				case 1: table2post[$(this).html()]=[];break;
				case 2:case 3:case 4:case 6: $(this).dblclick(toEditable);
			}
		});
	});

	$("#all").click(chooseAll);

	$("#opposite").click(reverse);

	$("#delete").click(deleteChosen);

	$("#cancel").click(function() {
		$("table").html(table);
		$("tr").each(toggleFirst);
		$("tr td").each(function() {
			var index = $(this).index();
			$(this).css("color","#000");
			switch(index) {
				case 2:case 3:case 4:case 6: $(this).unbind("dblclick");
			}
		});
		toggleBtn();
	});

	$("#verify").click(function() {
		sendOperateMessage();
		$("tr").each(toggleFirst);
		$("tr td").each(function() {
			$(this).css("color","#000");
			var index = $(this).index();
			switch(index) {
				case 2:case 3:case 4:case 6: $(this).unbind("dblclick");
			}
		});
		toggleBtn();
	});

	initPage();
 });

function getSelect(now,id,pos,value,w,h) {
	var item = [];
	switch(pos) {
		case 2:item = rights.concat();break;
		case 4:item = yesNo.concat();break;
		case 6:item = states.concat();
	}
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
		if(now.html() != tdValue[id][pos]) {
			now.css("color","#890101");
			table2post[id][thName[pos]] = now.html();
		}
		else {
			now.css("color","#000");
			table2post[id][thName[pos]] = "";
		}
		console.log(table2post[id]);
	});	
	return select;
}

function getInput(now,id,pos,value,w,h) {
	var input = $("<input type='text'/>");
	var width = parseInt(w.substring(0,w.indexOf('p')))-2;
	var height = parseInt(h.substring(0,h.indexOf('p')))-2;
	input.css("width",width+"px").css("height",height+"px").val(value);
	input.blur(function() {
		now.html($(this).val());
		now.dblclick(toEditable);
		$(this).remove();
		if(now.html() != tdValue[id][pos]) {
			now.css("color","#890101");
			table2post[id][thName[pos]] = now.html();
		}
		else {
			now.css("color","#000");
			table2post[id][thName[pos]] = "";
		}
		console.log(table2post[id]);
	});
	return input;
}

function toEditable() {
	var now =$(this);
	// var index = $("table").find("td").index(now);
	var id = now.parent().children("td").eq(1).html();
	var pos = $(this).index();
	var value = now.html();
	now.html(null);
	var w = now.css("width");
	var h = now.css("height");
	var input;
	switch(pos) {
		case 3: input = getInput(now,id,pos,value,w,h);break;
		case 2:case 4:case 6: input = getSelect(now,id,pos,value,w,h);
	}
	input.css("border","none").css("background-color","#fff")
	.css("font-size",now.css("font-size")).css("outline","none")
	.appendTo(now).get(0).focus();
	now.unbind("dblclick");
}

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
			var tr = $(this).parent().parent();
			delId.push(tr.children("td").eq(1).html());
			tr.remove();
		}
	});
	console.log(delId);
}


/***************************************数据请求函数**************************************************/

function generateRequest(count){
    var requestOrder=new Object();
    requestOrder.main={};
    return generateRequestMessage(count,defaultLengthPerPage,"operateQuery",requestOrder);
}

function generateRequestMessage (offset,length,operateType,orderCondition) {
    var content = new Object();
    content.condition = new Object();
    content.condition.operateType = operateType;
    content.condition.offeset = offset;
    content.condition.lengths = length;
    content.rows = [];
    content.rows[0] = orderCondition;
    return content;
}

function ajaxSendRequest(targetUrl,content) {
    $.ajax({
        url:targetUrl,
        type:'POST',
        dataType:'json',
        data: JSON.stringify(content),
        mimeType:"application/json",
        success: function(data) {
            console.log("success" + data);
            render(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("XMLstatus:" + XMLHttpRequest.status+ "XMLreadyState:" + XMLHttpRequest.readyState + "textStatus:" + textStatus);
            alert("XMLstatus:" + XMLHttpRequest.status+ "XMLreadyState:" + XMLHttpRequest.readyState + "textStatus:" + textStatus);
        }
    });
}

function render(data){
    var dataObject = jsonToObject(data);
    console.log("render:" + dataObject);
    var tpl =
        "{{#condition}}\
            <tr>\
            	<td><input type='checkbox' //></td>\
                <td>{{userId}}</td>\
                <td>{{role}}</td>\
                <td>{{username}}</td>\
                <td>{{isEmployee}}</td>\
                <td>{{hopitalId}}</td>\
                <td>{{enable}}</td>\
                <td>{{createdTime}}</td>\
                <td>{{modifiedTime}}</td>\
            </tr>\
        {{/condition}}";
    var datas = dataObject.rows;
    var table = $("table");
    table.html(originalTable);
    for (var index in datas){
        var html = Mustache.to_html(tpl, datas[index]);
        table.append(html);
    }
}

function jsonToObject(data){
    return JSON.parse(data);
}

function initPage() {
    var content = generateRequest(count);
    ajaxSendRequest(respectiveUrl,content);
}

function sendOperateMessage() {
	var content = modifyOperateMessage();
	// console.log("modify: "+JSON.stringify(content));
	ajaxSendRequest(respectiveUrl,content);
	content = deleteOperateMessage();
	// console.log("delete: "+JSON.stringify(content));
	ajaxSendRequest(respectiveUrl,content);
}

function deleteOperateMessage() {
	var content = {};
	content.condition = {
		"operateType":"operateDelete",
		"tableType":"user"/************************修改表******************************/
	};
	content.rows = [];
	for(var i=0;i<delId.length;i++) {
		content.rows[i] = {
			"userId":delId[i]
		};
	}
	return content;
}

function modifyOperateMessage() {
	var content = {};
	content.condition = {
		"operateType":"operateModify",
		"tableType":"user"/************************修改表******************************/
	};
	content.rows = [];
	var rowsCnt = 0;
	for(var i in table2post) {
		var row = table2post[i];
		// console.log(table2post);
		var jCnt = false;
		var rowsMap = new Object();
		for(var j in row) {
			if(!jCnt) {
				// content.rows[rowsCnt] = [];
				// content.rows[rowsCnt]["userId"] = i;
				rowsMap["userId"] = i;
				jCnt = true;
			}
			if(row[j] != "") {
				rowsMap[j] = row[j];
				// content.rows[rowsCnt][j] = row[j];
			}
		}
		if(jCnt) {
			content.rows[rowsCnt] = rowsMap;
		}
		rowsCnt++;
	}
	// console.log(content.rows);
	return content;
}