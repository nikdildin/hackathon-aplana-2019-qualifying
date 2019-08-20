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
function get_memory() {
    ajax_get('/actuator/metrics/jvm.memory.used', function(data) {
        memory_last = memory;
        memory = data.measurements[0].value;
        if (0 == memory_last) {
            memory_last = memory;
        }
        accum = 0;
    });
}
String.prototype.toHHMMSS = function () {
    var seconds = parseInt(this, 10);
    var hours = Math.floor(seconds / 3600);
    var minutes = Math.floor((seconds - (hours * 3600)) / 60);
    seconds = seconds - (hours * 3600) - (minutes * 60);

    if (hours < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    var time = hours+':'+minutes+':'+seconds;
    return time;
}

var show_interval = 1000;
var request_interval = 10000;

var uptime;
var memory=0;
var memory_last=0;
get_memory();

setInterval(
  () => {
    accum += show_interval;
    uptime += show_interval/1000;
    var showtime = Math.round(uptime)+"";
    document.getElementById("uptime").innerHTML = showtime.toHHMMSS();
    document.getElementById("memory").innerHTML = Math.round((memory_last+(memory-memory_last)*accum/request_interval)/1024/1024) + " Mb";
  },
  show_interval
);
setInterval(
  () => {
    get_memory();
  },
  request_interval
);
ajax_get('/actuator/metrics/process.uptime', function(data) {
    uptime = parseInt(data.measurements[0].value);
});
ajax_get('/actuator/metrics/system.cpu.count', function(data) {
    document.getElementById("cpu").innerHTML = data.measurements[0].value;
});