import { MathOperation } from "./MathOperation.ts";
import { Monkey } from "./Monkey.ts";
import { MonkeyTable } from "./MonkeyTable.ts";

console.log("root monke says:");
console.log(MonkeyTable.getMonkey("root").getResult());

const rootChildren = MonkeyTable.getMonkey("root").getChildren();
let goal = 0;
let resolveMonkey = MonkeyTable.getMonkey("root");
for (const child of rootChildren) {
  if (child.hasChildMonkey("humn")) {
    resolveMonkey = child;
    continue;
  }
  goal = child.getResult() as number;
}

const operationString = resolveMonkey.stringifyOperation((monke: Monkey) =>
  monke.name == "humn" ? "x" : undefined
);

console.log("\nhumn number for root truthy:");
console.log(
  MathOperation.solve(`${goal} = ${operationString}`, "x"),
);
