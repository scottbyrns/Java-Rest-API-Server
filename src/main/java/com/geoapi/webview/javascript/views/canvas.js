LiveWidgets.addWidget({
	name: 'canvas',
	model: {
		height: 0,
		width: 0,
		
	},
	constructor: function () {
		this.model.width = this.element.clientWidth;
		// this.model.height = this.element.clientHeight;
	},
	reinit: function () {
		// this.controller.bindMousedownToSliderBar();
	},
	template: '<canvas height="<?=height ?>" width="<?=width ?>"></canvas>',
	controller: {
		handleMessage: function (message, channel) {
			console.log(arguments);
			
			
		}
	}
});