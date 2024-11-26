document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;

  let droplet = new Droplet(input);
  droplet.steamify();

  (<HTMLInputElement>document.getElementById("output")).value = "Total Surface: " + droplet.getSurface(false);
  (<HTMLInputElement>document.getElementById("output")).value += "\nOuter Surface: " + droplet.getSurface(true);
}

enum Material {
  Air,
  Lava,
  Steam
}

class Droplet {
  private voxelMap = new Map<string, Material>;
  private boundingBox = new BoundingBox();

  constructor(input: string) {
    input.split("\n").forEach(cube => {
      let [x, y, z] = cube.split(",").map(el => Number.parseInt(el));
      let pos = new Vec3(x, y, z);
      this.setVoxel(pos, Material.Lava);
    });
  }

  private getVoxel(pos: Vec3): Material {
    let val: Material = this.voxelMap.get(pos.toString());
    if (!val) return Material.Air;
    return val;
  }

  private setVoxel(pos: Vec3, material: Material): void {
    this.boundingBox.adjust(pos);
    this.voxelMap.set(pos.toString(), material);
  }

  public getSurface(steamy: boolean = false): number {
    let surface = 0;
    this.voxelMap.forEach((material, positionString) => {
      if (material != Material.Lava) return;
      let [x, y, z] = positionString.replaceAll(/[()]/g, "").split(",").map(el => Number.parseInt(el));
      let voxel = new Vec3(x, y, z);
      voxel.getNeighbours().forEach(neighbour => {
        let neighMaterial = this.getVoxel(neighbour);
        if (neighMaterial == Material.Lava) return;
        if (steamy && neighMaterial == Material.Air) return;
        surface++;
      })
    });
    return surface;
  }

  steamify() {
    this.boundingBox.expand(1);
    let startVec = this.boundingBox.getMinVec();
    let checkNext: Vec3[] = [startVec];
    while (checkNext.length > 0) {
      let tempNext: Vec3[] = [];
      checkNext.forEach(voxel => {
        this.setVoxel(voxel, Material.Steam);
        let validVoxelNeighbours: Vec3[] = voxel.getNeighbours();
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
  private minVec = new Vec3(0, 0, 0);
  private maxVec = new Vec3(1, 1, 1);

  constructor() {

  }

  public adjust(pos: Vec3): void {
    this.minVec = Vec3.min(this.minVec, pos);
    this.maxVec = Vec3.max(this.maxVec, pos);
  }

  public expand(radius: number): void {
    this.minVec = new Vec3(this.minVec.x - radius, this.minVec.y - radius, this.minVec.z - radius);
    this.maxVec = new Vec3(this.maxVec.x + radius, this.maxVec.y + radius, this.maxVec.z + radius);
  }

  public toString(): string {
    return this.minVec + " -> " + this.maxVec;
  }

  public getMinVec(): Vec3 {
    return this.minVec;
  }

  public getMaxVec(): Vec3 {
    return this.maxVec;
  }

  public vecWithin(pos: Vec3) {
    return this.minVec.x <= pos.x && this.maxVec.x >= pos.x && this.minVec.y <= pos.y && this.maxVec.y >= pos.y && this.minVec.z <= pos.z && this.maxVec.z >= pos.z;
  }
}

class Vec3 {
  public readonly x: number;
  public readonly y: number;
  public readonly z: number;

  constructor(x: number, y: number, z: number) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public static min(vec0: Vec3, vec1: Vec3): Vec3 {
    return new Vec3(Math.min(vec0.x, vec1.x), Math.min(vec0.y, vec1.y), Math.min(vec0.z, vec1.z));
  }

  public static max(vec0: Vec3, vec1: Vec3): Vec3 {
    return new Vec3(Math.max(vec0.x, vec1.x), Math.max(vec0.y, vec1.y), Math.max(vec0.z, vec1.z));
  }

  public static sum(vec0: Vec3, vec1: Vec3): Vec3 {
    return new Vec3(vec0.x + vec1.x, vec0.y + vec1.y, vec0.z + vec1.z);
  }

  public toString(): string {
    return `(${this.x}, ${this.y}, ${this.z})`;
  }

  public getNeighbours(): Vec3[] {
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