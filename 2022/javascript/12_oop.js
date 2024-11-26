import { Vec2 } from "./vec2.js";
document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let region = new Region(input);
    region.pathFind();
    document.getElementById("output").value = "Shortest Path from S: " + region.getEndDistance();
    document.getElementById("output").value += "\nShortest Path from a: ";
};
class Region {
    elevationMap = [];
    start;
    end;
    constructor(input) {
        let rows = input.split("\n");
        rows.forEach((row, y) => {
            let points = row.split("");
            points.forEach((point, x) => {
                if (this.elevationMap[x] == undefined)
                    this.elevationMap[x] = [];
                if (point == "S") {
                    this.start = new Point("a", new Vec2(x, y), this);
                    this.elevationMap[x][y] = this.start;
                }
                else if (point == "E") {
                    this.end = new Point("z", new Vec2(x, y), this);
                    this.elevationMap[x][y] = this.end;
                }
                else {
                    this.elevationMap[x][y] = new Point(point, new Vec2(x, y), this);
                }
            });
        });
    }
    pathFind() {
        this.start.update(0);
    }
    getPoint(pos) {
        if (this.elevationMap[pos.x] == undefined)
            return undefined;
        return this.elevationMap[pos.x][pos.y];
    }
    getEndDistance() {
        return this.end.distance;
    }
}
class Point extends Vec2 {
    region;
    elevation;
    distance = 0;
    //public suggestedPath: Vec2;
    constructor(elevation, pos, region) {
        super(pos.x, pos.y);
        this.elevation = elevation.charCodeAt(0) - 97;
        this.region = region;
    }
    hasBeenVisited() {
        return this.distance != 0;
    }
    canMoveTo(point) {
        return this.elevation + 1 >= point.elevation;
    }
    getNeighbors() {
        let neighbors = [];
        let directions = [new Vec2(1, 0), new Vec2(0, 1), new Vec2(-1, 0), new Vec2(0, -1)];
        directions.forEach(direction => {
            let neighbor = this.region.getPoint(this.copy().addVec(direction));
            if (neighbor != undefined)
                neighbors.push(neighbor);
        });
        return neighbors;
    }
    update(distance) {
        this.distance = distance;
        let neighbors = this.getNeighbors();
        neighbors.forEach(neighbor => {
            if ((neighbor.hasBeenVisited() && this.canMoveTo(neighbor) && neighbor.distance > this.distance + 1) || (!neighbor.hasBeenVisited() && this.canMoveTo(neighbor))) {
                //neighbor.suggestedPath = new Vec2(this.x - neighbor.x, this.y - neighbor.y);
                neighbor.update(this.distance + 1);
            }
        });
    }
}
