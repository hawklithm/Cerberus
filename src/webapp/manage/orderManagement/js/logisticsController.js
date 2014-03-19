$(function(){
	var count = 0;
	$(".previous").on("click", function() {
		count++;
        console.log(count);
		var content = generateContent(count);
		var data = sentRequest(content);
	});
	$(".next").on("click",function(){
		count--;
        console.log(count);
		var content = generateContent(count);
		var data = sentRequest(content);
	});
	function generateContent (count) {
		var content = new Object();
		content.condition = new Object();
		content.condition.operateType = "operateQuery";
		content.condition.offeset = count;
		content.condition.lengths = 14;
		content.row = [];
		var requestOrder = new Object();
		requestOrder.main = {};
		content.row[0] = requestOrder;
	}
	function sentRequest(content) {
		$.ajax({
			url:"/Cerberus/TransManager",
			dataType:"POST",
			data: JSON.stringify(content),
			mimeType:"application/json",
			success: function(data) {
				console.log("success" + data);
				return data;
			},
			error:function(data, status, er){
				console.log("error");
				alert("error:"+ data +"status:"+status+"er:"+er);
			}
		});
	}
    function render() {
//        var respectiveUrl = "/Cerberus/TransManager";
//        var content = generateContent();
//        var data = initialDataRequest(respectiveUrl,content);
        var data = getData();
        var dataObject = jsonToObject(data);
        console.log(dataObject);
        var tpl = "{{#main}}<tr><td>{{id}}</td><td>{{orderId}}</td><td>{{startTime}}</td></tr>{{/main}}";
        var html = Mustache.to_html(tpl, dataObject);
        var table = $("table");
        table.append(html);

    }
    function initialDataRequest(respectiveUrl,content){
        $.ajax({
            url: respectiveUrl,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(content),
            mimeType:'application/json',
            success: function(data){
                alert(data);
                console.log(data);
                return data;
            },
            error: function(data, status, er){
                console.log("error");
                alert("error:"+data+"status:"+status+"er:"+er);
            }

        });

    }
    function generateInitRequestContent(){
        var content = new Object();
        content.condition = new Object();
        content.condition.operateType = "operateQuery";
        content.condition.offset = 0;
        content.condition.lengths = 15;
        content.row = new Array();
        var contentData = new Object();
        contentData.main = {};
        content.row[0] = contentData;
        return content;
    }
    function jsonToObject(data){
        var thisData = JSON.stringify(data);
        var a = eval('('+ thisData +')');
        var dataObject = {};
        dataObject.main = [];
        dataObject.condition = a.condition;
        for( var i = 0; i < a.row.length; i++ ){
            dataObject.main[i] = a.row[i].main;
        }
        return dataObject;
    }
    function getData() {
        var data = {
            "condition":{
                "status":"ok"
            },
            "row":[
                {
                    main:{
                        "id":"123",
                        "orderId":"order123",
                        "startTime":"2013-9-15"
                    },
                    sub:{}
                }
            ]
        };
        return data;
    }
    render();

});