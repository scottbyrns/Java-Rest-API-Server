LiveWidgets.addWidget({
	name: 'call-to-action-controls',
	model: {
		
	},
	constructor: function () {

	},
	reinit: function () {

	},
	controller: {
		setControls: function (controls) {
						$(this.element).html("");
			for (var i = 0; i < controls.length; i += 1) {
				var button = $("<button />");

				try {
					button.addClass(controls[i].type)
					.html(controls[i].label).click(function (action, group) {
						this.sendMessage(action);
						if (group !== undefined) {
						    LiveWidgets.sendMessage(group, {channel:action, message:{}});
						}
					}.bind(this, controls[i].action, controls[i].outboundGroup));
					
					$(this.element).append(button);
				}
				catch (e) {
					console.error(e);
				}
					
				// $(this.element).add()
			}
			

		},
		handleMessage: function (channel, message) {
			console.log(arguments);
			if (channel == "set-controls") {
				this.controller.setControls(message);
			}
			
		}
	}
});


LiveWidgets.addWidget({
	name: 'call-to-action-configuration',
	model: {
		
	},
	constructor: function () {

	},
	reinit: function () {

	},
	controller: {
		handleMessage: function (channel, message) {
			if (channel == "share your configuration" && message == this.model.for) {
				try {
					var controls = [];
					$(this.element).find("figure").each(function (index, element) {
						var control = {
							action: element.getAttribute("data-action"),
							type: element.getAttribute("data-type"),
							label: element.getAttribute("data-label"),
                            outboundGroup: element.getAttribute("data-outbound-group")
						};
			
						controls.push(control);
			
					});
				}
				catch (e) {
					console.error(e);
				}
				
				console.log(controls);

		this.sendMessage("set-controls", controls);				
			}
		}
	}
});








LiveWidgets.addWidget({
	name: 'activity-controller',
	model: {
		
	},
	constructor: function () {
		setTimeout(function () {
						LiveWidgets.sendMessage("call-to-action", {channel:"my-records", message:"share your configuration"});
		}, 0);
	},
	reinit: function () {

	},
	controller: {
		handleMessage: function (channel, message) {
			if (channel.indexOf("go-to-") > -1) {
							console.log(arguments);
				this.controller.showSpace(channel.replace("go-to-", ""));
			}
		},
		
		showSpace: function (name) {
			$(".activity-view").hide();
			$("#" + name).show();
			LiveWidgets.sendMessage("call-to-action", {channel:name, message:"share your configuration"});
		}
	}
});










LiveWidgets.addWidget({
	name: 'activity-title',
	model: {
		
	},
	constructor: function () {
		setTimeout(function () {
			LiveWidgets.sendMessage("activities", {channel:"go-to-my-records", message:"go-to-my-records"});
		}, 0);
	},
	reinit: function () {

	},
	controller: {
		allWordsToLeadingUpper: function( str ) {
			return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
		},
		
		handleMessage: function (channel, message) {
			if (channel.indexOf("go-to-") > -1) {
							console.log(arguments);
				this.controller.showSpace(channel.replace("go-to-", ""));
			}
		},
		
		showSpace: function (name) {
			name = name.replace("-", " ");
			name = name.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
			$(this.element).html(name);
		}
	}
});








