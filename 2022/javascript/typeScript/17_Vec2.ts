export class Vec2 {
    public x: number;
    public y: number;

    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }

    public static add(vec0: Vec2, vec1: Vec2) {
        return new Vec2(vec0.x + vec1.x, vec0.y + vec1.y);
    }

    public static sub(vec0: Vec2, vec1: Vec2) {
        return new Vec2(vec0.x - vec1.x, vec0.y - vec1.y);
    }
}