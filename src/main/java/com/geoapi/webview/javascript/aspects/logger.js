LiveWidgets.addWidget({
	name: 'logger',
	controller: {
		handleMessage: function (message, channel) {
			console.log(message);
		}
	}
});