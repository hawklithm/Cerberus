$(function(){
    var count = 0;
//    var status = 0;
    var defaultLengthPerPage=15;
    var originalTable=$("table").html();
    /***
     * 
     * 修改URL
     * 
     ***/
    var respectiveUrl = "/Cerberus/OrderManager";
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
        var info = form.find("input#info").val();
        content.condition = {
            "operateType":"operateQuery",
            "offset":count,
            "lengths":14
        };
        var alarmInfo = {
            "info":info
        };
        content.rows = [];
        content.rows[0] = alarmInfo;
        return content;

    }
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
    function initPage() {
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl,content);
    }
    function render(data){
        var dataObject = jsonToObject(data);
        console.log("render:" + dataObject);
        /***
         * 
         * 修改变量名 
         * 
         ***/
        var tpl =
            "{{#main}}\
                <tr>\
                    <td>{{id}}</td>\
                    <td>{{content}}</td>\
                    <td>{{time}}</td>\
                </tr>\
            {{/main}}";
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
    initPage();
});