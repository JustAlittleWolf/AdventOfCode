import { Vec2 } from "./vec2.js";

document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;
  let commands: Array<string> = input.split("\n");
  let rope = new Rope(10);
  commands.forEach(command => rope.move(command));
  (<HTMLInputElement>document.getElementById("output")).value = "Visited Spaces #1: " + rope.knots[1].visitedSpaces.length;
  (<HTMLInputElement>document.getElementById("output")).value += "\nVisited Spaces #9: " + rope.knots[9].visitedSpaces.length;
}

class Rope {
  public knots: Array<Knot>;

  static directions: Object = {
    "U": new Vec2(0, 1),
    "D": new Vec2(0, -1),
    "L": new Vec2(-1, 0),
    "R": new Vec2(1, 0)
  };

  constructor(length: number) {
    this.knots = [];
    for (let i = 0; i < length; i++) {
      this.knots.push(new Knot(new Vec2(0, 0), this));
    }
    this.knots.forEach(knot => knot.addVisited(knot.pos.copy()));
  }

  public move(command: string) {
    let args: Array<string> = command.split(" ");
    for (let i = 0; i < Number(args[1]); i++) {
      this.moveHead(Rope.directions[args[0]]);
    }
  }

  moveHead(vec: Vec2) {
    this.head.pos.add(vec.x, vec.y);
    this.head.movement.set(vec.x, vec.y);
    this.knots.slice(1).forEach(knot => {
      knot.movement.set(0, 0);
      if (!knot.isAdjacent(knot.prev())) {
        if (knot.pos.sharesCoord(knot.prev().pos)) {
          knot.movement.setVec(knot.prev().pos.copy().subVec(knot.pos).shorten(1));
        } else if (knot.prev().movement.isDiagonal()) {
          knot.movement.setVec(knot.prev().movement);
        } else {
          knot.movement.setVec(knot.prev().getLastPos().copy().subVec(knot.pos));
        }
        knot.pos.addVec(knot.movement);
        knot.addVisited(knot.pos.copy());
      }
    });
  }

  getNumberOfKnots(): number {
    return this.knots.length;
  }

  get head(): Knot {
    return this.knots[0];
  }
}

class Knot {
  pos: Vec2;
  index: number;
  rope: Rope;
  visitedSpaces: Array<Vec2>;
  movement: Vec2;

  constructor(pos: Vec2, rope: Rope) {
    this.pos = pos;
    this.rope = rope;
    this.index = rope.getNumberOfKnots();
    this.visitedSpaces = [];
    this.movement = new Vec2(0, 0);
  }

  next(): Knot {
    if (this.index == this.rope.getNumberOfKnots() - 1) return undefined;
    return this.rope.knots[this.index + 1];
  }

  prev(): Knot {
    if (this.index == 0) return undefined;
    return this.rope.knots[this.index - 1];
  }

  addVisited(vec: Vec2) {
    if (!this.visitedSpaces.some(elem => elem.equals(vec))) {
      this.visitedSpaces.push(vec);
    }
  }

  isAdjacent(knot: Knot): boolean {
    return this.pos.isAdjacent(knot.pos);
  }

  getLastPos(): Vec2 {
    return this.pos.copy().subVec(this.movement);
  }
}