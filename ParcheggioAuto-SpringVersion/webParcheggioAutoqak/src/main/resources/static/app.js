var stompClient = null;
//alert("app.js")

function setConnected(connected) {
console.log(" %%% app setConnected:" + connected );
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/it-unibo-iss');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        //SUBSCRIBE to STOMP topic updated by the server
        stompClient.subscribe('/topic/infodisplay', function (msg) {
             var jsonMsg = JSON.parse(msg.body).content;
             updatePage(jsonMsg)
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendUpdateRequest(){
	console.log(" sendUpdateRequest "  );
    stompClient.send("/app/update", {}, JSON.stringify({'name': 'update' }));
}

function updatePage(message){
    var msg =message.split("%");
    if(message.includes("fan")){
        if(msg[1]=="true"){
            document.getElementById("fan").value = "ON";
        } else{
            document.getElementById("fan").value = "OFF";
        }

    }
    if(message.includes("temp")){
            document.getElementById("temp").value = msg[1];
    }
    if(message.includes("stato")){
            document.getElementById("stato").value = msg[1];
            if(msg[1]=="STOP"){
                document.getElementById("stop").value = "start"
                document.getElementById("stop").innerHTML = "Start Robot"
            }
    }
    if(message.includes("alarm")){
        if(msg[1]=="true"){
            document.getElementById("outdoor").style = "background-color:#ff0000; width: 5em;";
        } else{
            document.getElementById("outdoor").style = "background-color:#00ff00; width: 5em;";
        }
    }
    if(message.includes("park")){
        console.log(msg[0])
        console.log(msg[1])
        console.log(msg[2])
        console.log(msg[3])
        var colore = "";
        if(msg[3]=="false"){
            colore = "background-color:#ff0000";
        } else{
            colore = "background-color:#00ff00";
        }
        if(msg[1]=="0" && msg[2]=="0"){
            document.getElementById("p1").style = colore;
        }
        if(msg[1]=="0" && msg[2]=="1"){
            document.getElementById("p2").style = colore;
        }
        if(msg[1]=="1" && msg[2]=="0"){
            document.getElementById("p3").style = colore;
        }
        if(msg[1]=="1" && msg[2]=="1"){
            document.getElementById("p4").style = colore;
        }
        if(msg[1]=="2" && msg[2]=="0"){
            document.getElementById("p5").style = colore;
        }
        if(msg[1]=="2" && msg[2]=="1"){
            document.getElementById("p6").style = colore;
        }
    }
}

$(function () {
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendRequestData(); });
});


