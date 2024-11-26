package Day2;

public class Match {
    private final Choice enemyChoice;
    private final Choice playerChoice;
    private final MatchResult proposedResult;
    public Match(String input) {
        String[] args = input.split(" ");
        enemyChoice = switch(args[0]) {
            case "A" -> Choice.ROCK;
            case "B" -> Choice.PAPER;
            default -> Choice.SCISSORS;
        };
        playerChoice = switch (args[1]) {
            case "X" -> Choice.ROCK;
            case "Y" -> Choice.PAPER;
            default -> Choice.SCISSORS;
        };
        proposedResult = switch(args[1]) {
            case "X" -> MatchResult.ENEMY_WIN;
            case "Y" -> MatchResult.DRAW;
            default -> MatchResult.PLAYER_WIN;
        };
    }

    public int getFirstResult(){
        final int matchCalculation = ((playerChoice.value - enemyChoice.value) + 3) % 3;
        MatchResult matchResult = switch (matchCalculation) {
            case 0 -> MatchResult.DRAW;
            case 1 -> MatchResult.PLAYER_WIN;
            default -> MatchResult.ENEMY_WIN;
        };
        return matchResult.value + playerChoice.value;
    }

    public int getSecondResult() {
        int choiceCalculation = (enemyChoice.value + proposedResult.value / 3) % 3;
        Choice proposedChoice = switch (choiceCalculation) {
            case 0 -> Choice.PAPER;
            case 1 -> Choice.SCISSORS;
            default -> Choice.ROCK;
        };
        return proposedResult.value + proposedChoice.value;
    }

    public enum MatchResult {
        PLAYER_WIN(6),
        ENEMY_WIN(0),
        DRAW(3);
        private final int value;
        MatchResult(int value) {
            this.value = value;
        }
    }
}
