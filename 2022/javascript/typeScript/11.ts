document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;
  let monkeyGroup: MonkeyGroup = new MonkeyGroup(input);
  monkeyGroup.doRounds(20);
  (<HTMLInputElement>document.getElementById("output")).value = "Monkey Business 20: " + monkeyGroup.getMonkeyBusiness();
  monkeyGroup = new MonkeyGroup(input, true);
  monkeyGroup.doRounds(10000);
  (<HTMLInputElement>document.getElementById("output")).value += "\nMonkey Business 10000: " + monkeyGroup.getMonkeyBusiness();
}

type NextMonkey = {
  truthy: number,
  falsy: number
}

class Monkey {
  private items: Array<number>;
  private operation: Function;
  private test: Function;
  private monkeyGroup: MonkeyGroup;
  private divider: number;
  public inspections: number = 0;
  private nextMonkey: NextMonkey;

  constructor(monke: string, monkeyGroup: MonkeyGroup) {
    let rows: Array<string> = monke.split("\n");
    this.items = rows[1].replaceAll(",", "").split(" ").map(Number).splice(4);
    this.operation = new Function("old", "return " + rows[2].split("=")[1]);
    this.divider = Number.parseInt(rows[3].split(" ")[5]);
    this.nextMonkey = {
      truthy: Number.parseInt(rows[4].split(" ")[9]),
      falsy: Number.parseInt(rows[5].split(" ")[9])
    }
    this.test = (item: number): number => (item / this.divider) % 1 == 0 ? this.nextMonkey.truthy : this.nextMonkey.falsy;
    this.monkeyGroup = monkeyGroup;
  }

  public doCycle(): void {
    this.items.forEach(item => {
      this.inspections++;
      item = Math.floor(this.operation(item) / (this.monkeyGroup.stressed ? 1 : 3)) % this.monkeyGroup.getLeastCommonMultiple();
      this.monkeyGroup.getMonkey(this.test(item)).addItem(item);
    });
    this.items = [];
  }

  public addItem(item: number): void {
    this.items.push(item);
  }

  public getDivider(): number {
    return this.divider;
  }
}

class MonkeyGroup {
  private monkeys: Array<Monkey>;
  private leastCommonMultiple: number;
  public stressed: boolean;

  constructor(monkes: string, stressed: boolean = false) {
    this.monkeys = monkes.split("\n\n").map(elem => new Monkey(elem, this));
    this.stressed = stressed;
    this.leastCommonMultiple = this.monkeys.reduce((acc, monke) => this.kgV(acc, monke.getDivider()), 1)
  }

  public getMonkey(i: number): Monkey {
    return this.monkeys[i];
  }

  private doRound(): void {
    this.monkeys.forEach(monke => {
      monke.doCycle();
    });
  }

  public doRounds(rounds: number): void {
    for (let i = 0; i < rounds; i++) {
      this.doRound();
    }
  }

  public getMonkeyBusiness(): number {
    return this.getMonkeyByActiveness(0).inspections * this.getMonkeyByActiveness(1).inspections;
  }

  private getMonkeyByActiveness(i: number): Monkey {
    type inspectionCounter = {
      inspections: number,
      index: number
    };
    let inspectionArray: Array<inspectionCounter> = [];
    this.monkeys.forEach((monke, curIndex) => inspectionArray.push({ inspections: monke.inspections, index: curIndex }));
    inspectionArray.sort((a, b) => b.inspections - a.inspections);
    return this.getMonkey(inspectionArray[i].index);
  }

  public getLeastCommonMultiple(): number {
    return this.leastCommonMultiple;
  }

  private kgV(a: number, b: number): number {
    return (a * b) / this.ggT(a, b);
  }

  private ggT(a: number, b: number): number {
    while (b != 0) {
      let h: number = a % b;
      a = b;
      b = h;
    }
    return Math.abs(a);
  }
}