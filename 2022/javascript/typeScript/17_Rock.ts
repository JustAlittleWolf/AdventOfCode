import { Material, fromString } from "./17_Material";
import { Vec2 } from "./17_Vec2";

export class Rock {
    private shape: Material[][] = [];
    private pos = new Vec2(0, 0);
    private nextRock: string = "Flat";

    public static Flat: Rock = new Rock("####").setNextRock("Plus");
    public static Plus: Rock = new Rock(".#.\n###\n.#.").setNextRock("L");
    public static L = new Rock("..#\n..#\n###").setNextRock("I");
    public static I = new Rock("#\n#\n#\n#").setNextRock("Square");
    public static Square = new Rock("##\n##").setNextRock("Flat");

    constructor(input: string) {
        let height = input.split("\n").length - 1;
        input.split("\n").forEach((row, y) => {
            if (!(this.shape[height - y] instanceof Array)) this.shape[height - y] = [];
            row.split("").forEach((character, x) => {
                this.shape[height - y][x] = fromString(character);
            })
        });
    }

    public getMaterial(x: number, y: number): Material {
        if (!(this.shape[y] instanceof Array)) return Material.Air;
        if (!this.shape[y][x]) return Material.Air;
        return this.shape[y][x];
    }

    public getPos(): Vec2 {
        return this.pos;
    }

    public setPos(vec: Vec2): void {
        this.pos = vec;
    }

    public getWidth(): number {
        return this.shape[0].length;
    }

    public getHeight(): number {
        return this.shape.length;
    }

    public toString(): string {
        return this.shape.reduceRight((acc, cur) => acc + "\n" + cur.reduce((acc_, cur_) => acc_ + cur_, ""), "");
    }

    public getNextRock(): Rock {
        if (Rock[this.nextRock] instanceof Rock) return Rock[this.nextRock];
        return Rock.Flat;
    }

    private setNextRock(next: string): Rock {
        this.nextRock = next;
        return this;
    }

    public getShape(): Material[][] {
        return this.shape;
    }
}