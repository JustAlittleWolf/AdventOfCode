import { Vec2 } from "./17_Vec2";
export class JetManager {
    jetMemory = [];
    count = -1;
    constructor(input) {
        this.jetMemory = input.split("");
    }
    next() {
        this.count++;
        if (this.count >= this.jetMemory.length)
            this.count = 0;
        switch (this.jetMemory[this.count]) {
            case ">":
                return new Vec2(1, 0);
            case "<":
                return new Vec2(-1, 0);
            default:
                throw new Error("Could not parse Steam Jet: " + this.jetMemory[this.count]);
        }
    }
    freshCycle() {
        return this.count == 0;
    }
}
