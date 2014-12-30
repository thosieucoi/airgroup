
parentOffset = rroot.test(offsetParent[0].nodeName) ? { top: 0, left: 0 } : offsetParent.offset();
// Subtract element margins
// note: when an element has margin: auto the offsetLeft and marginLeft
// are the same in Safari causing offset.left to incorrectly be 0
offset.top -= parseFloat( jQuery.css(elem, "marginTop") ) || 0;
offset.left -= parseFloat( jQuery.css(elem, "marginLeft") ) || 0;
// Add offsetParent borders
parentOffset.top += parseFloat( jQuery.css(offsetParent[0], "borderTopWidth") ) || 0;
parentOffset.left += parseFloat( jQuery.css(offsetParent[0], "borderLeftWidth") ) || 0;
// Subtract the two offsets
return {
top: offset.top - parentOffset.top,
left: offset.left - parentOffset.left
};
},
offsetParent: function() {
return this.map(function() {
var offsetParent = this.offsetParent || document.body;
while ( offsetParent && (!rroot.test(offsetParent.nodeName) && jQuery.css(offsetParent, "position") === "static") ) {
offsetParent = offsetParent.offsetParent;
}
return offsetParent || document.body;
});
}
});
// Create scrollLeft and scrollTop methods
jQuery.each( {scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function( method, prop ) {
var top = /Y/.test( prop );
jQuery.fn[ method ] = function( val ) {
return jQuery.access( this, function( elem, method, val ) {
var win = getWindow( elem );
if ( val === undefined ) {
return win ? (prop in win) ? win[ prop ] :
win.document.documentElement[ method ] :
elem[ method ];
}
if ( win ) {
win.scrollTo(
!top ? val : jQuery( win ).scrollLeft(),
top ? val : jQuery( win ).scrollTop()
);
} else {
elem[ method ] = val;
}
}, method, val, arguments.length, null );
};
});
function getWindow( elem ) {
return jQuery.isWindow( elem ) ?
elem :
elem.nodeType === 9 ?
elem.defaultView || elem.parentWindow :
false;
}
// Create innerHeight, innerWidth, height, width, outerHeight and outerWidth methods
jQuery.each( { Height: "height", Width: "width" }, function( name, type ) {
jQuery.each( { padding: "inner" + name, content: type, "": "outer" + name }, function( defaultExtra, funcName ) {
// margin is only for outerHeight, outerWidth
jQuery.fn[ funcName ] = function( margin, value ) {
var chainable = arguments.length && ( defaultExtra || typeof margin !== "boolean" ),
extra = defaultExtra || ( margin === true || value === true ? "margin" : "border" );
return jQuery.access( this, function( elem, type, value ) {
var doc;
if ( jQuery.isWindow( elem ) ) {
// As of 5/8/2012 this will yield incorrect results for Mobile Safari, but there
// isn't a whole lot we can do. See pull request at this URL for discussion:
// https://github.com/jquery/jquery/pull/764
return elem.document.documentElement[ "client" + name ];
}
// Get document width or height
if ( elem.nodeType === 9 ) {
doc = elem.documentElement;
// Either scroll[Width/Height] or offset[Width/Height] or client[Width/Height], whichever is greatest
// unfortunately, this causes bug #3838 in IE6/8 only, but there is currently no good, small way to fix it.
return Math.max(
elem.body[ "scroll" + name ], doc[ "scroll" + name ],
elem.body[ "offset" + name ], doc[ "offset" + name ],
doc[ "client" + name ]
);
}
return value === undefined ?
// Get width or height on the element, requesting but not forcing parseFloat
jQuery.css( elem, type, value, extra ) :
// Set width or height on the element
jQuery.style( elem, type, value, extra );
}, type, chainable ? margin : undefined, chainable, null );
};
});
});
// Expose jQuery to the global object
window.jQuery = window.$ = jQuery;
// Expose jQuery as an AMD module, but only for AMD loaders that
// understand the issues with loading multiple versions of jQuery
// in a page that all might call define(). The loader will indicate
// they have special allowances for multiple jQuery versions by
// specifying define.amd.jQuery = true. Register as a named module,
// since jQuery can be concatenated with other files that may use define,
// but not use a proper concatenation script that understands anonymous
// AMD modules. A named AMD is safest and most robust way to register.
// Lowercase jquery is used because AMD module names are derived from
// file names, and jQuery is normally delivered in a lowercase file name.
// Do this after creating the global so that if an AMD module wants to call
// noConflict to hide this version of jQuery, it will work.
if ( typeof define === "function" && define.amd && define.amd.jQuery ) {
define( "jquery", [], function () { return jQuery; } );
}
})( window );
