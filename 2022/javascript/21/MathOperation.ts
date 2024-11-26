export class MathOperation {
  public static simplify(operation: string): string {
    try {
      return eval(operation);
    } catch {
      return operation;
    }
  }

  //Simple equation solver served by Noodle King
  public static solve(operation: string, solvee: string): number {
    let leftHandSide = Number.parseInt(operation.split(" = ")[0]);
    let rightHandSide = operation.split(" = ")[1];

    while (rightHandSide.replace(/[^\(]/g, "").length > 0) {
      if (!(rightHandSide.startsWith("(") && rightHandSide.endsWith(")"))) {
        throw new Error("Could not solve for " + rightHandSide);
      }

      rightHandSide = rightHandSide.slice(1).slice(0, -1);
      const tempSolveSide = rightHandSide.replace(/\(.+\)/, "").replace(
        solvee,
        "",
      );
      const tempOperation = tempSolveSide.match(/[+\-*/]/)?.[0];
      const tempFactor = Number.parseInt(
        tempSolveSide.replace(/[+\-*/]/, "").trim(),
      );
      const tempXonLeftSide = tempSolveSide.split(/[+\-*/]/).map((elem) =>
        elem.trim()
      )[0].length == 0;
      switch (tempOperation) {
        case "+":
          leftHandSide -= tempFactor;
          break;
        case "-":
          if (tempXonLeftSide) {
            leftHandSide += tempFactor;
          } else {
            leftHandSide = tempFactor - leftHandSide;
          }
          break;
        case "*":
          leftHandSide /= tempFactor;
          break;
        case "/":
          if (tempXonLeftSide) {
            leftHandSide *= tempFactor;
          } else {
            leftHandSide = tempFactor / leftHandSide;
          }
          break;
        default:
          throw new Error("Could not parse operation: " + tempOperation);
      }
      rightHandSide = rightHandSide.match(/\(.+\)/)?.[0] as string;
      if (!rightHandSide) rightHandSide = solvee;
    }

    return leftHandSide;
  }
}
