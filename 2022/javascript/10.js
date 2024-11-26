document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let commands = input.split("\n");
    let cpu = new CPU();
    commands.forEach(command => {
        cpu.execute(command);
    });
    document.getElementById("output").value = "Signal Strength Sum: " + cpu.sum;
    document.getElementById("output").value += "\n\n" + cpu.crt;
};
class CPU {
    register; //sprite Pos
    cycles;
    sum;
    crt;
    constructor() {
        this.register = 1;
        this.cycles = 0;
        this.sum = 0;
        this.crt = "";
    }
    execute(command) {
        switch (true) {
            case command.startsWith("noop"):
                this.cycle();
                break;
            case command.startsWith("addx"):
                this.cycle();
                this.cycle();
                this.register += Number.parseInt(command.split(" ")[1]);
        }
    }
    cycle() {
        if (this.cycles % 40 == 0) {
            this.crt += "\n";
        }
        this.crt += Math.abs(this.register - this.cycles % 40) <= 1 ? "#" : ".";
        this.cycles += 1;
        if ((this.cycles + 20) % 40 == 0) {
            this.sum += this.register * this.cycles;
        }
    }
}
export {};
