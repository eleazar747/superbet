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
				bookMaker : {
					code : ""
				},
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
				sport : {
					code : ""
				},
				code : "",
				date : "",
			};
		}
	},
	bet : {
		label : 'Bets',
		resource : 'bets',
		fields : [ {
			ref : 'id',
			label : 'Id',
		}, {
			ref : 'match.sport.code',
			label : 'Sport',
		}, {
			ref : 'match.code',
			label : 'Match',
		}, {
			ref : 'match.date',
			label : 'Match date',
		}, {
			ref : 'bookMaker.code',
			label : 'Bookmaker',
		}, {
			ref : 'betType.code',
			label : 'BetType',
		}, {
			ref : 'code',
			label : 'Code',
		}, {
			ref : 'odd',
			label : 'Odd',
		}, {
			ref : 'date',
			label : 'Date',
		} ]
	},
	surebet : {
		label : 'Surebet',
		resource : 'bets',
		fields : [ {
			ref : 'sport',
			label : 'Sport'
		}, {
			ref : 'betType',
			label : 'Bet type'
		}, {
			ref : 'match',
			label : 'Match'
		}, {
			ref : 'odds',
			label : 'Odds'
		}, {
			ref : 'date',
			label : 'Date'
		}, {
			ref : 'profit',
			label : 'Profit'
		}, {
			ref : 'alternatives',
			label : 'Alternatives'
		} ]
	}

}