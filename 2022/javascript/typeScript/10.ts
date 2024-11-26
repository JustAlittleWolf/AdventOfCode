document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;
  let commands: Array<string> = input.split("\n");
  let cpu: CPU = new CPU();
  commands.forEach(command => {
    cpu.execute(command);
  });
  (<HTMLInputElement>document.getElementById("output")).value = "Signal Strength Sum: " + cpu.sum;
  (<HTMLInputElement>document.getElementById("output")).value += "\n\n" + cpu.crt;
}

class CPU {
  private register: number; //sprite Pos
  private cycles: number;
  public sum: number;
  public crt: string;

  constructor() {
    this.register = 1;
    this.cycles = 0;
    this.sum = 0;
    this.crt = "";
  }

  public execute(command: string): void {
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

  private cycle() {
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