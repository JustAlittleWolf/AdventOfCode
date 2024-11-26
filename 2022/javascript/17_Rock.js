import { Material, fromString } from "./17_Material";
import { Vec2 } from "./17_Vec2";
export class Rock {
    shape = [];
    pos = new Vec2(0, 0);
    nextRock = "Flat";
    static Flat = new Rock("####").setNextRock("Plus");
    static Plus = new Rock(".#.\n###\n.#.").setNextRock("L");
    static L = new Rock("..#\n..#\n###").setNextRock("I");
    static I = new Rock("#\n#\n#\n#").setNextRock("Square");
    static Square = new Rock("##\n##").setNextRock("Flat");
    constructor(input) {
        let height = input.split("\n").length - 1;
        input.split("\n").forEach((row, y) => {
            if (!(this.shape[height - y] instanceof Array))
                this.shape[height - y] = [];
            row.split("").forEach((character, x) => {
                this.shape[height - y][x] = fromString(character);
            });
        });
    }
    getMaterial(x, y) {
        if (!(this.shape[y] instanceof Array))
            return Material.Air;
        if (!this.shape[y][x])
            return Material.Air;
        return this.shape[y][x];
    }
    getPos() {
        return this.pos;
    }
    setPos(vec) {
        this.pos = vec;
    }
    getWidth() {
        return this.shape[0].length;
    }
    getHeight() {
        return this.shape.length;
    }
    toString() {
        return this.shape.reduceRight((acc, cur) => acc + "\n" + cur.reduce((acc_, cur_) => acc_ + cur_, ""), "");
    }
    getNextRock() {
        if (Rock[this.nextRock] instanceof Rock)
            return Rock[this.nextRock];
        return Rock.Flat;
    }
    setNextRock(next) {
        this.nextRock = next;
        return this;
    }
    getShape() {
        return this.shape;
    }
}
