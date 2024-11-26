document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let valveManager = new ValveManager(input);
    valveManager.compress();
    document.getElementById("output").value = "Maximum Pressure: " + ValveManager.getMaximumPressure(valveManager.getValvesCopy(), "AA", 30);
    document.getElementById("output").value += "\n2: ";
};
class ValveManager {
    valves = new Map();
    constructor(input) {
        input.split("\n").forEach(valve => {
            this.valves.set(valve.match(/(?<=Valve )\w+/)[0], new Valve(parseInt(valve.match(/(?<=rate=)\d+/)[0]), valve.match(/(?<=Valve )\w+/)[0], valve.match(/(?<=valves? )[A-Z, ]+/)[0]));
        });
    }
    static getMaximumPressure(valves, last, minutesLeft) {
        let valveArr = Array.from(valves.values());
        if (valves.size == 2)
            return valveArr[0].maximumFlow(valveArr[1], last, minutesLeft);
        if (valves.size > 2) {
            let maxPressures = [];
            for (let [name, valve] of valves) {
                let newMap = new Map(valves);
                newMap.delete(name);
                let lastDistance = valve.paths.get(last) == undefined ? 0 : valve.paths.get(last);
                let minutesLeftAfterValve = minutesLeft - (1 + lastDistance);
                if (minutesLeftAfterValve > 0)
                    maxPressures.push(valve.pressure * minutesLeftAfterValve + ValveManager.getMaximumPressure(newMap, name, minutesLeftAfterValve));
            }
            if (maxPressures.length == 0)
                return 0;
            return Math.max(...maxPressures);
        }
    }
    getValve(name) {
        return this.valves.get(name);
    }
    compress() {
        while (this.getMinPathNum() != this.valves.size - 1) {
            this.compressStep();
        }
        this.valves.forEach((valve, name) => {
            if (valve.pressure == 0 && name != "AA") {
                this.valves.delete(name);
                this.valves.forEach(valve => valve.paths.delete(name));
            }
        });
    }
    compressStep() {
        this.valves.forEach((valve, name) => {
            new Map(valve.paths).forEach((distance, path) => {
                this.getValve(path).paths.forEach((neighDistance, neighPath) => {
                    if ((!valve.paths.has(neighPath) || valve.paths.get(neighPath) > neighDistance + distance) && neighPath != name)
                        valve.paths.set(neighPath, neighDistance + distance);
                });
            });
        });
    }
    getMinPathNum() {
        let min = Infinity;
        this.valves.forEach(valve => {
            min = Math.min(min, valve.paths.size);
        });
        return min;
    }
    getValvesCopy() {
        return new Map(this.valves);
    }
}
class Valve {
    pressure;
    paths = new Map();
    name;
    constructor(pressure, name, leadsTo) {
        this.pressure = pressure;
        this.name = name;
        leadsTo.replaceAll(" ", "").split(",").forEach(dest => this.paths.set(dest, 1));
    }
    maximumFlow(other, from, timeLeft) {
        let d1 = this.paths.get(from);
        let d2 = other.paths.get(from);
        let variant1 = this.pressure * Math.max(timeLeft - (1 + d1), 0) + other.pressure * Math.max(timeLeft - (2 + d1 + this.paths.get(other.name)), 0);
        let variant2 = other.pressure * Math.max(timeLeft - (1 + d2), 0) + this.pressure * Math.max(timeLeft - (2 + d2 + other.paths.get(this.name)), 0);
        return Math.max(variant1, variant2);
    }
}
export {};
