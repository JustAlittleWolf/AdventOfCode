import { MathOperation } from "./MathOperation.ts";
import { Monkey } from "./Monkey.ts";
import { MonkeyReference } from "./MonkeyReference.ts";
import { Operation } from "./Operation.ts";
import { Operator } from "./Operator.ts";

export class MonkeyOperation implements Operation {
  private operator: Operator;
  private firstMonkey: MonkeyReference;
  private secondMonkey: MonkeyReference;

  constructor(firstMonkey: string, operator: Operator, secondMonkey: string) {
    this.operator = operator;
    this.firstMonkey = new MonkeyReference(firstMonkey);
    this.secondMonkey = new MonkeyReference(secondMonkey);
  }

  public getResult(operation: Operator): number | boolean {
    if (!operation) operation = this.operator;
    const num1 = this.firstMonkey.resolve().getResult() as number;
    const num2 = this.secondMonkey.resolve().getResult() as number;
    switch (operation) {
      case "+":
        return num1 + num2;
      case "-":
        return num1 - num2;
      case "*":
        return num1 * num2;
      case "/":
        return num1 / num2;
      case "=":
        return num1 == num2;
      default:
        throw new Error("Could not parse operator: " + this.operator);
    }
  }

  public hasChildMonkey(name: string): boolean {
    return this.firstMonkey.resolve().hasChildMonkey(name) ||
      this.secondMonkey.resolve().hasChildMonkey(name);
  }

  public getChildren(): Monkey[] {
    return [this.firstMonkey.resolve(), this.secondMonkey.resolve()];
  }

  // deno-lint-ignore ban-types
  public stringify(callback: Function): string {
    const result1 = MathOperation.simplify(
      this.firstMonkey.resolve().stringifyOperation(callback),
    );
    const result2 = MathOperation.simplify(
      this.secondMonkey.resolve().stringifyOperation(callback),
    );

    return `(${result1} ${this.operator} ${result2})`;
  }
}
