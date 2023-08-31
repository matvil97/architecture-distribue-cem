'use strict';

const rest = require('rest');
const mime = require('rest/interceptor/mime');
const errorCode = require('rest/interceptor/errorCode');

// conf for REST/json :
// see doc https://www.npmjs.com/package/rest
module.exports = rest
		.wrap(mime)
		.wrap(errorCode);