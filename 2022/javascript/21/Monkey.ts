import { Operation } from "./Operation.ts";
import { OperationParser } from "./OperationParser.ts";
import { Operator } from "./Operator.ts";

export class Monkey {
  public name: string;
  private operation: Operation;

  constructor(input: string) {
    const [name, operation] = input.split(": ");
    this.name = name;
    this.operation = OperationParser.fromString(operation);
  }

  public getResult(operation: Operator | null = null): number | boolean {
    return this.operation.getResult(operation);
  }

  public hasChildMonkey(name: string): boolean {
    if (this.name == name) return true;
    return this.operation.hasChildMonkey(name);
  }

  public getChildren(): Monkey[] {
    return this.operation.getChildren();
  }

  // deno-lint-ignore ban-types
  public stringifyOperation(callback: Function = () => {}): string {
    const callbackResult = callback(this);
    if (callbackResult !== undefined) return callbackResult;
    return this.operation.stringify(callback);
  }
}
