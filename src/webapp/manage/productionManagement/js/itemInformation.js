$(function(){
    var count = 0;
//    var status = 0;
    var defaultLengthPerPage=15;
    var originalTable=$("table").html();
    var respectiveUrl = "/Cerberus/FlowRecordManager";
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
    function getFormValue (count){
        var form = $("form");
        var content = {};
        var process = form.find("#selectProName option:selected").data("value");
        var itemNum = form.find("input#itemNum").val();
        var itemType = form.find("#selectItemType option:selected").data("value");
        var beginTime = form.find("input#beginTime").val();
        var endTime = form.find("input#endTime").val();
        content.condition = {
            "operateType":"operateQuery",
            "tableType":"item",
            "offset":count,
            "lengths":14
        };
        var itemInformation = {
            "startTime":beginTime,
            "endTime":endTime,
            "itemId":itemNum,
            "itemType":itemType,
            "procssName":process
        };
        content.rows = [];
        content.rows[0] = itemInformation;
        return content;

    }
    function generateRequest(count){
        var flowInfo=new Object();
        flowInfo.main={};
        return generateRequestMessage(count,defaultLengthPerPage,"operateQuery","item",flowInfo);
    }
    function generateRequestMessage (offset,length,operateType,tableType,itemCondition) {
        var content = new Object();
        content.condition = new Object();
        content.condition.operateType = operateType;
        content.condition.tableType = tableType;
        content.condition.offset = offset;
        content.condition.lengths = length;
        content.rows = [];
        content.rows[0] = itemCondition;
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
    function initPage() {
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl,content);
    }
    function render(data){
        var dataObject = jsonToObject(data);
        console.log("render:" + dataObject);
        var tpl =
            "{{#condition}}\
                <tr>\
                    <td>{{itemId}}</td>\
                    <td>{{type}}</td>\
                    <td>{{processName}}</td>\
                    <td>{{staffInfo}}</td>\
                    <td>{{time}}</td>\
                </tr>\
                {{/condition}}";
        var datas = dataObject.rows;
        var table = $("table");
        table.html(originalTable);
        for (var index in datas){
            var html = Mustache.to_html(tpl, datas[index]);
            table.append(html);
        }
        // table.html(originalTable);
        // for (var index in datas){
        //     var html = Mustache.to_html(tpl, datas[index]);
        //     table.append(html);
        // }
        // dialog(datas);
    }
    function jsonToObject(data){
        return JSON.parse(data);
    }

    function dialog(dataObject) {
        $("#dialog").dialog({
            autoOpen: false,
            show:{
                effect:"blind",
                duration: 500
            },
            hide:{
                effect:"explode",
                duration: 500
            }
        });
        $("#dialog").dialog({position : {my:"center top", at:"center top", of: window}});
        $(".detail").on("click", function(event){
            var dialog = $("#dialog");
            dialog.dialog("open");
            dialog.dialog({height:500});
            var rowNum = $(this).parent("tr").index();
            rowNum --;
            var main = dataObject[rowNum].main;
            var subs = dataObject[rowNum].subs;
            var reflects = dataObject[rowNum].reflects;
            var tpl = "<h2>主订单信息</h2><p>订单ID:{{orderId}}</p><p>用户ID:{{userId}}</p><p>开始时间:{{startDay}}</p><p>结束时间:{{endDay}}</p><p>下单时间:{{orderTime}}</p><p>订单状态{{orderStatus}}</p><p>期望到货时间:{{expectReceiveTime}}</p><p>优先级:{{level}}</p><p>备注:{{orderNote}}</p>";
            var html = Mustache.to_html(tpl, main);
            dialog.html(html);

            var tplSub = "<h2>子订单{{counter}}信息:</h2><p>父订单Id:{{parentId}}</p><p>包类型:{{packageType}}</p><p>包数量:{{packageAmount}}</p>";
            for (var index in subs){
                subs[index].counter=index+1;
                var htmlSub = Mustache.to_html(tplSub, subs[index]);
                dialog.append(htmlSub);
            }
            var tplReflect = "<h2>反件子订单{{counter}}信息:</h2><p>父订单Id:{{parentId}}</p><p>包类型:{{itemType}}</p><p>包数量:{{itemAmount}}</p>";
            for (var index in reflects){
                reflects[index].counter=index+1;
                var htmlReflect = Mustache.to_html(tplReflect, reflects[index]);
                dialog.append(htmlReflect);
            }
        });
//        return false;
        event.preventDefault();
    }
    initPage();
});