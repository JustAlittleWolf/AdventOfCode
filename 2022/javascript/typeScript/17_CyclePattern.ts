export class CyclePattern {
    public towerHeight: number;
    public name: string;
    public rockNum: number;

    constructor(towerHeight: number, name: string, rockNum: number) {
        this.towerHeight = towerHeight;
        this.name = name;
        this.rockNum = rockNum;
    }

    public static areEqual(cycle0: CyclePattern, cycle1: CyclePattern) {
        return cycle0.name == cycle1.name;
    }
}