package edu.csuci.appaca.utils.b2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class BasicContactListener implements ContactListener {

    private boolean isFixtureCollidable(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Collidable;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(isFixtureCollidable(a)) {
            ((Collidable) a.getBody().getUserData()).beginContact(contact, a, b);
        }
        if(isFixtureCollidable(b)) {
            ((Collidable) b.getBody().getUserData()).beginContact(contact, b, a);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(isFixtureCollidable(a)) {
            ((Collidable) a.getBody().getUserData()).endContact(contact, a, b);
        }
        if(isFixtureCollidable(b)) {
            ((Collidable) b.getBody().getUserData()).endContact(contact, b, a);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(isFixtureCollidable(a)) {
            ((Collidable) a.getBody().getUserData()).preSolve(contact, oldManifold, a, b);
        }
        if(isFixtureCollidable(b)) {
            ((Collidable) b.getBody().getUserData()).preSolve(contact, oldManifold, b, a);;
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(isFixtureCollidable(a)) {
            ((Collidable) a.getBody().getUserData()).postSolve(contact, impulse, a, b);
        }
        if(isFixtureCollidable(b)) {
            ((Collidable) b.getBody().getUserData()).postSolve(contact, impulse, b, a);;
        }

    }
}
