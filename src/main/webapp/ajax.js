function ajax_get(url, callback) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            console.log('responseText:' + xmlhttp.responseText);
            try {
                var data = JSON.parse(xmlhttp.responseText);
            } catch(err) {
                console.log(err.message + " in " + xmlhttp.responseText);
                return;
            }
            callback(data);
        }
    };

    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

setInterval(
  () => {
    ajax_get('/actuator/metrics/process.uptime', function(data) {
        document.getElementById("uptime").innerHTML = data.measurements[0].value;
    });
    ajax_get('/actuator/metrics/jvm.memory.used', function(data) {
        document.getElementById("memory").innerHTML = data.measurements[0].value;
    });
  },
  1000
);
ajax_get('/actuator/metrics/system.cpu.count', function(data) {
    document.getElementById("cpu").innerHTML = data.measurements[0].value;
});