type Vec2 = {
    x: number,
    y: number
}

type Material = undefined | true | false;

document.getElementById("submit").onclick = () => {
    let input: string = (<HTMLInputElement>document.getElementById("input")).value;

    let caveMap = new CaveMap(input);
    caveMap.calculateSand();

    (<HTMLInputElement>document.getElementById("output")).value = "Sand: " + caveMap.totalSand;

    caveMap = new CaveMap(input, 2);
    caveMap.calculateSand();

    (<HTMLInputElement>document.getElementById("output")).value += "\nSand with floor: " + caveMap.totalSand;
}

class CaveMap {
    private tiles: boolean[][] = [];
    private sandSpawn: Vec2 = {
        x: 500,
        y: 0
    };
    public static materials = {
        Air: undefined,
        Rock: true,
        Sand: false
    };
    private maxDepth: number = 0;
    private floor: number = Infinity;
    public totalSand: number = 0;
    constructor(input: string, floor: number = Infinity) {
        for (const row of truncateArray(input.split("\n"))) {
            let prevPoint: Vec2 = undefined;
            for (const point of row.split(" -> ")) {
                let curPoint: Vec2 = { x: Number.parseInt(point.split(",")[0]), y: Number.parseInt(point.split(",")[1]) };
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

    public setMaterial(x: number, y: number, material: Material) {
        if (this.tiles[x] == undefined) this.tiles[x] = [];
        this.tiles[x][y] = material;
    }

    public getMaterial(x: number, y: number): Material {
        if (y >= this.floor) return CaveMap.materials.Rock;
        if (this.tiles[x] == undefined) return undefined;
        return this.tiles[x][y];
    }

    public calculateSand() {
        while (this.getMaterial(this.sandSpawn.x, this.sandSpawn.y) == CaveMap.materials.Air) {
            let sand = new Sand(this.sandSpawn.x, this.sandSpawn.y, this);
            while (sand.fall()) {
                if (sand.y > this.maxDepth) return;
            }
        }
    }

    public getGraphics(): string {
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
    public x: number;
    public y: number;
    private caveMap: CaveMap;
    constructor(x: number, y: number, caveMap: CaveMap) {
        this.x = x;
        this.y = y;
        this.caveMap = caveMap;
    }

    public fall(): boolean {
        let targetLocationsX: number[] = [0, -1, 1];
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

    private set() {
        this.caveMap.setMaterial(this.x, this.y, CaveMap.materials.Sand);
    }
}

function truncateArray(arr: any[]): any[] {
    if (arr[0] == "") arr.shift();
    if (arr[arr.length - 1] == "") arr.pop();
    return arr;
}