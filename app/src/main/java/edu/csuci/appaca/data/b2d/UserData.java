package edu.csuci.appaca.data.b2d;

import com.badlogic.gdx.physics.box2d.Fixture;

public enum UserData {

    PLATFORM, PLAYER, PLAYER_FOOT, SPRING;

    public static boolean isFixtureData(Fixture fixture, UserData data) {
        return fixture != null && fixture.getUserData() != null && fixture.getUserData().equals(data);
    }

    public static boolean areBothFixtureData(Fixture thisFixture, UserData thisData, Fixture otherFixture, UserData otherData) {
        return isFixtureData(thisFixture, thisData) && isFixtureData(otherFixture, otherData);
    }

}
