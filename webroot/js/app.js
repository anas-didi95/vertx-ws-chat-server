const App = {
  data() {
    return {
      username: ""
    }
  },
  created() {
    this.username = prompt("Please enter your username")
  },
}

Vue.createApp(App).mount("#app")

let eventBus;
let username;

function init() {
  registerHandler();
}

function registerHandler() {
  eventBus = new EventBus("http://localhost:5000/eventbus");
  eventBus.onopen = function () {
    eventBus.registerHandler('out', function (error, message) {
      const counter = message.body;
      document.getElementById('current_value').innerHTML = counter;
    });
  }
}

function increment() {
  console.log("increament");
  eventBus.send("in", { "body": "hello" }, "headers");
}
