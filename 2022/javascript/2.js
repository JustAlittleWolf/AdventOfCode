document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let points = 0;
    for (let row of input.split("\n")) {
        let enemy = RPS.mapFirst(row.split(" ")[0]);
        let you = RPS.mapSecond(row.split(" ")[1]);
        points += you.valueOf();
        points += RPS.wins(you, enemy).valueOf();
    }
    document.getElementById("output").value = "Points Round 1: " + points;
    points = 0;
    for (let row of input.split("\n")) {
        let enemy = RPS.mapFirst(row.split(" ")[0]);
        let result = RPS.mapSecondPart2(row.split(" ")[1]);
        points += result.valueOf();
        points += RPS.resultToChoice(enemy, result).valueOf();
    }
    document.getElementById("output").value += "\nPoints Round 2: " + points;
};
var choice;
(function (choice) {
    choice[choice["rock"] = 1] = "rock";
    choice[choice["paper"] = 2] = "paper";
    choice[choice["scissors"] = 3] = "scissors";
})(choice || (choice = {}));
var result;
(function (result) {
    result[result["loss"] = 0] = "loss";
    result[result["draw"] = 3] = "draw";
    result[result["win"] = 6] = "win";
})(result || (result = {}));
class RPS {
    static wins(first, second) {
        if (first == second)
            return result.draw;
        if (first == choice.rock && second == choice.scissors)
            return result.win;
        if (first == choice.paper && second == choice.rock)
            return result.win;
        if (first == choice.scissors && second == choice.paper)
            return result.win;
        return result.loss;
    }
    static mapFirst(input) {
        return { "A": choice.rock, "B": choice.paper, "C": choice.scissors }[input];
    }
    static mapSecond(input) {
        return { "X": choice.rock, "Y": choice.paper, "Z": choice.scissors }[input];
    }
    static mapSecondPart2(input) {
        return { "X": result.loss, "Y": result.draw, "Z": result.win }[input];
    }
    static resultToChoice(enemyChoice, outcome) {
        if (outcome == result.draw)
            return enemyChoice;
        if (enemyChoice == choice.rock)
            return outcome == result.win ? choice.paper : choice.scissors;
        if (enemyChoice == choice.paper)
            return outcome == result.win ? choice.scissors : choice.rock;
        if (enemyChoice == choice.scissors)
            return outcome == result.win ? choice.rock : choice.paper;
    }
}
export {};
