package edu.csuci.appaca.notifications;

import android.content.Context;

import edu.csuci.appaca.data.StaminaManager;

public class StaminaNotification {

    public static void checkIfStaminaIsFull(Context context){
        int currentStamina = StaminaManager.getCurrentStamina();
        if (currentStamina == StaminaManager.MAX_STAMINA)
        {
            sendNotification(context);
        }
    }

    private static void sendNotification(Context context){}
}
