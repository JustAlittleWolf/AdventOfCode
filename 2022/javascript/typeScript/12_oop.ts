import { Vec2 } from "./vec2.js";

document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;
  let region: Region = new Region(input);
  region.pathFind();
  (<HTMLInputElement>document.getElementById("output")).value = "Shortest Path from S: " + region.getEndDistance();
  (<HTMLInputElement>document.getElementById("output")).value += "\nShortest Path from a: ";
}

class Region {
  private elevationMap: Point[][] = [];
  private start: Point;
  private end: Point;

  constructor(input: String) {
    let rows: string[] = input.split("\n");
    rows.forEach((row, y) => {
      let points: string[] = row.split("");
      points.forEach((point, x) => {
        if (this.elevationMap[x] == undefined) this.elevationMap[x] = [];
        if (point == "S") {
          this.start = new Point("a", new Vec2(x, y), this);
          this.elevationMap[x][y] = this.start;
        } else if (point == "E") {
          this.end = new Point("z", new Vec2(x, y), this);
          this.elevationMap[x][y] = this.end;
        } else {
          this.elevationMap[x][y] = new Point(point, new Vec2(x, y), this);
        }
      });
    });
  }

  public pathFind(): void {
    this.start.update(0);
  }

  public getPoint(pos: Vec2): Point {
    if (this.elevationMap[pos.x] == undefined) return undefined;
    return this.elevationMap[pos.x][pos.y];
  }

  public getEndDistance(): number {
    return this.end.distance;
  }
}

class Point extends Vec2 {
  private region: Region;
  public elevation: number;
  public distance: number = 0;
  //public suggestedPath: Vec2;

  constructor(elevation: string, pos: Vec2, region: Region) {
    super(pos.x, pos.y);
    this.elevation = elevation.charCodeAt(0) - 97;
    this.region = region;
  }

  private hasBeenVisited(): boolean {
    return this.distance != 0;
  }

  private canMoveTo(point: Point): boolean {
    return this.elevation + 1 >= point.elevation;
  }

  private getNeighbors(): Point[] {
    let neighbors: Point[] = [];
    let directions: Vec2[] = [new Vec2(1, 0), new Vec2(0, 1), new Vec2(-1, 0), new Vec2(0, -1)];
    directions.forEach(direction => {
      let neighbor: Point = this.region.getPoint(this.copy().addVec(direction));
      if (neighbor != undefined) neighbors.push(neighbor);
    });
    return neighbors;
  }

  public update(distance: number): void {
    this.distance = distance;
    let neighbors: Point[] = this.getNeighbors();
    neighbors.forEach(neighbor => {
      if ((neighbor.hasBeenVisited() && this.canMoveTo(neighbor) && neighbor.distance > this.distance + 1) || (!neighbor.hasBeenVisited() && this.canMoveTo(neighbor))) {
        //neighbor.suggestedPath = new Vec2(this.x - neighbor.x, this.y - neighbor.y);
        neighbor.update(this.distance + 1);
      }
    });
  }
}