document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;

  let sensorManager = new SensorManager(input);

  (<HTMLInputElement>document.getElementById("output")).value = "# at 2000000: " + sensorManager.getNoBeaconNumAtY(2000000);

  let range: Vec4 = {
    x1: 0,
    y1: 0,
    x2: 4000000,
    y2: 4000000
  }

  let y = sensorManager.getRowWithLessThan(range, 4000001);
  let ranges = sensorManager.getNoBeaconRangeAtY(y, 0, 4000000, false);
  let x = ranges[0].end + 1;
  console.log(x, y);

  (<HTMLInputElement>document.getElementById("output")).value += "\nTuning frequency: " + (x * 4000000 + y);
}

type Vec2 = {
  x: number,
  y: number
}

type Vec4 = {
  x1: number,
  y1: number,
  x2: number,
  y2: number
}

class SensorManager {
  private sensors: Sensor[];
  private beacons: Vec2[];

  constructor(input: string) {
    this.sensors = [];
    for (let sensor of input.split("\n")) {
      this.sensors.push(new Sensor(sensor));
    }
    this.sensors.sort((a, b) => a.pos.x - b.pos.x);
    this.beacons = [];
    for (let sensor of this.sensors) {
      if (!this.beacons.some(elem => elem.y == sensor.beacon.y && elem.x == sensor.beacon.x)) this.beacons.push(sensor.beacon);
    }
  }

  public getNoBeaconRangeAtY(y: number, min: number = -Infinity, max: number = Infinity, beaconCheck: boolean = true): Span[] {
    let spans: Span[] = [];
    for (let i = 0; i < this.sensors.length; i++) {
      let noBeaconRange = this.sensors[i].getNoBeaconRangeAtY(y, min, max);
      if (noBeaconRange != null) {
        if (spans.length > 0 && spans[spans.length - 1].overlaps(noBeaconRange)) {
          spans[spans.length - 1] = Span.merge(spans[spans.length - 1], noBeaconRange);
        } else {
          spans[spans.length] = noBeaconRange;
        }
      }
    }
    return spans;
  }

  public getNoBeaconNumAtY(y: number, min: number = -Infinity, max: number = Infinity, beaconCheck: boolean = true): number {
    let spans: Span[] = this.getNoBeaconRangeAtY(y, min, max, beaconCheck);
    if (spans.length == 0) return 0;
    let sum = 0;
    if (beaconCheck) for (let beacon of this.beacons) {
      if (beacon.y != y) continue;
      for (let span of spans) {
        if (span.contains(beacon.x)) sum--;
      }
    }
    for (let span of spans) {
      sum += span.length();
    }
    return sum;
  }

  public getRowWithLessThan(range: Vec4, lessThan: number): number {
    for (let i = range.y1; i <= range.y2; i++) {
      if (this.getNoBeaconNumAtY(i, range.x1, range.x2, false) < lessThan) {
        return i;
      }
    }
  }
}

class Sensor {
  public range: number;
  public pos: Vec2;
  public beacon: Vec2;

  constructor(input: string) {
    let [sensX, sensY, beacX, beacY] = input.match(/(?<=x?y?=)-?(\d+)/g).map(Number);
    this.pos = { x: sensX, y: sensY };
    this.beacon = { x: beacX, y: beacY };
    this.range = Math.abs(beacX - sensX) + Math.abs(beacY - sensY);
  }

  public getNoBeaconRangeAtY(y: number, min: number, max: number): Span | null {
    let diff: number = this.range - Math.abs(y - this.pos.y);
    if (this.pos.x + diff < min) return null;
    if (this.pos.x - diff > max) return null;
    return diff < 0 ? null : new Span(Math.max(this.pos.x - diff, min), Math.min(this.pos.x + diff, max));
  }
}

class Span {
  public start: number;
  public end: number;
  constructor(start: number, end: number) {
    this.start = start;
    this.end = end;
  }

  public overlaps(other: Span): boolean {
    return this.contains(other.start) || this.contains(other.end) || other.contains(this.start);
  }

  public static merge(range1: Span, range2: Span): Span {
    return new Span(Math.min(range1.start, range2.start), Math.max(range1.end, range2.end));
  }

  public length(): number {
    return Math.abs(this.end - this.start) + 1;
  }

  public contains(x: number): boolean {
    return x >= this.start && x <= this.end;
  }
}