import { MonkeyOperation } from "./MonkeyOperation.ts";
import { NumericOperation } from "./NumericOperation.ts";
import { Operator } from "./Operator.ts";

export class OperationParser {
  public static fromString(input: string): NumericOperation | MonkeyOperation {
    if (OperationParser.isValidNumber(input)) {
      return new NumericOperation(Number.parseInt(input));
    }

    const [monke1, operator, monke2] = input.split(" ");
    return new MonkeyOperation(monke1, operator as Operator, monke2);
  }

  private static isValidNumber(num: string): boolean {
    return !Number.isNaN(Number.parseInt(num));
  }
}
