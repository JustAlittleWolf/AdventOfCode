package me.wolfii.implementations.day24;

public record Hailstone(Vec3L position, Vec3L velocity) {
    public static Hailstone of(String line) {
        String[] parts = line.split(" @ ");
        String[] pos = parts[0].split(",");
        Vec3L position = new Vec3L(Long.parseLong(pos[0].trim()), Long.parseLong(pos[1].trim()), Long.parseLong(pos[2].trim()));
        String[] vel = parts[1].split(",");
        Vec3L velocity = new Vec3L(Long.parseLong(vel[0].trim()), Long.parseLong(vel[1].trim()), Long.parseLong(vel[2].trim()));
        return new Hailstone(position, velocity);
    }

    public boolean pathsIntersect(Hailstone other, long minX, long maxX, long minY, long maxY) {
        double dividend = velocity.x() * other.position.y() - velocity.x() * position.y() - velocity.y() * other.position.x() + velocity.y() * position.x();
        double dividor = velocity.y() * other.velocity.x() - velocity.x() * other.velocity.y();

        if(dividor == 0) return false;

        double s = dividend / dividor;
        if(s < 0) return false;
        double t;
        if(this.velocity.x() == 0) {
            t = ( other.position.y() + s * other.velocity.y() - position.y()) / (double) velocity.y();
        } else {
            t = (other.position.x() + s * other.velocity.x() - position.x()) / (double) velocity.x();
        }
        if(t < 0) return false;

        double intersectX = this.position.x() + t * this.velocity.x();
        double intersectY = this.position.y() + t * this.velocity.y();
        if(intersectX < minX || intersectX > maxX) return false;
        return !(intersectY < minY) && !(intersectY > maxY);
    }
}
