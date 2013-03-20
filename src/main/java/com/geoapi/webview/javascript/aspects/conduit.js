LiveWidgets.addWidget({
	name: 'conduit',
	constructor: function () {
        window.that = this;
	},
	controller: {
		handleMessage: function (channel, message) {
//		console.log(argument);
		    if (message[this.element.getAttribute("data-widget-id")] == true) {
//		        console.warn("aborted");
		        return;
		    }
//		    console.warn(this.model.outlet);
		    message[this.element.getAttribute("data-widget-id")] = true;
            LiveWidgets.sendMessage(this.model.outlet, {channel:channel, message:message});
		}
	}
});
//
//LiveWidgets.addWidget({
//	name: 'receiver',
//	constructor: function () {
//        Foundation.MessageController.addHandler(this.model.outlet, this.controller.handleMessage);
//	},
//	controller: {
//		handleMessage: function (message, channel) {
//
//            Foundation.MessageController.sendMessage(this.model.outlet, channel, message);
//			console.log(message);
//		}
//	}
//});
