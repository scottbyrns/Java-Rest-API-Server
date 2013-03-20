(function (className, namespace) {
	if (typeof namespace == 'string' && !window[namespace]) {
		window[namespace] = {};
	}
	else if (typeof namespace == 'string' && window[namespace] && typeof className == 'string' && window[namespace][className]) {
		return;
	}
	namespace = namespace || 'window';
	/**
	 * Message controller constructor.
	 * @returns {MessageController} instance
	 */
	var MessageController = function () {
		/**
		 * Object to store the message handlers.
		 * @property
		 */
		this.messagePool = {};
		/**
		 * Unique ID counter
		 * @property
		 */
		this.currentUID = 0;
	};
	/**
	 * Prototype of the MessageController class
	 * @prototype
	 */
	MessageController.prototype = {
		/**
		 * Add an event listener
		 * @param {String} group Listener group to add the callback to.
		 * @param {Function} callback callback to register to a listener group.
		 */
		addListener: function (group, callback) {
			this.currentUID += 1;
			var listener = {
				group: group,
				UID: this.currentUID,
				sendMessage: (function (that) {
					return function (message, channel) {
						return that.sendMessage(this.group, message, channel);
					};
				})(this),
				destroy: (function (that) {
					return function () {
						return that.removeListener(this);
					};
				})(this)
			};

			this.messagePool[group] = this.messagePool[group] || {};
			this.messagePool[group][listener.UID] = callback;
			return listener;
		},
		/**
		 * Remove a registered callback
		 * @param {Listener} listener Listener resource object
		 */
		removeListener: function (listener) {
			try {
				delete this.messagePool[listener.group][listener.UID];
			}
			catch (e) {}
		},
		/**
		 * Send a message to a listener group.
		 * @param {String} group Listener group reference by name
		 * @param {Any} message Message to be send to the specified listener group
		 * @param {String} channel Message channel to allow listener callback to filter messages
		 * targeted at specific listeners.
		 */
		sendMessage: function (group, message, channel) {
			var messageGroup = this.messagePool[group],
			exceptions = [];
			for (callback in messageGroup) {
				if (messageGroup.hasOwnProperty(callback)) {
					try {
						messageGroup[callback](message, channel);
					}
					catch (e) {
						exceptions.push({
							message: 'Callback failed for' + callback,
							exception: e
						});
					}
				}
			}
			return exceptions;
		}
	};

	window[namespace][className] = new MessageController();

})('MessageController', 'Foundation');


var when = function (statement) {
    return {
        if: function (channel) {
            console.log(statement);
            return {
                do: function (action) {
                    console.log(channel);
                    Foundation.MessageController.addListener(statement, function () {
                        console.log(action);
                        if (arguments[0] == channel) {
                            action.apply(null, arguments);
                        }
                    });
                }
            }
        },
        do: function (action) {
            Foundation.MessageController.addListener(statement, action);
        }
    }
}



var LiveSpace = function (name) {

    this.name = name;
    this.stages = [];

    when("Document is ready").do(this.start.bind(this));

//    when("LiveSpace Created").if(name).do(this.startLiveSpace.bind(this));
//    when("LiveSpace Started").if(name).do(this.startLiveSpace.bind(this));
//    when("LiveSpace Stopped").if(name).do(this.startLiveSpace.bind(this));
//    when("LiveSpace Paused").if(name).do(this.startLiveSpace.bind(this));



};

LiveSpace.prototype = {
    start: function () {
        Foundation.MessageController.sendMessage("LiveSpace Created", name);
    },
    addStage: function (stage) {
        this.stages.push(stage);
        Foundation.MessageController.sendMessage("Stage added to LiveSpace.");
    }
};

var Stage = function (name) {

};



//Controller.FarmController = {
//    "did start work day": function () {
//        Foundation.MessageController.sendMessage("did start work day");
//    }
//}

