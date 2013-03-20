LiveWidgets.addWidget({
	name: 'LiveSpace',
	model: {
		width: 0,
		position: 0,
		percent: 0,
		height: 0
	},
	constructor: function () {
		window.LS = this;
		this.model.height = this.element.parentNode.clientHeight;
		console.log(this);
	},
	reinit: function () {
		// this.controller.bindMousedownToSliderBar();
	},
	template: '<section data-widget="canvas" data-group="livespace" data-height="<?=height ?>"></section>',
	controller: {
		
	}
});




LiveWidgets.addWidget({
	name: 'drawer-handle',
	model: {
		lastOffsetX: 0,
		mousedown: false,
		lasteventTime: 0,
		distanceTraveled: 0
	},
	constructor: function () {
		
		var callback = function (event) {

					if (this.model.mousedown == true) {		
						if (this.lasteventTime + 100 > new Date().getTime()) {
							this.model.mousedown = false;
							return;
						}
				
						this.controller.handleDragStart(arguments[0]);
				
					}
				}.bind(this);
		
		$(this.element).bind("mousedown", function () {
			this.model.mousedown = true;
			this.lasteventTime = new Date().getTime();
				this.model.lastOffsetX = event.pageX;
				
				$("#Foundation").bind("mousemove", this.controller.callback);
		}.bind(this));


		
		$("#Foundation").bind("mouseup", function () {
			if (!this.model.mousedown) {
				return;
			}
			this.model.mousedown = false;
			
			$("#Foundation").unbind(this.controller.callback);
		}.bind(this));
		
		// this.element.addEventListener('drag', this.handleDrag.bind(this), false);
		// this.element.addEventListener('click', this.handleDrag.bind(this), false);
	},
	reinit: function () {

	},
	controller: {
		callback: function (event) {

					if (this.model.mousedown == true) {		
						if (this.lasteventTime + 100 > new Date().getTime()) {
							this.model.mousedown = false;
							return;
						}
				
						this.controller.handleDragStart(arguments[0]);
				
					}
				},
		handleDragStart: function (event) {
			
			if (event.pageX < 150) {
				$("#Activities").css("left", "150px");		
				$("#Tools").width(150);
				this.sendMessage("convert toolbar to icons");
				return;
			}
			// console.log(event.offsetX);
			if (this.model.lastOffsetX == 0) {
				this.model.lastOffsetX = event.pageX;
			}

			var offset = 0;
				offset = (this.model.lastOffsetX - event.pageX);			// console.log(event.pageX);
				
				// if (Math.abs(offset) > 10) {
				// 	return;
				// }
			// console.log(event);
			
			// console.log($("#Tools").width());
			
			this.model.distanceTraveled = offset;
			// console.log(offset);

			$("#Activities").css("left", $("#Tools").width());		
			$("#Tools").width($("#Tools").width() - offset);

			this.model.lastOffsetX = event.pageX;
			
			if ($("#Tools").width() < 150) {
				$("#Activities").css("left", "150px");		
				$("#Tools").width(150);
				this.sendMessage("convert toolbar to icons");
				return;
			}


		},
		handleMessage: function (channel, message) {
			// console.log(arguments);
			
		}
	}
});





LiveWidgets.addWidget({
	name: 'star-rating',
	model: {
		min: 0,
		max: 0,
		value: 0
	},
	constructor: function () {
        window.star = this;


	},
	reinit: function () {
	    $(this.element).html("");
        var span = this.model.max - this.model.min;
        var stars = span * this.model.value;

        for (var i = 0; i < stars; i += 1) {
            $(this.element).html($(this.element).html() + "*");
        }
	},
	controller: {
        handleMessage: function (message, channel) {
            if (channel = "set-rating") {
                this.model.value = message;
                this.reinit();
            }
        }
	}
});