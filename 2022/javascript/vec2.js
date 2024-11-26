export class Vec2 {
    x;
    y;
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
    add(x, y) {
        this.x += x;
        this.y += y;
        return this;
    }
    addVec(vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }
    sub(x, y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    subVec(vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        return this;
    }
    isAdjacent(vec) {
        return !(Math.max(Math.abs(this.x - vec.x), Math.abs(this.y - vec.y)) > 1);
    }
    copy() {
        return new Vec2(this.x, this.y);
    }
    set(x, y) {
        this.x = x;
        this.y = y;
        return this;
    }
    setVec(vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }
    equals(vec) {
        return this.x == vec.x && this.y == vec.y;
    }
    isDiagonal() {
        return this.x != 0 && this.y != 0;
    }
    sharesCoord(vec) {
        return this.x == vec.x || this.y == vec.y;
    }
    shorten(num) {
        if (this.x != 0)
            this.x -= Math.sign(this.x) * num;
        if (this.y != 0)
            this.y -= Math.sign(this.y) * num;
        return this;
    }
    isNextTo(vec) {
        return !(Math.max(Math.abs(this.x - vec.x), Math.abs(this.y - vec.y)) > 1) && (this.x == 0 || this.y == 0);
    }
}
