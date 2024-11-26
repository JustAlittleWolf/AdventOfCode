import { Vec2 } from "./vec2.js";
document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let commands = input.split("\n");
    let rope = new Rope(10);
    commands.forEach(command => rope.move(command));
    document.getElementById("output").value = "Visited Spaces #1: " + rope.knots[1].visitedSpaces.length;
    document.getElementById("output").value += "\nVisited Spaces #9: " + rope.knots[9].visitedSpaces.length;
};
class Rope {
    knots;
    static directions = {
        "U": new Vec2(0, 1),
        "D": new Vec2(0, -1),
        "L": new Vec2(-1, 0),
        "R": new Vec2(1, 0)
    };
    constructor(length) {
        this.knots = [];
        for (let i = 0; i < length; i++) {
            this.knots.push(new Knot(new Vec2(0, 0), this));
        }
        this.knots.forEach(knot => knot.addVisited(knot.pos.copy()));
    }
    move(command) {
        let args = command.split(" ");
        for (let i = 0; i < Number(args[1]); i++) {
            this.moveHead(Rope.directions[args[0]]);
        }
    }
    moveHead(vec) {
        this.head.pos.add(vec.x, vec.y);
        this.head.movement.set(vec.x, vec.y);
        this.knots.slice(1).forEach(knot => {
            knot.movement.set(0, 0);
            if (!knot.isAdjacent(knot.prev())) {
                if (knot.pos.sharesCoord(knot.prev().pos)) {
                    knot.movement.setVec(knot.prev().pos.copy().subVec(knot.pos).shorten(1));
                }
                else if (knot.prev().movement.isDiagonal()) {
                    knot.movement.setVec(knot.prev().movement);
                }
                else {
                    knot.movement.setVec(knot.prev().getLastPos().copy().subVec(knot.pos));
                }
                knot.pos.addVec(knot.movement);
                knot.addVisited(knot.pos.copy());
            }
        });
    }
    getNumberOfKnots() {
        return this.knots.length;
    }
    get head() {
        return this.knots[0];
    }
}
class Knot {
    pos;
    index;
    rope;
    visitedSpaces;
    movement;
    constructor(pos, rope) {
        this.pos = pos;
        this.rope = rope;
        this.index = rope.getNumberOfKnots();
        this.visitedSpaces = [];
        this.movement = new Vec2(0, 0);
    }
    next() {
        if (this.index == this.rope.getNumberOfKnots() - 1)
            return undefined;
        return this.rope.knots[this.index + 1];
    }
    prev() {
        if (this.index == 0)
            return undefined;
        return this.rope.knots[this.index - 1];
    }
    addVisited(vec) {
        if (!this.visitedSpaces.some(elem => elem.equals(vec))) {
            this.visitedSpaces.push(vec);
        }
    }
    isAdjacent(knot) {
        return this.pos.isAdjacent(knot.pos);
    }
    getLastPos() {
        return this.pos.copy().subVec(this.movement);
    }
}
