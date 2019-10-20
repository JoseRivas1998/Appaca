package edu.csuci.appaca.data.b2d;

import com.badlogic.gdx.physics.box2d.Fixture;

public enum UserData {

    PLATFORM, PLAYER, PLAYER_FOOT;

    public static boolean isFixtureData(Fixture fixture, UserData data) {
        return fixture != null && fixture.getUserData() != null && fixture.getUserData().equals(data);
    }

}
