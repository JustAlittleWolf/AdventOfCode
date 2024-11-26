import { Monkey } from "./Monkey.ts";
import { Operation } from "./Operation.ts";

export class NumericOperation implements Operation {
  private result: number;

  constructor(result: number) {
    this.result = result;
  }

  public getResult(): number {
    return this.result;
  }

  public hasChildMonkey(): boolean {
    return false;
  }

  public getChildren(): Monkey[] {
    return [];
  }

  public stringify(): string {
    return `${this.result}`;
  }
}
