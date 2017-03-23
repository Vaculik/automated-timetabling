
app.factory('Vector', [
    'Point',
    function(Point) {
        'use strict';

        class Vector {
            constructor(point1, point2) {
                if (!point2) {
                    point2 = new Point(0, 0);
                }
                this._vectorPoint = point1.subtract(point2);
            }

            get x() {
                return this._vectorPoint.x;
            }

            get y() {
                return this._vectorPoint.y;
            }

            normalVector() {
                let p = new Point(-1 * this.y, this.x);
                return new Vector(p);
            }

            multiply(multiplier) {
                let p = this._vectorPoint.multiply(multiplier);
                return new Vector(p);
            }
        }

        return Vector;
    }
]);