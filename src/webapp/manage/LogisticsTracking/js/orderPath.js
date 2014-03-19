$(function(){
    var count = 0;
//    var status = 0;
    var defaultLengthPerPage=0;
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

    $("button").on("click",function() {
        var content = generateRequest(count);
        ajaxSendRequest(respectiveUrl,content);
    });

    function getFormValue (count){
        var form = $("form");
        var content = {};
        var OrderNum = form.find("input#transportOrderNum").val();
        var hospitalName = form.find("input#orderNum").val();
        var vehicleNum = form.find("input#vehicleNum").val();
        content.condition = {
            "operateType":"operateQuery",
            "offset":count,
            "lengths":0
        };
        var orderInformation = {
            "orderId": OrderNum,
            "hospitalName": hospitalName,
            "vehicleId": vehicleNum
        };
        content.rows = [];
        content.rows[0] = orderInformation;
        return content;

    }
    function generateRequest(count){
        var requestTransport=new Object();
        requestTransport.main={};
        return generateRequestMessage(count,defaultLengthPerPage,"operateQuery",requestTransport);
    }
    function generateRequestMessage (offset,length,operateType,transportCondition) {
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
                console.log("XMLstatus: " + XMLHttpRequest.status+ " XMLreadyState: " + XMLHttpRequest.readyState + " textStatus: " + textStatus);
                alert(" XMLstatus: " + XMLHttpRequest.status+ " XMLreadyState: " + XMLHttpRequest.readyState + " textStatus: " + textStatus);
            }
        });
    }
    function initPage() {
        // var content = generateRequest(count);
        // ajaxSendRequest(respectiveUrl,content)
         var datas = getData().rows;
         showPath(datas);

    }
    function render(data){
        var dataObject = jsonToObject(data);
        console.log("render:" + dataObject);
        var datas = dataObject.rows;
        showPath(datas);
    }
    function jsonToObject(data){
        return JSON.parse(data);
    }
    function getData() {
        var data = {
            "condition":{
                "status":"ok"
            },
            "rows":[
                {
                    "main":{
                        "id":"123",
                        "orderId":"123456",
                        "startTime":"2013-2-30",
                        "finishTime":"2014-2-30",
                        "startAddress":"北京",
                        "endAddress":"电子科大清水河站",
                        "vehicleId":"231",
                        "currentAddress":"林荫中街站",
                        "orderStatus":"准备发往电子科大清水河站"
                    },
                    "subs":[
                        {
                            "id":"1234",
                            "parentId":"123456",
                            "subOrderId":"1234567",
                            "vehicleId":"231",
                            "gps":[
                                {
                                    "vehicleId":"231",
                                    "longitude":"116.603443",
                                    "latitude":"40.088108",
                                    "stationName":"北京机场总站",
                                    "time":"2013-4-1"
                                },
                                {
                                    "vehicleId":"231",
                                    "longitude":"108.766653",
                                    "latitude":"34.445703",
                                    "stationName":"西安机场总站",
                                    "time":"2013-4-2"
                                },
                                {
                                    "vehicleId":"231",
                                    "longitude":"103.965376",
                                    "latitude":"30.580799",
                                    "stationName":"成都机场总站",
                                    "time":"2013-4-3"
                                },
                                {
                                    "vehicleId":"231",
                                    "longitude":"104.106393",
                                    "latitude":"30.681601",
                                    "stationName":"电子科大沙河站",
                                    "time":"2013-4-4"
                                },
                                {
                                    "vehicleId":"231",
                                    "longitude":"104.081829",
                                    "latitude":"30.642242",
                                    "stationName":"林荫中街站",
                                    "time":"2013-4-5"
                                }
                            ]
                        }
                    ]
                }
            ]
        }
        return data;
    }
    function showPath(dataObject) {
        var map = new BMap.Map("container");
        map.enableScrollWheelZoom();
        map.enableContinuousZoom();
        map.centerAndZoom("成都",12);
        map.addControl(new BMap.NavigationControl()); 
        var driving = new BMap.DrivingRoute(map);
        for(i in dataObject) {
            var main = dataObject[i].main;
            var curAddr = main.currentAddress;
            var orderStatus = main.orderStatus;
            var subs = dataObject[i].subs;
            for(j in subs) {
                var gps = subs[j].gps;
                var point = [];
                var time = [];
                var stationName = [];
                var last = -1;
                var bigPoint = [];
                var bigNum = 0;
                var centerPoint = [];
                for(k in gps) {
                    var now = gps[k];
                    var longitude = parseFloat(now.longitude);
                    var latitude = parseFloat(now.latitude);
                    point[k] = new BMap.Point(longitude,latitude);
                    time[k] = now.time;
                    stationName[k] = now.stationName;
                    var end = k==(gps.length-1);
                    setMark(point[k],stationName[k],time[k],map,end);
                    if(last == -1) {
                        last = 0;
                        centerPoint[0] = point[k];
                        if(stationName[k].indexOf('总')!=-1) {
                            bigPoint[bigNum] = point[k];
                            bigNum++;
                        }
                        continue;
                    }
                    if(stationName[k].indexOf('总')==-1) {
                        driving.search(point[last],point[k]);
                        if(bigNum > 1) {
                            drawCurve(bigPoint,map);
                        }
                        bigNum = 0;
                    }
                    else {
                        if(stationName[last].indexOf('总')==-1) {
                            driving.search(point[last],point[k]);
                        }
                        bigPoint[bigNum] = point[k];
                        bigNum++;
                    }
                    last = k;
                    centerPoint[0] = centerPoint[1];
                    centerPoint[1] = point[k];
                }
                driving.setSearchCompleteCallback(function() {
                    var pts = driving.getResults().getPlan(0).getRoute(0).getPath();
                    var polyline = new BMap.Polyline(pts);
                    map.addOverlay(polyline);
                });
                setTimeout(function() {
                    map.setViewport(centerPoint);
                },1000);
            }
        }

    }

    function setMark(point, name, time,map,end) {
        var mark = new BMap.Marker(point);
        var lable = new BMap.Label(name,{position:point});
        var contentStr = "时间: "+time;
        var opts = {
            width: 300,
            height: 100,
            title: name
        };
        var infoWindow = new BMap.InfoWindow(contentStr,opts);
        mark.addEventListener("click",function() {
            this.openInfoWindow(infoWindow);
        });
        if(end) {
            mark.setAnimation(BMAP_ANIMATION_BOUNCE);
        }
        map.addOverlay(mark);
        map.addOverlay(lable);
    }

    function drawCurve(points,map) {
        var curve = new BMapLib.CurveLine(points,
            {strokeColor:"blue",strokeWeight:3, strokeOpacity:0.5});
        map.addOverlay(curve);
        curve.enableEditing();
    }

    function addPoint(pointInfo) {

    }

    initPage();
});