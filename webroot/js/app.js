let eventBus;

function init() {
  registerHandler();
}

function registerHandler() {
  eventBus = new eventBus("http://localhost:8888/eventbus");
  eventBus.onopen = function () {
    eventBus.registerHandler('out', function (error, message) {
      const counter = message.body;
      document.getElementById('current_value').innerHTML = counter;
    });
  }
}

function increment() {
  eventBus.send('in')
}
