let eventBus;
let username;

function init() {
  registerHandler();
}

function registerHandler() {
  console.log("registerHandler");
  eventBus = new EventBus("http://localhost:5000/eventbus");
  eventBus.onopen = function () {
    eventBus.registerHandler('out', function (error, message) {
      const counter = message.body;
      document.getElementById('current_value').innerHTML = counter;
    });
    eventBus.registerHandler("ws-refresh-chat", function (error, message) {
      console.log("[registerHandler][ws-refreh-chat] message", message);
      console.log("[registerHandler][ws-refreh-chat] error", error);
    });
  }
}

function increment() {
  console.log("increament");
  eventBus.send("in", { "body": "hello" }, "headers");
}

const App = {
  data() {
    return {
      username: "",
      message: ""
    }
  },
  created() {
    this.username = prompt("Please enter your jjusername")
  },
  methods: {
    sendMessage() {
      console.log("message", this.message)
      eventBus.send("ws-send-message", {
        "username": this.username,
        "message": this.message
      })
    }
  }
}
Vue.createApp(App).mount("#app")

