import { Vec2 } from "./17_Vec2";

export class JetManager {
    private jetMemory: String[] = [];
    private count = -1;

    constructor(input: string) {
        this.jetMemory = input.split("");
    }

    public next(): Vec2 {
        this.count++;
        if (this.count >= this.jetMemory.length) this.count = 0;
        switch (this.jetMemory[this.count]) {
            case ">":
                return new Vec2(1, 0);
            case "<":
                return new Vec2(-1, 0);
            default:
                throw new Error("Could not parse Steam Jet: " + this.jetMemory[this.count]);
        }
    }

    public freshCycle(): boolean {
        return this.count == 0;
    }
}