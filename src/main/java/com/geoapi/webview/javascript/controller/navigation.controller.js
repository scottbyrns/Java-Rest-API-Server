LiveWidgets.addWidget({
	name: 'navigation-controller',
	controller: {
		setActiveButton: function (button) {
			$("li", this.element).removeClass("active");
			$("li[data-message="+button+"]", this.element).addClass("active");
		},
		handleMessage: function (message, channel) {
			if(message.indexOf("go-to") == 0) {
				this.controller.setActiveButton(message);
			}
			if (message == "convert toolbar to icons") {
				$(this.element).find("li").each(function () {
					$(this).addClass("icon");
				});
			}
		}
	}
});