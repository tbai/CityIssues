$(document).ready(function() {

    var sandbox = new bai.Sandbox("parseEPTCRadars");
    var month;
    var year;
    var addrMap = {}; // {addr:bai.address}
    var list = [];
    var issueList = [];
    
    $("#parseButton").click(function(){
        var text = $("#textField").val();
        text = text.trim();
        month = $("#monthField").val();
        year = $("#yearField").val();
        var lines = text.split("\n");

        for(var i in lines){
            var cols = lines[i].split(":");
            var day = cols[0].substring(0, lines[i].indexOf(",")).trim();
            var col;
            if(cols[1].trim().indexOf("Corredor") >=0){
                col = cols[3].trim();
            } else {
                col = cols[2].trim();
            }
            if(col[col.length-1] == "."){
                col = col.substring(0, col.length-1);
            }
            var avs = col.split(";");
            for(var j in avs){
                avs[j] = avs[j].trim();
                addrMap[avs[j]]="";
            }

            list.push({
                day:day,
                avList:avs
            });
        }
        
        for(i in addrMap){
            getAddress(i);
            sleep(500); // to avoid overquery limit from google api
        }

        
        console.log("addrMap");
        console.log(addrMap);
        console.log("list");
        console.log(list);
        
        $("#submitButton").removeAttr("disabled");
    });

    $("#submitButton").click(function(){
        if( !list.length ){
            alert("O parser falhou");
            return;
        }
        issueList = [];
        for (var i in list){
            var avList = list[i].avList;
            var date = new Date(year, parseInt(month)-1, list[i].day);
            if(!date){
                alert("invalid date");
            }
            var desc = "Radar Móvel da EPTC<br/>Lista dos radares para hoje - "+list[i].day+"/"+month+"/"+year+"<br/> - Av. "+avList.join(", Av. ");
            for(var j in avList){
                var addressObj = addrMap[avList[j]];
                var issue = {
                    date: ""+date.getTime(),
                    desc:desc,
                    address:{
                        formatted:addressObj.formatted,
                        lat:addressObj.lat,
                        lng:addressObj.lng,
                        city:addressObj.city,
                        state:addressObj.state,
                        country:addressObj.country
                    }
                };
                issueList.push(issue);
            }
        }
        console.log("issueList:");
        console.log(issueList);
        var data = JSON.stringify(issueList);
        
        if(confirm("Enviar agora?") ){
            $.ajax({
                type: "POST",
                url: "/admin/saveEPTCRadars",
                contentType:"text/json",
                success: function(json){
                    if(json.success){
                        alert("sucesso!")
                    } else {
                        alert("não deu!")
                        console.log(json);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("não deu! " + textStatus);
                },
                dataType: "json",
                data:data
            });
        }
    });
    
    function sleep(milliSeconds){
        var startTime = new Date().getTime();
        while (new Date().getTime() < startTime + milliSeconds);
    }

    function getAddress(key){
        var addr = "Av. " + key+" - Porto Alegre - RS, Brasil"
        bai.MapUtils.geocode(addr, function(result, status){
            if(result && result.length){
                var address = bai.MapUtils.getAddressFromGeocodeResult(result[0]);
                addrMap[key] = address;
            } else {
                alert(status + " não achou " + addr);
            }
        }, this);
    }
});
