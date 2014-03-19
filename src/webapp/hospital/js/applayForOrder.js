/**
 * Created by Administrator on 14-1-8.
 */
$(function(){
    var count=1;
    var number="";
console.log("haha");
    $("button#addOrder").on("click", function(){
        count++;
        number="\""+"select"+count+"\"";
       console.log("hah");

        if (count<10){

        $(".sub").append(
            "<li> \
                    <label ><h4>手术包类型</h4></label> \
                    <select id="+number+"class=\"form-control\"> \
                        <option>初级手术包</option> \
                        <option>中级手术包</option> \
                        <option>高级手术包</option> \
                    </select> \
                    <input name=\"pacnum\" type=\"number\" min=\"0\"class=\"form-control\" placeholder=\"数量\"> \
                </li>"
        );
        }
    });
});