document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let caveMap = new CaveMap(input);
    caveMap.calculateSand();
    document.getElementById("output").value = "Sand: " + caveMap.totalSand;
    caveMap = new CaveMap(input, 2);
    caveMap.calculateSand();
    document.getElementById("output").value += "\nSand with floor: " + caveMap.totalSand;
};
class CaveMap {
    tiles = [];
    sandSpawn = {
        x: 500,
        y: 0
    };
    static materials = {
        Air: undefined,
        Rock: true,
        Sand: false
    };
    maxDepth = 0;
    floor = Infinity;
    totalSand = 0;
    constructor(input, floor = Infinity) {
        for (const row of truncateArray(input.split("\n"))) {
            let prevPoint = undefined;
            for (const point of row.split(" -> ")) {
                let curPoint = { x: Number.parseInt(point.split(",")[0]), y: Number.parseInt(point.split(",")[1]) };
                this.maxDepth = Math.max(curPoint.y, this.maxDepth);
                prevPoint = prevPoint == undefined ? curPoint : prevPoint;
                for (let x = Math.min(curPoint.x, prevPoint.x); x <= Math.max(curPoint.x, prevPoint.x); x++) {
                    this.setMaterial(x, curPoint.y, CaveMap.materials.Rock);
                }
                for (let y = Math.min(curPoint.y, prevPoint.y); y <= Math.max(curPoint.y, prevPoint.y); y++) {
                    this.setMaterial(curPoint.x, y, CaveMap.materials.Rock);
                }
                prevPoint = curPoint;
            }
        }
        if (floor != Infinity) {
            this.floor = this.maxDepth + floor;
            this.maxDepth = this.floor - 1;
        }
    }
    setMaterial(x, y, material) {
        if (this.tiles[x] == undefined)
            this.tiles[x] = [];
        this.tiles[x][y] = material;
    }
    getMaterial(x, y) {
        if (y >= this.floor)
            return CaveMap.materials.Rock;
        if (this.tiles[x] == undefined)
            return undefined;
        return this.tiles[x][y];
    }
    calculateSand() {
        while (this.getMaterial(this.sandSpawn.x, this.sandSpawn.y) == CaveMap.materials.Air) {
            let sand = new Sand(this.sandSpawn.x, this.sandSpawn.y, this);
            while (sand.fall()) {
                if (sand.y > this.maxDepth)
                    return;
            }
        }
    }
    getGraphics() {
        let xmin = 0;
        for (let x = 0; x < this.tiles.length; x++) {
            if (this.tiles[x] != undefined) {
                if (this.tiles[x].reduce((acc, tile) => acc && tile == CaveMap.materials.Air), true) {
                    xmin = x - 1;
                    break;
                }
            }
        }
        let xmax = this.tiles.length - 1 + 2;
        let output = "";
        for (let y = 0; y < this.maxDepth + 2; y++) {
            for (let x = xmin; x < xmax; x++) {
                switch (this.getMaterial(x, y)) {
                    case CaveMap.materials.Air:
                        output += ".";
                        break;
                    case CaveMap.materials.Rock:
                        output += "#";
                        break;
                    case CaveMap.materials.Sand:
                        output += "o";
                        break;
                }
            }
            output += "\n";
        }
        return output;
    }
}
class Sand {
    x;
    y;
    caveMap;
    constructor(x, y, caveMap) {
        this.x = x;
        this.y = y;
        this.caveMap = caveMap;
    }
    fall() {
        let targetLocationsX = [0, -1, 1];
        for (const xOffset of targetLocationsX) {
            if (this.caveMap.getMaterial(this.x + xOffset, this.y + 1) == CaveMap.materials.Air) {
                this.y++;
                this.x += xOffset;
                return true;
            }
        }
        this.set();
        this.caveMap.totalSand++;
        return false;
    }
    set() {
        this.caveMap.setMaterial(this.x, this.y, CaveMap.materials.Sand);
    }
}
function truncateArray(arr) {
    if (arr[0] == "")
        arr.shift();
    if (arr[arr.length - 1] == "")
        arr.pop();
    return arr;
}
export {};
