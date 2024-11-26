import { Monkey } from "./Monkey.ts";
import { input } from "./input.ts";

export class MonkeyTable {
  private static monkeys: Map<string, Monkey> = new Map();

  static {
    for (const monke of input.split("\n")) {
      const name = monke.split(": ")[0];
      MonkeyTable.monkeys.set(name, new Monkey(monke));
    }
  }

  public static getMonkey(name: string): Monkey {
    if (!MonkeyTable.monkeys.has(name)) {
      throw new Error("Tried to access monkey that doesn't exist: " + name);
    }
    return MonkeyTable.monkeys.get(name) as Monkey;
  }
}
