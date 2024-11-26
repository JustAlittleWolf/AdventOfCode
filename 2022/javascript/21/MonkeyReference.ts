import { Monkey } from "./Monkey.ts";
import { MonkeyTable } from "./MonkeyTable.ts";

export class MonkeyReference {
  private name: string;

  constructor(name: string) {
    this.name = name;
  }

  public resolve(): Monkey {
    return MonkeyTable.getMonkey(this.name);
  }
}
