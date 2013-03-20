LiveWidgets.addWidget({
	name: 'user-account-controller',
	model: {
        username: "Username",
        password: "password"
	},
	controller: {
        handleMessage: function (message, channel) {
            if (channel == "the user would like to login") {

                if (message.username && message.password) {
                    this.model.username = message.username;
                    this.model.password = message.password;
                    this.controller.login();
                }
                else {
                    this.sendMessage("the user did not provide the enough information to login");
                }
            }
        },
        login: function () {
            $.get("user/login/" + this.model.username + "/" + this.model.password, function(data) {
                if (data.status == 200) {
                    console.log(data);
                    this.sendMessage({}, "user did login");
                }
                else {
                    this.sendMessage("user did fail to login");
                }
            }.bind(this));
        },
        logout: function () {
            $.get("user/logout", function(data) {
                this.sendMessage("user did logout");
            }.bind(this));
        }
    }
});
