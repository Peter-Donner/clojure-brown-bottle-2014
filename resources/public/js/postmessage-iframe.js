(function() {
  var counter = 0;
  setInterval(function() {
    window.parent.postMessage(JSON.stringify({data:"counter from iframe: " + ++counter}), "*");
  }, 3000);
  window.addEventListener("message",function(event) {
    console.log("message received:  " + event.data,event);
    document.getElementById("logger").innerHTML = event.data;
  },false);
})();
