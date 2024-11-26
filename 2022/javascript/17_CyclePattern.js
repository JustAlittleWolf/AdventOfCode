export class CyclePattern {
    towerHeight;
    name;
    rockNum;
    constructor(towerHeight, name, rockNum) {
        this.towerHeight = towerHeight;
        this.name = name;
        this.rockNum = rockNum;
    }
    static areEqual(cycle0, cycle1) {
        return cycle0.name == cycle1.name;
    }
}
