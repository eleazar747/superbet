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
			ref : 'synchronizer',
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
				synchronizer : "",
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
		}, {
			ref : 'bookMaker.code',
			label : 'BookMaker',
			requiered : true
		}, {
			ref : 'refEntityType',
			label : 'Type',
			requiered : true
		}, {
			ref : 'modelCode',
			label : 'Model code',
			requiered : true
		}, {
			ref : 'bookMakerCode',
			label : 'BookMaker code',
			requiered : true
		} ],
		init : function() {
			return {
				id : "",
				bookMaker : "",
				bookMaker.code : "",
				refEntityType : "",
				modelCode : "",
				bookMakerCode : ""
			};
		}
	},
	match : {
		label : 'Matches',
		resource : 'matches',
		fields : [ {
			ref : 'id',
			label : 'Id',
			readonly : true
		}, {
			ref : 'sport.code',
			label : 'Sport',
			requiered : true
		}, {
			ref : 'code',
			label : 'Code',
			requiered : true
		}, {
			ref : 'date',
			label : 'Date',
			requiered : true
		} ],
		init : function() {
			return {
				id : "",
				sport : "",
				sport.code : "",
				code : "",
				date : "",
			};
		}
	}

}