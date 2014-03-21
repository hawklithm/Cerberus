$(function () {
    var respectiveUrl = "/Cerberus/TransManager";
    var count = 0;
    var lengthPerPage=15;
    var originalTable=$("table").html();
    console.log("table: "+originalTable);
    $("input#transOrderQuery").on("click",function(){
//        var selectedNum=$("select#selectTranspoetOrder").val();
//        console.log(selectedNum);
        var transOrderId=Number($("input#transportOrderNum").val());
        console.log(transOrderId);
        var orderId=Number($("input#orderNum").val());
        console.log(orderId);
        var vehicleId=Number($("input#vehicleNum").val());
        console.log(vehicleId);
        var beginTime=Number($("input#beginTime").val());
        console.log(beginTime);
        var endTime=Number($("input#endTime").val());
        console.log(endTime);
        var orderCondition=new Object();
        console.log(orderCondition);
        orderCondition.main={};
        orderCondition.main.id=transOrderId;
        orderCondition.main.vehicleId=vehicleId;
        orderCondition.main.orderId=orderId;
        orderCondition.main.startTime=beginTime;
        orderCondition.main.finishTime=endTime;
        var content=generateRequestMessage(count,lengthPerPage,"operateQuery",orderCondition);
        ajaxSendRequest(respectiveUrl, content);
    });
    $(".previous").on("click", function () {
        count++;
        console.log(count);
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl, content);
    });
    $(".next").on("click", function () {
        if (count > 0) {
            count--;
            console.log(count);
            var content = generateRequest(count);
            ajaxSendRequest(respectiveUrl, content);
        }
    });
    function generateRequest(count) {
        var requestOrder = new Object();
        requestOrder.main = {};
        return generateRequestMessage(count, lengthPerPage, "operateQuery", requestOrder);
    }

    function generateRequestMessage(offset, length, operateType, orderCondition) {
        var content = new Object();
        content.condition = new Object();
        content.condition.operateType = operateType;
        content.condition.offset = offset;
        content.condition.lengths = length;
        content.rows = [];
        content.rows[0] = orderCondition;
        return content;
    }

    function initPage() {
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl, content);

    }

    function render(data) {
        var dataObject = jsonToObject(data);
        console.log("render:" + dataObject);
        var tpl =
            "{{#main}}\
                <tr>\
                    <td>{{#id}}{{id}}{{/id}}</td>\
                    <td>{{#orderId}}{{orderId}}{{/orderId}}</td>\
                    <td>{{#vehicleId}}{{vehicleId}}{{/vehicleId}}</td>\
                    <td>{{#currentAddress}}{{currentAddress}}{{/currentAddress}}</td>\
                    <td>{{#startTime}}{{startTime}}{{/startTime}}</td>\
                    </tr>\
            {{/main}}";
        var datas = dataObject.rows;
        var table = $("table");
        table.html(originalTable);
        for (var index in datas) {
            var html = Mustache.to_html(tpl, datas[index]);
            table.append(html);
        }
    }

    function ajaxSendRequest(targetUrl, content) {
        $.ajax({
            url: targetUrl,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(content),
            mimeType: "application/json",
            success: function (data) {
                console.log("success" + data);
                render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("XMLstatus:" + XMLHttpRequest.status+ "XMLreadyState:" + XMLHttpRequest.readyState + "textStatus:" + textStatus);
                alert("XMLstatus:" + XMLHttpRequest.status+ "XMLreadyState:" + XMLHttpRequest.readyState + "textStatus:" + textStatus);
            }
        });
    }

    function jsonToObject(data) {
        return JSON.parse(data);
    }
    initPage();

});