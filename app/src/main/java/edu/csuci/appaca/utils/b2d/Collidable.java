package edu.csuci.appaca.utils.b2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public interface Collidable {

    void beginContact(Contact contact, Fixture thisFixture, Fixture other);
    void endContact(Contact contact, Fixture thisFixture, Fixture other);
    void preSolve(Contact contact, Manifold oldManifold, Fixture thisFixture, Fixture other);
    void postSolve(Contact contact, ContactImpulse impulse, Fixture thisFixture, Fixture other);

}
