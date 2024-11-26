export class Vec2 {
  x: number;
  y: number;

  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }

  add(x: number, y: number): Vec2 {
    this.x += x;
    this.y += y;
    return this;
  }

  addVec(vec: Vec2): Vec2 {
    this.x += vec.x;
    this.y += vec.y;
    return this;
  }

  sub(x: number, y: number): Vec2 {
    this.x -= x;
    this.y -= y;
    return this;
  }

  subVec(vec: Vec2): Vec2 {
    this.x -= vec.x;
    this.y -= vec.y;
    return this;
  }

  isAdjacent(vec: Vec2): boolean {
    return !(Math.max(Math.abs(this.x - vec.x), Math.abs(this.y - vec.y)) > 1);
  }

  copy(): Vec2 {
    return new Vec2(this.x, this.y);
  }

  set(x: number, y: number): Vec2 {
    this.x = x;
    this.y = y;
    return this;
  }

  setVec(vec: Vec2): Vec2 {
    this.x = vec.x;
    this.y = vec.y;
    return this;
  }

  equals(vec: Vec2): boolean {
    return this.x == vec.x && this.y == vec.y;
  }

  isDiagonal(): boolean {
    return this.x != 0 && this.y != 0;
  }

  sharesCoord(vec: Vec2): boolean {
    return this.x == vec.x || this.y == vec.y;
  }

  shorten(num: number): Vec2 {
    if (this.x != 0) this.x -= Math.sign(this.x) * num;
    if (this.y != 0) this.y -= Math.sign(this.y) * num;
    return this;
  }

  isNextTo(vec: Vec2): boolean {
    return !(Math.max(Math.abs(this.x - vec.x), Math.abs(this.y - vec.y)) > 1) && (this.x == 0 || this.y == 0);
  }
}