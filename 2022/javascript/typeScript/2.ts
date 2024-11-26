document.getElementById("submit").onclick = () => {
  let input: string = (<HTMLInputElement>document.getElementById("input")).value;

  let points: number = 0;
  for (let row of input.split("\n")) {
    let enemy = RPS.mapFirst(row.split(" ")[0]);
    let you = RPS.mapSecond(row.split(" ")[1]);

    points += you.valueOf();
    points += RPS.wins(you, enemy).valueOf();
  }

  (<HTMLInputElement>document.getElementById("output")).value = "Points Round 1: " + points;

  points = 0;
  for (let row of input.split("\n")) {
    let enemy = RPS.mapFirst(row.split(" ")[0]);
    let result = RPS.mapSecondPart2(row.split(" ")[1]);

    points += result.valueOf();
    points += RPS.resultToChoice(enemy, result).valueOf();
  }

  (<HTMLInputElement>document.getElementById("output")).value += "\nPoints Round 2: " + points;
}

enum choice {
  "rock" = 1,
  "paper" = 2,
  "scissors" = 3
}

enum result {
  "loss" = 0,
  "draw" = 3,
  "win" = 6
}

class RPS {
  public static wins(first: choice, second: choice): result {
    if (first == second) return result.draw;
    if (first == choice.rock && second == choice.scissors) return result.win;
    if (first == choice.paper && second == choice.rock) return result.win;
    if (first == choice.scissors && second == choice.paper) return result.win;
    return result.loss;
  }

  public static mapFirst(input: string): choice {
    return { "A": choice.rock, "B": choice.paper, "C": choice.scissors }[input];
  }

  public static mapSecond(input: string): choice {
    return { "X": choice.rock, "Y": choice.paper, "Z": choice.scissors }[input];
  }

  public static mapSecondPart2(input: string): result {
    return { "X": result.loss, "Y": result.draw, "Z": result.win }[input];
  }

  public static resultToChoice(enemyChoice: choice, outcome: result): choice {
    if (outcome == result.draw) return enemyChoice;
    if (enemyChoice == choice.rock) return outcome == result.win ? choice.paper : choice.scissors;
    if (enemyChoice == choice.paper) return outcome == result.win ? choice.scissors : choice.rock;
    if (enemyChoice == choice.scissors) return outcome == result.win ? choice.rock : choice.paper;
  }
}