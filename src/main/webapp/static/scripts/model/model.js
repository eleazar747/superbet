var superbetMetamodel = {
	bookmaker : {
		label : 'Bookmakers',
		resource : 'bookmakers',
		fields : [ {
			ref : 'id',
			label : 'Id',
			readonly : true
		}, {
			ref : 'code',
			label : 'Code',
			requiered : true
		}, {
			ref : 'url',
			label : 'Url',
			requiered : true
		}, {
			ref : 'urlSync',
			label : 'Url sync',
			requiered : true
		}, {
			ref : 'synchronizerService',
			label : 'Sync service',
			requiered : true
		}, {
			ref : 'encoding',
			label : 'Encoding'
		} ],
		init : function() {
			return {
				id : "",
				code : "",
				url : "",
				urlSync : "",
				synchronizerService : "",
				encoding : ""
			};
		}
	},
	sport : {
		label : 'Sports',
		resource : 'sports',
		fields : [ {
			ref : 'id',
			label : 'Id',
			readonly : true
		}, {
			ref : 'code',
			label : 'Code',
			requiered : true
		} ],
		init : function() {
			return {
				id : "",
				code : ""
			};
		}
	},
	bettype : {
		label : 'Bet types',
		resource : 'bettypes',
		fields : [ {
			ref : 'id',
			label : 'Id',
			readonly : true
		}, {
			ref : 'code',
			label : 'Code',
			requiered : true
		} ],
		init : function() {
			return {
				id : "",
				code : ""
			};
		}
	},
	datamapping : {
		label : 'Data mappings',
		resource : 'datamappings',
		fields : [ {
			ref : 'id',
			label : 'Id',
			readonly : true
		} ],
		init : function() {
			return {
				id : "",
				code : ""
			};
		}
	}
}