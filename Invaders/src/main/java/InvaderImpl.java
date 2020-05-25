public class InvaderImpl implements Invader {
    private int damage;
    private int distance;

    public InvaderImpl(int damage, int distance) {
        this.damage = damage;
        this.distance = distance;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;;
    }

    public int compareTo(Invader o) {

        int cmp = Integer.compare(this.distance, o.getDistance());

        if (cmp == 0){
            cmp = Integer.compare(o.getDamage(), this.damage);
        }

        return cmp;
    }
}
