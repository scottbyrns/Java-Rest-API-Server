LiveWidgets.addWidget({
	name: 'server-health-controller',
	model: {
        healthPath: "/health/ping"
	},
	constructor: function () {
        this.sendMessage("check-connection");
	},
	reinit: function () {
		// this.controller.bindMousedownToSliderBar();
	},
	controller: {
		handleMessage: function (message, channel) {
		    console.log(channel);
            if (channel == "check-connection") {

                    $.get(this.model.healthPath, function(data) {
                        console.log(data);
                        if (data.status == 200) {
                            this.sendMessage("the server connection is alive");
                        }
                        else {
                            this.sendMessage("the server connection is broken");
                        }
                        $(this.element).append(jsonHtmlTable);
                    }.bind(this));
            }


		}
	}
});