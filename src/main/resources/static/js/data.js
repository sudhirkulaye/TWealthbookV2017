$(document).ready(function () {
		Morris.Donut.prototype.resizeHandler = function () {
            this.timeoutId = null;
            if (this.el && this.el.width() > 0 && this.el.height() > 0) {
                this.raphael.setSize(this.el.width(), this.el.height());
                return this.redraw();
            }
            else return null;
        };
        Morris.Donut.prototype.setData = function (data) {
            var row;
            this.data = data;
            this.values = (function () {
                var _i, _len, _ref, _results;
                _ref = this.data;
                _results = [];
                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    row = _ref[_i];
                    _results.push(parseFloat(row.value));
                }
                return _results;
            }).call(this);
            if (this.el && this.el.width() > 0 && this.el.height() > 0) {
                return this.redraw();
            }
            else return null;
        };

        Morris.Donut({
          element: 'donut-example1',
          data: [
            {label: "Download Sales", value: 12},
            {label: "In-Store Sales", value: 30},
            {label: "Mail-Order Sales", value: 20}
          ]
        });
});