document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let monkeyGroup = new MonkeyGroup(input);
    monkeyGroup.doRounds(20);
    document.getElementById("output").value = "Monkey Business 20: " + monkeyGroup.getMonkeyBusiness();
    monkeyGroup = new MonkeyGroup(input, true);
    monkeyGroup.doRounds(10000);
    document.getElementById("output").value += "\nMonkey Business 10000: " + monkeyGroup.getMonkeyBusiness();
};
class Monkey {
    items;
    operation;
    test;
    monkeyGroup;
    divider;
    inspections = 0;
    nextMonkey;
    constructor(monke, monkeyGroup) {
        let rows = monke.split("\n");
        this.items = rows[1].replaceAll(",", "").split(" ").map(Number).splice(4);
        this.operation = new Function("old", "return " + rows[2].split("=")[1]);
        this.divider = Number.parseInt(rows[3].split(" ")[5]);
        this.nextMonkey = {
            truthy: Number.parseInt(rows[4].split(" ")[9]),
            falsy: Number.parseInt(rows[5].split(" ")[9])
        };
        this.test = (item) => (item / this.divider) % 1 == 0 ? this.nextMonkey.truthy : this.nextMonkey.falsy;
        this.monkeyGroup = monkeyGroup;
    }
    doCycle() {
        this.items.forEach(item => {
            this.inspections++;
            item = Math.floor(this.operation(item) / (this.monkeyGroup.stressed ? 1 : 3)) % this.monkeyGroup.getLeastCommonMultiple();
            this.monkeyGroup.getMonkey(this.test(item)).addItem(item);
        });
        this.items = [];
    }
    addItem(item) {
        this.items.push(item);
    }
    getDivider() {
        return this.divider;
    }
}
class MonkeyGroup {
    monkeys;
    leastCommonMultiple;
    stressed;
    constructor(monkes, stressed = false) {
        this.monkeys = monkes.split("\n\n").map(elem => new Monkey(elem, this));
        this.stressed = stressed;
        this.leastCommonMultiple = this.monkeys.reduce((acc, monke) => this.kgV(acc, monke.getDivider()), 1);
    }
    getMonkey(i) {
        return this.monkeys[i];
    }
    doRound() {
        this.monkeys.forEach(monke => {
            monke.doCycle();
        });
    }
    doRounds(rounds) {
        for (let i = 0; i < rounds; i++) {
            this.doRound();
        }
    }
    getMonkeyBusiness() {
        return this.getMonkeyByActiveness(0).inspections * this.getMonkeyByActiveness(1).inspections;
    }
    getMonkeyByActiveness(i) {
        let inspectionArray = [];
        this.monkeys.forEach((monke, curIndex) => inspectionArray.push({ inspections: monke.inspections, index: curIndex }));
        inspectionArray.sort((a, b) => b.inspections - a.inspections);
        return this.getMonkey(inspectionArray[i].index);
    }
    getLeastCommonMultiple() {
        return this.leastCommonMultiple;
    }
    kgV(a, b) {
        return (a * b) / this.ggT(a, b);
    }
    ggT(a, b) {
        while (b != 0) {
            let h = a % b;
            a = b;
            b = h;
        }
        return Math.abs(a);
    }
}
export {};
