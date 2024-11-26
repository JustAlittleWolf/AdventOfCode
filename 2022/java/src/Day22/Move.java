package Day22;


public record Move(Direction direction, int distance) {
    public Move decreaseDistance() {
        return new Move(this.direction, this.distance - 1);
    }

    public Move stop() {
        return new Move(this.direction, 0);
    }
}
