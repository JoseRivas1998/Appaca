package edu.csuci.appaca.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HighScore {

    private enum HighScoreInstance {
        INSTANCE;
        Map<MiniGames, Integer> highScores;
        boolean loaded;

        HighScoreInstance() {
            this.highScores = new HashMap<>();
            this.loaded = false;
        }
    }

    public static int getHighScore(MiniGames miniGame) {
        if(!HighScoreInstance.INSTANCE.highScores.containsKey(miniGame)) {
            HighScoreInstance.INSTANCE.highScores.put(miniGame, 0);
            return getHighScore(miniGame);
        }
        return HighScoreInstance.INSTANCE.highScores.get(miniGame);
    }

    public static boolean putHighScore(MiniGames miniGame, int highScore) {
        int currentHighScore = getHighScore(miniGame);
        int newHighScore = Math.max(highScore, currentHighScore);
        HighScoreInstance.INSTANCE.highScores.put(miniGame, newHighScore);
        return newHighScore == highScore;
    }

    public static void init() {
        for (MiniGames miniGame : MiniGames.values()) {
            HighScoreInstance.INSTANCE.highScores.put(miniGame, 0);
        }
    }

    public static JSONArray toJSONArray() throws JSONException {
        JSONArray highScores = new JSONArray();
        for (MiniGames miniGame : MiniGames.values()) {
            JSONObject gameScore = new JSONObject();
            gameScore.put("id", miniGame.ordinal());
            gameScore.put("score", getHighScore(miniGame));
            highScores.put(gameScore);
        }
        return highScores;
    }

    public static void load(JSONArray highScores) {
        if(HighScoreInstance.INSTANCE.loaded) return;
        try {
            for (int i = 0; i < highScores.length(); i++) {
                JSONObject gameScore = highScores.getJSONObject(i);
                MiniGames game = MiniGames.values()[gameScore.getInt("id")];
                int score = gameScore.getInt("score");
                HighScoreInstance.INSTANCE.highScores.put(game, score);
            }
        } catch (JSONException e) {
            init();
        }
        HighScoreInstance.INSTANCE.loaded = true;
    }

}
