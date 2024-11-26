export class Vec2 {
    x;
    y;
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
    static add(vec0, vec1) {
        return new Vec2(vec0.x + vec1.x, vec0.y + vec1.y);
    }
    static sub(vec0, vec1) {
        return new Vec2(vec0.x - vec1.x, vec0.y - vec1.y);
    }
}
