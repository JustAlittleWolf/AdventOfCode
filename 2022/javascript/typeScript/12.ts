type Vec2 = {
  x: number,
  y: number
}

document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;
  let rows: number = input.split("\n").length;
  let rowLength: number = input.split("\n")[0].length + 1;
  let maxDistance: number = Infinity;
  for (let i = 0; i < rows - 1; i++) {
    let curIndex: number = i * rowLength;
    let variableInput: string = input.replaceAll("S", "a");
    maxDistance = Math.min(maxDistance, getDistance(variableInput.substring(0, curIndex) + "S" + variableInput.substring(curIndex + 1)));
  }

  (<HTMLInputElement>document.getElementById("output")).value = "Shortest Path from S: " + getDistance(input);
  (<HTMLInputElement>document.getElementById("output")).value += "\nShortest Path from a: " + maxDistance;
}

function getDistance(input: string): number {
  let heightMap: number[][] = [];
  let distanceMap: number[][] = [];
  let inspectNext: Vec2[] = [];
  let start: Vec2;
  let end: Vec2;

  input.split("\n").forEach((row, x) => {
    heightMap[x] = [];
    distanceMap[x] = [];
    row.split("").forEach((part, y) => {
      distanceMap[x][y] = Infinity;
      heightMap[x][y] = part.charCodeAt(0) - 97;
      if (part == "S") {
        start = { x: x, y: y };
        distanceMap[x][y] = 0;
        heightMap[x][y] = 0;
      }
      if (part == "E") {
        end = { x: x, y: y };
        heightMap[x][y] = 25;
      }
    });
  });

  inspectNext.push(start);
  doCycle();

  function doCycle() {
    let toDelete: number = inspectNext.length;
    for (const curr of inspectNext) {
      let neighbors: Vec2[] = [{ x: curr.x + 1, y: curr.y }, { x: curr.x - 1, y: curr.y }, { x: curr.x, y: curr.y + 1 }, { x: curr.x, y: curr.y - 1 }];
      for (const neigh of neighbors) {
        if (heightMap[neigh.x] == undefined || heightMap[neigh.x][neigh.y] == undefined) continue;
        if (heightMap[neigh.x][neigh.y] > heightMap[curr.x][curr.y] + 1) continue;
        if (distanceMap[neigh.x][neigh.y] <= distanceMap[curr.x][curr.y] + 1) continue;
        distanceMap[neigh.x][neigh.y] = distanceMap[curr.x][curr.y] + 1;
        inspectNext.push(neigh);
      }
    }
    inspectNext.splice(0, toDelete);
    if (inspectNext.length != 0 && distanceMap[end.x][end.y] == Infinity) doCycle();
  }

  return distanceMap[end.x][end.y];
}