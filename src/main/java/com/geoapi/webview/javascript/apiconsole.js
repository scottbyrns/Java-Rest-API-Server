/**
 *
 * Copyright (C) 2012 by Scott Byrns
 * http://github.com/scottbyrns
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 */
(function (className, namespace) {
	if (typeof namespace == 'string' && !window[namespace]) {
		window[namespace] = {};
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
	
})('MessageController', 'GEOAPI');


GEOAPI.MessageController.addListener("api-response", function (message, channel) {

    console.log(message);

});

$.getJSON("http://localhost:8081/SNAPSHOTS/scott/1.0.6-SNAPSHOT/cogo/meter-distance-between-locations-with-ids:19:20", function (data) {
    GEOAPI.MessageController.sendMessage("api-response", data, data.status);
});