import { JetManager } from "./17_JetManager";
import { Material } from "./17_Material";
import { Rock } from "./17_Rock";
import { Vec2 } from "./17_Vec2";
import { input } from "./17_input";
import { CyclePattern } from "./17_CyclePattern";
export class Tunnel {
    width;
    structure = [];
    curRock = Rock.Square;
    jetManager = new JetManager(input);
    rockNum = 1;
    cycles = [];
    syntheticHeight = 0;
    constructor(width) {
        this.width = width;
        this.summonNextRock();
    }
    simulateStep() {
        let sideMovement = this.jetManager.next();
        this.tryMove(sideMovement);
        if (!this.tryMove(new Vec2(0, -1))) {
            this.settleRock();
            this.summonNextRock();
        }
    }
    simulateRocks(rocks) {
        while (this.rockNum <= rocks) {
            this.simulateStep();
            this.checkCycle(rocks);
        }
    }
    checkCycle(rocks) {
        if (!this.jetManager.freshCycle())
            return;
        let curCycle = new CyclePattern(this.getHeight(), this.curRock.toString() + (this.getHeight() - this.curRock.getPos().y), this.rockNum);
        let matchingCycles = this.cycles.filter(cycle => CyclePattern.areEqual(cycle, curCycle));
        if (matchingCycles.length <= 0) {
            this.cycles.push(curCycle);
            return;
        }
        let cycleHeight = curCycle.towerHeight - matchingCycles[0].towerHeight;
        let cycleRocks = curCycle.rockNum - matchingCycles[0].rockNum;
        let syntheticCycles = Math.floor((rocks - this.rockNum) / cycleRocks);
        this.rockNum = syntheticCycles * cycleRocks + this.rockNum;
        this.syntheticHeight = syntheticCycles * cycleHeight;
    }
    getHeight() {
        return this.structure.length;
    }
    getSyntheticHeight() {
        return this.structure.length + this.syntheticHeight;
    }
    summonNextRock() {
        this.curRock = this.curRock.getNextRock();
        this.curRock.setPos(new Vec2(2, this.getHeight() + 3));
    }
    curRockCollides() {
        let curPos = this.curRock.getPos();
        let collides = false;
        this.curRock.getShape().forEach((row, y) => {
            row.forEach((material, x) => {
                if (material == Material.Air)
                    return;
                if (this.getMaterial(curPos.x + x, curPos.y + y) == Material.Air)
                    return;
                collides = true;
            });
        });
        return collides;
    }
    tryMove(movementVec) {
        this.curRock.setPos(Vec2.add(this.curRock.getPos(), movementVec));
        if (this.curRockCollides()) {
            this.curRock.setPos(Vec2.sub(this.curRock.getPos(), movementVec));
            return false;
        }
        return true;
    }
    settleRock() {
        let curPos = this.curRock.getPos();
        this.curRock.getShape().forEach((row, y) => {
            row.forEach((material, x) => {
                if (material == Material.Air)
                    return;
                this.setMaterial(x + curPos.x, y + curPos.y, Material.Rock);
            });
        });
        this.rockNum++;
    }
    setMaterial(x, y, material) {
        if (!(this.structure[y] instanceof Array))
            this.structure[y] = [];
        this.structure[y][x] = material;
    }
    getMaterial(x, y) {
        if (x < 0 || x >= this.width)
            return Material.Rock;
        if (y < 0)
            return Material.Rock;
        if (!(this.structure[y] instanceof Array))
            return Material.Air;
        if (!this.structure[y][x])
            return Material.Air;
        return this.structure[y][x];
    }
    toString() {
        let sum = "";
        let curRockPos = this.curRock.getPos();
        for (let y = this.getHeight() + 3 + this.curRock.getHeight() - 1; y >= 0; y--) {
            for (let x = 0; x < this.width; x++) {
                let curRockMaterial = this.curRock.getMaterial(x - curRockPos.x, y - curRockPos.y);
                if (curRockMaterial == Material.Rock) {
                    sum += "@";
                    continue;
                }
                sum += this.getMaterial(x, y);
            }
            sum += "\n";
        }
        return sum;
    }
}
