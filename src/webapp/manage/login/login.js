$(function(){
      $(".btn-primary").on("click",function(){
        var content = new Object();
        content.username = $("#username").val();
        content.password = $("#password").val();
        console.log("haha");
        $.ajax({
          url:'',
          type:'POST',
          dataType:'json',
          data:JSON.stringify(content),
          mimeType:'application/json',
          success:function(){
            alert("success");
            console.log("success");
            // console.log(data);
          },
          error:function(data,status,er){
            console.log("error");
            alert("error:"+data+"status:"+status+"er:"+er);
          }
        });
      })
    });