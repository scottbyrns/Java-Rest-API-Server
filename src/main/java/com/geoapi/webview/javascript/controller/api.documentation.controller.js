

LiveWidgets.addWidget({
	name: 'api-documentation-controller',
	model: {
        endpoints: []
	},
	constructor: function () {


        $.get(this.model.endpointpath, function(data) {
            this.model.endpoints = data.response;



            this.controller.fetchEndpoints.call(this);

        }.bind(this));



	},
	reinit: function () {

	},
	controller: {
	    drawEndpoints: function () {

            try {
                        for (var i = 0; i < this.model.endpoints.length; i += 1) {
                            if (undefined == this.model.endpointMappings[this.model.endpoints[i]]) {
                                console.warn("Endpoint couldn't be mapped.");
                                continue;
                            }

                            if (this.model.endpointMappings[this.model.endpoints[i]].mapped) {
                                console.warn("already mapped");
                                continue;
                            }
                            else {
                                this.model.endpointMappings[this.model.endpoints[i]].mapped = true;
                            }


                            $(this.element).append("<section class=\"api-service-header\"><h4>" + this.model.endpoints[i].replace("/", "") + " </h4></section>")


                            this.model.endpointMappings[this.model.endpoints[i]].response.status;
                            this.model.endpointMappings[this.model.endpoints[i]].response.methods;

                            for (var j = 0; j < this.model.endpointMappings[this.model.endpoints[i]].response.methods.length; j += 1) {
                                $(this.element).append("<section class=\"api-service-method\"><h5>"+this.model.endpointMappings[this.model.endpoints[i]].response.methods[j].signature + "<span> - "+this.model.endpointMappings[this.model.endpoints[i]].response.methods[j].status+"</span><h5><p>" + this.model.endpointMappings[this.model.endpoints[i]].response.methods[j].description + "</p></section>");
                            }
                        }
            }
            catch (e) {
                console.error(e);
            }
	    },
	    fetchEndpoints: function () {
	    	    this.model.endpointMappings = {};
                for (var index = 0; index < this.model.endpoints.length; index += 1) {
                    if (undefined == this.model.endpoints[index] || this.model.endpoints[index] == "/") {
                        continue;
                    }

                    $.get(this.model.endpoints[index].substr(1), function(index, data) {
                        this.model.endpointMappings[this.model.endpoints[index]] = data;
                        this.controller.drawEndpoints();
                    }.bind(this, index));
                }

	    },
		handleMessage: function (channel, message) {

		},

	 stripLeadingSlash: function(str) {
            if(str.substr(0, 1) == '/') {
                var os = str.substr(1);
                console.log(os);
                return os;
            }
            return str;
        }
	}
});

