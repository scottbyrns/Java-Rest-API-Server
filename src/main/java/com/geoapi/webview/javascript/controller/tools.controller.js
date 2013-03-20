var ToolsController = function () {
	
	this.container = $("#Tools");
	this.lastOffsetX = 0;
	$("#tool-drawer-handler").bind("click", this.handleDragStart.bind(this));
	$("#tool-drawer-handler")[0].addEventListener('dragstart', this.handleDragStart.bind(this), false);
	
};

ToolsController.prototype = {
	resize: function () {
		
	},
	
	handleDragStart: function (event) {
		console.log(arguments);
		
		$("#Tools").width($("#Tools").width() - (this.lastOffsetX - event.offsetX));
		$("#Activities").css("left", (($("#Activities").css("left").replace("px", "") * 1) - (this.lastOffsetX - event.offsetX)) + "px");
		this.lastOffsetX = event.offsetX;
	}
	
	renderView: function () {
		this.container.html("");
	}
	
};

// new ToolsController();

// 
// 
// LiveWidgets.addWidget({
// 	name: 'drawer-handle',
// 	model: {
// 		lastOffsetX: 0
// 	},
// 	constructor: function () {
// 		$("#tool-drawer-handler")[0].addEventListener('drag', this.handleDrag.bind(this), false);
// 	},
// 	reinit: function () {
// 
// 	},
// 	controller: {
// 		handleDragStart: function (event) {
// 			console.log(arguments);
// 		
// 			$("#Tools").width($("#Tools").width() - (this.model.lastOffsetX - event.offsetX));
// 			$("#Activities").css("left", (($("#Activities").css("left").replace("px", "") * 1) - (this.model.lastOffsetX - event.offsetX)) + "px");
// 			this.model.lastOffsetX = event.offsetX;
// 		},
// 		handleMessage: function (channel, message) {
// 			console.log(arguments);
// 			
// 		}
// 	}
// });
