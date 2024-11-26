document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let droplet = new Droplet(input);
    droplet.steamify();
    document.getElementById("output").value = "Total Surface: " + droplet.getSurface(false);
    document.getElementById("output").value += "\nOuter Surface: " + droplet.getSurface(true);
};
var Material;
(function (Material) {
    Material[Material["Air"] = 0] = "Air";
    Material[Material["Lava"] = 1] = "Lava";
    Material[Material["Steam"] = 2] = "Steam";
})(Material || (Material = {}));
class Droplet {
    voxelMap = new Map;
    boundingBox = new BoundingBox();
    constructor(input) {
        input.split("\n").forEach(cube => {
            let [x, y, z] = cube.split(",").map(el => Number.parseInt(el));
            let pos = new Vec3(x, y, z);
            this.setVoxel(pos, Material.Lava);
        });
    }
    getVoxel(pos) {
        let val = this.voxelMap.get(pos.toString());
        if (!val)
            return Material.Air;
        return val;
    }
    setVoxel(pos, material) {
        this.boundingBox.adjust(pos);
        this.voxelMap.set(pos.toString(), material);
    }
    getSurface(steamy = false) {
        let surface = 0;
        this.voxelMap.forEach((material, positionString) => {
            if (material != Material.Lava)
                return;
            let [x, y, z] = positionString.replaceAll(/[()]/g, "").split(",").map(el => Number.parseInt(el));
            let voxel = new Vec3(x, y, z);
            voxel.getNeighbours().forEach(neighbour => {
                let neighMaterial = this.getVoxel(neighbour);
                if (neighMaterial == Material.Lava)
                    return;
                if (steamy && neighMaterial == Material.Air)
                    return;
                surface++;
            });
        });
        return surface;
    }
    steamify() {
        this.boundingBox.expand(1);
        let startVec = this.boundingBox.getMinVec();
        let checkNext = [startVec];
        while (checkNext.length > 0) {
            let tempNext = [];
            checkNext.forEach(voxel => {
                this.setVoxel(voxel, Material.Steam);
                let validVoxelNeighbours = voxel.getNeighbours();
                validVoxelNeighbours = validVoxelNeighbours.filter(pos => this.getVoxel(pos) == Material.Air);
                validVoxelNeighbours = validVoxelNeighbours.filter(pos => this.boundingBox.vecWithin(pos));
                validVoxelNeighbours = validVoxelNeighbours.filter(pos => !tempNext.some(elem => elem.toString() == pos.toString()));
                tempNext = tempNext.concat(validVoxelNeighbours);
            });
            checkNext = tempNext;
        }
    }
}
class BoundingBox {
    minVec = new Vec3(0, 0, 0);
    maxVec = new Vec3(1, 1, 1);
    constructor() {
    }
    adjust(pos) {
        this.minVec = Vec3.min(this.minVec, pos);
        this.maxVec = Vec3.max(this.maxVec, pos);
    }
    expand(radius) {
        this.minVec = new Vec3(this.minVec.x - radius, this.minVec.y - radius, this.minVec.z - radius);
        this.maxVec = new Vec3(this.maxVec.x + radius, this.maxVec.y + radius, this.maxVec.z + radius);
    }
    toString() {
        return this.minVec + " -> " + this.maxVec;
    }
    getMinVec() {
        return this.minVec;
    }
    getMaxVec() {
        return this.maxVec;
    }
    vecWithin(pos) {
        return this.minVec.x <= pos.x && this.maxVec.x >= pos.x && this.minVec.y <= pos.y && this.maxVec.y >= pos.y && this.minVec.z <= pos.z && this.maxVec.z >= pos.z;
    }
}
class Vec3 {
    x;
    y;
    z;
    constructor(x, y, z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    static min(vec0, vec1) {
        return new Vec3(Math.min(vec0.x, vec1.x), Math.min(vec0.y, vec1.y), Math.min(vec0.z, vec1.z));
    }
    static max(vec0, vec1) {
        return new Vec3(Math.max(vec0.x, vec1.x), Math.max(vec0.y, vec1.y), Math.max(vec0.z, vec1.z));
    }
    static sum(vec0, vec1) {
        return new Vec3(vec0.x + vec1.x, vec0.y + vec1.y, vec0.z + vec1.z);
    }
    toString() {
        return `(${this.x}, ${this.y}, ${this.z})`;
    }
    getNeighbours() {
        let neighOffsets = [
            new Vec3(1, 0, 0),
            new Vec3(-1, 0, 0),
            new Vec3(0, 1, 0),
            new Vec3(0, -1, 0),
            new Vec3(0, 0, 1),
            new Vec3(0, 0, -1)
        ];
        return neighOffsets.map(neigh => Vec3.sum(neigh, this));
    }
}
export {};
