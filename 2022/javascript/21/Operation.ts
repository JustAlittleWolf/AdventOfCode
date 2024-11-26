import { Monkey } from "./Monkey.ts";
import { Operator } from "./Operator.ts";

export interface Operation {
  getResult(operation: Operator | null): number | boolean;
  hasChildMonkey(name: string): boolean;
  getChildren(): Monkey[];
  // deno-lint-ignore ban-types
  stringify(callback: Function): string;
}
