package cz.muni.fi.model.structural.dual;

public class Arm {

    private int armAngle;
    private int armNum;
    private int armOffset;
    private int leftLanes;
    private int rightLanes;
    private Site nextSite;
    private long osmWayId;
    private String streetName;

    public static class Builder {
        private int armAngle;
        private int armNum;
        private int armOffset;
        private int leftLanes = 0;
        private int rightLanes = 0;
        private Site nextSite;
        private long osmWayId = 0;
        private String streetName;

        public Builder armAngle(int val) {
            armAngle = val;
            return this;
        }

        public Builder armNum(int val) {
            armNum = val;
            return this;
        }

        public Builder armOffset(int val) {
            armOffset = val;
            return this;
        }

        public Builder leftLanes(int val) {
            leftLanes = val;
            return this;
        }

        public Builder rightLanes(int val) {
            rightLanes = val;
            return this;
        }

        public Builder nextSite(Site val) {
            nextSite = val;
            return this;
        }

        public Builder osmWayId(long val) {
            osmWayId = val;
            return this;
        }

        public Builder streetName(String val) {
            streetName = val;
            return this;
        }

        public Arm build() {
            return new Arm(this);
        }
    }

    private Arm(Builder builder) {
        this.armAngle = builder.armAngle;
        this.armNum = builder.armNum;
        this.armOffset = builder.armOffset;
        this.leftLanes = builder.leftLanes;
        this.rightLanes = builder.rightLanes;
        this.nextSite = builder.nextSite;
        this.osmWayId = builder.osmWayId;
        this.streetName = builder.streetName;
    }

    public int getArmAngle() {
        return armAngle;
    }

    public int getArmNum() {
        return armNum;
    }

    public int getArmOffset() {
        return armOffset;
    }

    public int getLeftLanes() {
        return leftLanes;
    }

    public int getRightLanes() {
        return rightLanes;
    }

    public Site getNextSite() {
        return nextSite;
    }

    public long getOsmWayId() {
        return osmWayId;
    }

    public String getStreetName() {
        return streetName;
    }
}
