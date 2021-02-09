let eventBus;
let username;

function init() {
  registerHandler();
}

function registerHandler() {
  console.log("registerHandler");
  eventBus = new EventBus("http://localhost:5000/eventbus");
  eventBus.onopen = function () {
    eventBus.registerHandler("ws-refresh-chat", function (error, response) {
      console.log("[registerHandler][ws-refreh-chat] response", response);
      console.log("[registerHandler][ws-refreh-chat] error", error);

      const elChat = document.getElementById("chat");
      const txtMessage = document.createTextNode(`${response.body.username}: ${response.body.message}\n`);
      elChat.appendChild(txtMessage);
    });
  }
}

const App = {
  data() {
    return {
      username: "",
      message: ""
    }
  },
  created() {
    this.username = prompt("Please enter your username")
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
