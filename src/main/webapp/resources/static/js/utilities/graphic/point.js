
app.factory('Point', [
    function() {
        'use strict';

        class Point {
            constructor(x, y) {
                this._x = x;
                this._y = y;
            }

            get x() {
                return this._x;
            }

            get y() {
                return this._y;
            }

            add(point) {
                let newX = this._x + point.x;
                let newY = this._y + point.y;
                return new Point(newX, newY);
            }

            subtract(point) {
                let newX = this._x - point.x;
                let newY = this._y - point.y;
                return new Point(newX, newY);
            }

            multiply(multiplier) {
                let newX = this._x * multiplier;
                let newY = this._y * multiplier;
                return new Point(newX, newY);
            }

            getArrayCoords() {
                return [this.x, this.y];
            }
        }

        return Point;
    }
]);