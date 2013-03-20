LiveWidgets.addWidget({
	name: 'application-overlay',
	controller: {
		handleMessage: function (message, channel) {
		    if (message == "user did login" || channel == "user did login") {
		        $(this.element).removeClass("overlay");
		    }
            if (channel == "hide the overlay") {
                $(this.element).css({
                    zIndex:"0 !important",
                    top:"auto !important",
                    height:"75px !important",
                    background: "#333 !important"
                });
            }
		}
	}
});


LiveWidgets.addWidget({
	name: 'account-login-form',
	model: {

	},
	constructor: function () {
        $("#login-button").click(this.controller.handleClick);
	},
	controller: {
	    handleClick: function () {
            this.sendMessage("the user would like to login", {
                username: $("#username-input", this.element).val(),
                password: $("#password-input", this.element).val()
            });
	    },
		handleMessage: function (message, channel) {
		    if (message == "user did login" || channel == "user did login") {
//		        alert("remove overlay from form");
		        $(this.element).removeClass("overlay");
                this.sendMessage({}, "hide the overlay");
		    }
//
//            if (channel == "hide the overlay") {
//                $(this.element).css({
//                    zIndex:"0 !important",
//                    top:"auto !important",
//                    height:"75px !important",
//                    background: "#333 !important"
//                });
//            }
		}
	}
});

